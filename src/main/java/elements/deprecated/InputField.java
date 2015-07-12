package elements.deprecated;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class InputField extends PageElement {

    public InputField(WebElement element) {
        super(element);
        this.elementName = "input field";
    }

    public void setText(String text) {
        try {
            if (appears()) {
                element().sendKeys(text);
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to set text to "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
        }
    }
    public void sendKeys(Keys keys) {
        try {
            if (appears()) {
                element().sendKeys(keys);
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to send keys to "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
        }
    }
    public void clear() {
        try {
            if (appears()) {
                element().clear();
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to clear text of "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
        }
    }
    public boolean isClear() {
        return getValue().length() == 0;
    }
    public String getValue() {
        try {
            if (appears()) {
                return element().getAttribute("value");
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get value of "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }
    }
    public String getPlaceholder() {
        try {
            if (appears()) {
                return element().getAttribute("placeholder");
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get placeholder of "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }
    }
    public String getMaxLength() {
        try {
            if (appears()) {
                return element().getAttribute("maxlength");
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get max length value of "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }
    }

}
