package concrete.makeit;

import elements.deprecated.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 This class describe popup and his behaves
 */
public class SubscribePopUp {

    public SubscribePopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        setUp();
    }

    public Button sendButton() {
        return this.submit;
    }
    public ClickableElement closeCross() {
        return this.close;
    }
    public CheckableInputField nameField() {
        return this.name;
    }
    public CheckableInputField emailField() {
        return this.email;
    }
    public TextContainer bigTextContainer() {
        return this.big;
    }
    public TextContainer smallTextContainer() {
        return this.small;
    }
    public PageElement body() {
        return this.body;
    }

    private void setUp() {
        this.name = new CheckableInputField(fieldName, new ErrorMessage(fieldName_errorMessage), null);
        this.email = new CheckableInputField(fieldEmail, new ErrorMessage(fieldEmail_errorMessage), null);
        this.submit = new Button(buttonSubmit);
        this.close = new ClickableElement(closeCross);
        this.big = new TextContainer(bigTextContainer);
        this.small = new TextContainer(smallTextContainer);
        this.body = new PageElement(popupBody);
    }

    @FindBy(css = ".popup.form") private WebElement popupBody;
    @FindBy(css = ".close") private WebElement closeCross;
    @FindBy(id = "input-popup-01") private WebElement fieldName;
    @FindBy(css = ".field-formsubscribe-name .help-block") private WebElement fieldName_errorMessage;
    @FindBy(id = "input-popup-02") private WebElement fieldEmail;
    @FindBy(css = ".field-formsubscribe-email .help-block") private WebElement fieldEmail_errorMessage;
    @FindBy(css = "#subscribe-form-popup .btn") private WebElement buttonSubmit;
    @FindBy(css = ".popup.form>h2") private WebElement bigTextContainer;
    @FindBy(css = ".popup.form>p") private WebElement smallTextContainer;

    private CheckableInputField name;
    private CheckableInputField email;
    private Button submit;
    private ClickableElement close;
    private TextContainer big;
    private TextContainer small;
    private PageElement body;

}

