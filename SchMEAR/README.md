#SchMEAR is a Web Server Project creator for: _S_pring, _M_aven, _E_clipse, google _A_pp engine, and _R_.e.s.t.

This 'guide' is for your benefit only for now, is not complete, but might help you out a lot for now, I plan on improving this dramatically here soon. 

To use this project creator you will need these tools:

*   JDK 7 (OpenJDK appears to work fine)

*   Maven 3.1

*   Eclipse (This is actually Optional, but the pom file that will be created will allow easy import of the project into Eclipse if it has the maven plugin.)

  
I have a single archetype created that will provide you everything you will need, including: 
*   pom.xml 
*   folder structure
*   library-dependencies
*   example code

To Install this maven archetype to your local repository (because I haven't yet published this to a public repository), You need to go to the directory **archetypes/base-rest/** and run:

    mvn install

This will allow you to be able to make use of the SchMEAR-base-rest archetype.

This will allow you to run the following command where you wish your new project to be created. You need to edit the command to include the proper groupID (CompanyName/PackageName), and artifactID (ProjectName):


```
    mvn archetype:generate                      \
      -DarchetypeGroupId=edu.vu.isis            \
      -DarchetypeArtifactId=schmear-base-rest   \
      -DarchetypeVersion=1.0-SNAPSHOT           \
      -DinteractiveMode=false                   \
      -DgroupId=<com.mycompany.app>             \
      -DartifactId=<my-app>
``` 



and paste it into your linux machine's terminal (the '\' makes this all a single line command) and run it. You will now have a working starter project.

The starter project will have a single Spring controller, domain object, and a repository. If you start the Google App Engine dev-server the following link will allow you to view the REST information it displays

[http://localhost:8080/v1/stories/](http://localhost:8080/v1/stories/)

----

Once your customized new project is created, These commands will be available to you when in the project's root directory with the pom file.
Maven is the build tool that does all the compilation, etc. for you.

To Clean all local working directories:

    mvn clean

To Build the project, including running all Unit Tests:

    mvn package

Building will run all the tests, but if you want to manually run tests (Will run all Unit Tests, non-'*IntegrationTest.java' tests):

    mvn test

Building and running all the IntegrationTests (This is not yet implemented, so is considered experimental/beta)

    mvn integration-test

To test the app on a local webserver instance:

    mvn appengine:devserver

To test the app on a local webserver instance: (and run in the background, non-blocking):

    mvn appengine:devserver_start

To stop a background running version of the local webserver:

    mvn appengine:devserver_stop
    
To update the project to the Google App Engine servers(must be authorized developer, and follow steps to authorize the 'client'):

    mvn appengine:update

What 'Schmear' means: a number of related things, ideas, etc., resulting in a unified appearance, attitude, plan, or the like (usually used in the phrase the whole schmear). From dictionary.reference.com
