# Organizational analysis

The project analyzes company and its organizational structure and identify potential improvements.

**Technology stack**

* JDK 17
* JUnit
* Maven

**Running the app**

Build the organizational-analyzer.jar file using the following command:
mvn package

**Please use the following command to run the application:**

<code>java -jar target/organizational-analyzer.jar filepath</code>

where the <code>filepath</code> is the path to a csv file.

# Using Maven Wrapper:

This project includes support for Maven Wrapper. The Wrapper is an executable Unix shell script used in place of a fully
installed Maven.

To install the project using Maven Wrapper:

**For Unix-based systems:**

bash
<br> <code>./mvnw clean install</code>

**For Windows:**

bash
<br> <code>mvnw.cmd clean install</code>

# Assumption:

The analyzing criteria could be changed.
Implemented a PropertiesLoader class to load configuration properties from an application.properties file.

This approach allows us to configure certain parameters of the organizational analysis without hardcoding them in the code.

By externalizing these properties into a configuration file, we can easily adjust them without needing to recompile the application.
