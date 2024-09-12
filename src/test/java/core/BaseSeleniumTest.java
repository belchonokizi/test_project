package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public abstract class BaseSeleniumTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        //скачиваем драйвер
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //открываем страницу на весь экран
        driver.manage().window().maximize();
        //сколько ждем загрузку
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        //чтобы у каждой страницы было обращение к драйверу
        BaseSeleniumPage.setDriver(driver);
    }

    @AfterEach
    public void tearDown() {
//        закрытие драйвера, обязательно
        driver.close();
//        закрытие браузера, необязательно
//        driver.quit();
    }

}
