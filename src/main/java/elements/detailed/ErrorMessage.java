package elements.detailed;

import org.openqa.selenium.WebElement;

public class ErrorMessage extends TextContainer{

    public ErrorMessage(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("error message");
    }

}
