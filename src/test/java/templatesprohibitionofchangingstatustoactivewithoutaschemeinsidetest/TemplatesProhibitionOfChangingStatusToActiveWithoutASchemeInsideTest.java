package templatesprohibitionofchangingstatustoactivewithoutaschemeinsidetest;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import processtemplates.ProcessesTemplates;

import static processtemplates.CreatingANewProcessTemplate.errorMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TemplatesProhibitionOfChangingStatusToActiveWithoutASchemeInsideTest {

    /**
     * @Author Ivan Tyulkin, QA;
     * @Date 24.07.2023;
     *
     * Сьют всего package. : Шаблоны: Работа со статусами / запрет на изменение статуса шаблона на Действующий без схемы внутри
     * Предварительные действия до начала работы (описание методов произведено в package ru.sbrf.cpri.templates.advance классе AdvanceSteps):
     * 1. Авторизация под администратором
     * 2. Зайти в раздел администрирование
     * 3. Зайти в раздел Подразделения
     */

    private WebDriver driver;
    private AdvanceSteps advanceSteps;
    private AdministrationPage administrationPage;
    private ProcessesTemplates processesTemplates;
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
    @DisplayName("Тест на проверку успешного создания и удаления шаблона процесса.")
    @Description("Проверка, что тестовые данные - создание и удаление шаблона процесса - успешно можно создать и удалить.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T535")
    public void shouldCreateAndDeleteATemplateTest() throws InterruptedException {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        processesTemplates = new ProcessesTemplates(driver);
        processesTemplates.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        processesTemplates.fillTheFormOfAddingANewTemplate();
        // Проверка успешного создания нового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон создан')]")).isEnabled()));
        Thread.sleep(3000);
        // Единый шаг удаления тестовых данных.
        processesTemplates.deleteTestDataFromTheListOfTemplates();
        // Проверка успешного удаления тестового шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div[contains(text(), 'Шаблон удален')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку свойств шаблона.")
    @Description("Проверка, что открывается модальное окно с формой редактирования шаблона, поля ввода не доступны для нажатия, поля заполнены данными.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T535")
    public void shouldCheckTheTemplatePropertiesTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        processesTemplates = new ProcessesTemplates(driver);
        processesTemplates.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        processesTemplates.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        processesTemplates.findWebElementToDelete();
        // Проверки.
        // Проверка успешного всплытия модального окна с формой редактирования шаблона.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/span/strong[contains(text(), 'Свойства шаблона')]")).isEnabled()));
        // Проверка, что поле 'Наименование шаблона' недоступно для ввода данных.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'name']")).isEnabled()));
        // Проверка, что поле 'Наименование шаблона' заполнено и содержит название шаблона.
        processesTemplates.fieldOfTemplateNameIsFilled();
        // Проверка, что поле 'Статус шаблона' недоступно для ввода данных.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/span[contains(text(), 'Черновик')]")).isEnabled()));
        // Проверка, что поле 'Статус шаблона' заполнено и содержит название шаблона.
        processesTemplates.fieldOfTemplateStatusIsFiled();
        // Проверка, что поле 'Версия' недоступно для ввода данных.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'version']")).isEnabled()));
        // Проверка, что поле 'Версия' заполнено и содержит значение.
        processesTemplates.fieldOfTemplateVersionIsFilled();
        // Проверка, что поле 'ФИО последнего пользователя' недоступно для ввода данных.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'updatedBy']")).isEnabled()));
        // Проверка, что поле 'ФИО последнего пользователя' заполнено и содержит значение.
        processesTemplates.fieldOfFioOfTheLastUserIsFilled();
        // Проверка, что поле 'Дата последнего редактирования' недоступно для ввода данных.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form/div[5]/div[1]//div/button")).isEnabled()));
        // Проверка, что поле 'Дата последнего редактирования' заполнено и содержит значение.
        processesTemplates.fieldFromOfTheCalendarIsFilled();
        // Проверка, что поле 'Дата создания' недоступно для ввода данных.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form/div[5]/div[2]//div/button")).isEnabled()));
        // Проверка, что поле 'Дата создания' заполнено и содержит значение.
        processesTemplates.fieldToOfTheCalendarIsFilled();
        // Проверка, что поле 'Документ-основание' недоступно для ввода данных.
        assertThat(false, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'baseDocument']")).isEnabled()));
        // Проверка, что поле 'Документ-основание' заполнено и содержит значение.
        processesTemplates.fieldOfTheBasedDocumentIsFilled();
        // Единый шаг удаления тестовых данных.
        driver.navigate().refresh();
        processesTemplates.deleteTestDataFromTheListOfTemplates();
    }

    @Test
    @DisplayName("Тест на проверку кнопки 'Редактировать'.")
    @Description("Проверка, что активны поля с наименованием шаблона, документом-основанием, статусом шаблона и загрузкой документа-основания, а также появилась кнопка 'Удалить'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T535")
    public void shouldCheckTheEditButtonTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        processesTemplates = new ProcessesTemplates(driver);
        processesTemplates.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        processesTemplates.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        processesTemplates.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        processesTemplates.editButtonClick();
        // Проверки.
        // Проверка, что поле 'Наименование шаблона' доступно для ввода данных.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'name']")).isEnabled()));
        // Проверка, что поле 'Статус шаблона' доступно для ввода данных.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/span[contains(text(), 'Черновик')]")).isEnabled()));
        // Проверка, что поле 'Документ-основание' доступно для ввода данных.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/input[@name = 'baseDocument']")).isEnabled()));
        // Проверка, что поле 'Добавить документ-основание' доступно для загрузки данных.
        new WebDriverWait(driver, TIME_OUT_10)
                .until(ExpectedConditions.elementToBeClickable(addABasedDocumentToUpload));
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/form//div/span/pre")).isEnabled()));
        // Единый шаг удаления тестовых данных.
        driver.navigate().refresh();
        processesTemplates.deleteTestDataFromTheListOfTemplates();
    }

    @Test
    @DisplayName("Тест на проверку статуса шаблона 'Действующий' из выпадающего списка.")
    @Description("Проверка, что в поле после выбора статуса отображается статус шаблона 'Действующий', при этом кнопка 'Сохранить' активна.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T535")
    public void shouldCheckTheStatusOfTheTemplateTest() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        processesTemplates = new ProcessesTemplates(driver);
        processesTemplates.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        processesTemplates.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        processesTemplates.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        processesTemplates.editButtonClick();
        // Изменяю статус шаблона с 'Черновик' на 'Действующий', и убираю фокус с поля.
        processesTemplates.changeTheTemplateStatus();
        // Проверка, что поле статуса поменялось на 'Действующий'.
        processesTemplates.fieldStatusOfTheTemplateHasBeenChangedAndNowIsFilledWithTheProperStatus();
        // Проверка, что кнопка 'Сохранить' активна.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
        // Единый шаг удаления тестовых данных.
        driver.navigate().refresh();
        processesTemplates.deleteTestDataFromTheListOfTemplates();
    }

    @Test
    @DisplayName("Тест на проверку кнопки 'Сохранить'.")
    @Description("Проверка, что при нажатии на кнопку 'Сохранить' возникает сообщение об ошибке - 'Отсутствует схема в шаблоне, невозможно перевести в статус' 'Действующий', модальное окно при этом не закрылось, а кнопка 'Сохранить' по-прежнему активна.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T535")
    public void shouldCheckTheSaveButton() {
        // Единый шаг входа на ресурс зарегистрированного пользователя - под администратором. Вход на тестовый стенд.
        advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystemByAdmin();
        // Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.
        administrationPage = new AdministrationPage(driver);
        administrationPage.processesTemplatesButtonClick();
        // Нажимаю на кнопку 'Добавить шаблон'.
        processesTemplates = new ProcessesTemplates(driver);
        processesTemplates.newTemplateAddButtonClick();
        // Единый шаг заполнения формы создания нового шаблона с нажатием кнопки 'Сохранить' в конце.
        processesTemplates.fillTheFormOfAddingANewTemplate();
        // Нахожу в списке только что созданный тестовый шаблон и кликаю на кнопку 'Свойства шаблона'.
        processesTemplates.findWebElementToDelete();
        // Нажимаю кнопку 'Редактировать' в модульном всплывающем окне 'Свойства шаблона'.
        processesTemplates.editButtonClick();
        // Изменяю статус шаблона с 'Черновик' на 'Действующий', и убираю фокус с поля.
        processesTemplates.changeTheTemplateStatus();
        // Проверка, что поле статуса поменялось на 'Действующий'.
        processesTemplates.fieldStatusOfTheTemplateHasBeenChangedAndNowIsFilledWithTheProperStatus();
        // Нажимаю кнопку 'Сохранить'.
        processesTemplates.saveButtonClick();
        // Проверка, что появляется сообщение об ошибке сохранения.
        assertThat(true, equalTo(driver.findElement(errorMessage).isEnabled()));
        // Проверка, что кнопка 'Сохранить' активна.
        assertThat(true, equalTo(driver.findElement(By
                .xpath(".//div/button[contains(text(), 'Сохранить')]")).isEnabled()));
        // Единый шаг удаления тестовых данных.
        driver.navigate().refresh();
        processesTemplates.deleteTestDataFromTheListOfTemplates();
    }

    @After
    public void teardown() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}
