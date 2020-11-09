## Welcome to NC TaskManager project

**NC TaskManager** is as ...

### Prerequisites:
- Java 8
- Maven

### Preparing:
It should to specify an email send properties in the `mail.properties` file by `src/main/resources/` path. <br>
For example (Gmail SMTP):
```properties
# SMTP Service Configuration 
mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
# Credentials
username=username
password=password
# Sender
from=username@gmail.com
# Recipients
recipients=custom@custom.com
```

### Build & Run:
First, you have to clone the GitHub repo:
```bash
git clone https://github.com/pyrih/NCTaskManager.git
```
#### Unix:
Execute command `mvn clean package` in terminal and then to run project execute `java -jar target/TaskManager-5.0-jar-with-dependencies.jar`
#### Windows:
Use `build.cmd` script to create an executable JAR with Maven and then `run.cmd` to run the project.

### Contribute:
For any problems, comments, or feedback please create an issue
[here](https://github.com/pyrih/NCTaskManager/issues).

### *Stay productive* :rocket:

