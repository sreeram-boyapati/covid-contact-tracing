# -*- coding:utf-8 -*-

from flask import flash, request, url_for, redirect, render_template
from flask import jsonify
from flask.views import MethodView


class IndexView(MethodView):

    def get(self):
        return jsonify({
            'success': True,
            'message': 'Welcome to covidguard Test Result Server'
        }), 200


class WarmupView(MethodView):

    def get(self):
        return jsonify({
            'success': True,
            'message': 'Warmup Request'
        }), 200
