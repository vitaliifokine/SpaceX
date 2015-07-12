package elements.detailed;

import org.openqa.selenium.WebElement;

public class Checkbox extends BooleanButton{

    public Checkbox(WebElement element, String name)  {
        super(element, name);
        this.fullName.setClassification("checkbox");
    }
    public Checkbox(WebElement element, WebElement wrapper, String name) {
        super(element, wrapper, name);
        this.fullName.setClassification("checkbox");
    }

}
