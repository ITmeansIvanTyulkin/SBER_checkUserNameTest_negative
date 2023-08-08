package processtemplates;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static constants.Constants.*;
import static dateistoday.DateIsToday.formatDate;
import static org.junit.Assert.assertNotNull;

public class CreatingANewProcessTemplate {

    // Локаторы страницы.
    private final By addATemplateButton = By.xpath("//div/button/span[contains(text(), 'Добавить шаблон')]");

    // Локаторы модального окна создания нового шаблона.
    private final By modalWindowTitle = By.xpath("//div/span/strong[contains(text(), 'Новый шаблон')]");
    private final By templateNameField = By.xpath("//div/form//div/input[@name = 'name']");
    private final String TEMPLATE_NAME = "Тестовый шаблон №1";
    private final By templateStatusField = By.xpath("//div/form//div/span[contains(text(), 'Черновик')]");
    private final By expectedStatusOfTheTemplateIsActive = By.xpath("//div/form//div/span[contains(text(), 'Действующий')]");
    private final By templateStatusActive = By.xpath("//div[@class = 'ant-select-item ant-select-item-option']");
    private final By templateVersionField = By.xpath("//div/form//div/input[@name = 'version']");
    private final By fioOfTheLastUserField = By.xpath("//div/form//div/input[@value = 'Тюлькин Иван undefined']");
    private final By dateOfCreatingATemplate = By.xpath("//div/form//div/input[@placeholder = 'дд.мм.гггг']");
    private final By basedOnTheDocumentField = By.xpath("//div/form//div/input[@name = 'baseDocument']");
    public static final By addABasedDocumentToUpload = By.xpath("//div/form//div/span/pre");
    private final String BASED_ON_THE_DOCUMENT = "Документ 123";
    private final By saveButton = By.xpath("//div/button[contains(text(), 'Сохранить')]");
    private final By modalWindowOfDeletingATemplate = By.xpath("//div/span/strong[contains(text(), 'Свойства шаблона')]");
    private final By editTheTemplateButton = By.xpath("//div/button[contains(text(), 'Редактировать')]");
    private final By deleteButton = By.xpath("//div/button[contains(text(), 'Удалить')]");
    private final By deletingConfirmMessage = By.xpath("//div/span[contains(text(), 'Вы действительно хотите удалить шаблон')]");
    private final By confirmButtonToDeleteTheTestTemplate = By.xpath("//div/button/span[contains(text(), 'Да')]");
    public static final By errorMessage = By.xpath("//div[contains(text(), 'Отсутствует схема в шаблоне, не возможно перевести в статус \"Действующий\"')]");

    // Методы.
    private final WebDriver driver;

