#!/bin/bash

uwsgi --http-socket :8080 --wsgi-file main.py --callable app --master --processes 1 --threads 2