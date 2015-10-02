/**
 * 
 */
package cn.edu.xidian.sse.twitter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

/**
 * @class GetTweetsStream.java
 * @brief 
 * @author 王宇净
 * @version 
 * @date 2015年8月20日
 */
public class GetTweetsStream {
	
	//Twitter 的这个接口能够让第三方近乎实时地获取公开数据的各种子集：
	
	public static void main(String[] args) throws TwitterException {
		new Config();

        StatusListener listener = new StatusListener() {
        	
        	 // 当有目标用户（根据follow中的userId过滤）发布推文时，调用该方法
            public void onStatus(Status status) {
                System.out.println("***************************");
                
                //将数据转换成json的String格式
                String str = DataObjectFactory.getRawJSON(status); 
                
                //将数据写入文件
              try {
              	File file = new File("D:\\TwitterData.txt");
                  if(!file.exists()) {
                  	file.createNewFile();
                  }
					FileWriter fw = new FileWriter(file, true);
//					System.out.println(str);
					fw.write(str);
					fw.write("\r\n");
					fw.flush();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
               
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				System.out.println("Got stall warning:" + warning);
			}
        };

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
	    
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        twitterStream.addListener(listener);   //// 这里加入了listener用于处理侦听推文
        
        //没有关键字
        //一旦一条新 tweet 发布，正文提及了 track 数组中的某一个词，这条消息就会被主动推送过来
        String[] trackArray = new String[]{"we"};
        long[] follow = new long[]{};   // userId 是在Twitter App页面中的用户ID
        
//        count Indicates the number of previous statuses to stream before transitioning to the live stream.
//        follow Specifies the users, by ID, to receive public tweets from.
//        track Specifies keywords to track.
        FilterQuery filter = new FilterQuery();
        filter.track(trackArray);
        filter.follow(follow);
     // filter() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(filter);
        
//        //推文
//        Status status = twitter.updateStatus(message);
//        System.out.println("Successfully updated the status to ["+ status.getText() + "].");
        
  
    }
}
