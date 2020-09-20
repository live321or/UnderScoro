from django.db import models
from random import uniform, randint
from math import log, floor
import logging

TEXT_LENGTH = 4096
logger = logging.getLogger(__name__)

class Location(models.Model):
    name = models.TextField(max_length=30)
    description = models.TextField(max_length=TEXT_LENGTH)
    pass


class LocationPath(models.Model):
    from_location = models.ForeignKey(Location, models.CASCADE, related_name='from_loc')
    to_location = models.ForeignKey(Location, models.CASCADE, related_name='to_loc')
    seconds = models.PositiveIntegerField(default=10)
    pass


class Character(models.Model):
    pwd = models.CharField(max_length=16)
    _exp = models.FloatField(default=0.0)
    _lvl = models.PositiveIntegerField(default=1)

    strength = models.PositiveIntegerField(default=1)
    agility = models.PositiveIntegerField(default=1)
    intelligence = models.PositiveIntegerField(default=1)
    luck = models.PositiveIntegerField(default=1)

    attributes = models.PositiveIntegerField(default=9)
    skill_points = models.PositiveIntegerField(default=0)

    location = models.ForeignKey(Location, models.SET(1), default=1)

    quests = models.ManyToManyField(to="Quest", related_name='characters_on_quest', through='CharacterQuests')

    @property
    def lvl(self):
        return self._lvl

    @lvl.setter
    def lvl(self, value):
        logger.debug("level adding")
        self._lvl = value
        self.attributes += floor(log(self.lvl) + 1)

    @property
    def exp(self):
        return self._exp

    @exp.setter
    def exp(self, value):
        logger.debug("_exp = "+str(self._exp))
        self._exp = value
        logger.debug("_exp after adding = " +str( self._exp))
        lvl_up_exp = 30 * 4 ** (self.lvl - 1)
        logger.debug("lvl_up_exp = "+str(lvl_up_exp))
        if self._exp >= lvl_up_exp:
            self._exp -= lvl_up_exp
            self.lvl += 1

    @property
    def max_hp(self):
        return (1 + self.lvl / 10) * (20 + self.strength + self.strength // 10 * 4)

    @property
    def max_weight(self):
        return 100

    @property
    def hp(self):
        if '_Character__hp' not in dir(self):
            self.__hp = self.max_hp
        return self.__hp

    @property
    def dmg(self):
        return (1 + self.lvl / 10) * (5 + self.strength // 2 + self.strength // 10 * 2)

    @property
    def regen(self):
        return 0.4 + self.strength / 15 + self.lvl / 10

    @property
    def dodge(self):
        return (20 * self.agility ** 2)/(self.agility ** 2 + 30 * self.agility) / 100

    @property
    def accuracy(self):
        return 0.6 + (40 * self.agility ** 2) / (self.agility ** 2 + 40 * self.agility) / 100

    @property
    def crit_pow(self):
        return 1.1 + (190 * self.agility ** 2) / (self.agility ** 2 + 100 * self.agility) / 100

    @property
    def sk_pow(self):
        return (1 + self.lvl / 10) * self.intelligence // 2

    @property
    def max_mp(self):
        return (1 + self.lvl / 10) * (10 + self.intelligence * 2 + self.lvl // 10 * 20)

    @property
    def mp(self):
        if '_Character__mp' not in dir(self):
            self.__mp = self.max_mp
        return self.__mp

    @property
    def mr(self):
        return (1 + self.lvl / 10) * (0.2 + self.intelligence * 0.03 + self.lvl * 0.05)

    @property
    def crit_ch(self):
        return 10 + (90 * self.luck ** 2) / (self.luck ** 2 + 35 * self.luck) / 100

    @property
    def sc_ch(self):
        return 60 + (40 * self.luck ** 2) / (self.luck ** 2 + 35 * self.luck) / 100

    @property
    def drop_chance(self):
        return 1

    pass


class VariableValue(models.Model):
    min_value = models.FloatField()
    max_value = models.FloatField()
    pass


class Effect(models.Model):
    EFFECT_TYPES = (
        ('ps', 'poison'),
        ('bl', 'bleed'),
        ('id', 'instant_damage'),
        ('bf', 'baf'),
        ('hl', 'heal'),
        ('mr', 'mana_restore'),
        ('o', 'other')
    )
    TARGETS = (
        ('s', 'self'),
        ('e', 'enemy'),
        ('f', 'friend'),
        ('fp', 'friends_party'),
        ('ep', 'enemy_party'),
        ('fr', 'friends_row'),
        ('er', 'enemy_row'),
        ('fc', 'friends_column'),
        ('ec', 'enemy_column'),
        ('a', 'all_field')
    )
    name = models.CharField(max_length=30)
    description = models.TextField(max_length=TEXT_LENGTH)
    chance_work = models.FloatField(default=1)
    chance_apply = models.FloatField(default=1)
    value = models.ForeignKey(VariableValue, models.CASCADE)
    target = models.CharField(max_length=2, choices=TARGETS)
    e_type = models.CharField(max_length=2, choices=EFFECT_TYPES)

    # @classmethod
    # def choseEffect(cls, ):
    pass


class Requirement(models.Model):
    STRENGTH = ('s', "strength")
    AGILITY = ('a', "agility")
    INTEL = ('i', 'intelligence')
    LUCK = ('l', 'luck')
    LVL = ('e', 'lvl')
    REQ_ATR = (
        STRENGTH,
        AGILITY,
        INTEL,
        LUCK,
        LVL
    )
    required_atr = models.CharField(choices=REQ_ATR, max_length=1)
    required_val = models.ForeignKey(VariableValue, models.CASCADE)
    pass


class Item(models.Model):
    name = models.TextField(max_length=30)
    description = models.TextField(max_length=TEXT_LENGTH)

    requirements = models.ManyToManyField(Requirement)

    ITEM_TYPES = (
        ("ARMOR", (
            ('ba', "body_armor"),
            ('la', 'leg_armor'),
            ('bo', 'boots'),
            ('hm', 'helmet'),
            ('rg', 'ring'),
            ('ch', 'charm')
        )),
        ("WEAPON", (
            ('sw', 'sword'),
            ('bw', 'bow'),
            ('ts', 'two_hand_sw'),
            ('st', 'staff')
        )),
        ('SECOND', (
            ('sh', 'shield'),
            ('or', 'orb'),
            ('dg', 'dagger'),
            ('ar', 'arrow')
        )),
        ('us', 'usable'),
        ('ot', 'other'),
        ('rn', 'rune')
    )
    item_type = models.CharField(max_length=2, choices=ITEM_TYPES, default='ot')
    effects = models.ManyToManyField(Effect, related_name='effects')
    possible_effects = models.ManyToManyField(Effect, related_name='pos_effects')

    mass = models.FloatField(default=1)
    main_effect_value = models.ForeignKey(VariableValue, models.SET(1))
    rune_placeholders = models.PositiveSmallIntegerField(default=0)

    # def generate(self, owner, saleable=True):
    #     if self.item_type in self.ITEM_TYPES[0]:
    #         arm = VariableValue.objects.get(id=self.main_effect_value)
    #         res = ArmorInstance(
    #             arm=uniform(arm.min_value, arm.max_value),
    #             item_id=self.id,
    #             owner_id=owner,
    #             pos_effect_num=randint(0, Effect.pos_effects_set.count(id=self.id))
    #         )

    pass


class ItemInstance(models.Model):
    item = models.ForeignKey(Item, models.SET(1))
    owner = models.ForeignKey(Character, models.CASCADE)
    pos_effect_num = models.PositiveSmallIntegerField()
    INV_STATES = (
        ('b', 'onBody'),
        ('c', 'chest'),
        ('i', 'inventory')
    )
    inventory = models.CharField(max_length=1, choices=INV_STATES)
    saleable = models.BooleanField(default=True)
    attached_runes = models.ManyToManyField("self", related_name="runes")
    pass


class WeaponInstance(ItemInstance):
    dmg = models.ForeignKey(VariableValue, models.SET(1))
    pass


class ArmorInstance(ItemInstance):
    arm = models.IntegerField()
    pass


class Spell(models.Model):
    name = models.CharField(max_length=30)
    description = models.TextField(max_length=TEXT_LENGTH)

    distance = models.IntegerField(choices=((1, 'one'), (2, 'two'), (3, 'three')), default=1)

    requirements = models.ManyToManyField(Requirement)
    effects = models.ManyToManyField(Effect)

    mob_only = models.BooleanField(default=False)

    pass


class CharactersSpell(models.Model):
    character = models.ForeignKey(Character, models.CASCADE, related_name='spells')
    spell = models.ForeignKey(Spell, models.CASCADE)

    equipped = models.BooleanField(default=False)

    pass


class Mob(models.Model):
    name = models.CharField(max_length=30)
    description = models.TextField(max_length=TEXT_LENGTH)

    lvl = models.PositiveIntegerField()

    strength = models.PositiveIntegerField(default=1)
    agility = models.PositiveIntegerField(default=1)
    intelligence = models.PositiveIntegerField(default=1)
    luck = models.PositiveIntegerField(default=1)

    spells = models.ManyToManyField(Spell)

    pass


class MobDrop(models.Model):
    mob = models.ForeignKey(Mob, models.CASCADE)
    item = models.ForeignKey(Item, models.CASCADE)

    chance = models.FloatField()
    pass


class MobsOnLocation(models.Model):
    location = models.ForeignKey(Location, models.CASCADE)
    mob = models.ForeignKey(Mob, models.CASCADE)

    percent = models.FloatField()
    aggressive_percent = models.FloatField(default=0.0)
    pass


class QuestLine(models.Model):
    name = models.CharField(max_length=40)
    description = models.TextField(max_length=TEXT_LENGTH)
    pass


class Quest(models.Model):
    TRIGGERS = (
        ('k', 'kill'),
        ('v', 'visit'),
        ('o', 'obtain'),
        ('d', 'deliver')
    )
    line = models.ForeignKey(QuestLine, models.CASCADE)
    inline = models.PositiveIntegerField()

    name = models.CharField(max_length=30)
    description = models.TextField(max_length=TEXT_LENGTH)

    trigger = models.CharField(max_length=1, choices=TRIGGERS)
    amount = models.PositiveIntegerField(default=1)
    foreign = models.PositiveIntegerField()

    pass


class CharacterQuests(models.Model):
    character = models.ForeignKey(Character, models.CASCADE)
    quest = models.ForeignKey(Quest, models.CASCADE)

    kill_count = models.PositiveIntegerField(null=True, default=None)

    # def check_quest(self, **kwargs):
    #
    #     def go_next():
    #         self.quest = Quest.objects.get(
    #             line=self.quest.line,
    #             inline=self.quest.inline + 1
    #         )
    #         self.kill_count = None
    #
    #     if self.quest.trigger == 'k':
    #         if self.kill_count is None:
    #             self.kill_count = 0
    #             self.save()
    #         if 'killed' in kwargs.keys() and kwargs['killed'] == self.quest.foreign:
    #             self.kill_count += 1
    #             self.save()
    #         if self.kill_count >= self.quest.amount:
    #             go_next()
    #             return True
    #     elif self.quest.trigger == 'v':
    #         if 'location' in kwargs.keys() and kwargs['location'] == self.quest.foreign:
    #             go_next()
    #             return True
    #     elif self.quest.trigger == 'o':
    #         items = ItemInstance.objects.filter(owner_id=self.character.id, item_id=self.quest.foreign
    #                                             ).filter(inventory__in=('b', 'i'))
    #         if items.count() >= self.quest.amount:
    #             go_next()
    #             return True
    #     elif self.quest.trigger == 'd':
    #         if 'location' in kwargs.keys() and kwargs['location'] == self.quest.foreign:
    #
    #     pass
    pass

