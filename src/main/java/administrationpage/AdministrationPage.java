import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AdministrationPage {

    private final WebDriver driver;

    public AdministrationPage(WebDriver driver) {
        this.driver = driver;
    }

    /*
    Локаторы для элементов страницы "https://cpri-ci02887368.apps.ift-efsemp1-dm.delta.sbrf.ru/cpri/admin"
    Это - начальная страница, на которую попадает АДМИН сразу после заполнения полей в форме входа.
     */
    private final By processesTemplatesButton = By.xpath("//div/section/a[contains(text(), 'Шаблоны процессов')]");

    // Методы.
    @Step("Нажимаю на кнопку 'Шаблоны процессов' для перехода в раздел сайта 'Шаблоны процессов'.")
    public void processesTemplatesButtonClick() {
        driver.findElement(processesTemplatesButton).click();
        // Ожидание видимости на экране следующей страницы с заголовком 'Шаблоны процессов'.
        waitForTheContent();
        // Ожидание видимости на экране кнопки 'Добавить шаблон'.
        waitForTheTemplateAddButton();
    }

    // Ожидания.
    @Step("Ожидание видимости на экране следующей страницы с заголовком 'Шаблоны процессов'.")
    public void waitForTheContent() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div/h1[contains(text(), 'Шаблоны процессов')]")));
    }

    @Step("Ожидание видимости на экране кнопки 'Добавить шаблон'.")
    public void waitForTheTemplateAddButton() {
        new WebDriverWait(driver, TIME_OUT_15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div/button/span[contains(text(), 'Добавить шаблон')]")));
    }
}