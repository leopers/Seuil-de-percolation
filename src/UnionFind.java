public class UnionFind {
  static int[] equiv;
  static int[] height;

  static void init(int len) {
    equiv = new int[len];
    height = new int[len];

    for (int i = 0; i < len; ++i) {
      equiv[i] = i;
      height[i] = 0;
    }
  }

  static int naiveFind(int x) {
    return equiv[x];
  }

  static int fastFind(int x) {
    while (x != equiv[x])
      x = equiv[x];
    return x;
  }

  static int logFind(int x) {
    while (equiv[x] != x) {
      equiv[x] = equiv[equiv[x]];
      x = equiv[x];
    }

    return x;
  }

  static int naiveUnion(int x, int y) {
    int rx = equiv[x];

    for (int i = 0; i < equiv.length; ++i)
      if (equiv[i] == rx)
        equiv[i] = equiv[y];

    return equiv[y];
  }

  static int fastUnion(int x, int y) {
    x = find(x);
    y = find(y);
    equiv[x] = y;

    return y;
  }

  static int logUnion(int x, int y) {
    x = find(x);
    y = find(y);

    if (height[x] < height[y]) {
      equiv[x] = y;
      return y;
    }

    else {
      equiv[y] = x;
      if (height[x] == height[y])
        height[x]++;
      return x;
    }
  }

  static int find(int n) {
    return fastFind(n);
  }

  static int union(int x, int y) {
    return fastUnion(x, y);
  }
}
