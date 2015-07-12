package basePageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * This class designed to provide basis any element of page. It supports all common interactions with elements
 * Elements means not only fields or buttons - in this case class designed to be extended especially by forms.
 * But if required - any page element can extend this class to use this API.
 */
public class BasePageElement {
    protected WebDriverWait wait;
    protected WebDriver driver;

    public BasePageElement(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, defaultWaitTimeout);
    }

    private int  defaultWaitTimeout = 10;

    protected void setDefaultWaitTimeout(int seconds) {
        if (seconds < 0) {
            System.err.println("Timeout has been set wrongly. Using default wait timeout.");
        } else {
            this.defaultWaitTimeout = seconds;
        }
    }
    private boolean autoClear = false;
    protected void setAutoClear (boolean isAutoClearRequired)
    {
        this.autoClear = isAutoClearRequired;
    }
    private boolean useWaitAfterTyping = false;
    private int waitDurationAfterTyping;
    public void setWaitDurationAfterTyping(int millis) {
        if  (millis > 0) {
            this.waitDurationAfterTyping = millis;
            useWaitAfterTyping = true;
        } else {
            useWaitAfterTyping = false;
            System.err.println("Wait after typing has been set wrongly. Using no wait after typing.");
        }

    }
    private boolean typingRetried = false;
    private void setTypingRetried (boolean status) {
        this.typingRetried = status;
    }
    private void switchTypingRetried () {this.typingRetried = !typingRetried;}
    private boolean getTypingRetried () {
        return typingRetried;
    }

    protected boolean isElementEnabled (WebElement element) {
        if (isElementAppears(element)) {
            try {
                return element.isEnabled();
            } catch (StaleElementReferenceException e) {
                if (isElementAppears(element)) {
                    try {
                        return element.isEnabled();
                    } catch (WebDriverException ex) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
    private void  clearElement (WebElement element) {
        try {
            element.clear();
        } catch (StaleElementReferenceException e) {
            try {
                if (isElementAppears(element)) {
                    element.clear();
                } else {
                    System.err.println("Element is not appears to clear it.");
                }
            } catch (StaleElementReferenceException ex) {
                System.err.println("Stale reference to element to clear it.");
            }
        }

    }
    private void sendKeysTo(WebElement element, String text) {
        try {
            element.sendKeys(text);
        } catch (StaleElementReferenceException e) {
            try {
                if (isElementAppears(element)) {
                    element.sendKeys(text);
                } else {
                    System.err.println("Element is not appears to send keys to it.");
                }
            } catch (StaleElementReferenceException ex) {
                System.err.println("Stale reference to element to send key to it.");
            }
        }

    }
    protected void clearElementsText (WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            if (element.getAttribute("value").length() != 0 || element.getAttribute("value") == null) {
                System.err.println("ERROR: I cleared element but its value stay still in it.");
            }
        } catch (TimeoutException e) {
            System.err.println("ERROR: Cannot see an element. I waited for it to be visible but reached time out.");
        }
    }
    protected void  setElementsText(WebElement element, String text){
        wait = new WebDriverWait(driver, 2);
        if (isElementAppears(element)) {
            if (isElementEnabled(element)) {
                clearElement(element);
                sendKeysTo(element, text);
                if (useWaitAfterTyping) {
                    pause(waitDurationAfterTyping);
                }
                if (!autoClear) {
                    try {
                        wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
                        if (getTypingRetried()) {
                            System.out.println("Retyping succeeded.");
                            switchTypingRetried();
                        }
                    } catch (TimeoutException ex) {
                        System.err.println("WARNING: Actual value '" + getElementsValue(element) + "' not become equals text '"+text+"' that I send to an element.");
                        if (!typingRetried) {
                            System.out.println("Trying to retype text '"+text+"'...");
                            setTypingRetried(true);
                            setElementsText(element, text);
                        } else {
                            System.err.println("WARNING: Actual value '" + getElementsValue(element) + "' not become equals text '"+text+"' that I send to an element.");
                        }
                    }
                }

            } else {
                System.err.println("ERROR: Element that should accept keys is disabled!");
            }
        } else {
            System.err.println("Element not appears to set text to it.");
        }

    }
    protected void  uncheckedSetText(WebElement element, String text) {
        if (isElementAppears(element)) {
            if (isElementEnabled(element)) {
                clearElement(element);
                sendKeysTo(element, text);
            } else {
                System.err.println("ERROR: Element that should accept keys is disabled!");
            }
        } else {
            System.err.println("Element not appears to set text to it.");
        }
    }
    protected void  setElementsTextUnchecked (WebElement element, String text){
        wait = new WebDriverWait(driver, 2);
        if (isElementAppears(element)) {
            if (isElementEnabled(element)) {
                clearElement(element);
                sendKeysTo(element, text);
                if (useWaitAfterTyping) {
                    pause(waitDurationAfterTyping);
                }
            } else {
                System.err.println("ERROR: Element that should accept keys is disabled!");
            }
        } else {
            System.err.println("Element not appears to set text to it.");
        }

    }
    protected void  setKeysToElement(WebElement element, Keys keys) {
        try {
            element.sendKeys(keys);
        } catch (WebDriverException e) {
            if (isElementAppears(element)) {
                try {
                    element.sendKeys(keys);
                } catch (WebDriverException ex) {
                    System.err.println("EXCEPTION: Unable to send keys to an element.");
                }
            }
        }
    }
    protected void  setKeysToElement (By by, Keys keys) {
        try {
            driver.findElement(by).sendKeys(keys);
        } catch (WebDriverException e) {
            if (isElementAppears(driver.findElement(by))) {
                try {
                    driver.findElement(by).sendKeys(keys);
                } catch (WebDriverException ex) {
                    System.err.println("EXCEPTION: Unable to send keys to an element.");
                }
            }
        }
    }
    protected void waitForElementVisible(WebElement element){
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            System.err.println("WAIT TIMEOUT: I waited for element's visibility but reached timeout");
        }
    }
    protected void clickElement(WebElement clickableElement) {
        try {
            if (isElementAppears(clickableElement)) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
                    clickableElement.click();
                } catch (WebDriverException e) {
                    if (isElementAppears(clickableElement)) {
                        wait.until(ExpectedConditions.elementToBeClickable(clickableElement));
                        clickableElement.click();
                    }
                }

            } else {
                System.err.println("Element is not appears.");
            }


        } catch (WebDriverException e) {
            System.err.println("EXCEPTION: Cannot click an element. I waited for it to be clickable but got exception: " + e.toString());

        }

    }
    protected void unsafeClick (WebElement element) {
        try {
            element.click();
        } catch (WebDriverException ex) {
            try {
                pause(1000);
                element.click();
            } catch (WebDriverException e) {
                System.err.println("Unsafe click failed.");
            }
        }
    }
    protected void selectValueInDropdown(WebElement dropdown, String value){
        try {
            Select select = new Select(dropdown);
            select.selectByVisibleText(value);
        } catch (NoSuchElementException e) {
            System.err.println("ERROR: Cannot find such <select> element.");
        }

    }

    protected String  getElementsPlaceholder(WebElement element) {
        if (isElementDisplayed(element)) {
            if (element.getTagName().equals("input")) {
                return element.getAttribute("placeholder");
            } else {
                System.err.println("WARNING: Element is not input. Cannot get it's placeholder.");
                return "";
            }
        } else {
            System.err.println("ERROR: Element is not present");
            return "";
        }
    }
    protected String  getElementsValue(WebElement element) {
        if (isElementAppears(element)) {
            try {
                String value = element.getAttribute("value");
                if (value.length() == 0) {
                    try {Thread.sleep(500);} catch (InterruptedException e) {}
                    value = element.getAttribute("value");
                }
                return value;
            } catch (WebDriverException e) {
                if (isElementAppears(element)) {
                    String value = element.getAttribute("value");
                    if (value.length() == 0) {
                        try {Thread.sleep(500);} catch (InterruptedException ex) {}
                        value = element.getAttribute("value");
                    }
                    return value;
                } else {
                    System.err.println("ERROR: Element is not present");
                    return "";
                }
            }

        } else {
            System.err.println("ERROR: Element is not present");
            return "";
        }
    }
    protected String  getElementsText(WebElement element) {
        try {
            if (isElementAppears(element)) {
                return element.getText();
            } else {
                System.err.println("Cannot get text of the element because it doesn't appears.");
                return "";
            }
        } catch (StaleElementReferenceException e) {
            try {
                if (isElementAppears(element)) {
                    return element.getText();
                } else {
                    System.err.println("Cannot get text of the element because it doesn't appears.");
                    return "";
                }
            } catch (WebDriverException ex) {
                System.err.println("EXCEPTION: Cannot get text of the element.");
                return "";
            }
        }

    }
    protected String  getCurrentSelectedValue(WebElement dropdown) {
        try {
            Select select = new Select(dropdown);
            return select.getFirstSelectedOption().getText();
        } catch (NoSuchElementException e) {
            System.err.println("ERROR: Cannot find such <select> element.");
            return "";
        }

    }

    protected boolean isElementWithTextDisplayed(List<WebElement> webElementList, String requiredText) {
        boolean result = false;
        try {
            for (WebElement element : webElementList) {
                if (element.isDisplayed() && element.getText().equalsIgnoreCase(requiredText)) {
                    result = true;
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("ERROR: Cannot find required elements at current page: " + driver.getCurrentUrl());
        }
        return result;
    }
    protected boolean isElementDisplayed(WebElement element) {
        if (element != null) {
            try {
                return element.isDisplayed();
            } catch (WebDriverException ex) {
                return false;
            }
        } else {
            return false;
        }
    }
    protected boolean isAnyElementOfListDisplayed(List<WebElement> elements) {
        boolean status = false;
        try {
            for (WebElement element : elements) {
                if (isElementDisplayed(element)) {
                    status = true;
                    break;
                }
            }
        } catch (WebDriverException e) {
            status = false;
        }

        return status;
    }
    protected boolean isElementDisplayed(WebElement element, int waitTimeout) {
        try {
            wait = new WebDriverWait(driver, waitTimeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            return isElementDisplayed(element);
        } catch (WebDriverException ex) {
            return false;
        }
    }
    protected boolean isElementAppears(WebElement element, int waitingDuration){
        boolean status = true;
        int retry = 0;
        while (!isElementDisplayed(element)) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * waitingDuration) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until element appear but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    protected boolean isElementAppears(WebElement element){
        boolean status = true;
        int retry = 0;
        while (!isElementDisplayed(element)) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * defaultWaitTimeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until element appear but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    protected boolean isElementsAppears(List<WebElement> elements){
        boolean status = true;
        int retry = 0;
        while (!isAnyElementOfListDisplayed(elements)) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * defaultWaitTimeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until any element of required list appear but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    protected boolean isElementDisappears(WebElement element, int waitingDuration) {
        boolean status = true;
        int retry = 0;
        while (isElementDisplayed(element)) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * waitingDuration) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until element disappear but reached timeout while waiting.");
                break;
            }
        }
        return status;

    }
    protected boolean isElementDisappears(WebElement element) {
        boolean status = true;
        int retry = 0;
        while (isElementDisplayed(element)) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * defaultWaitTimeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until element disappear but reached timeout while waiting.");
                break;
            }
        }
        return status;

    }

    protected boolean isElementsDisappears(List<WebElement> elements) {
        boolean status = true;
        int retry = 0;
        while (isAnyElementOfListDisplayed(elements)) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * defaultWaitTimeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until any element of required list appear but reached timeout while waiting.");
                break;
            }
        }
        return status;

    }
    protected boolean ElementHasRequiredText (WebElement element, String requiredText) {
        String actualText = getElementsText(element);
        if (actualText.equals(requiredText)) {
            return true;
        } else {
            System.err.println("ERROR: Element's text mismatch.");
            System.out.println("Element has text: '"+actualText+"' that not matches required: '" +requiredText+"'.");
            return false;
        }
    }
    protected boolean isAttributePresent (WebElement element, String attribute) {
        boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null){
                result = true;
                System.err.println("ERROR: Required attribute value mismatch.");
                System.out.println("Element has attribute: '" + attribute + "' with value: '" + element.getAttribute(attribute) + "'");
            }
        } catch (Exception e) {
            System.err.println("Exception when getting element's attribute '"+attribute+"' presence.");
        }
        return result;
    }
    protected boolean checkTextAppearsInElement (WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
            return true;
        } catch (TimeoutException e) {
            System.err.println("WAIT TIMEOUT: When waited for text '"+text+"' at the element I reached timeout.");
            return false;
        }
    }
    protected boolean checkExactTextPresentInElement (WebElement element, String text) {
        try {
            return element.getText().equalsIgnoreCase(text);
        } catch (NoSuchElementException e) {
            System.err.println("ERROR: No such element with text '"+text+"'.");
            return false;
        }
    }
    protected boolean isCheckboxChecked(WebElement checkbox) {
        try {
            wait.until(ExpectedConditions.visibilityOf(checkbox));
            return checkbox.isSelected();
        } catch (TimeoutException e) {
            System.err.println("WAIT TIMEOUT: Waited for visibility of checkbox to check if it is checked but reached timeout.");
            return false;
        } catch (StaleElementReferenceException ex) {
            if (isElementAppears(checkbox)) {
                try {
                    return checkbox.isSelected();
                }catch (WebDriverException exc) {
                    return false;
                }

            } else {
                return false;
            }
        }
    }
    protected boolean checkElementHasClass(WebElement element, String className) {

        boolean result = false;
        try {
            String value = element.getAttribute("class");
            if (value != null) {
                result = true;
            }
        } catch (Exception e) {
            System.err.println("Exception when getting element's attribute \"class\" presence.");
        }

        return result && element.getAttribute("class").equals(className);
    }
    protected ArrayList<String> getElementsTexts(List<WebElement> list) {  //http://habrahabr.ru/post/128269/
        ArrayList<String> newList = new ArrayList<String>(list.size());
        for (WebElement element : list) {
            newList.add(getElementsText(element));
        }
        return newList;
    }
    protected void pause (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
