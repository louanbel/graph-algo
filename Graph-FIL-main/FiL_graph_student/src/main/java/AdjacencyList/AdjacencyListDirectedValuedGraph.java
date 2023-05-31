package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class AdjacencyListDirectedValuedGraph extends AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public AdjacencyListDirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.order = matrixVal.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrixVal[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,matrixVal[n.getLabel()][j]);
                    nn.getPreds().put(n,matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
        super.addArc(from, to);
        if(!this.isArc(from, to))
        {
            from.getSuccs().put(to, cost);
            to.getPreds().put(from, cost);
        }
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
            	s.append("(").append(sn).append(",").append(n.getSuccs().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListDirectedValuedGraph al = new AdjacencyListDirectedValuedGraph(matrixValued);
        System.out.println(al);
        // Test addArc
        DirectedNode nodeX = new DirectedNode(0);
        DirectedNode nodeY = new DirectedNode(1);
        al.addArc(nodeX, nodeY, 1555);
        System.out.println(al);
    }
	
}
