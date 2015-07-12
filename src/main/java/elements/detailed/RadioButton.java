package elements.detailed;

import org.openqa.selenium.WebElement;

public class RadioButton extends BooleanButton{

    public RadioButton(WebElement element, String name) {
        super(element, name);
        this.fullName.setClassification("radio button");
    }
    public RadioButton(WebElement element, WebElement wrapper, String name) {
        super(element, wrapper, name);
        this.fullName.setClassification("radio button");
    }

}
