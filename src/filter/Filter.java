package filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The filter class including all the filter operations. Those operation help to calculate the color
 * matrix mapping.
 */
public class Filter implements ImageFilter {

  /**
   * The built-in blur filter, set as final private static to avoid modification and outer change.
   */
  final private static Double[][] blurFilter = new Double[][]{{0.0625, 0.125, 0.0625},
      {0.125, 0.25, 0.125},
      {0.0625, 0.125, 0.0625}};

  /**
   * The built-in sharp filter, set as final private static to avoid modification and outer change.
   */
  final private static Double[][] sharpFilter = new Double[][]{
      {-0.125, -0.125, -0.125, -0.125, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125}, {-0.125, 0.25, 1.0, 0.25, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125}, {-0.125, -0.125, -0.125, -0.125, -0.125}};

  /**
   * Helper for filter method, receive the matrix of the filter and the list of r, g, and b, return
   * the list of converted rgb values.
   *
   * @param filter filter matrix
   * @param r      red matrix
   * @param g      greeen matrix
   * @param b      blue matrix
   * @return a list of color matrix
   */
  private List<int[][]> filterHelper(Double[][] filter, int[][] r, int[][] g, int[][] b) {
    int height = r.length;
    int width = r[0].length;
    int[][] rCache = new int[height][width];
    int[][] gCache = new int[height][width];
    int[][] bCache = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int k = 0; k < width; k++) {
        rCache[i][k] = filtering(filter, r, i, k);
        gCache[i][k] = filtering(filter, g, i, k);
        bCache[i][k] = filtering(filter, b, i, k);
      }
    }
    return new ArrayList<>(Arrays.asList(rCache, gCache, bCache));
  }

  /**
   * The filter method receive the given filter, the list of rgb values, the x and y coordinate
   * return the supposed filtered value.
   *
   * @param op the given filter
   * @param ch the list of rgb values
   * @param x  the x index of pixel
   * @param y  the y index of pixel
   * @return the result of the filter
   */
  private int filtering(Double[][] op, int[][] ch, int x, int y) {
    int central = op.length / 2;
    int count = 0;
    int startX = Math.max(0, x - op.length / 2);
    int endX = Math.min(x + op.length / 2, ch.length - 1);
    int startY = Math.max(0, y - op.length / 2);
    int endY = Math.min(y + op.length / 2, ch[0].length - 1);
    for (int i = startX; i <= endX; i++) {
      for (int k = startY; k <= endY; k++) {
        count += op[i + central - x][k + central - y] * ch[i][k];
      }
    }
    return count;
  }


  @Override
  public List<int[][]> blur(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException {
    if (r == null || g == null || b == null) {
      throw new IllegalArgumentException("Input can not be empty");
    }
    return this.filterHelper(blurFilter, r, g, b);
  }


  @Override
  public List<int[][]> sharp(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException {
    if (r == null || g == null || b == null) {
      throw new IllegalArgumentException("Input can not be empty");
    }
    return this.filterHelper(sharpFilter, r, g, b);
  }

  @Override
  public List<int[][]> mosaic(int[][] r, int[][] g, int[][] b, int seed) {
    ArrayList<int[]> seedList = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < seed; i++) {
      seedList.add(new int[]{random.nextInt(r.length), random.nextInt(r[0].length)});
    }

    int[][] sortedSeedPixelList = listNearestDistanceRecorder(r, seedList);

    int[][] evenListR = getEvenedList(r, sortedSeedPixelList, seed);
    int[][] evenListG = getEvenedList(g, sortedSeedPixelList, seed);
    int[][] evenListB = getEvenedList(b, sortedSeedPixelList, seed);

    return new ArrayList<>(Arrays.asList(evenListR, evenListG, evenListB));
  }

  private int[][] listNearestDistanceRecorder(int[][] r, ArrayList<int[]> seedlist) {
    int[][] sortedSeedList = new int[r.length][r[0].length];
    for (int i = 0; i < r.length; i++) {
      for (int j = 0; j < r[0].length; j++) {
        int currK = 0;
        double distanceMin = Double.MAX_VALUE;
        for (int k = 0; k < seedlist.size(); k++) {
          double distanceTemp = distance(seedlist.get(k)[1], i, seedlist.get(k)[0],
              j);
          if (Double.compare(distanceMin, distanceTemp) > 0) {
            distanceMin = distanceTemp;
            currK = k;
          }
        }
        sortedSeedList[i][j] = currK;
      }
    }
    return sortedSeedList;
  }

  private int[][] getEvenedList(int[][] r, int[][] sortedSeedList, int seed) {
    //even?
    int[] sumRecorder = new int[seed];
    int[] evenRecorder = new int[seed];
    int[] evenCount = new int[seed];
    int[][] evenedList = new int[r.length][r[0].length];
    //get the sum
    for (int i = 0; i < sortedSeedList.length; i++) {
      for (int j = 0; j < sortedSeedList[0].length; j++) {
        sumRecorder[sortedSeedList[i][j]] += r[i][j];
        evenCount[sortedSeedList[i][j]] += 1;
      }
    }
    //get the even
    for (int i = 0; i < seed; i++) {
      evenRecorder[i] = evenCount[i] == 0 ? 0 : sumRecorder[i] / evenCount[i];
    }
    // put the even into 2d list
    for (int i = 0; i < sortedSeedList.length; i++) {
      for (int j = 0; j < sortedSeedList[0].length; j++) {
        evenedList[i][j] = evenRecorder[sortedSeedList[i][j]];
      }
    }
    return evenedList;
  }

  private double distance(int x1, int x2, int y1, int y2) {
    return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
  }

}
