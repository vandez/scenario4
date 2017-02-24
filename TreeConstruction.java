package scenario4;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nancy Amelia on 2/21/2017.
 */
public class TreeConstruction  {
    double test[][] = {{-1.5, 1.5}, {-1, 0}, {5, 0}, {4.5, 3.5}, {4.6, -3}};
    List<double[]> arr = new ArrayList<>(Arrays.asList(test));

    List<double[]> robotPos = new ArrayList<>();
    List<Obstacles> allObs = new ArrayList<>();

    double [][]createGraphMST( List<double[]> coordinates, List<Obstacles>ObsList )
    {
        int coorNo=coordinates.size();
        double resultGraph [][]= new double [coorNo][coorNo];
        List<Polygon> PolyList = constructPolygon(ObsList);
        //System.out.print("{");
        try{
                FileWriter writer = new FileWriter("truerobot.txt ", true);
                writer.write("{");

            for ( int i =0;i<coorNo;i++)
            {
                writer.write("{");
                //System.out.print("{");
                for ( int j=0;j<coorNo;j++)
                {
                    Point A = new Point(coordinates.get(i)[0],coordinates.get(i)[1]);
                    Point B = new Point(coordinates.get(j)[0],coordinates.get(j)[1]);
                    Segment seg = new Segment(A,B);

                    boolean intersect = CheckIntersection(PolyList,seg);
                    if ( !intersect) {
                        resultGraph[i][j] = computeDistance(coordinates.get(i)[0], coordinates.get(i)[1], coordinates.get(j)[0], coordinates.get(j)[1]);
                    }
                    else
                    {
                        resultGraph[i][j] =0;
                    }
                    writer.write(Double.toString(resultGraph[i][j]));
                   // System.out.print(resultGraph[i][j]);
                    if ( j!= coorNo-1) {
                       // System.out.print(",");
                        writer.write(",");
                    }
                }
                //System.out.print("}");
                writer.write("}");
                if ( i!= coorNo-1) {
                   // System.out.print(",");
                    writer.write(",");
                }
            }
            //System.out.println("}");
            writer.write("} \n");

            writer.close();
        } catch (IOException e) {
            // do something
        }



        return resultGraph;
    }
    double [][]createGraphMST1( List<double[]> coordinates)
    {
        int coorNo=coordinates.size();
        double resultGraph [][]= new double [coorNo][coorNo];
        try{
            FileWriter writer = new FileWriter("truerobot.txt ", true);
            writer.write("{");
        for ( int i =0;i<coorNo;i++)
        {
            writer.write("{");
            //System.out.print("{");
            for ( int j=0;j<coorNo;j++)
            {
                resultGraph[i][j] = computeDistance(coordinates.get(i)[0], coordinates.get(i)[1], coordinates.get(j)[0], coordinates.get(j)[1]);

                //System.out.print(resultGraph[i][j]);
                writer.write(Double.toString(resultGraph[i][j]));
                if ( j!= coorNo-1) {
                    writer.write(",");
                    //System.out.print(",");
                }
            }
            //System.out.print("}");
            writer.write("}");
            if ( i!= coorNo-1) {
                writer.write(",");
               // System.out.print(",");
            }
        }
        //System.out.println(" ");
            writer.write("}  \n");
            writer.close();
        } catch (IOException e) {
            // do something
        }
        return resultGraph;
    }

    Boolean CheckIntersection ( List<Polygon> polygonList , Segment seg)
    {
        for ( Polygon  poly : polygonList)
        {
            if ( poly.Intersect(seg)==true)
            {
                return true;
            }
        }
        return  false;
    }
    List<double[]> combined (List<Obstacles> obsObs, List<double[]> arr )
    {
        List<double[]> finalRobObs = new ArrayList<>();

        for ( double [] arrayi: arr)
        {
            finalRobObs.add(arrayi);
        }

        for ( Obstacles a : obsObs)
        {
            List<double[]> temp = a.getObstacle();
            for(double[] arrai : temp)
            {
                finalRobObs.add(arrai);
            }
        }

        /*for(double[] arrai : finalRobObs)
        {
            System.out.println(arrai[0] +","+arrai[1]);;
        }*/
        return finalRobObs;
    }


