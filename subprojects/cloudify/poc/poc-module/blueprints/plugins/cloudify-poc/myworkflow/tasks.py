__author__ = '700608667'

from cloudify.workflows import ctx
from cloudify.decorators import workflow

@workflow
def simpleworkflow(**kwargs):
    ctx.logger.info("*** executing simpleworkflow ***")

    for node in ctx.nodes:
        if node.id == 'myLogger':
            for instance in node.instances:
                instance.execute_operation("non_functionals.logging")

    ctx.logger.info("*** completed simpleworkflow ***")