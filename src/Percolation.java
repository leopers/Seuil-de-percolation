public class Percolation {
    static int size = 10;
    static int length = size * size;

    static boolean[] grid = new boolean[length];

    static void init() {
        for (int i = 0; i < length; i++) {
            grid[i] = false;
        }
    }

    static void print() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print(grid[i * size + j] ? "*" : "-");
            }
            System.out.println();
        }
    }

    static int randomShadow() {
        int indice = (int) (length * Math.random());

        while (grid[indice]) {
            indice = (int) (length * Math.random());
        }

        grid[indice] = true;
        return indice;
    }

    static boolean detectPath(boolean[] seen, int n, boolean up) {

        if (n < 0 || n >= length)
            return false;

        if (n / size == 0 && up && grid[n]) {
            seen[n] = true;
            return true;
        }
        if (n / size == size - 1 && !up && grid[n]) {
            seen[n] = true;
            return true;
        }

        if (!seen[n] && grid[n]) {
            seen[n] = true;

            if (n % size == 0) {
                return detectPath(seen, n - size, up) || detectPath(seen, n + size, up) || detectPath(seen, n + 1, up);
            }

            if (n % size == size - 1) {
                return detectPath(seen, n - size, up) || detectPath(seen, n + size, up) || detectPath(seen, n - 1, up);
            }

            return detectPath(seen, n - size, up) || detectPath(seen, n + size, up) || detectPath(seen, n - 1, up)
                    || detectPath(seen, n + 1, up);

        } else
            return false;
    }

    static boolean isNaivePercolation(int n) {
        boolean[] seen1 = new boolean[length];
        boolean[] seen2 = new boolean[length];
        return detectPath(seen1, n, false) && detectPath(seen2, n, true);
    }

    static boolean isPercolation(int n) {
        return isNaivePercolation(n);
    }

    static double percolation() {
        double proportion = 0;

        int hazard = 0;
        do {
            hazard = randomShadow();
            proportion++;
        } while (!isPercolation(hazard));

        return proportion / length;
    }

    static double monteCarlo(int n) {
        double est = 0;

        for (int i = 0; i < n; ++i) {
            init();
            est += percolation();
        }

        return est / n;
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        System.out.printf("Seuil de percolation : %.6f\n", monteCarlo(Integer.parseInt(args[0])));
        long duration = System.currentTimeMillis() - start;

        System.out.printf("Temps d'éxecution : %d\n", duration);
    }
}
