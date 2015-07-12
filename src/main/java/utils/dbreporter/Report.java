package utils.dbreporter;


import com.mysql.fabric.jdbc.FabricMySQLDriver;
import utils.Priority;

import java.sql.*;

public class Report {

    private Report(String projectName, String testsClass, DBCredential dbCredential) {

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            this.connection = DriverManager.getConnection(dbCredential.getConnectionURL(),
                    dbCredential.getLogin(), dbCredential.getPassword());

            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO reports (date, project_name, tests_class) VALUES (NOW(), '" + projectName + "', '" + testsClass + "')");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reports ORDER BY id DESC LIMIT 1;");
            if (resultSet.next()) {
                this.reportID = resultSet.getInt(1);
                this.date = "" + resultSet.getDate(2);
                this.time = "" + resultSet.getTime(2);
            } else {
                System.err.println("Unable to get id from result set - its empty.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    //
                }
            }
        }
    }

    public static Report getInstance(String projectName, String testsCategory) {
        if (projectName.length() > 40) {
            System.err.println("Project name you set is too large (max = 40 symbols).\nCutting name to 40 symbols length...");
            projectName = projectName.substring(0, 41);
        }
        if (testsCategory.length() > 40) {
            System.err.println("Test category name you set is too large (max = 40 symbols).\nCutting category name to 40 symbols length...");
            testsCategory = testsCategory.substring(0, 41);
        }
        if (report != null) {
            return report;
        } else {
            report =  new Report(projectName, testsCategory, DBCredentials.ROOT);
            return report;
        }
    }
    public TestCase createTestCase(String caseName, Priority priority) {
        if (caseName.length() > 240) {
            System.err.println("Test case name you set is too large (max = 240 symbols).\nCutting category name to 240 symbols length...");
            caseName = caseName.substring(0, 241);
        }
        incRunTests();
        return new TestCase(this, caseName, priority);
    }
    public int getID() {
        return this.reportID;
    }
    public String getDate() {
        return this.date;
    }
    public void finish() {
        try {
            db_Update();
            connection.close();
            report = null;
        } catch (SQLException e) {
            //
        }
    }
    @Override public String toString() {
        return "Report #" + reportID + "; [Date: " + date + " " + time + "]";
    }

    void incRunTests() {
        this.casesRun += 1;
    }
    void incFailedTests() {
        this.casesFailed += 1;
    }

    Connection getCurrentConnection() {
        return this.connection;
    }
    int getCurrentReportID() {
        return this.reportID;
    }
    private void db_Update() {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("UPDATE reports set cases_run = '" + this.casesRun + "' where id = '" + this.reportID + "';");
            statement.execute("UPDATE reports set cases_failed = '" + this.casesFailed + "' where id = '" + this.reportID + "';");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e1) {
                //
            }
            System.exit(0);

        }
    }

    private int reportID;
    private String date;
    private String time;
    private Connection connection;
    private static Report report = null;
    private int casesRun = 0;
    private int casesFailed = 0;

}
