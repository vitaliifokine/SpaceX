package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;


public class BooleanButton extends ClickableElement {

    public BooleanButton(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("checkbox");
    }
    public BooleanButton(WebElement element, WebElement wrapper, String name) {
        super(element, wrapper, name);
        this.fullName.setClassification("checkbox");
    }

    public Result<Void> makeSelected() {
        String message;
        boolean status = false;
        Result<Boolean> selected = isSelected();
        if (!selected.getValue()) {
            Result clickResult = click();
            if (clickResult.getStatus().getBooleanValue()) {
                Result<Boolean> selectedResult = isSelected();
                if (selectedResult.getValue()) {
                    status = true;
                    message = selectedResult.getDescription() +  " Selection of " + this.fullName + " was successful.";
                } else {
                    message = selectedResult.getDescription() +  " Selection of " + this.fullName + " wasn't successful.";
                }
            } else {
                message = clickResult.getDescription() +  " Selection of " + this.fullName + " wasn't successful.";
            }
        } else {
            status = true;
            message = selected.getDescription();
        }
        return new Result<Void>(status, null, message);
    }
    public Result<Boolean> isSelected() {
        String message;
        boolean status;
        Boolean result = null;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                result = element().isSelected();
                message = appearance.getDescription() + " Checking " + this.fullName + " for it's selection condition was successful.";
                status = true;
            } else {
                message = appearance.getDescription() + " Checking " + this.fullName + " for it's selection condition wasn't successful.";
                status = false;
            }

        } catch (WebDriverException e) {
            message = "Unable to check if " + fullName + " is selected using locator '" + getLocator() + "'.\nCause: " + e.getClass();
            status = false;
        }
        return new Result<Boolean>(status, result, message);
    }
    public Result<Boolean> notSelected() {
        String message;
        boolean status = false;
        Boolean result = null;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                result = ! element().isSelected();
                message = appearance.getDescription() + " Checking " + this.fullName + " for it's selection condition was successful.";
                status = true;
            } else {
                message = appearance.getDescription() + " Checking " + this.fullName + " for it's selection condition wasn't successful.";
            }

        } catch (WebDriverException e) {
            message = "Unable to check if " + fullName + " is selected using locator '" + getLocator() + "'.\nCause: " + e.getClass();
        }
        return new Result<Boolean>(status, result, message);
    }
}
