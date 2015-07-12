package sid;

import basePageObject.TempMail;
import concrete.sid.AccreditationJournalTable;
import concrete.sid.AccreditationModalBody;
import concrete.sid.AccreditationPage;
import concrete.sid.Visitors;
import datas.ValidationData;
import elements.detailed.Button;
import elements.detailed.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.RandomGenerator;

/**
 this test performed in the test site
 */
public class CycleOfRegistrationAndAuthorization {

    @Test(dependsOnMethods = "TempMailBeforeLogin")
    public void createRegistration(){
            driver.get("http://develop.preprod.sid.exp0.in/company/oracle/event/oraclesupershow/visitor/index");
            console(visitors.createRegistrationButton().appears());
            console(visitors.createRegistrationButton().click());
            visitors.nameField().clear();
            nameVisitor = RandomGenerator.getStringOfLetters(10);
            visitors.nameField().sendText(nameVisitor);
            visitors.emailField().clear();
            console(visitors.emailField().sendText(userMail));
            visitors.lastName().clear();
            console(visitors.lastName().sendText(RandomGenerator.getStringOfLetters(10)));
            console(visitors.company().appears());
            visitors.company().clear();
            console(visitors.company().sendText(RandomGenerator.getStringOfLetters(1).toUpperCase() + RandomGenerator.getStringOfLetters(6)));
            visitors.phone().clear();
            visitors.phone().sendText(RandomGenerator.getRandomPhoneNumber(9));
            console(visitors.create().appears());
            visitors.create().click();
            pause(1000);

        }

    @Test(dependsOnMethods = "createRegistration")
    public void newRegistrationTableOfVisitors(){ //
        WebTable table = new WebTable(driver);
        console(table.getAllColumnNames());
        console(table.tableSize());
        console(table.inColumnNamed("Name").useFilter(nameVisitor));
        console(table.rowNumber(1).cell("Name").getText().getValue());
        console(table.rowNumber(1).cell("#SID").getText());
        userSID = table.rowNumber(1).cell("#SID").getText().getValue();
//      console(table.inColumnNamed("Name").useFilter("drsuqpeteg").getTextsFromElementsInBody().getValue().size());
    }

    @Test(dependsOnMethods = "newRegistrationTableOfVisitors")
    public void accreditationUser(){
        String resultName;
        driver.get("http://develop.preprod.sid.exp0.in/company/oracle/event/oraclesupershow/accreditation/index");
        accreditation.searchForAccreditation().clear();
        accreditation.searchForAccreditation().sendText(nameVisitor);
        accreditation.searchForAccreditation().sendKeys(Keys.RETURN);
        console(accreditation.userNameInPanelResult().getText().getValue());
        //driver.findElement(By.xpath("//input[contains(@class, 'btn')][@value='Approve']")).click();
        console(accreditation.approveButton().appears());
        console(accreditation.userCardConditions().getTextAgainst("Registration"));
        console(accreditation.userCardConditions().getTextAgainst("Tickets"));
        console(accreditation.userCardConditions().getTextAgainst("Accreditation"));
        console(accreditation.userCardConditions().getTextAgainst("Badge Printed"));
        resultName = accreditation.userNameInPanelResult().getText().getValue();
        console(accreditation.checkEqualsSID(resultName,userSID));
        accreditation.approveButton().click();
        console(accreditationModalBody.modalBodyAc().appears());
        console(accreditationModalBody.attendeeTypeSelect().selectText("Гость"));
        console(accreditationModalBody.categorySelect().selectText("Посетитель_выставки [1000]"));





        //accreditationModalBody.attendeeTypeSelect().selectText("Гость");
       // accreditationModalBody.categorySelect().selectText("");
       // accreditationModalBody.approveOrderButton().click();
    }


    @Test//(dependsOnMethods = "newRegistrationTableOfVisitors")
    public void createAccreditation(){
        driver.get("http://develop.preprod.sid.exp0.in/company/oracle/event/oraclesupershow/accreditation/index");
        console(accreditation.searchForAccreditation().appears());
        accreditation.searchForAccreditation().clear();
        accreditation.searchForAccreditation().sendText(nameVisitor);
        accreditation.searchForAccreditation().sendKeys(Keys.RETURN);
        pause(1000);
    }
    @Test
    public void checkAccreditationJournal(){

    }


    @Test
    public void Login() {
        userMail = tempmail.goAndGetNewEmail();
        driver.get("http://v2.smart-id.ru/sign_in");
        driver.findElement(By.cssSelector("#LoginForm_login")).sendKeys("lalakolesnik@mail.ru");
        driver.findElement(By.cssSelector("#LoginForm_password")).sendKeys("123456789");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-block")).click();
        pause(1000);

    }
    @Test
    public void TempMailBeforeLogin() {
        userMail = tempmail.goAndGetNewEmail();
        driver.get("http://develop.preprod.sid.exp0.in");
        driver.findElement(By.cssSelector("#LoginForm_login")).sendKeys("lalakolesnik@mail.ru");
        driver.findElement(By.cssSelector("#LoginForm_password")).sendKeys("123456789");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-block")).click();
        pause(1000);
    }
    @Test//(dependsOnMethods = "Login")
    public void testSIDTable() {
        driver.get("http://develop.preprod.sid.exp0.in/company/oracle/event/oraclesupershow/report/tickets");
        WebTable table = new WebTable(driver);
        console(table.inColumnNamed("Order ID").useFilter(">510000").rowWithExactText("516667").cell("Payment Date").getText());
    }
    @Test//(dependsOnMethods = "Login")
    public void testGeneralization() {
        driver.get("http://develop.preprod.sid.exp0.in/company/oracle/event/oraclesupershow/report/accreditations");
        Button button = journal.badgeButton();
        console(button.appears());
        console(button.getText());
        console(journal.badgeButton().appears());
        console(journal.badgeButton().click());
        console(journal.checkBoxes().getElementInPosition(1).getValue().click());
        console(journal.checkBoxes().getElementInPosition(1).getValue().click());
        console("");
    }

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
        accreditationModalBody = new AccreditationModalBody(driver);

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.driver.close();
        this.driver.quit();
    }

    @Test
    public void test1() {
        System.out.println(Thread.currentThread().getId());
    }
    @Test
    public void test2() {
        System.out.println(Thread.currentThread().getId());
    }
    @Test
    public void test3() {
        System.out.println(Thread.currentThread().getId());
    }
    @Test
    public void test4() {
        System.out.println(Thread.currentThread().getId());
    }
    @Test
    public void test5() {
        System.out.println(Thread.currentThread().getId());
    }

    @Test(dataProviderClass = ValidationData.class, dataProvider = "invalidEmails")
    public void testMails(String mail) {
        System.out.println(mail);
    }

    private String nameVisitor;
    private String userSID;
    private String userMail;
    private TempMail tempmail;
    private WebDriver driver;
    private AccreditationJournalTable journal;
    private AccreditationPage accreditation;
    private AccreditationModalBody accreditationModalBody;
    private Visitors visitors;

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
