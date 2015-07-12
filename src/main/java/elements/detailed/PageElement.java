package elements.detailed;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import utils.Timeouts;

import static utils.StringProcessor.firstToUpperCase;

public class PageElement {

    public PageElement(WebElement element, String name) {
        this.element = element;
        this.name = name;
        this.fullName = new ElementFullName(this.classification, name);
        if (this instanceof Wrapper) {
            // checking for instance to avoid STACK OVERFLOW (cause of inheritance of wrapper)
        } else {
            this.wrapper = new Wrapper(element, name);
        }
        this.isWrapped = false;
    }
    public PageElement(WebElement element, WebElement wrapper, String name) {
        this.fullName = new ElementFullName(this.classification, name);
        this.element = element;
        this.name = name;
        this.wrapper = new Wrapper(wrapper, name);
        this.isWrapped = true;
    }

    public Result<Boolean> appears() {
        boolean result = true;
        String message = "";
        int retry = 0;
        while (!displayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                result = false;
                message = "Wait timeout: I waited until " + this.fullName +
                        " appear using locator '" + getLocator() + "' but reached timeout while waiting.";
                break;
            }
        }
        if (result) {
            message = firstToUpperCase(this.fullName.toString()) + " appears.";
        }
        return new Result<Boolean>(true, result, message);
    }

    public Result<Boolean> appears(int timeout) {
        boolean result = true;
        String message = "";
        int retry = 0;
        while (!displayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                result = false;
                message = "Wait timeout: I waited until " + this.fullName +
                        " appear using locator '" + getLocator() + "' but reached timeout while waiting.";
                break;
            }
        }
        if (result) {
            message = firstToUpperCase(this.fullName.toString()) + " appears.";
        }
        return new Result<Boolean>(true, result, message);
    }
    public Result<Boolean> disappears() {
        boolean result = true;
        String message = "";
        int retry = 0;
        while (displayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                result = false;
                message = "Wait timeout: I waited until " + this.fullName +
                        " disappear using locator '" + getLocator() + "' but reached timeout while waiting.";
                break;
            }
        }
        if (result) {
            message = firstToUpperCase(this.fullName.toString()) + " disappears.";
        }
        return new Result<Boolean>(true, result, message);
    }
    public Result<Boolean> disappears(int timeout) {
        boolean result = true;
        String message = "";
        int retry = 0;
        while (displayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                result = false;
                message = "Wait timeout: I waited until " + this.fullName +
                        " disappear using locator '" + getLocator() + "' but reached timeout while waiting.";
                break;
            }
        }
        if (result) {
            message = firstToUpperCase(this.fullName.toString()) + " disappears.";
        }
        return new Result<Boolean>(true, result, message);
    }
    public Result<Boolean> isDisplayed() {
        if (displayed()) {
            // statuses here always = true because here are no exceptions or other probable action-breakers
            return new Result<Boolean>(true, true, firstToUpperCase(this.fullName.toString()) + " is displayed.");
        } else {
            return new Result<Boolean>(true, false, firstToUpperCase(this.fullName.toString()) + " is not displayed.");
        }
    }
    public Result<Boolean> notDisplayed() {
        if (displayed()) {
            // statuses here always = true because here are no exceptions or other probable action-breakers
            return new Result<Boolean>(true, false, firstToUpperCase(this.fullName.toString()) + " is displayed.");
        } else {
            return new Result<Boolean>(true, true, firstToUpperCase(this.fullName.toString()) + " is not displayed.");
        }
    }
    public Result<String>  getTagName() {
        boolean status = false;
        String result = "";
        String message;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                result = this.element.getTagName() ;
                message = appearance.getDescription()  + " Tag name for " + this.fullName + " got successfully.";
                status = true;
            } else {
                message = appearance.getDescription() + " Cannot get a tag name from " + this.fullName + ".";
            }
        } catch (WebDriverException ex) {
            message = "Unable to get tag name of " + this.fullName +
                    " using locator '"+getLocator()+"'.\nCause: " + ex.getClass().getSimpleName();
        }
        return new Result<String>(status, result, message);

    }

    public Result<String>  getAttribute(String attributeName) {
        boolean status = false;
        String result = "";
        String message;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                result = this.element.getAttribute(attributeName) ;
                message = appearance.getDescription()  + " Attribute value of '"+attributeName+"' for " + this.fullName + " got successfully.";
                status = true;
            } else {
                message = appearance.getDescription() + " Cannot get attribute value of '"+attributeName+"' from " + this.fullName + ".";
            }
        } catch (WebDriverException ex) {
            message = "Unable to get attribute '"+attributeName+"' name of " + this.fullName +
                    " using locator '"+getLocator()+"'.\nCause: " + ex.getClass().getSimpleName();
        }
        return new Result<String>(status, result, message);

    }

    public String getLocator() {
        return this.element.toString();
    }
    public Wrapper wrapper() {
        if (isWrapped) {
            return this.wrapper;
        } else {
            System.err.println("Message: You're asked for wrapper when element actually isn't wrapped. Returning this element as Wrapper...");
            return new Wrapper(this.element, this.name);
        }

    }

    protected WebElement element() {
        return this.element;
    }
    protected boolean displayed() {
        if (element != null) {
            if (isWrapped) {
                return wrapper.displayed();
            } else  {
                try {
                    return element.isDisplayed();
                } catch (WebDriverException ex) {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        return "Element {name: '" + this.fullName + "'; locator: '" + getLocator() + "'; tag: '" + getTagName().getValue() + "';};";
    }

    private WebElement element;
    private Wrapper wrapper;
    protected ElementFullName fullName;
    private String classification = "element";
    protected boolean isWrapped = false;
    private int timeout = Timeouts.SEC_MEDIUM;
    private String name = "";

    private WebDriver getDriver() {
        WrapsDriver wraps = (WrapsDriver) this.element;
        return wraps.getWrappedDriver();
    }
}
