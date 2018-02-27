public class Tile implements Comparable {
    boolean blocked;
    int x, y;
    boolean searched, found;
    //delete if not needed
    
    int f, g, h, hNew;
    
    public int pathLength = Integer.MAX_VALUE;
    String path = "";
    
    Tile(int x, int y) {
        this.x = x;
        this.y = y;
        blocked = false;
        searched = false;
        found = false;
        f = 0;
        g = Integer.MAX_VALUE;
        h = 0;
    }
    
    Tile(int x, int y, boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
        searched = false;
        found = false;
        f = 0;
        g = Integer.MAX_VALUE;
        h = 0;
    }
    
    Tile(int x, int y, boolean blocked, boolean found) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
        searched = false;
        this.found = found;
        f = 0;
        g = Integer.MAX_VALUE;
        h = 0;
    }
    
    boolean isBlocked() {
        return blocked;
    }
    
    void setBlock(boolean b) {
        blocked = b;
    }
    
    boolean isSearched() {
        return searched;
    }
    
    void setSearch(boolean b) {
        searched = b;
    }
    
    boolean isFound() {
        return searched;
    }
    
    void setFound(boolean b) {
        searched = b;
    }
    
    boolean equals(Tile t) {
        if (this.x == t.x && this.y == t.y)
            return true;
        return false;
    }
    
    void H(int size, boolean forwards){
        if (forwards) h = size-x + size-y;
        else h = x + y;
        F(h);
    }
    
    void G(int g){
        this.g = g; 
        F(h);
    }
    
    void F(int h){
        f = g+h;       
    }
    
    //forget hnew
    void Hnew(int pathLength, int size){
        //this.hNew = pathLength - (2*size) + this.h;
        F(hNew);
    }
    
    @Override
    public int compareTo(Object o) {
        
        Tile t = (Tile) o;
        
        if(this.f > t.f)
            return 1;
        if(this.f == t.f) {
//part 2
        	
        		if(this.g > t.g) {
        			//return 1;
        			return -1;
        		} 
        		if(this.g == t.g) {
            
        			return ((int) Math.round(Math.random()))*2-1;
        		}
        		if(this.g < t.g) {
        			//return -1;
        return 1;
        		}
        		
        		return 0;

        } 
               
        return -1;
    }
}