import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Hearth {
    public void test()throws Exception{
        System.setProperty("webdriver.gecko.driver","C:\\Users\\NigaKolczan\\Desktop\\geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.navigate().to("https://www.margonem.pl");
        //List<WebElement> elements = ((FirefoxDriver) webDriver).findElement(By.id("hero"));
        webDriver.findElement(By.className("button-container")).click();
        webDriver.findElement(By.className("menu-login")).click();
        webDriver.findElement(By.id("popup-login-input")).sendKeys("mujStary12");
        webDriver.findElement(By.id("popup-login-password")).sendKeys("Testing12");
        webDriver.findElement(By.className("btn-pink2")).click();
        webDriver.findElement(By.className("enter-game")).click();
        Thread.sleep(1500);
        System.out.println(webDriver.findElement(By.id("botloc")).getText());
        System.out.println(webDriver.findElement(By.xpath("//tip[contains(@span, '2 lvl')]")).getAttribute("innerHTML"));
        System.out.println(webDriver.findElement(By.id("lagmeter")).getText());
        List<WebElement> elements = webDriver.findElements(By.className("npc"));
        for (WebElement element : elements){
            System.out.println(element);
        }
        /*for(WebElement element : elements){
            System.out.println(element);
        }*/
        //System.out.println(webDriver.getPageSource());
        /* String search;
        WebClient client = new WebClient();
        //client.getOptions().setJavaScriptEnabled(false);
        try {
            String searchUrl = "http://margonem.pl";
            HtmlPage page = client.getPage(searchUrl);
            List<HtmlDivision> divs = page.getByXPath("//div");

            for(HtmlDivision div : divs){
                System.out.println(div);
            }
            //HtmlAnchor anchor = page.getAnchorByName("");
        }catch (Exception e){
            e.printStackTrace();
        }*/
        /*URL url = new URL("https://weii.tu.koszalin.pl");
        URLConnection con = url.openConnection();
        InputStream inputStream = con.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }*/
    }
    public void alive(){

    }

}
//centerbox>botloc tip="nazwa mapy">x,y
//centerbox>battle>
//centerbox>npcNumerID