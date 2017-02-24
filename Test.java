package scenario4;

import java.util.*;
import java.lang.*;
import java.io.*;
 
class Test
{
    // Number of vertices in the graph
    private static final int V=15;
 
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(double key[], Boolean mstSet[])
    {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index=-1;
 
        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min)
            {
                min = key[v];
                min_index = v;
            }
 
        return min_index;
    }
    
    
    int[] twoMin(double[] a, int vert, boolean visited[])
    {

        int[] vertexMin = new int[2];
        double min1 = 0;
        double min2 = 0;
        int c1 = 0;
        int c2 = c1+1;
        
        while(min2 == 0 || min1 == 0){
        	if(min1 == 0 || visited[c1] == true){
        	min1 = a[c1];
            vertexMin[0] = c1;
            c1++;
            c2++;
        	}
        	if(min2 == 0 || visited[c2] == true){
            min2 = a[c2];
            vertexMin[1] = c2;
            c2++;
        	}
            
        }
        
        
        
        if (min2 < min1)
        {
            min1 = a[1];
            min2 = a[0];
            vertexMin[0] = 1;
            vertexMin[1] = 0;
        }

        for (int i = 0; i < a.length; i++)
        	if(i != vert || visited[i] == false){
            if (a[i] < min1)
            {
                min2 = min1;
                vertexMin[1] = vertexMin[0];
                min1 = a[i];
                vertexMin[0] = i;
            }
            else if (a[i] < min2)
            {
                min2 = a[i];
                vertexMin[1] = i;
            }
        	}
        return vertexMin;
        
    }
    
 
    // A utility function to print the constructed MST stored in
    // parent[]
    void printMST(int parent[], int n, double graph[][])
    {
        System.out.println("Edge   Weight");
        for (int i = 1; i < V; i++)
            System.out.println((parent[i]+1)+" - "+ (i+1)+"    "+
                               graph[i][parent[i]]);
    }
 
    // Function to construct and print MST for a graph represented
    //  using adjacency matrix representation
    void primMST(double graph[][])
    {
        // Array to store constructed MST
        int parent[] = new int[V];
        
        ArrayList<ArrayList<Integer>> child = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> children = new ArrayList<Integer>();
        children.add(0);
        
        for (int x = 0; x < V; x++){
        	child.add(children);
        }
        System.out.println(child);
 
        // Key values used to pick minimum weight edge in cut
        double key[] = new double [V];
 
        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[V];
 
        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++)
        {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
 
        // Always include first 1st vertex in MST.
        key[0] = 0;     // Make key 0 so that this vertex is
                        // picked as first vertex
        parent[0] = -1; // First node is always root of MST
        
        int parentCount[] = new int[V];

 
        // The MST will have V vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);
 
            // Add the picked vertex to the MST Set
            mstSet[u] = true;
 
            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)
 
                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v]!=0 && mstSet[v] == false &&
                    graph[u][v] <  key[v])
                {
                	
                    if(parentCount[u] == 2){
                    	System.out.println("hey!");
                    	for (int x = 0; x < V; x++){
                    		if(parent[x] == u && graph[u][v] < graph[u][x]){
                    			parent[v] = u;


                    		}              
                    	}
                    }
                    
                    if(parentCount[u] < 2){
                    	
                	parentCount[u]++;
                	
                    parent[v]  = u;

                    
                    if(parent[v] != u){
                        parentCount[parent[v]]--;  
                        
                     }
                    
  
    
                    key[v] = graph[u][v];
                    System.out.println("this is for: " + (u+1) + " " +(v+1));

                    }
                    
                    
                }
        }
        
//        System.out.println(child);
 
        // print the constructed MST
        printMST(parent, V, graph);
    }
 
    public static void main (String[] args)
    {
        /* Let us create the following graph
           2    3
        (0)--(1)--(2)
        |    / \   |
        6| 8/   \5 |7
        | /      \ |
        (3)-------(4)
             9          */
        Test t = new Test();
        double graph[][] = new double[][] {{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,7.0710678118654755,1.4142135623730951,7.0710678118654755},{0.0,0.0,0.0,0.0,3.1622776601683795,1.4142135623730951,1.4142135623730951,1.4142135623730951,1.0,2.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,0.0,5.0,0.0,0.0,0.0,0.0,0.0,0.0,4.123105625617661,1.0,5.0,0.0,0.0,1.0},{0.0,0.0,5.0,0.0,4.0,0.0,0.0,0.0,0.0,3.1622776601683795,1.4142135623730951,5.0990195135927845,0.0,0.0,0.0,0.0},{0.0,3.1622776601683795,0.0,4.0,0.0,4.0,4.47213595499958,0.0,2.23606797749979,1.4142135623730951,3.1622776601683795,0.0,0.0,0.0,0.0,0.0},{0.0,1.4142135623730951,0.0,0.0,4.0,0.0,2.0,2.8284271247461903,2.23606797749979,3.1622776601683795,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,1.4142135623730951,0.0,0.0,4.47213595499958,2.0,0.0,2.0,2.23606797749979,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,1.4142135623730951,0.0,0.0,0.0,2.8284271247461903,2.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,1.0,0.0,0.0,2.23606797749979,2.23606797749979,2.23606797749979,1.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0},{0.0,2.0,0.0,3.1622776601683795,1.4142135623730951,3.1622776601683795,0.0,0.0,1.0,0.0,2.0,0.0,0.0,0.0,0.0,0.0},{0.0,0.0,4.123105625617661,1.4142135623730951,3.1622776601683795,0.0,0.0,0.0,0.0,2.0,0.0,4.0,0.0,0.0,0.0,0.0},{0.0,0.0,1.0,5.0990195135927845,0.0,0.0,0.0,0.0,0.0,0.0,4.0,0.0,4.0,0.0,0.0,2.0},{0.0,0.0,5.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,4.0,0.0,6.0,0.0,6.0},{7.0710678118654755,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,6.0,0.0,6.0,0.0},{1.4142135623730951,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,6.0,0.0,6.0},{7.0710678118654755,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,2.0,6.0,0.0,6.0,0.0}} 
;
        
        		
        // Print the solution
        t.primMST(graph);
    }
}
