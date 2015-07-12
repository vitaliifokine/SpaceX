package utils.excreporter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Priority;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExcelReport {

    public ExcelReport (String name) {

        this.fileDestination = getNewWorkbookPath(name);

        try {
            FileInputStream inputStream = new FileInputStream(new File(this.fileDestination));
            this.workbook = new XSSFWorkbook(inputStream);
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            System.exit(0);
        } catch (IOException ex) {
            System.err.println("IO exception.");
            System.exit(0);
        }

        this.workbook.setSheetName(0, "Overall");
        this.sheet = workbook.getSheetAt(0);
        this.firstRow = this.sheet.createRow(this.firstInformalRow);
        createHeaders();
    }

    public TestCase createTestCase(String caseName, Priority priority) {
        if (callable != null) {
            callable.feedBack();
        }
        casesRun++;
        return new TestCase(this, caseName, priority);
    }
    public void finish() {
        Cell nameCell;
        Cell priorityCell;
        Cell defaultStatus;

        if (callable != null) {
            this.callable.feedBack();
        }

        for (Note note : notes) {
            if (rowForTestCase != firstInformalRow) {
                Row row = this.sheet.createRow(rowForTestCase);

                nameCell = row.createCell(OverallColumns.TEST_CASE_NAME.getPosition());
                priorityCell = row.createCell(OverallColumns.TC_PRIORITY.getPosition());
                defaultStatus = row.createCell(OverallColumns.TC_STATUS.getPosition());

                nameCell.setCellValue(note.getTestCaseName());
                nameCell.setCellStyle(StyleFactory.createInfoCellStyle(this.workbook));
                priorityCell.setCellValue(note.getPriority().toString());
                priorityCell.setCellStyle(StyleFactory.createPriorityStyle(this.workbook, note.getPriority()));
                defaultStatus.setCellValue(note.getStatus().toString());
                defaultStatus.setCellStyle(StyleFactory.createStatusCellStyle(this.workbook, note.getStatus()));

                rowForTestCase++;

            } else {
                nameCell = this.firstRow.createCell(OverallColumns.TEST_CASE_NAME.getPosition());
                priorityCell = this.firstRow.createCell(OverallColumns.TC_PRIORITY.getPosition());
                defaultStatus = this.firstRow.createCell(OverallColumns.TC_STATUS.getPosition());


                nameCell.setCellValue(note.getTestCaseName());
                nameCell.setCellStyle(StyleFactory.createInfoCellStyle(this.workbook));
                priorityCell.setCellValue(note.getPriority().toString());
                priorityCell.setCellStyle(StyleFactory.createPriorityStyle(this.workbook, note.getPriority()));
                defaultStatus.setCellValue(note.getStatus().toString());
                defaultStatus.setCellStyle(StyleFactory.createStatusCellStyle(this.workbook, note.getStatus()));

                rowForTestCase++;
            }


        }

        setOverallData();

    }
    public static void setFileDirectory(String path) {
        fileDirectory = path;
    }

    //for package use
    String getFileDestination() {
        return this.fileDestination;
    }
    XSSFWorkbook getWorkbook() {
        return this.workbook;
    }
    void incFailedTests() {
        this.casesFailed++;
    }
    void addNote(Note note) {
        this.notes.add(note);
    }
    void setCallable(TestCase callable) {
        this.callable = callable;
    }

    private void writeToFile() {
        try {
            FileOutputStream output = new FileOutputStream(new File(this.fileDestination));
            this.workbook.write(output);
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //actions to table
    private void createHeaders() {

        this.sheet.setColumnWidth(OverallColumns.TEST_CASE_NAME.getPosition(), 15000);
        this.sheet.setColumnWidth(OverallColumns.TC_STATUS.getPosition(), 4000);
        this.sheet.setColumnWidth(OverallColumns.TC_PRIORITY.getPosition(), 4000);
        this.sheet.setColumnWidth(OverallColumns.DATE.getPosition(), 5000);
        this.sheet.setColumnWidth(OverallColumns.CASES_RUN.getPosition(), 5000);
        this.sheet.setColumnWidth(OverallColumns.CASES_FAILED.getPosition(), 5000);

        CellStyle headerStyle = StyleFactory.createHeaderCellStyle(workbook);

        CellStyle intermediateCellStyle = StyleFactory.createIntermediateCellStyle(workbook);

        int headerPosition = 1;
        Row row = this.sheet.createRow(headerPosition);

        row.setHeight((short) 400);
        Cell cell;
        int mapSize = this.header.size();
        int index = 1;
        for (Map.Entry<Integer, String> entry : this.header.entrySet()) {
            cell = row.createCell(entry.getKey());
            cell.setCellValue(entry.getValue());
            if (index != 1 && index != mapSize) {
                cell.setCellStyle(intermediateCellStyle);
            } else {
                cell.setCellStyle(headerStyle);
            }
            index++;
        }

        writeToFile();
    }
    private void setOverallData() {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();

        Cell dateCell = this.firstRow.createCell(OverallColumns.DATE.getPosition());
        dateCell.setCellValue(dateFormat.format(date));
        dateCell.setCellStyle(StyleFactory.createBlackCenteredTextStyle(this.workbook));

        Cell casesRunCell = this.firstRow.createCell(OverallColumns.CASES_RUN.getPosition());
        casesRunCell.setCellValue(this.casesRun);
        casesRunCell.setCellStyle(StyleFactory.createBlackCenteredTextStyle(this.workbook));


        Cell casesFailedCell = this.firstRow.createCell(OverallColumns.CASES_FAILED.getPosition());
        casesFailedCell.setCellValue(this.casesFailed);
        casesFailedCell.setCellStyle(StyleFactory.createBlackCenteredTextStyle(this.workbook));

        writeToFile();

    }
    private String getNewWorkbookPath(String name) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date date = new Date();

        String path = fileDirectory + name + " ["+dateFormat.format(date)+"].xlsx";

        XSSFWorkbook wb = new XSSFWorkbook();
        wb.createSheet("Default");


        try {
            FileOutputStream fos = new FileOutputStream(path);
            wb.write(fos);
            fos.flush();
            fos.close();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private int casesRun = 0;
    private int casesFailed = 0;
    private int rowForTestCase = 2;
    private int firstInformalRow = 2;
    private Sheet sheet;
    private String fileDestination;
    private static String fileDirectory = "";
    private XSSFWorkbook workbook;
    private TestCase callable;
    private Row firstRow; // this row is shared between 2 neighbour tables
    private ArrayList<Note> notes = new ArrayList<Note>(50);
    private HashMap<Integer, String> header = new HashMap<Integer, String>();
    {
        header.put(OverallColumns.TEST_CASE_NAME.getPosition(), "Test case name");
        header.put(OverallColumns.TC_STATUS.getPosition(), "Status");
        header.put(OverallColumns.TC_PRIORITY.getPosition(), "Priority"); // default value but it should change during test reporting
        header.put(OverallColumns.DATE.getPosition(), "Date");
        header.put(OverallColumns.CASES_RUN.getPosition(), "Cases run");
        header.put(OverallColumns.CASES_FAILED.getPosition(), "Cases failed");
    }

}
