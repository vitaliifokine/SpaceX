package testpackage;


import elements.detailed.Status;
import utils.Priority;
import utils.excreporter.ExcelReport;
import utils.excreporter.TestCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Practice {


    public static void main(String[] args) {
        /*String s = "iwtwcvffom fmysaugmqe (1290427)";
        Matcher matcher = Pattern.compile("[(]\\d+[)]").matcher(s);

        if (matcher.find()) {
            String s2 = matcher.group();
            System.out.println(s2);
        }*/

        String s = "dsgfhgjhgas fhas ghjg sahgdf gadshgf adgsjgf jhadsgf gasj.";
        ExcelReport.setFileDirectory("C:\\IdeaProjects\\SpaceX\\reports\\");
        ExcelReport report = new ExcelReport("some report");
        TestCase tc = report.createTestCase("some case", Priority.HIGH);
        tc.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        tc.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        tc.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        tc.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        tc.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        tc.makeStep(s, s, s, s, true, Status.SUCCESS, s);
        report.finish();
    }


}