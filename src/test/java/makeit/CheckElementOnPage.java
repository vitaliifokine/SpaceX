package makeit;

import concrete.makeit.ElementsOnPage;
import concrete.makeit.SubscribePopUp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 This class is checking availability all basic elements on page
 */
public class CheckElementOnPage {

    WebDriver driver;
    SubscribePopUp subscribePopUp;
    ElementsOnPage elementsOnPage;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        subscribePopUp = new SubscribePopUp(driver);
        elementsOnPage = new ElementsOnPage(driver);
        driver.get("http://makeitshow.ru/ru");
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.driver.quit();
    }


    @Test
    public void checkElementOnPage(){
        console(subscribePopUp.body().appears(10));
        console(subscribePopUp.closeCross().appears());
        subscribePopUp.closeCross().click();
        console(elementsOnPage.logoMain().appears());
        console(elementsOnPage.menuButton().appears());
        console(elementsOnPage.captionMain().appears());
        console(elementsOnPage.rusLang().appears());
        console(elementsOnPage.engLong().appears());

    }
    private void console(Object ob) {
        System.out.println(ob);
    }
}
