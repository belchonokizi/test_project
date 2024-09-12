package helpDesk;

import core.BaseSeleniumPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import readProperties.ConfigProvider;

public class MainPage extends BaseSeleniumPage {
    /** 1 способ
    private final By queueList = By.id("id_queue");
    private final By queueList2 = By.xpath("//select[@id = 'id_queue']");

    //нахождение элемента
    private WebElement queueListElement = driver.findElement(queueList);
     **/

    //инициализация элемента только тогда, когда к нему обращаемся
    @FindBy(xpath = "//select[@id = 'id_queue']")
    private WebElement queueList;

    @FindBy(xpath = "//select[@id = 'id_queue']//option[@value='1']")
    private WebElement queueValue;

    @FindBy(id = "id_title")
    private WebElement title;

    @FindBy(id = "id_body")
    private WebElement body;

    @FindBy(id = "id_due_date")
    private WebElement dateField;

    //выбор в календаре даты 23
    @FindBy(xpath = "//table[@class='ui-datepicker-calendar']//a[text()='23']")
    private WebElement dateValue;

    @FindBy(id = "id_submitter_email")
    private WebElement email;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(id = "userDropdown")
    private WebElement loginButton;

    public MainPage() {
        //открытие страницы
        driver.get(ConfigProvider.URL);
        //инициализируем элементы через PageFactory
        PageFactory.initElements(driver, this);
    }

    public MainPage createTicket(String titleValue, String bodyValue, String emailValue) {
        queueList.click();
        queueValue.click();
        //вписываем в поле значение
        title.sendKeys(titleValue);
        body.sendKeys(bodyValue);
        dateField.click();
        dateValue.click();
        email.sendKeys(emailValue);
        submitButton.click();
        return this;
    }

    //открываем страницу с авторизацией
    public LoginPage openLogin() {
        loginButton.click();
        return new LoginPage();
    }
}
