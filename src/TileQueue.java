import java.util.ArrayList;

public class TileQueue {
  
  ArrayList<Tile> queue = new ArrayList<Tile>();
 
  public void push(Tile pushedElement) {
    queue.add(pushedElement);  
  }
 
  public Tile pop() {
    return queue.remove(0);  
  }
 
  public Tile peek() {
    return queue.get(0);
  }
  
  
  
  public int getSize(){
    return queue.size();
  }
  
}