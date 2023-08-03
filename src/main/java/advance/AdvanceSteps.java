import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class AdvanceSteps {

// Предварительные методы.
// Данный класс описывает открытие страницы проекта с формой входа, а также ожидание заголовка формы входа, вход в систему под админом.

    private final WebDriver driver;

    // Локаторы проверки доступности формы входа (заголовок формы входа 'СУДИР' - виден).
    private final By titleOfTheFormSudir = By.xpath("//div[@class = 'main-logo']/img");

    // Локаторы для полей формы авторизации в системе под администратором.
    private final By enteringName = By.xpath("//div//form//div/input[@id = 'username']");
    private final By password = By.xpath("//div//form//div/input[@id = 'password']");
    private final By enterButton = By.xpath("//div//form//div/input[@name = 'login']");

    // Локаторы для списка 'Вход в платформу'.
    private final By titleOfThePlatform = By.xpath("//div/h2/em");
    private final By vhodDlyaPolsovateleiCPRIssl = By.xpath("//div//a[contains(text(), 'Вход для пользователей CPRI')]");

    // Локаторы меню администратора для перехода к разделу 'Администрирование'/
    private final By adminAvatar = By.xpath("//div/header/div/menu//div/span/span[@class = 'ant-avatar-string']");
    private final By droppingMenu = By.xpath("//div/header/div/menu//div/ul/li/a[contains(text(), 'Администрирование')]");

    // Методы.
    public AdvanceSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открытие главной страницы проекта.")
    public void openHomePage() {
        driver.get(BASE_URI_VIA_SUDIR);
        driver.manage().timeouts().implicitlyWait(TIME_OUT_10, TimeUnit.SECONDS);
    }

    @Step("Ввод данных пользователя типа АДМИНИСТРАТОР в поля формы и нажатие кнопки ВОЙТИ")
    public void shouldEnterTheSystem() {
        openHomePage();
        waitForTheEnteringForm();
        driver.findElement(enteringName).sendKeys(ADMIN_NAME);
        driver.findElement(password).sendKeys(ADMIN_PASSWORD);
        driver.findElement(enterButton).click();
        waitForTheTitle();
    }

    @Step("Выбор из списка строк входа - строки 'Вход для пользователей CPRI'.")
    public void shouldEnterTheMainScreen() {
        driver.findElement(vhodDlyaPolsovateleiCPRIssl).isDisplayed();
        driver.findElement(vhodDlyaPolsovateleiCPRIssl).click();
        waitForTheTitleOfTheMainScreen();
    }

    @Step("Нажимаю на аватар администратора.")
    public void adminAvatarClick() {
        driver.findElement(adminAvatar).isDisplayed();
        driver.findElement(adminAvatar).click();
        waitForTheDropDownMenu();
    }

    @Step("Нажимаю на пункт 'Администрирование' в выпадающем меню.")
    public void menuItemClick() {
        driver.findElement(droppingMenu).isDisplayed();
        driver.findElement(droppingMenu).click();
        waitForThePageItems();
    }

    @Step("Единый шаг входа на ресурс зарегистрированного пользователя.")
    public void shouldEnterTheSystemByAdmin() {
        // 1. Открытие главной страницы проекта, ввод данных пользователя типа АДМИНИСТРАТОР в поля формы и нажатие кнопки ВОЙТИ
        shouldEnterTheSystem();
        // 2. Выбор из списка строк входа - строки 'Вход для пользователей CPRI'.
        shouldEnterTheMainScreen();
        // 3. Нажимаю на аватар администратора.
        adminAvatarClick();
        // 4. Нажимаю на пункт 'Администрирование' в выпадающем меню.
        menuItemClick();
    }

    // Ожидания.
    @Step("Ожидание видимости на экране заголовка формы входа - 'СУДИР'.")
    public void waitForTheEnteringForm() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(titleOfTheFormSudir));
    }

    @Step("Проверка видимости на экране заголовка 'Вход в платформу'.")
    public void waitForTheTitle() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(titleOfThePlatform));
    }

    @Step("Ожидание видимости на экране раздела 'Главное на сегодня'.")
    public void waitForTheTitleOfTheMainScreen() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div/h1[contains(text(), 'Главное на сегодня')]")));
    }

    @Step("Ожидание видимости на экране раздела 'Администрирование' в выпадающем меню.")
    public void waitForTheDropDownMenu() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div/header/div/menu//div/ul/li/a[contains(text(), 'Администрирование')]")));
    }

    @Step("Ожидание видимости на экране раздела страницы.")
    public void waitForThePageItems() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div/section/a[contains(text(), 'Шаблоны процессов')]")));
    }
}