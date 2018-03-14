package cn.lm.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("parent")
public abstract class EasyUINode implements Node {
	protected Node parent;
	protected List<Node> children=new ArrayList<>();
	
	protected String id;
	protected String text;
	
	public EasyUINode(){
	}
	
	public EasyUINode(String id, String text) {
		super();
		this.id = id;
		this.text = text;
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

}
