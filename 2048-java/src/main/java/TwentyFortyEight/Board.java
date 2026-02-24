package TwentyFortyEight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;

public class Board extends App{
    private Tile[][] tiles;
    private int size;
    private Random random;

    public Board(int size) {
        this.size = size;
        this.tiles = new Tile[size][size];
        this.random = new Random();
        spawnRandomTile();
        spawnRandomTile();
    }

    public void draw(PApplet app, Map<Integer, PImage> tileImages) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Tile tile = tiles[row][col];
                if (tile != null) {
                    tile.updateAnimation();
                    tile.draw(app, tileImages);
                }
            }
        }
    }

    // Get a list of all empty cells
    private List<int[]> getEmptyCells() {
        List<int[]> empty = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (tiles[row][col] == null) {
                    empty.add(new int[]{row, col});
                }
            }
        }
        return empty;
    }
    
    
    public void drawGridBackground(PApplet app, int hoveredRow, int hoveredCol){
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = col * App.CELLSIZE;
                int y = row * App.CELLSIZE + App.TOP_UI_SPACE;
                int tileSize = CELLSIZE - CELL_BUFFER;

                if (tiles[row][col] == null && row == hoveredRow && col == hoveredCol) {
                    app.fill(225, 213, 200); // lighter for hover
                    
                } else {
                    app.fill(205, 193, 180); // regular empty tile
                    app.noTint();
                }
                
                app.noStroke();
                app.rect(x, y, tileSize, tileSize, 10);
            }
        }


    }
    

    // Spawn a 2 or 4 in a random empty cell
    public void spawnRandomTile() {
        List<int[]> emptyCells = getEmptyCells();
        if (emptyCells.isEmpty()) return;

        int[] cell = emptyCells.get(random.nextInt(emptyCells.size()));
        int row = cell[0];
        int col = cell[1];
        int value = random.nextBoolean() ? 2 : 4;
        tiles[row][col] = new Tile(value, row, col);

        Tile tile = new Tile(value, row, col);
        tile.setAnimPosition();
        tiles[row][col] = tile;
    }

    // Spawn a tile at a given mouse position if the cell is empty
    public void spawnTileAt(int mouseX, int mouseY) {
        int row = (mouseY - App.TOP_UI_SPACE) / App.CELLSIZE;
        int col = mouseX / App.CELLSIZE;
        
        // Ensure the calculated cell is within bounds
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return;
        }
        
        // Only spawn a tile if the cell is empty
        if (tiles[row][col] == null) {
            int value = random.nextBoolean() ? 2 : 4;
            tiles[row][col] = new Tile(value, row, col);
        }
    }

    public boolean canMove(){
        List<int[]> emptyCells = getEmptyCells();
        if (!emptyCells.isEmpty()){
            return true;
        } else {
            for(int row = 0; row < size; row++){
                for(int col = 0; col < size; col++){
                    Tile current = tiles[row][col];
                    if(current == null) continue;

                    if(col + 1 < size){
                        Tile right = tiles[row][col+1];
                        if(right != null && current.getValue() == right.getValue()){
                            return true;
                        }
                    }

                    if(row + 1 < size){
                        Tile below = tiles[row + 1][col];
                        if(below != null && current.getValue() == below.getValue()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //movement:
    public boolean moveLeft(){
        boolean moved = false;

        for(int row = 0; row < size; row++){
            List<Tile> line = new ArrayList<>();

            for(int col = 0; col < size; col++){
                if(tiles[row][col] != null){
                    line.add(tiles[row][col]);
                }
            }

            for(int i = 0; i < line.size() - 1; i++){
                Tile current = line.get(i);
                Tile next = line.get(i + 1);

                if(current.getValue() == next.getValue()){
                    current.setValue(current.getValue() * 2);
                    line.remove(i +1);
                    moved = true;
                }
            }

            for(int col = 0 ; col < size; col++){
                if(col < line.size()){
                    Tile tile = line.get(col);
                    tile.setPosition(row, col);
                    if(tiles[row][col] != tile){
                        moved = true;
                    }
                    tiles[row][col] = tile;
                } else {
                    tiles[row][col] = null;
                }
            }
        }

        return moved;
    }

    
    public boolean moveRight(){
        boolean moved = false;

        for(int row = 0; row < size; row++){
            List<Tile> line = new ArrayList<>();

            for(int col = size -1 ; col >= 0; col--){
                if(tiles[row][col] != null){
                    line.add(tiles[row][col]);
                }
            }

            for(int i = 0; i < line.size() - 1; i++){
                Tile current = line.get(i);
                Tile next = line.get(i + 1);

                if(current.getValue() == next.getValue()){
                    current.setValue(current.getValue() * 2);
                    line.remove(i +1);
                    moved = true;
                }
            }

            for(int col = size - 1, i = 0 ; col >= 0; col--, i++){
                if(i < line.size()){
                    Tile tile = line.get(i);
                    tile.setPosition(row, col);
                    if(tiles[row][col] != tile){
                        moved = true;
                    }
                    tiles[row][col] = tile;
                } else {
                    tiles[row][col] = null;
                }
            }
        }

        return moved;
    }

    public boolean moveUp(){
        boolean moved = false;

        for(int col = 0; col < size; col++){
            List<Tile> line = new ArrayList<>();

            for(int row = 0; row < size; row++){
                if(tiles[row][col] != null){
                    line.add(tiles[row][col]);
                }
            }

            for(int i = 0; i < line.size() - 1; i++){
                Tile current = line.get(i);
                Tile next = line.get(i + 1);

                if(current.getValue() == next.getValue()){
                    current.setValue(current.getValue() * 2);
                    line.remove(i +1);
                    moved = true;
                }
            }

            for(int row = 0 ; row < size; row++){
                if(row < line.size()){
                    Tile tile = line.get(row);
                    tile.setPosition(row, col);
                    if(tiles[row][col] != tile){
                        moved = true;
                    }
                    tiles[row][col] = tile;
                } else {
                    tiles[row][col] = null;
                }
            }
        }

        return moved;
    }

    public boolean moveDown(){
        boolean moved = false;

        for(int col = 0; col < size; col++){
            List<Tile> line = new ArrayList<>();

            for(int row = size -1; row >= 0; row--){
                if(tiles[row][col] != null){
                    line.add(tiles[row][col]);
                }
            }

            for(int i = 0; i < line.size() - 1; i++){
                Tile current = line.get(i);
                Tile next = line.get(i + 1);

                if(current.getValue() == next.getValue()){
                    current.setValue(current.getValue() * 2);
                    line.remove(i +1);
                    moved = true;
                }
            }

            for(int row = size -1, i = 0 ; row >= 0; row--, i++){
                if(i < line.size()){
                    Tile tile = line.get(i);
                    tile.setPosition(row, col);
                    if(tiles[row][col] != tile){
                        moved = true;
                    }
                    tiles[row][col] = tile;
                } else {
                    tiles[row][col] = null;
                }
            }
        }

        return moved;
    }
    

}
