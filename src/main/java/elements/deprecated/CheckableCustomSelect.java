package elements.deprecated;

import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckableCustomSelect extends CustomSelect{

    public CheckableCustomSelect(WebElement element, List<WebElement> options, ErrorMessage message, ErrorDecoration decoration) {
        super(element, options);
        this.message = message;
        this.decoration = decoration;
        this.elementName = "custom select (required)";
    }
    public CheckableCustomSelect(WebElement element, WebElement wrapper, List<WebElement> options, ErrorMessage message, ErrorDecoration decoration) {
        super(element, wrapper, options);
        this.message = message;
        this.decoration = decoration;
        this.elementName = "custom select (required)";
    }

    public ErrorDecoration errorDecoration() {
        return this.decoration;
    }
    public ErrorMessage errorMessage() {
        return this.message;
    }

    private ErrorMessage message;
    private ErrorDecoration decoration;
}
