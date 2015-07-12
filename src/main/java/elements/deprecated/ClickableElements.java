package elements.deprecated;

import org.openqa.selenium.WebElement;

import java.util.List;

public class ClickableElements extends PageElements {

    public ClickableElements(List<WebElement> elements) {
        super(elements);
        this.elementListName = "clickable elements";
    }
    public ClickableElements(List<WebElement> elements, List<WebElement> wrappers) {
        super(elements, wrappers);
        this.elementListName = "clickable elements";
    }

    public void clickElementHasText(String text) {
        boolean clicked = false;
        for (WebElement element : elements()) {
            if (element.getText().equalsIgnoreCase(text)) {
                element.click();
                clicked = true;
                break;
            }
        }
        if (!clicked) {
            System.err.println("Element with exact text '"+text+"' not found in "+elementListName+" list to click it.\nAvailable elements: " + getElementsTexts());
        }
    }
    public void clickElementContainsText(String text) {
        boolean clicked = false;
        if (this.isWrapped) {
            int counter = 0;
            for (WebElement element : elements()) {
                counter++;
                if (element.getText().contains(text)) {
                    wrappers().clickElementNumber(counter);
                    clicked = true;
                    break;
                }
            }
        } else {
            for (WebElement element : elements()) {
                if (element.getText().contains(text)) {
                    element.click();
                    clicked = true;
                    break;
                }
            }
        }

        if (!clicked) {
            System.err.println("Element that contains text '"+text+"' not found in "+elementListName+" list to click it.\nAvailable elements: " + getElementsTexts());
        }
    }
    public void clickElementNumber(int elementNumber) {
        if (this.isWrapped) {
            wrappers().clickElementNumber(elementNumber);
        } else {
            if (elementNumber > elements().size()) {
                System.err.println("Element number '"+elementNumber+"' couldn't be found because amount of elements is " + elements().size());
            } else {
                int counter = 0;
                for (WebElement element : elements()) {
                    counter++;
                    if (counter == elementNumber) {
                        element.click();
                        break;
                    }

                }

            }
        }
    }
}
