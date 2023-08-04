package processtemplates;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static constants.Constants.TIME_OUT_15;
import static org.junit.Assert.assertNotNull;

public class ProcessesTemplates {

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
    private final By fioOfTheLastUserField = By.xpath("//div/form//div/input[@name = 'updatedBy']");
    private final By calendarFieldFrom = By.xpath("//div/form/div[5]/div[1]//div/button");
    private final By calendarFieldTo = By.xpath("//div/form/div[5]/div[2]//div/button");
    private final By basedOnTheDocumentField = By.xpath("//div/form//div/input[@name = 'baseDocument']");
    public static final By addABasedDocumentToUpload = By.xpath("//div/form//div/span/pre");
    private final String BASED_ON_THE_DOCUMENT = "Документ-основание";
    private final By saveButton = By.xpath("//div/button[contains(text(), 'Сохранить')]");
    private final By modalWindowOfDeletingATemplate = By.xpath("//div/span/strong[contains(text(), 'Свойства шаблона')]");
    private final By editTheTemplateButton = By.xpath("//div/button[contains(text(), 'Редактировать')]");
    private final By deleteButton = By.xpath("//div/button[contains(text(), 'Удалить')]");
    private final By deletingConfirmMessage = By.xpath("//div/span[contains(text(), 'Вы действительно хотите удалить шаблон')]");
    private final By confirmButtonToDeleteTheTestTemplate = By.xpath("//div/button/span[contains(text(), 'Да')]");
    public static final By errorMessage = By.xpath("//div[contains(text(), 'Отсутствует схема в шаблоне, не возможно перевести в статус \"Действующий\"')]");

    // Методы.
    private final WebDriver driver;

    public ProcessesTemplates(WebDriver driver) {
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

    @Step("Вношу в поле 'Документ-основание' название документа.")
    public void filTheFieldOfBaseDocument() {
        driver.findElement(basedOnTheDocumentField).isEnabled();
        driver.findElement(basedOnTheDocumentField).clear();
        driver.findElement(basedOnTheDocumentField).click();
        driver.findElement(basedOnTheDocumentField).sendKeys(BASED_ON_THE_DOCUMENT);
    }

    @Step("Нажимаю кнопку 'Сохранить'.")
    public void saveButtonClick() {
        driver.findElement(saveButton).isEnabled();
        driver.findElement(saveButton).click();
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

    @Step("Проверяю, что поле 'Наименование шаблона' заполнено и содержит название шаблона.")
    public void fieldOfTemplateNameIsFilled() {
        String stringElement = driver.findElement(templateNameField).getAttribute("value");
        Assert.assertEquals(stringElement, TEMPLATE_NAME);
    }

    @Step("Проверка, что поле 'Статус шаблона' заполнено и содержит название шаблона.")
    public void fieldOfTemplateStatusIsFiled() {
        String stringElement = driver.findElement(templateStatusField).getText();
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'Версия' заполнено и содержит значение.")
    public void fieldOfTemplateVersionIsFilled() {
        String stringElement = driver.findElement(templateVersionField).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение.")
    public void fieldOfFioOfTheLastUserIsFilled() {
        String stringElement = driver.findElement(fioOfTheLastUserField).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'Дата последнего редактирования' заполнено и содержит значение.")
    public void fieldFromOfTheCalendarIsFilled() {
        String stringElement = driver.findElement(calendarFieldFrom).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'Дата создания' заполнено и содержит значение.")
    public void fieldToOfTheCalendarIsFilled() {
        String stringElement = driver.findElement(calendarFieldTo).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'Документ-основание' заполнено и содержит значение.")
    public void fieldOfTheBasedDocumentIsFilled() {
        String stringElement = driver.findElement(basedOnTheDocumentField).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Изменяю статус шаблона с 'Черновик' на 'Действующий'.")
    public void changeTheTemplateStatus() {
        driver.findElement(templateStatusField).isEnabled();
        driver.findElement(templateStatusField).click();
        // Ожидание видимости на экране кнопки подтверждающего сообщения об удалении.
        waitForTheStatusOfTheTemplate();
        driver.findElement(templateStatusActive).click();
        // Убираю фокус с поля.
        driver.findElement(templateNameField).click();
    }

    @Step("Проверка, что поле статуса поменялось на 'Действующий'.")
    public void fieldStatusOfTheTemplateHasBeenChangedAndNowIsFilledWithTheProperStatus() {
        String stringElement = driver.findElement(expectedStatusOfTheTemplateIsActive).getText();
        Assert.assertEquals(stringElement, "Действующий");
    }

    @Step("Удаляю тестовые данные.")
    public void deleteTestData() {
        driver.findElement(deleteButton).isEnabled();
        driver.findElement(deleteButton).click();
        // Ожидание видимости на экране кнопки подтверждающего сообщения об удалении.
        waitForTheDeletingMessage();
        driver.findElement(confirmButtonToDeleteTheTestTemplate).click();
    }

    @Step("Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.")
    public void fillTheFormOfAddingANewTemplate() {
        // Вношу в поле 'Наименование шаблона' название шаблона.
        fillTheFieldOfTemplateName();
        // Вношу в поле 'Документ-основание' название документа.
        filTheFieldOfBaseDocument();
        // Нажимаю кнопку 'Сохранить'.
        saveButtonClick();
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
    @Step("Ожидание видимости на экране кнопки подтверждающего сообщения об удалении.")
    public void waitForTheStatusOfTheTemplate() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.elementToBeClickable(templateStatusActive));
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