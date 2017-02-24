package scenario4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;

/**
 * Created by Nancy Amelia on 2/23/2017.
 */
public class DFS2 {



        public static void main (String[]args)
        {
            double [][] finalMST ={{0,1,0,0,0,0,0},
                    {0,0,0,1,1,0,0},
                    {0,0,0,0,0,0,0},
                    {0,0,1,0,0,0,0},
                    {0,0,0,0,0,1,1},
                    {0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0}};

            ArrayList<ArrayList<Integer>> allpaths = new ArrayList<ArrayList<Integer>>();
            int V =6 ;
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

//            System.out.println("v:"+v);
//            System.out.println("no"+(x+children));
//            for(int z = (x+children); z<6; z++){
//            	if(mychildren[x].size()>=(x+children)){
//            	mychildren[x].remove(z);
//            	}
//            }



                childrenCount[v] = children;

//            for(int z = v + childrenCount[v]; z<6; z++){
//            	if(mychildren[v].size()>0){
//            	mychildren[v].remove();
//            	}
//            }

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


                if(leaf && isVisited[no]==1)
                {

                //System.out.println(newst.toString());
                allpaths.add(new ArrayList(newst));
                    newst.pop();

                    System.out.println(lastchild[(int)newst.peek()]);
                    
                    if(childrenCount[(int)newst.peek()]>=2){
                    	int numvisit = 0;
                    	for(int x = 0; x< mychildren[(int)newst.peek()].size(); x++){
                    		if(isVisited[mychildren[(int)newst.peek()].get(x)]==1){
                    			numvisit++;
                    		}
                    	}
                    	if(numvisit == mychildren[(int)newst.peek()].size() ){
                        newst.pop();
                    	}
                    }

                }





            }
            
            System.out.println(allpaths);
            ArrayList<Integer[]> brancharrays = new ArrayList<>();
    
            ArrayList<ArrayList<Integer[]>> allpaths2 = new ArrayList<>();
            
            ArrayList<Integer[]> allpaths3 = new ArrayList<>();
            
            for(ArrayList<Integer> z : allpaths){
            	for(int x = 0; x < z.size()-1; x=x+2){
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
            
            

         
        }
    }