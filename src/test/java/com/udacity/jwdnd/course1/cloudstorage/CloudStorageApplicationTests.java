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

	private final String serverAddress = "http://localhost:";

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
		if (driver != null) {
			driver.quit();
		}
	}

	//test that verifies that an unauthorized user can only access the login and signup pages
	@Test
	@Order(1)
	public void unauthorizedAccess() {
		driver.get(serverAddress + port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(serverAddress + port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get(serverAddress + port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());

		driver.get(serverAddress + port + "/files/get/1");
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
		String firstname = "usr";
		String lastname = "usr";
		String username = "usr";
		String password = "123";

		//signup
		driver.get(serverAddress + port + "/signup");
		assertTrue(signupPage.signup(firstname, lastname, username, password, driver));
		//login
		driver.get(serverAddress + port + "/login");
		assertTrue(loginPage.login(username, password, driver));
		//go to "/home"
		driver.get(serverAddress + port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		//logout
		assertTrue(homePage.logout(driver));
		//home page is no longer accessible
		driver.get(serverAddress + port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	//test that creates a note, and verifies it is displayed.
	@Test
	@Order(3)
	public void createNote() {
		//login
		driver.get(serverAddress + port + "/login");
		assertTrue(loginPage.login("usr", "123", driver));
		//go to "/home"
		driver.get(serverAddress + port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		//post a note
		assertTrue(homePage.postNote(driver));
	}

	//test that edits an existing note and verifies that the changes are displayed
	@Test
	@Order(4)
	public void editNote(){
		assertTrue(homePage.editNote(driver));
	}

	//test that deletes a note and verifies that the note is no longer displayed
	@Test
	@Order(5)
	public void deleteNote(){
		assertTrue(homePage.deleteNote(driver));
	}

	//test that creates a set of credentials,
	// verifies that they are displayed,
	// and verifies that the displayed password is encrypted.
	@Test
	@Order(6)
	public void createCredential(){
		assertTrue(homePage.createCredential(driver));
	}

	//test that views an existing set of credentials,
	// verifies that the viewable password is unencrypted,
	// edits the credentials, and verifies that the changes are displayed
	@Test
	@Order(7)
	public void editCredential(){
		assertTrue(homePage.editCredential(driver));
	}

	//test that deletes an existing set of credentials and verifies that the credentials are no longer displayed
	@Test
	@Order(8)
	public void deleteCredential(){
		assertTrue(homePage.deleteCredential(driver));
	}
}