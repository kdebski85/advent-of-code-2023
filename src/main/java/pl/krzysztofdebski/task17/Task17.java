package pl.krzysztofdebski.task17;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;
import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static pl.krzysztofdebski.utils.Utils.inBounds;
import static pl.krzysztofdebski.utils.Utils.readOneDigitTwoDimensionsArray;

public class Task17 {

    record Vertex(Coord coord, Direction direction) {

    }

    public static void main(String[] args) throws IOException {
        int[][] weights = readOneDigitTwoDimensionsArray(Path.of("src/main/resources/task17/17-task.input"));

        GraphBuilder<Vertex, DefaultEdge, ? extends DefaultDirectedWeightedGraph<Vertex, DefaultEdge>> builder
            = DefaultDirectedWeightedGraph.createBuilder(DefaultEdge.class);

        for (int y = 0; y < weights.length; y++) {
            for (int x = 0; x < weights[y].length; x++) {
                Coord coord = new Coord(y, x);
                for (int i = 1; i <= 3; i++) {
                    for (Direction cardinalDirection : Direction.CARDINAL_DIRECTIONS) {
                        for (Direction relative : List.of(Direction.left90(cardinalDirection), Direction.right90(cardinalDirection))) {
                            Coord target = coord.relative(relative, i);
                            if (inBounds(target, weights)) {
                                double weight = 0;
                                for (int j = 1; j <= i; j++) {
                                    weight += coord.relative(relative, j).value(weights);
                                }

                                builder.addEdge(new Vertex(coord, cardinalDirection), new Vertex(target, relative), weight);
                            }
                        }
                    }
                }
            }
        }

        Vertex source = new Vertex(new Coord(-1, -1), Direction.N);
        Vertex target = new Vertex(new Coord(-1, -1), Direction.S);

        int DUMMY_EDGE_WEIGHT = 1;

        for (Direction cardinalDirection : Direction.CARDINAL_DIRECTIONS) {
            builder.addEdge(source, new Vertex(new Coord(0, -0), cardinalDirection), DUMMY_EDGE_WEIGHT);
            builder.addEdge(new Vertex(new Coord(weights[0].length - 1, weights.length - 1), cardinalDirection), target, DUMMY_EDGE_WEIGHT);
        }

        GraphPath<Vertex, DefaultEdge> pathBetween = DijkstraShortestPath.findPathBetween(builder.build(), source, target);

        System.out.println(pathBetween.getWeight() - 2 * DUMMY_EDGE_WEIGHT);
    }
}
