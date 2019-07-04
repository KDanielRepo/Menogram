import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Setter
@Getter
public class Hearth {
    String location;
    String x;
    int pozX;
    String y;
    int pozY;
    int enmX = 0;
    int enmY = 0;
    int[][] mapa = new int[96][64];
    Robot robot = new Robot();

    public Hearth() throws AWTException {
    }


    public void test() throws Exception {


        System.setProperty("webdriver.gecko.driver", "C:\\Users\\NigaKolczan\\Desktop\\geckodriver.exe");
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
        //System.out.println(webDriver.findElement(By.xpath("//tip[contains(@span, '2 lvl')]")).getAttribute("innerHTML"));
        System.out.println(webDriver.findElement(By.id("lagmeter")).getText());


        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        Thread.sleep(200);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        Thread.sleep(200);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C);
        Thread.sleep(200);

        List<WebElement> elements = webDriver.findElements(By.xpath("//div[@class='npc']"));
        List<Integer> enemyPositionsX = new ArrayList<>();
        List<Integer> enemyPositionsY = new ArrayList<>();
        List<Integer[]> integers = new ArrayList<>();
        for (WebElement element : elements) {
            //System.out.println("Styl: " + element.getAttribute("style"));
            //System.out.println("bez: " + element.getCssValue("left"));
            String enemyX = element.getCssValue("left").replaceAll("\\D+", "");
            //System.out.println(element.getAttribute("Tip"));
            String enemyY = element.getCssValue("top").replaceAll("\\D+", "");
            enmX = Integer.parseInt(enemyX) / 32;
            enmY = Integer.parseInt(enemyY) / 32;
            try {
                String testing = element.getAttribute("Tip").replaceAll("\\D+", "");
                if (Integer.parseInt(testing) > 3 & Integer.parseInt(testing) < 6) {
                    enemyPositionsX.add(enmX);
                    enemyPositionsY.add(enmY);
                }
            } catch (NullPointerException | NumberFormatException e) {
                //System.out.println("err");
            }
        }
        for (int i = 0; i < enemyPositionsX.size(); i++) {
            //System.out.println(enemyPositionsX.get(i) + " " + enemyPositionsY.get(i));
        }
        location = webDriver.findElement(By.id("botloc")).getText();
        for(int i = 0; i < location.length();i++){
            if(location.charAt(i)==44 | i>1){
                y+=location.charAt(i);
            }else {
                x+=location.charAt(i);
            }
        }
        x = x.replaceAll("\\D+", "");
        pozX = Integer.parseInt(x) * 32;
        y = y.replaceAll("\\D+", "");
        pozY = Integer.parseInt(y) * 32;
        System.out.println(Integer.parseInt(x)+" "+Integer.parseInt(y)+" oraz: "+enemyPositionsX.get(0)+" "+enemyPositionsY.get(0));
        while (enemyPositionsX.size()>0) {
            Actions actions = new Actions(webDriver);
            PathFinder pathFinder = new PathFinder(Integer.parseInt(x), Integer.parseInt(y), enemyPositionsX.get(0), enemyPositionsY.get(0));
            for(int i = pathFinder.getMovesX().size()-1; i>0;i--){
                /*if (Math.abs(pozX - pathFinder.getMovesX().get(i)) <= 8 & Math.abs(pozY - pathFinder.getMovesY().get(i)) <= 8) {
                    actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(0 + (pathFinder.getMovesX().get(i) * 32), 0 + (pathFinder.getMovesY().get(i) * 32)).click().perform();
                    Thread.sleep(100);
                    pozX = pathFinder.getMovesX().get(i);
                    pozY = pathFinder.getMovesY().get(i);
                }*/
                if(pozX-pathFinder.getMovesX().get(i)<0){
                    robot.keyPress(KeyEvent.VK_D);
                    Thread.sleep(200);
                    robot.keyRelease(KeyEvent.VK_D);

                }else if(pozX-pathFinder.getMovesX().get(i)>0){
                    robot.keyPress(KeyEvent.VK_A);
                    Thread.sleep(200);
                    robot.keyRelease(KeyEvent.VK_A);

                }
                if(pozY-pathFinder.getMovesY().get(i)<0){
                    robot.keyPress(KeyEvent.VK_S);
                    Thread.sleep(200);
                    robot.keyRelease(KeyEvent.VK_S);

                }else if(pozY-pathFinder.getMovesY().get(i)>0){
                    robot.keyPress(KeyEvent.VK_W);
                    Thread.sleep(200);
                    robot.keyRelease(KeyEvent.VK_W);

                }
                if(Math.abs(pozX-pathFinder.getMovesX().get(i))==1 && Math.abs(pozY-pathFinder.getMovesY().get(i))==1){
                    System.out.println("AN GARDE");
                    robot.keyPress(KeyEvent.VK_E);
                    Thread.sleep(200);
                    robot.keyRelease(KeyEvent.VK_E);
                    Thread.sleep(500);
                    robot.keyPress(KeyEvent.VK_Z);
                    Thread.sleep(200);
                    robot.keyRelease(KeyEvent.VK_Z);
                }
                pathFinder.getMovesX().remove(i);
                pathFinder.getMovesY().remove(i);
            }
            location = webDriver.findElement(By.id("botloc")).getText();
            for(int i = 0; i < location.length();i++){
                if(location.charAt(i)==44 | i>1){
                    y+=location.charAt(i);
                }else {
                    x+=location.charAt(i);
                }
            }
            x = x.replaceAll("\\D+", "");
            pozX = Integer.parseInt(x) * 32;
            y = y.replaceAll("\\D+", "");
            pozY = Integer.parseInt(y) * 32;
            enemyPositionsX.remove(0);
            enemyPositionsY.remove(0);
        }
    }

}









    /*public void checkBlock () throws InterruptedException {
            location = webDriver.findElement(By.id("botloc")).getText();
            String tempX = location.substring(0, 1);
            int tempPozX = Integer.parseInt(x) * 32;
            String tempY = location.substring(3, 4);
            int tempPozY = Integer.parseInt(y) * 48;
            if (pozX == tempPozX) {
                robot.keyPress(KeyEvent.VK_D);
                Thread.sleep(200);
                robot.keyRelease(KeyEvent.VK_D);
            }
            if (pozY == tempPozY) {

            }
        }*/
