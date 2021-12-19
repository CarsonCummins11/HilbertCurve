public class Point {
  int x;
  int y;
  public Point(int xx, int yy){
    x = xx;
    y = yy;
  }  
  @Override
  public String toString(){
      return "point: "+x+","+y;
  }
}
