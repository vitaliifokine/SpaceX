package elements.detailed;

import org.openqa.selenium.WebElement;

import java.util.List;

public class Wrappers extends ClickableElements {

    public Wrappers(List<WebElement> wrappers, String name) {
        super(wrappers, name);
        this.fullName.setClassification("wrappers");
    }


}
