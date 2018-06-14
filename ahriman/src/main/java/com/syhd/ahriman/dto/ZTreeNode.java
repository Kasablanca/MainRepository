package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"checked","open","icon","iconClose","iconOpen","parentId"})
public class ZTreeNode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<ZTreeNode> children = new LinkedList<>();
	private Boolean checked;
	private Boolean open;
	private String icon;
	private String iconClose;
	private String iconOpen;
	
	private Integer id;
	private Integer parentId;
	private Integer verNo;
	private Byte type;
	private String path;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ZTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<ZTreeNode> children) {
		this.children = children;
	}
	public Boolean isChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconClose() {
		return iconClose;
	}
	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}
	public String getIconOpen() {
		return iconOpen;
	}
	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
