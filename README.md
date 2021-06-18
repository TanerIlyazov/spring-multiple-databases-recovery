Mock project with branch reproducing an unexpected(hopefully only for me) behaviour by spring boot and Hikari connection pools.
Seems like recovering the connection to an external datasource is not working as spring's documentation implies when
- not using AutoDatasourceConfiguration
- or having more than 1 datasource

There are two branches in this project:
- `master`: instructions and code to reproduce failing recovery
- `single-datasource`: instructions and code with successfully recovering datasource connection

Both branches use the same datasource and jpa configuration.

---

Running the application with the started mysql container will populate the User and Product tables with pseudo-random values and start querying them every 30s.
If you stop the container with `docker stop single-mysql-db`, the connection will be lost and you'd start getting *Connection Refused*, starting the container again with `docker start single-mysql-db` should result in a revived connection and the select statements will return results as normal.

Start mysql database with docker
```
docker run --name single-mysql-db -p 3307:3306 -e MYSQL_DATABASE=SingleDB -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_USER=single_admin -e MYSQL_PASSWORD=single_password -d percona:5.7
```

In case you're using an ARM based machine 
```
docker run --name single-mysql-db --platform linux/amd64 -p 3307:3306 -e MYSQL_DATABASE=SingleDB -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_USER=single_admin -e MYSQL_PASSWORD=single_password -d percona:5.7
```