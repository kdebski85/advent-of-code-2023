package pl.krzysztofdebski.task11;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;
import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Task11 {

    public static void main(String[] args) throws IOException {
        long result = 0;
        char[][] map = Utils.readTwoDimensionsCharArray(Path.of("src/main/resources/task11/11-task.input"));

        int[] emptyRowsBefore = new int[map.length];
        int emptyRows = 0;
        for (int y = 0; y < map.length; y++) {
            boolean empty = true;
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] != '.') {
                    empty = false;
                    break;
                }
            }
            emptyRowsBefore[y] = emptyRows;
            if (empty) {
                emptyRows++;
            }
        }

        int[] emptyCollsBefore = new int[map[0].length];
        int emptyColls = 0;

        for (int x = 0; x < map[0].length; x++) {
            boolean empty = true;
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] != '.') {
                    empty = false;
                    break;
                }
            }
            emptyCollsBefore[x] = emptyColls;
            if (empty) {
                emptyColls++;
            }
        }

        List<Coord> stars = new ArrayList<>();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] != '.') {
                    stars.add(new Coord(y, x));
                }
            }
        }

        for (int i = 0; i < stars.size(); i++) {
            Coord star1 = stars.get(i);
            for (int j = i + 1; j < stars.size(); j++) {
                Coord star2 = stars.get(j);
                Direction distance = star2.distance(star1);
                result += Math.abs(distance.Δc()) +
                    Math.abs(distance.Δr()) +
                    Math.abs(emptyRowsBefore[star1.y()] - emptyRowsBefore[star2.y()]) +
                    Math.abs(emptyCollsBefore[star1.x()] - emptyCollsBefore[star2.x()]);
            }
        }

        System.out.println(result);
    }
}
