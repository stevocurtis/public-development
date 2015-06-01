from setuptools import setup

setup(

    # Do not use underscores in the plugin name.
    name='cloudify-generic',

    version='0.1',
    author='stevocurtis',
    author_email='stevo.curtis@gmail.com',
    description='cloudify-generic',

    # This must correspond to the actual packages in the plugin.
    packages=['test'],

    license='LICENSE',
    zip_safe=False,
    install_requires=[],
    test_requires=[
        "nose"
    ]
)
