"""game_back_test URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from . import views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('usr/', views.info, name='info'),
    path('log/', views.log, name='login'),
    path('reg/', views.reg, name='register'),
#    path('move/', views.move, name='move character'),
    path('bres/', views.battle_result, name=' battle result'),
    path('attradd/', views.set_abilities, name='updating characters attributes'),
    path('location/', views.location_info, name='location info'),
    path('del/', views.del_all_users, name='deleter'),
]
