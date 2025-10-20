import io.github.bonigarcia.seljup.SeleniumJupiter;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

@ExtendWith(SeleniumJupiter.class)
class HelloWorldFirefoxSelJupTest {

    @Test
    void test(FirefoxDriver driver) {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        driver.findElement(By.linkText("Slow calculator") ).click();

        assertThat(driver.getCurrentUrl()).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");

        String title = driver.getTitle();
        System.out.print("Hello World!");
        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }

}