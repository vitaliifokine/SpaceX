package elements.deprecated;

import org.openqa.selenium.WebElement;

import java.util.List;

public class ErrorList extends PageElements {

    public ErrorList(List<WebElement> elements) {
        super(elements);
        this.elementListName = "error list";
    }

}
