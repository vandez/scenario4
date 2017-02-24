package scenario4;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class Together {
	
    public static void main (String[] args) {
    
    //graph no obstacles but withobstacles, dijk must be separated 
    //MST should be noobstacles
    
//        double graph[][] = new double[][] {{0.0,14.142135623730951,12.806248474865697,12.727922061357855,5.0,7.0,8.06225774829855},
//        		{14.142135623730951,0.0,2.0,1.4142135623730951,11.180339887498949,10.44030650891055,9.219544457292887},
//        		{12.806248474865697,2.0,0.0,1.4142135623730951,10.44030650891055,8.54400374531753,7.280109889280518},
//        		{12.727922061357855,1.4142135623730951,1.4142135623730951,0.0,9.848857801796104,9.219544457292887,8.06225774829855},
//        		{5.0,11.180339887498949,10.44030650891055,9.848857801796104,0.0,8.602325267042627,8.94427190999916},
//        		{7.0,10.44030650891055,8.54400374531753,9.219544457292887,8.602325267042627,0.0,1.4142135623730951},
//        		{8.06225774829855,9.219544457292887,7.280109889280518,8.06225774829855,8.94427190999916,1.4142135623730951,0.0}
//                                   };
        
        TreeConstruction tree = new TreeConstruction();
        List<double[][]> name = tree.getResult();
        double graph[][] = tree.getAdMatrix(name, 1);
        List<double[][]> namerobot = tree.getResultRobot();
        double graphrobot[][] = tree.getAdMatrix(namerobot, 1);
        
        
        
        //	List<double[]> coordinates = new ArrayList<double[]>();
//	coordinates.add(new double[]{0,0});
//	coordinates.add(new double[]{10,10});
//	coordinates.add(new double[]{8,10});
//	coordinates.add(new double[]{9,9});
//	coordinates.add(new double[]{5,0});
//	coordinates.add(new double[]{0,7});
//	coordinates.add(new double[]{1,8});

	
    int numnodes = graph[0].length;
	
	//MST 
    MST t = new MST(graphrobot,graphrobot[0].length);
    ArrayList<ArrayList<Integer[]>> myMST = t.primMST();
    
    //DIJK
    
    dijks u = new dijks(graph);
//    ArrayList<ArrayList<ArrayList<Integer>>> myDIJK = u.Runthrough(graph,myMST,numnodes);
    

    
    //TESTCASE NUM1
    
	ArrayList<ArrayList<ArrayList<Integer>>> allpaths = u.Runthrough(myMST, numnodes);

	System.out.println(allpaths);
    
    
    
    //Convert to coordinates
    ArrayList<ArrayList<ArrayList<double[]>>> coordDIJK = new ArrayList<ArrayList<ArrayList<double[]>>>();
    TreeConstruction v = new TreeConstruction();
    ArrayList<ArrayList> allLists = new ArrayList<>();
    allLists = v.getList();
    
    
    for(int x = 0; x < allpaths.size(); x++){
    	ArrayList<ArrayList<Integer>> middle = allpaths.get(x);
    	ArrayList<ArrayList<double[]>> onepath = new ArrayList<ArrayList<double[]>>();
    	for(int y = 0; y < middle.size(); y++){
    		ArrayList<Integer> inner = middle.get(y);
			ArrayList<double[]> setofcoord = new ArrayList<double[]>();
    		for(int z = 0; z < inner.size(); z++){
    				int point = inner.get(z);
    				double[] mypoint = new double[2];
    				mypoint = v.getCoordinates(allLists, point, 1);
//    				System.out.println(point);
//    				System.out.println("x: "+mypoint[0] + " y: " + mypoint[1]);
    				setofcoord.add(mypoint);
    		}
    	    onepath.add(setofcoord);	
    			
    	}
    	coordDIJK.add(onepath);
    }
    
    //printing
    
    
//    System.out.println("");
//    System.out.println("");
//    System.out.println("here: ");
//   // System.out.println("size"+myDIJK);
//
//    
//    //coordDIJK.clear();
//    
//    for(int f = 0; f < coordDIJK.size(); f++) {
//        for(int j = 0; j < (coordDIJK.get(f)).size(); j++) {
//        	for(int k = 0; k <2; k++){
//        		System.out.print("(" + ((coordDIJK.get(f)).get(j)).get(k)[0] + ", " + ((coordDIJK.get(f)).get(j)).get(k)[1] + ")");
//        	}
//        	
//        }
//	    System.out.println("");
//
//    }
    

    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            visualisation ex = new visualisation(coordDIJK);
            ex.setVisible(true);
        }
    });
	
   
    
    }
	

}
