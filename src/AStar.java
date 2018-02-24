import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class AStar {
    
    // two dimensional array, think rows and columns
    Tile[][] rects = new Tile[101][101];
    TileQueue tq = new TileQueue();
    
    String totalPathLength = "";
    
  
    //ArrayList<String> pathTiles = new ArrayList<String>();
    int size = 100;
    
    void search(Tile start, Tile target) {
        
        do {
            // if (start.equals(target)){
        
            if (start.x == size && start.y == size) {//
                start.path = start.path + start.x + "," + start.y + "\n";
                //changed from: start.path += " Path Length = " + start.pathLength;
                totalPathLength = "Path Length = " + start.pathLength;
                return;
            }
            
            if (tq.getSize() == 0) {
            		System.out.println("path is blocked, cannot be reached");
            		return;
            }
            
            System.out.println(" tile: " + start.pathLength + " " + start.x
                    + " " + start.y);
            
            Tile[] neighbors = getNeighbors(start);
            
            for (Tile t : neighbors) {
                
                // if ((t.x+t.y)>(start.x+start.y) && t.blocked == false){
                if (t.searched != true && t.blocked == false) {
                    tq.push(t);
                    t.searched = true;
                }
                if (t.pathLength > start.pathLength) {
                    t.path = start.path + start.x + "," + start.y + "\n";
                    t.pathLength = start.pathLength + 1;
                }
            }
            
            start = tq.pop();
        } while (true);
    }
    
    Tile[] getNeighbors(Tile t) {
        
        // upper-left corner
        if (t.x == 0 && t.y == 0) {
            return new Tile[] { rects[t.x][t.y + 1], rects[t.x + 1][t.y] };
        }
        // upper-right corner
        if (t.x == size && t.y == 0) {
            return new Tile[] { rects[t.x][t.y + 1], rects[t.x - 1][t.y] };
        }
        // lower-left corner
        if (t.x == 0 && t.y == size) {
            return new Tile[] { rects[t.x][t.y - 1], rects[t.x + 1][t.y] };
        }
        // lower-right corner
        if (t.x == size && t.y == size) {
            return new Tile[] { rects[t.x][t.y - 1], rects[t.x - 1][t.y] };
        }
        /*********************************************************/
        
        // top wall
        if (t.y == 0) {
            return new Tile[] { rects[t.x][t.y + 1], rects[t.x + 1][t.y],
                    rects[t.x - 1][t.y] };
        }
        // bottom wall
        if (t.y == size) {
            return new Tile[] { rects[t.x][t.y - 1], rects[t.x + 1][t.y],
                    rects[t.x - 1][t.y] };
        }
        // left wall
        if (t.x == 0) {
            return new Tile[] { rects[t.x][t.y + 1], rects[t.x][t.y - 1],
                    rects[t.x + 1][t.y] };
        }
        // right wall
        if (t.x == size) {
            return new Tile[] { rects[t.x][t.y + 1], rects[t.x][t.y - 1],
                    rects[t.x - 1][t.y] };
        }
        
        /*********************************************************/
        
        // if(t.x > 0 && t.y > 0 && t.x < 100 && t.y < 100)
        return new Tile[] { rects[t.x][t.y + 1], rects[t.x][t.y - 1],
                rects[t.x + 1][t.y], rects[t.x - 1][t.y] };
    }
    
    public void run() throws FileNotFoundException {
        // TODO Auto-generated method stub
        
        String fileNameDefined = "RandomMaze1.csv";
        // -File class needed to turn stringName to actual file
        File file = new File(fileNameDefined);
        
        // -read from filePooped with Scanner class
        Scanner inputStream = new Scanner(file);
        int row = 0;
        // hashNext() loops line-by-line
        while (inputStream.hasNext()) {
            // read single line, put in string
            String data = inputStream.next();
            String[] line = data.split(",");
            int col = 0;
            for (String s : line) {
                Double d = Double.valueOf(s);
                boolean isBlocked = (d > .9);
                rects[row][col] = new Tile(row, col, isBlocked);
                
                col++;
                
            }
            row++;
        }
        // after loop, close scanner
        inputStream.close();
        
        System.out.println(rects[0][0].pathLength);
        
        rects[0][0].pathLength = 0;
        rects[0][0].searched = true;
        search(rects[0][0], rects[size][size]);
        System.out.print("\n" + rects[size][size].path);
        writeToFile(rects[size][size].path);
        
    }
    
    
    public static void writeToFile(String toWrite) 
	{
    	
    		File file = new File("AStar.csv");
		if(!file.delete()) {
			System.out.println("no file data present, creating CSV.");
		}
		try 
		{
			FileWriter writer = new FileWriter("AStar.csv", true);
			writer.write(toWrite);
			writer.close();
		}catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
    
}
