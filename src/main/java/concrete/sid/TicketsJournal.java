package concrete.sid;

import elements.detailed.ClickableElement;
import elements.detailed.WebTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class TicketsJournal {

    public TicketsJournal(WebDriver driver){
        PageFactory.initElements(driver, this);
        table = new WebTable(driver, "//div[not (@class='hide')]/table[@class='items']");
        setUp();
    }

    WebTable table;
    public ClickableElement depositBox() {
        return this.deposit;
    }
    public ClickableElement withdrawBox() {
        return this.withdraw;
    }

    @FindBy(xpath = ".//*[@id='deposit']//*[@class = 'checkbox']") private WebElement depositCheckbox;
    @FindBy(xpath = ".//*[@id='withdraw']//*[@class = 'checkbox']") private WebElement withdrawCheckbox;


    private void setUp() {
        this.deposit = new ClickableElement(depositCheckbox, "�������");
        this.withdraw = new ClickableElement(withdrawCheckbox, "�����");

    }
    private ClickableElement deposit;
    private ClickableElement withdraw;


}
