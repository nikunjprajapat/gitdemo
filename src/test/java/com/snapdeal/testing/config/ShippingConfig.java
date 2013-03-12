package com.snapdeal.testing.config;

import java.io.FileInputStream;
import java.util.Properties;



public class ShippingConfig {

	private static  ShippingConfig instance = null;
	private Properties globalConfigs;
	private Properties domainConfigs;
	private String domain = "debug";
	public static String ext=".cfg";

	
	private static void  loadConfigFile(String configLocation) throws Exception {
		
		String globalConfigFile=configLocation+"Regression.cfg";
		instance = new ShippingConfig();
		instance.globalConfigs = new Properties();
		instance.globalConfigs.load(new FileInputStream(globalConfigFile));
		if(instance.globalConfigs.get("domain")!=null)
			instance.domain=instance.globalConfigs.get("domain").toString();
		String domainConfigFile=configLocation+instance.domain+ext;
		instance.domainConfigs = new Properties();
		instance.domainConfigs.load(new FileInputStream(domainConfigFile));
	System.out.println("globalconfigfile and envconfig file is "+globalConfigFile+domainConfigFile);
	}

	

	public static ShippingConfig getInstance()throws Exception {


		return getInstance(getConfigLocation());	
	}

	public static ShippingConfig getInstance(String configLocation)throws Exception {
		if(instance==null)
			loadConfigFile(configLocation);
		return instance;
	}


	public   String getConfig(String key) throws Exception 
	{

		if(instance==null)
			throw new RuntimeException("Initialize AppConfig");
		Object value=null;
		
		value=domainConfigs.get(key);
		if (value==null) {
			value=globalConfigs.get(key);
		}
		if (value!=null)
			return value.toString();
		else
			return null;
	}
	public String getDomain()
	{
		return domain;
	}

	public static String getConfigLocation() {
		String path = System.getProperty("user.dir");
		path=path+"/src/test/java/com/snapdeal/testing/config/";
		return path;


	}
	public static void main(String[] args) throws Exception {
		ShippingConfig appConfig = ShippingConfig.getInstance();
		System.out.println("appConfig.getConfig(key)===="+appConfig.getConfig("key"));
		


	}	

}

