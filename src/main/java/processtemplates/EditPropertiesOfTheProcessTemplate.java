package processtemplates;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

import static constants.Constants.*;
import static dateistoday.DateIsToday.formatDate;
import static org.junit.Assert.assertNotNull;

public class EditPropertiesOfTheProcessTemplate {

    // Локаторы страницы.
    private final By addATemplateButton = By.xpath("//div/button/span[contains(text(), 'Добавить шаблон')]");

    // Локаторы модального окна создания нового шаблона.
    private final By modalWindowTitle = By.xpath("//div/span/strong[contains(text(), 'Новый шаблон')]");
    private final By templateNameField = By.xpath("//div/form//div/input[@name = 'name']");
    private final String TEMPLATE_NAME = "Тестовый шаблон №1";
    private final String TEMPLATE_NAME_2 = "Тестовый шаблон №2";
    private final By templateStatusField = By.xpath("//div/form//div/span[contains(text(), 'Черновик')]");
    private final By expectedStatusOfTheTemplateIsActive = By.xpath("//div/form//div/span[contains(text(), 'Действующий')]");
    private final By templateStatusActive = By.xpath("//div[@class = 'ant-select-item ant-select-item-option']");
    private final By templateVersionField = By.xpath("//div/form//div/input[@name = 'version']");
    private final By fioOfTheLastUserField = By.xpath("//div/form//div/input[@name = 'updatedBy']");
    private final By calendarFieldDateOfTheLastEditing = By.cssSelector("div.TemplatePropertiesModal_dataPickerWrapper__JGzzU > div:nth-child(1) > div.ant-col.ant-form-item-control > div > div > div > div > div > div > div > input");
    private final By calendarFieldDateOfCreating = By.cssSelector("div.TemplatesPanel_content__1YpsI > div.Modal_wrapper__meIPm > div > div.Modal_content__ouhhq.undefined > form > div.TemplatePropertiesModal_dataPickerWrapper__JGzzU > div:nth-child(2) > div.ant-col.ant-form-item-control > div > div > div > div > div > div > div > input");
    private final By basedOnTheDocumentField = By.xpath("//div/form//div/input[@name = 'baseDocument']");
    public static final By addABasedDocumentToUpload = By.xpath("//div/form//div/span/pre");
    private final String BASED_ON_THE_DOCUMENT = "Документ-основание";
    private final String BASED_ON_THE_DOCUMENT_2 = "Документ-основание №2";
    private final By saveButton = By.xpath("//div/button[contains(text(), 'Сохранить')]");
    private final By modalWindowOfDeletingATemplate = By.xpath("//div/span/strong[contains(text(), 'Свойства шаблона')]");
    private final By editTheTemplateButton = By.xpath("//div/button[contains(text(), 'Редактировать')]");
    private final By deleteButton = By.xpath("//div/button[contains(text(), 'Удалить')]");
    private final By deletingConfirmMessage = By.xpath("//div/span[contains(text(), 'Вы действительно хотите удалить шаблон')]");
    private final By confirmButtonToDeleteTheTestTemplate = By.xpath("//div/button/span[contains(text(), 'Да')]");
    public static final By errorMessage = By.xpath("//div[contains(text(), 'Отсутствует схема в шаблоне, не возможно перевести в статус \"Действующий\"')]");
    private final By dateOfCreatingATemplate = By.xpath("//div/form//div/input[@placeholder = 'дд.мм.гггг']");
    private final By deleteTheBaseDocumentThatWasUploaded = By.cssSelector("#root > div > div > div:nth-child(2) > div.TemplatesPanel_content__1YpsI > div.Modal_wrapper__meIPm > div > div.Modal_content__ouhhq.undefined > form > div:nth-child(7) > div > div > div > div > div > div > div > div > div:nth-child(2) > svg");
    private final By modalWindowIsDisabled = By.xpath("/html/body/div[1]/div/div/div[2]/div[2]/div[2]/div");

    // Методы.
    private final WebDriver driver;