//Tutaj odpowiedz na wiadomosc, zostaw to!
/* while (true){
            try {
                System.out.println(webDriver.findElement(By.xpath("//div[@id='chat']/div[@id='chatTxtContainer']/div[@id='chattxt']/div[@class='priv2']/span[@class='chatmsg']")).getAttribute("innerHTML"));
                Thread.sleep(2000);
                if(webDriver.findElement(By.xpath("//div[@id='chat']/div[@id='chatTxtContainer']/div[@id='chattxt']/div[@class='priv2']/span[@class='chatmsg']")).getAttribute("innerHTML") != null){
                    webDriver.findElement(By.id("bottombar")).click();
                    //webDriver.findElement(By.id("bottombar")).sendKeys("Pondrawiam, Marcin Gortat");
                    //Thread.sleep(1000);
                    //webDriver.findElement(By.id("bottxt")).click();
                    //webDriver.findElement(By.id("bottxt")).sendKeys("Pondrawiam, Marcin Gortat");
                    //Thread.sleep(1000);
                    //webDriver.findElement(By.id("inpchat")).click();
                    webDriver.findElement(By.id("inpchat")).sendKeys("Pondrawiam, Marcin Gortat");
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                }
            }catch (Exception e){
                e.printStackTrace();
                Thread.sleep(2000);
            }
        }*/


//chat>chatTxtContainer>chattxt>[]>abs>span class"chatmsg">msg</span
//gdy wiadomosc priv napisz pondro


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
    /*public String extract(String a){
        StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        for(int i = 0; i< a.length();i++){
            char c = a.charAt(i);
            if()
            if(Character.isDigit(c)){
                builder.append(c);
            }
        }
    }*/


//centerbox>botloc tip="nazwa mapy">x,y
//centerbox>battle>
//centerbox>npcNumerID

/*

} else {

        if (pozX < enmX) {
        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_D);
        } else if (pozX > enmX) {
        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_A);
        }
        if (pozY < enmY) {
        robot.keyPress(KeyEvent.VK_S);
        Thread.sleep(50);
        robot.keyRelease(KeyEvent.VK_S);
        } else if (pozY > enmY) {
        robot.keyPress(KeyEvent.VK_W);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_W);
        }
        }
        if (pozX < enmX & pozY < enmY) {
        if (enmX - pozX <= 8 & enmY - pozY <= 5) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(512 - (enmX * 32), 0 + (enmY * 48)).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_D);

        robot.keyPress(KeyEvent.VK_S);
        Thread.sleep(50);
        robot.keyRelease(KeyEvent.VK_S);
        }
        }
        if (pozX < enmX & pozY > enmY) {
        if (enmX - pozX <= 8 & pozY - enmY <= 5) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(512 - (enmX * 32), 512 - (enmY * 48)).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_W);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_W);

        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_D);
        }
        }
        if (pozX > enmX & pozY < enmY) {
        if (pozX - enmX <= 8 & enmY - pozY <= 5) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(0 + (enmX * 32), 0 + (enmY * 48)).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_S);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_S);

        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_A);


        }
        }
        if (pozX > enmX & pozY > enmY) {
        if (pozX - enmX <= 8 & pozY - enmY <= 5) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(0 + (enmX * 32), 512 - (enmY * 48)).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_W);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_W);

        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_A);

        }
        }
        if (pozX == enmX & pozY < enmY) {
        if (enmY - pozY <= 5) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(0, 0 + (enmY * 48)).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_S);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_S);

        }
        }
        if (pozX == enmX & pozY > enmY) {
        if (pozY - enmY <= 5) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(0, 512 - (enmY * 48)).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_W);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_W);

        }
        }
        if (pozX < enmX & pozY == enmY) {
        if (enmX - pozX <= 8) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(512 - (enmX * 32), 0).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_D);

        }
        }
        if (pozX > enmX & pozY == enmY) {
        if (pozX - enmX <= 8) {
        actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(0 + (enmX * 32), 0).click().perform();
        } else {
        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(150);
        robot.keyRelease(KeyEvent.VK_A);

        }
        }*/
/*
            if(pozX < enmX){
                if(enmX - pozX <=8 & enmY - pozY <=5){
                    actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(898-(enmX*32),562-(enmY*48)).click().perform();
                }
                if(pozX-enmX <= 8 & pozY - enmY <=5){
                    actions.moveToElement(webDriver.findElement(By.id("base"))).moveByOffset(386+(enmX*32),50+(enmY*48)).click().perform();
                }
                if(enmX-pozX <= 8 & pozY - enmY <=5){

                }
                if(pozX-enmX <= 8 & enmY - pozY  <=5){

                }*/
