import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;  
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

public class Test1 {

    WebDriver driver;

    @Before
    public void setUp() {
    	String url= "https://www.saucedemo.com";
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Macbook\\eclipse-workspace\\CompanyAssignment\\src\\main\\resources\\geckodriver.exe" );  
         
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();  
        capabilities.setCapability("marionette",true);  
        driver= new FirefoxDriver(capabilities);  
         
        // Launch Website  
        driver.navigate().to(url);  
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("[Assertion Failed]: Expected page title: 'https://www.saucedemo.com/' Actual page title: '"+currentUrl+"'","https://www.saucedemo.com/", currentUrl);
        System.out.println("Page URL verification successful.");
        String currentPageTitle = driver.getTitle();
        Assert.assertEquals("[Assertion Failed]: Expected page title: 'Swag Labs' Actual page title: '"+currentPageTitle+"'","Swag Labs",currentPageTitle);
   
        System.out.println("Page Title verification successful.");
        
    }

    @Test
    public void testLoginAndVerifyProductsPage() {
        // Find and fill in the username and password fields
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        System.out.println("User entered the user name");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        System.out.println("User entered the password");
        // Click the login button
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
        System.out.println("User click on login button");
        // Verify if the user is taken to the products page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("https://www.saucedemo.com/inventory.html", currentUrl);
        System.out.println("Page URL is dislayed as expected");
        Assert.assertTrue("Assertion Failed: Product page text is not displayed as expected.", (driver.findElement(By.xpath("//span[@class='title']")).getText()).contains("Products"));
        System.out.println("Product text is dislayed as expected");
    }

    @After
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}
