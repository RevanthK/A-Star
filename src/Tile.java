public class Tile { 
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
/*
  void update() { 
    fill(0);
    if (blocked)
      fill(255, 0, 0);
    else
      if (searched)
        fill(0, 0, 255);

    rect(x*7+21, y*7+21, 5, 5);
  } 
*/
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