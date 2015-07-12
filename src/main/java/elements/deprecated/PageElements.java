package elements.deprecated;

import utils.Timeouts;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PageElements {

    public PageElements (List<WebElement> elements) {
        this.elements = elements;
        if (this instanceof Wrappers) {
            // checking for instance to avoid STACK OVERFLOW (cause of inheritance of wrapper)
        } else {
            this.wrappers = new Wrappers(elements);
        }

        this.isWrapped = false;
    }
    public PageElements (List<WebElement> elements, List<WebElement> wrappers) {
        this.elements = elements;
        this.wrappers = new Wrappers(wrappers);
        this.isWrapped = true;
    }

    public ArrayList<String> getElementsTexts     () {
        ArrayList<String> newList = new ArrayList<String>(elements.size());
        if (isElementsAppears()) {
            for (WebElement element : elements) {
                newList.add(element.getText());
            }
        }
        return newList;
    }
    public boolean isDisplayedElementWithExactText(String requiredText) {
        boolean result = false;
        try {
            for (WebElement element : elements) {
                if (element.isDisplayed() && element.getText().equalsIgnoreCase(requiredText)) {
                    result = true;
                    break;
                }
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to check if element displayed with exact text '" + requiredText + "'.\nCause: " + e.toString());
        }
        return result;
    }
    public boolean isDisplayedElementContainsText (String requiredText) {
        boolean result = false;
        try {
            for (WebElement element : elements) {
                if (element.isDisplayed() && element.getText().contains(requiredText)) {
                    result = true;
                    break;
                }
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to check if element displayed contains text '" + requiredText + "'.\nCause: " + e.toString());
        }
        return result;
    }
    public boolean isAnyElementOfListDisplayed    () {
        boolean status = false;
        if (this.isWrapped) {
            status = wrappers().isAnyElementOfListDisplayed();
        } else {
            try {
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        status = true;
                        break;
                    }
                }
            } catch (WebDriverException e) {
                status = false;
            }
        }


        return status;
    }
    public boolean isElementsAppears              (){
        boolean status = true;
        int retry = 0;
        while (!isAnyElementOfListDisplayed()) {
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            retry++;
            if (retry > 10 * timeout) {
                status = false;
                System.err.println("WAIT TIMEOUT: I waited until any element of "+elementListName+" appear but reached timeout while waiting.");
                break;
            }
        }
        return status;
    }
    public int getElementsCount                   () {
        if (isElementsAppears()) {
            return this.elements.size();
        } else {
            return 0;
        }

    }
    public int getPositionOfElementContainsText   (String text) {
        int position = 0;
        boolean found = false;
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
                System.err.println("List of elements is empty to get position of element by text '" + text + "'.");
            }

        } catch (WebDriverException e) {
            System.err.println("Cannot get position of element by text '" + text + "'.\nCause: " + e.toString());
        }

        if (found) {
            return position;
        } else {
            return 0;
        }
    }
    public int getPositionOfElementWithAttribute  (WebElementAttribute attribute) {
        int position = 0;
        boolean found = false;
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
                System.err.println("List of elements is empty to get position of element by attribute '" + attribute.toString() + "'.");
            }

        } catch (WebDriverException e) {
            System.err.println("Cannot get position of element by attribute '" + attribute.toString() + "'.\nCause: " + e.toString());
        }

        if (found) {
            return position;
        } else {
            return 0;
        }
    }
    public String getTextOfElementWithAttribute   (WebElementAttribute attribute) {
        String text = "";
        boolean found = false;
        try {
            if (!elements.isEmpty()) {
                for (WebElement element : this.elements) {
                    if (element.getAttribute(attribute.getName()).contains(attribute.getValue())) {
                        text = element.getText();
                        found = true;
                        break;
                    }
                }
            } else {
                System.err.println("List of elements is empty to get text of element by attribute '" + attribute.toString() + "'.");
            }

        } catch (WebDriverException e) {
            System.err.println("Cannot get text of element by attribute '" + attribute.toString() + "'.\nCause: " + e.toString());
        }
        if (!found) {
            System.err.println("Element with attribute '" + attribute.toString() + "' not found.");
        }
        return text;

    }

    public Wrappers wrappers() {
        if (isWrapped) {
            return this.wrappers;
        } else {
            System.err.println("Message: You're asked for wrappers when elements actually aren't wrapped. Returning this elements as Wrappers...");
            return new Wrappers(this.elements);
        }
    }

    protected List<WebElement> elements () {
        return this.elements;
    }

    private List<WebElement> elements;
    protected Wrappers wrappers;
    protected boolean isWrapped = false;
    private int timeout = Timeouts.SEC_MEDIUM;
    protected String elementListName = "elements";
}
