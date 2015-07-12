package elements.deprecated;

import org.openqa.selenium.WebElement;

public class CheckableRadioButton extends RadioButton{

    public CheckableRadioButton(WebElement element, ErrorMessage message, ErrorDecoration decoration) {
        super(element);
        this.elementName = "radio button (required)";
        this.message = message;
        this.decoration = decoration;
    }
    public CheckableRadioButton(WebElement element, WebElement wrapper, ErrorMessage message, ErrorDecoration decoration) {
        super(element, wrapper);
        this.elementName = "radio button (required)";
        this.message = message;
        this.decoration = decoration;
    }

    public ErrorMessage errorMessage() {
        return this.message;
    }
    public ErrorDecoration errorDecoration() {
        return this.decoration;
    }

    private ErrorMessage message;
    private ErrorDecoration decoration;
}
