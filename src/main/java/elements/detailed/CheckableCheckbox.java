package elements.detailed;

import org.openqa.selenium.WebElement;

public class CheckableCheckbox extends Checkbox{

    public CheckableCheckbox(WebElement element, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(element, name);
        this.fullName.setClassification("checkbox*");

        this.message = message;
        this.decoration = decoration;
    }
    public CheckableCheckbox(WebElement element, WebElement wrapper, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(element, wrapper, name);
        this.fullName.setClassification("checkbox*");
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
