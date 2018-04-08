package cn.lm.model;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class User {
	
	public User() {
	}
	
	public User(String username,Integer age) {
		this.username = username;
		this.age = age;
	}
	
    @Override
	public String toString() {
		return "User [username=" + username + ", age=" + age + "]";
	}

	private Integer userid;

    private String rid;

    private Integer verno;

    private String addacc;

    private String addtime;

    private String updacc;

    private String updtime;

    private Integer companyid;

    private String phone;

    private String password;

    private String username;

    private Byte nearremind;

    private Byte applystat;

    private Byte gender;

    private Integer age;

    private String address;

    private String email;

    private String headpic;

    private String introduction;

    private Byte useraddtype;

    private String lastlogintime;

    private String appversion;

    private String deviceversion;

    private Byte devicetype;

    private Byte userstat;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public Integer getVerno() {
        return verno;
    }

    public void setVerno(Integer verno) {
        this.verno = verno;
    }

    public String getAddacc() {
        return addacc;
    }

    public void setAddacc(String addacc) {
        this.addacc = addacc == null ? null : addacc.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getUpdacc() {
        return updacc;
    }

    public void setUpdacc(String updacc) {
        this.updacc = updacc == null ? null : updacc.trim();
    }

    public String getUpdtime() {
        return updtime;
    }

    public void setUpdtime(String updtime) {
        this.updtime = updtime == null ? null : updtime.trim();
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Byte getNearremind() {
        return nearremind;
    }

    public void setNearremind(Byte nearremind) {
        this.nearremind = nearremind;
    }

    public Byte getApplystat() {
        return applystat;
    }

    public void setApplystat(Byte applystat) {
        this.applystat = applystat;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic == null ? null : headpic.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Byte getUseraddtype() {
        return useraddtype;
    }

    public void setUseraddtype(Byte useraddtype) {
        this.useraddtype = useraddtype;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime == null ? null : lastlogintime.trim();
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion == null ? null : appversion.trim();
    }

    public String getDeviceversion() {
        return deviceversion;
    }

    public void setDeviceversion(String deviceversion) {
        this.deviceversion = deviceversion == null ? null : deviceversion.trim();
    }

    public Byte getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(Byte devicetype) {
        this.devicetype = devicetype;
    }

    public Byte getUserstat() {
        return userstat;
    }

    public void setUserstat(Byte userstat) {
        this.userstat = userstat;
    }
}