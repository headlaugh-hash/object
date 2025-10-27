package com.headlaugh.model;

public class UserInfo {
	private long id;
	private String userName;
	private String eduBackground;
	private String jobProcess;
	private String rewardProcess;
	private String loginName;
	private String password;
	
	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getEduBackground() {
		return eduBackground;
	}



	public void setEduBackground(String eduBackground) {
		this.eduBackground = eduBackground;
	}



	public String getJobProcess() {
		return jobProcess;
	}



	public void setJobProcess(String jobProcess) {
		this.jobProcess = jobProcess;
	}



	public String getRewardProcess() {
		return rewardProcess;
	}



	public void setRewardProcess(String rewardProcess) {
		this.rewardProcess = rewardProcess;
	}



	public String getLoginName() {
		return loginName;
	}



	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	public UserInfo( String userName, String eduBackground, String jobProcess, String rewardProcess,
			String loginName, String password) {
		super();
		this.userName = userName;
		this.eduBackground = eduBackground;
		this.jobProcess = jobProcess;
		this.rewardProcess = rewardProcess;
		this.loginName = loginName;
		this.password = password;
	}
	public UserInfo(long id, String userName, String eduBackground, String jobProcess, String rewardProcess,
			String loginName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.eduBackground = eduBackground;
		this.jobProcess = jobProcess;
		this.rewardProcess = rewardProcess;
		this.loginName = loginName;
		this.password = password;
	}

	public UserInfo() {
		super();
	}

	@Override
	public String toString() {
		return this.id+"#"+this.userName+"#"+this.eduBackground+"#"+this.jobProcess+"#"+this.rewardProcess;
	}
}
