package elements.deprecated;

import org.openqa.selenium.WebElement;

public class CheckableCheckbox extends Checkbox{

    public CheckableCheckbox(WebElement element, ErrorMessage message, ErrorDecoration decoration) {
        super(element);
        this.elementName = "checkbox (required)";
        this.message = message;
        this.decoration = decoration;
    }
    public CheckableCheckbox(WebElement element, WebElement wrapper, ErrorMessage message, ErrorDecoration decoration) {
        super(element, wrapper);
        this.elementName = "checkbox (required)";
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
