package pl.krzysztofdebski.task15;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Task15 {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(Path.of("src/main/resources/task15/15-task.input"));
        scanner.useDelimiter(",");

        long result = 0;

        while (scanner.hasNext()) {
            String string = scanner.next();
            char[] chars = string.toCharArray();
            int hash = 0;
            for (char c : chars) {
                if (c != '\n') {
                    hash += c;
                    hash *= 17;
                    hash &= 255;
                }
            }
            result += hash;
        }

        System.out.println(result);
    }
}
