package GraphAlgorithms;

import java.util.ArrayList;

import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Triple;
import Nodes.UndirectedNode;

public class PrimAlgo {
    // Q9.
    private BinaryHeapEdge binaryHeapEdge;

    public AdjacencyListUndirectedValuedGraph graph;

    public AdjacencyListUndirectedValuedGraph minimumTree;

    public PrimAlgo(AdjacencyListUndirectedValuedGraph g) {
        this.binaryHeapEdge = new BinaryHeapEdge();
        this.graph = g;
        this.minimumTree = new AdjacencyListUndirectedValuedGraph(new int[g.getNbNodes()][g.getNbNodes()]);
    }

    public int runPrimAlgo(UndirectedNode initialNode) {
        ArrayList<UndirectedNode> visitedNodes = new ArrayList<>();

        UndirectedNode currentNode = this.graph.getNodeOfList(initialNode);

        // Ajout du noeud inital à la liste des noeuds visités
        visitedNodes.add(currentNode);

        Triple<UndirectedNode, UndirectedNode, Integer> minEdge;
        UndirectedNode from;
        UndirectedNode to;

        int totalCost = 0;
        int currentCost;
        int neighbourWeight;

        while (visitedNodes.size() < this.graph.getNbNodes()) {
            for (UndirectedNode neighbour : currentNode.getNeighbours().keySet()) {
                neighbourWeight = currentNode.getNeighbours().get(neighbour);
                this.binaryHeapEdge.insert(currentNode, neighbour, neighbourWeight);
            }

            // Récupération du plus petit voisin
            minEdge = this.binaryHeapEdge.remove();
            from = minEdge.getFirst();
            to = minEdge.getSecond();

            // Gestion des cycles
            while (visitedNodes.contains(from) && visitedNodes.contains(to)) {
                minEdge = this.binaryHeapEdge.remove();
                from = minEdge.getFirst();
                to = minEdge.getSecond();
            }
            
            currentCost = minEdge.getThird();

            // Ajout de la valeur de l'arête au poids total
            totalCost += currentCost;
            minimumTree.addEdge(from, to, currentCost);

            // Note le noeud adjacent comme visité si pas déjà visité 
            if (visitedNodes.contains(from)) {
                visitedNodes.add(to);
                currentNode = this.graph.getNodeOfList(to);
            } else {
                visitedNodes.add(from);
                currentNode = this.graph.getNodeOfList(from);
            }

        }
        return totalCost; 
    }

    public static void main(String[] args) {
        // Graph from course's example
        int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7;
        AdjacencyListUndirectedValuedGraph graph = new AdjacencyListUndirectedValuedGraph(new int[8][8]);
        graph.addEdge(new UndirectedNode(A), new UndirectedNode(B), 4);
        graph.addEdge(new UndirectedNode(A), new UndirectedNode(D), 6);
        graph.addEdge(new UndirectedNode(A), new UndirectedNode(G), 2);
        graph.addEdge(new UndirectedNode(B), new UndirectedNode(E), 5);
        graph.addEdge(new UndirectedNode(B), new UndirectedNode(C), 2);
        graph.addEdge(new UndirectedNode(C), new UndirectedNode(E), 6);
        graph.addEdge(new UndirectedNode(C), new UndirectedNode(H), 5);
        graph.addEdge(new UndirectedNode(C), new UndirectedNode(D), 7);
        graph.addEdge(new UndirectedNode(D), new UndirectedNode(F), 8);
        graph.addEdge(new UndirectedNode(F), new UndirectedNode(G), 7);
        graph.addEdge(new UndirectedNode(F), new UndirectedNode(H), 3);
        graph.addEdge(new UndirectedNode(H), new UndirectedNode(G), 5);
        graph.addEdge(new UndirectedNode(G), new UndirectedNode(E), 4);
        System.out.println(graph.toString());

        // run Prim's Algorithm
        PrimAlgo primInstance = new PrimAlgo(graph);
        int minimumCost = primInstance.runPrimAlgo(new UndirectedNode(E));
        System.out.println("total cost : " + minimumCost);
        System.out.println("Minimum tree : " + primInstance.minimumTree);
    }

}
