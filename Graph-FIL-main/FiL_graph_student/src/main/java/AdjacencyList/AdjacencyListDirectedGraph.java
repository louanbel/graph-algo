package AdjacencyList;

import java.util.ArrayList;
import java.util.List;



import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;



public class AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

	private static int _DEBBUG =0;
	
	protected List<DirectedNode> nodes;
    protected int order;
    protected int m;

    
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------
 

	public AdjacencyListDirectedGraph(){
		this.nodes = new ArrayList<DirectedNode>();
		this.order = 0;
	    this.m = 0;		
	}
	
	public AdjacencyListDirectedGraph(List<DirectedNode> nodes) {
		this.nodes = nodes;
        this.order = nodes.size();
        this.m = 0;
        for (DirectedNode i : nodes) {
            this.m += i.getNbSuccs();
        }
    }

    public AdjacencyListDirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrix[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,0);
                    nn.getPreds().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public AdjacencyListDirectedGraph(AdjacencyListDirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();
        for(DirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (DirectedNode n : g.getNodes()) {
        	DirectedNode nn = this.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn,0);
                snn.getPreds().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * Returns the list of nodes in the graph
     */
    public List<DirectedNode> getNodes() {
        return nodes;
    }

    /**
     * Returns the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.order;
    }
    
    /**
	 * @return the number of arcs in the graph
 	 */
    public int getNbArcs() {
        return this.m;
    }

    /**
	 * @return true if arc (from,to) exists in the graph
 	 */
    public boolean isArc(DirectedNode from, DirectedNode to) {
    	return from.getSuccs().containsKey(to);
    }

    /**
	 * Removes the arc (from,to), if it exists
 	 */
    public void removeArc(DirectedNode from, DirectedNode to) {
    	if(isArc(from, to))
        {
            from.getSuccs().remove(to);
            to.getPreds().remove(from);
            this.m--;
        }
    }

    /**
	 * Adds the arc (from,to) if it is not already present in the graph, requires the existing of nodes from and to 
 	 */
    public void addArc(DirectedNode from, DirectedNode to) {
    	if(!isArc(from, to)) 
        {
            from.addSucc(to, _DEBBUG);
            to.addPred(from, _DEBBUG);
            this.m++;
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    public DirectedNode makeNode(int label) {
        return new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (DirectedNode j : nodes.get(i).getSuccs().keySet()) {
                int IndSucc = j.getLabel();
                matrix[i][IndSucc] = 1;
            }
        }
        return matrix;
    }

    /**
	 * @return a new graph implementing IDirectedGraph interface which is the inverse graph of this
 	 */
    public AdjacencyListDirectedGraph computeInverse() {
        AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph(new int[this.order][this.order]); // creation of a new empty matrix of size equal to "order". 
        //
        return g;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
        System.out.println(al);
        // Test isArc
        DirectedNode node0 = al.getNodes().get(0);
        DirectedNode node3 = al.getNodes().get(3);
        System.out.println(al.isArc(node0, node3));
        // Test removeArc should be false
        al.removeArc(node0, node3);
        System.out.println(al.isArc(node0, node3));
    }
}
