##Preparation
Explore the basic concept of (JSON Web Token) and (component testing how to)

##Tasks

- Write appropriate tests to the new backend component. Mock the LoginService where necessary. 
    * Call /home w/o JWT and expect an appropriate HTTP error code. 
    * Call /home w/o feasible JWT and expect an appropriate HTTP error code.
    * Call /home w/ a feasible JWT for user "John Doe" and expect a welcome message including that name. 
    * Call /login w/o login or password and expect an appropriate HTTP error code. 
    * Call /login with providing a JWT and expect an appropriate HTTP error code. 
    * Call /login with "feasible" login and password (the mock accepts it) and expect the matching JWT. 

- Write the backend component to get the tests green. 
- Write contract test(s) to the LoginService and use/write a mock to get them green. 
- Do some refactoring, if necessary.

Use a JVM language (Java or Scala preferred, but no exclusions). 
Use the frameworks, tools and libraries of your choice (e.g. JUnit, Spring, Tomcat, Play, Dropwizard).
Provide the code and build scripts / receipt as well as the compiled code (if applicable). 
If possible provide a Docker container or Vagrant VM running the component.

Have fun!
