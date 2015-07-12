package utils.dbreporter;


import elements.detailed.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestStep {

    public TestStep(TestCase testCase, int stepNumber, String description, String stepData, String actualResult, String expectedResult, boolean result, Status status, String info) {

        this.stepData = stepData;
        this.caseID = testCase.getCaseID();

        this.stepNumber = stepNumber;
        this.description = description;
        this.actualResult = actualResult;
        this.expectedResult = expectedResult;
        if (result) {
            this.result = StepResult.SUCCESS;
        } else {
            this.result = StepResult.FAILED;
        }

        this.connection = testCase.getConnection();
        this.status = status;
        this.info = info;

    }

    public void submit() {

        PreparedStatement statement;
        String p_request = "INSERT INTO test_steps (test_case_id, step_number, step_desc, step_data, actual_result, expected_result, step_status, step_condition, additional_info) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            statement = this.connection.prepareStatement(p_request);
            statement.setInt(1, this.caseID);
            statement.setInt(2, this.stepNumber);
            statement.setString(3, this.description);
            statement.setString(4, this.stepData);
            statement.setString(5, this.actualResult);
            statement.setString(6, this.expectedResult);
            statement.setInt(7, this.result.intValue());
            statement.setString(8, this.status.toString());
            statement.setString(9, this.info);

            statement.execute();
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

    @Override
    public String toString() {
         return "Test makeStep {" +
                 "Case id: " + this.caseID + "; " +
                 "Step number: " + this.stepNumber + "; " +
                 "Step description: " + this.description + "; " +
                 "Step data: " + this.stepData + "; " +
                 "Actual result: " + this.actualResult + "; " +
                 "Expected result: " + this.expectedResult + "; " +
                 "Result: " + this.result + "; " +
                 "Status: " + this.status +";" +
                 "}";
    }

    private String description,
            stepData,
            actualResult,
            expectedResult;
    private StepResult result;
    private Status status;
    private int stepNumber, caseID;
    private Connection connection;
    private String info;

}
