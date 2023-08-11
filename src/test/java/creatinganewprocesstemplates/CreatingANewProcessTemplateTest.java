package creatinganewprocesstemplates;

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
import processtemplates.CreatingANewProcessTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class CreatingANewProcessTemplateTest {

    /**
     * @Author Ivan Tyulkin, QA;
     * @Date 26.07.2023;
     *
     * Сьют всего package. : Шаблоны: Создание нового шаблона процесса
     * Предварительные действия до начала работы (описание методов произведено в package ru.sbrf.cpri.templates.advance классе AdvanceSteps):
     * 1. Авторизация под администратором
     * 2. Зайти в раздел администрирование
     * 3. Зайти в раздел Подразделения
     */

    private WebDriver driver;
    private AdvanceSteps advanceSteps;
    private AdministrationPage administrationPage;
    private CreatingANewProcessTemplate creatingANewProcessTemplate;
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
    @DisplayName("Тест на проверку успешного нажатия кнопки добавления нового шаблона.")
    @Description("Проверка, что после нажатия кнопки добавления нового шаблона появляется модальное окно 'Новый шаблон' и кнопка 'Сохранить' не активна.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldPopupAModalWindowAndTheSaveButtonIsDisableTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Проверки.
        // Проверка, что появляется модальное окно 'Новый шаблон'.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/span/strong[contains(text(), 'Новый шаблон')]")).isEnabled()));
        // Проверка, что кнопка 'Сохранить' не активна.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку ввода уникального наименования нового шаблона.")
    @Description("Проверка, что после ввода уникального наименования нового шаблона, его название отображается в поле ввода и кнопка 'Сохранить' не активна.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldDisplayTheNameOfTheTemplateAndTheSaveButtonIsDisabledTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Вношу в поле 'Наименование шаблона' название шаблона.
        creatingANewProcessTemplate.fillTheFieldOfTemplateName();
        // Проверки.
        // Проверяю, что поле 'Наименование шаблона' заполнено и содержит название шаблона.
        creatingANewProcessTemplate.fieldOfTemplateNameIsFilled();
        // Проверка, что кнопка 'Сохранить' не активна.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку, что в поле 'Версия' информация появляется автоматически с '1'.")
    @Description("Проверка, что поле 'Версия' заполняется автоматически, начало отсчёта с '1'.")
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldFilledAutomaticallyTheFieldOfVersionTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Проверки.
        // Проверка, что поле 'Версия' заполнено и содержит значение '1'.
        creatingANewProcessTemplate.fieldOfTemplateVersionIsFilled();
    }

    @Test
    @DisplayName("Тест на проверку, что 'ФИО последнего пользователя' заполняется автоматически и берётся от авторизованного пользователя.")
    @Description("Проверка, что поле 'ФИО последнего пользователя' заполняется автоматически и берётся от авторизованного пользователя кнопка 'Сохранить' не активна.")
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldFilledAutomaticallyTheFieldOfFioTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Проверки.
        // Проверка, что поле 'ФИО последнего пользователя' вообще имеет значение, заполненное автоматически, то есть, не нулевое.
        creatingANewProcessTemplate.fieldOfFioOfTheLastUserIsFilledWithFioNotNull();
        // Проверка, что поле 'ФИО последнего пользователя' заполняется автоматически и берётся от текущего авторизованного пользователя
        creatingANewProcessTemplate.fieldOfFioOfTheLastUserIsFilledWithTyulkinIvan();
        // Проверка, что кнопка 'Сохранить' не активна.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку, что поле 'Дата создания шаблона' заполняется автоматически текущей датой.")
    @Description("Проверка, что поле 'Дата создания шаблона' заполняется автоматически текущей датой и временем.")
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldFilledAutomaticallyTheFieldOfDateAndTimeTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Проверки.
        // Проверка, что поле 'Дата создания' заполняется автоматически и содержит значение.
        creatingANewProcessTemplate.fieldToOfTheDateIsFilled();
        // Проверка, что поле 'Дата создания шаблона' заполняется автоматически текущей датой и временем.
        creatingANewProcessTemplate.fieldToOfTheDateIsFilledProperly();
        // Проверка, что кнопка 'Сохранить' не активна.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку ввода названия документа-основания.")
    @Description("Проверка, что в поле 'Документ-основание' данные отображаются и кнопка 'Сохранить' активна.")
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldTheNameOfTheBasedDocumentDisplayedAndTheSaveButtonIsEnabledTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Вношу в поле 'Наименование шаблона' название шаблона.
        creatingANewProcessTemplate.fillTheFieldOfTemplateName();
        // Вношу в поле 'Документ-основание' название тестового документа, согласно 6 шагу тест-кейса 'CPRI-T493' - 'Документ 123'.
        creatingANewProcessTemplate.filTheFieldOfBaseDocument();
        // Проверки.
        // Проверка, что поле 'Документ-основание' заполнено и содержит значение.
        creatingANewProcessTemplate.fieldOfTheBasedDocumentIsFilled();
        // Проверка, что кнопка 'Сохранить' активна.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на добавление (upload) документа-основания.")
    @Description("Проверка, загрузка документа-основания проходит успешно и он отображается в модальном окне.")
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldUploadABasedDocumentTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Вношу в поле 'Наименование шаблона' название шаблона.
        creatingANewProcessTemplate.fillTheFieldOfTemplateName();
        // Вношу в поле 'Документ-основание' название тестового документа, согласно 6 шагу тест-кейса 'CPRI-T493' - 'Документ 123'.
        creatingANewProcessTemplate.filTheFieldOfBaseDocument();
        // Загружаю документ-основание. Это файл Excel, весом не более 20МБ, согласно 7 шагу тест-кейса 'CPRI-T493'.
        // В этом же методе проверяю, что файл загружен успешно.
        creatingANewProcessTemplate.uploadTheBasedDocument();
        // Проверка, что кнопка 'Сохранить' активна.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на нажатие кнопки 'Сохранить'.")
    @Description("Проверка, что после нажатия кнопки 'Сохранить' модальное окно закрылось, отображается сообщение об успешном создании шаблона, шаблон сохранен и добавлен в список.")
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T493")
    public void shouldTheTemplateBeSavedSuccessfullyTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        creatingANewProcessTemplate = new CreatingANewProcessTemplate(driver);
        creatingANewProcessTemplate.newTemplateAddButtonClick();
        // Вношу в поле 'Наименование шаблона' название шаблона.
        creatingANewProcessTemplate.fillTheFieldOfTemplateName();
        // Вношу в поле 'Документ-основание' название тестового документа, согласно 6 шагу тест-кейса 'CPRI-T493' - 'Документ 123'.
        creatingANewProcessTemplate.filTheFieldOfBaseDocument();
        // Загружаю документ-основание. Это файл Excel, весом не более 20МБ, согласно 7 шагу тест-кейса 'CPRI-T493'.
        // В этом же методе проверяю, что файл загружен успешно.
        creatingANewProcessTemplate.uploadTheBasedDocument();
        // Нажимаю кнопку 'Сохранить'.
        creatingANewProcessTemplate.saveButtonClick();
        // Проверки.
        // Проверка, что шаблон успешно создан.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон создан')]")).isEnabled()));
        // Проверка, что после нажатия кнопки 'Сохранить' модальное окно закрылось.
        creatingANewProcessTemplate.waitForTheModalWindowToBeClosed();
        // Проверка, что шаблон сохранен и добавлен в список и его название соответствует тестовому - 'Тестовый шаблон №1'.
        creatingANewProcessTemplate.findWebElementToCheck();
        // Удаление тестовых данных.
        driver.navigate().refresh();
        creatingANewProcessTemplate.deleteTestDataFromTheListOfTemplates();
    }

    @After
    public void teardown() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}
