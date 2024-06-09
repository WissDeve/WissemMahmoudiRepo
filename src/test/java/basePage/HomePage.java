package basePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends base_page {

	public HomePage(WebDriver driver) {
		super(driver);
		
	}
	
	
	private By name= By.xpath("//input[@placeholder='Name']");
	private By email = By.xpath("//input[@data-qa='signup-email");
	private By signupButton = By.xpath("//button[normalize-space()='Signup']");
	
   public void enterName(String nom) {
	   
	   base_page_driver.findElement(name).sendKeys(nom);
		
		
	}
	
	
	public void enterEmail(String mail) {
		
		 base_page_driver.findElement(email).sendKeys(mail);
		
		
	}
	
	public void clickSignup() {
		
		base_page_driver.findElement(signupButton).click(); 
	}
	
	
	

}
