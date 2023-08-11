package editpropertiesoftheprocesstemplate;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class EditPropertiesOfTheProcessTemplateTest {

    /**
     * @Author Ivan Tyulkin, QA;
     * @Date 28.07.2023;
     *
     * Сьют всего package. : Шаблоны: Редактирование свойств шаблона процесса
     * Предварительные действия до начала работы (описание методов произведено в package ru.sbrf.cpri.templates.advance классе AdvanceSteps):
     * 1. Авторизация под администратором
     * 2. Зайти в раздел администрирование
     * 3. Зайти в раздел Подразделения
     */

    private WebDriver driver;
    private AdvanceSteps advanceSteps;
    private AdministrationPage administrationPage;
    private EditPropertiesOfTheProcessTemplate editPropertiesOfTheProcessTemplate;
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
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldSuccessfullyCreateANewTemplateAndDeleteItTest() throws InterruptedException {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Проверка успешного создания нового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон создан')]")).isEnabled()));
        Thread.sleep(3000);
        // Единый шаг удаления тестовых данных.
        editPropertiesOfTheProcessTemplate.deleteTestDataFromTheListOfTemplates();
        // Проверка успешного удаления тестового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон удален')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку успешного нажатия кнопки 'Свойства шаблона'.")
    @Description("Проверка, что открылось модальное окно с формой редактирования шаблона, поля ввода не доступны для нажатия и заполнены тестовыми данными.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldTheModalWindowPopsUpTheFieldsHaveDataAndAreUnreachableToBeEditedTest() throws InterruptedException {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete();
        // Проверки.
        // Проверка, что открылось модальное окно с формой редактирования шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/span/strong[contains(text(), 'Свойства шаблона')]")).isEnabled()));
        // Проверяю, что поле 'Наименование шаблона' заполнено и содержит название шаблона.
        editPropertiesOfTheProcessTemplate.fieldOfTemplateNameIsFilled();
        // Проверяю, что поле 'Наименование шаблона' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'name']")).isEnabled()));
        // Проверка, что поле 'Статус шаблона' заполнено и содержит название шаблона.
        editPropertiesOfTheProcessTemplate.fieldOfTemplateStatusIsFiled();
        // Проверяю, что поле 'Статус шаблона' недоступно для клика по нему.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/span[contains(text(), 'Черновик')]")).isEnabled()));
        // Проверка, что поле 'Версия' заполнено и содержит значение '1'.
        editPropertiesOfTheProcessTemplate.fieldOfTemplateVersionIsFilled();
        // Проверяю, что поле 'Версия' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'version']")).isEnabled()));
        // Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение.
        editPropertiesOfTheProcessTemplate.fieldOfFioOfTheLastUserIsFilledWithFioNotNull();
        // Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение - имя текущего пользователя.
        editPropertiesOfTheProcessTemplate.fieldOfFioOfTheLastUserIsFilledWithTyulkinIvan();
        // Проверяю, что поле 'ФИО последнего пользователя' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'updatedBy']")).isEnabled()));
        // Проверка, что поле 'Дата последнего редактирования' заполнено и содержит значение.
        editPropertiesOfTheProcessTemplate.fieldToOfTheDateIsFilled();
        // Проверяю, что поле 'Дата последнего редактирования' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                        .cssSelector("div.TemplatePropertiesModal_dataPickerWrapper__JGzzU > div:nth-child(1) > div.ant-col.ant-form-item-control > div > div > div > div > div > div > div > input"))
                .isEnabled()));
        // Проверка, что поле 'Дата создания шаблона' заполнено текущей датой и временем создания шаблона.
        editPropertiesOfTheProcessTemplate.fieldToOfTheDateIsFilledProperly();
        // Проверяю, что поле 'Дата создания шаблона' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                        .cssSelector("div.TemplatesPanel_content__1YpsI > div.Modal_wrapper__meIPm > div > div.Modal_content__ouhhq.undefined > form > div.TemplatePropertiesModal_dataPickerWrapper__JGzzU > div:nth-child(2) > div.ant-col.ant-form-item-control > div > div > div > div > div > div > div > input"))
                .isEnabled()));
        // Проверка, что поле 'Документ-основание' заполнено и содержит значение.
        editPropertiesOfTheProcessTemplate.fieldOfTheBasedDocumentIsFilled();
        // Проверяю, что поле 'Документ-основание' недоступно для клика по нему.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'baseDocument']")).isEnabled()));
        // Проверяю, что загруженный файл загружен и соответствует тестовому файлу по названию.
        editPropertiesOfTheProcessTemplate.isFileExist("Test_data");
        // Единый шаг удаления тестовых данных, когда ещё открыта форма и успешно закончились проверки.
        Thread.sleep(3000);
        editPropertiesOfTheProcessTemplate.deleteTestDataFromTheListOfTemplatesAfterAssertsAndTheModalWindowIsStillOpen();
        // Проверка успешного удаления тестового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон удален')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку успешного нажатия кнопки 'Редактировать'.")
    @Description("Проверка, что активны поля с наименованием шаблона, документом-основанием, статусом шаблона и загрузкой документа-основания, а также появилась кнопка 'Удалить'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldTheFieldsBeActiveAndThereIsADeleteButtonTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.editButtonClick();
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
        editPropertiesOfTheProcessTemplate.deleteTheBaseDocumentFromTheUploadField();
        // Проверяю, что поле 'Добавить документ-основание' теперь пустое и доступно для загрузки нового файла.
        assertThat(true, equalTo(driver.findElement(addABasedDocumentToUpload).isEnabled()));
        // Проверяю, что появилась кнопка 'Удалить'.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Удалить')]")).isEnabled()));
        // Удаляю тестовые данные.
        editPropertiesOfTheProcessTemplate.deleteTestData();
    }

    @Test
    @DisplayName("Тест на проверку успешного ввода нового наименования шаблона.")
    @Description("Проверка, что новые данные отображаются в поле ввода и кнопка 'Сохранить' активна.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldEditTestDataInYheFieldsOfTheFormTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.editButtonClick();
        // Вношу в поле 'Наименование шаблона' новое название шаблона.
        editPropertiesOfTheProcessTemplate.fillTheFieldOfTemplateNameByANewName();
        // Проверки.
        // Проверяю, что поле 'Наименование шаблона' заполнено новым названием и содержит новое название шаблона - 'Тестовый шаблон №2'.
        editPropertiesOfTheProcessTemplate.fieldOfTemplateNameIsFilledWithTheNewName();
        // Проверяю, что кнопка 'Сохранить' активна.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
        // Удаляю тестовые данные.
        editPropertiesOfTheProcessTemplate.deleteTestData();
    }

    @Test
    @DisplayName("Тест на проверку успешного ввода нового документа-основания.")
    @Description("Проверка, что новые данные отображаются в поле ввода и кнопка 'Сохранить' активна.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldANewBaseDocumentBeInputSuccessfullyTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.editButtonClick();
        // Вношу в поле 'Документ-основание' новое название документа.
        editPropertiesOfTheProcessTemplate.filTheFieldOfBaseDocumentWithTheNewData();
        // Проверки.
        // Проверяю, что поле 'Документ-основание' заполнено новым названием и содержит - 'Документ-основание №2'.
        editPropertiesOfTheProcessTemplate.fieldOfBaseDocumentIsFilledWithTheNewName();
        // Проверяю, что кнопка 'Сохранить' активна.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
        // Удаляю тестовые данные.
        editPropertiesOfTheProcessTemplate.deleteTestData();
    }

    /**
     *      Шаг №6 тест-кейса https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486 игнорирую,
     *      так как этот шаг был уже проверен ранее при
     *      проверке поля методом deleteTheBaseDocumentFromTheUploadField();
     *      Название теста, в котором имеется данная проверка - shouldTheFieldsBeActiveAndThereIsADeleteButtonTest() - в текущем тестовом классе.
     */

    @Test
    @DisplayName("Тест на проверку успешной загрузки нового документа-основания.")
    @Description("Проверка, что в поле отображается новый документ-основание.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldUploadANewBaseDocumentToTheFieldTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.editButtonClick();
        // Удаляю загруженный файл из окна редактирования шаблона - из поля 'Загрузить документ-основание'.
        editPropertiesOfTheProcessTemplate.deleteTheBaseDocumentFromTheUploadField();
        // 1. Загружаю документ-основание 2-ю версию. Это файл Excel, весом не более 20МБ.
        // 2. Проверяю, что файл загружен успешно.
        // 3. Проверяю, что загруженный файл соответствует тестовому файлу 'Test_data_2.xlsx' по названию.
        editPropertiesOfTheProcessTemplate.uploadTheBasedDocumentVersion_2();
        // Удаляю тестовые данные.
        editPropertiesOfTheProcessTemplate.deleteTestData();
    }

    @Test
    @DisplayName("Тест на проверку успешного сохранения сделанных изменений.")
    @Description("Проверка, что после нажатия кнопки 'Сохранить', модальное окно закрылось, появилось сообщение об успешном изменении шаблона и в списке шаблонов процесса шаблон отображается под новым именем.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldSaveAllNewDataTest() throws InterruptedException {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.editButtonClick();
        // Вношу в поле 'Наименование шаблона' новое название шаблона.
        editPropertiesOfTheProcessTemplate.fillTheFieldOfTemplateNameByANewName();
        // Вношу в поле 'Документ-основание' новое название документа.
        editPropertiesOfTheProcessTemplate.filTheFieldOfBaseDocumentWithTheNewData();
        // Удаляю загруженный файл из окна редактирования шаблона - из поля 'Загрузить документ-основание'.
        editPropertiesOfTheProcessTemplate.deleteTheBaseDocumentFromTheUploadField();
        // Загружаю документ-основание 2-ю версию. Это файл Excel, весом не более 20МБ.
        editPropertiesOfTheProcessTemplate.uploadTheBasedDocumentVersion_2();
        // Нажимаю кнопку 'Сохранить'.
        editPropertiesOfTheProcessTemplate.saveButtonClick();
        // Проверки.
        // Проверяю, что появилось сообщение об успешном изменении шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон изменен')]")).isEnabled()));
        Thread.sleep(2000);
        // Проверяю, закрылось ли модальное окно.
        editPropertiesOfTheProcessTemplate.isTheModalWindowClosed();
        // Проверка, что в списке шаблонов процесса шаблон отображается под новым именем.
        editPropertiesOfTheProcessTemplate.findWebElement_2ToCheck();
        // Удаляю тестовые данные.
        // Единый шаг удаления тестовых данных - тестового шаблона №2.
        editPropertiesOfTheProcessTemplate.deleteTestDataFromTheListOfTemplates_2();
    }

    @Test
    @DisplayName("Тест на проверку успешного клика на 'Свойства шаблона'.")
    @Description("Проверка, что после нажатия кнопки 'Свойства шаблона', отображается имя пользователя, который изменил шаблон, отображается время редактирования шаблона (время в которое был сохранён шаблон).")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T486")
    public void shouldDisplayUserNameAndEditTime() throws InterruptedException {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        editPropertiesOfTheProcessTemplate = new EditPropertiesOfTheProcessTemplate(driver);
        editPropertiesOfTheProcessTemplate.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        editPropertiesOfTheProcessTemplate.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.editButtonClick();
        // Вношу в поле 'Наименование шаблона' новое название шаблона.
        editPropertiesOfTheProcessTemplate.fillTheFieldOfTemplateNameByANewName();
        // Вношу в поле 'Документ-основание' новое название документа.
        editPropertiesOfTheProcessTemplate.filTheFieldOfBaseDocumentWithTheNewData();
        // Удаляю загруженный файл из окна редактирования шаблона - из поля 'Загрузить документ-основание'.
        editPropertiesOfTheProcessTemplate.deleteTheBaseDocumentFromTheUploadField();
        // Загружаю документ-основание 2-ю версию. Это файл Excel, весом не более 20МБ.
        editPropertiesOfTheProcessTemplate.uploadTheBasedDocumentVersion_2();
        // Нажимаю кнопку 'Сохранить'.
        editPropertiesOfTheProcessTemplate.saveButtonClick();
        Thread.sleep(5000);
        // Нахожу в списке созданный тестовый шаблон №2 и кликаю на кнопку 'Свойства шаблона'.
        editPropertiesOfTheProcessTemplate.findWebElementToDelete_2();
        // Проверки.
        // Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение - имя текущего пользователя.
        editPropertiesOfTheProcessTemplate.fieldOfFioOfTheLastUserIsFilledWithTyulkinIvan();
        // Проверка, что поле 'Дата создания шаблона' заполнено текущей датой и временем создания шаблона.
        editPropertiesOfTheProcessTemplate.fieldToOfTheDateIsFilledProperly();
        // Удаляю тестовый шаблон.
        editPropertiesOfTheProcessTemplate.editButtonClick();
        editPropertiesOfTheProcessTemplate.deleteTestData();
    }

    @After
    public void teardown() throws InterruptedException {
        Thread.sleep(15000);
        driver.quit();
    }
}
