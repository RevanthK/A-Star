import processing.core.PApplet;
import processing.data.Table;

public class Drawing extends PApplet{
    
    Tile[][] rects; // twodimensional array, think rows and columns
    TileQueue tq;

    int size = 100;

    Table table;

    void readMaze() {

        table = loadTable("RandomMaze1.csv");

        for (int r=0; r<=size; r++) // rows
        {
          for (int c=0; c<=size; c++) // columns per row
          {
            if (table.getFloat(r, c) > .9) {       
              rects[r][c] = new Tile(r, c, true);
              //tq.push(rects[r][c]);
            } else
              rects[r][c] = new Tile(r, c, false);
          }
        }
      }

      void readPath(){
        
        table = loadTable("Path.csv");
         
        
        for(int i=0;i<=size*2;i++){
           println(table.getInt(i,0) + " " +  table.getInt(i,1));
           rects[table.getInt(i,0)][table.getInt(i,1)].searched = true;  
        }
        
        
        
      }
    
    
    public static void main(String[] args) {
        PApplet.main("Drawing");
    }

    public void settings(){
        size(750, 750);
    }

    public void setup(){

        fill(0);
        noStroke();

        rects = new Tile[size+1][size+1];
        tq = new TileQueue();

        readMaze();
        readPath();
    }

    public void draw(){
        background(255); // clear background with white
        for (int r=0; r<=size; r++) // rows
        {
          for (int c=0; c<=size; c++) // columns per row
          {
            update(rects[r][c]);
          }
        }
    }
    
    
    
    void update(Tile t) { 
        fill(0);
        if (t.blocked)
          fill(255, 0, 0);
        else
          if (t.searched)
            fill(0, 0, 255);

        rect(t.x*7+21, t.y*7+21, 5, 5);
      } 

}