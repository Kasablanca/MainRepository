package cn.lm.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("parent")
public class MenuNode{
	public static final String PATH_DELIMITER = "/";
	
	private MenuNode parent;
	private List<MenuNode> children=new ArrayList<>();
	
	private Integer id;
	private Integer parentId;
	private String text;
	private String path;
	
	public MenuNode getParent() {
		return parent;
	}
	public void setParent(MenuNode parent) {
		this.parent = parent;
	}
	public List<MenuNode> getChildren() {
		return children;
	}
	public void setChildren(List<MenuNode> children) {
		this.children = children;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "MenuNode [id=" + id + ", text=" + text + ", path=" + path + "]";
	}
}
