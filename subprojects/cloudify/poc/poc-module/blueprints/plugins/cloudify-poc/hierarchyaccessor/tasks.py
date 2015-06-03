__author__ = '700608667'

from cloudify.workflows import ctx
from cloudify.decorators import workflow

@workflow
def addhierarchyruntimeproperties(**kwargs):
    ctx.logger.info("*** executing addhierarchyruntimeproperties ***")

    for node in ctx.nodes:
        nodeProperties = node.properties
        ctx.logger.info("*** node with id {} and name {} has properties {}***".format(node.id, node.name, nodeProperties))
        for instance in node.instances:
            nodeInstanceRuntimeProperties = node.runtime_properties
            ctx.logger.info("   *** node instance with {} has properties {}***".format(node, nodeInstanceRuntimeProperties))

    ctx.logger.info("*** completed addhierarchyruntimeproperties ***")