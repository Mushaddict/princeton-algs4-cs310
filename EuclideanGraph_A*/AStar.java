import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import java.io.FileNotFoundException;

public class AStar {

    private static double INFINITY = Double.MAX_VALUE;
    private static double EPSILON = 0.000001;

    private EuclideanGraph G;
    private double[] dist;
    private int[] pred;
    private int flag;

    public AStar(EuclideanGraph G, int flag) {
        this.G = G;
        this.flag = flag;
    }

    // return shortest path distance from s to d
    public double distance(int s, int d) {
        astar(s, d);
        return dist[d];
    }

    // print shortest path from s to d 
    // interchange s and d to print in right order
    public void showPath(int d, int s) {
        astar(s, d);
        if (pred[d] == -1) {
            System.out.println(d + " is unreachable from " + s);
            return;
        }
        for (int v = d; v != s; v = pred[v]) {
            System.out.print(v + "-");
        }
        System.out.println(s);
    }

    private double getHeuristicDis(int v, int d) {
        if (flag == 0) {
            return 0;
        }
        return G.distance(v, d);
    }

    // AStar's algorithm to find shortest path from s to d
    private void astar(int s, int d) {
        int V = G.V();

        // initialize
        dist = new double[V];
        pred = new int[V];
        for (int v = 0; v < V; v++) {
            dist[v] = INFINITY;
        }
        for (int v = 0; v < V; v++) {
            pred[v] = -1;
        }

        // priority queue
        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(V);
        for (int v = 0; v < V; v++) {
            pq.insert(v, dist[v]);
        }

        // set distance of source
        dist[s] = 0;
        pred[s] = s;
        pq.changeKey(s, G.distance(s, d));

        // run AStar's algorithm
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            if (v == d) {
                break;
            }
            // System.out.println("process " + v + " " + dist[v]);

            // v not reachable from s so stop
            if (pred[v] == -1) {
                break;
            }

            // scan through all nodes w adjacent to v
            IntIterator i = G.neighbors(v);
            while (i.hasNext()) {
                int w = i.next();
                if (dist[v] + G.distance(v, w) < dist[w] - EPSILON) {
                    dist[w] = dist[v] + G.distance(v, w);
                    pq.changeKey(w, dist[w] + getHeuristicDis(w, d));
                    pred[w] = v;
                    // System.out.println("    lower " + w + " to " + dist[w]);
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        EuclideanGraph graph = new EuclideanGraph(new In(args[0]));

        int flag = Integer.parseInt(args[2]);
        AStar astar = new AStar(graph, flag);

        In in = new In(args[1]);
        while (!in.isEmpty()) {
            String startStr = in.readString();
            if (startStr.equals("\n")) {
                break;
            }
            int s = Integer.parseInt(startStr);
            int d = Integer.parseInt(in.readString());
            astar.showPath(s, d);
        }
        long endtTime = System.currentTimeMillis();
        System.out.printf("total elapsed time is %d ms\n", endtTime - startTime);
    }
}
