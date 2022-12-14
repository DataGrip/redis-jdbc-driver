# Redis JDBC Driver

[![CI](https://github.com/datagrip/redis-jdbc-driver/workflows/CI/badge.svg?branch=main)](https://github.com/datagrip/redis-jdbc-driver/actions?query=workflow%3ACI+branch%3Amain)

Type 4 JDBC driver based on [Jedis](https://github.com/redis/jedis) that allows Java programs to connect to a Redis database.

This driver is embedded into [DataGrip](https://www.jetbrains.com/datagrip/).

## License

[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](./LICENSE)

## Get The Driver

### Download

You can download the precompiled driver (jar) on the [releases page](https://github.com/DataGrip/redis-jdbc-driver/releases).

### Build

```
# Linux, MacOs
./gradlew jar

# Windows
gradlew.bat jar
```

You will find driver jar in ```build/libs```

## Connectivity

| Server              | Status              |
| ------------------- | ------------------- |
| Redis Standalone    | ***Supported***     |
| Redis Cluster       | *Not supported yet* |
| Redis Sentinel      | *Not supported yet* |

Default host and port: ```127.0.0.1:6379```

### URL templates

#### Redis Standalone
```
jdbc:redis://[[<user>:]<password>@][<host>[:<port>]][/<database>][?<property1>=<value>&<property2>=<value>&...]
```

### Properties

| Property              | Type    | Default | Description                         |
| --------------------- | ------- | ------- | ----------------------------------- |
| user                  | String  | null    |                                     |
| password              | String  | null    |                                     |
| database              | Integer | 0       |                                     |
| connectionTimeout     | Integer | 2000    | Connection timeout in milliseconds. |
| socketTimeout         | Integer | 2000    | Socket timeout in milliseconds.     |
| blockingSocketTimeout | Integer | 0       | Socket timeout (in milliseconds) to use during blocking operation. Default is '0', which means to block forever. |
| clientName            | String  | null    |                                     |


## Commands Execution


| Commands        | Status              |
| --------------- | ------------------- |
| Native          | ***Supported***     |
| RedisBloom      | *Not supported yet* |
| RediSearch      | *Not supported yet* |
| RedisGraph      | *Not supported yet* |
| RedisJSON       | *Not supported yet* |
| RedisTimeSeries | *Not supported yet* |

## Classes

| Interface                       | Class                           | Comment                                                                             |
| ------------------------------- | ------------------------------- | ----------------------------------------------------------------------------------- |
|```java.sql.Driver```            |```jdbc.RedisDriver```           |                                                                                     |
|```java.sql.Connection```        |```jdbc.RedisConnection```       |                                                                                     |
|```java.sql.Statement```         |```jdbc.RedisStatement```        |                                                                                     |
|```java.sql.PreparedStatement``` |```jdbc.RedisPreparedStatement```| **Dummy implementation**: it is equivalent to ```jdbc.RedisStatement```.            |
|```java.sql.DatabaseMetaData```  |```jdbc.RedisDatabaseMetaData``` | **Minimal implementation**: it does not contain information about database objects. |
|```java.sql.ResultSet```         |```jdbc.resultset.RedisResultSetBase```|                                                                               |
|```java.sql.ResultSetMetaData``` |```jdbc.resultset.RedisResultSetMetaData```| **Partial implementation**: it contains only information about columns.   |

## References

* [DataGrip 2022.3](https://www.jetbrains.com/datagrip/whatsnew/2022-3/): Redis support
  +  [DataGrip 2022.3 EAP 2](https://blog.jetbrains.com/datagrip/2022/11/02/datagrip-2022-3-eap-2-redis-support/): Redis support (in details)
