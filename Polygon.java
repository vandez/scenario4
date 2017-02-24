package scenario4;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nancy Amelia on 2/22/2017.
 */ // polygon structure
class Polygon {
	public List<Point> vertices;
	// null constructor
	public Polygon() {
		vertices = new ArrayList<Point>();
	}
	// add a vertex
	public void addVertex(double _x, double _y) {
		Point p = new Point(_x, _y);
		vertices.add(p);
	}
	// number of vertices
	public int size() {
		return vertices.size();
	}
	// index of vertice
	public int IndexOf(Point p) {
		for (int i = 0; i < size(); i++)
			if (vertices.get(i).Equals(p))
				return i;
		return -1;
	}

	// check if a point is inside this polygon or not
	public boolean Inside(Point point) {
		int j = vertices.size() - 1;
		boolean oddNodes = false;

		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).y < point.y && vertices.get(j).y >= point.y ||
					vertices.get(j).y < point.y && vertices.get(i).y >= point.y) {
				if (vertices.get(i).x +
						(point.y - vertices.get(i).y) / (vertices.get(j).y - vertices.get(i).y) * (vertices.get(j).x - vertices.get(i).x) < point.x) {
					oddNodes = !oddNodes;
				}
			}
			j = i;
		}

		return oddNodes;
	}

	// check if a part of the segment, of which 2 end points are the polygon's vertices, is inside this or not
	public boolean Intersect(Segment s) {
		// a triangle does not Intersect any segment of which end points are the triangle's vertices
		// if (size() == 3)
		//	return false;
		// polygon has more than 3 vertices
		// split the big segment into parts
		Point split_point = null;
		for (int i = 0; i < size(); i++) {
			Point p1 = vertices.get(i);
			Point p2 = vertices.get((i + 1) % size());
			Segment edge = new Segment(p1, p2);
			split_point = s.InterSectionExceptThisEnds(edge);
			if (split_point != null)
				break;
		}
		// if we can split
		if (split_point != null) // then check each part
		{
			boolean first_part = Intersect(new Segment(s.p1, split_point));
			if (first_part == true) // a part intersects means whole segment intersects
				return first_part;
			// if first part doesn't intersect
			// it depends on second one
			boolean second_part = Intersect(new Segment(split_point, s.p2));
			return second_part;
		}
		// cannot split this segment
		else {
			boolean result = Cover(s);
			return result;
		}
	}

	public boolean Cover(Segment s) {
		// if segment is a edge of this polygon
		int p1_pos = this.IndexOf(s.p1);
		int p2_pos = this.IndexOf(s.p2);
		if (p1_pos != -1 && p2_pos != -1) {
			int pos_distance = Math.abs(p1_pos - p2_pos);
			if (pos_distance == 1 || pos_distance == size() - 1) // adjcent vertices
				return false;
		}
		// segment is unseparatable
		// so,if the mid point is inside polygon, whole segment will inside
		Point mid = s.MidPoint();

		if (this.Inside(mid))
			return true;
		else
			return false;
	}

	// check if a point is one of this polygon vertices
	public boolean isVertex(Point p) {
		for (int i = 0; i < vertices.size(); i++)
			if (vertices.get(i).Equals(p))
				return true;
		return false;
	}

	public static void main(String[] args)
	{
		List <Polygon> AllPoly  = new ArrayList<>();

		Polygon p = new Polygon();
		p.addVertex(0,1);
		p.addVertex(2,3);
		p.addVertex(4,1);
		p.addVertex(4,10);
		p.addVertex(0,10);

		Point x1 = new Point(0,1);
		Point x2 = new Point(0,10);
		Point x3 = new Point(5,0);
		Point x4 = new Point(4.5,3.5);
		Point x5 = new Point(4.6,-3);
		//double robot[][] = {{0,1}, {2, 0}, {3, 5}, {6, 2},{9, 0}};
		//double obs2[][] = {{8, 1}, {4, 1}, {4, 4}, {5, 2}};
//-0.05704426993442535, 0.0443520770557942)
		Polygon p2 = new Polygon();
		p2.addVertex(-0.05704426993442535, 0.0443520770557942);


		//{{0,1}, {2, 0}, {3, 5}, {6, 2},{9, 0}};
		Segment test  = new Segment(x1,x2);
		boolean result = p.Intersect(test);
		System.out.println(result);


		// double obs1[][] = {{1,2}, {1, 4}, {3, 4}, {3, 2}};

	}
}
