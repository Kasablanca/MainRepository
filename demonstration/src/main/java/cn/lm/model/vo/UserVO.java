package cn.lm.model.vo;

import cn.lm.model.TUser;

public class UserVO extends TUser {
	
	private String birthdayStr;

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
}
