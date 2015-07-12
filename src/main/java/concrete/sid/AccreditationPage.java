package concrete.sid;

import elements.detailed.*;
import elements.myelems.UserCardConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccreditationPage {

    public AccreditationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        setUp();
    }

    public InputField searchForAccreditation() {
        return this.searchAc;
    }

    public Link userNameInPanelResult() {
        return this.userNameResult;
    }

    public TextContainer companyInPanelResult() {
        return this.companyInPanel;
    }

    public Button approveButton() {
        return this.approveInPanel;
    }

    public Button printBadge() {
        return this.printBadgeInPanel;
    }

    public UserCardConditions userCardConditions() {
        return this.conditions;
    }

    @FindBy(css = "#search-field")
    private WebElement searchAccreditationField;

    //in panel
    @FindBy(xpath = "//*[@class='col-md-6']//*[@class='user-name']")
    private WebElement userNameInPanel;
    @FindBy(css = ".col-md-6.company-name.text-muted")
    private WebElement companyNameInPanel;
    @FindBy(xpath = "//input[contains(@class, 'btn')][@value='Approve']")
    private WebElement approveButton;
    @FindBy(xpath = "//*[@id='open-print-70d5'")
    private WebElement printButton;
    //rows in panel

    //modal-body
    @FindBy(xpath = "//*[@class='modal-content']//div[@class='modal-body']")
    private WebElement modalbodyAccreditation;
    @FindBy(xpath = "//*[@class='modal-header']//*[@class='modal-title']")
    private WebElement modaltitleAccreditation;
    @FindBy(className = "acc-res-block")
    private List<WebElement> keys;
    @FindBy(className = "label-info")
    private List<WebElement> values;

    private void setUp() {
        this.searchAc = new InputField(searchAccreditationField, "Поле_поиска_при_аккредитации");
        this.userNameResult = new Link(userNameInPanel, "Имя_пользователя_в_панели_результатов");
        this.companyInPanel = new TextContainer(companyNameInPanel, "Компания_в_панели_результатов");
        this.approveInPanel = new Button(approveButton, "Утвердить_аккредитацию");
        this.printBadgeInPanel = new Button(printButton, "Расспечатать_бейдж");
        this.conditions = new UserCardConditions(keys, values);
    }

    private InputField searchAc;
    private Link userNameResult;
    private TextContainer companyInPanel;
    private Button approveInPanel;
    private Button printBadgeInPanel;
    private UserCardConditions conditions;

    public  String getThirdWordInString(String resultName) {
        String temp;
        String[] result = resultName.split(" ");
        temp = result[2];
        return temp;
    }
    public  boolean checkEqualsSID (String resultName, String userSID){
        String userSID1 = "(" + userSID + ")";
        return getThirdWordInString(resultName).equals(userSID1);
    }
}

        /*Pattern p = Pattern.compile(".+\\.("+b+")");
        Matcher m = p.matcher(a);
        return m.matches();*/