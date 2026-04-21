public class PercolationUF {
  static int size = 10;
  static int length = size * size;

  static boolean[] grid = new boolean[length];

  static void init() {
    for (int i = 0; i < length; i++) {
      grid[i] = false;
    }

    UnionFind.init(length);
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
    int index = (int) (length * Math.random());

    while (grid[index]) {
      index = (int) (length * Math.random());
    }

    grid[index] = true;
    propagateUnion(index);

    return index;
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

    if (n < length && n >= 0 && !seen[n] && grid[n]) {
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

  static boolean isFastPercolation(int n) {
    if (!grid[n])
      return false;

    boolean up = false;
    boolean down = false;

    for (int i = 0; i < size; ++i) {
      if (grid[i] && UnionFind.find(i) == UnionFind.find(n)) {
        up = true;
        break;
      }
    }

    for (int i = length - size; i < length; ++i) {
      if (grid[i] && UnionFind.find(i) == UnionFind.find(n)) {
        down = true;
        break;
      }
    }

    return up && down;
  }

  static boolean isPercolation(int n) {
    return isFastPercolation(n);
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

  static void propagateUnion(int x) {
    int[] neighbours = { x + size, x - size, x + 1, x - 1 };
    int[] neighbours_left = { x + size, x - size, x + 1 };
    int[] neighbours_right = { x + size, x - size, x - 1 };
    int[] neighbours_up = { x + size, x + 1, x - 1 };
    int[] neighbours_down = { x - size, x + 1, x - 1 };
    int[] neighbours_zero = { x + size, x + 1 };
    int[] neighbours_size = { x + size, x - 1 };
    int[] neighbours_length = { x - size, x - 1 };
    int[] neighbours_SW = { x - size, x + 1 };

    if (x == 0) {
      for (int k : neighbours_zero)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    if (x == size - 1) {
      for (int k : neighbours_size)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    if (x == length - size) {
      for (int k : neighbours_SW)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    if (x == length) {
      for (int k : neighbours_length)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    if (x % size == 0) {
      for (int k : neighbours_left)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    if (x % size == size - 1) {
      for (int k : neighbours_right)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    if (x / size == 0) {
      for (int k : neighbours_up)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    if (x / size == size - 1) {
      for (int k : neighbours_down)
        if (grid[k])
          UnionFind.union(x, k);
      return;
    }

    for (int k : neighbours)
      if (grid[k])
        UnionFind.union(x, k);
    return;
  }

  public static void main(String[] args) {

    long start = System.currentTimeMillis();
    System.out.printf("Seuil de percolation : %.6f\n", monteCarlo(Integer.parseInt(args[0])));
    long duration = System.currentTimeMillis() - start;

    System.out.printf("Temps d'éxecution : %d\n", duration);
  }
}
