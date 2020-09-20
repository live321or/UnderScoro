from ..celery import app
from . import models as db

from datetime import datetime as d, timedelta as td
import logging

chars = {}


@app.task
def get_char(character_id):
    logging.debug(chars)
    for ch in chars.keys():
        if ch.id == character_id:
            chars[ch] = d.now()
            return ch
    c = db.Character.objects.get(id=character_id)
    chars[c] = d.now()
    return c
    pass


@app.task
def move_character(character_id, location_id):
    c = get_char(character_id)
    c.location = db.Location.objects.get(id=location_id)
    c.save()
    pass


@app.task
def logout():
    logging.info('Removing unused characters starting...')
    for ch in chars.keys():
        if chars[ch] - d.now() > td(minutes=10):
            logging.debug('Removing character with id='+ch.pk)
            chars.pop(ch).save()
    logging.info('Removing unused characters ended')


@app.task
def count_battles():
    pass


@app.task
def test(arg):
    logging.debug('TEST!!!')
