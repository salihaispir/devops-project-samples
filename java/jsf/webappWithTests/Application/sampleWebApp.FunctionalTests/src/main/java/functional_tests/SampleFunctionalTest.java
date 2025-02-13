package functional_tests;

import org.junit.*;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class SampleFunctionalTest {
    
	private static WebDriver driver;

    @Before
    public void startBrowser() {
        String path = Paths.get(System.getenv("ChromeWebDriver"), "chromedriver.exe").toString();
	    System.setProperty("webdriver.chrome.driver", path);
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--no-sandbox");
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
    }
	
    @Test(timeout = 600000)
    public void testAssertTitleWithSelenium() throws InterruptedException, AssertionError {
        if (isAlertPresent()) {
            System.out.println(isAlertPresent());
            driver.switchTo().alert().accept();
        }
        
        long startTimestamp = (new Date()).getTime();
        long endTimestamp = startTimestamp + 60*10*1000;
        
        while(true)
        {
            try
            {
                driver.get(System.getProperty("webAppUrl"));
                assertEquals("Sample JSF Application", driver.getTitle());
                break;
            }
            catch(AssertionError e)
            {
                startTimestamp = (new Date()).getTime();
                if(startTimestamp > endTimestamp)
                {
                    throw e;
                }
                Thread.sleep(5000);
            }
        }
    }
    
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    
    @After
    public void tearDown() {
    	try {
            driver.quit();
    	} catch(Exception e) {
    		
    	}
    }
}