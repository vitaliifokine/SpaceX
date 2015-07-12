package elements.detailed;

import org.openqa.selenium.WebElement;

public class Wrapper extends ClickableElement {

    public Wrapper(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("wrapper");
    }


}
