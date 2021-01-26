package io.saul.citizenshiptracker.service;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;
import java.util.Properties;

@Service
public class ECASTrackerService {

    private Properties formInputs;

    public ECASTrackerService(){
        formInputs = new Properties();
        
        setup(formInputs);
        
        File chromeDriver = new File("chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
    }

    private void setup(Properties formInputs) {
        InputStream inputStream = null;
        try {
            File propFile = new File("forms.properties");

            if(!propFile.exists()){
                //noinspection ResultOfMethodCallIgnored
                propFile.createNewFile();
                System.err.println("Prop file did not exist. It was created for you. Please fill it out accordingly.");
                System.exit(1);
            }

            inputStream = new FileInputStream(propFile);

            formInputs.load(inputStream);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignore) {}
            }
        }
    }

    public String fire() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://services3.cic.gc.ca/ecas/security.do");
            driver.findElement(By.id("agree")).click();
            driver.findElement(By.name("_target1")).click();
            driver.findElement(By.id("idTypeLabel")).sendKeys("Client ");
            driver.findElement(By.name("identifier")).sendKeys(getProperty("ecas.doesExist.uci"));
            driver.findElement(By.name("surname")).sendKeys(getProperty("ecas.doesExist.familyName"));
            driver.findElement(By.name("dateOfBirth")).sendKeys(getProperty("ecas.doesExist.yearOfBirth"));
            driver.findElement(By.name("dateOfBirth")).sendKeys(Keys.TAB);
            driver.findElement(By.name("dateOfBirth")).sendKeys(getProperty("ecas.doesExist.monthDayOfBirth"));
            driver.findElement(By.name("countryOfBirth")).sendKeys(getProperty("ecas.doesExist.countryOfBirth"));
            driver.findElement(By.name("_submit")).click();
            String currentUrl = driver.getCurrentUrl();
            if(currentUrl.equalsIgnoreCase("https://services3.cic.gc.ca/ecas/dataaccessexception.do"))
                return null;
            else {
                return currentUrl;
            }
        } finally {
            driver.quit();
        }
    }

    private String getProperty(String field) {
        String value = formInputs.getProperty(field);
        if(value == null){
            System.err.println(MessageFormat.format("Warning! Mis-configured form.properties file is missing field: {0}. Please update the properties file accordingly.", field));
            System.exit(1);
        }
        return value;
    }
}
