__author__ = '700608667'

from cloudify import ctx
from cloudify.decorators import operation

@operation
def simplelogger(**kwargs):
    print "*** running simplelogger ***"