package org.ibs.steps;

import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AutoTestUISteps {
    private RemoteWebDriver driver;
    @И("драйвер Jenkins")
    public void testVegetableAdd() throws MalformedURLException {
        // Настройка ChromeOptions для Selenoid
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*"); // Добавляем аргумент для разрешения удаленного доступа

        // Настройка DesiredCapabilities для Selenoid
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        // Добавляем параметры для Selenoid
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true); // Включение VNC
        selenoidOptions.put("enableVideo", false); // Отключение записи видео
        capabilities.setCapability("selenoid:options", selenoidOptions);

        // Подключение к Selenoid Hub
        WebDriver driver = new RemoteWebDriver(new URL("http://jenkins.applineselenoid.fvds.ru:4444/wd/hub"), capabilities);

    }

    @И("открыт сайт {string}")
    public void открыть_сайт(String string) {

        driver.get("https://qualit.applineselenoid.fvds.ru/");
    }

    @И("страница загружена")
    public void загрузка_страницы () {
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @И("окно браузера настроено")
    public void развернуть_окно () {
        driver.manage().window().maximize();
    }

    @И("перейти во вкладку \"Песочница\" и выбрать раздел \"Товары\"")
    public void перейти_во_вкладку () {
        RemoteWebElement btnSandbox = (RemoteWebElement) driver.findElement(By.xpath("//a[@class='nav-link dropdown-toggle']"));
        btnSandbox.click();

        RemoteWebElement btnGoods = (RemoteWebElement) driver.findElement(By.xpath("//a[@href='/food']"));
        btnGoods.click();
    }

    @И("нажать на кнопку \"Добавить\"")
    public void нажать_кнопку_добавить () {
        WebElement btnAdd = driver.findElement(By.xpath("//button[@data-toggle='modal']"));
        btnAdd.click();
    }

    @И("ввести название {string} в поле ввода имени товара")
    public void ввести_навзвание_товара (String name) {
        WebElement inputName = driver.findElement(By.xpath("//input[@id='name']"));
        inputName.sendKeys(name);
    }

    @И("выбрать тип товара \"Фрукт\"")
    public void выбрать_тип_товара () {
        WebElement typeDropdown = driver.findElement(By.xpath("//select[@id='type']/..//option[@value='FRUIT']"));
        typeDropdown.click();
    }

    @И("выбрать тип товара \"Овощ\"")
    public void выбрать_тип_товара2 () {
        WebElement typeDropdown = driver.findElement(By.xpath("//select[@id='type']/..//option[@value='VEGETABLE']"));
        typeDropdown.click();
    }

    @И("поставить галочку \"Экзотический\" если {string} равно \"Да\"")
    public void нажать_на_чек_бокс(String condition) {
            WebElement exoticCheckbox = driver.findElement(By.xpath("//input[@id='exotic']"));
            exoticCheckbox.click();
            Assertions.assertTrue(exoticCheckbox.isSelected());
    }

    @И("нажать на кнопку сохранить")
    public void сохранить_изменения () {
        WebElement saveButton = driver.findElement(By.xpath("//button[@id='save']"));
        saveButton.click();
    }

    @И("проверить что добавленный товар отображается в таблице под номером {int}")
    public void проверить_товар (int rowNumber) {
        WebElement rowTitle = driver.findElement(By.xpath(String.format("//tr[%d]/th[@scope='row']", rowNumber)));
        Assertions.assertEquals(Integer.toString(rowNumber), rowTitle.getText(), "Товар не был добавлен.");
    }

    @И("перейти во вкладку \"Песочница\" и выбираю раздел \"Сброс данных\"")
    public void сброс_данных () {
        WebElement btnSandbox = driver.findElement(By.xpath("//a[@class='nav-link dropdown-toggle']"));
        btnSandbox.click();

        WebElement btnReset = driver.findElement(By.xpath("//a[@id='reset']"));
        btnReset.click();
    }

    @И("закрыть страницу")
    public void закрыть_страницу () {
        driver.close();
        driver.quit();
    }

}
