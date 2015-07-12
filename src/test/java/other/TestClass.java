package other;

import elements.detailed.Status;
import utils.Priority;
import utils.excreporter.ExcelReport;
import utils.excreporter.TestCase;


public class TestClass {

    public static void main(String [] args) {
        String s = "asdasdasdasd";
        ExcelReport.setFileDirectory("C:\\Users\\Tester\\IdeaProjects\\SpaseX\\reports\\");
        ExcelReport report = new ExcelReport("name");
        TestCase testCase = report.createTestCase("test case 1", Priority.HIGH);
        testCase.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        testCase.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        testCase.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        testCase.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        testCase.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        testCase.makeStep(s, s, s, s, true, Status.SUCCESS, s);

        report.finish();
    }

}
