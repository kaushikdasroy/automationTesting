package com.robust.selenium.configs;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robust.common.utils.PropertiesReader;

public class Browser {

	private static WebDriver webDriver;
	static Logger log = LoggerFactory.getLogger(Browser.class);
	static String launchMode = System.getProperty("browser.launchmode",
			PropertiesReader.getValue("browser.launchmode"));

	public static WebDriver driver() {
		log.info("launching browser .. ");

		if (webDriver == null) {
			log.info("webdriver null .. ");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			log.info("webdriver null 1.. ");
			System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
			log.info("webdriver null 2..... ");
			try {
				//log.info("Going to try to get webdriver .. ");
				webDriver = "headless".equalsIgnoreCase(launchMode) ? new PhantomJSDriver(getDesiredCapabilities())
						: "selenium-grid".equalsIgnoreCase(launchMode)
								? new RemoteWebDriver(new URL("localhost:4444"), capabilities)
								: new ChromeDriver(getChromeOptions());
				//log.info("Got webdriver .. ");				
			//} catch (MalformedURLException e) {
			} catch (Exception e) {					
				log.info("exception trying webdriver .. ");
				e.printStackTrace();
			}
		}
		log.info("returning webdriver .. " + webDriver );
		return webDriver;

	}

	private static ChromeOptions getChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		if ("headless-chrome".equalsIgnoreCase(launchMode)) {
			options.addArguments("--disable-gpu", "--incognito", "--headless");
		}
		return options;
	}

	public static void setDriverNull() {
		webDriver = null;
	}

	/**
	 * set capabilities for headless browser
	 * 
	 * @return
	 */
	public static DesiredCapabilities getDesiredCapabilities() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setJavascriptEnabled(true);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./Drivers/phantomjs.exe");
		return caps;
	}

}
