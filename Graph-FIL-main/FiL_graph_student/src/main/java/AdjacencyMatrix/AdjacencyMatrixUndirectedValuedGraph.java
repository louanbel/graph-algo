package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;


public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	private  int[][] matrixCosts;	// The graph with Costs

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixUndirectedValuedGraph(int[][] mat, int[][] matrixVal) {
		super(mat);
		this.matrixCosts = new int[this.order][this.order];
		for(int i =0;i<this.order;i++){
			for(int j=i;j<this.order;j++){				
				int cost = matrixVal[i][j]; 
				this.matrixCosts[i][j] = cost;
				this.matrixCosts[j][i] = cost; 
			}
		}
	}


	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	/**
	 * @return the matrix with costs of the graph
 	 */
	public int[][] getMatrixCosts() {
		return matrixCosts;
	}

	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------	
	
	/**
     * removes the edge (x,y) if there exists at least one between these nodes in the graph. And if there remains no arc, removes the cost.
     */
	
	public void removeEdge(int x, int y) {
		super.removeEdge(x, y);
		// A completer
		if (!super.isEdge(x, y)) {
			this.matrixCosts[x][y]	= 0;
			this.matrixCosts[y][x]	= 0;
		}
	}

	/**
     * adds the edge (x,y,cost), we allow the multi-graph. If there is already one initial cost, we keep it.
     */
	public void addEdge(int x, int y, int cost ) {
		super.addEdge(x,y);
		if (this.matrixCosts[x][y] == 0 && this.matrixCosts[y][x] == 0) {
			this.matrixCosts[x][y]	= cost;
			this.matrixCosts[y][x]	= cost;
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder(super.toString() + "\n Matrix of Costs: \n");
		for (int[] matrixCost : this.matrixCosts) {
			for (int i : matrixCost) {
				s.append(i).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}
	
	
	public static void main(String[] args) {
		int[][] matrix = GraphTools.generateGraphData(10, 20, true, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		GraphTools.afficherMatrix(matrix);
		AdjacencyMatrixUndirectedValuedGraph am = new AdjacencyMatrixUndirectedValuedGraph(matrix, matrixValued);
		System.out.println(am);
		// A completer
	}

}
