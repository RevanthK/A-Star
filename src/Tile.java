public class Tile implements Comparable {
    boolean blocked;
    int x, y;
    boolean searched;
    
    int f, g, h;
    
    public int pathLength = Integer.MAX_VALUE;
    String path = "";
    
    Tile(int x, int y) {
        this.x = x;
        this.y = y;
        blocked = false;
        searched = false;
        f = 0;
        g = 0;
        h = 0;
    }
    
    Tile(int x, int y, boolean blocked) {
        this.x = x;
        this.y = y;
        this.blocked = blocked;
        searched = false;
        f = 0;
        g = 0;
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
    
    boolean equals(Tile t) {
        if (this.x == t.x && this.y == t.y)
            return true;
        return false;
    }
    
    void H(int size){
        h = size-x + size-y;
        F();
    }
    
    void G(int g){
        this.g = g; 
        F();
    }
    
    void F(){
        f = g+h;       
    }
    
    @Override
    public int compareTo(Object o) {
        
        Tile t = (Tile) o;
        
        if(this.f > t.f)
            return 1;
        if(this.f == t.f)    
            return 0;
        
        return -1;
    }
}