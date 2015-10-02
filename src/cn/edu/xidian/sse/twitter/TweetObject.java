/**
 * 
 */
package cn.edu.xidian.sse.twitter;

import java.util.Date;

/**
 * @class TweetObject.java
 * @brief 
 * @author 王宇净
 * @version 
 * @date 2015年8月19日
 */
public class TweetObject {
	
	private Date createAt ;
	
	private String user ;
    
	private Long userId ;
    
	private String source ;
    
	private String text ;
    
	private String toUser ;
    
	private Long toUserId ;

	/**
	 * @return the createAt
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * @param createAt the createAt to set
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the toUser
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * @param toUser the toUser to set
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	/**
	 * @return the toUserId
	 */
	public Long getToUserId() {
		return toUserId;
	}

	/**
	 * @param toUserId the toUserId to set
	 */
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	
	
	public void display() {
		System.out.println("**************");
		System.out.println(this.user + "\t" + this.userId + "\t" + this.text + "\t" + this.source
				+"\t" + this.toUser + "\t" + this.createAt);
	}
}
