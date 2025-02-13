package pl.krzysztofdebski.task10;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;
import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;

public class Task10b {

    public static void main(String[] args) throws IOException {
        long result = 0;
        char[][] map = Utils.readTwoDimensionsCharArray(Path.of("src/main/resources/task10/10-sample.input"));
        Coord coord = Coord.find(map, 'S');
        boolean[][] visited = new boolean[map.length * 3][map[0].length * 3];
        visited[3 * coord.y()][3 * coord.x()] = true;
        visited[3 * coord.y() + 1][3 * coord.x()] = true;
        if (coord.relative(Direction.E).value(map) == 'J') {
            visited[3 * coord.y()][3 * coord.x() + 1] = true;
        } else {
            visited[3 * coord.y()][3 * coord.x() - 1] = true;
        }

        Direction direction = Direction.S;
        coord = coord.relative(direction);
        //south is always '|'
        visited[3 * coord.y() - 1][3 * coord.x()] = true;
        visited[3 * coord.y()][3 * coord.x()] = true;
        visited[3 * coord.y() + 1][3 * coord.x()] = true;

        while (coord.value(map) != 'S') {
            coord = coord.relative(direction);
            int yScaled = 3 * coord.y();
            int xScaled = 3 * coord.x();

            visited[yScaled][xScaled] = true;

            switch (coord.value(map)) {
                case '|' -> {
                    visited[yScaled - 1][xScaled] = true;
                    visited[yScaled + 1][xScaled] = true;
                }
                case '-' -> {
                    visited[yScaled][xScaled - 1] = true;
                    visited[yScaled][xScaled + 1] = true;
                }
                case 'L' -> {
                    visited[yScaled][xScaled + 1] = true;
                    visited[yScaled - 1][xScaled] = true;
                    direction = Direction.S.equals(direction) ? Direction.E : Direction.N;
                }
                case 'J' -> {
                    visited[yScaled][xScaled - 1] = true;
                    visited[yScaled - 1][xScaled] = true;
                    direction = Direction.S.equals(direction) ? Direction.W : Direction.N;
                }
                case '7' -> {
                    visited[yScaled][xScaled - 1] = true;
                    visited[yScaled + 1][xScaled] = true;
                    direction = Direction.N.equals(direction) ? Direction.W : Direction.S;
                }
                case 'F' -> {
                    visited[yScaled][xScaled + 1] = true;
                    visited[yScaled + 1][xScaled] = true;
                    direction = Direction.N.equals(direction) ? Direction.E : Direction.S;
                }
            }
        }

        Deque<Coord> toCheck = new ArrayDeque<>();

        for (int i = 0; i < visited.length; i++) {
            toCheck.add(new Coord(i, 0));
            toCheck.add(new Coord(i, visited[i].length - 1));
        }

        for (int i = 1; i < visited[0].length - 1; i++) {
            toCheck.add(new Coord(0, i));
            toCheck.add(new Coord(visited.length - 1, i));
        }

        while (!toCheck.isEmpty()) {
            Coord coordToCheck = toCheck.pollFirst();
            if (!coordToCheck.value(visited)) {
                visited[coordToCheck.y()][coordToCheck.x()] = true;
                for (Direction cardinalDirection : Direction.CARDINAL_DIRECTIONS) {
                    Coord relative = coordToCheck.relative(cardinalDirection);
                    if (relative.valueIfInBounds(visited) == Boolean.FALSE && !toCheck.contains(relative)) {
                        toCheck.add(relative);
                    }
                }
            }
        }

        for (int y = 0; y < visited.length; y += 3) {
            main:
            for (int x = 0; x < visited[0].length; x += 3) {
                for (int y1 = y; y1 < y + 3; y1++) {
                    for (int x1 = x; x1 < x + 3; x1++) {
                        if (visited[y1][x1]) {
                            continue main;
                        }
                    }
                }
                result++;
            }
        }

        System.out.println(result);
    }
}
