package makeit;

import basePageObject.TempMail;
import concrete.makeit.SubscribeForm;
import concrete.makeit.SubscribePopUp;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.RandomGenerator;

public class SubscribeFromFooterTests {

    WebDriver driver;
    SubscribeForm subscribeForm;
    SubscribePopUp subscribePopUp;
    TempMail tempEmail;
    String tempeMail;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        subscribeForm = new SubscribeForm(driver);
        subscribePopUp = new SubscribePopUp(driver);
        tempEmail = new TempMail(driver);
        tempeMail = tempEmail.goAndGetNewEmail();
        driver.get("http://makeitshow.ru/ru");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.driver.close();
        this.driver.quit();

    }

    @Test
    public void sendEmptyFieldName() { //
        console(subscribeForm.nameField().isClear());
        subscribeForm.nameField().setText(RandomGenerator.getStringOfLetters(10));
        console(subscribeForm.nameField().isClear());
        subscribeForm.nameField().clear();
        console(subscribeForm.nameField().isClear());
        subscribeForm.sendButton().click();
        console(subscribeForm.sendButton().getText());
        console(subscribeForm.nameField().errorMessage().appears());
        console(subscribeForm.nameField().errorMessage().getText());
    }
    @Test
    public void testSubscribePopup() { //

        console(subscribePopUp.body().appears(10));
        console(subscribePopUp.closeCross().appears());
        console(subscribePopUp.closeCross().getTagName());
        console(subscribePopUp.bigTextContainer().getText());
        console(subscribePopUp.smallTextContainer().getText());
        console(subscribePopUp.sendButton().getText());
        subscribePopUp.sendButton().click();
        console(subscribePopUp.nameField().errorMessage().appears());
        console(subscribePopUp.nameField().errorMessage().getText());
        subscribePopUp.nameField().setText(RandomGenerator.getStringOfLetters(5));
        subscribePopUp.nameField().sendKeys(Keys.RETURN);
        console(subscribePopUp.nameField().errorMessage().disappears());
        console(subscribePopUp.emailField().errorMessage().appears());
        console(subscribePopUp.emailField().errorMessage().getText());
        subscribePopUp.nameField().clear();
        subscribePopUp.emailField().setText(tempeMail);
        subscribePopUp.emailField().sendKeys(Keys.RETURN);
        console(subscribePopUp.emailField().errorMessage().disappears());
        subscribePopUp.closeCross().click();
        console(subscribePopUp.body().disappears());

    }
    @Test
    public void correctSubscription(){
        popUpClose();
        console(subscribeForm.nameField().isClear());
        subscribeForm.nameField();
    }

    public void popUpClose(){
        console(subscribePopUp.body().appears(10));
        console(subscribePopUp.closeCross().appears());
        console(subscribePopUp.closeCross().getTagName());
        console(subscribePopUp.bigTextContainer().getText());
        console(subscribePopUp.smallTextContainer().getText());
        subscribePopUp.sendButton().click();
    }
    @Test
    private void getTempMail() {
        String tempeMail = tempEmail.goAndGetNewEmail();
    }



    private void console(Object ob) {
        System.out.println(ob);
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}