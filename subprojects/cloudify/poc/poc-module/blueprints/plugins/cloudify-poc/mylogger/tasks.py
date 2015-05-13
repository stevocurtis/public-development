__author__ = '700608667'

from cloudify import ctx
from cloudify.decorators import operation

@operation
def simplelogger(**kwargs):
    ctx.logger.info("*** running simplelogger ***")
    ctx.logger.info("    simplelogger found node with id {} type {} and properties".format(ctx.node.id, ctx.type, ctx.node.properties))
    ctx.logger.info("*** completed simplelogger ***")