    double computeDistance( double x1,double y1, double x2,double y2)
    {
        return Math.sqrt(((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
    }
    public List<Polygon> constructPolygon(List<Obstacles> obsObs)
    {
        List <Polygon> AllPoly = new ArrayList<>();
        for ( Obstacles a : obsObs)
        {
            Polygon p = new Polygon();
            List<double[]> temp = a.getObstacle();
            for (double [] arry: temp)
                {
                p.addVertex(arry[0],arry[1]);
            }
            AllPoly.add(p);
        }
        return AllPoly;

    }
    public double [] getCoordinates ( ArrayList<ArrayList> AllQuesCoor,int nodes,int questionNumber)
    {
        ArrayList<double[]> test =AllQuesCoor.get(questionNumber-1);
        //System.out.println(test.get(0)[0]+","+test.get(0)[1] );
        double coor [] = new double [2];
        coor[0]= test.get(nodes)[0];
        coor[1]= test.get(nodes)[1];
//        System.out.println(coor[0]+","+coor[1]);
        return coor;

    }
    public void readInput ()
    {
        //problem with where to put it
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("/Users/condei/Downloads/data.txt"));
            // read the first line from the text file
            String fileRead = br.readLine();
            //List<double[]> haha=new ArrayList<>();
            // loop until all lines are read
            while (fileRead != null)
            {
                List<List>overall=new ArrayList<>();



                    Pattern pattern = Pattern.compile("\\{(.*?)\\}",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(fileRead);
                    while (matcher.find()) {
                        double [] coor= new double[2];
                        List<Double>haha=new ArrayList<>();
                        String word = matcher.group(1);
                        String [] col = word.split(",");
                        for(String s :col)
                        {
                            double d =Double.parseDouble(s);
                            haha.add(d);
                        }
                        overall.add(haha);
                        //Splitting the coordinates
                        //String[] coor = word.split(",");


                        //System.out.println(word);
                    }
                    for(List a : overall)
                    {
                        for(Object b: a)
                        {
                            System.out.println(b.toString());
                        }
                    }


                fileRead = br.readLine();

            }
            // close file stream
            br.close();
        }

        // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }



    }

    public ArrayList<ArrayList> getList()
    {
        ArrayList<ArrayList> AllLists = new ArrayList<>();

        //problem with where to put it
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("/Users/condei/Downloads/data.txt"));
            // read the first line from the text file
            String fileRead = br.readLine();
            // loop until all lines are read
            while (fileRead != null)
            {
                ArrayList<double[]> robotPos = new ArrayList<>();
                ArrayList<Obstacles> allObs = new ArrayList<>();
                ArrayList <double []> finalList=new ArrayList<>();
                //Splitting the robots from obstabcles
                if ( fileRead.contains("#"))
                {
                    String[] tokenize = fileRead.split("#");
                    String robot = tokenize[0];
                    //System.out.println("Question" +robot.substring(0,1));
                    //System.out.println(" ");
                    robot=robot.substring(3);

                    String obstacle = tokenize[1];
                    //Splitting between the obstacles
                    String[] obstacles= obstacle.split(";");
                    //System.out.println("robot"+robot);
                    //System.out.println("obs"+obstacle);
                    //System.out.println("obs 1"+obstacles[0]);
                    //System.out.println("obs 2"+obstacles[1]);
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        //Splitting the coordinates
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        //System.out.println("robots:" +coordinates[0]+","+coordinates[1]);
                        robotPos.add(coordinates);
                    }

                    for( String test: obstacles) {

                        Obstacles obs = new Obstacles();
                        Pattern pattern2 = Pattern.compile("\\((.*?)\\)", Pattern.DOTALL);
                        Matcher matcher2 = pattern2.matcher(test);
                        while (matcher2.find()) {

                            String word = matcher2.group(1);


                            String[] coor = word.split(",");
                            double[] coordinates = new double[2];
                            coordinates[0] = Double.parseDouble(coor[0]);
                            coordinates[1] = Double.parseDouble(coor[1]);
                            //System.out.println("OBS"+i+":" +coordinates[0]+","+coordinates[1]);
                            obs.addObstacles(coordinates[0],coordinates[1]);
                        }

                        allObs.add(obs);
                    }

                    fileRead = br.readLine();

                    finalList=(ArrayList<double[]>) combined(allObs,robotPos);
                    //createGraphMST1(robotPos);
                    /// /createGraphMST(finalList,allObs);

                }
                else
                {
                    String robot = fileRead;
                    //System.out.println("Question" +robot.substring(0,1));
                    //System.out.println(" ");
                    robot=robot.substring(3);
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        //Splitting the coordinates
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        //System.out.println("robots:" +coordinates[0]+","+coordinates[1]);
                        robotPos.add(coordinates);
                    }
                    //createGraphMST1(robotPos);
                    finalList=robotPos;
                    fileRead = br.readLine();



                }
                AllLists.add(finalList);

            }
            // close file stream
            br.close();
        }

        // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return AllLists;

    }

    public double[][] getAdMatrix(List<double[][]> name,int quesNo)
    {
        return name.get(quesNo-1);
    }

    public List<double[][]> getResult ()
    {
        List<double[][]> resulting = new ArrayList<>();
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("/Users/condei/Downloads/data.txt"));
            // read the first line from the text file
            String fileRead = br.readLine();
            // loop until all lines are read
            while (fileRead != null)
            {
                List<double[]> robotPos = new ArrayList<>();
                List<Obstacles> allObs = new ArrayList<>();
                List <double []> finalList=new ArrayList<>();
                double [][] distancecomp;
                //Splitting the robots from obstabcles
                if ( fileRead.contains("#"))
                {
                    String[] tokenize = fileRead.split("#");
                    String robot = tokenize[0];
                    robot=robot.substring(3);
                    String obstacle = tokenize[1];
                    String[] obstacles= obstacle.split(";");
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        robotPos.add(coordinates);
                    }

                    for( String test: obstacles) {

                        Obstacles obs = new Obstacles();
                        Pattern pattern2 = Pattern.compile("\\((.*?)\\)", Pattern.DOTALL);
                        Matcher matcher2 = pattern2.matcher(test);
                        while (matcher2.find()) {

                            String word = matcher2.group(1);


                            String[] coor = word.split(",");
                            double[] coordinates = new double[2];
                            coordinates[0] = Double.parseDouble(coor[0]);
                            coordinates[1] = Double.parseDouble(coor[1]);
                            obs.addObstacles(coordinates[0],coordinates[1]);
                        }

                        allObs.add(obs);
                    }

                    fileRead = br.readLine();

                    finalList=combined(allObs,robotPos);
                  distancecomp=createGraphMST(finalList,allObs);

                }
                else
                {
                    String robot = fileRead;
                    robot=robot.substring(3);
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        robotPos.add(coordinates);
                    }
                    distancecomp= createGraphMST1(robotPos);
                    finalList=robotPos;
                    fileRead = br.readLine();



                }
                resulting.add(distancecomp);

            }
            br.close();

        }


        // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return resulting;

    }
    public List<double[][]> getResultRobot ()
    {
        List<double[][]> resulting = new ArrayList<>();
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("/Users/condei/Downloads/data.txt"));
            // read the first line from the text file
            String fileRead = br.readLine();
            // loop until all lines are read
            while (fileRead != null)
            {
                List<double[]> robotPos = new ArrayList<>();
                List<Obstacles> allObs = new ArrayList<>();
                List <double []> finalList=new ArrayList<>();
                double [][] distancecomp;
                //Splitting the robots from obstabcles
                if ( fileRead.contains("#"))
                {
                    String[] tokenize = fileRead.split("#");
                    String robot = tokenize[0];
                    robot=robot.substring(3);
                    String obstacle = tokenize[1];
                    String[] obstacles= obstacle.split(";");
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        robotPos.add(coordinates);
                    }

                    for( String test: obstacles) {

                        Obstacles obs = new Obstacles();
                        Pattern pattern2 = Pattern.compile("\\((.*?)\\)", Pattern.DOTALL);
                        Matcher matcher2 = pattern2.matcher(test);
                        while (matcher2.find()) {

                            String word = matcher2.group(1);


                            String[] coor = word.split(",");
                            double[] coordinates = new double[2];
                            coordinates[0] = Double.parseDouble(coor[0]);
                            coordinates[1] = Double.parseDouble(coor[1]);
                            obs.addObstacles(coordinates[0],coordinates[1]);
                        }

                        allObs.add(obs);
                    }

                    fileRead = br.readLine();

                    finalList=combined(allObs,robotPos);
                  distancecomp=createGraphMST1(robotPos);

                }
                else
                {
                    String robot = fileRead;
                    robot=robot.substring(3);
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        robotPos.add(coordinates);
                    }
                    distancecomp= createGraphMST1(robotPos);
                    finalList=robotPos;
                    fileRead = br.readLine();



                }
                resulting.add(distancecomp);

            }
            br.close();

        }


        // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return resulting;

    }

    public List<double[]> getRobotArray()
    {
        List<double[]> robotPos = new ArrayList<>();
        List<double[][]> resulting = new ArrayList<>();
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("/Users/condei/Downloads/data.txt"));
            // read the first line from the text file
            String fileRead = br.readLine();
            // loop until all lines are read
            while (fileRead != null)
            {

                List<Obstacles> allObs = new ArrayList<>();
                List <double []> finalList=new ArrayList<>();
                double [][] distancecomp;
                //Splitting the robots from obstabcles
                if ( fileRead.contains("#"))
                {
                    String[] tokenize = fileRead.split("#");
                    String robot = tokenize[0];
                    robot=robot.substring(3);
                    String obstacle = tokenize[1];
                    String[] obstacles= obstacle.split(";");
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        robotPos.add(coordinates);
                    }

                    for( String test: obstacles) {

                        Obstacles obs = new Obstacles();
                        Pattern pattern2 = Pattern.compile("\\((.*?)\\)", Pattern.DOTALL);
                        Matcher matcher2 = pattern2.matcher(test);
                        while (matcher2.find()) {

                            String word = matcher2.group(1);


                            String[] coor = word.split(",");
                            double[] coordinates = new double[2];
                            coordinates[0] = Double.parseDouble(coor[0]);
                            coordinates[1] = Double.parseDouble(coor[1]);
                            obs.addObstacles(coordinates[0],coordinates[1]);
                        }

                        allObs.add(obs);
                    }

                    fileRead = br.readLine();

                    finalList=combined(allObs,robotPos);
                    distancecomp=createGraphMST(finalList,allObs);

                }
                else
                {
                    String robot = fileRead;
                    robot=robot.substring(3);
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        robotPos.add(coordinates);
                    }
                    distancecomp= createGraphMST1(robotPos);
                    finalList=robotPos;
                    fileRead = br.readLine();



                }
                resulting.add(distancecomp);

            }
            br.close();

        }


        // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return robotPos;

    }

    public void parser ()
    {

        //problem with where to put it
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("/Users/condei/Downloads/data.txt"));
            // read the first line from the text file
            String fileRead = br.readLine();
            // loop until all lines are read
            while (fileRead != null)
            {

                List <double []> finalList=new ArrayList<>();
                //Splitting the robots from obstabcles
                if ( fileRead.contains("#"))
                {
                    String[] tokenize = fileRead.split("#");
                    String robot = tokenize[0];
                    //System.out.println("Question" +robot.substring(0,1));
                    //System.out.println(" ");
                    robot=robot.substring(3);

                    String obstacle = tokenize[1];
                    //Splitting between the obstacles
                    String[] obstacles= obstacle.split(";");
                    //System.out.println("robot"+robot);
                    //System.out.println("obs"+obstacle);
                    //System.out.println("obs 1"+obstacles[0]);
                    //System.out.println("obs 2"+obstacles[1]);
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        //Splitting the coordinates
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        //System.out.println("robots:" +coordinates[0]+","+coordinates[1]);
                        robotPos.add(coordinates);
                    }

                    for( String test: obstacles) {

                        Obstacles obs = new Obstacles();
                        Pattern pattern2 = Pattern.compile("\\((.*?)\\)", Pattern.DOTALL);
                        Matcher matcher2 = pattern2.matcher(test);
                        while (matcher2.find()) {

                            String word = matcher2.group(1);


                            String[] coor = word.split(",");
                            double[] coordinates = new double[2];
                            coordinates[0] = Double.parseDouble(coor[0]);
                            coordinates[1] = Double.parseDouble(coor[1]);
                            //System.out.println("OBS"+i+":" +coordinates[0]+","+coordinates[1]);
                            obs.addObstacles(coordinates[0],coordinates[1]);
                        }

                        allObs.add(obs);
                    }

                    fileRead = br.readLine();

                    finalList=combined(allObs,robotPos);
                    createGraphMST1(robotPos);
                    /// /createGraphMST(finalList,allObs);

                }
                else
                {
                    String robot = fileRead;
                    //System.out.println("Question" +robot.substring(0,1));
                    //System.out.println(" ");
                    robot=robot.substring(3);
                    Pattern pattern = Pattern.compile("\\((.*?)\\)",Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(robot);
                    while (matcher.find()) {
                        String word = matcher.group(1);
                        //Splitting the coordinates
                        String[] coor = word.split(",");
                        double [] coordinates = new double [2];
                        coordinates[0]=Double.parseDouble(coor[0]);
                        coordinates[1]=Double.parseDouble(coor[1]);
                        //System.out.println("robots:" +coordinates[0]+","+coordinates[1]);
                        robotPos.add(coordinates);
                    }
                    double [][] finalres= createGraphMST1(robotPos);
                    finalList=robotPos;
                    fileRead = br.readLine();



                }

            }
            // close file stream
            br.close();
        }


        // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    }



    public void go ()
    {
        //double robot [][] = {{0,0}, {10, 10}, {8, 10}, {9, 9},{5, 0},{0, 7},{1, 8} };//(0,0), (10,10), (8,10), (9,9), (5,0), (0,7), (1,8)
        double robot[][] = {{0,1}, {2, 0}, {3, 5}, {6, 2},{9, 0}}; //Samples {{-1.5, 1.5}, {-1, 0}, {5, 0}, {4.5, 3.5}, {4.6, -3}};
        List<double[]> arrRobot = new ArrayList<>(Arrays.asList(robot));

        //obstacles
        double obs1[][] = {{1,2}, {1, 4}, {3, 4}, {3, 2}};
        Obstacles arrobs1 = new Obstacles();
        List<double[]> listobs1 = new ArrayList<>(Arrays.asList(obs1));
        arrobs1.setObstacle(listobs1);

        double obs2[][] = {{8, 1}, {4, 1}, {4, 4}, {5, 2}};
        List<double[]> listobs2 = new ArrayList<>(Arrays.asList(obs2));
        Obstacles arrobs2 = new Obstacles();
        arrobs2.setObstacle(listobs2);

        List<Obstacles> AllObs = new ArrayList<>();
        AllObs.add(arrobs1);
        AllObs.add(arrobs2);



        List <double []> finalList = new ArrayList<>();
        finalList=combined(AllObs,arrRobot);
        createGraphMST(finalList,AllObs);

    }



    public static void main (String[] args) {
        //robot 1: (0,0), (10,10), (8,10), (9,9), (5,0), (0,7), (1,8)
        //double robot [][] = {{0,0}, {10, 10}, {8, 10}, {9, 9},{5, 0},{0, 7},{1, 8} };
        //List<double[]> arrRobot = new ArrayList<>(Arrays.asList(robot));
        //new TreeConstruction().createGraphMST1(arrRobot);
       // new TreeConstruction().readInput();
        List<double[][]> test = new ArrayList<>();
        test= new TreeConstruction().getResult();
        for ( double[][] a : test)
        {
            int no = a.length;
            for ( int i =0 ;i <no;i++) {
                for (int j = 0; j < no; j++)
                {
                    System.out.println(a[i][j]);
                }
            }
        }
        //double coor [] = new TreeConstruction().getCoordinates(test,1,1);

    }


}
