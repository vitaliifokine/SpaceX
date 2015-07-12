package concrete.sid;


import elements.detailed.Button;
import elements.detailed.InputField;
import elements.detailed.WebTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Visitors {

    public Visitors(WebDriver driver){
        PageFactory.initElements(driver, this);
        table = new WebTable(driver, "//div[@class='table-responsive']/table[@class='table table-hover table-striped table-bordered']");
        setUp();
    }
    WebTable table;

    public Button createRegistrationButton(){
        return this.createRegistration;
    }
    public InputField nameField(){
        return this.nameFieldReg;
    }
    public InputField emailField(){
        return this.emailFieldReg;
    }
    public InputField lastName(){
        return this.lastNameReg;
    }
    public InputField company(){
        return this.companyReg;
    }
    public InputField phone(){
        return this.phoneReg;
    }
    public Button create(){
        return this.createReg;
    }



        //create registration
    @FindBy(xpath = "//*[@id='yw1']/li/a") private WebElement createRegistrationButton;
    @FindBy(xpath = "//*[@id='Registration_243']") private WebElement nameFieldOnCreateRegistration;
    @FindBy(xpath = "//*[@id='Registration_246']") private WebElement emailFieldOnCreateRegistration;
    @FindBy(xpath = "//*[@id='Registration_249']") private WebElement lastNameFieldOnCreateRegistration;
    @FindBy(css= "#Registration_252") private WebElement companyFieldOnCreateRegistration;
    @FindBy(css = "#Registration_255") private WebElement phoneFieldOnCreateRegistration;
    @FindBy(css = ".btn.btn-primary.btn-block") private WebElement createButtonAfterFillingFields;



    private void setUp(){
        this.createRegistration = new Button(createRegistrationButton, "Create Registration ");
        this.nameFieldReg = new InputField(nameFieldOnCreateRegistration, "Name");
        this.emailFieldReg = new InputField(emailFieldOnCreateRegistration, "Email");
        this.lastNameReg = new InputField(lastNameFieldOnCreateRegistration, "Last Name");
        this.companyReg = new InputField(companyFieldOnCreateRegistration, "Company");
        this.phoneReg = new InputField(phoneFieldOnCreateRegistration, "Phone");
        this.createReg = new Button(createButtonAfterFillingFields, "Create Button");

    }
    private Button createRegistration;
    private InputField nameFieldReg;
    private InputField emailFieldReg;
    private InputField lastNameReg;
    private InputField companyReg;
    private InputField phoneReg;
    private Button createReg;

}
