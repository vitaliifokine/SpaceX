package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class TextContainer extends PageElement {

    public TextContainer(WebElement element) {
        super(element);
        this.elementName = "text container";
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

}
