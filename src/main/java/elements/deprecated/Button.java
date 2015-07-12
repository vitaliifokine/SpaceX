package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Button extends ClickableElement{

    public Button(WebElement element) {
        super(element);
        this.elementName = "button";
    }

    public String getText() {
        try {
            if (appears()) {
                if (getTagName().equals("input")) {
                    return element().getAttribute("value");
                } else {
                    return element().getText();
                }
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get text of "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }

    }
}
