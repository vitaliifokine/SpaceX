package elements.detailed;

import org.openqa.selenium.WebElement;

import java.util.List;

public class ErrorList extends PageElements {

    public ErrorList(List<WebElement> elements, String name) {
        super(elements, name);
        this.fullName.setClassification("error list");
    }

}
