package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ClickableElements extends PageElements {

    public ClickableElements(List<WebElement> elements, String name) {
        super(elements, name);
        this.fullName.setClassification("clickable elements");
    }
    public ClickableElements(List<WebElement> elements, List<WebElement> wrappers, String name) {
        super(elements, wrappers, name);
        this.fullName.setClassification("clickable elements");
    }

    public Result<Void> clickElementHasText(String text) {

        boolean status = false;
        String message;
        try {
            if (this.isWrapped) {
                int counter = 0;
                for (WebElement element : elements()) {
                    counter++;
                    if (element.getText().equalsIgnoreCase(text)) {
                        wrappers().clickElementNumber(counter);
                        status = true;
                        break;
                    }
                }
            } else {
                for (WebElement element : elements()) {
                    if (element.getText().equalsIgnoreCase(text)) {
                        element.click();
                        status = true;
                        break;
                    }
                }
            }
            if (status) {
                message = "Successfully clicked to element with text '"+text+"' in " + this.fullName + ".";
            } else {
                message = "Element with exact text '"+text+"' not found in "+ this.fullName +" list to click it.\nAvailable elements: " + getElementsTexts();
            }
        } catch (WebDriverException e) {
            status = false;
            message = "Unable to click element with text '"+text+"' in "+this.fullName+".\nCause: " + e.getClass().getSimpleName();
        }

        return new Result<Void>(status, null, message);
    }
    public Result<Void> clickElementContainsText(String text) {

        boolean status = false;
        String message;
        try {
            if (this.isWrapped) {
                int counter = 0;
                for (WebElement element : elements()) {
                    counter++;
                    if (element.getText().contains(text)) {
                        wrappers().clickElementNumber(counter);
                        status = true;
                        break;
                    }
                }
            } else {
                for (WebElement element : elements()) {
                    if (element.getText().contains(text)) {
                        element.click();
                        status = true;
                        break;
                    }
                }
            }
            if (status) {
                message = "Successfully clicked to element that contains text '"+text+"' in " + this.fullName + ".";
            } else {
                message = "Element contains text '"+text+"' not found in "+ this.fullName +" list to click it.\nAvailable elements: " + getElementsTexts();
            }
        } catch (WebDriverException e) {
            status = false;
            message = "Unable to click element that contains text '"+text+"' in "+this.fullName+".\nCause: " + e.getClass().getSimpleName();
        }

        return new Result<Void>(status, null, message);
    }
    public Result<Void> clickElementNumber(int elementNumber) {

        boolean status = false;
        String message;
        try {
            if (this.isWrapped) {
                Result<Void> clickResult = wrappers().clickElementNumber(elementNumber);
                if (clickResult.getStatus().getBooleanValue()) {
                    status = true;
                    message = "Successfully clicked to element's wrapper in "+this.fullName+".";
                } else {
                    status = true;
                    message = "Click to element's wrapper wasn't successful in "+this.fullName+". " + clickResult.getDescription();
                }
            } else {
                if (elementNumber > elements().size()) {
                    message = "Element number '"+elementNumber+"' couldn't be found because amount of elements is " + elements().size();
                    status = false;
                } else {
                    int counter = 0;
                    for (WebElement element : elements()) {
                        counter++;
                        if (counter == elementNumber) {
                            element.click();
                            status = true;
                            break;
                        }

                    }
                    if (status) {
                        message = "Successfully clicked to element #"+elementNumber+" in " + this.fullName + ".";
                    } else {
                        message = "Element #"+elementNumber+" not found in "+ this.fullName +" list to click it.\nAvailable elements: " + getElementsTexts();
                    }
                }

            }

        } catch (WebDriverException e) {
            status = false;
            message = "Unable to click element #"+elementNumber+" in "+this.fullName+".\nCause: " + e.getClass().getSimpleName();
        }

        return new Result<Void>(status, null, message);

    }
}
