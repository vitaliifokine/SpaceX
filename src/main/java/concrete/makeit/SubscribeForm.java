package concrete.makeit;



import elements.deprecated.Button;
import elements.deprecated.CheckableInputField;
import elements.deprecated.ErrorMessage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SubscribeForm {

    public SubscribeForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
        setUp();
    }

    @FindBy (css = ".field-formsubscribe-email + input.btn") WebElement buttonSend;
    @FindBy (id = "input-01") WebElement fieldName;
    @FindBy (id = "input-02") WebElement fieldEmail;
    @FindBy (css = ".field-formsubscribe-name .help-block") WebElement errorMessage_name;
    @FindBy (css = ".field-formsubscribe-email .help-block") WebElement errorMessage_email;

    private CheckableInputField name;
    private CheckableInputField email;
    private Button send;

    public Button sendButton() {
        return this.send;
    }
    public CheckableInputField nameField() {
        return this.name;
    }
    public CheckableInputField emailField() {
        return this.email;
    }

    private void setUp() {
        this.name = new CheckableInputField(fieldName, new ErrorMessage(errorMessage_name), null);
        this.email = new CheckableInputField(fieldEmail, new ErrorMessage(errorMessage_email), null);
        this.send = new Button(buttonSend);
    }
}
