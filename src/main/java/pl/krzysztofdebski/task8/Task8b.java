package pl.krzysztofdebski.task8;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphBuilder;
import org.jgrapht.traverse.AbstractGraphIterator;
import pl.krzysztofdebski.utils.MathUtils;
import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task8b {

    private static final Pattern pattern = Pattern.compile("([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)");

    public static void main(String[] args) throws IOException {
        List<List<String>> lines = Utils.partitionByNewLines(readAllLines(Path.of("src/main/resources/task8/8-task.input")));

        GraphBuilder<String, Edge, ? extends DefaultDirectedGraph<String, Edge>> builder
            = DefaultDirectedGraph.createBuilder(Edge.class);

        List<String> dirs = lines.get(0);

        for (String line : lines.get(1)) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }
            String source = matcher.group(1);
            String leftTarget = matcher.group(2);
            String rightTarget = matcher.group(3);
            if (leftTarget.equals(rightTarget)) {
                builder.addEdge(source, leftTarget, new Edge(true, true));
            } else {
                builder.addEdge(source, leftTarget, new Edge(true, false));
                builder.addEdge(source, rightTarget, new Edge(false, true));
            }
        }

        DefaultDirectedGraph<String, Edge> graph = builder.build();

        long result = graph.vertexSet().stream()
                           .filter(v -> v.endsWith("A"))
                           .map(v -> {
                               int count = 0;
                               GraphIterator graphIterator = new GraphIterator(graph, v, dirs.getFirst());
                               while (graphIterator.hasNext()) {
                                   graphIterator.next();
                                   count++;
                               }
                               return (long) count;
                           })
                           .reduce(MathUtils::lcm)
                           .orElseThrow();

        System.out.println(result);
    }

    private static class Edge extends DefaultEdge {

        final boolean left;
        final boolean right;

        public Edge(final boolean left, final boolean right) {
            this.left = left;
            this.right = right;
        }
    }

    private static class GraphIterator extends AbstractGraphIterator<String, Edge> {

        private String vertex;
        private final String dirs;
        private int index = 0;

        public GraphIterator(Graph<String, Edge> g, String startingVertex, String dirs) {
            super(g);
            this.dirs = dirs;
            this.vertex = startingVertex;
        }


        @Override
        public boolean hasNext() {
            return !vertex.endsWith("Z");
        }

        @Override
        public String next() {
            boolean left = dirs.charAt(index) == 'L';
            index = (index + 1) % dirs.length();

            vertex = graph.getEdgeTarget(
                graph.outgoingEdgesOf(vertex)
                     .stream()
                     .filter(e -> left ? e.left : e.right)
                     .findFirst()
                     .orElseThrow());
            return vertex;
        }
    }


}
