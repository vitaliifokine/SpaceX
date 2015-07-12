package basePageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *This class designed to provide basis for pages. Basic actions with pages
 */
public class BasePage {
    private WebDriver driver;
    public WebDriverWait wait;
    private String PAGE_URL;
    private String PAGE_TITLE;

    protected void setPAGE_URL(String url) {
        this.PAGE_URL = url;
    }

    protected void setPAGE_TITLE(String title) {
        this.PAGE_TITLE = title;
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void load(String URL) {
        driver.get(URL);
        boolean getOut = false;
        boolean loaded = true;
        int retry = 2;
        int tryCounter = 0;
        while (!getCurrentUrl().equals(URL) && !getOut) {
            tryCounter++;
            driver.get(URL);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tryCounter >= retry) {
                getOut = true;
                loaded = false;
            }
        }
        if (!loaded) {
            System.err.println("I tried to load page using URL = " + URL + " and made " + retry + " retries. But actual loaded URL = '" + getCurrentUrl() + "' does not equals required.");
        }
        String actualTitle = getCurrentTitle();
        String requiredTitle = getPageTitle();
        if (!actualTitle.equals(requiredTitle)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            actualTitle = getCurrentTitle();
            if (!actualTitle.equals(requiredTitle)) {
                System.err.println("ERROR: Actual title: " + actualTitle + "\nis not equals required title: " + requiredTitle);
            }
        }

    }

    public void load() {
        driver.get(getPageUrl());
        boolean getOut = false;
        boolean loaded = true;
        int retry = 2;
        int tryCounter = 0;
        while (!getCurrentUrl().equals(getPageUrl()) && !getOut) {
            tryCounter++;
            driver.get(getPageUrl());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tryCounter >= retry) {
                getOut = true;
                loaded = false;
            }
        }
        if (!loaded) {
            System.err.println("I tried to load page using URL = " + getPageUrl() + " and made " + retry + " retries. But actual loaded URL = '" + getCurrentUrl() + "' does not equals required.");
        }
        String actualTitle = getCurrentTitle();
        String requiredTitle = getPageTitle();
        if (!actualTitle.equals(requiredTitle)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            actualTitle = getCurrentTitle();
            if (!actualTitle.equals(requiredTitle)) {
                System.err.println("ERROR: Actual title: " + actualTitle + "\nis not equals required title: " + requiredTitle);
            }
        }

    }

    public void loadWithNoCheck() {
        driver.get(getPageUrl());
    }

    public void loadAndCheckRedirection(BasePage page) {
        driver.get(getPageUrl());
        boolean getOut = false;
        boolean loaded = true;
        int retry = 2;
        int tryCounter = 0;
        while (!getCurrentUrl().equals(page.getPageUrl()) && !getOut) {
            tryCounter++;
            driver.get(getPageUrl());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tryCounter >= retry) {
                getOut = true;
                loaded = false;
            }
        }
        if (!loaded) {
            System.err.println("I tried to load page using URL = " + getPageUrl() + " and made " + retry + " retries. But actual loaded URL = '" + getCurrentUrl() + "' does not equals required URL for redirection = '" + page.getPageUrl() + "'.");
        }
        String actualTitle = getCurrentTitle();
        String requiredTitle = page.getPageTitle();
        if (!actualTitle.equals(requiredTitle)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            actualTitle = getCurrentTitle();
            if (!actualTitle.equals(requiredTitle)) {
                System.err.println("ERROR: Actual title: " + actualTitle + "\nis not equals required title: " + requiredTitle);
            }
        }
    }

    public void deleteCookies() {
        driver.manage().deleteAllCookies();
    }

    public void deleteCookie(String cookieName) {
        driver.manage().deleteCookieNamed(cookieName);
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void forceLogout() {
        deleteCookies();
        refresh();
    }

    public boolean checkLoaded() {
        if (getPageUrl().equals(getCurrentUrl())) {
            return true;
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (getPageUrl().equals(getCurrentUrl())) {
                return true;
            } else {
                System.err.println("Current URL '" + getCurrentUrl() + "' not matches required URL '" + getPageUrl() + "'");
                return false;
            }
        }
    }

    public boolean checkNotLoaded() {
        return !getPageUrl().equals(getCurrentUrl());
    }

    public String getPageUrl() {
        if (PAGE_URL != null) {
            return PAGE_URL;
        } else {
            System.err.println("WARNING: PAGE_URL is null. Check out for right pointer set up.");
            return "";
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        if (PAGE_TITLE != null) {
            return PAGE_TITLE;
        } else {
            System.err.println("WARNING: PAGE_TITLE is null. Check out for right pointer set up.");
            return "";
        }
    }

    public String getCurrentTitle() {
        return driver.getTitle();
    }

    protected void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
