package com.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("parent")
public class EasyUINode implements Node {
	/**父节点*/
	private Node parent;
	
	/**子节点*/
	private List<Node> children=new ArrayList<>();
	
	/** 代表数据库的path字段，以及easyui tree的节点id */
	protected String id;
	
	/** 代表数据库的caption字段，以及easyui tree的节点文本 */
	protected String text;
	
	/** 代表easyui tree的节点状态  */
	protected String state;
	
	/** 代表easyui tree的复选框的选中状态 */
	protected boolean checked;
	
	/** 代表easyui tree的自定义属性 */
	protected Map<String,Object> attributes=new HashMap<>(4);
	
	/**
	 * 代表节点图标CSS类ID
	 */
	protected String iconCls;
	
	public EasyUINode(){
	}

	@Override
	public int compareTo(Node node) {
		EasyUINode other = (EasyUINode) node;
		
		//简单地使用id比较先后顺序
		return this.id.compareTo(other.id);
	}

	@Override
	public boolean contains(Node node) {
		EasyUINode other=(EasyUINode) node;

		int n1=id.length();
		int n2=other.id.length();

		if(n1>=n2){
			//如果前者的路径深度大于等于后者，则不包含
			return false;
		} else{
			//前者路径深度小于后者，则只需按前者的路径字符串比较是否相等即可
			char[] v1=id.toCharArray();
			char[] v2=other.id.toCharArray();

			for(int i=0;i<n1;i++){
				if(v1[i]!=v2[i]){
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public boolean isLeafNode() {
		return children.size() == 0;
	}

	@Override
	public List<Node> getChildren() {
		return children;
	}

	@Override
	public Node getParent() {
		return parent;
	}

	@Override
	public void setParent(Node node) {
		parent = node;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "EasyUINode [id=" + id + ", text=" + text + "]";
	}

}
