public class QuadTree {
	private final static int NUM_NODES = 4;
	private final QuadTree[] nodes;
	private static final QuadTree WHITE = new QuadTree();
	private static final QuadTree BLACK = new QuadTree();
	
	public static void main(String[] args){
		QuadTree[] buildArray = new QuadTree[4];
		QuadTree firstNode = QuadTree.BLACK;
		QuadTree longTree = new QuadTree();
		QuadTree swirlTree = new QuadTree();
		for(int i = 0; i < 4; i++){
			buildArray[0] = firstNode;
			buildArray[1] = QuadTree.WHITE;
			buildArray[2] = QuadTree.WHITE;
			buildArray[3] = QuadTree.BLACK;
			longTree = new QuadTree(buildArray);
			longTree.prettyPrint();
			firstNode = longTree;
		}
		firstNode = QuadTree.BLACK;
		for(int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				buildArray[j] = (i == j ? QuadTree.WHITE : firstNode);
			}
			swirlTree = new QuadTree(buildArray);
			swirlTree.prettyPrint();
			firstNode = swirlTree;
		}
		QuadTree mergeTree = qtUnion(longTree, swirlTree);
		mergeTree.prettyPrint();
	}
	
	public QuadTree(){
		nodes = new QuadTree[NUM_NODES];
	}
	
	public static QuadTree qtSplice(QuadTree[] nodes){
		int colorTest = 0;
		for (int i = 0; i < NUM_NODES; i++){
			if (nodes[i] == BLACK)
				colorTest++;
			if (nodes[i] == WHITE)
				colorTest--;
		}
		if(colorTest == NUM_NODES)
			return BLACK;
		if(colorTest == -NUM_NODES)
			return WHITE;
		return new QuadTree(nodes);
	}
	
	public QuadTree(QuadTree[] nodes){
		this.nodes = new QuadTree[NUM_NODES];
		for (int i = 0; i < NUM_NODES; i++)
			this.nodes[i] = nodes[i];		
	}
	
	// Creates new QuadTree using rule {WW} = W; {BW, WB, BB} = B
	public static QuadTree qtUnion(QuadTree left, QuadTree right){
		if (left==BLACK || right==BLACK){
			return BLACK;
		} else if (left==WHITE) {
			return right;
		} else if (right==WHITE) {
			return left;
		} else {
			QuadTree[] nodes = new QuadTree[4];
			for (int i = 0; i < NUM_NODES; i++){
				nodes[i] = qtUnion(left.nodes[i], right.nodes[i]);
			}
			return qtSplice(nodes);
		}
	}
	
	public String printTree(){
		if (this == BLACK) {
			return "1";
		} else if (this == WHITE) {
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
	
	public String prettyString(){
		if(this == BLACK)
			return "#";
		if(this == WHITE)
			return " ";
		int maxLength = 1;
		String nodePrint[] = new String[NUM_NODES];
		for(int i = 0; i < NUM_NODES; i++){
			nodePrint[i] = nodes[i].prettyString();
			maxLength = Math.max(maxLength, nodePrint[i].length());
		}
		String text = "";
		for(int i = 0; i < NUM_NODES; i++){
			for(int j = 0; j < maxLength; j++){
				text = text + nodePrint[i].charAt((j * nodePrint[i].length()) / maxLength);
			}
		}
		return text;
	}
	
	public void prettyPrint(){
		String text = prettyString();
		int length = (int) Math.sqrt(text.length());
		char[][] printOut = new char[length][length];
		for(int i = 0; i < text.length(); i++){
			int x = 0;
			int y = 0;
			for(int j = 1; j < length; j *= 2){
				if((i / j / j) % 2 == 1)
					y += j;
				if((i / j / j) % 4 > 1)
					x += j;
			}
			printOut[x][y] = (char) text.charAt(i);
		}
		char[] header = new char[length+2];
		header[0] = '+';
		header[length+1] = '+';
		for(int i = 0; i < length; i++)
			header[i+1] = '-';
		System.out.println(new String(header));
		for(int i = 0; i < length; i++){
			System.out.println("|" + new String(printOut[i]) + "|");
		}
		System.out.println(new String(header));
	}
}
