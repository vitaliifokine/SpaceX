package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Checkbox extends ClickableElement{

    public Checkbox(WebElement element)  {
        super(element);
        this.elementName = "checkbox";
    }
    public Checkbox(WebElement element, WebElement wrapper) {
        super(element, wrapper);
        this.elementName = "checkbox";
    }

    public void makeSelected() {
        if (!isSelected()) {
            if (this.isWrapped) {
                wrapper().click();
            } else {
                click();
            }
        }
    }
    public boolean isSelected() {
        try {

            if (appears()) {
                return element().isSelected();
            } else {
                return false;
            }

        } catch (WebDriverException e) {
            System.err.println("Unable to check if "+elementName+" is selected using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return false;
        }
    }
}
