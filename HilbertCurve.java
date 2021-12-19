public class HilbertCurve {
    Point[] points;
    int dim;
    int minx;
    int miny;
    int[][] pointLookup;
    boolean searchable = false;
    public HilbertCurve(){
        minx = 0;
        miny = 0;
    }
    public Point get2DFromHilbert(int i){
        return points[i];
    }
    public int getHilbertPosition(Point p){
        if(searchable){
            return pointLookup[p.x][p.y];
        }else{
            pointLookup = new int[dim][dim];
            for(int i = 0; i<points.length; i++){
                pointLookup[points[i].x][points[i].y] = i;
            }
            searchable = true;
            return pointLookup[p.x][p.y];
        }
    }
    //mirrors the curve over the line y=x
    public void mirrorLeft(){
        for(int i = 0; i<points.length; i++){
            points[i] = new Point(points[i].y,points[i].x);
        }
    }
    //mirrors the curve over the line y=-x
    public void mirrorRight(){
        for(int i = 0; i<points.length; i++){
            int px = (int)((-(points[i].x - minx - (dim/2 - 0.5)))+ minx + dim/2 - 0.5);
            int py = (int)((-(points[i].y - miny - (dim/2 - 0.5)))+ miny + dim/2 - 0.5);
            points[i] = new Point(py ,px);
        }
    }
    public HilbertCurve copy(){
        Point[] nhc = new Point[points.length];
        for(int i = 0; i<points.length; i++){
            nhc[i] = points[i];
        }
        HilbertCurve ret = new HilbertCurve();
        ret.points = nhc;
        ret.dim = dim;
        return ret;
    }

    public void shiftUp(int by){
        for(int i = 0; i<points.length; i++){
            points[i] = new Point(points[i].x,points[i].y+by);
        }
        miny+=by;
    }
    public void shiftRight(int by){
        for(int i = 0; i<points.length; i++){
            points[i] = new Point(points[i].x+by,points[i].y);
        }
        minx+=by;
    }
}
