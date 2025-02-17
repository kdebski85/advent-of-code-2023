package pl.krzysztofdebski.task14;

import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

public class Task14 {

    public static void main(String[] args) throws IOException {
        char[][] chars = Utils.readTwoDimensionsCharArray(Path.of("src/main/resources/task14/14-task.input"));

        for (int y = 1; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                if (chars[y][x] == 'O') {
                    int y1 = y;
                    while (y1 > 0 && chars[y1 - 1][x] == '.') {
                        y1--;
                    }
                    if (y1 != y) {
                        chars[y1][x] = 'O';
                        chars[y][x] = '.';
                    }
                }
            }
        }

        long result = 0;

        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                if (chars[y][x] == 'O') {
                    result += chars.length - y;
                }
            }
        }

        System.out.println(result);
    }
}
