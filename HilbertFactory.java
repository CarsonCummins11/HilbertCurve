import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

public class HilbertFactory {
    ArrayList<HilbertCurve> known_top_rights;
    ArrayList<HilbertCurve> known_top_lefts;
    ArrayList<HilbertCurve> known_lefts;
    ArrayList<HilbertCurve> known_rights;
    ArrayList<HilbertCurve> known;
    
    public HilbertFactory(){
        known_top_rights = new ArrayList<>();
        known_top_lefts = new ArrayList<>();
        known_lefts = new ArrayList<>();
        known_rights = new ArrayList<>();
        known = new ArrayList<>();
        known.add(build(2));
    }

    private Point[] curve(int dim){
        if(dim==2){
            return new Point[]{
                new Point(0,0),
                new Point(0,1),
                new Point(1,1),
                new Point(1,0)
            };
        }
        if(Math.pow(2, known.size())>=dim){
            return known.get(Util.log2Int(dim)-1).copy().points;
        }
        HilbertCurve c00 = buildleft(dim/2);
        HilbertCurve c01 = buildtopleft(dim/2);
        HilbertCurve c10 = buildright(dim/2);
        HilbertCurve c11 = buildtopright(dim/2);
        
        Point[] ret = new Point[dim*dim];
        for(int i = 0; i<(dim*dim)/4; i++){
            ret[i] = c00.points[i];
        }
        for(int i = (dim*dim)/4; i<(dim*dim)/2; i++){
            ret[i] = c01.points[i-(dim*dim)/4];
        }
        for(int i = (dim*dim)/2; i<3*(dim*dim)/4; i++){
            ret[i] = c11.points[i-(dim*dim)/2];
        }
        for(int i = 3*(dim*dim)/4; i<(dim*dim); i++){
            ret[i] = c10.points[i-3*(dim*dim)/4];
        }

        HilbertCurve ta = new HilbertCurve();
        ta.dim = dim;
        ta.points = ret;
        known.add(ta.copy());

        return ret;
    }
    public HilbertCurve build(int dim){
        dim = Util.closestPowerTwo(dim);
        HilbertCurve ret = buildtopleft(dim);
        ret.shiftUp(-dim);
        return ret;
    }
    public HilbertCurve buildtopleft(int nd){
        if(known_top_lefts.size() >= Util.log2Int(nd))return known_top_lefts.get(Util.log2Int(nd)-1).copy();
        HilbertCurve ret = new HilbertCurve();
        ret.dim = nd;
        ret.points = curve(nd);
        ret.shiftUp(nd);
        known_top_lefts.add(ret);
        return ret.copy();
    }
    public HilbertCurve buildtopright(int nd){
        if(known_top_rights.size() >= Util.log2Int(nd))return known_top_rights.get(Util.log2Int(nd)-1).copy();
        HilbertCurve ret = new HilbertCurve();
        ret.dim = nd;
        ret.points = curve(nd);
        ret.shiftUp(nd);
        ret.shiftRight(nd);
        known_top_rights.add(ret);
        return ret.copy();
    }
    public HilbertCurve buildleft(int nd){
        if(known_lefts.size() >= Util.log2Int(nd))return known_lefts.get(Util.log2Int(nd)-1).copy();
        HilbertCurve ret = new HilbertCurve();
        ret.dim = nd;
        ret.points = curve(nd);
        ret.mirrorLeft();
        known_lefts.add(ret);
        return ret.copy();
    }
    public HilbertCurve buildright(int nd){
        if(known_rights.size() >= Util.log2Int(nd))return known_rights.get(Util.log2Int(nd)-1).copy();
        HilbertCurve ret = new HilbertCurve();
        ret.dim = nd;
        ret.points = curve(nd);
        ret.mirrorRight();
        ret.shiftRight(nd);
        known_rights.add(ret);
        return ret.copy();
    }
    public void draw(Graphics g,int width,int hilbert, Color c){
        Point[] pts = build(hilbert).points;
        float scalart = ((float)width)/hilbert;
        int scalar = ((int)scalart);
        for (int i = 0; i<pts.length; i++){
            if(i+1<pts.length){
                g.setColor(c);
                g.drawLine(pts[i].x*scalar, pts[i].y*scalar, pts[i+1].x *scalar, pts[i+1].y *scalar);
            }
        }
    }
}
