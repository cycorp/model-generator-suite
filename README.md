Cyc Model Generator Suite
=========================

The Cyc Model Generator Suite provides automated generation of domain-specific Java APIs from terms
in the Cyc KB. It is conceptually similar to, e.g., object-relational mapping (ORM) frameworks like
Hibernate which provide domain-specific APIs for traditional relational databases.


Components
----------

The Cyc Model Generator Suite consists of the following components:

* `model-parent`:            Parent pom for all Cyc Model Generator-related projects.
* `model-generator`:         The Cyc Model Generator, which performs code generation of
   domain-specific Java APIs from terms in the Cyc KB.
* `model-generator-plugin`:  Maven plugin for invoking the Cyc Model Generator.
* `model-archetype-project`: Maven archetype for creating Cyc Model Generator-based projects.


Installation
------------

From the command line:

    mvn clean install -DskipTests=true
    cd model-archetype-project
    mvn install archetype:update-local-catalog


Usage
-----

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

Next, edit the project's `src/main/resources/CycModelDescription.xml`. This file contains some
commented-out configuration examples, along with some explanatory documentation.

Lastly, run the following to generate model representation code:

    mvn clean compile

This will pop up a GUI panel asking for a Cyc server address. If you are running in a headless
environment, or otherwise wish to specify the server at the command line, use the following instead:

    mvn clean compile -Dcyc.session.server=[HOST_NAME]:[BASE_PORT]

For example:

    mvn clean compile -Dcyc.session.server=localhost:3600

View the generated project's `pom.xml` for additional configuration settings.


Quickstart example
------------------

Install the Model Generator Suite, following the steps in the _Installation_ section. Then, from the
command line:

    mvn archetype:generate                               \
      -DarchetypeGroupId=com.cyc.model.maven             \
      -DarchetypeArtifactId=cyc-model-archetype-project  \
      -DarchetypeVersion=1.0.0-rc6                       \
      -DgroupId=my.test                                  \
      -DartifactId=test-project                          \
      -Dversion=0.1.0-SNAPSHOT

If Maven prompts you for anything, accept the defaults. Then:

    cd test-project

Then, edit `src/main/resources/CycModelDescription.xml`. Uncomment the example configuration.

Next, edit `src/main/java/my/test/App.java`. Uncomment the example code.

Finally, do the following:

    mvn clean compile -Dcyc.session.server=[HOST_NAME]:[BASE_PORT]
    mvn exec:java -Dexec.mainClass="my.test.App" -Dcyc.session.server=[HOST_NAME]:[BASE_PORT]

For example:

    mvn clean compile -Dcyc.session.server=localhost:3600
    mvn exec:java -Dexec.mainClass="my.test.App" -Dcyc.session.server=localhost:3600

This will build the project and run the example code in the my.test.App class.


Contact
-------

For questions about these projects, or for issues with using them, please visit the
[Cyc Dev Center issues page](http://dev.cyc.com/issues/).
