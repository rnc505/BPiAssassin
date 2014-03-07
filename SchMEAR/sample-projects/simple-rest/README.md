Need JDK 6+ (Read 7+ somewhere, but I need to confirm that)
Need Maven 3.1+

To Clean all local working directories:

    mvn clean

To Build the project, including running all Unit Tests:

    mvn package

Building will run all the tests, but if you want to manually run tests (Will run all Unit Tests, non-'*IntegrationTest.java' tests):

    mvn test

To test the app on a local webserver instance:

    mvn appengine:devserver 

To test the app on a local webserver instance: (and run in the background, non-blocking):

    mvn appengine:devserver_start

To stop a background running version of the local webserver:

    mvn appengine:devserver_stop

To update the project to the Google App Engine servers(must be authorized developer, and follow steps to authorize the 'client'):

    mvn appengine:update

To create a stand-alone executable jar with a copy of tomcat 7 inside of it, and to run it:
    
    mvn clean package tomcat7:exec-war-only && java -jar target/testapp-4-war-exec.jar
    
I have wanted to get the spring-boot plugin working, so integration-testing could be completed with maven, however using that plugin is a mess, And Google App Engine uses a varient of Jetty 6.x, and Spring needs Jetty 9+ for its Integration Tests to run. (or Tomcat, which is what gradle tomcat plugin uses.) Due to those having 3.0+ servlet spec, and netty 6 only have servlet spec 2.5 in it. 

To start up a local server with gradle to run the integration tests:

    ./gradlew tomcatRunWar     (Linux)
    gradlew.bat tomcatRunWar   (Windows)

To run Integration Tests:

    ./gradlew test     (Linux)
    gradlew.bat test   (Windows)

