package concrete.sid;

import elements.detailed.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AccreditationJournalTable {

    WebTable tableAccreditation;

    public AccreditationJournalTable(WebDriver driver){
        PageFactory.initElements(driver, this);
        tableAccreditation = new WebTable(driver, "//div[@class='table-responsive']/table[@class='table table-hover table-striped table-bordered']");
        setUp();
    }

    public Button badgeButton() {
        return this.badge;
    }
    public Button criteriaSelect() {
        return this.criteria;
    }
    public WebSelect statusSelect() {
        return this.status;
    }
    public Elements<Checkbox> checkBoxes() {
        return this.checkboxes;
    }
    public  WebSelect badgeselect() {
        return this.badgesel;
    }


    @FindBy(xpath = "//*[@id='accreditation-grid']/table/thead/tr[2]/td[3]/div/button") private WebElement badgeSelected;
    @FindBy(xpath = "//*[@id='accreditation-grid']/table/thead/tr[2]/td[4]/div/button") private WebElement criteriaSelected;
    @FindBy(xpath = "//*[@id='Accreditation_active']") private WebElement statusSelect;
    //inputfield
    @FindBy(xpath = "//*[@id='Accreditation_created']") private WebElement dateAccreditations;
    @FindBy(xpath = "//*[@id='Accreditation_authorName']") private WebElement AuthorAccreditations;
    @FindBy(xpath = "//*[@id='Accreditation_sid']']") private WebElement sidAccreditations;
    @FindBy(xpath = "//*[@id='Accreditation_name']") private WebElement nameAccreditations;
    @FindBy(xpath = "//*[@id='Accreditation_lastName']") private WebElement lastnameAccreditations;
    @FindBy(xpath = "//*[@id='Accreditation_company']") private WebElement companyAccreditations;
    @FindBy(xpath = "//*[@id='Accreditation_prints']") private WebElement printsAccreditations;
    @FindBy(xpath = "//*[@id='Accreditation_ticketCode']") private WebElement ticketCodeAccreditations;
    @FindBy(css = ".dropdown-menu li") private List<WebElement> options;
    @FindBy(css = ".multiselect-container.dropdown-menu.pull-right") private WebElement badgeSelect;

    private void setUp() {
        this.badge = new Button(badgeSelected, "badge");
        this.criteria = new Button(criteriaSelected, "criteria");
        this.status = new WebSelect(statusSelect, "status");
        this.checkboxes = new Elements<Checkbox>(Checkbox.class , options, "participant");
        this.badgesel = new WebSelect(badgeSelect , "selectBadge");

    }
    private Button badge;
    private Button criteria;
    private WebSelect status;
    private Elements<Checkbox> checkboxes;
    private WebSelect badgesel;


}
