package elements.deprecated;

import utils.Timeouts;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
/**
 * This class designed to provide APIs to handle different web tables.
 * Works using By.xpath() approach.
 * */
public class WebTable {

    public  WebTable(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Timeouts.SEC_MEDIUM);
        this.tableLocation = "//table";
        this.tableHeadPath = tableLocation + "/thead/*[1]/*";
    }
    public  WebTable(WebDriver driver, String pathToTable) {
        this.driver = driver;
        this.tableLocation = pathToTable;
        this.tableHeadPath = tableLocation + "/thead/*[1]/*";
        wait = new WebDriverWait(driver, Timeouts.SEC_MEDIUM);
    }
    public  WebTable(WebDriver driver, String pathToTable, String pathToHeadElements, int bodyRowsShift) {
        this.driver = driver;
        this.bodyRowsShift = bodyRowsShift;
        this.tableLocation = pathToTable;
        this.tableHeadPath = tableLocation + pathToHeadElements;
        wait = new WebDriverWait(driver, Timeouts.SEC_MEDIUM);
    }

    protected void setEmptinessIndicator (String xpathToEmpty) {this.emptinessIndicator = xpathToEmpty;}

    public ArrayList<String> getAllColumnNames() {
        ArrayList<String> names = new ArrayList<String>(tableSize());
        for (WebElement name : getElementsByXpath(tableHeadPath)) {
            names.add(name.getText().replaceAll("\\n", "/"));
        }
        return names;
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
        if (tableSize() > 0) {
            return new Column(tableSize());
        } else {
            return new Column(false);
        }

    }
    public Column inFirstColumn () {
        if (tableSize() > 0) {
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
    public boolean isEmpty() {
        if (isElementAppears(tableLocation)) {
            return isDisplayedElementXpath(emptinessIndicator);
        } else {
            return false;
        }
    }
    public int tableSize() {
        try {
            return driver.findElements(By.xpath(tableLocation + "/*[1]/*[1]/*")).size();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
    public int getPositionOfColumnNamed (String columnName) {
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
            return col;
        } else {
            System.err.println("Unable to locate column '"+columnName+"' (Column not found)");
            System.out.println("Names of actual columns: " + getAllColumnNames());
            return 0;
        }
    }
    public boolean checkAppearance () {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableLocation)));
            return driver.findElement(By.xpath(tableLocation)).isDisplayed();
        } catch (TimeoutException e) {
            System.err.println("Cannot find table element until time out.");
            return false;
        }
    }

    public class Column {
        private int column = 0;
        private boolean columnFound = true;
        private Column (int column) {
            this.column = column;
        }
        private Column (boolean found) {
            this.columnFound = found; // for case if not found
        }

        public Row findRowWithExactText(String text) {
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
        public Row findRowContainsText(String text) {
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
        public List<WebElement> getElements () {
            if (columnFound) {
                try {
                    return driver.findElements(By.xpath(tableLocation + "/tbody/tr/td["+column+"]"));
                } catch (NoSuchElementException e) {
                    System.err.println("Not found elements using xpath '"+tableLocation + "/tbody/tr/td["+column+"]"+"'");
                    return new ArrayList<WebElement>();
                }
            } else {
                return new ArrayList<WebElement>();
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
        public void findAndClickToCellWithText (String cellText) {
            if (columnFound) {
                clickElement(tableLocation + "/tbody/tr[contains(text(), '" + cellText + "')]/td[" + column + "]/*");
            }
        }
        public void clickToSort() {
            clickElement(tableLocation + tableHeadPath + "[" + column + "]");
        }
    }

    public class Row {
        private int row = 0;
        private boolean rowFound = true;
        private Row (int row) {
            this.row = row;
            this.rowFound = true;
        }
        private Row (boolean found) {
            this.rowFound = found; // for case if not found
        }
        public List<WebElement> getElements () {
            if (rowFound) {
                try {
                    return driver.findElements(By.xpath(tableLocation + "/tbody/tr["+row+"]/td"));
                } catch (NoSuchElementException e) {
                    return new ArrayList<WebElement>();
                }
            } else {
                return new ArrayList<WebElement>();
            }

        }
        public void clickElementInColumnNamed(String columnName) {
            if (rowFound) {
                int column = getPositionOfColumnNamed(columnName);
                try {
                    clickElement(tableLocation + "/tbody/tr["+row+"]/td["+column+"]/*");
                } catch (WebDriverException e) {
                    System.err.println("Unable to find element using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+column+"]/*' to click on it");
                }
            }

        }
        public void clickElementInColumnNumber(int columnNumber) {
            if (rowFound) {
                try {
                    clickElement(tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*");
                } catch (NoSuchElementException e) {
                    System.err.println("No such element found using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*' to click on it");
                }
            }
        }
        public void clickElementInColumnNamed(String columnName, String text) {
            if (rowFound) {
                int column = getPositionOfColumnNamed(columnName);
                try {
                    clickElement(tableLocation + "/tbody/tr["+row+"]/td["+column+"]/*[contains(text(), '"+text+"')]");
                } catch (NoSuchElementException e) {
                    System.err.println("No such element found using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+column+"]/*[contains(text(), '"+text+"')]' to click on it");
                }
            }
        }
        public void clickElementInColumnNumber(int columnNumber, String text) {
            if (rowFound) {
                try {
                    clickElement(tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*[contains(text(), '"+text+"')]");
                } catch (NoSuchElementException e) {
                    System.err.println("No such element found using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*[contains(text(), '"+text+"')]' to click on it");
                }
            }
        }
        public void clickElementInLastColumn() {
            if (rowFound) {
                int columnNumber = tableSize();
                try {
                    clickElement(tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*");
                } catch (NoSuchElementException e) {
                    System.err.println("No such element found using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*' to click on it");
                }
            }
        }
        public void clickElementInLastColumn(String text) {
            if (rowFound) {
                int columnNumber = tableSize();
                try {
                    clickElement(tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*[contains(text(), '"+text+"')]");
                } catch (NoSuchElementException e) {
                    System.err.println("No such element found using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+columnNumber+"]/*[contains(text(), '"+text+"')]' to click on it");
                }
            }
        }
        public String getTextFromColumnNamed(String columnName) {
            if (rowFound) {
                int column = getPositionOfColumnNamed(columnName);
                if (column > 0) {
                    try {
                        return driver.findElement(By.xpath(tableLocation + "/tbody/tr[" + row + "]/td[" + column + "]")).getText();
                    } catch (WebDriverException e) {
                        System.err.println("Unable get cell's text using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+column+"]'");
                        return "";
                    }
                } else {
                    System.err.println("Text from column '"+columnName+"' has not been got.");
                    return "";
                }
            } else {
                System.err.println("Unable to locate proper cell to get it's text (Row not found).");
                return "";
            }
        }
        public String getTextFromColumnNumber(int column) {
            if (rowFound) {
                try {
                    return driver.findElement(By.xpath(tableLocation + "/tbody/tr["+row+"]/*[local-name() = 'td' or local-name() = 'th']["+column+"]")).getText();
                } catch (WebDriverException e) {
                    System.err.println("Unable get cell's text using xpath '" +tableLocation + "/tbody/tr["+row+"]/*[local-name() = 'td' or local-name() = 'th']["+column+"]'");
                    return "";
                }
            } else {
                return "";
            }

        }
        public WebElement getElementFromColumnNamed(String columnName) {
            if (rowFound) {
                int column = getPositionOfColumnNamed(columnName);
                try {
                    return driver.findElement(By.xpath(tableLocation + "/tbody/tr[" + row + "]/td[" + column + "]/*"));
                } catch (NoSuchElementException e) {
                    System.err.println("No such element found using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+column+"]/*'");
                    return driver.findElement(By.xpath(tableLocation));
                }
            } else {
                return driver.findElement(By.xpath(tableLocation));
            }

        }
        public WebElement getElementFromColumnNumber(int column) {
            if (rowFound) {
                try {
                    return driver.findElement(By.xpath(tableLocation + "/tbody/tr[" + row + "]/td[" + column + "]/*"));
                } catch (NoSuchElementException e) {
                    System.err.println("No such element found using xpath '"+tableLocation + "/tbody/tr["+row+"]/td["+column+"]/*'");
                    return driver.findElement(By.xpath(tableLocation));
                }
            } else {
                return driver.findElement(By.xpath(tableLocation));
            }

        }

        public WebElement element (){
            try {
                return driver.findElement(By.xpath(tableLocation + "/tbody/tr["+row+"]"));
            } catch (WebDriverException e) {
                System.err.println("Unable to get Element of row.");
                return null;
            }
        }
        public String getAttribute(String attributeName) {
            try {
                return element().getAttribute(attributeName);
            } catch (NullPointerException e) {
                return "";
            }
        }

        public Cell cell (String columnName) {
            if (rowFound) {
                int column = getPositionOfColumnNamed(columnName);
                return new Cell(row, column);
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
                return new Cell(row, tableSize());
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
    }

    public class Cell {
        private int row = 0;
        private int column = 0;
        private boolean cellFound = true;
        private String cellXPath = "";
        public Cell (int row, int column) {
            this.row = row;
            this.column = column;
            this.cellFound = true;
            this.cellXPath = tableLocation + "/tbody/tr[" + this.row + "]/td[" + this.column + "]";
        }
        public Cell (boolean found) {
            this.cellFound = found;
        }

        public boolean hasText (String text) {
            return this.getText().equals(text);
        }
        public boolean containsText (String text) {
            return this.getText().contains(text);
        }
        public String getText() {
            if (cellFound && (column != 0)) {
                return getElementsText(cellXPath);
            } else {
                System.err.println("Unable to locate proper cell to get it's text (Cell not found).");
                return "";
            }
        }
        public void click() {
            if (cellFound && (column != 0)) {
                clickElement(cellXPath + "/*");
            } else {
                System.err.println("Unable to locate proper cell to click it (Cell not found).");
            }
        }
        public void click(String text) {
            if (cellFound && (column != 0)) {
                clickElement(cellXPath + "//*[contains(text(), "+text+")]");
            } else {
                System.err.println("Unable to locate proper cell to click it (Cell not found).");
            }
        }
        public WebElement element (){
            try {
                return driver.findElement(By.xpath(cellXPath));
            } catch (WebDriverException e) {
                System.err.println("Unable to get Element of cell.");
                return null;
            }
        }
        public void typeText(String text) {
            if (cellFound && (column != 0)) {
                setElementsText(cellXPath + "//input", text);
            } else {
                System.err.println("Unable to locate proper cell to set text to it (Cell not found).");
            }
        }
        public String getInputValue () {
            if (cellFound && (column != 0)) {
                return getElementsValue(cellXPath + "//input");
            } else {
                System.err.println("Unable to locate proper cell to click it (Cell not found).");
                return "";
            }
        }
        public String getAttribute(String attributeName) {
            try {
                return element().getAttribute(attributeName);
            } catch (NullPointerException e) {
                return "";
            }
        }

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
                    if (!getElementsValue(tableLocation + "/thead/tr[2]/td["+col+"]//input").equalsIgnoreCase(text)) {
                        setElementsText(tableLocation + "/thead/tr[2]/td[" + col + "]//input", text);
                        if (isElementDisappears(loading)) {
                            setKeysToElement((tableLocation + "/thead/tr[2]/td["+col+"]//input"), Keys.RETURN);
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

    //vars
    private WebDriver driver;
    private WebDriverWait wait;
    private String tableLocation;
    private String tableHeadPath;
    private int bodyRowsShift = 0;
    private String emptinessIndicator = "//*[@class = 'empty']";

    //private helpers
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
                // in this case MILLIS_SHORT is period for asking if element isDisplayed or not
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
    private void setKeysToElement(String xpath, Keys keys) {
        if (isElementAppears(xpath)) {
            try {
                driver.findElement(By.xpath(xpath)).sendKeys(keys);
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        driver.findElement(By.xpath(xpath)).sendKeys(keys);
                    } catch (WebDriverException ex) {
                        System.err.println("Unable to set keys to an element.");
                    }
                }
            }
        }
    }
    private void setElementsText (String xpath, String text) {
        boolean textSet = false;
        if (isElementAppears(xpath)) {
            try {
                driver.findElement(By.xpath(xpath)).clear();
                driver.findElement(By.xpath(xpath)).sendKeys(text);
                textSet = true;
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        driver.findElement(By.xpath(xpath)).clear();
                        driver.findElement(By.xpath(xpath)).sendKeys(text);
                        textSet = true;
                    } catch (WebDriverException ex) {
                        System.err.println("Unable to set text to an element.");
                    }
                }
            }
        }
        if (!textSet) {
            System.err.println("");
        }
    }
    private void clickElement (String xpath) {
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
        if (!clicked) {
            System.err.println("Element hasn't been clicked.");
        }
    }
    private String getElementsValue (String xpath) {
        if (isElementAppears(xpath)) {
            try {
                return driver.findElement(By.xpath(xpath)).getAttribute("value");
            } catch (WebDriverException e) {
                if (isElementAppears(xpath)) {
                    try {
                        return driver.findElement(By.xpath(xpath)).getAttribute("value");
                    } catch (WebDriverException ex) {
                        System.err.println("Unable to get element's value.");
                        return "";
                    }
                } else {
                    System.err.println("Element's value hasn't been obtained.");
                    return "";
                }
            }
        } else {
            System.err.println("Element's value hasn't been obtained.");
            return "";
        }

    }
    private void pause (int duration_millis) {
        try {
            Thread.sleep(duration_millis);
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}
