package cn.edu.xidian.sse.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @class Login.java
 * @brief 
 * @author 王宇净
 * @version 
 * @date 2015年8月20日
 */
public class Config {
	public static String oauth_consumerKey = null;
	public static String oauth_consumerSecret = null;
	public static String oauth_accessToken = null;
	public static String oauth_accessTokenSecret = null;
    
    static{
    	File file = new File("src/twitter4j1.properties");
        Properties prop = new Properties();
        InputStream is = null;
        
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
                oauth_consumerKey = prop.getProperty("oauth.consumerKey");
                oauth_consumerSecret = prop.getProperty("oauth.consumerSecret");
                oauth_accessToken = prop.getProperty("oauth.accessToken");
                oauth_accessTokenSecret = prop.getProperty("oauth.accessTokenSecret");
            }     
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
        }
        
    }
}
