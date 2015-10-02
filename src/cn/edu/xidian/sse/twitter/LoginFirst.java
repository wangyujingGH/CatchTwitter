package cn.edu.xidian.sse.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * @author wyj
 * @since Twitter4J 2.2.4
 * @date 2015年8月20日
 * @brief 第一次使用，通过consumerKey和consumerSecret获得accesskey和accessSecret（AccessToken）
 *        以后的登陆就不需要这样登陆了
 */
public class LoginFirst {
    /**
     * Usage: java  twitter4j.examples.oauth.GetAccessToken [consumer key] [consumer secret]
     *
     * @param args message
     */
    public static void main(String[] args) {
        File file = new File("src/twitter4j1.properties");
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;
        
        String oauth_consumerKey = null;
        String oauth_consumerSecret = null;
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
                oauth_consumerKey = prop.getProperty("oauth.consumerKey");
                oauth_consumerSecret = prop.getProperty("oauth.consumerSecret");
                System.out.println(oauth_consumerKey);
                System.out.println(oauth_consumerSecret);
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
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignore) {
                }
            }
        }
        
        String oauth_requestToken = null;
        String oauth_requestTokenSecret = null;
        
        try {    
        	
        	//OAuth有两种Token，分别是RequestToken和AccessTonke。
        	//通过OAuth Consumer的key和secret就可以获取RequestTokenKey和RequestTokenSecret：
        	
        	ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setOAuthConsumerKey(oauth_consumerKey);
            configurationBuilder.setOAuthConsumerSecret(oauth_consumerSecret);
            configurationBuilder.setHttpProxyHost("127.0.0.1");
            configurationBuilder.setHttpProxyPort(1080);
            Configuration configuration = configurationBuilder.build();
            Twitter twitter = new TwitterFactory(configuration).getInstance();
            
//            Twitter twitter = new TwitterFactory().getInstance();
//            twitter.setOAuthConsumer(oauth_consumerKey, oauth_consumerSecret);
//            RequestToken requestToken = twitter.getOAuthRequestToken();
//            RequestToken requestToken = twitter.getOAuthRequestToken();
            RequestToken requestToken = twitter.getOAuthRequestToken("http://hintdesk.com");
            System.out.println("Got request token.");
            System.out.println("Request token: " + requestToken.getToken());
            System.out.println("Request token secret: " + requestToken.getTokenSecret());
            oauth_requestToken = requestToken.getToken();
            oauth_requestTokenSecret = requestToken.getTokenSecret();
          
//            //跳转到Twitter OAuth验证页面
//            String authorizationURL = requestToken.getAuthorizationURL();
//            
//            //通过requestTokenKey和requestTokenSecret获得accessToken，包含了accessTokenKey和accessTokenSecret
//            AccessToken accessToken = twitter.getOAuthAccessToken(oauth_requestToken, oauth_requestTokenSecret);
//            //把accessToken给twitter，twitter这个实例就算登陆了，效果等同于twitter = new twitter( "name ", "password ")
//            twitter.setOAuthAccessToken(accessToken);
            
            //有了ConsumerKey和ConsumerScret，还有accessToken和accessTokenSecret。
            //下一次登陆twitter就可以直接登陆了
            /*
            twitter = new Twitter();
            twitter.setOAuthConsumer(ConsumerKey, ConsumerScret);
            AccessToken accessToken = new AccessToken(Utility.getCookie( "accessToken ", request), Utility.getCookie( "accessTokenSecret ", request));
            twitter.setOAuthAccessToken(accessToken);
            twitter.updateStatus( "OAuth登陆成功 ");
            */
            AccessToken accessToken = null;
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                try {
                    Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
                } catch (IOException ignore) {
                } catch (URISyntaxException e) {
                    throw new AssertionError(e);
                }
                System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                String pin = br.readLine();
                try {
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken(requestToken);
                    }
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                }
            }
            System.out.println("Got access token.");
            System.out.println("Access token: " + accessToken.getToken());
            System.out.println("Access token secret: " + accessToken.getTokenSecret());

            try {
                prop.setProperty("oauth.accessToken", accessToken.getToken());
                prop.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
                os = new FileOutputStream(file);
                prop.store(os, "twitter4j1.properties");
                os.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(-1);
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException ignore) {
                    }
                }
            }
            System.out.println("Successfully stored access token to " + file.getAbsolutePath() + ".");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get accessToken: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            System.exit(-1);
        }
    }

}


       

