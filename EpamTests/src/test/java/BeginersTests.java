import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BeginersTests {
    private WebDriver driver;
    @BeforeSuite
    public void Precondition(){
        System.setProperty("webdriver.chrome.driver", "D://ChromeDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://training.by");



    }


    @Test
    public void successLogInningByGoogle(){
        WebDriverWait wait=new WebDriverWait(driver, 20);
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='header-auth__signin']//span")));
        WebElement mailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.reg-footer-social-item.reg-footer-social-item--google")));
        WebElement userMail = driver.findElement(By.cssSelector("div.user-info__name"));
        loginButton.click();
        mailInput.click();
        String expected="Beata Kovtan";
        Assert.assertEquals(userMail.getText(),expected);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement logOutButton = driver.findElement(By.xpath("//div[@class=\"user-info__name\"]//parent::a[@class=\"user-info dropdown-toggle\"]"));
        logOutButton.click();
        WebElement logOut = driver.findElement(By.xpath("//div[@id='user-info-dropdown-menu']//a[contains(text(),\"Sign out\")]"));
        logOut.click();
    }

    @Test
    public void successLogInning(){
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement signInButton = driver.findElement(By.xpath("//p[@class=\"header-auth__signin\"]//span"));
        signInButton.click();
        WebElement mailInput = driver.findElement(By.id("signInEmail"));
        mailInput.sendKeys("beatka99@mail.ru");
        WebElement passwordInput = driver.findElement(By.id("signInPassword"));
        passwordInput.sendKeys("twilight");
        WebElement signIn = driver.findElement(By.className("popup-reg-sign-in-form__sign-in"));
        signIn.click();
        WebElement userMail = driver.findElement(By.cssSelector("div.user-info__name"));
        String expected="beatka99";
        Assert.assertEquals(userMail.getText(),expected,String.format("User mail is at top-right corner."));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //WebElement logOutButton = driver.findElement(By.xpath("//div[@class=\"user-info__name\"]//parent::a[@class=\"user-info dropdown-toggle\"]"));
       // logOutButton.click();
       // WebElement logOut = driver.findElement(By.xpath("//div[@id='user-info-dropdown-menu']//a[contains(text(),\"Sign out\")]"));
       // logOut.click();
    }

    @Test
    public void unsuccessLogInning(){
        WebDriverWait wait=new WebDriverWait(driver, 20);
        WebElement signInButton = driver.findElement(By.xpath("//p[@class='header-auth__signin']//span"));
        signInButton.click();
        WebElement mailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInEmail")));
        mailInput.sendKeys("beata99@mail.ru");
        WebElement passwordInput = driver.findElement(By.id("signInPassword"));
        passwordInput.sendKeys("twilight");
        WebElement signIn = driver.findElement(By.className("popup-reg-sign-in-form__sign-in"));
        signIn.click();
        WebElement alert = driver.findElement(By.cssSelector("div.popup__error-message.ng-binding"));
        String expected=alert.getText();
        String actual = "Login failed. Please try again.";
        Assert.assertEquals(actual,expected,String.format("Alert is shown, user don't logined"));
    }

    @Test
    public void testNewsPage(){
        WebElement loginButton = driver.findElement(By.xpath("//p[@class='heatytder-auth__signin']//span"));
        WebElement mailInput = driver.findElement(By.cssSelector("a.reg-footer-social-item.reg-footer-social-item--google"));
        loginButton.click();
        mailInput.click();
        WebElement news= driver.findElement(By.cssSelector("a.topNavItem.news.click.hover"));
        news.click();
        List<String> linksExpected=new ArrayList<String>();
        linksExpected.add("news");
        linksExpected.add("success stories");
        linksExpected.add("materials");
        linksExpected.add("videos");
        List<WebElement> linksActual= driver.findElements(By.cssSelector("div.tab-nav__item.ng-scope>span.ng-binding"));
        Assert.assertEquals(linksActual.toString(),linksExpected);
        WebElement materials = driver.findElement(By.xpath("//span[contains(text(),\"Materials\")]"));
        materials.click();
        List<WebElement> materialsLinksActual= driver.findElements(By.cssSelector("div.news-page-item__title>a.ng-binding"));
        Assert.assertTrue(materialsLinksActual.toString().contains("materials"));

    }

    @Test
    public void trainingsSearchField() {
        //WebElement localization = driver.findElement(By.cssSelector("div.location-selector__globe"));
        //localization.click();
        //WebElement ukrainian = driver.findElement(By.xpath("//a[contains(text(),\"English\")]"));
        //ukrainian.click();
        WebDriverWait wait=new WebDriverWait(driver, 40);
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='header-auth__signin']//span")));
        loginButton.click();
        WebElement mailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("signInEmail")));
        mailInput.sendKeys("beatka99@mail.ru");
        WebElement passwordInput = driver.findElement(By.id("signInPassword"));
        passwordInput.sendKeys("twilight");
        WebElement signIn = driver.findElement(By.className("popup-reg-sign-in-form__sign-in"));
        signIn.click();

        WebElement trainingList = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.main-nav__list a.topNavItem.training.click.hover")));
        trainingList.click();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        WebElement trainingListSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.input-field-search.ng-pristine.ng-valid.ng-touched")));

        actions.moveToElement(trainingListSearch);
        actions.perform();
        trainingListSearch.click();
        WebElement bySkillsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'By skills')]")));
        bySkillsButton.click();

        WebElement skillsSearchInput = driver
                .findElement(By.xpath("//input[@name='training-filter-input']"));
        skillsSearchInput.sendKeys("Java");

        WebElement javaCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(.,'Java')]//span")));
        javaCheckbox.click();

        WebElement collapseSkillsArrow = driver.findElement(By.xpath("//div[@class='filter-toggle__arrow-icon arrow-icon-rotate']"));
        collapseSkillsArrow.click();

        List<WebElement> skillsSearchResultsList = driver.
                findElements(By.xpath("//div[@class='training-list__container training-list__desktop']//a"));
        skillsSearchResultsList.forEach(element-> Assert.assertTrue(element.getText().contains("JAVA"),
                String.format("Element %s does not contain 'Java' word.",element)));

        WebElement clearSkill = driver.findElement(By.cssSelector("span.filter-field__input-item-close-icon.filter-field__input-item-close-icon--common"));
        clearSkill.click();
        WebElement expandSkillsArrow = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.className("filter-toggle__arrow-icon")));
        expandSkillsArrow.click();
        List<WebElement> dataCheckbox = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//label[contains(.,'Data')]//input[not(contains(@class,\"our-skills\"))]//following-sibling::span")));
        dataCheckbox.forEach(WebElement::click);
        List<WebElement> skillsDataSearchResultsList = driver.
                findElements(By.xpath("//div[@class='training-list__container training-list__desktop']//a"));
        skillsDataSearchResultsList.forEach(element-> Assert.assertTrue(element.getText().contains("DATA"),
                String.format("Element %s does not contain 'Data' word.",element)));

        clearSkill.click();
        WebElement skillsPascalSearchInput = driver
                .findElement(By.xpath("//input[@name='training-filter-input']"));
        skillsSearchInput.sendKeys("Pascal");
        List<WebElement> skillsPascalSearchResultsList = driver.
                findElements(By.xpath("//div[@class='training-list__container training-list__desktop']//a"));
         Assert.assertTrue(skillsPascalSearchResultsList.isEmpty(),
                String.format("Element %s does not contain 'Java' word."));



        WebElement logOutButton = driver.findElement(By.xpath("//div[@class=\"user-info__name\"]//parent::a[@class=\"user-info dropdown-toggle\"]"));
        logOutButton.click();
        WebElement logOut = driver.findElement(By.xpath("//div[@id='user-info-dropdown-menu']//a[contains(text(),\"Sign out\")]"));
        logOut.click();
    }


    @Test
    public void trainingsLocation(){
        WebElement localization = driver.findElement(By.cssSelector("div.location-selector__globe"));
        localization.click();
        WebElement ukrainianLanguage = driver.findElement(By.xpath("//a[contains(text(),\"English\")]"));
        ukrainianLanguage.click();
        WebDriverWait wait=new WebDriverWait(driver, 40);
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='header-auth__signin']//span")));
        loginButton.click();
        WebElement mailInput = driver.findElement(By.id("signInEmail"));
        mailInput.sendKeys("beatka99@mail.ru");
        WebElement passwordInput = driver.findElement(By.id("signInPassword"));
        passwordInput.sendKeys("twilight");
        WebElement signIn = driver.findElement(By.className("popup-reg-sign-in-form__sign-in"));
        signIn.click();
        WebElement trainingList = driver.findElement(By.cssSelector("ul.main-nav__list a.topNavItem.training.click.hover"));
        trainingList.click();
        WebElement trainingListSearch = driver.findElement(By.cssSelector("input.input-field-search.ng-pristine.ng-valid.ng-touched"));
        trainingListSearch.click();
        WebElement ukrainian = driver.findElement(By.cssSelector("div.location__not-active-label.city-name.ng-binding.location__location-active-label.location__location-active-label-country"));
        ukrainian.click();
        WebElement lviv = driver.findElement(By.xpath("//li[@class=\"cities ng-scope\"][last()-1]//label//span//preceding-sibling::input[not(contains(@class, 'our-skills'))]"));
        lviv.click();
        WebElement pointer = driver.findElement(By.cssSelector("div.filter-toggle__arrow-icon.arrow-icon-rotate"));
        pointer.click();
        List<WebElement> locationsOfTheTrainings = driver.findElements(By.xpath("//div[@class='training-list__container training-list__desktop']//div[@class=\"training-item__location ng-binding\"][contains(text(),'Lviv, Ukraine')]"));
        String actual = "Lviv, Ukraine";
        Assert.assertTrue(locationsOfTheTrainings.toString().contentEquals(actual));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement logOutButton = driver.findElement(By.xpath("//div[@class=\"user-info__name\"]//parent::a[@class=\"user-info dropdown-toggle\"]"));
        logOutButton.click();
        WebElement logOut = driver.findElement(By.xpath("//div[@id='user-info-dropdown-menu']//a[contains(text(),\"Sign out\")]"));
        logOut.click();
    }


    @AfterSuite
    public void PostCondition() {

        driver.quit();
    }


}
