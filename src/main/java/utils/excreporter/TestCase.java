package utils.excreporter;

import elements.detailed.Status;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Priority;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * API designed to provide ability to report directly to Excel file
 * Be careful! This API has dependency from Apache POI.
 */

public class TestCase {

    TestCase(ExcelReport caller, String caseName, Priority priority) {
        this.caller = caller;
        this.caller.setCallable(this);
        this.priority = priority;
        this.workbook = this.caller.getWorkbook();
        this.fileDestination = this.caller.getFileDestination();
        this.sheet = this.workbook.createSheet(caseName);
        this.caseName = caseName;
        createHeader();
    }

    public void makeStep(String description, String data, String actualResult, String expectedResult, Boolean passed, Status stepStatus, String additionalInfo) {

        CellStyle errorStyle = StyleFactory.createFailCellStyle(this.workbook);
        CellStyle warningStyle = StyleFactory.createUnknownCellStyle(this.workbook);
        CellStyle successStyle = StyleFactory.createSuccessCellStyle(this.workbook);
        CellStyle stepStyle = StyleFactory.createStepCellStyle(this.workbook);
        CellStyle infoStyle = StyleFactory.createInfoCellStyle(this.workbook);

        Row theRow = this.sheet.createRow(nextRow);
        Cell cellStatus;
        Cell cellDescription;
        Cell cellStep;

        cellStep = theRow.createCell(StepColumns.STEP_NUMBER.getPosition() + this.initialShift);
        cellStep.setCellValue(this.step); this.step++;
        cellStep.setCellStyle(stepStyle);

        cellDescription = theRow.createCell(StepColumns.DESCRIPTION.getPosition() + this.initialShift);
        cellDescription.setCellValue(description);
        cellDescription.setCellStyle(infoStyle);

        cellDescription = theRow.createCell(StepColumns.DATA.getPosition() + this.initialShift);
        cellDescription.setCellValue(data);
        cellDescription.setCellStyle(infoStyle);

        cellDescription = theRow.createCell(StepColumns.ACTUAL_RESULT.getPosition() + this.initialShift);
        cellDescription.setCellValue(actualResult);
        cellDescription.setCellStyle(infoStyle);

        cellDescription = theRow.createCell(StepColumns.EXPECTED_RESULT.getPosition() + this.initialShift);
        cellDescription.setCellValue(expectedResult);
        cellDescription.setCellStyle(infoStyle);

        cellDescription = theRow.createCell(StepColumns.ADDITIONAL_INFO.getPosition() + this.initialShift);
        cellDescription.setCellValue(additionalInfo);
        cellDescription.setCellStyle(infoStyle);

        if (stepStatus == Status.SUCCESS) {

            cellStatus = theRow.createCell(StepColumns.CONDITION.getPosition() + this.initialShift);
            cellStatus.setCellValue("Success");
            cellStatus.setCellStyle(successStyle);

        } else if (stepStatus == Status.UNKNOWN) {

            cellStatus = theRow.createCell(StepColumns.CONDITION.getPosition() + this.initialShift);
            cellStatus.setCellValue("Unknown");
            cellStatus.setCellStyle(warningStyle);
            this.hasWarning = true;

        } else {

            cellStatus = theRow.createCell(StepColumns.CONDITION.getPosition() + this.initialShift);
            cellStatus.setCellValue("Fail");
            cellStatus.setCellStyle(errorStyle);
            this.hasError = true;

        }

        if (passed == null) {

            cellStatus = theRow.createCell(StepColumns.STATUS.getPosition() + this.initialShift);
            cellStatus.setCellValue("Unknown");
            cellStatus.setCellStyle(warningStyle);
            this.hasWarning = true;

        } else if (passed) {

            cellStatus = theRow.createCell(StepColumns.STATUS.getPosition() + this.initialShift);
            cellStatus.setCellValue("Success");
            cellStatus.setCellStyle(successStyle);


        } else {

            cellStatus = theRow.createCell(StepColumns.STATUS.getPosition() + this.initialShift);
            cellStatus.setCellValue("Fail");
            cellStatus.setCellStyle(errorStyle);
            this.hasError = true;

        }

        markSheet(this.sheet);
        writeToFile(this.workbook);
        this.nextRow++;

    }

    void feedBack() {
        Status status;
        if (!hasError && !hasWarning) {
            status = Status.SUCCESS;
        } else if (hasWarning && !hasError) {
            status = Status.UNKNOWN;
            this.caller.incFailedTests();
        } else {
            status = Status.FAIL;
            this.caller.incFailedTests();
        }
        this.caller.addNote(new Note (this.caseName, status, this.priority));
    }

