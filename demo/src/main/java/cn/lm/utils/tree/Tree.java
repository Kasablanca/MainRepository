package cn.lm.utils.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Tree<T extends Node> {
	private List<T> rootNodes=new LinkedList<T>();
	private int size;
	
	public Tree(List<T> nodes){
		buildTree(nodes);
	}
	
	protected void buildTree(List<T> nodes){
		Collections.sort(nodes, new Comparator<T>() {
			@Override
			public int compare(T n1, T n2) {
				return n1.compareTo(n2);
			}
		});
		for(T node:nodes){
			appendNode(node);
		}
	}
	
	protected void appendNode(T node){
		if(rootNodes.size()==0){
			rootNodes.add(node);
			node.setParent(null);
			size++;
		} else{
			//先作为子节点添加，若不是，则作为兄弟节点添加
			for(T elmt:rootNodes){
				if(elmt.contains(node)){
					appendChild(elmt,node);
					return;
				}
			}
			//可以确定是兄弟节点
			appendSibling(rootNodes,node);
		}
	}
	
	protected void appendChild(T n1,T n2){
		@SuppressWarnings("unchecked")
		List<T> children=(List<T>) n1.getChildren();
		if(children.size()==0){
			children.add(n2);
			size++;
			n2.setParent(n1);
		} else{
			for(T node:children){
				if(node.contains(n2)){
					//递归添加后代节点
					appendChild(node,n2);
					return;
				}
			}
			//说明是兄弟节点
			appendSibling(children, n2);
		}
	}
	
	protected void appendSibling(List<T> nodes,T node){
		for(int i=0,size=nodes.size();i<size;i++){
			//比较两个节点的先后顺序
			int n=node.compareTo(nodes.get(i));
			if(n==0){
				return;
			}
			if(n<0){
				nodes.add(i, node);
				this.size++;
				node.setParent(nodes.get(0));
				return;
			}
		}
		nodes.add(node);
		this.size++;
		node.setParent(nodes.get(0));
	}
	
	public List<T> getRootNodes(){
		return rootNodes;
	}
	
	public List<T> getAllNodes(){
		List<T> list=new ArrayList<>();
		getAllNodes(rootNodes,list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private final void getAllNodes(List<T> from,List<T> to){
		for(T node:from){
			to.add(node);
			if(node.getChildren().size()>0){
				//递归添加子节点
				getAllNodes((List<T>) node.getChildren(),to);
			}
		}
	}
	
	public int getSize(){
		return size;
	}
	
	public void printTree(){
		printTree(rootNodes,0);
	}
	
	@SuppressWarnings("unchecked")
	private void printTree(List<T> nodes,int depth){
		for(T node:nodes){
			for(int i=0;i<depth;i++){
				System.out.print("\t");
			}
			System.out.println(node);
			if(node.getChildren().size()>0){
				printTree((List<T>) node.getChildren(),depth+1);
			}
		}
	}
}
