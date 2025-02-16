package pl.krzysztofdebski.task15;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task15b {

    record Record(String label, int value) {

    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(Path.of("src/main/resources/task15/15-task.input"));
        scanner.useDelimiter(",");

        @SuppressWarnings("unchecked")
        List<Record>[] hashmap = new List[256];
        for (int i = 0; i < hashmap.length; i++) {
            hashmap[i] = new ArrayList<>();
        }

        while (scanner.hasNext()) {
            String string = scanner.next().trim();

            if (string.contains("-")) {
                String label = string.substring(0, string.length() - 1);
                int hash = hash(label);
                hashmap[hash].removeIf(record -> label.equals(record.label));
            } else {
                String[] split = string.split("=");
                String label = split[0];
                int value = Integer.parseInt(split[1]);
                int hash = hash(label);
                List<Record> records = hashmap[hash];
                int index = -1;
                for (int i = 0; i < records.size(); i++) {
                    if (records.get(i).label.equals(label)) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    records.set(index, new Record(label, value));
                } else {
                    records.add(new Record(label, value));
                }
            }
        }

        long result = 0;

        for (int i = 0; i < hashmap.length; i++) {
            List<Record> records = hashmap[i];
            for (int j = 0; j < records.size(); j++) {
                result += (long) (i + 1) * (j + 1) * records.get(j).value;
            }
        }

        System.out.println(result);
    }

    private static int hash(String string) {
        char[] chars = string.toCharArray();
        int hash = 0;
        for (char c : chars) {
            if (c != '\n') {
                hash += c;
                hash *= 17;
                hash &= 255;
            }
        }
        return hash;
    }
}
