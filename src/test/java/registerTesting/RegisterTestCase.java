package registerTesting;

import java.io.IOException;
import java.time.Duration;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RegisterTestCase {
    WebDriver driver;
    WebDriverWait wait;
    private boolean allLinesProcessed = false; // Add the boolean flag
 
    @BeforeClass
    @Parameters({"browser"})
    public void openBrowser(String browser) {
    	
    	
    	if(browser.equals("chrome")) {
    	
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("https://automationexercise.com/signup");
        driver.manage().window().maximize();
    	}
    	
    	if(browser.equals("firefox")) {
        	
            System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
            
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
             driver = new FirefoxDriver(options);
             wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            driver.get("https://automationexercise.com/signup");
            driver.manage().window().maximize();
        	}
    }

    @Test(dataProvider ="test_data")
    public void registerUser(String name,String email,String firstname, String password,String anotherName,String lastname,String adress,String zipcode) {
        if (allLinesProcessed) {
            // Stop running the test if all lines have been processed
            System.out.println("All Excel lines have been processed. Test will not run again.");
            return;
        }
    	
    	
    	// Fill in signup form
    	
        Random random = new Random();

        int randomNumber = random.nextInt();
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys(Keys.chord(Keys.CONTROL, "a"), name);

        //driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys(name);
        
        driver.findElement(By.xpath("//input[@data-qa='signup-email']")).clear();
        driver.findElement(By.xpath("//input[@data-qa='signup-email']"))
                .sendKeys(randomNumber+email);
        driver.findElement(By.xpath("//button[normalize-space()='Signup']")).click();

        // Fill in account creation form
        driver.findElement(By.id("name")).sendKeys(firstname);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("first_name")).sendKeys(anotherName);
        driver.findElement(By.id("last_name")).sendKeys(lastname);
        driver.findElement(By.id("address1")).sendKeys(adress);

        Select dropdown = new Select(driver.findElement(By.id("country")));
        dropdown.selectByIndex(6);

        driver.findElement(By.id("state")).sendKeys("Brooklyn");
        driver.findElement(By.id("city")).sendKeys("Jakarta");
        driver.findElement(By.id("zipcode")).sendKeys("1234");
        driver.findElement(By.id("mobile_number")).sendKeys("12341234");
        driver.findElement(By.id("zipcode")).sendKeys(zipcode);
        driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
        
        //wait.until(ExpectedConditions.urlToBe("https://automationexercise.com/account_created"));

        
       //driver.get("https://automationexercise.com/");
       driver.get("https://automationexercise.com/logout");
       
   
       
  

       
    }
    
    
    
    

    @Test(dependsOnMethods = "registerUser")
    public void verifyAccountCreation() {
        WebElement accountCreated = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@data-qa='account-created']")));

        // Assert that the account created message is displayed
        Assert.assertTrue(accountCreated.isDisplayed(), "Element is not displayed.");

        // Verify the current URL after successful registration
        String expectedUrl = "https://automationexercise.com/account_created";
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "URL after registration does not match expected.");
        System.out.println("Registration successful. Current URL: " + currentUrl);
    }
    
    
    
    
    
    @DataProvider
    public String[][] test_data() throws InvalidFormatException, IOException{
    	
    	
    	readExcelData myData = new readExcelData();
    	return myData.readSheet();
    	
    	
    }
    
    
    

    @AfterClass
    public void closeBrowser() {
        //driver.quit();
    }
}