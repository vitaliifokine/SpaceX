package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class ClickableElement extends PageElement {

    public ClickableElement(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("clickable element");
    }
    public ClickableElement(WebElement element, WebElement wrapper, String name) {
        super(element, wrapper, name);
        this.fullName.setClassification("clickable element");
    }

    public Result<Void> click() {
        String message;
        boolean status = false;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                if (this.isWrapped) {
                    wrapper().click();
                    status = true;
                    message = appearance.getDescription() + " Clicked to " + this.fullName + " successfully.";
                } else {
                    element().click();
                    status = true;
                    message = appearance.getDescription() +  " Clicked to " + this.fullName + " successfully.";
                }
            } else {
                message = appearance.getDescription() + " Click to " + this.fullName + " wasn't successful.";
            }
        } catch (WebDriverException e) {
            message = "Failed to click at " + this.fullName + " using locator '" + getLocator() + "'.\nCause: " + e.getClass() + ".";
        }
        return new Result<Void>(status, null, message);
    }

}
