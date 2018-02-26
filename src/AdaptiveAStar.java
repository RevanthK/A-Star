import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AdaptiveAStar {
    
    // two dimensional array, think rows and columns
    Tile[][] rects; //= new Tile[size+1][size+1];
    BinaryHeap<Tile> tq = new BinaryHeap<Tile>();
    //Queue<Tile> tq = new PriorityQueue<Tile>(); 
    
    String totalPathLength = "";
    
    boolean empty = false;
    int pathlength = 0;
    
    // ArrayList<String> pathTiles = new ArrayList<String>();
    static int size;// = 4;
    
    public AdaptiveAStar(Tile[][] rects, int size, int pathlength) {
        this.rects = rects;
        this.size = size;
        this.pathlength = pathlength;
    }
    
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
            
            /*
            System.out.println(" tile: " + start.pathLength + " " + start.x
                    + " " + start.y);
            */
            
            Tile[] neighbors = getNeighbors(start);
            
            for (Tile t : neighbors) {
                
                // if ((t.x+t.y)>(start.x+start.y) && t.blocked == false){
                if (t.found != true && t.blocked == false) {
                    t.g = start.g+1; 
                    t.Hnew(pathlength, size);                  
                    tq.push(t);
                    //tq.add(t);
                    t.found = true;
                }
                if (t.pathLength > start.pathLength+1) {
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
        
        Tile begin = rects[0][0];
        Tile target = rects[size][size];
        begin.pathLength = 0;
        begin.found = true;
        long startTime = System.nanoTime();
        if (search(begin, target)) {
            //System.out.print("\n" + target.path);
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println("Adaptive A Star: " + (totalTime/1000000000.0) + " seconds");
            writeToFile(target.path);
        } else {
            // blocked
        }
        
    }
    
    public static void writeToFile(String toWrite) {
        
        File file = new File("Path2.csv");
        if (!file.delete()) {
            System.out.println("no file data present, creating CSV.");
        }
        try {
            FileWriter writer = new FileWriter("Path2.csv", true);
            writer.write(size + "\n");         
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
