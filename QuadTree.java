import java.util.*;

public class QuadTree {
	private final static int NUM_NODES = 4;
	private final boolean isBlack;
	private final boolean isWhite;
	private final QuadTree[] nodes;
	
	public static void main(String[] args){
		QuadTree whiteTree = new QuadTree(false);
		QuadTree blackTree = new QuadTree(true);
		QuadTree[] checkerArray = new QuadTree[4];
		for (int i = 0; i < 4; i++){
			checkerArray[i] = (i % 2 == 0 ? whiteTree: blackTree);
		}
		QuadTree checkerTree = new QuadTree(checkerArray);
		System.out.println(whiteTree.printTree());
		System.out.println(blackTree.printTree());
		System.out.println(checkerTree.printTree());
		checkerArray[0] = checkerTree;
		checkerArray[3] = checkerTree;
		QuadTree deepTree = new QuadTree(checkerArray);
		System.out.println(deepTree.printTree());	
		QuadTree mergeTree = new QuadTree(deepTree, checkerTree);
		System.out.println(mergeTree.printTree());
		
		
	}
	
	public QuadTree(boolean isBlack){
		this.isBlack=isBlack;
		this.isWhite=!isBlack;
		nodes = new QuadTree[NUM_NODES];
	}
	
	public QuadTree(QuadTree[] nodes){
		this.nodes = new QuadTree[NUM_NODES];
		int colorTest = 0;
		for (int i = 0; i < NUM_NODES; i++){
			if (nodes[i].isBlack)
				colorTest++;
			if (nodes[i].isWhite)
				colorTest--;
		}
		if((isBlack = (colorTest == NUM_NODES)) == (isWhite = (colorTest == -NUM_NODES))){
			copyNodes(nodes);
		}
	}
	
	private void copyNodes(QuadTree[] nodes){
		for (int i = 0; i < NUM_NODES; i++)
			this.nodes[i] = nodes[i];
	}
	
	public QuadTree(QuadTree left, QuadTree right){
		nodes = new QuadTree[NUM_NODES];
		if (left.isBlack || right.isBlack){
			isBlack = true;
			isWhite = false;
		} else if (left.isWhite) {
			isBlack = right.isBlack;
			isWhite = right.isWhite;
			copyNodes(right.nodes);
		} else if (right.isWhite) {
			isBlack = left.isBlack;
			isWhite = left.isWhite;
			copyNodes(left.nodes);
		} else {
			int colorTest = 0;
			for (int i = 0; i < NUM_NODES; i++){
				nodes[i] = new QuadTree(left.nodes[i], right.nodes[i]);
				if (nodes[i].isBlack)
					colorTest++;
				if (nodes[i].isWhite)
					colorTest--;
			}
			isBlack = (colorTest == NUM_NODES);
			isWhite = (colorTest == -NUM_NODES);
		}
	}
	
	public String printTree(){
		if (isBlack) {
			return "1";
		} else if (isWhite) {
			return "0";
		} else {
			String text = "[";
			for (int i = 0; i < NUM_NODES; i++){
				text += nodes[i].printTree();
			}
			text += "]";
			return text;
		}
	}
		
}
