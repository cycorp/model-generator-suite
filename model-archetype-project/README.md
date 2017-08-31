Cyc Model Archetype Project
===========================

This archetype provides a skeletal application for 
[Cyc Model Generator](https://github.com/cycorp/model-generator-suite) projects.


Installation
------------

This project depends upon the rest of the Cyc Model Generator Suite. Once that has been installed,
this archetype may be installed with the following:

    mvn install archetype:update-local-catalog


Building an application
-----------------------

Generate a project (substituting in the appropriate values):

    mvn archetype:generate                               \
      -DarchetypeGroupId=com.cyc.model.maven             \
      -DarchetypeArtifactId=cyc-model-archetype-project  \
      -DarchetypeVersion=1.0.0-rc6                       \
      -DgroupId=<my-groupid>                             \
      -DartifactId=<my-artifactId>                       \
      -Dversion=<my-version>

If Maven prompts you for anything, accept the defaults. Then:

    cd <my-artifactId>

... and follow the instructions in the generated project's `README.md`.


Quickstart example
------------------

Once you have the Model Generator Suite installed, do the following from the command line:

    mvn archetype:generate                               \
      -DarchetypeGroupId=com.cyc.model.maven             \
      -DarchetypeArtifactId=cyc-model-archetype-project  \
      -DarchetypeVersion=1.0.0-rc6                       \
      -DgroupId=my.test                                  \
      -DartifactId=test-project                          \
      -Dversion=0.1.0-SNAPSHOT

If Maven prompts you for anything, accept the defaults. Then:

    cd test-project

... and follow the instructions in the generated project's `README.md`.


Contact
-------

For questions about these projects, or for issues with using them, please visit the
[Cyc Dev Center issues page](http://dev.cyc.com/issues/).

