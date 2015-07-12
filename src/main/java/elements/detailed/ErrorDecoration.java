package elements.detailed;

import org.openqa.selenium.WebElement;

public class ErrorDecoration extends PageElement {

    public ErrorDecoration(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("error decoration");

    }

}
