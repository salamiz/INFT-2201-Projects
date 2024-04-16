## Design Decisions

This project adheres to the guidelines for Java Web Enterprise dynamic application implementing authentication and authorization functionalitties. It leverages several key technologies and practices:

### Framework & Server
- **Jakarta EE** with **Gradle** for build management.
- Deployed on **Tomcat Server version 10.1.19**.

### Development Practices
- Incorporates a **Gradle CI pipeline** to ensure continuous integration of updates, following best practices in software development.

### User Interface
- Utilizes additional **JavaScript** and **CSS** to enhance aesthetics and functionality through client-side logic and validations.

### Database Management
- Schema design with stringent validation rules, requiring all fields and unique constraints on username and email fields. The username serves as the primary key.

### Security
- Multi-faceted security approach including:
    - Client-side validation of user inputs.
    - Connection pooling to manage database connections efficiently.
    - Secure storage of connection credentials using environment variables.
    - Protection against SQL injection through prepared statements and parameterized queries.
    - Authentication and authorization mechanisms to safeguard user interactions and data.

## Dependencies

The project relies on the following dependencies for robust database interaction, secure password management, and efficient connection pooling:
- **PostgreSQL JDBC Driver**: For database connectivity.
- **HikariCP**: A high-performance JDBC connection pool.
- **JBCrypt**: For secure hashing of passwords.

These are specified in the `build.gradle` file for easy management.

## Setup Instructions

To set up the application for local development, please follow these steps:

1. **Java JDK Setup**: Ensure Java JDK 21 is installed and its bin directory is added to your system's environment variables.
2. **Tomcat Server**: Install Tomcat Apache Server 10.1.19 and add its path to your system environment variables for easy server management.
3. **IDE Configuration**: Open the project using Intellij IDEA Ultimate Edition, recommended for its support for Java EE and Gradle projects.
4. **Database Connection**: Add your PostgreSQL JDBC jar file to the project's dependencies. Configure the database credentials in the Tomcat Server's environment variables, matching the variable names used in the DatabaseUtility servlet.
5. **Database Setup**: Execute the provided SQL query to create a users table in your PostgreSQL database.
6. **Run Configuration**: Attach the Tomcat Server with the local configuration to your project's run configurations. Include database connection credentials in the startup/connection setup.

Once these steps are completed, the application is ready for launch and use for development or demonstration purposes.
