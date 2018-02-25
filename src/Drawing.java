import java.io.FileNotFoundException;

import processing.core.PApplet;
import processing.data.Table;


public class Drawing extends PApplet{
    
    Tile[][] rects; // twodimensional array, think rows and columns

    int size;
    

    Table table;

    void readMaze() {
    
        table = loadTable(AStar.filename);
        

        for (int r=0; r<=size; r++) // rows
        {
          for (int c=0; c<=size; c++) // columns per row
          {
            if (table.getFloat(r, c) >= .8) {       
              rects[c][r] = new Tile(c, r, true);
              //tq.push(rects[r][c]);
            } else
              rects[c][r] = new Tile(c, r, false);
          }
        }
      }

      void readPath(){
         
        table = loadTable("Path.csv");

         
        
        for(int i=1;i<table.getRowCount();i++){
           //println(table.getInt(i,0) + " " +  table.getInt(i,1));
           rects[table.getInt(i,0)][table.getInt(i,1)].searched = true;  
        }
        
        
        
      }
    
    
    public static void main(String[] args) {
        PApplet.main("Drawing");
    }

    public void settings(){
        //AStar ass = new AStar();
        BackwardsAStar ass = new BackwardsAStar();

        try {
            ass.run();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
        size(750, 750);
    }

    public void setup(){

        fill(0);
        noStroke();

        table = loadTable("Path.csv");
        
        size = table.getInt(0, 0);
        
        rects = new Tile[size+1][size+1];

        readMaze();
        readPath();


    }

    public void draw(){
        background(255); // clear background with white
        for (int r=0; r<=size; r++) // rows
        {
          for (int c=0; c<=size; c++) // columns per row
          {
            update(rects[c][r]);
          }
        }
    }
    
    
    
    void update(Tile t) { 
        fill(0);
        if (t.blocked)
          fill(255, 0, 0);
        else
          if (t.searched)
            fill(0, 230, 230);

        rect(t.x*7+21, t.y*7+21, 5, 5);
      } 

}