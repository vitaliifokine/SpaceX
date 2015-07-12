package elements.detailed;

import org.openqa.selenium.WebElement;

public class CheckableRadioButton extends RadioButton{

    public CheckableRadioButton(WebElement element, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(element, name);
        this.fullName.setClassification("radio button*");
        this.message = message;
        this.decoration = decoration;
    }
    public CheckableRadioButton(WebElement element, WebElement wrapper, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(element, wrapper, name);
        this.fullName.setClassification("radio button*");
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
