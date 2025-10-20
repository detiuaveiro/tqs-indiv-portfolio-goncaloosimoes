import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static java.lang.invoke.MethodHandles.lookup;
import io.github.bonigarcia.wdm.WebDriverManager;

class HelloWorldChromeJupiterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @Test
    void test() {
        // Exercise
        String sutUrl = "https://play.tetris.com/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Play Tetris | Tetris");
    }

    @Test
    void calc() {
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);

        // find the slow calculator page (doesn't click yet)
        driver.findElement(By.linkText("Slow calculator"));

        // click the link to go to the slow calculator page
        driver.findElement(By.linkText("Slow calculator")).click();

        assertThat(driver.getCurrentUrl()).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}