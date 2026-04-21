public class UnionFind {
  static int[] equiv;

  static void init(int len) {
    equiv = new int[len];

    for (int i = 0; i < len; ++i)
      equiv[i] = i;
  }

  static int naiveFind(int x) {
    return equiv[x];
  }

  static int naiveUnion(int x, int y) {
    int rx = equiv[x];

    for (int i = 0; i < equiv.length; ++i)
      if (equiv[i] == rx)
        equiv[i] = equiv[y];

    return equiv[y];
  }

  static int find(int n) {
    return naiveFind(n);
  }

  static int union(int x, int y) {
    return naiveUnion(x, y);
  }
}
