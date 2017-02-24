package scenario4;

import java.util.*;
import java.lang.*;
import java.io.*;
 
class MST
{
    // Number of vertices in the graph
	
    public static int V;  
    public static double graph[][];
    public static double newgraph[][];
    public static double zerostart[];
	
	 public MST(double graph[][], int size){
		this.graph = graph;
		this.V = size-1;
		this.newgraph=getshortGraph();
		this.zerostart=getzeroGraph();
	}

    
 
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    int minKey(double key[], Boolean mstSet[])
    {
        // Initialize min value
        double min = Integer.MAX_VALUE;
        int min_index=-1;
        
     
        for (int v = 0; v < V; v++){
            if ((mstSet[v] == false && key[v] < min))
            {
                min = key[v];
                min_index = v;
               
                	
            }
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
//        System.out.println("Edge   Weight");
//        for (int i = 1; i < V; i++){
//            System.out.println((parent[i]+1)+" - "+ (i+1)+"    "+
//                               (graph[i][parent[i]]));
//        }
    }
 
    // Function to construct and print MST for a graph represented
    //  using adjacency matrix representation
    ArrayList<ArrayList<Integer[]>> primMST()
    {
        // Array to store constructed MST
        int parent[] = new int[V];
 
        // Key values used to pick minimum weight edge in cut
        double key[] = new double [V];
 
        // To represent set of vertices not yet included in MST
        Boolean mstSet[] = new Boolean[V];
        
        Boolean parentSet[] = new Boolean[V];
        
        int mychild[][] = new int[V][2];
 
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
        
        double minWeights[] = new double[2];
        
        int prevNode = -1;
        //get shortest distance from 0
        
        double shortest = zerostart[1];
        int nodeshortest = 1;
        for(int x = 1; x<=V; x++){
        	if(zerostart[x]<shortest){
        		shortest = zerostart[x];
        		nodeshortest = x;
        	}
        }
        
        
        //new prim's
        
//        int n = V+1;
//        Stack nodeSt = new Stack<Integer>();
//        nodeSt.push(n);
//        
//        while()
 
        // The MST will have V vertices
        for (int count = 1; count < V-1; count++)
        {
        	 
            // Pick the minimum key vertex from the set of vertices
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
                if (newgraph[u][v]!=0 && mstSet[v] == false &&
                    newgraph[u][v] <  key[v] )
                {
                	

                	if(parentCount[u] == 2){
                		
                			if((newgraph[u][v] < newgraph[u][mychild[u][0]])){
                				parent[v] = u;  
                				key[v] = newgraph[u][v];
                				parent[mychild[u][0]] = V;
                				key[mychild[u][0]] = Integer.MAX_VALUE;
                				mychild[u][0] = v;
                        		System.out.println("replace " + mychild[u][0] +" with" + v);

                			}                		
                			if((newgraph[u][v] < newgraph[u][mychild[u][0]]) && (newgraph[u][mychild[u][1]]>newgraph[u][mychild[u][0]])){
                				parent[v] = u;
                				key[v] = newgraph[u][v];
                				parent[mychild[u][1]] = V;  
                				key[mychild[u][1]] = Integer.MAX_VALUE;
                				mychild[u][1] = v;
                        		System.out.println("replace " + mychild[u][1] +" with" + v);

                			}
                			if((newgraph[u][v] < newgraph[u][mychild[u][1]]) && (newgraph[u][mychild[u][1]]>newgraph[u][mychild[u][0]])){
                				parent[v] = u;  
                				key[v] = newgraph[u][v];
                				parent[mychild[u][1]] = V;
                				key[mychild[u][1]] = Integer.MAX_VALUE;
                				mychild[u][1] = v;
                        		System.out.println("replace " + mychild[u][1] +" with" + v);

                			}                		
                			if((newgraph[u][v] < newgraph[u][mychild[u][1]]) && (newgraph[u][mychild[u][0]]>newgraph[u][mychild[u][1]])){
                				parent[v] = u;
                				key[v] = newgraph[u][v];
                				parent[mychild[u][0]] = V; 
                				key[mychild[u][0]] = Integer.MAX_VALUE;
                				mychild[u][0] = v;
                        		System.out.println("replace " + mychild[u][0] +" with" + v);

                			}
                			
                		
                	}
                	else{
                		

//                    if(parent[v]!=u){
//                    parentCount[parent[v]]--;
//                    }
                    
                    parent[v]  = u;  
                    mychild[u][parentCount[u]] = v;
                    
                    key[v] = newgraph[u][v];
                	parentCount[u]++;

                	}
                	
//                	System.out.println(parentCount[u]);
//                	System.out.println((u+1) + " - " + (v+1));
                	
                	
                }
            
//            System.out.println("");

            
        }
        
        
 
        // print the constructed MST
        printMST(parent, V, newgraph);
        
//        System.out.println("Edge   Weight");
//        System.out.println("0 - "+ nodeshortest + "    " + zerostart[4]);

        int[][] finalMST = new int[V+1][V+1];
        finalMST[0][nodeshortest] = 1;
        finalMST[nodeshortest][0] = 1;
        int counter = nodeshortest;
        for (int i = 1; i < V; i++){
//          System.out.println((parent[i]+1)+" - "+ (i+1)+"    "+
//                             (graph[i][parent[i]]));
//          System.out.println((i+1)+"and"+(parent[i]+1));
          finalMST[parent[i]+1][i+1] = 1;
          finalMST[i+1][parent[i]+1] = 1;
      }

      
        ArrayList<ArrayList<Integer>> allpaths = new ArrayList<ArrayList<Integer>>();
        int v, i;
        int vertices[] = new int[V + 1];
        //assigning the value to coordinates
        for (int x = 0; x < V + 1; x++) {
            vertices[x] = x;
        }
        int vFirst = vertices[0];
        int[] isVisited = new int[V + 1];
        int n = V + 1;
        Stack st = new Stack<Integer>();
        st.push(vFirst);
        int childrenCount[] = new int[V+1];

        boolean childVisited[] = new boolean[V+1];
        ArrayList<Integer>[] mychildren = (ArrayList<Integer>[])new ArrayList[V+1];
        ArrayList<Integer> each = new ArrayList<Integer>();
        int lastchild[] = new int[V+1];

        int isvist2[] = new int[V+1];

        for(int x = 0; x<V+1; x++){
            mychildren[x]=each;
        }

        Stack newst = new Stack<Integer>();

        while (!st.isEmpty() ) {
            boolean leaf=true;


            newst.push(st.peek());
            //System.out.println(st.peek());
            v = (int) st.pop();
            //System.out.println("V" +v);

            if (isVisited[v] == 0) {
                isVisited[v] = 1;
            }
            //int[] children= new int [v+1];
            int children=0;
            ArrayList <Integer> test = new ArrayList<>();

            for (i = 0; i < n; i++) {
                if ((finalMST[v][i] == 1) && (isVisited[i] == 0)) {
                    st.push(i);
                    children++;
                    //int number = children[v];
                    //children[v]=number+1;
                    leaf=false;
                    //  System.out.println(v);
                    int kk = i;
                    int z = v;
                    if(finalMST[z][kk]==1){
                        test.add(kk);
                        //mychildren[z].add(kk);
                        isvist2[i]=1;

                    }

                }

            }
            mychildren[v]=test;



            childrenCount[v] = children;


            boolean allchildvis=false;

            for ( int j=0;j<n;j++ )
            {

                if ( finalMST[v][j]==1 )
                {
                    if(isVisited[j]==1) {
                        allchildvis = true;
                        childVisited[v] = true;
                    }
                }

            }
            
            int no =(int) newst.peek();


            if((leaf && isVisited[no]==1))
            {

            //System.out.println(newst.toString());
            allpaths.add(new ArrayList(newst));
                newst.pop();
                
//                System.out.println("numchild" + newst.peek() + ":"+ childrenCount[(int)newst.peek()]);

                
                //while the node newst.peek branches out
               
                if(!newst.empty()){
                while(childrenCount[(int)newst.peek()]>=1){
                	int numvisit = 0;
                	//check if children are visited
                	for(int x = 0; x< mychildren[(int)newst.peek()].size(); x++){
                		if(isVisited[mychildren[(int)newst.peek()].get(x)]==1){
                			numvisit++;
                		}
                	}
                	if(numvisit == mychildren[(int)newst.peek()].size() ){
                    	System.out.println(newst.peek());
                    	newst.pop();
                	}
                	else{
                		break;
                	}
                	
                	if(newst.isEmpty()){
                		break;
                	}
                		
                	
                }

            }
            }





        }
        
//        System.out.println(allpaths);
        ArrayList<Integer[]> brancharrays = new ArrayList<>();

        ArrayList<ArrayList<Integer[]>> allpaths2 = new ArrayList<>();
        
        ArrayList<Integer[]> allpaths3 = new ArrayList<>();
        
        for(ArrayList<Integer> z : allpaths){
        	for(int x = 0; x < z.size()-1; x++){
        		Integer eachedge[] = new Integer[2];
        		eachedge[0] = z.get(x);
        		eachedge[1] = z.get(x+1);  
        		boolean containsdup = false;
        		for(Integer[] each1 : allpaths3){
        			if(Arrays.equals(each1,eachedge)){
        				containsdup = true;
        			}
        		}
        		
        		if(containsdup == false){
        			brancharrays.add(eachedge);  
        			allpaths3.add(eachedge);

        		}
        		
        	}
        	allpaths2.add(brancharrays);
        	brancharrays = new ArrayList<Integer[]>();
        	            	
        }

        
        Set<Integer[]> hs = new HashSet<>();
        hs.addAll(brancharrays);
        brancharrays.clear();
        brancharrays.addAll(hs);
        

        
        
      
        	
        for(int f = 0; f < allpaths2.size(); f++) {
            for(int j = 0; j < (allpaths2.get(f)).size(); j++) {
                System.out.print("(" + ((allpaths2.get(f)).get(j))[0] + ", " + ((allpaths2.get(f)).get(j))[1] + ")");
            }
        }
//        
        
        return allpaths2;
        
    }
    
     double[][] getshortGraph(){
    	double[][] newgraph = new double[V][V];
    	
    	for(int x = 1; x <= V; x++){
    		for(int y = 1; y <= V; y++){
    			newgraph[x-1][y-1] = graph[x][y];
    		}
    	}
    	
    	return newgraph;
    }
    
     double[] getzeroGraph(){
    	double[] zerograph = new double[V+1];
    	
    	for(int x = 0; x <= V; x++){
    		zerograph[x] = graph[0][x];
    	}
    	
    	return zerograph;
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
         
        
        double graph[][] = new double[][] {{0.0,14.142135623730951,12.806248474865697,12.727922061357855,5.0,7.0,8.06225774829855},
        		{14.142135623730951,0.0,2.0,1.4142135623730951,11.180339887498949,10.44030650891055,9.219544457292887},
        		{12.806248474865697,2.0,0.0,1.4142135623730951,10.44030650891055,8.54400374531753,7.280109889280518},
        		{12.727922061357855,1.4142135623730951,1.4142135623730951,0.0,9.848857801796104,9.219544457292887,8.06225774829855},
        		{5.0,11.180339887498949,10.44030650891055,9.848857801796104,0.0,8.602325267042627,8.94427190999916},
        		{7.0,10.44030650891055,8.54400374531753,9.219544457292887,8.602325267042627,0.0,1.4142135623730951},
        		{8.06225774829855,9.219544457292887,7.280109889280518,8.06225774829855,8.94427190999916,1.4142135623730951,0.0}
                                   }; 
        
        MST t = new MST(graph,7);

        
        double newGraph[][] = t.getshortGraph();
        double zerostart[] = t.getzeroGraph();
        
        
        // Print the solution
        t.primMST();
    }
}