__author__ = '700608667'

import unittest
import os
import re

class TestTasks(unittest.TestCase):

    blueprint_root_dir = '..'
    blueprint_file_pattern = '.*blueprint.*yaml'

    def step(self, ext, dirname, names):
        for name in names:
            if (re.match(self.blueprint_file_pattern, name)):
                print('found blueprint file {}'.format(name))

    def validate_all_blueprints(self):
        for root, dirs, files in os.walk(self.blueprint_root_dir):
            for file in files:
                if re.match(self.blueprint_file_pattern, file):
                    print('found blueprint file {}'.format(file))
                    #TODO add real validation here

    def test_temp(self):
        self.validate_all_blueprints()