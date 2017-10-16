package lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class graph {
	String[] data;
	int edgeNumber;
	int vertexNumber;
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

public class lab1{
	
	static graph G = new graph();
	static int bridgeNumber;
	static ArrayList<String> bridgeWords;
	static Random random = new Random();
	static String dotpath="C:\\Users\\ASUS\\Desktop\\lab1";
	static String dotmegPath="dot -Tjpg path.dot -o path.jpg";
	static String dotmeg="dot -Tjpg lab1.dot -o lab1.jpg";
	
	public void showDirectedGraph(graph G) {
		
		File file=new File("lab1.dot");
		try {
			file.createNewFile();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			byte line[]=null;
			String a,b;
			FileOutputStream out=new FileOutputStream(file);
			line="digraph pic1 { \r\n".getBytes();
			out.write(line);
			for(int i=0;i<G.vertexNumber;i++)
				for(int j=0;j<G.vertexNumber;j++)
					if(G.map[i][j]>=1)
					{
						a=G.data[i];
						b=G.data[j];
						line=("  "+a+"->"+b+"[label=\""+G.map[i][j]+"\"]\r\n").getBytes();
						out.write(line);
					}
			line="}".getBytes();
			out.write(line);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try { 
			Runtime rt = Runtime.getRuntime(); 
			rt.exec("cmd /c cd "+dotpath +" && "+ dotmeg);
			  
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
			 
	}
	
	
	public static String queryBridgeWords(String word1, String word2) {
		String bridge = "";
		int word1Number = G.search(word1);
		int word2Number = G.search(word2);
		bridgeNumber = 0;
		bridgeWords = new ArrayList<String>();
		if (word1Number == -1 && word2Number != -1) {
			bridgeNumber = -1;
			bridge = "No \"" + word1 + "\" in the graph!";
		}
		if (word1Number != -1 && word2Number == -1) {
			bridgeNumber = -2;
			bridge = "No \"" + word2 + "\" in the graph!";
		}
		if (word1Number == -1 && word2Number == -1) {
			bridgeNumber = -3;
			bridge = "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
		}
		if (bridgeNumber == 0) {
			for (int i = 0; i < G.vertexNumber; i++)
				if (G.map[word1Number][i] > 0 && G.map[i][word2Number] > 0)
					bridgeWords.add(G.data[i]);
			bridgeNumber = bridgeWords.size();
		}
		if (bridgeNumber == 0)
			bridge = "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
		if (bridgeNumber == 1)
			bridge = "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" is: " + bridgeWords.get(0);
		if (bridgeNumber > 1) {
			bridge = "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: ";
			for (int i = 0; i < bridgeWords.size() - 1; i++)
				bridge += bridgeWords.get(i) + ", ";
			bridge += "and " + bridgeWords.get(bridgeWords.size() - 1) + ".";
		}
		return bridge;
	}
	
	public static String generateNewText(String inputText) {
		String newText = "";
		String lineTxt = inputText.toLowerCase();
		String word = "";
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < lineTxt.length(); i++) {
			if (lineTxt.charAt(i) <= 'z' && lineTxt.charAt(i) >= 'a') {
				word += lineTxt.charAt(i);
				if (i == lineTxt.length() - 1)
					words.add(word);
			}
			else {
				if (word != "")
					words.add(word);
				word = "";
			}
		}
		for (int i = 0; i < words.size() - 1; i++) {
			int randomNumber = 0;
			String newWord = "";
			queryBridgeWords(words.get(i), words.get(i + 1));
			if (bridgeNumber > 0) {
				randomNumber = random.nextInt(bridgeNumber);
				newWord = bridgeWords.get(randomNumber);
				newText += words.get(i) + " " + newWord + " ";
			}
			else
				newText +=words.get(i) + " ";
		}
		newText += words.get(words.size() - 1);
		return newText;
	}
	
	public static String calcShortestPath(String word1, String word2) {
		
		File file=new File("path.dot");
		try {
			file.createNewFile();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String shortestPath = "";
		ArrayList<String> path = new ArrayList<String>();
		int[] searchList = new int[99];
		int[] pre = new int[99];
		int shortestPathRecord[]=new int[99];
		int num=0;
		int recordFlag[] = new int[99];
		Arrays.fill(searchList, -1);
		Arrays.fill(pre, -1);
		int head = 0, tail = 1, goal = G.search(word2);
		searchList[0] = G.search(word1);
		G.getNext();
		if (searchList[0] >= 0 && goal >= 0) {
			while (tail > head) {
				if (searchList[head] == goal) {
					break;
				}
				for (int i = 0; i < G.next[searchList[head]]; i++)
				if (G.flag[G.path[searchList[head]][i]] == 0) {
					pre[G.path[searchList[head]][i]] = searchList[head];
					G.flag[G.path[searchList[head]][i]]++;
					searchList[tail++] = G.path[searchList[head]][i];
				}
				head++;
			}
			if (pre[goal] != -1) {
				int pos = goal;
				while (pos != searchList[0]) {
					pos = pre[pos];
					path.add(G.data[pos]);
				}
				shortestPath += G.data[searchList[0]];
				shortestPathRecord[num]=searchList[0];
				for (int i = path.size() - 2; i >= 0; i--)
				{
					for(int j=0;j<G.vertexNumber;j++)
						if(path.get(i).equals(G.data[j]))
							shortestPathRecord[++num]=j;
					shortestPath += "->" + path.get(i);
					}
				shortestPath += "->" + G.data[goal];
				shortestPathRecord[++num]=goal;
			}
			else{
				shortestPath = "No path from \"" + word1 + "\" to \"" + word2 + "\"!";
				return shortestPath;
				}
		}
		else {
			shortestPath = "No \"" + word1 + "\" or \"" + word2 + "\" in the graph!";
			return shortestPath;
		}
		try {
			byte line[]=null;
			String a,b;
			FileOutputStream out=new FileOutputStream(file);
			line="digraph pic2 { \r\n".getBytes();
			out.write(line);
			line=(G.data[shortestPathRecord[0]]+"[color=pink style=filled]\r\n").getBytes();
			out.write(line);
			for(int i=1;i<num;i++){
				line=(G.data[shortestPathRecord[i]]+"[color=green style=filled]\r\n").getBytes();
				out.write(line);
			}
			line=(G.data[shortestPathRecord[num]]+"[color=skyblue style=filled]\r\n").getBytes();
			out.write(line);
			for(int i=0;i<num;i++){
				a=G.data[shortestPathRecord[i]];
				b=G.data[shortestPathRecord[i+1]];
				line=("  "+a+"->"+b+" [label=\""+G.map[shortestPathRecord[i]][shortestPathRecord[i+1]]+"\" style=filled color=blue]\r\n").getBytes();
				out.write(line);
				recordFlag[i]=G.map[shortestPathRecord[i]][shortestPathRecord[i+1]];
				G.map[shortestPathRecord[i]][shortestPathRecord[i+1]]=0;
				}
			for(int i=0;i<G.vertexNumber;i++)
				for(int j=0;j<G.vertexNumber;j++)
					if(G.map[i][j]>=1)
					{
						a=G.data[i];
						b=G.data[j];
						line=("  "+a+"->"+b+"[label=\""+G.map[i][j]+"\"]\r\n").getBytes();
						out.write(line);
					}
			line="}".getBytes();
			out.write(line);
			out.close();
			for(int i=0;i<num;i++)
				G.map[shortestPathRecord[i]][shortestPathRecord[i+1]]=recordFlag[i];
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try { 
			Runtime rt = Runtime.getRuntime(); 
			rt.exec("cmd /c cd "+dotpath +" && "+ dotmegPath);
			  
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		return shortestPath;
	}
	
	public static String randomWalk() {
		String randomText = "";
		G.getNext();
		int start = random.nextInt(G.vertexNumber);
		randomText += G.data[start];
		int i = start;
		while (G.next[i] > 0) {
			int j = random.nextInt(G.next[i]);
			randomText += " " + G.data[G.path[i][j]];
			G.record[i][G.path[i][j]]++;
			if (G.record[i][G.path[i][j]] == 2)
				break;
			i = G.path[i][j];
		}
		
		File file=new File("random.txt");
		try {
			file.createNewFile();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			byte line[]=null;
			String a,b;
			FileOutputStream out=new FileOutputStream(file);
			line=randomText.getBytes();
			out.write(line);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return randomText;
	}
	
	public static void main(String[] args) {
		System.out.println("输入文本文件路径: ");
		ArrayList<String> words = new ArrayList<String>();
		String option = "";
		Scanner systemIn = new Scanner(System.in);
		String filePath = systemIn.nextLine();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if(file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null) {
					lineTxt = lineTxt.toLowerCase();
					String word = "";
					for (int i = 0; i < lineTxt.length(); i++) {
						if (lineTxt.charAt(i) <= 'z' && lineTxt.charAt(i) >= 'a') {
							word += lineTxt.charAt(i);
							if (i == lineTxt.length() - 1)
								words.add(word);
						}
						else {
							if (word != "")
								words.add(word);
							word = "";
						}
					}
				}
			read.close();
			}
			else {
				System.out.println("No File!");
			}
		}
		catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		for (int i = 0; i < words.size(); i++) {
			if (G.search(words.get(i)) == -1)
				G.newVertex(words.get(i));
			if (i != 0)
				G.newEdge(words.get(i - 1), words.get(i));
		}
		System.out.println("1.展示有向图\n2.查询桥接词\n3.根据桥接词生成新文本\n4.计算两个单词之间的最短路径\n5.随机游走\n6.退出");
		while ((option = systemIn.nextLine()) != null) {
			if (option.equals("1"))
				new lab1().showDirectedGraph(G);
			if (option.equals("2")) {
				String word1 = "", word2 = "";
				System.out.println("输入word1: ");
				word1 = systemIn.nextLine();
				System.out.println("输入word2: ");
				word2 = systemIn.nextLine();
				System.out.println(queryBridgeWords(word1, word2));
			}
			if (option.equals("3")) {
				String newTxtIn = "";
				System.out.println("输入新文本: ");
				newTxtIn = systemIn.nextLine();
				System.out.println(generateNewText(newTxtIn));
			}
			if (option.equals("4")) {
				String word1 = "", word2 = "";
				System.out.println("输入word1: ");
				word1 = systemIn.nextLine();
				System.out.println("输入word2: ");
				word2 = systemIn.nextLine();
				System.out.println(calcShortestPath(word1, word2));
			}
			if (option.equals("5"))
				System.out.println(randomWalk());
			if (option.equals("6"))
				break;
			System.out.println("1.展示有向图\n2.查询桥接词\n3.根据桥接词生成新文本\n4.计算两个单词之间的最短路径\n5.随机游走\n6.退出");
		}
		systemIn.close();
	}
}