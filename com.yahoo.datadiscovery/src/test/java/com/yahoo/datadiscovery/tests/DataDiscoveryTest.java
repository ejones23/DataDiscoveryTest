package com.yahoo.datadiscovery.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

public class DataDiscoveryTest {
  private WebDriver driver;
  private WebElement temp;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private final int MAX_TRIES_STALE_REFERENCE = 5;
  private String hostname = "formationstation.corp.ne1.yahoo.com";

  @Before
  public void setUp() throws Exception {
	//TODO: (learn how to) loop through multiple browsers
	System.setProperty("webdriver.chrome.driver", "/Users/ejones/Desktop/chromedriver");//Optional. If not specified, WebDriver will search your path for chromedriver.
	//driver = new ChromeDriver();//does NOT take full-page screenshots
	driver = new FirefoxDriver();//takes full-page screenshots
	//driver = new SafariDriver();//does NOT take full-page screenshots
    baseUrl = "http://formationstation.corp.ne1.yahoo.com:8000/";
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void testDataDiscovery() throws Exception {	
    driver.get(baseUrl + "data-discovery-ui/");
	click("//a[contains(., 'Browse')]");
	waitFor("id=discoveryBrowser");
	waitForUrl("http://" + hostname + ":8000/data-discovery-ui/#/browse", 10);
	verifyText("//div[@id='discoveryBrowser']//h1", "Browse...");
	verifyText("//div[@id='clusterChooser']//header", "1 Select a cluster:");
	verifyText("//div[@id='clusterChooser']//option[1]", "Please select a cluster");
	verifyText("//div[@id='clusterChooser']//option[2]", "Axonite Red");
	verifyText("//div[@id='clusterChooser']//option[3]", "Kryptonite Red");

	select("//select[contains(., 'Please select a cluster')]", "Axonite Red");
	waitFor("//div[@id='dbChooser']//div[.='Found 10 databases on Axonite Red']");
	waitForUrl("http://" + hostname + ":8000/data-discovery-ui/#/browse/AR/databases", 10);
	verifyText("//div[@id='dbChooser']//header", "2 Choose database:");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[1]", "benzene");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[2]", "cdrome");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[3]", "cdrome2");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[4]", "comment_db");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[5]", "default");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[6]", "dfsload");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[7]", "selinaz");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[8]", "st");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[9]", "storc");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[10]", "thiruvel");
	
	verifyDDTables("AR", "benzene", "hour", "b", "part", "benzene", "parts10k");
	verifyDDTables("AR", "cdrome", "tbl_student_10k", "tbl_student_gpa_10k_orc", "tbl_orc", "tbl_student_gpa_10k_rc", "abf_hourly_orc2" 
			, "abf_hourly_orc_bigint2", "words_text", "words_orc", "test_func", "abf_hourly", "abf_hourly_orc", "abf_hourly_orc_bigint");
	verifyDDTables("AR", "cdrome2", "tbl_orc");
	verifyDDTables("AR", "comment_db");
	verifyDDTables("AR", "default");
	verifyDDTables("AR", "dfsload", "fs_entries");
	verifyDDTables("AR", "selinaz", "benzene");
	verifyDDTables("AR", "st", "st_jobs", "st_job_counters", "st_job_confs", "st_tasks", "st_task_counters", "st_task_attempts"
			, "st_task_attempt_counters", "st_fs_audit", "st_job_summary", "st_fs_namespaces", "st_fs_blocks", "st_fs_entries", "st_simon_reports"
			, "st_gdm_dataset", "st_gdm_data_definition", "st_gdm_data_validation", "st_gdm_data_workflow_strategy", "st_gdm_data_flow"
			, "st_gdm_datasource", "st_gdm_datasource_interfaces", "st_gdm_datasource_interface_cmds", "st_gdm_datasource_resources");
	verifyDDTables("AR", "storc", "fs_audit", "fs_entries", "fs_blocks", "jobs");
	verifyDDTables("AR", "thiruvel", "domain", "tt", "t", "testdomain", "nsjson", "raw_usrs", "raw_devs", "part", "fs_entries");
	
	/*
	//verify second cluster
	select("//select[contains(., 'Please select a cluster')]", "Kryptonite Red");
	waitFor("//div[@id='dbChooser']//div[.='Found 10 databases on Kryptonite Red']");
	waitForUrl("http://" + hostname + ":8000/data-discovery-ui/#/browse/KR/databases", 10);
	verifyText("//div[@id='dbChooser']//header", "2 Choose database:");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[1]", "benzene");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[2]", "cdrome");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[3]", "cdrome2");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[4]", "comment_db");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[5]", "default");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[6]", "dfsload");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[7]", "selinaz");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[8]", "st");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[9]", "storc");
	verifyText("//div[@id='dbChooser']//div[@class='list-group']/a[10]", "thiruvel");
	
	verifyDDTables("KR", "benzene", "hour", "b", "part", "benzene", "parts10k");
	verifyDDTables("KR", "cdrome", "tbl_student_10k", "tbl_student_gpa_10k_orc", "tbl_orc", "tbl_student_gpa_10k_rc", "abf_hourly_orc2" 
			, "abf_hourly_orc_bigint2", "words_text", "words_orc", "test_func", "abf_hourly", "abf_hourly_orc", "abf_hourly_orc_bigint");
	verifyDDTables("KR", "cdrome2", "tbl_orc");
	verifyDDTables("KR", "comment_db");
	verifyDDTables("KR", "default");
	verifyDDTables("KR", "dfsload", "fs_entries");
	verifyDDTables("KR", "selinaz", "benzene");
	verifyDDTables("KR", "st", "st_jobs", "st_job_counters", "st_job_confs", "st_tasks", "st_task_counters", "st_task_attempts"
			, "st_task_attempt_counters", "st_fs_audit", "st_job_summary", "st_fs_namespaces", "st_fs_blocks", "st_fs_entries", "st_simon_reports"
			, "st_gdm_dataset", "st_gdm_data_definition", "st_gdm_data_validation", "st_gdm_data_workflow_strategy", "st_gdm_data_flow"
			, "st_gdm_datasource", "st_gdm_datasource_interfaces", "st_gdm_datasource_interface_cmds", "st_gdm_datasource_resources");
	verifyDDTables("KR", "storc", "fs_audit", "fs_entries", "fs_blocks", "jobs");
	verifyDDTables("KR", "thiruvel", "domain", "tt", "t", "testdomain", "nsjson", "raw_usrs", "raw_devs", "part", "fs_entries");
	 */
	
	//verify one table's info
	select("//select[contains(., 'Please select a cluster')]", "Axonite Red");
	click("//div[@id='dbChooser']//a[.='cdrome']");
	click("//div[@id='tableList']//dt[.='tbl_orc']");
	waitFor("//h1[.='tbl_orc']");
	waitForUrl("http://formationstation.corp.ne1.yahoo.com:8000/data-discovery-ui/#/browse/AR/database/cdrome/table/tbl_orc", 10);
	verifyText("//table[@id='pageHeaderSubInfo']//tr[1]", "IN DATABASE cdrome");
	verifyText("//table[@id='pageHeaderSubInfo']//tr[2]", "ON CLUSTER Axonite Red");
	verifyText("//div[@id='tableInfo']//header", "General Info:");
	verifyText("//div[@id='tableInfo']//dt[.='Owner']/following-sibling::dd", "");
	verifyText("//div[@id='tableInfo']//dt[.='Input Format']/following-sibling::dd", "org.apache.hadoop.hive.ql.io.orc.OrcInputFormat");
	verifyText("//div[@id='tableInfo']//dt[.='Output Format']/following-sibling::dd", "org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat");
	verifyText("//div[@id='tableInfo']//dt[.='SerDe']/following-sibling::dd", "org.apache.hadoop.hive.ql.io.orc.OrcSerde");
	verifyText("//div[@id='tableInfo']//dt[.='Location']/following-sibling::dd", "hdfs://gsbl90916.blue.ygrid.yahoo.com:8020/user/cdrome/hive12.oracle/tbl_orc");
	verifyText("//div[@id='tableInfo']//dt[.='Partitioned']/following-sibling::dd", "No");
	
	verifyText("//div[@id='tableProps']//header", "Properties:");
	verifyText("//div[@id='tableProps']//dt[.='numPartitions']/following-sibling::dd", "0");
	verifyText("//div[@id='tableProps']//dt[.='numFiles']/following-sibling::dd", "1");
	verifyText("//div[@id='tableProps']//dt[.='transient_lastDdlTime']/following-sibling::dd", "1403950674");
	verifyText("//div[@id='tableProps']//dt[.='numRows']/following-sibling::dd", "0");
	verifyText("//div[@id='tableProps']//dt[.='totalSize']/following-sibling::dd", "34990");
	verifyText("//div[@id='tableProps']//dt[.='rawDataSize']/following-sibling::dd", "0");
	 
	verifyText("//div[@id='tableSchema']//header", "Schema:");
	verifyText("//div[@id='tableSchema']//th[1]", "Column");
	verifyText("//div[@id='tableSchema']//th[2]", "Type");
	verifyText("//div[@id='tableSchema']//th[3]", "Description");
	verifyText("//div[@id='tableSchema']//tr[1]/td[1]", "name");
	verifyText("//div[@id='tableSchema']//tr[1]/td[2]", "string");
	verifyText("//div[@id='tableSchema']//tr[1]/td[3]", "from deserializer");
	verifyText("//div[@id='tableSchema']//tr[2]/td[1]", "gpa");
	verifyText("//div[@id='tableSchema']//tr[2]/td[2]", "string");
	verifyText("//div[@id='tableSchema']//tr[2]/td[3]", "from deserializer");
  }

  private void verifyDDTables(String initials, String database, String... tables) throws Exception{
	  String cluster = (initials.equals("AR"))? "Axonite Red" : "Kryptonite Red";
	  click("//div[@id='dbChooser']//a[.='" + database + "']");
	  waitFor("//div[@id='tableChooser']//div[.='Found " + tables.length + " tables on " + database + " on " + cluster + "']");
	  assertTrue(getUrl().equals("http://" + hostname + ":8000/data-discovery-ui/#/browse/" + initials + "/database/" + database + "/tables"));
	  if (tables.length == 0) {
		  verifyText("//div[@id='tableChooser']//header", "3 No tables found");
	  } else {
		  verifyText("//div[@id='tableChooser']//header", "3 Select a table for more details:");
	  }
	  for (int i = 0; i < tables.length; i++) {
		  verifyText("(//div[@id='tableList']//dt)[" + (i + 1) + "]", tables[i]);
	  }
  }
  
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  
  private void click(String identifier) throws Exception {
	  click(identifier, 0);
  }
  
  private void click(String identifier, int numTries) throws Exception{
	  try {
		  get(identifier).click();
	  } catch (StaleElementReferenceException sre) {
		  if(numTries <= MAX_TRIES_STALE_REFERENCE) {
			  Thread.sleep(1000);
			  click(identifier, ++numTries);
		  } else throw new Exception("StaleElementReferenceException was thrown more than " + MAX_TRIES_STALE_REFERENCE + " times.\n");
	  }
  }

  private void select(String identifier, String option) throws Exception{
	  select(identifier, option, 0);
  }
  
  private void select(String identifier, String option, int numTries) throws Exception{
	  try {
		  new Select(get(identifier)).selectByVisibleText(option);
	  } catch (StaleElementReferenceException sre) {
		  if (numTries <= MAX_TRIES_STALE_REFERENCE){
			  Thread.sleep(1000);
			  select(identifier, option, ++numTries);
		  } else throw new Exception("StaleElementReferenceException was thrown more than " + MAX_TRIES_STALE_REFERENCE + " times.\n");
	  }
  }

  private void type(String identifier, String entry) throws Exception{
	  try {
		  (temp = get(identifier)).clear();
		  temp.sendKeys(entry);
	  } catch (StaleElementReferenceException sre) {
		  Thread.sleep(1000);
		  type(identifier, entry);//DANGER! no base case
	  }
  }
  
  private WebElement get(String identifier) throws Exception{
	  String[] parsed = parseIdentifier(identifier);
	  return waitFor(parsed[0], parsed[1]);
  }
  
  private String[] parseIdentifier(String identifier){
	  String[] parsed = new String[2];
	  if (identifier.contains("=") && (identifier.indexOf("=") < 6)){// '< 6' to avoid 'key=value' pairs in XPath filters
		  parsed[0] = identifier.substring(0, identifier.indexOf("="));
		  parsed[1] = identifier.substring(identifier.indexOf("=") + 1);//may be id, css, or name
	  } else {
		  parsed[0] = "xpath";//defaulting to this as the most common case
		  parsed[1] = identifier;
	  }
	  return parsed;
  }
  
  //for readability
  private WebElement waitFor(String identifier) throws Exception {
	  return get(identifier);
  }
  
  private WebElement waitFor(String identifierType, String identifier) throws Exception{
	  for (int second = 0;; second++) {
		  if (second >= 2) fail("timeout");
		  try { 
			  if("xpath".equals(identifierType)) {
				  if (isElementPresent(By.xpath(identifier))) return driver.findElement(By.xpath(identifier));
			  } else if("id".equals(identifierType)) {
				  if (isElementPresent(By.id(identifier))) return driver.findElement(By.id(identifier)); 
			  } else if("css".equals(identifierType)) {
				  if (isElementPresent(By.cssSelector(identifier))) return driver.findElement(By.cssSelector(identifier));
			  } else if("name".equals(identifierType)) {
				  if (isElementPresent(By.name(identifier))) return driver.findElement(By.name(identifier));
			  }
		  } catch (Exception e) {}
		  Thread.sleep(1000);
	  }
  }
  
  private boolean isElementPresent(By by) {
	  try {
		  driver.findElement(by);
		  return true;
	  } catch (NoSuchElementException e) {
		  return false;
	  }
  }
  
  private void verifyText(String identifier, String regex) throws Exception {
	  WebElement element = get(identifier);
	  regex = regex.replaceAll("\\*", "[\\\\s\\\\S]*");//supports Selenium 'globbing' pattern '*'
	  try {
	      assertTrue(element.getText().matches(regex));
	  } catch (Error e) {
	      verificationErrors.append(e.toString() 
	    		  + "\n" 
	    		  + "COMPARING ELEMENT '" + element.getText() + "'\n"
	    		  + "TO REGEX '" + regex + "'\n"
	    		  );
	  }
  }
  
  private void takeScreenshot(String name) throws Exception {
	  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  FileUtils.copyFile(scrFile, new File("./target/surefire-reports/screenshots/" + name + ".jpg"));
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
  
  private void printUrl() {
	  System.out.println(getUrl());
  }
  
  private String getUrl() {
	  return driver.getCurrentUrl();
  }
  
  private void waitForUrl(String url, int seconds) throws InterruptedException {
	  long millis = seconds * 1000;
	  int interval = 100;
	  while (millis > 0) {
		  if (getUrl().equals(url)) break;
		  Thread.sleep(interval);
		  millis -= interval;
	  }
	  assertTrue(getUrl().equals(url));
  }
}

