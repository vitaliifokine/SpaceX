package elements.deprecated;

import org.openqa.selenium.WebElement;

public class CheckableInputField extends InputField{

    public CheckableInputField(WebElement element, ErrorMessage message, ErrorDecoration decoration) {
        super(element);
        this.elementName = "input field (required)";
        this.message = message;
        this.decoration = decoration;
    }

    public ErrorMessage errorMessage() {
        return this.message;
    }
    public ErrorDecoration errorDecoration() {
        return this.decoration;
    }

    private ErrorDecoration decoration;
    private ErrorMessage message;
}
