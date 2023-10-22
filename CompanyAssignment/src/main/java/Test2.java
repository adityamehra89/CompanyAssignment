import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

	public class Test2 {

	    WebDriver driver;
	    WebDriverWait wait;

	    @Before
	    public void setUp() {
	    	String url= "https://www.saucedemo.com";
	        System.setProperty("webdriver.gecko.driver","C:\\Users\\Macbook\\eclipse-workspace\\CompanyAssignment\\src\\main\\resources\\geckodriver.exe" );  
	         
	   
	   DesiredCapabilities capabilities = DesiredCapabilities.firefox();  
	   capabilities.setCapability("marionette",true);  
	   driver= new FirefoxDriver(capabilities);  
	         
	   // Launch Website  
	   driver.navigate().to(url);
	   String currentUrl = driver.getCurrentUrl();
       verifyPageURL("https://www.saucedemo.com/");
       verifyPageTitle("Swag Labs");
       
	        
	    }

	    @Test
	    public void testBuyItems() {
	        // Login to the application
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	WebElement username = driver.findElement(By.xpath("//input[@id='user-name']"));
	        username.sendKeys("standard_user");
	        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
	        password.sendKeys("secret_sauce");
	        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));
	        loginButton.click();

	        // Fill the basket with 3 items
	        verifyPageURL("https://www.saucedemo.com/inventory.html");
	        addItemsToCart(3);
	        cartIconCount("3");
	        // Buy 2 items
	        clickOnCartIcon();
	        verifyPageURL("https://www.saucedemo.com/cart.html");
	        verifyNumberOfProductInCartPage(3);
	        removeItems(1);
	        verifyNumberOfProductInCartPage(2);
	        
	        clickOnCheckoutButton();
	        
	        verifyPageURL("https://www.saucedemo.com/checkout-step-one.html");
	        
	        fillTheDetailsAndClickOnContinueButton();
	        
	        verifyPageURL("https://www.saucedemo.com/checkout-step-two.html");
	        
	        clickOnFinishButton();
	        
	        verifyPageURL("https://www.saucedemo.com/checkout-complete.html");
	        
	        verifyThankuMessageOnOrderConfirmationPage();
	        
	    }
	    
	    private void verifyPageURL(String expectedURL) {
	    	String currentURL = driver.getCurrentUrl();
	        Assert.assertEquals("[Assertion Failed]: Expected URL: '"+expectedURL+"' Actual URL: '"+currentURL+"'",expectedURL,currentURL);
	        
	        System.out.println("URL verification successful.");
	       
	    }
	    
	    private void verifyPageTitle(String expectedPageTitle) {
	    	String currentPageTitle = driver.getTitle();
	        Assert.assertEquals("[Assertion Failed]: Expected page title: '"+expectedPageTitle+"' Actual page title: '"+currentPageTitle+"'",expectedPageTitle,currentPageTitle);
	        
	        System.out.println("Page Title verification successful.");
	       
	    }

	    private void addItemsToCart(int count) {
	        for (int i = 0; i < count; i++) {
	        	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	            WebElement addButton = driver.findElement(By.xpath("(//button[text()='Add to cart'])[" + (i + 1) + "]"));
	         
	            addButton.click();
	        }
	        
	        
	    }

	    private void clickOnCartIcon() {
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        WebElement cart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
	        cart.click();
	        System.out.println("User click on cart icon");
	    }
	        
	   private void removeItems(int count) {
	        for (int i = 0; i < count; i++) {
	        	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	            WebElement removeButton = driver.findElement(By.xpath("(//button[text()='Remove'])[" + (i + 1) + "]"));
	            removeButton.click();
	        }
	     
	    
	     
	    }
	    
	    private void clickOnCheckoutButton() {
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        WebElement checkoutButton = driver.findElement(By.xpath("//button[@id='checkout']"));
	        checkoutButton.click();
	    }
	    
	    
	    
	    private void fillTheDetailsAndClickOnContinueButton() {
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        
	        
	        WebElement username = driver.findElement(By.xpath("//input[@id='first-name']"));
	        username.sendKeys("Aditya");
	        WebElement password = driver.findElement(By.xpath("//input[@id='last-name']"));
	        password.sendKeys("Mehra");
	        WebElement loginButton = driver.findElement(By.xpath("//input[@id='postal-code']"));
	        loginButton.sendKeys("244713");
	        
	        WebElement continueButton = driver.findElement(By.xpath("//input[@id='continue']"));
	        continueButton.click();
	    }
	    
	    private void cartIconCount(String expectedCartCount) {
	    	String actualCartCount = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']")).getText();
	    	Assert.assertEquals("[Assertion Failed]: Expected cart count: '"+expectedCartCount+"' Actual cart count: '"+actualCartCount+"'",actualCartCount,expectedCartCount);
	    	System.out.println("Cart icon count is dislayed as expected");
	    }
	    
	    private void verifyNumberOfProductInCartPage(int expectedNumberOfProductInCart){
	    	List<WebElement> elements = driver.findElements(By.xpath("//div[@class='cart_item']"));
	    	int actualNumberOfProductsInCart = elements.size();
	    	Assert.assertEquals("[Assertion Failed]: Expected product count: '"+expectedNumberOfProductInCart+"' Actual product count: '"+actualNumberOfProductsInCart+"'" ,expectedNumberOfProductInCart, actualNumberOfProductsInCart);
	    	System.out.println("Cart icon count is dislayed as expected");
	    }
	    
	    private void clickOnFinishButton() {
	    	WebElement finishButton = driver.findElement(By.xpath("//button[@id='finish']"));
	        finishButton.click();
	        System.out.println("User click on Finish button");
	    }
	    
	    private void verifyThankuMessageOnOrderConfirmationPage(){
	    	Assert.assertEquals("Assertion Failed: Thanku message is not displayed as expected.", (driver.findElement(By.xpath("//h2[@class='complete-header']")).getText()),"Thank you for your order!");
	    	System.out.println("Order is placed successfully");
	    }
	    
	    
	    
	   

	    @After
	    public void tearDown() {
	        // Close the browser
	        driver.quit();
	    }
	

	
	
}
