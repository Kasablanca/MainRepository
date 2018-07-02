package com.min.someapp.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.min.someapp.web.DeleteGroup;
import com.min.someapp.web.InsertGroup;
import com.min.someapp.web.UpdateGroup;

public class Authority implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message="权限ID不能为空",groups= {UpdateGroup.class,DeleteGroup.class})
    private Integer id;

    private Integer addAccId;

    private Date addTime;

    private Integer updAccId;

    private Date updTime;

    private Integer parentId;
    
    @NotNull(message="版本号不能为空",groups= {UpdateGroup.class,DeleteGroup.class})
    private Integer verNo;

    @NotEmpty(message="权限名不能为空",groups= {InsertGroup.class,UpdateGroup.class})
    @Length(min=2,max=50,message="权限名字长度必须在2-50位之间",groups= {InsertGroup.class,UpdateGroup.class})
    private String name;

    @NotEmpty(message="权限路径不能为空",groups= {InsertGroup.class,UpdateGroup.class})
    @Length(min=2,max=100,message="路径长度必须在2-100位之间",groups= {InsertGroup.class,UpdateGroup.class})
    private String url;

    @NotNull(message="权限类型不能为空",groups= {InsertGroup.class,UpdateGroup.class})
    private Byte type;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddAccId() {
        return addAccId;
    }

    public void setAddAccId(Integer addAccId) {
        this.addAccId = addAccId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdAccId() {
        return updAccId;
    }

    public void setUpdAccId(Integer updAccId) {
        this.updAccId = updAccId;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getVerNo() {
        return verNo;
    }

    public void setVerNo(Integer verNo) {
        this.verNo = verNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
    
    /**
     * 权限类型
     * @author MIN.LEE
     *
     */
    public static enum Type {
    	MENU((byte)0),
    	PAGE((byte)1),
    	FUNCTION((byte)2),
    	ROOT((byte)3);
    	
    	public final byte value;
    	
    	private Type(byte value) {
    		this.value = value;
    	}
    	
    	/**
    	 * 判断是否可以为父节点
    	 * @param type 节点类型
    	 * @return 是否可以为父节点
    	 */
    	public static boolean canBeParent(byte type) {
    		if(type == 0 || type == 1 || type == 3) return true;
    		return false;
    	}
    }
}