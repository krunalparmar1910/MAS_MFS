# MFS Datastore Service
## Prerequisites
1. Docker and Docker Desktop (only for Keycloak and integration tests, can be skipped if Keycloak is being run manually)
2. Keycloak (only if Docker is not available)
3. Java 17
4. IntelliJ IDE (recommended)
5. Visual Studio Code (recommended)
6. Node.JS v18 (minimum)
7. Yarn v1.12 (minimum)
8. Microsoft SQL Server 2008 R2
9. SQL Server Management Studio

## Starting the Backend Server
1. Start the keycloak server. Use command `docker-compose up -d` from the mas-docker folder. This will start up the keycloak server. Use command `docker-compose down` to shut down the server. Use command `docker-compose down -v` to shut down and destory the volumes.
2. Keycloak can also be started in a standalone manner without using docker. Download Keycloak and follow the steps on their documentation to start keycloak. Dev-mode can also be used to start locally.
3. If running for the first time, then create the database `mas_datastore` in SQL Server. Schemas will also be needed. Use the following commands to create the database, user and required schemas:
   ```
   CREATE DATABASE mas_datastore;

   USE mas_datastore;
   CREATE LOGIN mas_datastore_db_admin WITH PASSWORD = <set_password>, DEFAULT_DATABASE = mas_datastore;

   USE mas_datastore;
   CREATE USER mas_datastore_db_admin from LOGIN mas_datastore_db_admin;

   USE mas_datastore;
   EXEC sp_addrolemember 'db_owner', 'mas_datastore_db_admin';

   USE mas_datastore;
   EXEC ('CREATE SCHEMA mas_datastore_gst AUTHORIZATION mas_datastore_db_admin');

   USE mas_datastore;
   EXEC ('CREATE SCHEMA mas_datastore_itr AUTHORIZATION mas_datastore_db_admin');

   USE mas_datastore;
   EXEC ('CREATE SCHEMA mas_datastore_cibil AUTHORIZATION mas_datastore_db_admin');

   USE mas_datastore;
   EXEC ('CREATE SCHEMA mas_datastore_perfios AUTHORIZATION mas_datastore_db_admin');

   USE mas_datastore;
   ALTER USER mas_datastore_db_admin WITH DEFAULT_SCHEMA = mas_datastore_gst;
   ```
4. Set the required credentials manually in the `mas_datastore.mas_datastore_gst.api_info_credentials` table. These will be required to connect with the third party services.
5. Run `mvn clean install -DskipTests=true` from command line if running from command prompt or bash. If running from IntelliJ then run the goals clean and install for the entire project from the Maven window with skip tests option selected.
6. Once all dependencies are installed, then create a new Application configuration from IntelliJ to run. Select the class `com.pf.mas.MasFinancialServicesApplication` to run from module `mas-server`.
7. Specify the environment variables `DATASOURCE_USERNAME` as `mas_datastore_db_admin` and `DATASOURCE_PASSWORD` as the password you have set for this user. Following environment variables are supported:
   - DATASOURCE_USERNAME (required)
   - DATASOURCE_PASSWORD (required)
   - PERFIOS_BASE_URL (not mandatory, will be read from database first, if not present in database then will be read from environment variable)
   - PERFIOS_WEBHOOK_URL (not mandatory, will be read from database first, if not present in database then will be read from environment variable)
   - KARZA_ITR_BASE_URL (not mandatory, will be read from database first, if not present in database then will be read from environment variable)
   - KARZA_API_VERSION (not mandatory, will be read from database first, if not present in database then will be read from environment variable)
   - KARZA_ITR_KEY (not mandatory, will be read from database first, if not present in database then will be read from environment variable)
   - CORPOSITORY_BASE_URL (not mandatory, will be read from database first, if not present in database then will be read from environment variable)
8. Now run the application. Look for the line `Started <application_name> in x.y seconds` as this line marks the successful startup of the server.
9. When running for the first time, liquibase migrations will be run. For the subsequent times, only the newly added migrations will run.
10. To clean the database, drop the database entirely and run the commands from step 3 again. Then restart the server to get an empty database. This might be needed if breaking changes are introduced in liquibase migrations such as updating an already run migration.
11. Server is by default accessible at https://localhost:9090.

## Starting the UI Server
1. Open the `ui-service` folder in Visual Studio Code or terminal.
2. If running for the first time, run the command `yarn`. This will download and install all the dependencies for the UI. This might take some time depending on the network speed.
3. Run the command `yarn start` to start the UI. This is by default accessible at `http://localhost:3000`.

## Build and Deploy
1. Use the command `mvn clean install -DskipTests=true` or run clean and install from IntelliJ for the entire project. This will create a jar file in the target folder of mas-server module. This JAR is used to deploy and start the server on the UAT or the Production instance.
2. UI files are served from the backend itself. Hence you can also access the UI at `https://localhost:9090` when deploying locally and use the same URL for accessing on production.

## Troubleshooting
1. Sometimes you may run into liquibase issues of changed hashes. This is because a migration which has already run on the database was modified in the liquibase file. It is recommended to add a new changeset rather than modifying existing ones. Worst case, you may need to drop the database locally and create it again. This is not recommended as the entire data will be removed as well which cannot be the case on a production instance.
2. Sometimes while accessing the UI from `https://localhost:9090` all requests will fail. In this scenario, please check whether keycloak is started or not. Check the log folder for any errors. Check the network tab, if the browser is rejecting any requests to the backend with the error `NET::ERR_CERT_AUTHORITY_INVALID` then copy that request and open it in a new tab. Accept the security message by proceeding. This step is required to be done manually sometimes if the Website certificate has not been imported into Chrome correctly.
3. Sometimes you may get a TLS error while connecting to the database. This might be because the SQL Server version only supports TLS1.0 and Java 17 by default does not use older protocols as they are outdated and insecure. To bypass this issue, remove TLS1.0 from the jdk.tls.disabledAlgorithms property from the <JDK_HOME>\conf\security\java.security file. This is not recommended, it is highly recommended to use latest security protocols.
