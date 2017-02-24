package scenario4;

// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph
import java.util.*;
import java.lang.*;
import java.io.*;

class dijks{
    // Number of vertices in the graph
    int V=0;


    
    ArrayList<ArrayList<ArrayList<Integer>>> outer=new ArrayList<>();
    double graph[][];
    
    public dijks(double graph[][]){
    	this.graph = graph;
    }
    





    // A utility function to find the vertex with minimum distance
// value, from the set of vertices not yet included in shortest
// path tree
    int minDistance(double dist[], Boolean sptSet[])
    {

        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index;

        min_index=-1;
        for (int v = 0; v < V; v++) {
            if (sptSet[v] == false && dist[v] <= min)
            { min = dist[v];
                min_index = v;}
        }
        return min_index;
    }

    // Function to print shortest path from source to j
// using parent array
    void printPath(int parent[], int j, int counter1, int counter2, ArrayList<Integer> inner)
    {
        // Base Case : If j is source
        if (parent[j]==-1)
            return;



        printPath(parent, parent[j], counter1, counter2, inner);

        //outer.get(counter1).get(counter2).add(j);
//        System.out.print(j);
        inner.add(j);


    }

    // A utility function to print the constructed distance
// array
    void printSolution(double dist[], int n, int parent[], int src, int dest, int counter1, int counter2, ArrayList<Integer> inner)
    {

        //System.out.print("Vertex\t  Distance\tPath");
        for (int i = 1; i < n; i++)
        {
            if(i==dest) {
//                System.out.print("\n" + src + " -> " + i + "\t\t" + dist[i] + "\t\t" + src);
                printPath(parent, i,counter1, counter2, inner);
            }
        }

    }

    // Funtion that implements Dijkstra's single source shortest path
// algorithm for a graph represented using adjacency matrix
// representation
    ArrayList<Integer>  dijkstra(int src, int dest, int counter1,int counter2, int V, ArrayList<Integer> inner)

    {
        this.V=V;
        double dist[]=new double[V];  // The output array. dist[i] will hold
        // the shortest distance from src to i
        //inner.add(src);
        //middle.add(inner);
        //outer.add(middle);

        inner.add(src);



        // sptSet[i] will true if vertex i is included / in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[]=new Boolean[V];

        // Parent array to store shortest path tree
        int parent[]=new int[V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++)
        {
            parent[src] = -1;
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum distance vertex from the set of
            // vertices not yet processed. u is always equal to src
            // in first iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)

                // Update dist[v] only if is not in sptSet, there is
                // an edge from u to v, and total weight of path from
                // src to v through u is smaller than current value of
                // dist[v]
                if (!sptSet[v] && graph[u][v] < dist[v]&&
                        dist[u] + graph[u][v] < dist[v]&&graph[u][v]!=0)
                {
                    parent[v]  = u;
                    dist[v] = dist[u] + graph[u][v];
                }
        }

       
        //System.out.print("56");
        // print the constructed distance array
        printSolution(dist, V, parent,src, dest, counter1,counter2, inner);
//        for (int x = 1; x<inner.size()-1; x++){
//        	for(int y = 0; y < graph[inner.get(x)].length; y++){
//        	graph[inner.get(x)][y] = 0;
//        	graph[y][inner.get(x)] = 0;
//        	}
//        }
        return inner;
        
//        
//        {0,1,2}
//        {1} graph[1][everything] and
//        graph[vertyhing][1] = 0
// 

    }

    ArrayList<ArrayList<ArrayList<Integer>>> Runthrough(ArrayList<ArrayList<Integer[]>> testArrayouter, int V)
    {
        this.V=V;

        for (int i = 0; i < testArrayouter.size(); i++) {



            ArrayList<ArrayList<Integer>> middle =new ArrayList<>();
           // middle
            for(int j=0; j<testArrayouter.get(i).size();j++)
            {

                //outer.get(i).get(j).add(inner);

                int source=testArrayouter.get(i).get(j)[0];
                int destination = testArrayouter.get(i).get(j)[1];

                ArrayList<Integer> in = new ArrayList<>();
                ArrayList<Integer> inner = dijkstra(source,destination,i, j, V, in);
                if(j > 0 && inner.size()>0){
                	inner.remove(0);
                }
                middle.add(inner);//add to inner
//                System.out.println("middle:" + middle);
//                System.out.print("cycle");





            }

            outer.add(middle);

//            System.out.print("cycle2");


        }
        
        System.out.println("outer"+outer);
		return outer;



    }




}

public class Main {
    // A java program for Dijkstra's single source shortest
// path algorithm. The program is for adjacency matrix
// representation of the graph.





    public static void main(String[] args) {

//        double graph[][] = {{0.0,14.142135623730951,12.806248474865697,12.727922061357855,5.0,7.0,8.06225774829855},
//                {14.142135623730951,0.0,2.0,1.4142135623730951,11.180339887498949,10.44030650891055,9.219544457292887},
//                {12.806248474865697,2.0,0.0,1.4142135623730951,10.44030650891055,8.54400374531753,7.280109889280518},
//                {12.727922061357855,1.4142135623730951,1.4142135623730951,0.0,9.848857801796104,9.219544457292887,8.06225774829855},
//                {5.0,11.180339887498949,10.44030650891055,9.848857801796104,0.0,8.602325267042627,8.94427190999916},
//                {7.0,10.44030650891055,8.54400374531753,9.219544457292887,8.602325267042627,0.0,1.4142135623730951},
//                {8.06225774829855,9.219544457292887,7.280109889280518,8.06225774829855,8.94427190999916,1.4142135623730951,0.0}};
//
//        int V=graph[0].length;
//
//
//        dijks dek = new dijks();
//
//        //dek.V=V;
//
//        //dek.dijkstra(graph, 2,6,counter1, counter2);
//
//        ArrayList<ArrayList<Integer[]>> testArrayouter = new ArrayList<>();
//        ArrayList<Integer[]> testArrayinner = new ArrayList<Integer[]>();
//        testArrayinner.add(new Integer[2]);
//        testArrayinner.add(new Integer[2]);
//        testArrayinner.add(new Integer[2]);
//        testArrayinner.get(0)[0]=0;
//        testArrayinner.get(0)[1]=4;
//        testArrayinner.get(1)[0]=4;
//        testArrayinner.get(1)[1]=6;
//        testArrayinner.get(2)[0]=6;
//        testArrayinner.get(2)[1]=2;
//
//
//        testArrayouter.add(testArrayinner);
//        testArrayouter.add(testArrayinner);
//
//
//
//        dek.Runthrough(graph,testArrayouter, V);
//        System.out.print(dek.outer);
//
//












    }
}