package GraphAlgorithms;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Triple;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	private static Queue<Integer> fifo;
	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	// A completer
	public static void BFS(AdjacencyListDirectedGraph graph, int startVertex) {
		boolean[] visited = new boolean[graph.getNbNodes()];
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		visited[startVertex] = true;
		fifo = new LinkedList<Integer>();
		fifo.add(startVertex);
		while (!fifo.isEmpty()) {
			int currentVertex = fifo.poll();
			System.out.print(currentVertex + " ");
			for (DirectedNode node : graph.getNodes().get(currentVertex).getSuccs().keySet()) {
				int succ = node.getLabel();
				if (!visited[succ]) {
					visited[succ] = true;
					fifo.add(succ);
				}
			}
		}
	}

	public static void explorerSommet(AdjacencyListDirectedGraph graph, int vertex, boolean[] visited) {
		visited[vertex] = true;
		System.out.print(vertex + " ");
		for (DirectedNode node : graph.getNodes().get(vertex).getSuccs().keySet()) {
			int succ = node.getLabel();
			if (!visited[succ]) {
				explorerSommet(graph, succ, visited);
			}
		}
	}

	public static void explorerGraphe(AdjacencyListDirectedGraph graph) {
		boolean[] visited = new boolean[graph.getNbNodes()];
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		for (int i = 0; i < visited.length; i++) {
			if (!visited[i]) {
				explorerSommet(graph, i, visited);
			}
		}
	}




	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);

		System.out.println("BFS Traversal:");
		BFS(al, 0);
		System.out.println();

		System.out.println("DFS Traversal:");
		explorerGraphe(al);
	}
}
