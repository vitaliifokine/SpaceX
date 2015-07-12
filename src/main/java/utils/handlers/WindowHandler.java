package utils.handlers;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class designed to handle inner windows (e.g. 'ulogin' for social pages or finsturm payment system)
 */
public class WindowHandler {

    private static HashMap<String, Boolean> classifiedWindows = new HashMap<String, Boolean>();
    private static HashMap<String, String> handlesNames = new HashMap<String, String>();

    public synchronized static void switchTo(WebDriver driver, String handle) {
        try {
            driver.switchTo().window(handle);
        } catch (NoSuchWindowException e) {
            System.err.println("Window '"+handle+"' not found.");
        }

    }
    public synchronized static void close(WebDriver driver, String handle) {
        try {
            if (!handle.equals(driver.getWindowHandle())) {
                driver.switchTo().window(handle);
            }
            driver.close();
        } catch (NoSuchWindowException e) {
            System.err.println("Window '"+handle+"' not found.");
        }

    }
    public synchronized static void closeCurrent (WebDriver driver) {
        driver.close();
    }
    public synchronized static void switchToWindowWithTitleContains(WebDriver driver, String title) {
        try {
            boolean found = false;
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                if (driver.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.err.println("Window titled with '"+title+"' not found.");
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to switch to window titled '"+title+"'.");
        }
    }
    public synchronized static void setCurrentWindowAsPrimary (WebDriver driver) {
        try {
            boolean alreadyPresent = false;
            for (Map.Entry entry : classifiedWindows.entrySet()) {
                if (!entry.getKey().equals(driver.getWindowHandle())) {
                    entry.setValue(true);
                    alreadyPresent = true;
                    break;
                }
            }
            if (!alreadyPresent) {
                classifiedWindows.put(driver.getWindowHandle(), true);
            }
        } catch (NoSuchWindowException e) {
            System.err.println("Unable to get current window handle");
        }
    }
    public synchronized static void setCurrentWindowAsSecondary (WebDriver driver) {
        try {
            boolean alreadyPresent = false;
            for (Map.Entry entry : classifiedWindows.entrySet()) {
                if (!entry.getKey().equals(driver.getWindowHandle())) {
                    entry.setValue(false);
                    alreadyPresent = true;
                    break;
                }
            }
            if (!alreadyPresent) {
                classifiedWindows.put(driver.getWindowHandle(), false);
            }
        } catch (NoSuchWindowException e) {
            System.err.println("Unable to get current window handle");
        }
    }
    public synchronized static void setAllCurrentWindowsAsPrimary (WebDriver driver) {
        try {
            for (String handle : driver.getWindowHandles()) {
                classifiedWindows.put(handle, true);
            }
        } catch (WebDriverException e) {
            System.err.println("Unable set current windows as primary");
        }
    }
    public synchronized static void closeAllSecondaryWindows (WebDriver driver) {
        try {
            for (String handle : driver.getWindowHandles()) {
                if (!isWindowPrime(handle)) {
                    close(driver, handle);
                }
            }
        } catch (WebDriverException e) {
            System.err.println("Unable to close all secondary windows.");
        }
    }
    public synchronized static int  getWindowsCount (WebDriver driver) {
        return driver.getWindowHandles().size();
    }
    public synchronized static void switchToAnyPrimaryWindow(WebDriver driver) {
        boolean switched = false;
        for (String handle : driver.getWindowHandles()) {
            if (isWindowPrime(handle)) {
                switchTo(driver, handle);
                switched = true;
            }
        }
        if (!switched) {
            System.err.println("No prime windows found");
        }
    }

    private static boolean isWindowPrime(String handle) {
        if (classifiedWindows.get(handle) != null) {
            return classifiedWindows.get(handle);
        } else {
            System.err.println("Handle '"+handle+"' is not classified");
            return false;
        }

    }
    public synchronized static void saveCurrentWindowNameAs(WebDriver driver, String name) {
        handlesNames.put(name, driver.getWindowHandle());
    }
    public synchronized static void switchToWindowNamed(WebDriver driver, String name) {
        for (Map.Entry entry : handlesNames.entrySet()) {
            if (entry.getKey().equals(name)) {
                switchTo(driver, entry.getValue().toString());
            }
        }
    }
    public synchronized static void closeWindowNamed(WebDriver driver, String name) {
        for (Map.Entry entry : handlesNames.entrySet()) {
            if (entry.getKey().equals(name)) {
                close(driver, entry.getValue().toString());
            }
        }
    }
}