    public EditPropertiesOfTheProcessTemplate(WebDriver driver) {
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

    @Step("Вношу в поле 'Наименование шаблона' новое название шаблона.")
    public void fillTheFieldOfTemplateNameByANewName() {
        driver.findElement(templateNameField).isEnabled();
        driver.findElement(templateNameField).click();
        driver.findElement(templateNameField).sendKeys(Keys.CONTROL, "a");
        driver.findElement(templateNameField).sendKeys((Keys.DELETE));
        driver.findElement(templateNameField).sendKeys(TEMPLATE_NAME_2);
    }

    @Step("Вношу в поле 'Документ-основание' название документа.")
    public void filTheFieldOfBaseDocument() {
        driver.findElement(basedOnTheDocumentField).isEnabled();
        driver.findElement(basedOnTheDocumentField).clear();
        driver.findElement(basedOnTheDocumentField).click();
        driver.findElement(basedOnTheDocumentField).sendKeys(BASED_ON_THE_DOCUMENT);
    }

    @Step("Вношу в поле 'Документ-основание' новое название документа.")
    public void filTheFieldOfBaseDocumentWithTheNewData() {
        driver.findElement(basedOnTheDocumentField).isEnabled();
        driver.findElement(basedOnTheDocumentField).click();
        driver.findElement(basedOnTheDocumentField).sendKeys(Keys.CONTROL, "a");
        driver.findElement(basedOnTheDocumentField).sendKeys((Keys.DELETE));
        driver.findElement(basedOnTheDocumentField).sendKeys(BASED_ON_THE_DOCUMENT_2);
    }

    @Step("Загружаю документ-основание. Это файл Excel, весом не более 20МБ. Затем проверяю, что файл загружен успешно.")
    public void uploadTheBasedDocument() {
        String filePath = "C:\\Users\\21088700\\IdeaProjects\\stori-autotest\\templates\\src\\test\\resources\\Test_data.xlsx";
        driver.findElement(By.xpath("//div/form//div/input[@type = 'file']")).sendKeys(filePath);
        // Проверяю, что файл загружен успешно.
        WebElement webElement = driver.findElement(By.cssSelector("div.FilesList_file_list__6VxuE"));
        assertNotNull(webElement);
        // Проверяю, что загруженный файл загружен и соответствует тестовому файлу по названию.
        isFileExist("Test_data");
    }

    @Step("Загружаю документ-основание 2-ю версию. Это файл Excel, весом не более 20МБ. Затем проверяю, что файл загружен успешно.")
    public void uploadTheBasedDocumentVersion_2() {
        String filePath = "C:\\Users\\21088700\\IdeaProjects\\stori-autotest\\templates\\src\\test\\resources\\Test_data_2.xlsx";
        driver.findElement(By.xpath("//div/form//div/input[@type = 'file']")).sendKeys(filePath);
        // Проверяю, что файл загружен успешно.
        WebElement webElement = driver.findElement(By.cssSelector("div.FilesList_file_list__6VxuE"));
        isFileExist("Test_data_2");
    }

    @Step("Проверяю, что загруженный файл загружен и соответствует тестовому файлу по названию.")
    public boolean isFileExist(String Test_data) {
        try {
            driver.findElement(By.cssSelector("div.FilesList_file_list__6VxuE"));
        } catch (NoSuchElementException element) {
            return false;
        }
        return true;
    }

    @Step("Вспомогательный метод проверки закрытия модального окна.")
    public boolean isElementDisplayed(By element) {
        try {
            return driver.findElement(modalWindowIsDisabled).isEnabled() || driver.findElement(modalWindowIsDisabled).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Проверяю, закрылось ли модальное окно.")
    public void isTheModalWindowClosed() {
        if (!isElementDisplayed(modalWindowIsDisabled)) {
            driver.navigate().refresh();
        }
    }

    @Step("Нажимаю кнопку 'Сохранить'.")
    public void saveButtonClick() {
        driver.findElement(saveButton).isEnabled();
        driver.findElement(saveButton).click();
    }

    @Step("Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.")
    public void fillTheFormOfAddingANewTemplate() {
        // Вношу в поле 'Наименование шаблона' название шаблона.
        fillTheFieldOfTemplateName();
        // Вношу в поле 'Документ-основание' название документа.
        filTheFieldOfBaseDocument();
        // Загружаю документ-основание. Это файл Excel, весом не более 20МБ. Затем проверяю, что файл загружен успешно.
        uploadTheBasedDocument();
        // Нажимаю кнопку 'Сохранить'.
        saveButtonClick();
        // Проверка, что шаблон сохранен и добавлен в список и его название соответствует тестовому.
        findWebElementToCheck();
    }

    @Step("Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.")
    public void findWebElementToDelete() {
        WebElement webElement = driver.findElement(By.xpath("//div/h3[contains(text(), 'Тестовый шаблон №1')]/../..//div/button[2]"));
        webElement.click();
        // Ожидание видимости на экране окна редактирования/удаления шаблона с названием 'Тестовый шаблон №1'.
        waitForTheTitleOfTheModalWindowOfDeletingTheTemplate();
    }

    @Step("Проверка, что шаблон сохранен и добавлен в список и его название соответствует тестовому.")
    public void findWebElementToCheck() {
        String stringElement = driver.findElement(By.xpath("//div/h3[contains(text(), 'Тестовый шаблон №1')]/../..//div/button[2]/../../div/h3")).getText();
        Assert.assertEquals(stringElement, TEMPLATE_NAME);
    }

    @Step("Нахожу в списке созданный тестовый шаблон №2 и кликаю на кнопку 'Свойства шаблона'.")
    public void findWebElementToDelete_2() {
        WebElement webElement = driver.findElement(By.xpath("//div/h3[contains(text(), 'Тестовый шаблон №2')]/../..//div/button[2]"));
        webElement.click();
        // Ожидание видимости на экране окна редактирования/удаления шаблона с названием 'Тестовый шаблон №2'.
        waitForTheTitleOfTheModalWindowOfDeletingTheTemplate();
    }

    @Step("Проверка, что шаблон №2 сохранен и добавлен в список и его название соответствует тестовому.")
    public void findWebElement_2ToCheck() {
        String stringElement = driver.findElement(By.xpath("//div/h3[contains(text(), 'Тестовый шаблон №2')]/../..//div/button[2]/../../div/h3")).getText();
        Assert.assertEquals(stringElement, TEMPLATE_NAME_2);
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

    @Step("Единый шаг удаления тестовых данных - тестового шаблона №2.")
    public void deleteTestDataFromTheListOfTemplates_2() {
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        findWebElementToDelete_2();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editButtonClick();
        // Удаляю тестовые данные.
        deleteTestData();
    }

    @Step("Единый шаг удаления тестовых данных, когда ещё открыта форма и успешно закончились проверки.")
    public void deleteTestDataFromTheListOfTemplatesAfterAssertsAndTheModalWindowIsStillOpen() {
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editButtonClick();
        // Удаляю тестовые данные.
        deleteTestData();
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
        Assert.assertEquals(stringElement, "Черновик");
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
        Assert.assertEquals(stringElement, "ivtyulkin");
    }

    @Step("Проверка, что поле 'Дата последнего редактирования' заполнено и содержит значение.")
    public void fieldToOfTheDateIsFilled() {
        String stringElement = driver.findElement(calendarFieldDateOfTheLastEditing).getAttribute("value");
        assertNotNull(stringElement);
    }

    @Step("Проверка, что поле 'Дата создания шаблона' заполнено текущей датой и временем создания шаблона.")
    public void fieldToOfTheDateIsFilledProperly() {
        String stringElement = driver.findElement(calendarFieldDateOfCreating).getAttribute("value");
        Assert.assertEquals(stringElement, formatDate());
    }

    @Step("Проверка, что поле 'Документ-основание' заполнено и содержит значение.")
    public void fieldOfTheBasedDocumentIsFilled() {
        String stringElement = driver.findElement(basedOnTheDocumentField).getAttribute("value");
        Assert.assertEquals(stringElement, BASED_ON_THE_DOCUMENT);
    }

    @Step("Удаляю загруженный файл из окна редактирования шаблона - из поля 'Загрузить документ-основание'.")
    public void deleteTheBaseDocumentFromTheUploadField() {
        driver.findElement(deleteTheBaseDocumentThatWasUploaded).isEnabled();
        driver.findElement(deleteTheBaseDocumentThatWasUploaded).click();
    }

    @Step("Проверяю, что поле 'Наименование шаблона' заполнено новым названием и содержит новое название шаблона - 'Тестовый шаблон №2'.")
    public void fieldOfTemplateNameIsFilledWithTheNewName() {
        String stringElement = driver.findElement(templateNameField).getAttribute("value");
        Assert.assertEquals(stringElement, TEMPLATE_NAME_2);
    }

    @Step("Проверяю, что поле 'Документ-основание' заполнено новым названием и содержит - 'Документ-основание №2'.")
    public void fieldOfBaseDocumentIsFilledWithTheNewName() {
        String stringElement = driver.findElement(basedOnTheDocumentField).getAttribute("value");
        Assert.assertEquals(stringElement, BASED_ON_THE_DOCUMENT_2);
    }

    // Ожидания.
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