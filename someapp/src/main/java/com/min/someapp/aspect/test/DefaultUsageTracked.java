package com.min.someapp.aspect.test;

public class DefaultUsageTracked implements UsageTracked {
	private long userCount;

	@Override
	public void incrementUseCount() {
		setUserCount(getUserCount() + 1);
		System.out.println(userCount);
	}

	public long getUserCount() {
		return userCount;
	}

	public void setUserCount(long userCount) {
		this.userCount = userCount;
	}
	
}
