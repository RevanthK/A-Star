public class Tile{ 
  boolean blocked;
  int x, y;
  boolean searched;
  
  
  public int pathLength = Integer.MAX_VALUE;
  String path = "";


  Tile (int x, int y) {  
    this.x = x;
    this.y = y;
    blocked = false;
    searched = false;
  } 

  Tile (int x, int y, boolean blocked) { 
    this.x = x;
    this.y = y;
    this.blocked = blocked;
    searched = false;
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
  
  boolean equals(Tile t){
     if(this.x == t.x && this.y == t.y)
       return true;
     return false;
  }
} 