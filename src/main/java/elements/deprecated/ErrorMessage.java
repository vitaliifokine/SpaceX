package elements.deprecated;

import org.openqa.selenium.WebElement;

public class ErrorMessage extends TextContainer{

    public ErrorMessage(WebElement element) {
        super(element);
        this.elementName = "error message";
    }

}
