package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {
	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
	}

	@BeforeEach
	public void beforeEach() {
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@AfterAll
	public static void afterAll() {
//		if (driver != null) {
//			driver.quit();
//		}
	}


	//test that verifies that an unauthorized user can only access the login and signup pages
	@Test
	@Order(1)
	public void unauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/files/get/1");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	//test that signs up a new user,
	// logs in,
	// verifies that the home page is accessible,
	// logs out,
	// verifies that the home page is no longer accessible
	@Test
	@Order(2)
	public void signupLoginLogout(){
		//signup
		driver.get("http://localhost:" + port + "/signup");
		assertTrue(signupPage.signup("usr", "usr", "usr", "123", driver));
		//login
		driver.get("http://localhost:" + port + "/login");
		assertTrue(loginPage.login("usr", "123", driver));
		//go to "/home"
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		//logout
		assertTrue(homePage.logout(driver));
		//home page is no longer accessible
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	//test that creates a note, and verifies it is displayed.
	@Test
	@Order(3)
	public void createNote() {
		//login
		driver.get("http://localhost:" + port + "/login");
		assertTrue(loginPage.login("usr", "123", driver));
		//go to "/home"
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		//post a note
		homePage.postNote(driver);
	}
}

