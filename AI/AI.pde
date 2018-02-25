Tile[][] rects; // twodimensional array, think rows and columns
TileQueue tq;

int size = 100;

Table table;

void readMaze() {

  table = loadTable("RandomMaze1.csv");

  for (int r=0; r<=size; r++) // rows
  {
    for (int c=0; c<=size; c++) // columns per row
    {
      if (table.getFloat(r, c) > .9) {       
        rects[r][c] = new Tile(r, c, true);
        //tq.push(rects[r][c]);
      } else
        rects[r][c] = new Tile(r, c, false);
    }
  }
}

void readPath(){
  
  table = loadTable("Path.csv");
   
  
  for(int i=0;i<=size*2;i++){
     println(table.getInt(i,0) + " " +  table.getInt(i,1));
     rects[table.getInt(i,0)][table.getInt(i,1)].searched = true;  
  }
  
  
  
}


void setup ()
{
  size(750, 750);
  fill(0);
  noStroke();

  rects = new Tile[size+1][size+1];
  tq = new TileQueue();

  readMaze();
  readPath();

  //rects[0][0].pathLength = 0;
  //search(rects[0][0], rects[size][size]);
  //print("\n" + rects[size][size].path);
}

void draw ()
{
  background(255); // clear background with white
  for (int r=0; r<=size; r++) // rows
  {
    for (int c=0; c<=size; c++) // columns per row
    {
      rects[r][c].update();
    }
  }


  /*
  for(int i=0; i<tq.getSize(); i++){
   Tile t = tq.pop();
   print(t.x);
   println(" " + t.y);
   }
   */
}

void search(Tile start, Tile target) {

  //if (start.equals(target)){   
  if (start.x == size && start.y == size) {//
    start.path = start.path + "->" + start.x + "," + start.y;
    start.path += " Path Length = " + start.pathLength;
    return;
  }


  print(" tile: " + start.pathLength + " " + start.x + " " + start.y);

  Tile[] neighbors = getNeighbors(start);

  for (Tile t : neighbors) {



    //if ((t.x+t.y)>(start.x+start.y) && t.blocked == false){      
    if (t.searched != true && t.blocked == false) {
      tq.push(t);
      t.searched = true;
    }
    if (t.pathLength > start.pathLength) {
      t.path = start.path + "->" + start.x + "," + start.y;
      t.pathLength = start.pathLength+1;
    }
  }


  if (tq.getSize() != 0)
    search(tq.pop(), target);
  else
    print("No path");
}

Tile[] getNeighbors(Tile t) {

  //upper-left corner
  if (t.x == 0 && t.y == 0) {
    return new Tile[] {rects[t.x][t.y+1], rects[t.x+1][t.y]};
  }
  //upper-right corner
  if (t.x == size && t.y == 0) {
    return new Tile[] {rects[t.x][t.y+1], rects[t.x-1][t.y]};
  }
  //lower-left corner
  if (t.x == 0 && t.y == size) {
    return new Tile[] {rects[t.x][t.y-1], rects[t.x+1][t.y]};
  }
  //lower-right corner
  if (t.x == size && t.y == size) {
    return new Tile[] {rects[t.x][t.y-1], rects[t.x+1][t.y]};
  }
  /*********************************************************/

  //top wall
  if (t.y == 0) {
    return new Tile[] {rects[t.x][t.y+1], rects[t.x+1][t.y], rects[t.x-1][t.y]};
  } 
  //bottom wall
  if (t.y == size) {
    return new Tile[] {rects[t.x][t.y-1], rects[t.x+1][t.y], rects[t.x-1][t.y]};
  }
  //left wall
  if (t.x == 0) {
    return new Tile[] {rects[t.x][t.y+1], rects[t.x][t.y-1], rects[t.x+1][t.y]};
  }
  //right wall
  if (t.x == size) {
    return new Tile[] {rects[t.x][t.y+1], rects[t.x][t.y-1], rects[t.x-1][t.y]};
  }


  /*********************************************************/

  //if(t.x > 0 && t.y > 0 && t.x < 100 && t.y < 100)
  return new Tile[] {rects[t.x][t.y+1], rects[t.x][t.y-1], rects[t.x+1][t.y], rects[t.x-1][t.y]};
}
