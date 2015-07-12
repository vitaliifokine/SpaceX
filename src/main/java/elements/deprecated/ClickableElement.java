package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class ClickableElement extends PageElement {

    public ClickableElement(WebElement element) {
        super(element);
        this.elementName = "clickable element";
    }
    public ClickableElement(WebElement element, WebElement wrapper) {
        super(element, wrapper);
        this.elementName = "clickable element";
    }

    public void click() {
        try {
            if (appears()) {
                if (this.isWrapped) {
                    wrapper().click();
                } else {
                    element().click();
                }
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to click at "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
        }
    }

}
