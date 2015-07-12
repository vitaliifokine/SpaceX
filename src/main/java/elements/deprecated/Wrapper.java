package elements.deprecated;

import org.openqa.selenium.WebElement;

public class Wrapper extends ClickableElement {

    public Wrapper(WebElement element) {
        super(element);
        this.elementName = "wrapper";
    }

}
