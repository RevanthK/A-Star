import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class AStar {
    
    // two dimensional array, think rows and columns
    Tile[][] rects; //= new Tile[size+1][size+1];
    BinaryHeap tq = new BinaryHeap();
    //Queue<Tile> tq = new PriorityQueue<Tile>(); 
    
    String totalPathLength = "";
    
    boolean empty = false;
    
    // ArrayList<String> pathTiles = new ArrayList<String>();
    static int size;// = 4;
    static String filename = "RandomMaze1.csv";
    
    boolean search(Tile start, Tile target) {
        
        do {
            // if (start.equals(target)){
            
            if (start.x == size && start.y == size) {//
                start.path = start.path + start.x + "," + start.y + "\n";
                // changed from: start.path += " Path Length = " +
                // start.pathLength;
                totalPathLength = "Path Length = " + start.pathLength;
                return true;
            }
            
            System.out.println(" tile: " + start.pathLength + " " + start.x
                    + " " + start.y);
            
            Tile[] neighbors = getNeighbors(start);
            
            for (Tile t : neighbors) {
                
                // if ((t.x+t.y)>(start.x+start.y) && t.blocked == false){
                if (t.searched != true && t.blocked == false) {
                    t.g = start.g+1;
                    t.H(size);
                    tq.push(t);
                    //tq.add(t);
                    t.searched = true;
                }
                if (t.pathLength > start.pathLength) {
                    t.path = start.path + start.x + "," + start.y + "\n";
                    t.pathLength = start.pathLength + 1;
                }
            }
            
            if (tq.size() == 0) {
                
                System.out.println("path is blocked, cannot be reached");
                writeToFile(start.path);
                return false;              
            }
            
            start = (Tile) tq.pop();
            //start = (Tile) tq.remove();
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
        
        String fileNameDefined = filename;
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
            size = line.length - 1;
            if(row == 0)
                rects = new Tile[line.length][line.length];
            
            int col = 0;
            for (String s : line) {
                Double d = Double.valueOf(s);
                boolean isBlocked = (d >= .9);
                rects[col][row] = new Tile(col, row, isBlocked);
                
                col++;
                
            }
            row++;
        }
        // after loop, close scanner
        inputStream.close();
        
        rects[0][0].pathLength = 0;
        rects[0][0].searched = true;
        if (search(rects[0][0], rects[size][size])) {
            System.out.print("\n" + rects[size][size].path);
            writeToFile(rects[size][size].path);
        } else {
            // blocked
        }
        
    }
    
    public static void writeToFile(String toWrite) {
        
        File file = new File("Path.csv");
        if (!file.delete()) {
            System.out.println("no file data present, creating CSV.");
        }
        try {
            FileWriter writer = new FileWriter("Path.csv", true);
            writer.write(size + "\n");         
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
