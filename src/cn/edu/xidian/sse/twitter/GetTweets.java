/**
 * 
 */
package cn.edu.xidian.sse.twitter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @class GetTweets.java
 * @brief 
 * @author 王宇净
 * @version 
 * @date 2015年8月19日
 */
public class GetTweets {
	
	private Twitter twitter = null;
	
	public void login() {
		new Config();
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true);
	    cb.setOAuthConsumerKey(Config.oauth_consumerKey);
	    cb.setOAuthConsumerSecret(Config.oauth_consumerSecret);
        cb.setOAuthAccessToken(Config.oauth_accessToken);
        cb.setOAuthAccessTokenSecret(Config.oauth_accessTokenSecret);
        cb.setHttpProxyHost("127.0.0.1");
        cb.setHttpProxyPort(1080);
        //这里当得到需要的数据的时候，其实是一个json格式的数据
	    cb.setJSONStoreEnabled(true);
	    
		twitter = new TwitterFactory(cb.build()).getInstance();

	}
	
	public void getTweets() {
		Query query = new Query();
		query.setResultType(Query.MIXED);
		query.setSinceId(new Long(0));
		query.setMaxId(new Long(647339105303445504L));
		
		//设置开始和结束时间
		Calendar cal = Calendar.getInstance();
		//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		int n = -1;
		cal.add(Calendar.DATE, n*7);
		//想周几，这里就传几Calendar.MONDAY（tuesday...）
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		String start = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date date = new Date();  
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");//24小时制  
        String end = sdformat.format(date);   
		
        System.out.println(start + "\t" + end);
        
		query.setSince(start);
		query.setUntil(end);
		
		query.setCount(150);
		query.setQuery("we");	//查询关键字
	
		QueryResult result = null;
		try {
			result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			System.out.println(tweets.size());
			TweetObject tweetObj = null;
            for (Status status : tweets) {
            	tweetObj = new TweetObject();
                System.out.println("@" + status.getUser().getName() + " - " + status.getText());
                tweetObj.setCreateAt(status.getCreatedAt());
                tweetObj.setUser(status.getUser().getName());
                tweetObj.setUserId(status.getUser().getId());
                tweetObj.setSource(status.getSource());
                tweetObj.setText(status.getText());
                tweetObj.setToUser(status.getInReplyToScreenName());
                tweetObj.display();   
            }
            System.exit(0);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		GetTweets getTweets = new GetTweets();
		getTweets.login();
		getTweets.getTweets();
	}
}
