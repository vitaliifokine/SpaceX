package elements.deprecated;

import org.openqa.selenium.WebElement;

import java.util.List;

public class Wrappers extends ClickableElements {

    public Wrappers(List<WebElement> wrappers) {
        super(wrappers);
        this.elementListName = "wrappers";
    }


}
