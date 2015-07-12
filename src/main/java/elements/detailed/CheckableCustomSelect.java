package elements.detailed;

import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckableCustomSelect extends CustomSelect{

    public CheckableCustomSelect(WebElement element, List<WebElement> options, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(element, options, name);
        this.message = message;
        this.decoration = decoration;
        this.fullName.setClassification("custom select*");


    }
    public CheckableCustomSelect(WebElement element, WebElement wrapper, List<WebElement> options, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(element, wrapper, options, name);
        this.message = message;
        this.decoration = decoration;
        this.fullName.setClassification("custom select*");

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
