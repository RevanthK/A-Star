import java.io.FileNotFoundException;

import processing.core.PApplet;
import processing.data.Table;


public class Drawing extends PApplet{
    
    Tile[][] rects; // twodimensional array, think rows and columns

    int size;
    String filename = "RandomMaze4.csv";
    int counter = 0;

    Table table;

    void readMaze() {
    
        table = loadTable(filename);
        

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
         
        table = loadTable("Path2.csv");        
        
        for(int i=1;i<table.getRowCount();i++){
           //println(table.getInt(i,0) + " " +  table.getInt(i,1));
           rects[table.getInt(i,0)][table.getInt(i,1)].searched = true; 
           counter++;
           //System.out.println(counter);
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

        table = loadTable("Path.csv");
        
        size = table.getInt(0, 0);
        
        //size = 100;
        
        rects = new Tile[size+1][size+1];
        
        readMaze();
        
        AStar A = new AStar(rects, size);
        //BackwardsAStar A = new BackwardsAStar(rects, size);

        try {
            A.run();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        
           
       // readPath();
        
        int pl = rects[size][size].pathLength;
     
        
        for(int i=0; i<=size; i++){
            for(int j=0; j<=size; j++){
                rects[i][j].found = false;
                rects[i][j].searched = false;
                rects[i][j].g = 0;
                rects[i][j].h = 0;
                rects[i][j].f = 0;
                rects[i][j].pathLength = Integer.MAX_VALUE;
                rects[i][j].hNew = 0;
                rects[i][j].path = "";
            }
        }
        
                
        AdaptiveAStar A2 = new AdaptiveAStar(rects, size, pl);
        //BackwardsAStar A = new BackwardsAStar(rects, size);

        try {
            A2.run();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        
        readPath();
        
        System.out.println(rects[size][size].pathLength + " steps");
        System.out.println("Found: " + A.foundCounter);
        
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
        else{
          if (t.found)
            fill(0, 200, 200);
          if(t.searched)
              fill(0,255,0);
        }
        
        rect(t.x*7+21, t.y*7+21, 5, 5);
      } 

}