Cyc Model Archetype Project
===========================

Provides a skeletal application for Cyc Model Generator projects.


Installation
------------

    mvn install archetype:update-local-catalog


Building applications
---------------------

With this archetype installed, do the following:

    mvn archetype:generate                               \
      -DarchetypeGroupId=com.cyc.model.maven             \
      -DarchetypeArtifactId=cyc-model-archetype-project  \
      -DarchetypeVersion=1.0.0-alpha-preview-1           \
      -DgroupId=<my.groupid>                             \
      -DartifactId=<my-artifactId>                       \
      -Dversion=0.1.0-SNAPSHOT 


