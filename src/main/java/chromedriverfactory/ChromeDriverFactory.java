import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverFactory {

    private static WebDriver driver;

    public static final String BASE_URI_CERT = "https://cpri-ci02887368.apps.ift-efsemp1-dm.delta.sbrf.ru";
    public static final String PROPERTY = "webdriver.chrome.driver";
    public static final String PATH = "C:\\Users\\21088700\\IdeaProjects\\chromeDriver\\chromedriver.exe";

    // Метод, позволяющий убедиться, что драйвер работает и открывается браузер.
    public static void main(String[] args) {
        System.setProperty(PROPERTY, PATH);
        ChromeDriver driver = new ChromeDriver();
        driver.get(BASE_URI_CERT);
    }

    // Метод для запуска браузера.
    public static void setUpDriver() {
        System.setProperty(PROPERTY, PATH);
        //ChromeDriver driver;
    }
}