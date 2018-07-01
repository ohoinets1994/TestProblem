#Test Problem<h6>
To build project was used **Maven** tool which should be installed on PC. 
To build project using Command Line please download *Maven* tool by this [**LINK.**](https://maven.apache.org/download.cgi)
##Maven<h6>
JSON version:
```xml
<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20180130</version>
</dependency>
```
JUNIT version:
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```
##Manual<h6>
**Step 1:** Clone a project from the repository;<br>

**Step 2:** Open a command line in the project root directory;<br>

**Step 3:** Compile the project using bla command:
```ftl
mvn compile
```
**Step 4:** To launch an app in the command line the following command should be used:
```ftl
mvn exec:java -D"exec.mainClass"="com.hoinets.application.Starter"
```