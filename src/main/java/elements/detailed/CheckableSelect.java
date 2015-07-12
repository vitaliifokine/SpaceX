package elements.detailed;

import org.openqa.selenium.WebElement;

public class CheckableSelect extends WebSelect{

    public CheckableSelect(WebElement select, String name, ErrorMessage message, ErrorDecoration decoration) {
        super(select, name);
        this.message = message;
        this.decoration = decoration;
        this.fullName.setClassification("select*");
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
