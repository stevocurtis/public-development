__author__ = '700608667'

from cloudify.workflows import ctx
from cloudify.decorators import workflow

@workflow
def addhierarchyruntimeproperties(**kwargs):
    ctx.logger.info("*** executing addhierarchyruntimeproperties ***")

    for node in ctx.nodes:
        ctx.logger.info("*** found node {} ***".format(node))

    ctx.logger.info("*** completed addhierarchyruntimeproperties ***")