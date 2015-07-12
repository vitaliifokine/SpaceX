package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CustomSelect extends ClickableElement {

    public CustomSelect(WebElement element, List<WebElement> options, String name) {
        super(element, name);
        this.options = new CustomSelectOptions(options, name);
        this.fullName.setClassification("custom select");
    }
    public CustomSelect(WebElement element, WebElement wrapper, List<WebElement> options, String name) {
        super(element, wrapper, name);
        this.options = new CustomSelectOptions(options, name);
        this.fullName.setClassification("custom select");
    }

    public CustomSelectOptions options() {
        return this.options;
    }

    public Result<String> getCurrentlySelectedOptionText () {
        boolean status = false;
        String result = "";
        String message;
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                result =  element().getText();
                message = appearance.getDescription() + " Selected option text from " + this.fullName + " got successfully.";
                status = true;
            } else {
                message = appearance.getDescription() + " Selected option text from " + this.fullName + " wasn't got successfully.";
            }
        } catch (WebDriverException e) {
            message = "Unable to get text of currently selected option from "+ this.fullName +
                    " using locator '"+getLocator()+"'.\nCause: " + e.getClass().getSimpleName();

        }
        return new Result<String>(status, result, message);
    }

    public class CustomSelectOptions {

        public CustomSelectOptions(List<WebElement> elements, String name) {
            this.elements = new ClickableElements(elements, name);
            this.elements.fullName.setClassification("options of");
        }

        public Result<Void> clickOptionContainsText(String text) {
            Result<Boolean> display = elements.isAnyElementOfListDisplayed();
            if (!display.getValue()) {
                click();
            }
            return elements.clickElementContainsText(text);
        }
        public Result<Void> clickOptionHasExactText(String text) {
            if (!elements.isAnyElementOfListDisplayed().getValue()) {
                click();
            }
            return elements.clickElementHasText(text);
        }
        public Result<Void> clickOptionNumber(int number) {
            if (!this.elements.isAnyElementOfListDisplayed().getValue()) {
                click();
            }
            return elements.clickElementNumber(number);
        }
        public Result<String> getFullTextOfOptionContainsTest (String text) {
            String result = "";
            boolean status = false;
            String message;

            if (!elements.isAnyElementOfListDisplayed().getValue()) {
                click();
            }
            try {
                for (WebElement option : elements.elements()) {
                    if (option.getText().contains(text)) {
                        result = option.getText();
                        status = true;
                        break;
                    }
                }
                if (status) {
                    message = "Full text successfully gotten from option with text '"+text+"'.";
                } else {
                    message = "Option with text '"+text+"' not found.\nAvailable options: " + elements.getElementsTexts();
                }

            } catch (WebDriverException e) {
                message = "Unable to get full option text from option contains '"+text+"'.\nCause: " + e.toString();
            }
            return new Result<String>(status, result, message);
        }

        private ClickableElements elements;
    }

    private CustomSelectOptions options;
 }
