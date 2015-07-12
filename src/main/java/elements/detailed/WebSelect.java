package elements.detailed;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

public class WebSelect extends PageElement {

    public WebSelect(WebElement select, String name) {
        super(select, name);
        this.element = select;

        this.fullName.setClassification("select");
    }

    public Result<Void> selectText (String text) {
        boolean status = false;
        String message;
        Select select = new Select(element());
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                select.selectByVisibleText(text);
                status = true;
                //todo add checkout of proper selection.
                message = "Option with text '"+text+"' selected successfully."; // ?
            } else {
                message = "Selection wasn't successful. " + appearance.getDescription();
            }
        } catch (WebDriverException e) {
            message = "Unable to select text '"+text+"' in "+ fullName +" using locator '"+getLocator()+"'.\nCause: " + e.toString();
        }
        return new Result<Void>(status, null, message);
    }
    public Result<Void> selectIndex (int index) {
        boolean status = false;
        String message;
        Select select = new Select(element());
        try {
            Result<Boolean> appearance = appears();
            if (appears().getValue()) {
                select.selectByIndex(index);
                status = true;
                //todo add checkout of proper selection.
                message = "Option with index '" + index + "' selected successfully."; // ?
            } else {
                message = "Selection wasn't successful. " + appearance.getDescription();
            }
        } catch (WebDriverException e) {
            message = "Unable to select index '"+index+"' in "+ fullName +" using locator '"+getLocator()+"'.\nCause: " + e.toString();
        }
        return new Result<Void>(status, null, message);
    }
    public Result<String> getSelectedOptionText() {
        boolean status = false;
        String message;
        String result = "";
        Select select = new Select(element());
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                result = select.getFirstSelectedOption().getText();
                message = "Text got successfully.";
                status = true;
            } else {
                message = "Getting of selected option text wasn't successful. " + appearance.getDescription();
            }
        } catch (WebDriverException e) {
            message = ("Unable to get selected option text in "+ fullName +" using locator '"+getLocator()+"'.\nCause: " + e.toString());
        }
        return new Result<String>(status, result, message);
    }
    public Result<Void> selectOptionContainsText(String text) {
        boolean status = false;
        String message;
        Select select = new Select(element());
        try {
            Result<Boolean> appearance = appears();
            if (appearance.getValue()) {
                String requiredChoice = "";

                for (WebElement option : select.getOptions()) {
                    if (option.getText().contains(text)) {
                        requiredChoice = option.getText();
                        status = true;
                    }
                }
                if (status) {
                    selectText(requiredChoice);
                    message = "Text contains text '" + requiredChoice + "' found successfully.";
                } else {
                    ArrayList<String> optionsTexts = new ArrayList<String>(select.getOptions().size());
                    for (WebElement option : select.getOptions()) {
                        optionsTexts.add(option.getText());
                    }
                    message = "Option contains text '"+text+"' not found. Available options: " + optionsTexts;
                }
            } else {
                message = "Selection by containing text wasn't successful. " + appearance.getDescription();
            }
        } catch (WebDriverException e) {
            message = "Unable to selected option contains text '"+text+"' in "+ fullName +
                    " using locator '"+getLocator()+"'.\nCause: " + e.getClass().getSimpleName();

        }
        return new Result<Void>(status, null, message);
    }


    private WebElement element;

}
