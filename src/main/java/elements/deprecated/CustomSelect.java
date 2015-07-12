package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CustomSelect extends ClickableElement {

    public CustomSelect(WebElement element, List<WebElement> options) {
        super(element);
        this.options = new CustomSelectOptions(options);
        this.elementName = "custom select";
    }
    public CustomSelect(WebElement element, WebElement wrapper, List<WebElement> options) {
        super(element, wrapper);
        this.options = new CustomSelectOptions(options);
        this.elementName = "custom select";
    }

    public CustomSelectOptions options() {
        return this.options;
    }
    public String getCurrentlySelectedOptionText () {
        try {
            if (appears()) {
                return element().getText();
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get text of currently selected option from "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }

    }

    public class CustomSelectOptions {

        public CustomSelectOptions(List<WebElement> elements) {
            this.elements = new ClickableElements(elements);
            this.elements.elementListName = "options";
        }

        public void clickOptionContainsText(String text) {
            if (!elements.isAnyElementOfListDisplayed()) {
                click();
            }
            elements.clickElementContainsText(text);
        }
        public void clickOptionHasExactText(String text) {
            if (!elements.isAnyElementOfListDisplayed()) {
                click();
            }
            elements.clickElementHasText(text);
        }
        public String getFullTextOfOptionContainsTest (String text) {
            String fullText = "";
            if (!elements.isAnyElementOfListDisplayed()) {
                click();
            }
            try {
                boolean clicked = false;
                for (WebElement option : elements.elements()) {
                    if (option.getText().contains(text)) {
                        fullText = option.getText();
                        clicked = true;
                        break;
                    }
                }
                if (!clicked) {
                    System.err.println("Option with text '"+text+"' not found.\nAvailable options: " + elements.getElementsTexts());
                }

            } catch (WebDriverException e) {
                System.err.println("Unable to get full option text from option contains '"+text+"'.\nCause: " + e.toString());
            }
            return fullText;
        }
        public void clickOptionNumber(int number) {
            if (!this.elements.isAnyElementOfListDisplayed()) {
                click();
            }
            elements.clickElementNumber(number);


        }

        private ClickableElements elements;
    }

    private CustomSelectOptions options;
 }
