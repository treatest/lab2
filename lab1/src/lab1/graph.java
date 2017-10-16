package lab1;

/*

public class graph {
	String[] data;
	int edgeNumber;
	int vertexNumbd;
	int[] next;
	int[] flag;
	int[][] map;
	int[][] path;
	int[][] record;

	
	public graph() {
		this.vertexNumber = 0;
		this.edgeNumber = 0;
		this.data = new String[99];
		this.next = new int[99];
		this.flag = new int[99];
		this.map = new int[99][99];
		this.path = new int[99][99];
		this.record = new int[99][99];

	}
	
	public int search(String word) {
		for (int i = 0; i < this.vertexNumber; i++)
			if (this.data[i].equals(word))
				return i;
			return -1;
	}
	public void newVertex(String word) {
		this.data[this.vertexNumber++] = word;
	}
	
	public void newEdge(String word1, String word2) {
		int word1Number = 0, word2Number = 0;
		for (int i = 0; i < this.vertexNumber; i++) {
			if (this.data[i].equals(word1))
				word1Number = i;
			if (this.data[i].equals(word2))
				word2Number = i;
		}
		this.edgeNumber++;
		this.map[word1Number][word2Number]++;
	}
	
	public void getNext() {
		this.next = new int[99];
		this.flag = new int[99];
		this.path = new int[99][99];
		this.record = new int[99][99];
		for (int i = 0; i < this.vertexNumber; i++)
			for (int j = 0; j < this.vertexNumber; j++)
				if (this.map[i][j] > 0)
					this.path[i][this.next[i]++] = j;
	}
}
*/