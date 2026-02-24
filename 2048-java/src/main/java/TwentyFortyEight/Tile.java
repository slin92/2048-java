package TwentyFortyEight;

import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

public class Tile extends App{
    private int value;
    private int row;
    private int col;
    private float animX;
    private float animY;

    public Tile(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setAnimPosition(){
        this.animX = col * App.CELLSIZE ;
        this.animY = row * App.CELLSIZE + App.TOP_UI_SPACE ;
    }

    public void updateAnimation(){
        float targetX = col * App.CELLSIZE ;
        float targetY = row * App.CELLSIZE + App.TOP_UI_SPACE ;

        animX += (targetX - animX) * 0.5;
        animY += (targetY - animY) * 0.5;
    }

    // Draw the tile at its corresponding grid position
    public void draw(PApplet app, Map<Integer, PImage> tileImages) {
        int px = col * App.CELLSIZE;
        int py = row * App.CELLSIZE + App.TOP_UI_SPACE;
        int tileSize = App.CELLSIZE - App.CELL_BUFFER;

        boolean isHovered = (this.row == App.HOVERED_ROW && this.col == App.HOVERED_COL);

        if(isHovered){
            app.tint(255, 255, 255, 180); //tint over images
        } else{
            app.noTint();
        }
        // Getting the correct image for the tile value. 
        PImage img = tileImages.getOrDefault(value, tileImages.get(2048));
        app.image(img, animX, animY, tileSize, tileSize );
    }
}
