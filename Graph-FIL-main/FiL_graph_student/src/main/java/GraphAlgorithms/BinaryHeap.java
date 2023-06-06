package GraphAlgorithms;

public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    public boolean insert(int element) {

        if (this.nodes.length <= pos) {
            // Si la taille du tableau n'est pas suffisante, on le resize
            this.resize();
        }

        // Ajout de l'élément
        this.nodes[pos] = element;

        int currentPos = pos;

        int oldParentPos = this.getParentPos(currentPos);
        // On swap l'élément sur la bonne branche (percolate up)
        // Q3 Dans le pire des cas, la complexité est de O(log(pos)) avec une moyenne de
        // O(1)
        while (currentPos != 0 && this.nodes[currentPos] < this.nodes[oldParentPos]) {
            this.swap(currentPos, oldParentPos);
            currentPos = oldParentPos;
            oldParentPos = this.getParentPos(currentPos);
        }

        pos++;

        return true; // Vraiment une utilité ?
    }

    public int getParentPos(int child) {
        // retourne l'index du parent (integer arrondie par défaut)
        return child != 0 ? (child - 1) / 2 : 0;
    }

    public int remove() {
        // 1. Permuter la racine de l'arbre avec la dernière feuille utilisée et la
        // supprimer
        swap(0, this.pos - 1);
        this.nodes[this.pos - 1] = Integer.MAX_VALUE;
        this.pos--;

        int i = 0, oldPos = 0;
        boolean treeChanged = true;

        // 2. Percolate down
        while (!isLeaf(i) && treeChanged) // dans tous les cas, complexité de O(log(pos))
        {
            treeChanged = false;
            if (this.nodes[this.getBestChildPos(i)] < this.nodes[i]) {
                oldPos = this.getBestChildPos(i);
                this.swap(i, oldPos);
                i = oldPos;
                treeChanged = true;
            }
        }

        return i;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            if (2 * src + 2 > pos) {
                return 2 * src + 1;
            }
            // Retourne l'index de la plus petite des deux feuilles
            return this.nodes[2 * src + 1] < this.nodes[2 * src + 2] ? 2 * src + 1 : 2 * src + 2;
        }
    }

    /**
     * Test if the node is a leaf in the binary heap
     * 
     * @returns true if it's a leaf or false else
     * 
     */
    private boolean isLeaf(int src) {
        // Si pas d'enfants, c'est une feuille
        return this.pos <= src * 2 + 1 && this.pos <= src * 2 + 2;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
     * Recursive test to check the validity of the binary heap
     * 
     * @returns a boolean equal to True if the binary tree is compact from left to
     *          right
     * 
     */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty() + "\n");

        // int k = 20;
        // int m = k;
        // int min = 2;
        // int max = 20;
        // while (k > 0) {
        // int rand = min + (int) (Math.random() * ((max - min) + 1));
        // System.out.print(" insert " + rand);
        // jarjarBin.insert(rand);
        // k--;
        // }

        // Q4
        jarjarBin.insert(4);
        jarjarBin.insert(10);
        jarjarBin.insert(8);
        jarjarBin.insert(6);
        jarjarBin.insert(3);

        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());

        // Q5
        System.out.println(jarjarBin.nodes[jarjarBin.getBestChildPos(0)]); // Doit être la valeur 4 qui est plus petite
                                                                           // que 8

        // Q7
        jarjarBin.remove();
        System.out.println(jarjarBin);
        jarjarBin.remove();
        System.out.println(jarjarBin);

    }

}
