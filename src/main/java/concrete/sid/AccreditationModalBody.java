package concrete.sid;


import elements.detailed.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccreditationModalBody {

    public AccreditationModalBody(WebDriver driver){
        PageFactory.initElements(driver, this);
        setUp();
    }

    public PageElement modalBodyAc() {
       return this.modalBody;
    }
    public WebSelect attendeeTypeSelect() {
        return this.attendeeType;
    }
    public WebSelect categorySelect() {
        return this.category;
    }
    public Button approveOrderButton() {
        return this.approveOrder;
    }
    public Button approveAccreditationButton() {
        return this.approveAccreditation;
    }

    //modal-body
    @FindBy(xpath = "//*[@class='modal-dialog modal-lg']") private WebElement modalbodyAccreditation;
    @FindBy(xpath = "//*[@id='AccreditationForm_badgeType']") private WebElement attendeeTypeSelect;
    @FindBy(xpath = "//*[@id='AccreditationForm_badgeCriteria']") private WebElement categorySelect;
    @FindBy(xpath = "//*[@id='new-order-btn']") private WebElement approveOrderBut;
    @FindBy(xpath = "//*[@id='new-acc-btn']]") private WebElement approveAccreditationBut;

    private void setUp() {
        this.modalBody = new PageElement(modalbodyAccreditation, "Modal_body_accreditation");
        this.attendeeType = new WebSelect(attendeeTypeSelect, "Attendee_type_select");
        this.attendeeType = new WebSelect(categorySelect, "Category_select");
        this.approveOrder = new Button(approveOrderBut, "Approve_order_Button");
        this.approveAccreditation = new Button(approveAccreditationBut, "Approve_accreditation_Button");


    }
    private PageElement modalBody;
    private WebSelect attendeeType;
    private WebSelect category;
    private Button approveOrder;
    private Button approveAccreditation;


}





