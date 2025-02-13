package pl.krzysztofdebski.task10;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;
import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.ints;

public class Task10 {

    public static void main(String[] args) throws IOException {
        long result = 0;
        char[][] map = Utils.readTwoDimensionsCharArray(Path.of("src/main/resources/task10/10-task.input"));
        Coord coord = Coord.find(map, 'S');
        Direction direction = Direction.S;
        coord = coord.relative(direction);
        result++;

        while (coord.value(map) != 'S') {
            coord = coord.relative(direction);
            result++;
            switch (coord.value(map)) {
                case '|', '-' -> {}
                case 'L' -> direction = Direction.S.equals(direction) ? Direction.E : Direction.N;
                case 'J' -> direction = Direction.S.equals(direction) ? Direction.W : Direction.N;
                case '7' -> direction = Direction.N.equals(direction) ? Direction.W : Direction.S;
                case 'F' -> direction = Direction.N.equals(direction) ? Direction.E : Direction.S;
            }
        }

        System.out.println(result / 2);
    }
}
