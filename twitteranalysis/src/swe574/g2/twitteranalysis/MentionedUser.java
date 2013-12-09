package swe574.g2.twitteranalysis;

import twitter4j.UserMentionEntity;

public class MentionedUser extends TwitterUser {

	private int start;
	private int end;
	private String text;
	
	public MentionedUser() {
		// TODO Auto-generated constructor stub
	}
	
	public MentionedUser(UserMentionEntity mentionEntity) {
		super.setName( mentionEntity.getName() );
		super.setUsername( mentionEntity.getScreenName() );
		this.start = mentionEntity.getStart();
		this.end = mentionEntity.getEnd();
		this.text = mentionEntity.getText();	
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
