package elements.deprecated;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

public class WebSelect extends PageElement {

    public WebSelect(WebElement select) {
        super(select);
        this.select = new Select(element());
        this.elementName = "select";
    }

    public void selectText (String text) {
        try {
            if (appears()) {
                select.selectByVisibleText(text);
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to select text '"+text+"' in "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
        }
    }
    public void selectIndex (int index) {
        try {
            if (appears()) {
                select.selectByIndex(index);
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to select index '"+index+"' in "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
        }
    }
    public String getSelectedOptionText() {
        try {
            if (appears()) {
                return select.getFirstSelectedOption().getText();
            } else {
                return "";
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to get selected option text in "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());
            return "";
        }
    }
    public void selectOptionContainsText(String text) {
        try {
            if (appears()) {
                String requiredChoice = "";
                boolean optionFound = false;
                for (WebElement option : select.getOptions()) {
                    if (option.getText().contains(text)) {
                        requiredChoice = option.getText();
                        optionFound = true;
                    }
                }
                if (optionFound) {
                    selectText(requiredChoice);
                } else {
                    ArrayList<String> optionsTexts = new ArrayList<String>(select.getOptions().size());
                    for (WebElement option : select.getOptions()) {
                        optionsTexts.add(option.getText());
                    }
                    System.err.println("Option contains text '"+text+"' not found. Available options: " + optionsTexts);
                }
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to selected option contains text '"+text+"' in "+elementName+" using locator '"+getLocator()+"'.\nCause: " + e.toString());

        }
    }

    private Select select;

}
