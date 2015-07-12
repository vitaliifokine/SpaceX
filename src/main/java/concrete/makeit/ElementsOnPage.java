package concrete.makeit;



import elements.deprecated.Button;
import elements.deprecated.ClickableElement;
import elements.deprecated.TextContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/**
 This class is describes all basic elements on page
 */
public class ElementsOnPage {

    public ElementsOnPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        setUp();
    }

    public ClickableElement logoMain(){
        return  this.logo;
    }
    public TextContainer captionMain(){
        return  this.caption;
    }
    public Button menuButton(){
        return  this.menu;
    }
    public Button rusLang(){
        return  this.ru;
    }
    public Button engLong(){
        return this.eng;
    }


    @FindBy(css = ".logo-big") private WebElement logoBig;
    @FindBy(css = ".menu-navigation>span") private WebElement blockMenu;
    @FindBy(css = ".ru.active") private WebElement rusLanguage;
    @FindBy(css = ".en") private WebElement engLanguage;
    @FindBy(css = ".caption") private WebElement captionGeneral;

    //tags-categories
    @FindBy(css = ".science>a>img") private WebElement scienceCategory;
    @FindBy(css = ".tech>a>img") private WebElement techCategory;
    @FindBy(css = ".art>a>img") private WebElement artCategory;
    @FindBy(css = ".eco>a>img") private WebElement ecoCategory;
    @FindBy(css = ".fashion>a>img") private WebElement fashionCategory;
    @FindBy(css = ".food>a>img") private WebElement foodCategory;


    private void setUp() {
        this.logo = new ClickableElement(logoBig);
        this.caption = new TextContainer(captionGeneral);
        this.menu = new Button(blockMenu);
        this.ru = new Button(rusLanguage);
        this.eng = new Button(engLanguage);
    }

    private ClickableElement logo;
    private TextContainer caption;
    private Button menu;
    private Button ru;
    private Button eng;

}
