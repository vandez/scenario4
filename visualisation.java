package scenario4;

import java.awt.*;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JPanel;


class Surface extends JPanel {

    double max = 12;
    int windowSize = 900;
    double midpoint = windowSize / 2;
    int height = 4;
    double xSpan;
    double ySpan;
    double maxX;
    double maxY;
    double minX;
    double minY;

    List<double[]> arrRobot = new ArrayList<>();
    List<Obstacles> AllObs = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<double[]>>> coord;

    public Surface(ArrayList<ArrayList<ArrayList<double[]>>> coord){
    	this.coord = coord;
    }

    public double divideGrid() {
        double gridUnit = midpoint / max;
        return gridUnit;
    }

    public int positionOnGrid(double coor) {
        double halfHeight = height/2;
        int result = (int) (midpoint + (coor * divideGrid()) - halfHeight - (minX + (xSpan/2))*divideGrid());
        return result;
    }

    public int positionOnGridY(double coor) {
        double halfHeight = height/2;
        int result = (int) (midpoint - (coor * divideGrid()) - halfHeight + (minY + (ySpan/2))*divideGrid());
        return result;
    }

    public int linePosOnGrid(double coor) {
        int result = (int) (midpoint + (coor * divideGrid()) - (minX + (xSpan/2))*divideGrid());
        return result;
    }

    public int linePosOnGridY(double coor) {
        int result = (int) (midpoint - (coor * divideGrid()) + (minY + (ySpan/2))*divideGrid());
        return result;
    }


    public void doDrawing(Graphics g)
 {



        try
            {
                // create a Buffered Reader object instance with a FileReader
                BufferedReader br = new BufferedReader(new FileReader("/Users/condei/downloads/data.txt"));

                // read the first line from the text file
                String fileRead = br.readLine();

                // loop until all lines are read
                while (fileRead != null)
                {

                    //Splitting the robots from obstabcles
                    if ( fileRead.contains("#"))
                    {
                        String[] tokenize = fileRead.split("#");
                        String robot = tokenize[0];
                        robot=robot.substring(3);

                        String obstacle = tokenize[1];
                        //Splitting between the obstacles
                        String[] obstacles= obstacle.split(";");
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
                            arrRobot.add(coordinates);
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

                            AllObs.add(obs);
                        }

                        fileRead = br.readLine();

                    }
                    else
                    {
                        String robot = fileRead;
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
                            arrRobot.add(coordinates);
                        }

                        fileRead = br.readLine();


                    }

                }

                // close file stream
                br.close();
                Graphics2D g2d = (Graphics2D) g;

                double maxRobot = Double.MIN_VALUE;
                double maxPosRobot = Double.MIN_VALUE;
                double maxNegRobot = Double.MIN_VALUE;
                double maxXRobot = Double.MIN_VALUE;
                double maxYRobot = Double.MIN_VALUE;
                double minXRobot = Double.MAX_VALUE;
                double minYRobot = Double.MAX_VALUE;

                for(double[] temp : arrRobot) {
                    if(temp[0] > maxXRobot) {
                        maxXRobot = temp[0];
                    }
                    if(temp[1] > maxYRobot) {
                        maxYRobot = temp[1];
                    }
                }

                for(double[] temp : arrRobot) {
                    if(temp[0] < minXRobot) {
                        minXRobot = temp[0];
                    }
                    if(temp[1] < minYRobot) {
                        minYRobot = temp[1];
                    }
                }

                maxPosRobot = Math.max(maxXRobot, maxYRobot);
                maxNegRobot = Math.max(Math.abs(minXRobot), Math.abs(minYRobot));
                maxRobot = Math.max(maxPosRobot, maxNegRobot);
                maxX = maxXRobot;
                maxY = maxYRobot;
                minX = minXRobot;
                minY = minYRobot;

