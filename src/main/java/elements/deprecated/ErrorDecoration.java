package elements.deprecated;

import org.openqa.selenium.WebElement;

public class ErrorDecoration extends PageElement {

    public ErrorDecoration(WebElement element) {
        super(element);
        this.elementName = "error decoration";
    }

}
