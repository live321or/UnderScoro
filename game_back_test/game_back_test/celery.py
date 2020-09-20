import os
from celery import Celery
from django.conf import settings
import logging

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'game_back_test.settings')

app = Celery('game_back_test')
app.config_from_object('django.conf:settings', namespace='CELERY')

# Load task modules from all registered Django app configs.
# app.autodiscover_tasks()

