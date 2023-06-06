package GraphAlgorithms;
import java.util.ArrayList;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import Nodes.DirectedNode;

public class DijkstraAlgo {
    public AdjacencyListDirectedValuedGraph graph;

    public DijkstraAlgo(AdjacencyListDirectedValuedGraph g) {
        this.graph = g;
    }

    public int cout(DirectedNode x, DirectedNode y) {
        return x.getSuccs().get(y);
    }

    public char getLetterFromPosition(int position) {
        if (position < 1 || position > 26) {
            throw new IllegalArgumentException("Position invalide. La position doit être entre 1 et 26.");
        }
        char letter = (char) ('A' + position - 1);
        return letter;
    }

    public void runDijkstraAlgo(DirectedNode initialNode) {
        ArrayList<Boolean> edgeMarked = new ArrayList<Boolean>();
        ArrayList<DirectedNode> pred = new ArrayList<DirectedNode>();
        ArrayList<Integer> val = new ArrayList<Integer>();

        // Initializing
        for (DirectedNode n : this.graph.getNodes()) {
            edgeMarked.add(n.getLabel(), false);
            val.add(n.getLabel(), Integer.MAX_VALUE / 2);
            pred.add(n.getLabel(), null);
        }

        edgeMarked.set(initialNode.getLabel(), true);
        val.set(initialNode.getLabel(), 0);
        pred.set(initialNode.getLabel(), initialNode);

        // While all the nodes are not marked
        while (edgeMarked.contains(false)) {
            int x = 0;
            // Look for node x not marked with minimal value
            int min = Integer.MAX_VALUE / 2;

            for (int y = 0; y < this.graph.getNbNodes(); y++) {
                if (!edgeMarked.get(y) && val.get(y) < min) {
                    x = y;
                    min = val.get(y);
                }
            }

            // Update not fixed successors of x
            if (min < Integer.MAX_VALUE) {
                edgeMarked.set(x, true);
                DirectedNode nodeX = this.graph.getNodes().get(x);
                for (DirectedNode succ : nodeX.getSuccs().keySet()) {
                    if (!edgeMarked.get(succ.getLabel())) {
                        if (val.get(x) + cout(nodeX, succ) < val.get(succ.getLabel())) {
                            val.set(succ.getLabel(), val.get(x) + cout(nodeX, succ));
                            pred.set(succ.getLabel(), nodeX);
                        }
                    }
                }
            }
        }

        // printing results
        System.out.println("--------------------------------------------");
        System.out.print("nodes | ");
        for (DirectedNode n : this.graph.getNodes()) {
            System.out.print(getLetterFromPosition(n.getLabel() + 1) + "   ");
            if (n == this.graph.getNodes().get(this.graph.getNbNodes() - 1)) {
                System.out.println("");
            }
        }
        System.out.print("val   | ");
        for (DirectedNode n : this.graph.getNodes()) {
            System.out.print(val.get(n.getLabel()) + "   ");
            if (n == this.graph.getNodes().get(this.graph.getNbNodes() - 1)) {
                System.out.println("");
            }
        }
        System.out.println("--------------------------------------------");
    }

    public static void main(String[] args) throws Exception {
        int A = 0,
                B = 1,
                C = 2,
                D = 3,
                E = 4,
                F = 5,
                G = 6,
                H = 7;

        // Using class example
        AdjacencyListDirectedValuedGraph graph = new AdjacencyListDirectedValuedGraph(new int[8][8]);
        graph.addArc(new DirectedNode(A), new DirectedNode(B), 2);
        graph.addArc(new DirectedNode(A), new DirectedNode(C), 6);
        graph.addArc(new DirectedNode(B), new DirectedNode(D), 1);
        graph.addArc(new DirectedNode(B), new DirectedNode(H), 1);
        graph.addArc(new DirectedNode(C), new DirectedNode(B), 3);
        graph.addArc(new DirectedNode(C), new DirectedNode(G), 2);
        graph.addArc(new DirectedNode(C), new DirectedNode(F), 2);
        graph.addArc(new DirectedNode(D), new DirectedNode(C), 2);
        graph.addArc(new DirectedNode(D), new DirectedNode(G), 6);
        graph.addArc(new DirectedNode(D), new DirectedNode(E), 7);
        graph.addArc(new DirectedNode(E), new DirectedNode(B), 3);
        graph.addArc(new DirectedNode(E), new DirectedNode(H), 2);
        graph.addArc(new DirectedNode(F), new DirectedNode(D), 1);
        graph.addArc(new DirectedNode(F), new DirectedNode(E), 4);
        graph.addArc(new DirectedNode(G), new DirectedNode(A), 1);
        graph.addArc(new DirectedNode(G), new DirectedNode(F), 2);
        graph.addArc(new DirectedNode(H), new DirectedNode(F), 3);
        DijkstraAlgo dijkstraAlgo = new DijkstraAlgo(graph);

        // Using A as initial node
        dijkstraAlgo.runDijkstraAlgo(graph.getNodes().get(0));
    }
}
