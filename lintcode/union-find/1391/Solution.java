import java.util.ArrayList;

public class Solution {
    /**
     * @param grid:
     * @return: nothing
     */
    public int largestIsland(int[][] grid) {
        UnionFind uf = new UnionFind(grid.length * grid.length);

        ArrayList<int[]> toBeConvert = new ArrayList<int[]>();

        // connect with nodes on down/right side.
        int len = grid.length;
        for (int row = 0; row < len; row++) {
            for (int col = 0; col < len; col++) {
                // System.out.printf("\nCheck node grid[%d][%d] = {%d}: \n", row, col,
                // grid[row][col]);

                if (grid[row][col] == 1) {
                    // upside node
                    if (row > 0 && grid[row - 1][col] == 1) {
                        // System.out.printf("[connect] to connect upside node.\n");
                        uf.union(col + row * len, col + (row - 1) * len, false);
                    }

                    // downside node
                    if (row < len - 1 && grid[row + 1][col] == 1) {
                        // System.out.println("[connect] downside node.");
                        uf.union(col + row * len, col + (row + 1) * len, false);
                    }

                    // leftside node
                    if (col > 0 && grid[row][col - 1] == 1) {
                        // System.out.printf("[connect] to connect leftside node.\n");
                        uf.union(col + row * len, col - 1 + row * len, false);
                    }

                    // rightside node
                    if (col < len - 1 && grid[row][col + 1] == 1) {
                        // System.out.println("[connect] rightside node.");
                        uf.union(col + row * len, col + 1 + row * len, false);
                    }
                } else {
                    // System.out.println("[connect] to be convert");
                    toBeConvert.add(new int[] { row, col });
                }
            }
        }

        int cur = uf.largest;

        for (int[] ele : toBeConvert) {
            int row = ele[0];
            int col = ele[1];

            UnionFind tmpUf = new UnionFind(uf);

            // System.out.printf("\nConvert node grid[%d][%d] = {%d}: \n", row, col,
            // grid[row][col]);

            // upside node
            if (row > 0 && grid[row - 1][col] == 1) {
                // System.out.printf("[convert] to connect upside node.\n");
                tmpUf.union(col + row * len, col + (row - 1) * len, false);
            }

            // downside node
            if (row < len - 1 && grid[row + 1][col] == 1) {
                // System.out.printf("[convert] to connect downside node.\n");
                tmpUf.union(col + row * len, col + (row + 1) * len, false);
            }

            // leftside node
            if (col > 0 && grid[row][col - 1] == 1) {
                // System.out.printf("[convert] to connect leftside node.\n");
                tmpUf.union(col + row * len, col - 1 + row * len, false);
            }

            // rightside node
            if (col < len - 1 && grid[row][col + 1] == 1) {
                // System.out.printf("[convert] to connect rightside node.\n");
                tmpUf.union(col + row * len, col + 1 + row * len, false);
            }

            cur = cur < tmpUf.largest ? tmpUf.largest : cur;
        }

        return cur;
    }

    int getIndex(int col, int row, int len) {
        return col + row * len;
    }

    class UnionFind {
        int cnt;
        int[] size;
        int[] root;
        int largest;

        UnionFind(int n) {
            cnt = largest = 0;
            root = new int[n];
            size = new int[n];

            for (int i = 0; i < n; i++) {
                root[i] = i;
                size[i] = 1;
            }
        }

        UnionFind(UnionFind uf) {
            cnt = uf.cnt;
            largest = uf.largest;
            size = uf.size.clone();
            root = uf.root.clone();
        }

        void union(int i, int j, boolean pretend) {
            int left = compressed_find(i);
            int right = compressed_find(j);
            //System.out.printf("[u] left(%d) father: %d\n", i, left);
            //System.out.printf("[u] right(%d) father: %d\n", j, right);

            if (left == right) {
                // connected

                //System.out.println("[u] cycle detected.");
                return;
            }

            int newSize = size[left] + size[right];
            //System.out.printf("[u] size of left(%d): %d; size of right(%d): %d\n", left, size[left], right ,size[right]);

            largest = newSize > largest ? newSize : largest;
            //System.out.printf("[u] union {%d, %d}, now largest: %d\n", i, j, largest);

            if (i < j)
                root[j] = left;
            else
                root[i] = right;
            size[left] = size[right] = newSize;
            cnt++;
            //System.out.printf("[u][after] left(%d) father: %d\n", i, compressed_find(i));
            //System.out.printf("[u][after] right(%d) father: %d\n", j, compressed_find(j));
        }

        int compressed_find(int i) {
            int father = root[i];
            while (father != root[father]) {
                father = root[father];
            }

            int tmp = i;
            while (tmp != father) {
                // NOTE: bug at the beginning, cost my 30 min to find this out.
                int t = root[tmp];
                root[tmp] = father;
                //System.out.printf("[compressed_find] change %d's father from %d to %d\n", tmp, t, father);
                tmp = t;
            }

            return father;
        }
    }

    public static void main(String[] args) {
        // int[][] grid = { { 0, 1 }, { 1, 0 }, };
        // int[][] grid = { { 1, 0 }, { 0, 1 }, };
        // int[][] grid = { { 1, 1 }, { 1, 1 }, };
        int[][] grid = { { 1, 1 }, { 1, 0 }, };

        Solution s = new Solution();
        System.out.printf("Result: %d\n", s.largestIsland(grid));
    }
}