                double maxObs = Double.MIN_VALUE;
                if(AllObs.isEmpty() == false) {

                    double maxPosObs = Double.MIN_EXPONENT;
                    double maxNegObs = Double.MIN_EXPONENT;
                    double maxXObs = Double.MIN_VALUE;
                    double maxYObs = Double.MIN_VALUE;
                    double minXObs = Double.MAX_VALUE;
                    double minYObs = Double.MAX_VALUE;

                    for(Obstacles o : AllObs) {
                        List<double[]> test = o.getObstacle();
                        for(double[] a : test) {
                            if(a[0] > maxXObs) {
                                maxXObs = a[0];
                            }
                            if(a[1] > maxYObs) {
                                maxYObs = a[1];
                            }
                        }
                    }

                    for(Obstacles o : AllObs) {
                        List<double[]> test = o.getObstacle();
                        for(double[] a : test) {
                            if(a[0] < minXObs) {
                                minXObs = a[0];
                            }
                            if(a[1] < minYObs) {
                                minYObs = a[1];
                            }
                        }
                    }
                    maxPosObs = Math.max(maxXObs, maxYObs);
                    maxNegObs = Math.max(Math.abs(minXObs), Math.abs(minYObs));
                    maxObs = Math.max(maxPosObs, maxNegObs);
                    maxX = Math.max(maxXRobot, maxXObs);
                    maxY = Math.max(maxYRobot, maxYObs);
                    minX = Math.min(minXRobot, minXObs);
                    minY = Math.min(minYRobot, minYObs);
//                    System.out.println("maxXObs: " + maxXObs);
//                    System.out.println("maxYObs: " + maxYObs);
//                    System.out.println("minXObs: " + minXObs);
//                    System.out.println("minYObs: " + minYObs);


                }
                xSpan = Math.abs(minX) + Math.abs(maxX);
                ySpan = Math.abs(minY) + Math.abs(maxY);
                double maxSpan = Math.max(xSpan, ySpan);
                //System.out.println("xspan: " + xSpan + " yspan: " + ySpan);

                max = Math.max(maxRobot, maxObs);
                //System.out.println("maxxxx:  " + max);
                //If there are obstacles
                if(AllObs.isEmpty() == false) {
                    max = Math.min(max, maxSpan/2);
                    //System.out.println("maxspan: " +maxSpan);
                    if(max < 5) {
                        max = max + max/5;
                    }
                    if(max >= 5 && max <= 12) {
                        max = max + max/10;
                    }
                    else {
                        max = max + max/10;
                    }
                } else {
                    max = max + max/15;
                }

//                System.out.println("maxXrobot: " + maxXRobot);
//                System.out.println("maxYRobot: " + maxYRobot);
//                System.out.println("minXRobot: " + minXRobot);
//                System.out.println("minYRobot: " + minYRobot);
//                System.out.println("maxrobot: " + maxRobot);
//                System.out.println("maxObs: " + maxObs);
//                System.out.println("maxX: " + maxX);
//                System.out.println("maxY: " + maxY);
//                System.out.println("minX: " + minX);
//                System.out.println("minY: " + minY);
//
//                System.out.println("max after addition: " + max);

                //Drawing the obstacles
                for ( Obstacles a : AllObs)
                {
                    Random rand = new Random();
                    float red = rand.nextFloat();
                    float green = rand.nextFloat();
                    float blue = rand.nextFloat();
                    Color randomColor = new Color(red, green, blue);

                    List<double[]> temp = a.getObstacle();
                    int size = temp.size();
                    int[] x = new int[size];
                    int[] y = new int[size];
                    int i = 0;
                    for (double [] arry: temp) {
                        x[i] = linePosOnGrid(arry[0]);
                        y[i] = linePosOnGridY(arry[1]);
//                        g2d.drawString(Integer.toString(i+5) + "(" + Double.toString(arry[0]) + ", " +
//                                Double.toString(arry[1]) + ")" , positionOnGrid(arry[0]), positionOnGridY(arry[1]));
                        i++;
                    }



                    g.setColor(randomColor);
                    g.fillPolygon(x, y, temp.size());

                }


