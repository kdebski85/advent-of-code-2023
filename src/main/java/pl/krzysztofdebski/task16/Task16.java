package pl.krzysztofdebski.task16;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Deque;
import java.util.LinkedList;

import static pl.krzysztofdebski.utils.Direction.CARDINAL_DIRECTIONS;
import static pl.krzysztofdebski.utils.Direction.left90;
import static pl.krzysztofdebski.utils.Direction.right90;
import static pl.krzysztofdebski.utils.Utils.inBounds;
import static pl.krzysztofdebski.utils.Utils.readTwoDimensionsCharArray;

public class Task16 {

    record Move(Coord coord, Direction direction) {

    }

    public static void main(String[] args) throws IOException {
        char[][] map = readTwoDimensionsCharArray(Path.of("src/main/resources/task16/16-task.input"));
        boolean[][][] visited = new boolean[map.length][map[0].length][4];

        Deque<Move> queue = new LinkedList<>();
        queue.add(new Move(new Coord(0,0), Direction.E));

        while (!queue.isEmpty()) {
            Move move = queue.poll();
            Direction direction = move.direction;
            Coord coord = move.coord;
            if (inBounds(coord, map) && !visited[coord.y()][coord.x()][CARDINAL_DIRECTIONS.indexOf(direction)]) {
                visited[coord.y()][coord.x()][CARDINAL_DIRECTIONS.indexOf(direction)] = true;
                switch (coord.value(map)) {
                    case '.' -> queue.add(new Move(coord.relative(direction), direction));
                    case '/' -> {
                        if (direction.equals(Direction.N) || direction.equals(Direction.S)) {
                            queue.add(new Move(coord.relative(right90(direction)), right90(direction)));
                        } else {
                            queue.add(new Move(coord.relative(left90(direction)), left90(direction)));
                       }
                    }
                    case '\\' -> {
                        if (direction.equals(Direction.N) || direction.equals(Direction.S)) {
                            queue.add(new Move(coord.relative(left90(direction)), left90(direction)));
                        } else {
                            queue.add(new Move(coord.relative(right90(direction)), right90(direction)));
                        }
                    }
                    case '-' -> {
                        if (direction.equals(Direction.N) || direction.equals(Direction.S)) {
                            queue.add(new Move(coord.relative(Direction.E), Direction.E));
                            queue.add(new Move(coord.relative(Direction.W), Direction.W));
                        } else {
                            queue.add(new Move(coord.relative(direction), direction));
                        }
                    }
                    case '|' -> {
                        if (direction.equals(Direction.E) || direction.equals(Direction.W)) {
                            queue.add(new Move(coord.relative(Direction.N), Direction.N));
                            queue.add(new Move(coord.relative(Direction.S), Direction.S));
                        } else {
                            queue.add(new Move(coord.relative(direction), direction));
                        }
                    }
                }
            }
        }

        int result = 0;

        for (boolean[][] visitedRow : visited) {
            for (int x = 0; x < visited[0].length; x++) {
                for (int i = 0; i < 4; i++) {
                    if (visitedRow[x][i]) {
                        result++;
                        break;
                    }
                }
            }
        }

        System.out.println(result);
    }
}
