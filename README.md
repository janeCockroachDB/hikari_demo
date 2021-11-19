# Demo for connecting CockroachDB with Hikari

_This project is for personal usage._
## System Configuration
- macOS 11.6.1
- Arch x86_64
- Apache Maven 3.8.3
- Java 17.0.1
- [CockroachDB](https://github.com/cockroachdb/cockroach) (local beta ver > 21.2.0)

## Package Dependency
(Also see pom.xml)
- [HikariCP](https://github.com/brettwooldridge/HikariCP) 5.0.0
- mysql-connector-java 8.0.25
- slf4j-simple 1.7.32
- postgresql 42.3.1

## How to run

0. Install and start CockroachDB single node server with default configs in the insecure mode:
```bash
cockroach start-single-node --advertise-addr 'localhost' --insecure
```

1. Load the dataset with provided sql file:
```bash
cat src/main/resources/dbinit.sql | cockroach sql --url="postgresql://root@Janes-MacBook-Pro.local:26257?sslmode=disable"
```

2. Compile this demo project with ``mvn``:
```bash
mvn clean package 
```
  And if it runs successfully, it will create a ``jar`` file with path ``target/demoConnect-1.0-SNAPSHOT.jar``.

3. Make sure the CockroachDB server is still running, and run this java program with
```bash
java -cp target/demoConnect-1.0-SNAPSHOT.jar jane.demo.connect.App
```
