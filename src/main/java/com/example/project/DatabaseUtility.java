/**
 * Name: Zulkifli Salami
 * Student ID: 100850581
 * Date Completed: 03/25/2024
 * Description: This is a server side servlet used to set up connection pooling using HikariCP library
 */

package com.example.project;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseUtility {
    // Static instance of HikariDataSource for connection pooling
    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            // Configuring database connection from environment variables
            config.setJdbcUrl(System.getenv("DB_URL"));
            config.setUsername(System.getenv("DB_USER"));
            config.setPassword(System.getenv("DB_PASS"));

            config.setDriverClassName("org.postgresql.Driver");

            // Initializing the connection pool
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    // Public method to access the configured data source
    public static DataSource getDataSource() {
        return dataSource;
    }
}

