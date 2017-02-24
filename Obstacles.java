package scenario4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nancy Amelia on 2/21/2017.
 */
public class Obstacles {

    private List <double []> obsarr = new ArrayList<>();
    public void addObstacles (double xCoor,double yCoor)
    {
        double [] coor = new double[2];
        coor[0]=xCoor;
        coor[1]=yCoor;
        obsarr.add(coor);
    }
    public void setObstacle (List<double[]> obsarr)
    {
        this.obsarr=obsarr;
    }
    public List getObstacle()
    {
        return obsarr;
    }
    public void printObstacles()
    {
        for ( double [] arr : obsarr)
        {
            System.out.println(" coor: ( " +arr[0]+","+arr[1]+")");
        }
    }
}
