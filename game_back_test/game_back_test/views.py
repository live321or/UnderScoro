from django.http import JsonResponse
from django.contrib.auth import authenticate, login
from django.contrib.auth.models import User
from django.views.decorators.csrf import csrf_exempt
import logging
from math import floor

from .helpers import check_pass, check_username
from .game.tasks import *

LOGIN_REQUIRED = JsonResponse({'message': 'required logging in'}, status=401)
METHOD_NOT_ALLOWED = JsonResponse({'message': 'method not allowed'}, status=405)
OK_RESPONSE = JsonResponse({'message': 'processing...'}, status=202)

logger = logging.getLogger(__name__)

#def get_character(c_id):
 #   async_res = get_char.delay(c_id)
  #  while async_res.status != "SUCCESS":
   #     pass
    #return async_res.get()
@csrf_exempt
def del_all_users(request):
    if request.user.is_staff:
        db.Character.objects.filter(id__gt=3).delete()
        User.objects.filter(id__gt=3).delete()
        return JsonResponse({"deleted": true})


@csrf_exempt
def location_info(request):
    if request.method not in ("GET", "POST"):
        return METHOD_NOT_ALLOWED
    c = db.Character.objects.get(id=request.user.id)
    if request.method == "POST":
        try:
            l = db.Location.objects.get(name=request.POST["to"])
        except:
            return JsonResponse({"message": "Location {} not found".format(request.POST["to"])}, status=404)
        if l not in [it.to_location.name for it in db.LocationPath.objects.all().filter(
            location_from__name=c.location.name
        )]:
            return JsonResponse({"message": "You can not move from {} to {}".format(c.location.name, request.POST["to"])}, status=406)
        c.location = db.Location.objects.get(name=request.POST["to"])
        c.save()
    try:
        l = db.Location.objects.get(name=c.location.name)
    except:
        return JsonResponse({"message": "Location {} not found".format(c.location.name)}, status=404)
    usrs = db.Character.objects.filter(location__name=c.location.name).exclude(id=request.user.id)
    objs = [{
        "username": it.username,
        "name": it.first_name,
        "id": it.id
    } for it in User.objects.filter(id__in=[ch.id for ch in usrs])]
    return JsonResponse({
        "name": l.name,
        "descr": l.description,
        "id": l.id,
        "transitions": [it.to_location for it in db.LocationPath.objects.get(c.location.id)] ,
        "users_on_location": objs
    })
    pass


@csrf_exempt
def set_abilities(request):
    if request.method != "POST":
        return METHOD_NOT_ALLOWED
    vals = [float(request.POST[k]) for k in list(request.POST.keys()) if k in ("Str", "Agi", "Int", "Luc")]
    c = db.Character.objects.get(id=request.user.id)
    logger.debug("character: str = " + str(c.strength) + " agi = "+str(c.agility)+" int = "+str(c.intelligence)+" luck = "+str(c.luck)+" lvl = "+str(c.lvl)+" attrs = "+str(c.attributes)+" exp = "+str(c.exp))
    attrs = c.strength + c.agility + c.intelligence + c.luck
    req_points = sum(vals) - attrs
    logger.debug("req_points = " + str(req_points))
    if req_points > c.attributes:
        return JsonResponse({"name": request.user.first_name,
            "location": c.location.name,
            "Lvl": c.lvl,
            "point": c.attributes,
            "EXP": c.exp,
            "hp": c.hp,
            "maxHp": c.max_hp,
            "Str": c.strength,
            "Agi": c.agility,
            "Int": c.intelligence,
            "Luc": c.luck}, status=413)
    for k in list(request.POST.keys()):
        if k == "Str":
            c.attributes -= floor(float(request.POST[k])) - c.strength
            c.strength = floor(float(request.POST[k]))
        elif k == "Agi":
            c.attributes -= floor(float(request.POST[k])) - c.agility
            c.agility = floor(float(request.POST[k]))
        elif k == "Int":
            c.attributes -= floor(float(request.POST[k])) - c.intelligence
            c.intelligence = floor(float(request.POST[k]))
        elif k == "Luc":
            c.attributes -= floor(float(request.POST[k])) - c.luck
            c.luck = floor(float(request.POST[k]))
    logger.debug("before save character: str = " + str(c.strength) + " agi = "+str(c.agility)+" int = "+str(c.intelligence)+" luck = "+str(c.luck)+" lvl = "+str(c.lvl)+" attrs = "+str(c.attributes)+" exp = "+str(c.exp))
    c.save()
    return JsonResponse({"name": request.user.first_name,
            "location": c.location.name,
            "Lvl": c.lvl,
            "point": c.attributes,
            "EXP": c.exp,
            "hp": c.hp,
            "maxHp": c.max_hp,
            "Str": c.strength,
            "Agi": c.agility,
            "Int": c.intelligence,
            "Luc": c.luck})


