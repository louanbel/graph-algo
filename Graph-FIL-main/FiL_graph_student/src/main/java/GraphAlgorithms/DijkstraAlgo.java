package GraphAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import AdjacencyList.AdjacencyListDirectedValuedGraph;
import Nodes.DirectedNode;

public class DijkstraAlgo {
    public AdjacencyListDirectedValuedGraph graph;

    public DijkstraAlgo(AdjacencyListDirectedValuedGraph g) {
        this.graph = g;
    }

    public void runDijkstraAlgo(DirectedNode initialNode) {
        List<DirectedNode> edgeNotMarked = this.graph.getNodes();
        HashMap<DirectedNode, DirectedNode> pred = new HashMap<DirectedNode, DirectedNode>();
        HashMap<DirectedNode, Integer> val = new HashMap<DirectedNode, Integer>();
        
        // Initializing
        for(DirectedNode n: this.graph.getNodes()) {
            val.put(n, Integer.MAX_VALUE / 2);
            pred.put(n, null);
        }

        edgeNotMarked.remove(initialNode.getLabel());

        DirectedNode x = initialNode;
        // While all the nodes are not marked
        while(!edgeNotMarked.isEmpty()) {
            
            // Look for node x not marked with minimal value
            int min = Integer.MAX_VALUE / 2;
            for(DirectedNode y: this.graph.getNodes()) {
                if(edgeNotMarked.contains(y) && val.get(y) < min) {
                    x = y;
                    min = val.get(y);
                }
            }
            
            // Update not fixed successors of x
            if(min < Integer.MAX_VALUE / 2) {
                edgeNotMarked.remove(x);
                for(DirectedNode y: x.getSuccs().keySet())
                {
                    if(edgeNotMarked.contains(y) && val.get(x) + x.getSuccs().get(y) < val.get(y)) {
                        val.put(y, val.get(x) + x.getSuccs().get(y));
                        pred.put(y, x);
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        AdjacencyListDirectedValuedGraph al = new AdjacencyListDirectedValuedGraph(matrixValued);

        DijkstraAlgo dijkstraAlgo = new DijkstraAlgo(al);
        dijkstraAlgo.runDijkstraAlgo(null); // wip
    }
}
