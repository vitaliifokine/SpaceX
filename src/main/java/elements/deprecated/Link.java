package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Link extends ClickableElement{

    public Link(WebElement element) {
        super(element);
        this.elementName = "link";
    }

    public String getText() {
        try {
            if (appears()) {
                return element().getText();
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get text of "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }
    }
    public String getReference() {
        try {
            if (appears()) {
                return element().getAttribute("href");
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get reference of "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }
    }

}
