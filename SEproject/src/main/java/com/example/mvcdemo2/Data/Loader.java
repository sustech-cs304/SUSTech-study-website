package com.example.mvcdemo2.Data;


import com.example.mvcdemo2.task.TopicPopularity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.sql.*;

@Controller
public class Loader{
    private static final int BATCH_SIZE = 1000;
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicPopularity.class);

    private static void openDB(Properties prop) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            LOGGER.error("Cannot find the Postgres driver. Check CLASSPATH.");
            //System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        try {
            con = DriverManager.getConnection(url, prop);
            if (con != null) {
                LOGGER.info("Successfully connected to the database "
                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
                //System.out.println("Successfully connected to the database "
                        //+ prop.getProperty("database") + " as " + prop.getProperty("user"));
                con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            LOGGER.error("Database connection failed");
            LOGGER.error(e.getMessage());
            //System.err.println("Database connection failed");
            //System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static void setPrepareStatement() {
        try {
            stmt = con.prepareStatement("INSERT INTO public.student ( question_id,title, user_id, view_count, answer_count, tags,url,content) " +
                    "VALUES (?,?,?,?,?,?,?,?);");
        } catch (SQLException e) {
            LOGGER.error("Insert statement failed");
            LOGGER.error(e.getMessage());
            //System.err.println("Insert statement failed");
            //System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
        }
    }

    private static Properties loadDBUser() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("src/main/resources/dbUser.properties")));
            return properties;
        } catch (IOException e) {
            LOGGER.error("can not find db user file");
            //System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }

    private static List<String> loadTXTFile() {
        try {
            return Files.readAllLines(Path.of("/Users/sco/Downloads/mvcdemo-1/src/main/java/com/example/mvcdemo2/Data/data2.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadData(String line) {
        String[] lineData = line.split("¥");
        if (con != null) {
            try {
                stmt.setInt(1, Integer.parseInt(lineData[0]));
                stmt.setString(2, lineData[1]);
                stmt.setInt(3, Integer.parseInt(lineData[2]));
                stmt.setInt(4, Integer.parseInt(lineData[3]));
                stmt.setInt(5, Integer.parseInt(lineData[4]));
                stmt.setString(6, lineData[5]);
                stmt.setString(7, lineData[6]);
                stmt.setString(8, lineData[7]);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void clearDataInTable() {
        Statement stmt0;
        if (con != null) {
            try {
                stmt0 = con.createStatement();
                stmt0.executeUpdate("DELETE FROM student;");
                con.commit();
//                stmt0.executeUpdate("CREATE TABLE student (\n" +
//                        "    items VARCHAR(255),  \n" +
//                        "    account_id INT,\n" +
//                        "    user_id INT,\n" +
//                        "    view_count INT,\n" +
//                        "    answer_count INT\n" +
//                        ");");
//                con.commit();
//                stmt0.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    @GetMapping("/Load")
    public void Load(){
        Properties prop = loadDBUser();
        List<String> lines = loadTXTFile();

        // Empty target table
        openDB(prop);
        clearDataInTable();
        closeDB();


        int cnt = 0;
        long start = System.currentTimeMillis();
        openDB(prop);
        setPrepareStatement();
        try {
            for (String line : lines) {
                loadData(line);//do insert command
                if (cnt % BATCH_SIZE == 0) {
                    stmt.executeBatch();
                    LOGGER.info("insert " + BATCH_SIZE + " data successfully!");
                    //System.out.println("insert " + BATCH_SIZE + " data successfully!");
                    stmt.clearBatch();
                }
                cnt++;
            }

            if (cnt % BATCH_SIZE != 0) {
                stmt.executeBatch();
                LOGGER.info("insert " + cnt % BATCH_SIZE + " data successfully!");
                //System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");

            }
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeDB();
        long end = System.currentTimeMillis();
        LOGGER.info(cnt + " records successfully loaded");
        LOGGER.info("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");
        //System.out.println(cnt + " records successfully loaded");
        //System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");

    }


    public static void main(String[] args) {

        Properties prop = loadDBUser();
        List<String> lines = loadTXTFile();

        // Empty target table
        openDB(prop);
        clearDataInTable();
        closeDB();


        int cnt = 0;
        long start = System.currentTimeMillis();
        openDB(prop);
        setPrepareStatement();
        try {
            for (String line : lines) {
                loadData(line);//do insert command
                if (cnt % BATCH_SIZE == 0) {
                    stmt.executeBatch();
                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
                    stmt.clearBatch();
                }
                cnt++;
            }

            if (cnt % BATCH_SIZE != 0) {
                stmt.executeBatch();
                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");

            }
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeDB();
        long end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");

    }
}


