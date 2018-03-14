package cn.lm.utils.tree;

import java.util.List;

public interface Node {
	int compareTo(Node node);
	
	boolean contains(Node node);
	
	boolean isLeafNode();
	
	List<Node> getChildren();
	
	Node getParent();
	
	void setParent(Node node);
}
