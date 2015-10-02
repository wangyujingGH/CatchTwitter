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
 * @author ���
 * @version 
 * @date 2015��8��20��
 */
public class GetTweetsStream {
	
	//Twitter ������ӿ��ܹ��õ���������ʵʱ�ػ�ȡ�������ݵĸ����Ӽ���
	
	public static void main(String[] args) throws TwitterException {
		new Config();

        StatusListener listener = new StatusListener() {
        	
        	 // ����Ŀ���û�������follow�е�userId���ˣ���������ʱ�����ø÷���
            public void onStatus(Status status) {
                System.out.println("***************************");
                
                //������ת����json��String��ʽ
                String str = DataObjectFactory.getRawJSON(status); 
                
                //������д���ļ�
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
        
        //���ﵱ�õ���Ҫ�����ݵ�ʱ����ʵ��һ��json��ʽ������
	    cb.setJSONStoreEnabled(true);
	    
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        twitterStream.addListener(listener);   //// ���������listener���ڴ�����������
        
        //û�йؼ���
        //һ��һ���� tweet �����������ἰ�� track �����е�ĳһ���ʣ�������Ϣ�ͻᱻ�������͹���
        String[] trackArray = new String[]{"we"};
        long[] follow = new long[]{};   // userId ����Twitter Appҳ���е��û�ID
        
//        count Indicates the number of previous statuses to stream before transitioning to the live stream.
//        follow Specifies the users, by ID, to receive public tweets from.
//        track Specifies keywords to track.
        FilterQuery filter = new FilterQuery();
        filter.track(trackArray);
        filter.follow(follow);
     // filter() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(filter);
        
//        //����
//        Status status = twitter.updateStatus(message);
//        System.out.println("Successfully updated the status to ["+ status.getText() + "].");
        
  
    }
}
