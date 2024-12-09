#!/bin/bash

until /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "DROP DATABASE $DATABASE_NAME; CREATE DATABASE $DATABASE_NAME";
  do echo retry && sleep 1;
done;
echo "created database $DATABASE_NAME"

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; CREATE LOGIN $NEW_DB_ADMIN_USERNAME WITH PASSWORD = '$NEW_DB_ADMIN_PASSWORD', DEFAULT_DATABASE = $DATABASE_NAME";
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; CREATE USER $NEW_DB_ADMIN_USERNAME from LOGIN $NEW_DB_ADMIN_USERNAME"
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; ALTER ROLE db_owner ADD MEMBER $NEW_DB_ADMIN_USERNAME;"
echo "created user $NEW_DB_ADMIN_USERNAME"

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; EXEC ('CREATE SCHEMA $GST_SCHEMA_NAME AUTHORIZATION $NEW_DB_ADMIN_USERNAME')"
echo "created schema $GST_SCHEMA_NAME"

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; ALTER USER $NEW_DB_ADMIN_USERNAME WITH DEFAULT_SCHEMA = $GST_SCHEMA_NAME"
echo "default schema changed to $GST_SCHEMA_NAME for user $NEW_DB_ADMIN_USERNAME"

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; EXEC ('CREATE SCHEMA $ITR_SCHEMA_NAME AUTHORIZATION $NEW_DB_ADMIN_USERNAME')"
echo "created schema $ITR_SCHEMA_NAME"

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; EXEC ('CREATE SCHEMA $CIBIL_SCHEMA_NAME AUTHORIZATION $NEW_DB_ADMIN_USERNAME')"
echo "created schema $CIBIL_SCHEMA_NAME"

/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -Q "USE $DATABASE_NAME; EXEC ('CREATE SCHEMA $PERFIOS_SCHEMA_NAME AUTHORIZATION $NEW_DB_ADMIN_USERNAME')"
echo "created schema $PERFIOS_SCHEMA_NAME"

echo "db and schema setup completed successfully"
