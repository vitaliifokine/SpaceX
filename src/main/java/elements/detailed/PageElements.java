package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import utils.Timeouts;

import java.util.ArrayList;
import java.util.List;

import static utils.StringProcessor.firstToUpperCase;


public class PageElements {

    public PageElements (List<WebElement> elements, String name) {
        this.elements = elements;
        this.fullName = new ElementFullName(this.classification, name);
        if (this instanceof Wrappers) {
            // checking for instance to avoid STACK OVERFLOW (cause of inheritance of wrapper)
        } else {
            this.wrappers = new Wrappers(elements, name);
        }

        this.isWrapped = false;
    }
    public PageElements (List<WebElement> elements, List<WebElement> wrappers, String name) {
        this.fullName = new ElementFullName(this.classification, name);
        this.elements = elements;
        this.wrappers = new Wrappers(wrappers, name);
        this.isWrapped = true;
    }

    public Result<ArrayList<String>> getElementsTexts     () {
        ArrayList<String> result = new ArrayList<String>(elements.size());

        String message;

        boolean status;
        try {
            for (WebElement element : elements) {
                result.add(element.getText());
            }
            message = firstToUpperCase(this.fullName.toString()) + " getting of all elements texts.";
            status = true;
        } catch (WebDriverException e) {
            message = ("Unable get text of '" + this.fullName + "'.\nCause: " + e.toString());
            result = null;
            status = false;
        }
        return new Result<ArrayList<String>>(status, result, message);
    }
    public Result<Boolean> isDisplayedElementWithExactText(String requiredText) {
        String message;
        Boolean result = false;
        boolean status;
        try {
            for (WebElement element : elements) {
                if (element.isDisplayed() && element.getText().equals(requiredText)) {
                    result = true;
                    break;
                }
            }
            message = firstToUpperCase(this.fullName.toString()) + " checking for display of any element was successful.";
            status = true;
        } catch (WebDriverException e) {
            message = "Unable to check if " + this.fullName +  "  contains text '" + requiredText + "'.\nCause: " + e.toString();
            result = null;
            status = false;
        }
        return new Result<Boolean>(status, result, message);
    }
    public Result<Boolean> isDisplayedElementContainsText (String requiredText) {
        String message;
        Boolean result = false;
        boolean status;
        try {
            for (WebElement element : elements) {
                if (element.isDisplayed() && element.getText().contains(requiredText)) {
                    result = true;
                    break;
                }
            }
            if (result) {
                message = "Element that contains text '"+requiredText+"' found.";
            } else {
                message = "Element that contains text '"+requiredText+"' not found.";
            }
            status = true;
        } catch (WebDriverException e) {
            message = ("Unable to check if element displayed contains text '" + requiredText + "'.\nCause: " + e.toString());
            result = null;
            status = false;
        }
        return new Result<Boolean>(status, result, message);
    }
    public Result<Boolean> isAnyElementOfListDisplayed    () {
        String message;
        Boolean result = false;
        boolean status;
        if (this.isWrapped) {
            result = wrappers().isAnyElementOfListDisplayed().getValue();
            message = "Successfully got result from wrapped " + this.fullName + ".";
            status = true;
        } else {
            try {
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        result = true;
                        break;
                    }
                }
                message = "Successfully checked " + this.fullName + " for element displaying.";
                status = true;
            } catch (WebDriverException e) {
                status = false;
                message = "Unable to get text of " + this.fullName + ".\nCause: " + e.getClass().getSimpleName();
            }
        }


        return new Result<Boolean>(status, result, message);
    }
    public Result<Boolean> isElementsAppears              (){

        boolean status = false;
        String message;
        Boolean result = null;
        boolean timeIsOut = false;
        int retry = 0;

        while (!isAnyElementOfListDisplayed().getValue()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                timeIsOut = true;
                break;
            }
        }

        if (!timeIsOut) {
            result = true;
            status = true;
            message = firstToUpperCase(this.fullName.toString()) + " successfully appears.";
        } else  {
            message = "Wait timeout: I waited until any element of " + this.fullName + " appear but reached timeout while waiting.";
            result = false;
        }

        return new Result<Boolean>(status, result, message);
    }
    public Result<Integer> getElementsCount                   () {

        Integer result;
        String message;
        boolean status = false;

        if (isElementsAppears().getValue()) {
            result = this.elements.size();
            message = "Successfully taken count of " + this.fullName + ".";
        } else {
            message = "Looks like " + this.fullName + " doesn't appears.";
            result = 0;
        }

        return new Result<Integer>(true, result, message);
    }
    public Result<Integer> getPositionOfElementContainsText   (String text) {

        Integer result;
        String message;
        boolean status = false;

        int position = 0;
        Boolean found = false;
        try {
            if (!elements.isEmpty()) {
                for (WebElement element : this.elements) {
                    position++;
                    if (element.getText().contains(text)) {
                        found = true;
                        break;
                    }
                }
            } else {
                found = null;
            }

            if (found == null) {
                result = 0;
                message = "Looks like " + this.fullName + " is empty. Cannot get position of element by text '" + text + "'.";
            } else if (found) {
                result = position;
                message = "Successfully got position of element with text '"+text+"' in " + this.fullName + ".";
                status = true;
            } else {
                result = 0;
                message = "Element with text '"+text+"' in " + this.fullName + " not found. Available elements: " + getElementsTexts();
                status = true;
            }
        } catch (WebDriverException e) {
            message = "Cannot get position of element by text '" + text + "'.\nCause: " + e.getClass().getSimpleName();
            status = false;
            result = null;
        }


        return new Result<Integer>(status, result, message);
    }
    public Result<Integer> getPositionOfElementWithAttribute  (WebElementAttribute attribute) {

        Integer result;
        String message;
        boolean status = false;

        int position = 0;
        Boolean found = false;
        try {
            if (!elements.isEmpty()) {
                for (WebElement element : this.elements) {
                    position++;
                    if (element.getAttribute(attribute.getName()).contains(attribute.getValue())) {
                        found = true;
                        break;
                    }
                }
            } else {
                found = null;
            }

            if (found == null) {
                result = 0;
                message = "Looks like " + this.fullName + " is empty. Cannot get position of element by attribute '" + attribute + "'.";
            } else if (found) {
                result = position;
                message = "Successfully got position of element with attribute '" + attribute + "' in " + this.fullName + ".";
                status = true;
            } else {
                result = 0;
                message = "Element with attribute '" + attribute + "' in " + this.fullName + " not found.";
                status = true;
            }

        } catch (WebDriverException e) {
            message = "Cannot get position of element by attribute '" + attribute + "'.\nCause: " + e.getClass().getSimpleName();
            result = null;
            status = false;
        }


        return new Result<Integer>(status, result, message);

    }
    public Result<String>  getTextOfElementWithAttribute      (WebElementAttribute attribute) {

        String result = "";
        String message;
        boolean status = false;

        Boolean found = false;
        try {
            if (!elements.isEmpty()) {
                for (WebElement element : this.elements) {
                    if (element.getAttribute(attribute.getName()).contains(attribute.getValue())) {
                        result = element.getText();
                        found = true;
                        break;
                    }
                }
            } else {
                found = null;
            }

            if (found == null) {
                message = "Looks like " + this.fullName + " is empty. Cannot get text of element by attribute '" + attribute + "'.";
            } else if (found) {
                message = "Successfully got text of element with attribute '" + attribute + "' in " + this.fullName + ".";
                status = true;
            } else {
                message = "Element with attribute '" + attribute + "' in " + this.fullName + " not found.";
                status = true;
            }

        } catch (WebDriverException e) {
            message = "Cannot get position of element by attribute '" + attribute + "'.\nCause: " + e.getClass().getSimpleName();
            status = false;
        }

        return new Result<String>(status, result, message);

    }
    public Result<String>  getTextFromElementNumber(int elementNumber) {

        boolean status = false;
        String message;
        String result = "";
        try {

                if (elementNumber > elements().size()) {
                    message = "Element number '"+elementNumber+"' couldn't be found because amount of elements is " + elements().size();
                    status = false;
                } else {
                    int counter = 0;
                    for (WebElement element : elements()) {
                        counter++;
                        if (counter == elementNumber) {
                            result = element.getText();
                            status = true;
                            break;
                        }

                    }
                    if (status) {
                        message = "Successfully got text from element #"+elementNumber+" in " + this.fullName + ".";
                    } else {
                        message = "Element #"+elementNumber+" not found in "+ this.fullName +" list to get its text.\nAvailable elements: " + getElementsTexts();
                    }
                }



        } catch (WebDriverException e) {
            status = false;
            message = "Unable to get elements #"+elementNumber+" text in "+this.fullName+".\nCause: " + e.getClass().getSimpleName();
        }

        return new Result<String>(status, result, message);
    }

    public Wrappers wrappers() {
        if (isWrapped) {
            return this.wrappers;
        } else {
            System.err.println("Message: You're asked for wrappers when elements actually aren't wrapped. Returning this elements as Wrappers...");
            return new Wrappers(this.elements, "");
        }
    }

    protected List<WebElement> elements () {
        return this.elements;
    }

    private List<WebElement> elements;
    protected Wrappers wrappers;
    protected boolean isWrapped = false;
    private int timeout = Timeouts.SEC_MEDIUM;
    private String classification = "elements";
    protected ElementFullName fullName;
}
