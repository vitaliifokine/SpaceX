package sid;


import basePageObject.TempMail;
import concrete.sid.AccreditationJournalTable;
import concrete.sid.AccreditationPage;
import concrete.sid.Visitors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

public class Accreditation {

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        driver = new ChromeDriver();
        tempmail = new TempMail(driver);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        this.journal = new AccreditationJournalTable(driver);
        this.accreditation = new AccreditationPage(driver);
        this.visitors = new Visitors(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.driver.close();
        this.driver.quit();
    }



    String nameVisitor;
    String userMail;
    TempMail tempmail;
    WebDriver driver;
    AccreditationJournalTable journal;
    AccreditationPage accreditation;
    Visitors visitors;

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
