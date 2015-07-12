package elements.detailed;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class InputField extends PageElement {

    public InputField(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("input field");
    }

    public Result<Void> sendText(String text) {
        String message;
        boolean status = false;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                element().sendKeys(text);
                status = true;
                message = appearance.getDescription() + " Text '" + text + "' successfully sent to " + this.fullName + ".";
            } else {
                message = appearance.getDescription() + " Sending text '" + text + "' to " + this.fullName + " wasn't successful.";
            }
        } catch (WebDriverException e) {
            message = "Unable to send text '" + text + "' to " + this.fullName + " using locator '" + getLocator() +" '.\nCause: " + e.getClass();
        }
        return new Result<Void>(status, null, message);
    }
    public Result<Void> sendKeys(Keys key) {
        String message;
        boolean status = false;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                element().sendKeys(key);
                status = true;
                message = appearance.getDescription() +  " Key '" + key.name() + "' successfully sent to " + this.fullName + ".";
            } else {
                message = appearance.getDescription() + " Sending key '" + key.name() + "' to " + this.fullName + " wasn't successful. " + appearance.getDescription();
            }
        } catch (WebDriverException e) {
            message = "Unable to send key '" + key.name() + "' to " + this.fullName + " using locator '" + getLocator() +" '.\nCause: " + e.getClass();
        }
        return new Result<Void>(status, null, message);
    }
    public Result<Void> clear() {
        String message;
        boolean status = false;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                element().clear();
                status = true;
                message = appearance.getDescription() +  " Clearing of " + this.fullName + " proceeded successfully.";
            } else {
                message = appearance.getDescription() + " Clearing of " + this.fullName + " wasn't proceeded. ";
            }

        } catch (WebDriverException e) {
            message = "Unable to clear " + this.fullName + " using locator '" + getLocator() +" '.\nCause: " + e.getClass();
        }
        return new Result<Void>(status, null, message);
    }
    public Result<Boolean> isClear() {
        String message;
        boolean status = false;
        Boolean result = null;
        Result<Boolean> appearance = appears();
        if (appearance.getValue()) {
            if (getContent().getValue().length() == 0) {
                result = true;
            } else {
                result = false;

            }
            message = appearance.getDescription() + " Checking if " + this.fullName + " is clear was successful.";
            status = true;
        } else {
            message = "Checking if field is clear wasn't successful. " + appearance.getDescription();
        }

        return new Result<Boolean>(status, result, message);
    }
    public Result<Boolean> notClear() {
        String message;
        boolean status = false;
        Boolean result = null;
        Result<Boolean> appearance = appears();
        if (appearance.getValue()) {
            if (getContent().getValue().length() == 0) {
                result = false;
            } else {
                result = true;
            }
            message = appearance.getDescription() + " Checking if " + this.fullName + " is clear was successful.";
            status = true;
        } else {
            message = "Checking if " + this.fullName + " is clear wasn't successful. " + appearance.getDescription();
        }

        return new Result<Boolean>(status, result, message);
    }
    public Result<String> getContent() {
        String message;
        boolean status = false;
        String result = "";
        Result<Boolean> appearance = appears();
        if (appearance.getValue()) {
            try {
                result = element().getAttribute("value");
                status = true;
                message = appearance.getDescription() + " Content from " + this.fullName + " got successfully.";
            } catch (WebDriverException e) {
                message = "Unable to get value of "+ fullName +" using locator '" + getLocator() +
                        "'.\nCause: " + e.getClass().getSimpleName();
            }

        } else {
            message = appearance.getDescription() + " Getting content of " + this.fullName + " wasn't successful. ";
        }

        return new Result<String>(status, result, message);

    }
    public Result<String>  getPlaceholder() {
        String message;
        boolean status = false;
        String result = "";
        Result<Boolean> appearance = appears();
        if (appearance.getValue()) {
            try {
                result = element().getAttribute("placeholder");
                status = true;
                message = appearance.getDescription() + " Placeholder from " + this.fullName + " got successfully.";
            } catch (WebDriverException e) {
                message = "Unable to get placeholder of " + this.fullName + " using locator '" + getLocator() +
                        "'.\nCause: " + e.getClass().getSimpleName();
            }

        } else {
            message = appearance.getDescription() + " Getting of " + this.fullName + " placeholder wasn't successful. ";
        }

        return new Result<String>(status, result, message);
    }
    public Result<String>  getMaxLength() {
        String message;
        boolean status = false;
        String result = "";
        Result<Boolean> appearance = appears();
        if (appearance.getValue()) {
            try {
                result = element().getAttribute("maxlength");
                status = true;
                message = appearance.getDescription() + " Max length from " + this.fullName + " got successfully.";
            } catch (WebDriverException e) {
                message = "Unable to get max-length of "+ fullName +" using locator '"+getLocator()+"'.\nCause: " + e.getClass().getSimpleName();
            }

        } else {
            message = appearance.getDescription() + " Getting of max length wasn't successful.";
        }

        return new Result<String>(status, result, message);
    }

}
