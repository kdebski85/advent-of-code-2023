package pl.krzysztofdebski.task13;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLinesToCharArray;

public class Task13 {

    public static void main(String[] args) throws IOException {
        List<char[][]> lists = partitionByNewLinesToCharArray(readAllLines(Path.of("src/main/resources/task13/13-task.input")));

        long result = 0;

        for (char[][] map : lists) {
            rowCheck:
            for (int y = 0; y < map.length - 1; y++) {
                int rowsToCheck = Math.min(y + 1, map.length - y - 1);

                while (rowsToCheck > 0) {
                    int y1 = y + rowsToCheck;
                    int y2 = y - rowsToCheck + 1;

                    for (int x = 0; x < map[0].length; x++) {
                        if (map[y1][x] != map[y2][x]) {
                            continue rowCheck;
                        }
                    }

                    rowsToCheck--;
                }

                result += 100L * (y + 1);
            }

            collCheck:
            for (int x = 0; x< map[0].length - 1; x++) {
                int collsToCheck = Math.min(x + 1, map[0].length - x - 1);

                while (collsToCheck > 0) {
                    int x1 = x + collsToCheck;
                    int x2 = x - collsToCheck + 1;

                    for (char[] chars : map) {
                        if (chars[x1] != chars[x2]) {
                            continue collCheck;
                        }
                    }

                    collsToCheck--;
                }

                result += (x + 1);
            }


        }

        System.out.println(result);
    }
}
