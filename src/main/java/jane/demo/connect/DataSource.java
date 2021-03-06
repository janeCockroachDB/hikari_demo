package jane.demo.connect;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setDriverClassName(org.postgresql.Driver.class.getName());
        config.setJdbcUrl( "jdbc:postgresql://localhost:26257/bank?sslmode=disable" );
        config.setUsername( "root" );
        config.setMaxLifetime(30000);
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(2000);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataSource() {}

    public static void hello() {
        System.out.println("hello I'm a connection pool");
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void printConnectionPool() {
        System.out.println(ds.toString());
    }
}
