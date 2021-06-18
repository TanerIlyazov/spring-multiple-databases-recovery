Mock project with branch reproducing an unexpected(hopefully only for me) behaviour by spring boot and Hikari connection pools.
Seems like recovering the connection to an external datasource is not working as spring's documentation implies when 
- not using AutoDatasourceConfiguration
- or having more than 1 datasource

There are two branches in this project:
- `master`: instructions and code to reproduce failing recovery
- `single-datasource`: instructions and code with successfully recovering datasource connection

Both branches use the same datasource and jpa configuration.

---

Running the application with the started mysql containers will populate the User and Product tables with pseudo-random values and start querying every 30s.
If you stop one of the containers, f.e. with `docker stop user-mysql-db`, the connection will be lost and you'd start getting *Connection Refused*, starting the container again with `docker start user-mysql-db` will not be able to trigger the recovery mechanism of JDBC4 and the connection will be lost until a restart.

Start mysql database with docker
```
docker run --name user-mysql-db -p 3307:3306 -e MYSQL_DATABASE=UserDB -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_USER=user_admin -e MYSQL_PASSWORD=user_password -d percona:5.7
docker run --name product-mysql-db -p 3308:3306 -e MYSQL_DATABASE=ProductDB -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_USER=product_admin -e MYSQL_PASSWORD=product_password -d percona:5.7
```

In case you're using an ARM based machine 
```
docker run --name user-mysql-db --platform linux/amd64 -p 3307:3306 -e MYSQL_DATABASE=UserDB -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_USER=user_admin -e MYSQL_PASSWORD=user_password -d percona:5.7
docker run --name product-mysql-db --platform linux/amd64 -p 3308:3306 -e MYSQL_DATABASE=ProductDB -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_USER=product_admin -e MYSQL_PASSWORD=product_password -d percona:5.7
```