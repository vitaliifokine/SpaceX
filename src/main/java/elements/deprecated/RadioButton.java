package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class RadioButton extends ClickableElement{

    public RadioButton(WebElement element) {
        super(element);
        this.elementName = "radio button";
    }
    public RadioButton(WebElement element, WebElement wrapper) {
        super(element, wrapper);
        this.elementName = "radio button";
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
