package elements.detailed;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Timeouts;

import java.util.ArrayList;
import java.util.List;
/**
 * This class designed to provide APIs to handle different web tables.
 * Works using By.xpath() approach.
 * */
public class WebTable {

    public  WebTable(WebDriver driver) {
        this.driver = driver;
        this.tableLocation = "//table";
        this.tableHeadPath = tableLocation + "/thead/*[1]/*";
        wait = new WebDriverWait(driver, Timeouts.SEC_MEDIUM);
    }
    public  WebTable(WebDriver driver, String pathToTable) {
        this(driver);
        this.tableLocation = pathToTable;

    }
    public  WebTable(WebDriver driver, String pathToTable, String pathToHeadElements, int bodyRowsShift) {
        this(driver, pathToTable);
        this.bodyRowsShift = bodyRowsShift;
        this.tableHeadPath = tableLocation + pathToHeadElements;
        wait = new WebDriverWait(driver, Timeouts.SEC_MEDIUM);
    }

    public Result<ArrayList<String>> getAllColumnNames() {
        String message;
        ArrayList<String> result = new ArrayList<String>();

        Result<Boolean> appearance = checkAppearance();
        if (appearance.getValue()) {
            for (WebElement name : getElementsByXpath(tableHeadPath)) {
                // replaceAll("\\n", "/") -> for <select> elements to print their options like option1/option2/option3
                result.add(name.getText().replaceAll("\\n", "/"));
            }
            if (result.isEmpty()) {
                message = appearance.getDescription() + " Looks like there are no names in column headers. Using xpath = '"+tableHeadPath+"'.";
            } else {
                message = appearance.getDescription() + " Column names got successfully.";
            }
        } else {
            message = appearance.getDescription() + " Unable to get all column names.";
        }

        return new Result<ArrayList<String>>(true, result, message);
    }
    public Result<Boolean> isEmpty() {
        Boolean result;
        boolean status;
        String message;

        Result<Boolean> appearance = checkAppearance();
        if (appearance.getValue()) {
            if (this.isIndicated) {
                result = isDisplayedElementXpath(emptinessIndicator);
            } else {
                result = !isDisplayedElementXpath(tableLocation + "/tbody/tr/td");
            }
            status = true;
            message = appearance.getDescription() + " Checked for emptiness successfully.";
        } else {
            message = appearance.getDescription() + " Unable to check if table is empty or not.";
            result = false;
            status = false;
        }

        return new Result<Boolean>(status, result, message);
    }
    public Result<Integer> tableSize() {
        Integer result;
        String message;
        String pathToAnyElementInTableHead = tableLocation + "/*[1]/*[1]/*";
        try {
            result = driver.findElements(By.xpath(pathToAnyElementInTableHead)).size();
            message = "Table size got successfully.";
        } catch (NoSuchElementException e) {
            result = 0;
            message = "Looks like table is empty. Using xpath = '"+pathToAnyElementInTableHead+"'.";
        }

        return new Result<Integer>(true, result, message);
    }
    public Result<Integer> getPositionOfColumnNamed (String columnName) {
        Integer result;
        String message;
        boolean status;

        boolean found = false;
        int col = 0;
        for (WebElement sorter : getElementsByXpath(tableHeadPath)) {
            col++;
            if (sorter.getText().equalsIgnoreCase(columnName)) {
                found = true;
                break;
            }
        }
        if (found) {
            result = col;
            message = "Location of column '"+columnName+"' found successfully.";
            status = true;
        } else {
            message = "Unable to locate column '"+columnName+"' (Column not found). Using xpath = '"+tableHeadPath+"'. " +
                    "Names of actual columns: " + getAllColumnNames();
            result = 0;
            status = false;
        }

        return new Result<Integer>(status, result, message);
    }
    public Result<Boolean> checkAppearance () {
        Boolean result;
        String message;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableLocation)));
            message = "Visibility of table successfully confirmed.";
            result = driver.findElement(By.xpath(tableLocation)).isDisplayed();
        } catch (TimeoutException e) {
            message = "Cannot find table element until time out. Using xpath = '"+tableLocation+"'.";
            result = false;
        }
        return new Result<Boolean>(true, result, message);
    }

    public Column inColumnNamed (String columnName) {
        boolean found = false;
        int col = 0;
        for (WebElement column : getElementsByXpath(tableHeadPath)) {
            col++;
            if (column.getText().equalsIgnoreCase(columnName)) {
                found = true;
                break;
            }
        }
        if (found) {
            return new Column(col);
        } else {
            System.err.println("Unable to locate column '"+columnName+"' (Column not found)");
            System.out.println("Names of actual columns: " + getAllColumnNames());
            return new Column(false);
        }

    }
    public Column inLastColumn () {
        if (tableSize().getValue() > 0) {
            return new Column(tableSize().getValue());
        } else {
            return new Column(false);
        }

    }
    public Column inFirstColumn () {
        if (tableSize().getValue() > 0) {
            return new Column(1);
        } else {
            return new Column(false);
        }

    }
    public Column inColumnNumber (int number) {
        return new Column(number);
    }
    public Row rowNumber(int number) {
        return new Row(number + bodyRowsShift);
    }

    public class Column {

        public Row rowWithExactText(String text) {
            if (columnFound) {
                int row = 0;
                boolean found = false;

                try {
                    for (WebElement r : driver.findElements(By.xpath(tableLocation + "/tbody/tr/*[local-name() = 'td' or local-name() = 'th']["+column+"]"))) {
                        row++;
                        if (r.getText().toLowerCase().equals(text.toLowerCase())) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        return new Row(row);
                    } else {
                        System.err.println("Not found row with such text '"+text+"' in column #"+column+"");
                        if (isDisplayedElementXpath(emptinessIndicator)) {
                            System.out.println("Table body is empty ('No results found')");
                        }
                        return new Row(false);
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("Not found elements using xpath '"+tableLocation + "/tbody/tr/td["+column+"]"+"'");
                    return new Row(false);
                }
            } else {
                return new Row(false);
            }


        }
        public Row rowContainsText(String text) {
            if (columnFound) {
                int row = 0;
                boolean found = false;

                try {
                    for (WebElement r : driver.findElements(By.xpath(tableLocation + "/tbody/tr/*[local-name() = 'td' or local-name() = 'th']["+column+"]"))) {
                        row++;
                        if (r.getText().toLowerCase().contains(text.toLowerCase())) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        return new Row(row);
                    } else {
                        System.err.println("Not found row with such text '"+text+"' in column #"+column+"");
                        if (isDisplayedElementXpath(emptinessIndicator)) {
                            System.out.println("Table body is empty ('No results found')");
                        }
                        return new Row(false);
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("Not found elements using xpath '"+tableLocation + "/tbody/tr/td["+column+"]"+"'");
                    return new Row(false);
                }
            } else {
                return new Row(false);
            }


        }
        public Row rowNumber(int rowNumber) {
            if (columnFound) {
                try {
                    driver.findElement(By.xpath(tableLocation + "/tbody/tr["+rowNumber+"]"));
                    return new Row(rowNumber + bodyRowsShift);
                } catch (NoSuchElementException e) {
                    System.err.println("Not found elements using xpath '"+tableLocation + "/tbody/tr/td["+column+"]"+"'");
                    return new Row(false);
                }
            } else {
                return new Row(false);
            }


        }
        public Column useFilter (String text) {
            if (columnFound) {
                TableFilter filter = new TableFilter(column);
                filter.use(text);
                if (filter.isUsed()) {
                    return this;
                } else {
                    System.err.println("Filter wasn't used.");
                    return this;
                }
            } else {
                System.err.println("Unable to use filter because required column not located.");
                return this;
            }

        }
        public Cell cell (String cellText) {

            if (columnFound) {
                int row = 0;
                boolean found = false;

                try {
                    for (WebElement r : driver.findElements(By.xpath(tableLocation + "/tbody/tr/*[local-name() = 'td' or local-name() = 'th']["+column+"]"))) {
                        row++;
                        if (r.getText().toLowerCase().contains(cellText.toLowerCase())) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        return new Cell(row, column);
                    } else {
                        System.err.println("Not found cell with such text '"+cellText+"' in column #"+column+"");
                        if (isDisplayedElementXpath(emptinessIndicator)) {
                            System.out.println("Table body is empty ('No results found')");
                        }
                        return new Cell(false);
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("Not found elements using xpath '"+tableLocation + "/tbody/tr/td["+column+"]"+"'");
                    return new Cell(false);
                }
            } else {
                return new Cell(false);
            }

        }
        public Cell cell (int number) {
            //number counts from top
            if (columnFound) {
                return new Cell(number, column);
            } else {
                return new Cell(false);
            }

        }

        public Result<Void> clickToSort() {
            boolean status = false;
            String message;
            if (columnFound) {
                String path = tableLocation + tableHeadPath + "[" + column + "]";
                if(clickElement(path)) {
                    message = "Click to sorter was successful.";
                    status = true;
                } else  {
                    message = "Click to sorter wasn't successful. Using xpath = '"+path+"'.";
                }
            } else {
                message = "Column is not available. Click to sorter wasn't proceeded.";
            }
            return new Result<Void>(status, null, message);
        }
        public Result<ArrayList<String>> getTextsFromElementsInBody() {

            ArrayList<String> result = new ArrayList<String>();
            boolean status = false;
            String message;
            if (columnFound) {
                Result<ArrayList<String>> texts = getTextsOfElements(tableLocation + "/tbody/tr/td["+column+"]");
                if (texts.getStatus().getBooleanValue()) {
                    result = deleteFromBegin(texts.getValue(), bodyRowsShift);
                    message = texts.getDescription();
                    status = true;
                } else {
                    message = texts.getDescription();
                }
            } else {
                message = "Column hasn't been found and getting of it's elements texts cannot be proceeded.";
            }
            return new Result<ArrayList<String>>(status, result, message);

        }

        private int column = 0;
        private boolean columnFound = true;
        private Column (int column) {
            this.column = column;
        }
        private Column (boolean found) {
            this.columnFound = found; // for case if not found
        }

    }

    public class Row {

        public Result<ArrayList<String>> getAllTexts () {
            ArrayList<String> result = new ArrayList<String>();
            boolean status = false;
            String message;
            if (rowFound) {
                Result<ArrayList<String>> texts = getTextsOfElements(tableLocation + "/tbody/tr["+row+"]/td");
                if (texts.getStatus().getBooleanValue()) {
                    result = texts.getValue();
                    message = texts.getDescription();
                    status = true;
                } else {
                    message = texts.getDescription();
                }
            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getStatus().getBooleanValue()) {
                    message = emptiness.getDescription() + " Row hasn't been found and getting of it's elements texts cannot be proceeded.";
                } else {
                    message = "Row hasn't been found and getting of it's elements texts cannot be proceeded.";
                }

            }
            return new Result<ArrayList<String>>(status, result, message);
        }

        public Cell cell (String columnName) {
            if (rowFound) {
                Result<Integer> position = getPositionOfColumnNamed(columnName);
                if (position.getStatus().getBooleanValue()) {
                    int column = position.getValue();
                    return new Cell(row, column);
                } else {
                    return new Cell(false);
                }
            } else {
                return new Cell(false);
            }
        }
        public Cell cell (int columnPosition) {
            if (rowFound) {
                return new Cell(row, columnPosition);
            } else {
                return new Cell(false);
            }
        }
        public Cell lastCell () {
            if (rowFound) {
                Result<Integer> tableSize = tableSize();
                if (tableSize.getStatus().getBooleanValue()) {
                    return new Cell(row, tableSize.getValue());
                } else {
                    return new Cell(false);
                }

            } else {
                return new Cell(false);
            }
        }
        public Cell firstCell () {
            if (rowFound) {
                return new Cell(row, 1);
            } else {
                return new Cell(false);
            }
        }

        private int row = 0;
        private boolean rowFound = true;
        private Row (int row) {
            this.row = row;
            this.rowFound = true;
        }
        private Row (boolean found) {
            this.rowFound = found; // for case if not found
        }
    }

    public class Cell {

        public Cell (int row, int column) {
            this.row = row;
            this.column = column;
            this.cellFound = true;
            this.cellXPath = tableLocation + "/tbody/tr[" + this.row + "]/td[" + this.column + "]";
        }
        public Cell (boolean found) {
            this.cellFound = found;
        }

        public Result<Boolean> hasText (String text) {
            Boolean result = null;
            boolean status = false;
            String message;
            if (cellFound) {
                try {
                    result = this.getText().getValue().equals(text);
                    status = true;
                    message = "Cell found and checking if it has text '"+text+"' was successful.";
                } catch (WebDriverException e) {
                    message = "Cell found but checking if it has text '"+text+"' wasn't successful. Using xpath = '"
                            + cellXPath + "'.\nCause: " + e.getClass().getSimpleName();

                }
            } else {

                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getStatus().getBooleanValue()) {
                    message = emptiness.getDescription() + " Cell hasn't been found and checking if it has text '"+text+"' cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and checking if it has text '"+text+"' cannot be proceeded.";;
                }
            }

            return new Result<Boolean>(status, result, message);
        }
        public Result<Boolean> containsText (String text) {
            Boolean result = null;
            boolean status = false;
            String message;
            if (cellFound) {
                try {
                    result = this.getText().getValue().contains(text);
                    status = true;
                    message = "Cell found and checking if it contains text '"+text+"' was successful.";
                } catch (WebDriverException e) {
                    message = "Cell found but checking if it contains text '"+text+"' wasn't successful. Using xpath = '"
                            + cellXPath + "'.\nCause: " + e.getClass().getSimpleName();
                }
            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Cell hasn't been found and checking if it contains text '"+text+"' cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and checking if it contains text '"+text+"' cannot be proceeded.";
                }
            }
            return new Result<Boolean>(status, result, message);
        }

        public Result<Void> clickInto() {
            boolean status = false;
            String message;

            if (cellFound) {
                String path = cellXPath + "/*";
                if  (clickElement(path)) {
                    message = "Successfully clicked into cell.";
                    status = true;
                } else {
                    Result<Boolean> emptiness = isEmpty();
                    if (emptiness.getValue()) {
                        message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and clicking into it cannot be proceeded.";
                    } else {
                        message = "Click to cell wasn't successful. Using xpath = '" + path + "'.";
                    }
                }
            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and clicking into it cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and clicking into it cannot be proceeded.";
                }
            }
            return new Result<Void>(status, null, message);
        }
        public Result<Void> click() {
            boolean status = false;
            String message;

            if (cellFound) {
                String path = cellXPath;
                if  (clickElement(path)) {
                    message = "Successfully clicked to cell.";
                    status = true;
                } else {
                    Result<Boolean> emptiness = isEmpty();
                    if (emptiness.getValue()) {
                        message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and clicking into it cannot be proceeded.";
                    } else {
                        message = "Click to cell wasn't successful. Using xpath = '" + path + "'.";
                    }

                }
            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and click into it cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and click into it cannot be proceeded.";
                }

            }
            return new Result<Void>(status, null, message);
        }
        public Result<Void> click(String text) {
            //clicks going inside the cell to element that contains text
            boolean status = false;
            String message;

            if (cellFound) {
                String path = cellXPath + "//*[contains(text(), '" + text + "')]";
                if  (clickElement(path)) {
                    message = "Successfully clicked to cell with text '" + text + "'.";
                    status = true;
                } else {
                    Result<Boolean> emptiness = isEmpty();
                    if (emptiness.getValue()) {
                        message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and clicking into it cannot be proceeded.";
                    } else {
                        message = "Click to cell wasn't successful. Using xpath = '" + path + "'.";
                    }
                }
            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and click into it cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and click into it cannot be proceeded.";
                }
            }
            return new Result<Void>(status, null, message);
        }
        public Result<Void> setText(String text) {

            boolean status = false;
            String message;

            if (cellFound) {
                String path = cellXPath + "//*[contains(text(), '" + text + "')]";
                if  (setElementsText(path, text)) {
                    message = "Successfully set text '" + text + "' to cell.";
                    status = true;
                } else {
                    Result<Boolean> emptiness = isEmpty();
                    if (emptiness.getValue()) {
                        message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and setting text into it cannot be proceeded.";
                    } else {
                        message = "Setting text '" + text + "' to cell wasn't successful. Using xpath = '" + path + "'.";
                    }
                }
            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and setting text into it cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and setting text into it cannot be proceeded.";
                }
            }
            return new Result<Void>(status, null, message);
        }
        public Result<String> getText() {
            String result = "";
            boolean status = false;
            String message;

            if (cellFound) {
                try {
                    result = getElementsText(cellXPath);
                    status = true;
                    message = "Cell found and getting of it's text was successful.";
                } catch (WebDriverException e) {
                    message = "Cell found but getting of it's text wasn't successful. Using xpath = '"
                            + cellXPath + "'.\nCause: " + e.getClass().getSimpleName();
                }
            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and getting text from it cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and getting text from it cannot be proceeded.";
                }
            }
            return new Result<String>(status, result, message);
        }
        public Result<String> getInputValue () {
            String result = "";
            boolean status = false;
            String message;

            if (cellFound) {
                String path = cellXPath + "//input";
                Result<String> attribute = getElementsValue(path);
                if (attribute.getStatus().getBooleanValue()) {

                    result = attribute.getValue();
                    status = true;
                    message = attribute.getDescription();

                } else {
                    message = attribute.getDescription();
                }

            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and getting value from it cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and getting value from it cannot be proceeded.";
                }
            }
            return new Result<String>(status, result, message);

        }
        public Result<String> getAttribute(String attributeName) {
            String result = "";
            boolean status = false;
            String message;

            if (cellFound) {
                Result<String> attribute = getElementsAttribute(cellXPath, attributeName);
                if (attribute.getStatus().getBooleanValue()) {

                    result = attribute.getValue();
                    status = true;
                    message = attribute.getDescription();

                } else {

                    message = attribute.getDescription();
                }

            } else {
                Result<Boolean> emptiness = isEmpty();
                if (emptiness.getValue()) {
                    message = emptiness.getDescription() + " Table is empty. Cell hasn't been found and getting attribute from it cannot be proceeded.";
                } else {
                    message = "Cell hasn't been found and getting attribute from it cannot be proceeded.";
                }
            }
            return new Result<String>(status, result, message);
        }

        private int row = 0;
        private int column = 0;
        private boolean cellFound = true;
        private String cellXPath = "";
    }

    public class TableFilter {
        private int col = 0;
        private boolean isUsed = false;
        private TableFilter (int column) {
            this.col = column;
        }

        private void use (String text) {

            try {
                String tag = driver.findElement(By.xpath(tableLocation + "/thead/*[2]/*["+col+"]/*")).getTagName();
                String loading = "//*[contains(@class, 'grid-view') and contains(@class, 'grid-view-loading')]";
                if (tag.equals("input")) {
                    if (!getElementsValue(tableLocation + "/thead/tr[2]/td["+col+"]//input").getValue().equalsIgnoreCase(text)) {
                        setElementsText(tableLocation + "/thead/tr[2]/td[" + col + "]//input", text);
                        if (isElementDisappears(loading)) {
                            sendKeyToElement((tableLocation + "/thead/tr[2]/td[" + col + "]//input"), Keys.RETURN);
                        }

                    }
                } else if (tag.equals("select")) {
                    Select select = new Select (driver.findElement(By.xpath(tableLocation + "/thead/tr[2]/td["+col+"]/*")));
                    try {
                        select.selectByVisibleText(text);
                    } catch (NoSuchElementException ex) {
                        String op = "";
                        boolean foundOther = false;
                        for (WebElement option : select.getOptions()) {
                            if (option.getText().toLowerCase().contains(text.toLowerCase())) {
                                op = option.getText();
                                select.selectByVisibleText(option.getText());
                                foundOther = true;
                                break;
                            }
                        }
                        if (foundOther) {
                            System.out.println("Found option that probably matches '"+op+"' and selected instead of '"+text+"'");
                        } else {
                            System.err.println("No such option '"+text+"' found.");
                        }
                    }
                }
                pause(Timeouts.MILLIS_MEDIUM);
                isUsed = true;
                if (!isElementDisappears(loading)) {
                    System.err.println("Loading animation not disappears.");
                }
            } catch (NoSuchElementException e) {
                System.err.println("Unable to use filter with xpath '"+tableLocation + "/thead/tr[2]/td["+col+"]/*"+"'");
            }

        }

        private boolean isUsed () {
            return isUsed;
        }
    }

    protected void setEmptinessIndicator (String xpathToEmpty) {
        this.emptinessIndicator = xpathToEmpty;
        this.isIndicated = true;

    }

    //private helpers
    private ArrayList<String> deleteFromBegin(ArrayList<String> list, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount for deleting from list of strings should be positive.");
        }
        if (list.size() >= amount) {
            for (int i = 0; i < amount; i ++) {
                list.remove(0);
            }
        } else {
            int size = list.size();
            for (int i = 0; i < size; i ++) {
                list.remove(0);
            }
        }
        return list;
    }
    private Result<ArrayList<String>> getTextsOfElements(String xpath) {
        ArrayList<String> result = new ArrayList<String>();
        boolean status;
        String message;
        try {
            for (WebElement element : getElementsByXpath(xpath)) {
                result.add(element.getText());
            }
            status = true;
            message = "Successfully got text from elements.";
        } catch (WebDriverException e) {
            message = e.getClass().getSimpleName() + " occurs when I tried to get texts of elements. Using xpath = '"+xpath+"'.";
            status = false;
        }
        return new Result<ArrayList<String>>(status, result, message);
    }
    private Result<String> getElementsValue (String xpath) {
        String result = "";
        boolean status = false;
        String message;

        if (isElementAppears(xpath)) {
            try {
                result = driver.findElement(By.xpath(xpath)).getAttribute("value");
                status = true;
                message = "Got value from element successfully.";
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        result =  driver.findElement(By.xpath(xpath)).getAttribute("value");
                        status = true;
                        message = "Got value from element successfully.";
                    } catch (WebDriverException ex) {
                        message = "Unable to get element's attribute value. Using xpath = '" + xpath + "'.\nCause: " + ex.getClass().getSimpleName() + ".";
                    }
                } else {
                    message = "Element's value hasn't been obtained.";
                }
            }
        } else {
            message = "Element doesn't appears and it's value cannot be obtained. Using xpath = '"+
                    xpath+"'.";
        }
        return new Result<String>(status, result, message);

    }
    private Result<String> getElementsAttribute (String xpath, String attributeName) {

        String result = "";
        boolean status = false;
        String message;

        if (isElementAppears(xpath)) {
            try {
                result = driver.findElement(By.xpath(xpath)).getAttribute(attributeName);
                status = true;
                message = "Got attribute '"+attributeName+"' value from element successfully.";
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        result =  driver.findElement(By.xpath(xpath)).getAttribute(attributeName);
                        status = true;
                        message = "Got attribute '"+attributeName+"' value from element successfully.";
                    } catch (WebDriverException ex) {
                        message = "Unable to get element's attribute '" + attributeName + "' value. Using xpath = '" + xpath + "'.\nCause: " + ex.getClass().getSimpleName() + ".";
                    }
                } else {
                    message = "Element's attribute '" + attributeName + "' hasn't been obtained.";
                }
            }
        } else {
            message = "Element doesn't appears and it's attribute '"+attributeName+"' value cannot be obtained. Using xpath = '"+
                    xpath+"'.";
        }
        return new Result<String>(status, result, message);
    }
    private boolean isDisplayedElementXpath (String xpath) {
        try {
            return driver.findElement(By.xpath(xpath)).isDisplayed();
        } catch (WebDriverException ex) {
            return false;
        }
    }
    private boolean isElementDisappears (String xpath) {
        boolean status = true;
        int retry = 0;
        while (isDisplayedElementXpath(xpath)) {
            pause(Timeouts.MILLIS_SHORT);
            retry++;
            if (retry > (1000 / Timeouts.MILLIS_SHORT) * Timeouts.SEC_MEDIUM) { // (1000 / Millis) used because wait timeout used in SECONDS but pause in MILLISECONDS
                // in this case MILLIS_SHORT is period for asking if element displayed or not
                // and (1000 / Timeouts.MILLIS_****) * Timeouts.SEC_**** is amount of repetitions that loop should do
                // to wait for condition for Timeouts.SEC_**** seconds.
                status = false;
                System.err.println("Element with xpath '"+xpath+"' doesn't disappears.");
                break;
            }
        }
        return status;
    }
    private boolean isElementAppears (String xpath) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return driver.findElement(By.xpath(xpath)).isDisplayed();
        } catch (WebDriverException e) {
            if (! (e instanceof TimeoutException)) {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                    return driver.findElement(By.xpath(xpath)).isDisplayed();
                } catch (WebDriverException ex) {
                    System.err.println("Element with xpath '"+xpath+"' doesn't appears.");
                    return false;
                }
            } else {
                System.err.println("Element with xpath '"+xpath+"' doesn't appears.");
                return false;
            }
        }
    }
    private List<WebElement> getElementsByXpath (String xpath) {
        if (isElementAppears(xpath)) {
            try {
                return driver.findElements(By.xpath(xpath));
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        return driver.findElements(By.xpath(xpath));
                    } catch (WebDriverException ex) {
                        return new ArrayList<WebElement>();
                    }
                } else {
                    return new ArrayList<WebElement>();
                }
            }
        } else {
            return new ArrayList<WebElement>();
        }

    }
    private String getElementsText (String xpath) {
        if (isElementAppears(xpath)) {
            try {
                return driver.findElement(By.xpath(xpath)).getText();
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        return driver.findElement(By.xpath(xpath)).getText();
                    } catch (WebDriverException ex) {
                        System.err.println("Unable to get element's value.");
                        return "";
                    }
                } else {
                    System.err.println("Element's text hasn't been obtained.");
                    return "";
                }
            }
        } else {
            System.err.println("Element's text hasn't been obtained.");
            return "";
        }
    }
    private boolean sendKeyToElement(String xpath, Keys key) {
        boolean textHasBeenSet = false;
        if (isElementAppears(xpath)) {
            try {
                driver.findElement(By.xpath(xpath)).sendKeys(key);
                textHasBeenSet = true;
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        driver.findElement(By.xpath(xpath)).sendKeys(key);
                        textHasBeenSet = true;
                    } catch (WebDriverException ex) {
                        System.err.println("Unable to set text to an element.");
                    }
                }
            }
        }
        return textHasBeenSet;
    }
    private boolean setElementsText (String xpath, String text) {
        boolean textHasBeenSet = false;
        if (isElementAppears(xpath)) {
            try {
                driver.findElement(By.xpath(xpath)).clear();
                driver.findElement(By.xpath(xpath)).sendKeys(text);
                textHasBeenSet = true;
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        driver.findElement(By.xpath(xpath)).clear();
                        driver.findElement(By.xpath(xpath)).sendKeys(text);
                        textHasBeenSet = true;
                    } catch (WebDriverException ex) {
                        System.err.println("Unable to set text to an element.");
                    }
                }
            }
        }
        return textHasBeenSet;
    }
    private boolean clickElement (String xpath) {
        boolean clicked = false;
        if (isElementAppears(xpath)) {
            try {
                driver.findElement(By.xpath(xpath)).click();
                clicked = true;
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        driver.findElement(By.xpath(xpath)).click();
                        clicked = true;
                    } catch (WebDriverException ex) {
                        System.err.println("Unable to click on an element.");
                    }
                }
            }
        }
        return clicked;
    }

    private void pause (int duration_millis) {
        try {
            Thread.sleep(duration_millis);
        } catch (InterruptedException e) {
            //do nothing
        }
    }

    //vars
    private WebDriver driver;
    private WebDriverWait wait;
    private String tableLocation;
    private String tableHeadPath;
    private int bodyRowsShift = 0;
    private String emptinessIndicator = "//*[@class = 'empty']";
    private boolean isIndicated = false;
}


