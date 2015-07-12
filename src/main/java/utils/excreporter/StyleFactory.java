package utils.excreporter;

import elements.detailed.Status;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Priority;

class StyleFactory {

    public static Font createWhiteBoldFont(XSSFWorkbook workbook) {

        Font whiteBoldFont = workbook.createFont();
        whiteBoldFont.setColor(IndexedColors.WHITE.getIndex());
        whiteBoldFont.setBold(true);

        return whiteBoldFont;
    }

    public static Font createBlackBoldFont(XSSFWorkbook workbook) {

        Font blackBoldFont = workbook.createFont();
        blackBoldFont.setColor(IndexedColors.BLACK.getIndex());
        blackBoldFont.setBold(true);

        return blackBoldFont;
    }

    public static CellStyle createHeaderCellStyle(XSSFWorkbook workbook) {

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setFont(createWhiteBoldFont(workbook));

        return headerStyle;
    }

    public static CellStyle createIntermediateCellStyle(XSSFWorkbook workbook) {

        CellStyle intermediateCellStyle = workbook.createCellStyle();
        intermediateCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        intermediateCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        intermediateCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        intermediateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        intermediateCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        intermediateCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        intermediateCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        intermediateCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        intermediateCellStyle.setFont(createWhiteBoldFont(workbook));
        intermediateCellStyle.setRightBorderColor(IndexedColors.WHITE.getIndex());
        intermediateCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        intermediateCellStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        intermediateCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

        return intermediateCellStyle;
    }

    public static CellStyle createSuccessCellStyle(XSSFWorkbook workbook) {

        CellStyle successStyle = workbook.createCellStyle();
        successStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        successStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        successStyle.setAlignment(CellStyle.ALIGN_CENTER);
        successStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        successStyle.setFont(createWhiteBoldFont(workbook));
        successStyle.setBorderLeft(CellStyle.BORDER_THIN);
        successStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());

        return successStyle;
    }

    public static CellStyle createUnknownCellStyle(XSSFWorkbook workbook) {

        CellStyle warningStyle = workbook.createCellStyle();
        warningStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        warningStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        warningStyle.setAlignment(CellStyle.ALIGN_CENTER);
        warningStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        warningStyle.setFont(createWhiteBoldFont(workbook));
        warningStyle.setBorderLeft(CellStyle.BORDER_THIN);
        warningStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());

        return warningStyle;
    }

    public static CellStyle createFailCellStyle(XSSFWorkbook workbook) {

        CellStyle errorStyle = workbook.createCellStyle();
        errorStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        errorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        errorStyle.setAlignment(CellStyle.ALIGN_CENTER);
        errorStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        errorStyle.setFont(createWhiteBoldFont(workbook));
        errorStyle.setBorderLeft(CellStyle.BORDER_THIN);
        errorStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());

        return errorStyle;
    }

    public static CellStyle createStatusCellStyle (XSSFWorkbook workbook, Status status) {
        if (status.equals(Status.SUCCESS)) {
            return createSuccessCellStyle(workbook);
        } else if (status.equals(Status.FAIL)) {
            return createFailCellStyle(workbook);
        } else {
            return createUnknownCellStyle(workbook);
        }
    }

    public static CellStyle createStepCellStyle(XSSFWorkbook workbook) {

        CellStyle stepStyle = workbook.createCellStyle();
        stepStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
        stepStyle.setFillPattern(CellStyle.NO_FILL);
        stepStyle.setAlignment(CellStyle.ALIGN_CENTER);
        stepStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        stepStyle.setFont(createBlackBoldFont(workbook));

        return stepStyle;
    }

    public static CellStyle createDefaultStyle(XSSFWorkbook workbook) {

        CellStyle defaultStyle = workbook.createCellStyle();
        defaultStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
        defaultStyle.setFillPattern(CellStyle.NO_FILL);

        return defaultStyle;
    }

    public static CellStyle createInfoCellStyle(XSSFWorkbook workbook) {

        CellStyle infoStyle = workbook.createCellStyle();
        infoStyle.setWrapText(true);
        infoStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);

        return infoStyle;
    }

    public static CellStyle createPriorityStyle(XSSFWorkbook workbook, Priority priority) {
        CellStyle priorityStyle = workbook.createCellStyle();

        priorityStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        priorityStyle.setAlignment(CellStyle.ALIGN_CENTER);
        priorityStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        priorityStyle.setFont(createWhiteBoldFont(workbook));
        priorityStyle.setBorderLeft(CellStyle.BORDER_THIN);
        priorityStyle.setLeftBorderColor(IndexedColors.WHITE.getIndex());

        short index;

        if (priority != null) {

            if (priority.equals(Priority.HIGHEST)) {
                index = IndexedColors.DARK_RED.getIndex();
            } else if (priority.equals(Priority.HIGH)) {
                index = IndexedColors.RED.getIndex();
            } else if (priority.equals(Priority.MEDIUM)) {
                index = IndexedColors.ORANGE.getIndex();
            } else if (priority.equals(Priority.LOW)) {
                index = IndexedColors.LIGHT_ORANGE.getIndex();
            } else { // lowest
                index = IndexedColors.GREY_40_PERCENT.getIndex();
            }

        } else {
            throw new IllegalArgumentException("Priority cannot be null.");
        }

        priorityStyle.setFillForegroundColor(index);


        return priorityStyle;
    }

    public static CellStyle createBlackCenteredTextStyle(XSSFWorkbook workbook) {

        //the same as StepStyle
        CellStyle stepStyle = workbook.createCellStyle();
        stepStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
        stepStyle.setFillPattern(CellStyle.NO_FILL);
        stepStyle.setAlignment(CellStyle.ALIGN_CENTER);
        stepStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        stepStyle.setFont(createBlackBoldFont(workbook));

        return stepStyle;

    }
}
