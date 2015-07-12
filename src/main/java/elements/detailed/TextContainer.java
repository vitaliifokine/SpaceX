package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class TextContainer extends PageElement {

    public TextContainer(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("text container");
    }

    public Result<String> getText() {
        boolean status = false;
        String result = "";
        String message;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                result = element().getText();
                message = appearance.getDescription() + " Text from " + this.fullName + " got successfully.";
                status = true;
            } else {
                message = appearance.getDescription() + " Getting text from " + this.fullName + " wasn't successful.";
            }
        } catch (WebDriverException e) {
            message = "Unable to get text of " + this.fullName + " using locator '" + getLocator() + "'.\nCause: " + e.toString();

        }
        return new Result<String>(status, result, message);
    }

}
