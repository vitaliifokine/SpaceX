package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Button extends ClickableElement{

    public Button(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("button");
    }

    public Result<String> getText() {
        boolean status = false;
        String message;
        String result = "";
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                if (getTagName().getValue().equals("input")) {
                    result = element().getAttribute("value");
                    status = true;
                    message = appearance.getDescription() + " Text from " + this.fullName + " got successfully.";
                } else {
                    result = element().getText();
                    status = true;
                    message = appearance.getDescription() + " Text from " + this.fullName + " got successfully.";
                }
            } else {
                message = appearance.getDescription() + " Unable to get text from " + this.fullName + ".";
            }
        } catch (WebDriverException e) {
            message = "Unable to get text of " + this.fullName +
                    " using locator '"+getLocator()+"'.\nCause: " + e.getClass().getSimpleName();

        }
        return new Result<String>(status, result, message);
    }
}