                if(arrRobot.size() < 10) {
                    height = 10;
                }
                for(int i = 0; i < arrRobot.size(); i++) {
                    g2d.setColor(Color.black);
                    g2d.fillOval(positionOnGrid(arrRobot.get(i)[0]), positionOnGridY(arrRobot.get(i)[1]), height, height);
//                    g2d.drawString(Integer.toString(i) + "(" + Double.toString((arrRobot.get(i)[0])) + ", " +
//                            Double.toString((arrRobot.get(i)[1])) + ")" , positionOnGrid(arrRobot.get(i)[0]), positionOnGridY(arrRobot.get(i)[1]));
                }

//
//                double branch1[][] = {{-1, 0}, {2, 2}};
//                double branch15[][] = {{5, 0}, {4.6, -3}, {5,4}};
//                double branch2[][] = {{-1.5, 1.5}, {-1,0}};
//                double branch3[][] = {{5, 0}, {4.5, 3.5}};
//                // (-1.5, 1.5), (-1, 0); (-1, 0), (2, 2), (5, 0), (4.6, -3); (5, 0), (4.5, 3.5)
//                // coord { outer[  branch1 middle( br1 inner[x,y],[x,y]  ), (  br1.5 [x,y],[x,y]  ) ], [  branch2   ], [   branch3  ]   }
//
//                ArrayList<ArrayList<double[]>> middle1 = new ArrayList<>();
//                ArrayList<ArrayList<double[]>> middle2 = new ArrayList<>();
//                ArrayList<ArrayList<double[]>> middle3 = new ArrayList<>();
//
//                ArrayList<double[]> br1 = new ArrayList<>(Arrays.asList(branch1));
//                ArrayList<double[]> br15 = new ArrayList<>(Arrays.asList(branch15));
//                ArrayList<double[]> br2 = new ArrayList<>(Arrays.asList(branch2));
//                ArrayList<double[]> br3 = new ArrayList<>(Arrays.asList(branch3));
//
//                middle1.add(br1);
//                middle1.add(br15);
//                middle2.add(br2);
//                middle3.add(br3);
//
//                coord.add(middle1);
//                coord.add(middle2);
//                coord.add(middle3);

                //System.out.print("1: ");
                //ArrayList<ArrayList<ArrayList<double[]>>> coord;
                for(int i = 0; i < coord.size(); i++) {
                    for(int j = 0; j < (coord.get(i)).size(); j++) {
                        int l = j;
                        if(j < (coord.get(i)).size()-1) {
                            ArrayList<double[]> name = (coord.get(i)).get(l);
                            ArrayList<double[]> name2 = (coord.get(i)).get(l+1);
                            double[] firstarray = name.get(name.size() - 1);
                            double[] doublearray = name2.get(0);
                            g2d.drawLine(linePosOnGrid(firstarray[0]), linePosOnGridY(firstarray[1]), linePosOnGrid(doublearray[0]), linePosOnGridY(doublearray[1]) );
                        }

                        for(int k = 0; k < (((coord.get(i)).get(j)).size()) - 1; k++) {
                            g2d.drawLine(linePosOnGrid((((coord.get(i)).get(j)).get(k))[0]), linePosOnGridY((((coord.get(i)).get(j)).get(k))[1]),
                                    linePosOnGrid((((coord.get(i)).get(j)).get(k+1))[0]), linePosOnGridY((((coord.get(i)).get(j)).get(k+1))[1]));
//                            System.out.println("x: " + (((coord.get(i)).get(j)).get(k))[0] + " y: " + (((coord.get(i)).get(j)).get(k))[1] +
//                                    " next x: " + (((coord.get(i)).get(j)).get(k+1))[0] + " next y: " + (((coord.get(i)).get(j)).get(k+1))[1]);
//                        
                            }

                    }
                    Random rand = new Random();
                    float red = rand.nextFloat();
                    float green = rand.nextFloat();
                    float blue = rand.nextFloat();
                    Color randomColor = new Color(red, green, blue);
                    g2d.setColor(randomColor);

                }

//                for(ArrayList<ArrayList<double[]>> outer : coord) {
//                    for(ArrayList<double[]> middle : outer) {
//                        for(double[] inner : middle) {
//                            System.out.println("x: " + inner[0] + " y: " + inner[1]);
//                        }
//                    }
//                }


                for(int i = 0; i < coord.size(); i++) {
                    for(int j = 0; j < (coord.get(i)).size(); j++) {
                        for(int k = 0; k < ((coord.get(i)).get(j)).size(); k++) {
                            System.out.print("(" + (((coord.get(i)).get(j)).get(k))[0] + ", " + (((coord.get(i)).get(j)).get(k))[1] + ")");
                            if(k != ((coord.get(i)).get(j)).size() - 1) {
                                System.out.print(", ");
                            }
                        }
                        if(j!= ((coord.get(i).size()) - 1)) {
                            System.out.print(", ");
                        }

                    }
                    if(i != coord.size() - 1) {
                        System.out.print("; ");
                    }

                }
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}

public class visualisation extends JFrame {

    ArrayList<ArrayList<ArrayList<double[]>>> coord;

    public visualisation(ArrayList<ArrayList<ArrayList<double[]>>> coord) {
    	this.coord = coord;
        initUI();
    }

    public void initUI() {

        add(new Surface(coord));

        setTitle("Move and Tag");
        setSize(900, 920);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                visualisation ex = new visualisation();
//                ex.setVisible(true);
//            }
//        });

        
        
        
        
        
        
        
        
        

    }
}


