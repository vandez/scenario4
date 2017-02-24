package scenario4;


/**
 * Created by Nancy Amelia on 2/22/2017.
 */ // point structure with x,y are coordinates
class Point{
	public double x, y;
	//parameterized constructor
	public Point(double _X, double _Y){
		x = _X;
		y = _Y;
	}
	//copy constructor
	public Point(Point p) {
		x = p.x;
		y = p.y;
	}
	//compare operation
	public boolean Equals(Point p) {
		if (Math.abs(this.x - p.x) > 0.00001)
			return false;
		if (Math.abs(this.y - p.y) > 0.00001)
			return false;
		return true;
	}
	//calculate distance to a point
	public double distance(Point p) {
		double dx = x - p.x;
		double dy = y - p.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
}
