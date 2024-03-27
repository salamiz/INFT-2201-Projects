/**
 * Name: Zulkifli Salami
 * Student ID: 100850581
 * Date Completed: 03/25/2024
 * Description: This is a server side servlet used to set up connection pooling using HikariCP library
 *
 *    Copyright 2024 Zulkifli Salami
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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

