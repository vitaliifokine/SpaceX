package concrete.testcasino;




import elements.detailed.ClickableElement;
import elements.detailed.RadioButton;
import elements.detailed.WebTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebDriver;

public class HistoryTransactions {

    public HistoryTransactions(WebDriver driver){
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
        this.deposit = new ClickableElement(depositCheckbox, "Депозит");
        this.withdraw = new ClickableElement(withdrawCheckbox, "Вывод");

    }
    private ClickableElement deposit;
    private ClickableElement withdraw;




}
