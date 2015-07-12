package utils.dbreporter;



import elements.detailed.Status;
import utils.Priority;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestCase {

    TestCase(Report caller, String caseName, Priority priority) {
        this.currentCaller = caller;
        this.connection = currentCaller.getCurrentConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO test_cases (reports_id, test_case_name, test_case_priority) VALUES ("+currentCaller.getCurrentReportID()+", '"+caseName+"', '"+priority.intValue()+"');");
            ResultSet resultSet = statement.executeQuery("SELECT test_case_id FROM test_cases ORDER BY test_case_id DESC LIMIT 1;");
            if (resultSet.next()) {
                this.caseID = resultSet.getInt(1);
            } else {
                System.err.println("Unable to get id from result set - its empty.");
            }
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

        this.caseName = caseName;
        this.reportID = currentCaller.getCurrentReportID();
        this.stepNumber = 0;

    }

    public TestStep createStep(String description, String stepData, String actualResult, String expectedResult, boolean result, Status status, String info) {
        if (description.length() > 499) {
            System.err.println("Description you set is too large (max = 499 symbols).\nCutting description to 499 symbols length...");
            description = description.substring(0, 500);
        }
        if (stepData.length() > 99) {
            System.err.println("Step data you set is too large (max = 99 symbols).\nCutting description to 99 symbols length...");
            stepData = stepData.substring(0, 100);
        }
        if (actualResult.length() > 149) {
            System.err.println("Actual result you set is too large (max = 149 symbols).\nCutting description to 149 symbols length...");
            actualResult = actualResult.substring(0, 150);
        }
        if (expectedResult.length() > 149) {
            System.err.println("Expected result you set is too large (max = 149 symbols).\nCutting description to 149 symbols length...");
            expectedResult = expectedResult.substring(0, 150);
        }
        if (info.length() > 149) {
            System.err.println("Additional info you set is too large (max = 149 symbols).\nCutting description to 149 symbols length...");
            info = info.substring(0, 150);
        }

        this.stepNumber++;

        if (!this.isFailed) {
            if (!result) {
                this.isFailed = true;
                this.currentCaller.incFailedTests();
                db_markAsFailed();
            }
        }

        return new TestStep(this, this.stepNumber, description, stepData, actualResult, expectedResult, result, status, info);
    }

    int getCaseID() {
        return this.caseID;
    }
    Connection getConnection() {
        return this.connection;
    }

    @Override
    public String toString() {
        return "Test case {" +
                "Name: " + this.caseName +
                "; Case id: " + this.caseID +
                "; Report id: " + this.reportID +
                "; Steps amount: " + this.stepNumber + ";}";
    }

    private void db_markAsFailed() {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("UPDATE test_cases set test_case_status = '-1' where test_case_id = '"+this.caseID+"';");
            ResultSet resultSet = statement.executeQuery("SELECT test_case_id FROM test_cases ORDER BY test_case_id DESC LIMIT 1;");
            if (resultSet.next()) {
                this.caseID = resultSet.getInt(1);
            } else {
                System.err.println("Unable to get id from result set - its empty.");
            }
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
    private String caseName;
    private int reportID;
    private int caseID;
    private int stepNumber;
    private Connection connection;
    private Report currentCaller;
    public boolean isFailed;
}
