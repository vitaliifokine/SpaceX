package elements.deprecated;

import org.openqa.selenium.WebElement;

public class CheckableSelect extends WebSelect{

    public CheckableSelect(WebElement select, ErrorMessage message, ErrorDecoration decoration) {
        super(select);
        this.message = message;
        this.decoration = decoration;
        this.elementName = "select (required)";
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
