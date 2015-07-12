package sid;

import elements.detailed.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TicketsJournal {


    WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get("http://tat-vegas.valentin.dev.devcaz.com/ru/cabinet/account/history");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.driver.close();
        this.driver.quit();

    }

    @Test
    public void testSIDTable() {
        driver.get("http://develop.preprod.sid.exp0.in/sign_in");
        driver.findElement(By.xpath("//*[@id='LoginForm_login']")).sendKeys("lalakolesnik@mail.ru");
        driver.findElement(By.xpath("//*[@id='LoginForm_password']")).sendKeys("123456789");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-block")).click();
        pause(1000);
        driver.get("http://develop.preprod.sid.exp0.in/company/oracle/event/oraclesupershow/report/tickets");
        WebTable table = new WebTable(driver);
        console(table.inColumnNamed("Order ID").useFilter(">510000").rowWithExactText("516667").cell("Payment Date").getText());
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
