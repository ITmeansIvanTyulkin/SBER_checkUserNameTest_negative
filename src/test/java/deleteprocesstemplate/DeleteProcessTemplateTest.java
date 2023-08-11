package deleteprocesstemplate;

import administrationpage.AdministrationPage;
import advance.AdvanceSteps;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import processtemplates.DeleteProcessTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteProcessTemplateTest {

    /**
     * @Author Ivan Tyulkin, QA;
     * @Date 02.08.2023;
     *
     * Сьют всего package. : Шаблоны: Удаление шаблона процесса
     * Предварительные действия до начала работы (описание методов произведено в package ru.sbrf.cpri.templates.advance классе AdvanceSteps):
     * 1. Авторизация под администратором
     * 2. Зайти в раздел администрирование
     * 3. Зайти в раздел Подразделения
     */

    private WebDriver driver;
    private AdvanceSteps advanceSteps;
    private AdministrationPage administrationPage;
    private DeleteProcessTemplate deleteProcessTemplate;
//    private AdvanceStepsForDev advanceStepsForDev;

    @Before
    public void setUp() {
        setUpDriver();
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.addArguments("--ignore-certificate-errors");
        driver = new ChromeDriver(options);
        //driver.manage().window().maximize(); // Запуск браузера в полном окне.
    }

    @Test
    @DisplayName("Тест на проверку успешного создания нового шаблона.")
    @Description("Проверка, что можно успешно создать новый тестовый шаблон, заполнить все поля, подгрузить документ и затем удалить шаблон.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T502")
    public void shouldDeleteTheTemplateTest() throws InterruptedException {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        deleteProcessTemplate = new DeleteProcessTemplate(driver);
        deleteProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце с проверкой, что шаблон сохранён и добавлен в список и его название соответствует тестовому.
        deleteProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Проверка успешного создания нового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон создан')]")).isEnabled()));
        // Единый шаг удаления тестовых данных.
        Thread.sleep(3000);
        deleteProcessTemplate.deleteTestDataFromTheListOfTemplates();
        // Проверка успешного удаления тестового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон удален')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку успешного нажатия на кнопку 'Свойства шаблона'.")
    @Description("Проверка, что открывается модальное окно с формой редактирования шаблона, поля ввода в форме не доступны для нажатия и заполнены данными.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T502")
    public void shouldPopUpTheFormWithFieldsThatAreUnreachableToEditTest() throws InterruptedException {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        deleteProcessTemplate = new DeleteProcessTemplate(driver);
        deleteProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце с проверкой, что шаблон сохранён и добавлен в список и его название соответствует тестовому.
        deleteProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        deleteProcessTemplate.findWebElementToDelete();
        // Проверки.
        // Проверка, что открылось модальное окно с формой редактирования шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/span/strong[contains(text(), 'Свойства шаблона')]")).isEnabled()));
        // Проверяю, что поле 'Наименование шаблона' заполнено и содержит название шаблона.
        deleteProcessTemplate.fieldOfTemplateNameIsFilled();
        // Проверяю, что поле 'Наименование шаблона' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'name']")).isEnabled()));
        // Проверка, что поле 'Статус шаблона' заполнено и содержит название шаблона.
        deleteProcessTemplate.fieldOfTemplateStatusIsFiled();
        // Проверяю, что поле 'Статус шаблона' недоступно для клика по нему.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/span[contains(text(), 'Черновик')]")).isEnabled()));
        // Проверка, что поле 'Версия' заполнено и содержит значение '1'.
        deleteProcessTemplate.fieldOfTemplateVersionIsFilled();
        // Проверяю, что поле 'Версия' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'version']")).isEnabled()));
        // Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение.
        deleteProcessTemplate.fieldOfFioOfTheLastUserIsFilledWithFioNotNull();
        // Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение.
        deleteProcessTemplate.fieldOfFioOfTheLastUserIsFilledWithTyulkinIvan();
        // Проверяю, что поле 'ФИО последнего пользователя' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'updatedBy']")).isEnabled()));
        // Проверка, что поле 'Дата последнего редактирования' заполнено и содержит значение.
        deleteProcessTemplate.fieldToOfTheDateIsFilled();
        // Проверяю, что поле 'Дата последнего редактирования' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                        .cssSelector("div.TemplatePropertiesModal_dataPickerWrapper__JGzzU > div:nth-child(1) > div.ant-col.ant-form-item-control > div > div > div > div > div > div > div > input"))
                .isEnabled()));
        // Проверка, что поле 'Дата создания шаблона' заполнено текущей датой и временем создания шаблона.
        deleteProcessTemplate.fieldToOfTheDateIsFilledProperly();
        // Проверяю, что поле 'Дата создания шаблона' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                        .cssSelector("div.TemplatesPanel_content__1YpsI > div.Modal_wrapper__meIPm > div > div.Modal_content__ouhhq.undefined > form > div.TemplatePropertiesModal_dataPickerWrapper__JGzzU > div:nth-child(2) > div.ant-col.ant-form-item-control > div > div > div > div > div > div > div > input"))
                .isEnabled()));
        // Проверка, что поле 'Документ-основание' заполнено и содержит значение.
        deleteProcessTemplate.fieldOfTheBasedDocumentIsFilled();
        // Проверяю, что поле 'Документ-основание' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'baseDocument']")).isEnabled()));
        // Проверяю, что загруженный файл загружен и соответствует тестовому файлу по названию.
        deleteProcessTemplate.isFileExist("Test_data");
        // Единый шаг удаления тестовых данных, когда ещё открыта форма и успешно закончились проверки.
        Thread.sleep(3000);
        // Единый шаг удаления тестовых данных, когда ещё открыта форма и успешно закончились проверки.
        deleteProcessTemplate.deleteTestDataFromTheListOfTemplatesAfterAssertsAndTheModalWindowIsStillOpen();
        // Проверка успешного удаления тестового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон удален')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку успешного нажатия на кнопку 'Редактировать'.")
    @Description("Проверка, что активны поля с наименованием шаблона, документом-основанием, статусом шаблона и загрузкой документа-основания, а также появилась кнопка 'Удалить'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T502")
    public void shouldPopUpTheFormWithFieldsThatAreFreeReachableToEditTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        deleteProcessTemplate = new DeleteProcessTemplate(driver);
        deleteProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце с проверкой, что шаблон сохранён и добавлен в список и его название соответствует тестовому.
        deleteProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        deleteProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        deleteProcessTemplate.editButtonClick();
        // Проверки.
        // Проверяю, что поле 'Наименование шаблона' доступно для редактирования.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'name']")).isEnabled()));
        // Проверяю, что поле 'Документ-основание' доступно для редактирования.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'baseDocument']")).isEnabled()));
        // Проверяю, что поле 'Статус шаблона' доступно для редактирования.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/span[contains(text(), 'Черновик')]")).isEnabled()));
        // Проверка поле на активность: удаляю загруженный файл из окна редактирования шаблона - из поля 'Загрузить документ-основание'.
        deleteProcessTemplate.deleteTheBaseDocumentFromTheUploadField();
        // Проверяю, что поле 'Добавить документ-основание' теперь пустое и доступно для загрузки нового файла.
        assertThat(true, equalTo(driver.findElement(addABasedDocumentToUpload).isEnabled()));
        // Проверяю, что появилась кнопка 'Удалить'.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Удалить')]")).isEnabled()));
        // Удаляю тестовые данные.
        deleteProcessTemplate.deleteTestData();
        // Проверка успешного удаления тестового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон удален')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку успешного нажатия на кнопку 'Удалить'.")
    @Description("Проверка, что после нажатия на кнопку 'Удалить' появляется окно с предупреждением об удалении.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T502")
    public void shouldAModalWindowToConfirmDeletingATemplatePopsUp() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        deleteProcessTemplate = new DeleteProcessTemplate(driver);
        deleteProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце с проверкой, что шаблон сохранён и добавлен в список и его название соответствует тестовому.
        deleteProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        deleteProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        deleteProcessTemplate.editButtonClick();
        // Нажатие на кнопку удаления шаблона (тестовых данных) без последующего подтверждения удаления шаблона.
        deleteProcessTemplate.deleteButtonClick();
        // Проверки.
        // Ожидание видимости на экране всплывающего окна, подтверждающего сообщение об удалении.
        deleteProcessTemplate.waitForTheDeletingMessage();
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/span[contains(text(), 'Вы действительно хотите удалить шаблон')]")).isEnabled()));
        // Нажимаю на кнопку 'Да' в окне, подтверждающем удаление шаблона.
        deleteProcessTemplate.confirmButtonYesClick();
        // Проверка успешного удаления тестового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон удален')]")).isEnabled()));
        // Проверяю, закрылось ли модальное окно.
        deleteProcessTemplate.isTheModalWindowClosed();
        // Проверяю, есть ли тестовый шаблон в списке всех шаблонов на странице.
        deleteProcessTemplate.checkIfTheTestTemplateThatHasBeenDeletedIsStillOnThePage();
    }

    @After
    public void teardown() throws InterruptedException {
        Thread.sleep(15000);
        driver.quit();
    }
}