    private void markSheet(Sheet sheet) {
        if (!errorHasBeenSet) {
            if (hasError) {
                ((XSSFSheet) sheet).setTabColor(IndexedColors.RED.getIndex());
                errorHasBeenSet = true;
            } else if (hasWarning) {
                ((XSSFSheet) sheet).setTabColor(IndexedColors.LIGHT_ORANGE.getIndex());
                warningHasBeenSet = true;
            } else {
                ((XSSFSheet) sheet).setTabColor(IndexedColors.GREEN.getIndex());
                successHasBeenSet = true;
            }
        } else if (!warningHasBeenSet && !hasError) {
            if (hasWarning) {
                ((XSSFSheet) sheet).setTabColor(IndexedColors.LIGHT_ORANGE.getIndex());
                warningHasBeenSet = true;
            } else {
                ((XSSFSheet) sheet).setTabColor(IndexedColors.GREEN.getIndex());
                successHasBeenSet = true;
            }
        } else if (!successHasBeenSet && !hasWarning && !hasError){
            ((XSSFSheet) sheet).setTabColor(IndexedColors.GREEN.getIndex());
            successHasBeenSet = true;
        }

    }
    private void writeToFile(Workbook workbook) {
        try {
            FileOutputStream output = new FileOutputStream(new File(this.fileDestination));
            workbook.write(output);
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void createHeader() {

        sheet.setColumnWidth(StepColumns.DESCRIPTION.getPosition() + initialShift, 15000);
        sheet.setColumnWidth(StepColumns.DATA.getPosition() + initialShift, 9000);
        sheet.setColumnWidth(StepColumns.ACTUAL_RESULT.getPosition() + initialShift, 9000);
        sheet.setColumnWidth(StepColumns.EXPECTED_RESULT.getPosition() + initialShift, 9000);
        sheet.setColumnWidth(StepColumns.STATUS.getPosition() + initialShift, 3000);
        sheet.setColumnWidth(StepColumns.CONDITION.getPosition() + initialShift, 3000);
        sheet.setColumnWidth(StepColumns.ADDITIONAL_INFO.getPosition() + initialShift, 9000);

        CellStyle headerStyle = StyleFactory.createHeaderCellStyle(workbook);

        CellStyle intermediateCellStyle = StyleFactory.createIntermediateCellStyle(workbook);

        Row row = sheet.createRow(initialShift);

        row.setHeight((short) 400);
        Cell cell;
        int mapSize = header.size();
        int index = 1;
        for (Map.Entry<Integer, String> entry : header.entrySet()) {
            cell = row.createCell(entry.getKey());
            cell.setCellValue(entry.getValue());
            if (index != 1 && index != mapSize) {
                cell.setCellStyle(intermediateCellStyle);
            } else {
                cell.setCellStyle(headerStyle);
            }
            index++;
        }

        writeToFile(workbook);
    }

    //sources to set up
    private ExcelReport caller;
    private XSSFWorkbook workbook;
    private String fileDestination;
    private Sheet sheet;
    private Priority priority;
    private String caseName;

    //initials
    private HashMap<Integer, String> header = new HashMap<Integer, String>();
    private final int initialShift = 1;
    private int nextRow = 2;
    private int step = 1;

    //map setup
    {
        header.put(StepColumns.STEP_NUMBER.getPosition() + initialShift, "#");
        header.put(StepColumns.DESCRIPTION.getPosition() + initialShift, "Description");
        header.put(StepColumns.DATA.getPosition() + initialShift, "Data used");
        header.put(StepColumns.ACTUAL_RESULT.getPosition() + initialShift, "Actual result");
        header.put(StepColumns.EXPECTED_RESULT.getPosition() + initialShift, "Expected result");
        header.put(StepColumns.STATUS.getPosition() + initialShift, "Status");
        header.put(StepColumns.CONDITION.getPosition() + initialShift, "Condition");
        header.put(StepColumns.ADDITIONAL_INFO.getPosition() + initialShift, "Additional info");
        //if need more items - just add them below. use different and positive KEYs
    }

    //flags
    private boolean hasWarning = false;
    private boolean hasError = false;
    private boolean warningHasBeenSet = false;
    private boolean errorHasBeenSet = false;
    private boolean successHasBeenSet = false;

}