@csrf_exempt
def battle_result(request):
    if request.method != "POST":
        return METHOD_NOT_ALLOWED
    c = db.Character.objects.get(id=request.user.id)
    logger.debug("bres function: POST[EXP] = " + str(request.POST["EXP"]))
    logger.debug("character: str = " + str(c.strength) + " agi = "+str(c.agility)+" int = "+str(c.intelligence)+" luck = "+str(c.luck)+" lvl = "+str(c.lvl)+" attrs = "+str(c.attributes)+" exp = "+str(c.exp))
    logger.debug("before adding c.exp = " + str(c.exp))
    c.exp += float(request.POST['EXP'])
    c.save()
    logger.debug("character after save: str = " + str(c.strength) + " agi = "+str(c.agility)+" int = "+str(c.intelligence)+" luck = "+str(c.luck)+" lvl = "+str(c.lvl)+" attrs = "+str(c.attributes)+" exp = ="+str(c.exp))
    return JsonResponse({
        "name": request.user.first_name,
            "location": c.location.name,
            "Lvl": c.lvl,
            "point": c.attributes,
            "EXP": c.exp,
            "hp": c.hp,
            "maxHp": c.max_hp,
            "Str": c.strength,
            "Agi": c.agility,
            "Int": c.intelligence,
            "Luc": c.luck
    })


@csrf_exempt
def move(request):
    if not request.user.is_authenticated:
        return LOGIN_REQUIRED
    if not (request.method == 'POST' or request.method == 'GET'):
        return METHOD_NOT_ALLOWED
    if request.method == 'GET':
        if not request.GET['location_to'] in [it.to_location.name for it
                                              in db.Location.objects.get(
                id=db.Character.objects.get(id=request.user.id).location.id).from_loc.all()]:
            return JsonResponse({'message': 'no such path'}, status=400)
        move_character.delay(request.user.pk, db.Location.objects.get(name=request.GET['location_to']).pk)
        return OK_RESPONSE
    if request.method == 'POST':
        if not request.POST['location_to'] in [it.to_location.name for it
                                              in db.Location.objects.get(
                id=db.Character.objects.get(id=request.user.id).location.id).from_loc.all()]:
            return JsonResponse({'message': 'no such path'}, status=400)
        move_character.delay(request.user.pk, db.Location.objects.get(name=request.POST['location_to']).pk)
        return OK_RESPONSE


