import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolGraph;
import java.util.ArrayList;
import java.util.List;

public class DegreesOfSeparationBFS {

    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        String source = args[2];

        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        Graph G = sg.graph();
        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }

        // run breadth-first search from s
        int start = sg.indexOf(source);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, start);
        int des = sg.indexOf("Bacon, Kevin");
        List<Integer> pathList = new ArrayList<>();
        for (Integer e : bfs.pathTo(des)) {
            pathList.add(e);
        }
        StdOut.println(String.format("%s has a Bacon number of %d", source, pathList.size() / 2));
        for (int i = 2; i < pathList.size(); i += 2) {
            StdOut.println(String
                .format("%s was in \"%s\" with %s", sg.nameOf(pathList.get(i - 2)),
                    sg.nameOf(pathList.get(i - 1)), sg.nameOf(pathList.get(i))));
        }
    }
}
