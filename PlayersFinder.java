package com.mycompany.findplayers;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.awt.Point;

/**
 *
 * @author Mohamed
 */

public class PlayersFinder implements IPlayersFinder {
    public void DFSrec(String[] photo, int s, int r, int team, boolean[][] visited, Vector<Point> idx) {
        if (s < 0 || s >= photo.length || r < 0 || r >= photo[0].length() || visited[s][r]
                || photo[s].charAt(r) - '0' != team) {
            return;
        } else {
            idx.add(new Point(r, s));
            visited[s][r] = true;
            DFSrec(photo, s - 1, r, team, visited, idx);//check for 4d for the current element
            DFSrec(photo, s + 1, r, team, visited, idx);
            DFSrec(photo, s, r - 1, team, visited, idx);
            DFSrec(photo, s, r + 1, team, visited, idx);
        }
    }

    public java.awt.Point[] findPlayers(String[] photo, int team, int threshold) {
        int rows = photo.length;
        int cols = photo[0].length();
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visited[i][j] = false;
            }
        }

        Vector<Point> centers = new Vector<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < photo[0].length(); j++) {
                Vector<Point> indicies = new Vector<>();
                if (photo[i].charAt(j) - '0' == team && visited[i][j] == false) {
                    DFSrec(photo, i, j, team, visited, indicies);
                }
                if (indicies.size() * 4 >= threshold) {
                    int x = 0;
                    int y = 0;
                    int maxX = Integer.MIN_VALUE;
                    int maxY = Integer.MIN_VALUE;
                    int minX = Integer.MAX_VALUE;
                    int minY = Integer.MAX_VALUE;
                    for (Point p : indicies) {
                        if (p.x > maxX) {
                            maxX = p.x;
                        }
                        if (p.x < minX) {
                            minX = p.x;
                        }
                        if (p.y > maxY) {
                            maxY = p.y;
                        }
                        if (p.y < minY) {
                            minY = p.y;
                        }
                    }
                    x = minX + maxX + 1;
                    y = minY + maxY + 1;
                    centers.add(new Point(x, y));

                }

            }
        }
        Collections.sort(centers, Comparator.comparingInt((Point p) -> p.x).thenComparingInt((Point p) -> p.y));
        return centers.toArray(new Point[0]);
    }

    public static void main(String[] args) {
        // System.out.println("Hello World!");
        Scanner scanner = new Scanner(System.in);

        String[] sizeInput = scanner.nextLine().split(",");
        int rows = Integer.parseInt(sizeInput[0].trim());
        int cols = Integer.parseInt(sizeInput[1].trim());
        String[] photo = new String[rows];
        for (int i = 0; i < rows; i++) {
            if (!scanner.hasNextLine()) {

                return;
            }
            photo[i] = scanner.nextLine();
        }

        int team = Integer.parseInt(scanner.nextLine());
        int threshold = Integer.parseInt(scanner.nextLine());

      
        PlayersFinder finder = new PlayersFinder();
        Point[] data = finder.findPlayers(photo, team, threshold);
          //modify the format
        String result = Arrays.toString(
                Arrays.stream(data)
                        .map(p -> String.format("(%d, %d)", p.x, p.y))
                        .toArray(String[]::new));
        System.out.println(result);
        scanner.close();

    }
}