@csrf_exempt
def info(request):
    print(chars)
    if request.user.is_authenticated and request.method == 'GET':
        if 'username' not in request.GET.keys():
            c = db.Character.objects.get(id=request.user.id)
        else:
            c = db.Character.objects.get(id=User.objects.get_by_natural_key(request.GET['username']))
        res = {
            "id": c.id,
            'username': User.objects.get(id=c.id).username,
            "locationId": c.location.id,
            'locationName': c.location.name
        }
        stats = {
            'max_hp': c.max_hp,
            'max_mp': c.max_mp,
            'lvl': c.lvl,
            'max_weight': c.max_weight
        }
        if User.objects.get(id=c.id).username == request.user.username:
            items = c.iteminstance_set
            stats['hp'] = c.hp
            stats['mp'] = c.mp
            stats['exp'] = c.exp
            stats['attributes'] = {
                'strength': c.strength,
                'agility': c.agility,
                'intel': c.intelligence,
                'luck': c.luck
            }
            stats['skills'] = []
            try:
                for skill in db.CharactersSpell.objects.filter(character__id=c.id):
                    print(skill)
            except Exception as e:
                print(e)
            stats['skill_points'] = c.skill_points
            stats['atr_points'] = c.attributes
            itms = []
            for item in items.filter(inventory__in=('b', 'i')):
                itms.append(item)
        res['stats'] = stats
        return JsonResponse(res)
    elif request.method == "POST":
        c = db.Character.objects.get(id=request.user.id)
        return JsonResponse({"name": request.user.first_name,
            "location": c.location.name,
            "Lvl": c.lvl,
            "point": c.attributes,
            "EXP": c.exp,
            "hp": c.hp,
            "maxHp": c.max_hp,
            "Str": c.strength,
            "Agi": c.agility,
            "Int": c.intelligence,
            "Luc": c.luck})
    elif request.method == "DELETE":
        db.Character.objects.get(id=request.user.id).delete()
        User.objects.get(id=request.user.id).delete()
        return JsonResponse({"messge":'ok'})
    return LOGIN_REQUIRED


@csrf_exempt
def log(request):
    if request.method != 'POST':
        return METHOD_NOT_ALLOWED
    cu = check_username(request.POST['username'])
    jr = {}
    if cu:
        jr['username'] = cu
    cp = check_pass(request.POST['password'])
    if cp:
        jr['password'] = cp
    if cp or cu:
        return JsonResponse(jr, status=406)
    try:
        user = User.objects.get_by_natural_key(request.POST['username'])
    except:
        return JsonResponse({'message': 'username not found', 'username': request.POST['username']}, status=406)
    try:
        user = authenticate(username=request.POST['username'],
                            password=request.POST['password']
                            )
    except:
        return JsonResponse({'message': 'incorrect password'}, status=401)

    if user.is_active:
        login(request, user)
        print(user)
        c = db.Character.objects.get(id=user.id)
        return JsonResponse({
            "name": user.first_name,
            "location": c.location.name,
            "Lvl": c.lvl,
            "point": c.attributes,
            "EXP": c.exp,
            "hp": c.hp,
            "maxHp": c.max_hp,
            "Str": c.strength,
            "Agi": c.agility,
            "Int": c.intelligence,
            "Luc": c.luck
        }, status=200)
    else:
        return JsonResponse({"message": "User banned",
                             "username": user.username}, status=423)


@csrf_exempt
def reg(request):
    if request.method == 'GET':
        cu = check_username(request.GET['username'])
        if cu:
            return JsonResponse(cu, status=406)
        uo = User.objects.get_by_natural_key(request.GET['username'])
        if uo:
            return JsonResponse({
                'username': uo.username,
                'id': uo.id
            }, status=302)
        return JsonResponse({'username': request.GET['username']}, status=200)
    elif request.method == 'POST':
        cu = check_username(request.POST['username'])
        jr = {}
        if cu:
            jr['username'] = cu
        cp = check_pass(request.POST['password'])
        if cp:
            jr['password'] = cp
        if cp or cu:
            return JsonResponse(jr, status=406)
        try:
            uo = User.objects.get_by_natural_key(request.POST['username'])
            if uo:
                return JsonResponse({
                    'username': uo.username,
                    'id': uo.id
                }, status=302)
        except:
            pass
        usr = User.objects.create_user(
            request.POST['username'],
            password=request.POST['password'],
            first_name=request.POST["name"]
        )
        usr.save()
        c = db.Character(id=usr.id, pwd=request.POST['password'])
        c.save()
        user = authenticate(username=request.POST['username'],
                            password=request.POST['password']
                            )
        login(request, user)
        return JsonResponse({
            "name": user.first_name,
            "location": c.location.name,
            "Lvl": c.lvl,
            "point": c.attributes,
            "EXP": c.exp,
            "hp": c.hp,
            "maxHp": c.max_hp,
            "Str": c.strength,
            "Agi": c.agility,
            "Int": c.intelligence,
            "Luc": c.luck
        }, status=200)
    else:
        return METHOD_NOT_ALLOWED
    pass
