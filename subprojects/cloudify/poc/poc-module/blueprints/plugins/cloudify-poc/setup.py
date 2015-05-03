from setuptools import setup

setup(

    # Do not use underscores in the plugin name.
    name='cloudify-poc',

    version='0.1',
    author='tamir',
    author_email='tamir@gigaspaces.com',
    description='cloudify-poc',

    # This must correspond to the actual packages in the plugin.
    packages=['mylogger', 'myworkflow'],

    license='LICENSE',
    zip_safe=False,
    install_requires=[
        # Necessary dependency for developing plugins, do not remove!
        "cloudify-plugins-common>=3.1"
    ],
    test_requires=[
        "cloudify-dsl-parser>=3.1"
        "nose"
    ]
)
