import java.io.FileNotFoundException;

import processing.core.PApplet;
import processing.data.Table;


public class Drawing extends PApplet{
    
    Tile[][] rects; // twodimensional array, think rows and columns

    Tile[][] rectsOrigin;
    int size;
    String filename = "RandomMaze1.csv";
    String fs = "RandomMaze";
    String fe = ".csv";
    int counter = 0;


    static float AStarTotalTime = 0, AdaptiveTotalTime = 0;
    float AStarTotalCycles = 0, AdaptiveTotalCycles = 0;
    

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
        int counter = 0;
        
        while(counter < 49){
            
            counter++;
        
            System.out.println("Testcase: " + counter);
            
            
        filename = fs + counter + fe;

        table = loadTable("Path.csv");
        
        size = table.getInt(0, 0);
        
        //size = 100;
        
        rects = new Tile[size+1][size+1];
        rectsOrigin = new Tile[size+1][size+1];
        
        readMaze();
        
        AStar A = new AStar(rects, size);
        //BackwardsAStar A = new BackwardsAStar(rects, size);

        try {
            A.run();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        
        //System.out.println();
        System.out.println(A.foundCounter + " states expanded initially in AStar");
        AStarTotalCycles += A.foundCounter;
        //System.out.println(A.hitCounter + " states(hit) expanded initially in AStar");
        //System.out.println();
        
        //readPath();
        
        //PL is g*: the path of least cost from start to goal
        int pl = rects[size][size].pathLength;
        
        for(int i=0; i<=size; i++){
            for(int j=0; j<=size; j++){
                
                rectsOrigin[i][j] = new Tile(rects[i][j].x, rects[i][j].y, rects[i][j].blocked, rects[i][j].found);
                
            }
        }
     
        
       
        
        for(int i=0; i<=size; i++){
            for(int j=0; j<=size; j++){
                
                // System.out.println(rects[i][j].found);

                //in the closed list, set h to g* - g(s)
                if(rects[i][j].found == true){
                    
                    rects[i][j].h = pl - rects[i][j].g;
                    
                } 
                else{
                    rects[i][j].h = size-i + size-j;
                    
                }
                rects[i][j].found = false;
                rects[i][j].searched = false;

                rects[i][j].g = 0;
                rects[i][j].f = 0;
                //System.out.println("updated heuristic: " + rects[i][j].h);
                rects[i][j].pathLength = Integer.MAX_VALUE;
                rects[i][j].hNew = 0;
                rects[i][j].path = "";
            }
        }
        
       // System.out.println("aaa " + closedListCounter);
                
        AdaptiveAStar A2 = new AdaptiveAStar(rects, size, pl);
        //BackwardsAStar A = new BackwardsAStar(rects, size);

        try {
            A2.run();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        
        readPath();

        //System.out.println();
        System.out.println(A2.foundCounter + " states expanded in Adaptive A Star");
        AdaptiveTotalCycles += A2.foundCounter;
        //System.out.println(A2.hitCounter + " states(hit) expanded initially in AStar");
        
        
        int cp = 0;
        for(int i=0; i<=size; i++){
            for(int j=0; j<=size; j++){
                if(rectsOrigin[i][j].found && rects[i][j].found){
                    //System.out.println("Tile x: "+ rects[i][j].x + " y: " + rects[i][j].y);
                    cp++;
                }
            }
        }
        
        
        }
        
        System.out.println("\nAvg AStar Cycles : " + (AStarTotalCycles/44));
        System.out.println("Avg Adaptive Cycles : " + (AdaptiveTotalCycles/44));
        
        System.out.println("\nAvg AStar Time : " + (AStarTotalTime/44));
        System.out.println("Avg Adaptive Time : " + (AdaptiveTotalTime/44));
        
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