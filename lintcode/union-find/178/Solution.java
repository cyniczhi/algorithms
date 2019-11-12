import java.util.Arrays;

public class Solution {
    /**
     * @param n:     An integer
     * @param edges: a list of undirected edges
     * @return: true if it's a valid tree, or false
     */
    public boolean validTree(int n, int[][] edges) {
        // write your code here

        Union u = new Union(n);

        for (int[] edge : edges) {
            if (edge.length != 2) {
                throw new InternalError();
            }

            // construct trees with given edges
            if (!u.union(edge[0], edge[1])) {
                return false;
            }
            System.out.println(u.count());
        }

        return u.count() == 1;
    }

    class Union {
        private int[] id;
        private int cnt;

        public Union(int n) {
            cnt = n;
            id = new int[n];
            for (int i = 0; i < n; i++) {
                id[i] = i;
            }
            System.out.printf("init: %s\n", Arrays.toString(id));
        }

        boolean union(int i, int j) {

            int left = find(i);
            int right = find(j);
            System.out.printf("left: %d, right: %d\n", left, right);

            if (left == right) {
                System.out.printf("circle detected: %d, %d, %d", left, i, j);
                return false;
            }

            if (left < right)
                id[right] = left;
            else
                id[left] = right;
            System.out.println("count--");

            cnt--;
            return true;
        }

        int find(int i) {
            if (i > id.length - 1) {
                // System.out.println("unexpected: invalid index");
                return -1;
            }
            System.out.printf("retrieve root for: %d\n", i);

            int root = id[i];
            while (root != id[root])
                root = id[root];

            System.out.printf("found: %d\n", root);
            return root;
        }

        int count() {
            return cnt;
        }
    }

    public static void main(String[] args) {
        //int size = 10;
        //int[][] edges = { { 0, 1 }, { 5, 6 }, { 6, 7 }, { 9, 0 }, { 3, 7 }, { 4, 8 }, { 1, 8 }, { 5, 2 }, { 5, 3 }, };

        // int size = 5;
        // int[][] edges = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 1, 4 }, };

        int size = 5;
        int[][] edges = { { 0, 1 }, { 1, 2 }, { 0, 3 }, { 0, 4 }, { 1, 4 }, };

        Solution s = new Solution();
        System.out.println(s.validTree(size, edges));
    }
}
