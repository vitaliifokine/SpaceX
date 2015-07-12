package elements.detailed;

import org.openqa.selenium.WebElement;

public class CheckableInputField extends InputField{

    public CheckableInputField(WebElement element, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(element, name);
        this.fullName.setClassification("input field*");
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
