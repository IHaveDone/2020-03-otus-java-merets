/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class Schema {
    private static final Logger logger = LoggerFactory.getLogger(Schema.class);

    public static void createTableUser(DataSource dataSource) {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("CREATE TABLE user (" +
                     "id bigint(20) not null auto_increment, " +
                     "name varchar(255), " +
                     "age int(3) )")) {
            pst.executeUpdate();
            logger.info("Table `user` created");
        } catch (Exception e) {
            logger.error("Exception during creating of `user` table", e);
        }

    }

    public static void createTableAccount(DataSource dataSource) {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("CREATE TABLE account (" +
                     "no bigint(20) not null auto_increment, " +
                     "type varchar(255), " +
                     "rest number )")) {
            pst.executeUpdate();
            logger.info("Table `account` created");
        } catch (Exception e) {
            logger.error("Exception during creating of `account` table", e);
        }
    }

    public static void truncateAccount(DataSource dataSource) {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("TRUNCATE TABLE account")) {
            pst.executeUpdate();
            logger.info("Table `account` clear");
        } catch (Exception e) {
            logger.error("Exception during clearing of `account` table", e);
        }
    }

    public static void truncateUser(DataSource dataSource) {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("TRUNCATE TABLE user")) {
            pst.executeUpdate();
            logger.info("Table `user` clear");
        } catch (Exception e) {
            logger.error("Exception during clearing of `account` table", e);
        }
    }
}