    public CreatingANewProcessTemplate(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажимаю на кнопку 'Добавить шаблон'.")
    public void newTemplateAddButtonClick() {
        driver.findElement(addATemplateButton).isDisplayed();
        driver.findElement(addATemplateButton).isEnabled();
        driver.findElement(addATemplateButton).click();
        waitForTheTitleOfTheModalWindow();
    }

    @Step("Вношу в поле 'Наименование шаблона' название шаблона.")
    public void fillTheFieldOfTemplateName() {
        driver.findElement(templateNameField).isEnabled();
        driver.findElement(templateNameField).clear();
        driver.findElement(templateNameField).click();
        driver.findElement(templateNameField).sendKeys(TEMPLATE_NAME);
    }

    @Step("Проверяю, что поле 'Наименование шаблона' заполнено и содержит название шаблона.")
    public void fieldOfTemplateNameIsFilled() {
        String stringElement = driver.findElement(templateNameField).getAttribute("value");
        Assert.assertEquals(stringElement, TEMPLATE_NAME);
    }

    @Step("Проверка, что поле 'Версия' заполнено и содержит значение '1'.")
    public void fieldOfTemplateVersionIsFilled() {
        String stringElement = driver.findElement(templateVersionField).getAttribute("value");
        Assert.assertEquals(stringElement, "1");
    }

    @Step("Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение.")
    public void fieldOfFioOfTheLastUserIsFilledWithFioNotNull() {
        String stringElement = driver.findElement(fioOfTheLastUserField).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение.")
    public void fieldOfFioOfTheLastUserIsFilledWithTyulkinIvan() {
        String stringElement = driver.findElement(fioOfTheLastUserField).getAttribute("value");
        Assert.assertEquals(stringElement, "Тюлькин Иван undefined");
    }

    @Step("Проверка, что поле 'Дата создания шаблона' заполнено и содержит значение.")
    public void fieldToOfTheDateIsFilled() {
        String stringElement = driver.findElement(dateOfCreatingATemplate).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'Дата создания шаблона' заполнено текущей датой и временем создания шаблона.")
    public void fieldToOfTheDateIsFilledProperly() {
        String stringElement = driver.findElement(dateOfCreatingATemplate).getAttribute("value");
        Assert.assertEquals(stringElement, formatDate());
    }

    @Step("Вношу в поле 'Документ-основание' название документа.")
    public void filTheFieldOfBaseDocument() {
        driver.findElement(basedOnTheDocumentField).isEnabled();
        driver.findElement(basedOnTheDocumentField).clear();
        driver.findElement(basedOnTheDocumentField).click();
        driver.findElement(basedOnTheDocumentField).sendKeys(BASED_ON_THE_DOCUMENT);
    }

    @Step("Проверка, что поле 'Документ-основание' заполнено и содержит значение.")
    public void fieldOfTheBasedDocumentIsFilled() {
        String stringElement = driver.findElement(basedOnTheDocumentField).getAttribute("value");
        Assert.assertEquals(stringElement, BASED_ON_THE_DOCUMENT);
    }

    @Step("Загружаю документ-основание. Это файл Excel, весом не более 20МБ. Затем проверяю, что файл загружен успешно.")
    public void uploadTheBasedDocument() {
        String filePath = "C:\\Users\\21088700\\IdeaProjects\\stori-autotest\\templates\\src\\test\\resources\\Test_data.xlsx";
        driver.findElement(By.xpath("//div/form//div/input[@type = 'file']")).sendKeys(filePath);
        // Проверяю, что файл загружен успешно.
        WebElement webElement = driver.findElement(By.cssSelector("div.FilesList_file_list__6VxuE"));
        assertNotNull(webElement);
    }

    @Step("Нажимаю кнопку 'Сохранить'.")
    public void saveButtonClick() {
        driver.findElement(saveButton).isEnabled();
        driver.findElement(saveButton).click();
    }

    @Step("Проверка, что шаблон сохранен и добавлен в список и его название соответствует тестовому.")
    public void findWebElementToCheck() {
        String stringElement = driver.findElement(By.xpath("//div/h3[contains(text(), 'Тестовый шаблон №1')]/../..//div/button[2]/../../div/h3")).getText();
        Assert.assertEquals(stringElement, TEMPLATE_NAME);
    }

    @Step("Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.")
    public void findWebElementToDelete() {
        WebElement webElement = driver.findElement(By.xpath("//div/h3[contains(text(), 'Тестовый шаблон №1')]/../..//div/button[2]"));
        webElement.click();
        // Ожидание видимости на экране окна редактирования/удаления шаблона с названием 'Тестовый шаблон №1'.
        waitForTheTitleOfTheModalWindowOfDeletingTheTemplate();
    }

    @Step("Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.")
    public void editButtonClick() {
        driver.findElement(editTheTemplateButton).isEnabled();
        driver.findElement(editTheTemplateButton).click();
        // Ожидание видимости на экране кнопки 'Удалить' во всплывающем модальном окне.
        waitForTheDeleteButton();
    }

    @Step("Удаляю тестовые данные.")
    public void deleteTestData() {
        driver.findElement(deleteButton).isEnabled();
        driver.findElement(deleteButton).click();
        // Ожидание видимости на экране кнопки подтверждающего сообщения об удалении.
        waitForTheDeletingMessage();
        driver.findElement(confirmButtonToDeleteTheTestTemplate).click();
    }

    @Step("Единый шаг удаления тестовых данных.")
    public void deleteTestDataFromTheListOfTemplates() {
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editButtonClick();
        // Удаляю тестовые данные.
        deleteTestData();
    }

    // Ожидания.
    @Step("Ожидание закрытия модульного окна после нажатия кнопки 'Сохранить'.")
    public void waitForTheModalWindowToBeClosed() {
        new WebDriverWait(driver, TIME_OUT_10)
                .until(ExpectedConditions.invisibilityOfElementLocated(modalWindowTitle));
    }

    @Step("Ожидание видимости на экране кнопки подтверждающего сообщения об удалении.")
    public void waitForTheDeletingMessage() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(deletingConfirmMessage));
    }

    @Step("Ожидание видимости на экране кнопки 'Удалить' во всплывающем модальном окне.")
    public void waitForTheDeleteButton() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(deleteButton));
    }

    @Step("Ожидание видимости на экране заголовка всплывающего модального окна 'Свойства шаблона'.")
    public void waitForTheTitleOfTheModalWindowOfDeletingTheTemplate() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(modalWindowOfDeletingATemplate));
    }

    @Step("Ожидание видимости на экране заголовка всплывающего модального окна 'Новый шаблон'.")
    public void waitForTheTitleOfTheModalWindow() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(modalWindowTitle));
    }
}