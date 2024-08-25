Task Management System
===============================
## Details
These REST apis serve to manage tasks 

## Bootstrap instructions
To run this server locally,

1. Install Java 17 on your local machine - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html.
2. Install Maven on your local machine - https://maven.apache.org/install.html.
3. Clone the repository using **git clone https://github.com/sivalux/Task_Management_System.git**.
4. Download and install MySQL server and start the MySQL server locally.
5. Execute sql script to generate db and data **resources/db.sql**.
6. Navigate to project root directory and open a terminal.
7. Run **mvn clean package** which will run the unit and integration tests and build the project.
8. Run **java -jar target/task-management-system-0.0.1-SNAPSHOT.jar** to start the application.
9. To test the APIs in swagger navigate to http://localhost:8080/swagger-ui/index.html#/.
10. Alternatively you can use an API client like postman and test the endpoints.



