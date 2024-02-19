package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Teste la page de login du site demo.com
 */
public class AlertDemoTest {

    protected WebDriver driver;
    public String url = "https://demoqa.com/alerts";


    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        options.addArguments("--disable-gpu"); // Cette option est nécessaire sur certaines versions de Windows
        options.addArguments("--window-size=1920,1200"); // Définit la taille de la fenêtre, importante pour certaines applications

        // Ouvrir une URL commune
        driver.get(url);
    }

//    @Test
//    public void testAlertMessageDisplayed() {
//        By alertButton = By.id("alertButton");
//        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
//        wait.until(ExpectedConditions.elementToBeClickable(alertButton));
//        // This step will result in an alert on screen : click on the button with message: Click Button to see alert
//        driver.findElement(alertButton).click();
//
//        // Now wait for the alert to be present
//        wait.until(ExpectedConditions.alertIsPresent());//va tres vite
//
//        // Switch to the alert and accept it
//        Alert simpleAlert = driver.switchTo().alert();
//
//        // Assert that the alert text is as expected
//        String expectedAlertText = "You clicked a button";
//        Assert.assertEquals(simpleAlert.getText(), expectedAlertText, "Alert text does not match");
//
//        simpleAlert.accept();

//    }

//    @Test
//    public void testAlertMessageDisplayedWithPause() {
//        // Initialize the Actions class
//        Actions actions = new Actions(driver);
//
//        // Move to the alert button, pause for a specified duration, and then click
//        actions.moveToElement(driver.findElement(By.id("timerAlertButton")))
//                .pause(5000) // Pause for 5000 milliseconds (5 seconds)
//                .click()
//                .perform();
//
//        // Wait for the alert to be present
//        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20)); // 10 seconds timeout
//        wait.until(ExpectedConditions.alertIsPresent());
//
//        // Switch to the alert
//        Alert simpleAlert = driver.switchTo().alert();
//
//        // Assert that the alert text is as expected
//        String expectedAlertText = "You clicked a button";
//        Assert.assertEquals(simpleAlert.getText(), expectedAlertText, "Alert text does not match");
//
//
//        simpleAlert.accept();
//    }

    @Test
    public void testAlertPromptIsCorrect() {
        //test le bouton qui est vis a vis : On button click, prompt box will appear
        // This step will result in an alert on screen
        WebElement element = driver.findElement(By.id("promtButton"));
        //ici on a passé 1 seul argument qui est element => d'ou le 0
        //est une interface dans Selenium qui permet l'exécution de code JavaScript
        //On l'utilise parfois quand les méthodes standard de Selenium ne fonctionne pas.

      //  element.click();//si cela ne fonctionne pas=> vous pouvez utiliser JavascriptExecutor

      ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
        Alert promptAlert  = driver.switchTo().alert();
        String alertText = promptAlert.getText(); //get text to alert !!!!
        System.out.println("Alert text is : " + alertText);
        //Send some text to the alert
        promptAlert.sendKeys("Test User");//send keys to alert !!!!
        promptAlert.accept();//click on OK to alert !!!!

        //Vérifier que le texte s'est bien affiché en vert :
        By promptResult = By.id("promptResult");
        String messageActual = driver.findElement(promptResult).getText();

        Assert.assertEquals(messageActual, "You entered " + "Test User",
                "Le message affiché ne correspond pas au texte attendu.");

    }

    //test alert : On button click, confirm box will appear
    //Alert.accept(); or Alert.dismiss();

    @Test
    public void testAlertClickInOK() {
        // This step will result in an alert on screen
        WebElement element = driver.findElement(By.id("confirmButton"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
        Alert confirmationAlert = driver.switchTo().alert();
      //  String alertText = confirmationAlert.getText();//Pour aller chercher le texte dans l'Alert
       // System.out.println("Alert text is " + alertText);
        confirmationAlert.accept();//click sur OK

        By textOk = By.id("confirmResult");
        String textOkActual = driver.findElement(textOk).getText();

        Assert.assertEquals(textOkActual, "You selected Ok", "Sorry you don't click on OK.");
    }

    @Test
    public void testAlertClickInCancel() {//Les étudiants doivent faire la meme chose avec cancel
        // This step will result in an alert on screen
        WebElement element = driver.findElement(By.id("confirmButton"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
        Alert confirmationAlert = driver.switchTo().alert();
        //  String alertText = confirmationAlert.getText();//Pour aller chercher le texte dans l'Alert
        // System.out.println("Alert text is " + alertText);
        confirmationAlert.dismiss();//click sur cancel

        By textOk = By.id("confirmResult");
        String textCancelActual = driver.findElement(textOk).getText();

        Assert.assertEquals(textCancelActual, "You selected Cancel", "Sorry you click on " +
                textCancelActual);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
