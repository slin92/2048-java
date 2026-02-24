package TwentyFortyEight;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class App extends PApplet {

    public static int GRID_SIZE = 4; // 4x4 grid
    public static final int CELLSIZE = 100; // Cell size in pixels
    public static final int CELL_BUFFER = 8; // Space between cells
    public static final int TOP_UI_SPACE  = 60;
    public static int GRID_HEIGHT;
    public static int WIDTH;
    public static int HEIGHT; 
    public static final int FPS = 30;
    public static int HOVERED_ROW;
    public static int HOVERED_COL;
    public static String[] launchArgs;
    public int startTime;
    public boolean gameOver = false;
    int finalElapsedTime = 0;


    public static Random random = new Random();

    PImage tileblank;
    PImage tile2;
    PImage tile4;
    PImage tile8;
    PImage tile16;
    PImage tile32;
    PImage tile64;
    PImage tile128;
    PImage tile256;
    PImage tile512;
    PImage tile1024;
    PImage tile2048;
    PImage tile4096;

    Board board;
    Map<Integer, PImage> tileImages = new HashMap<>();


    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() {
        //this.configPath = "config.json";
        if (launchArgs != null && launchArgs.length > 0) {
            try {
                int size = Integer.parseInt(launchArgs[0]);
                if (size >= 2 && size <= 10) {
                    GRID_SIZE = size;
                }
            } catch (NumberFormatException e) {
                GRID_SIZE = 4;
            }
        } else {
            GRID_SIZE = 4;
        }
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        GRID_HEIGHT = GRID_SIZE * CELLSIZE;
        WIDTH = GRID_SIZE * CELLSIZE;
        HEIGHT = GRID_HEIGHT + TOP_UI_SPACE;
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player
     * and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
        // See PApplet javadoc:
        // loadJSONObject(configPath)
        // loadImage(this.getClass().getResource(filename).getPath().toLowerCase(Locale.ROOT).replace("%20",
        // " "));

        // create attributes for data storage, eg board
        tileblank = loadImage("piecesPNG/blank.png");
        tile2 = loadImage("piecesPNG/2.png");
        tile4 = loadImage("piecesPNG/4.png");
        tile8 = loadImage("piecesPNG/8.png");
        tile16 = loadImage("piecesPNG/16.png");
        tile32 = loadImage("piecesPNG/32.png");
        tile64 = loadImage("piecesPNG/64.png");
        tile128 = loadImage("piecesPNG/128.png");
        tile256 = loadImage("piecesPNG/256.png");
        tile512 = loadImage("piecesPNG/512.png");
        tile1024 = loadImage("piecesPNG/1024.png");
        tile2048 = loadImage("piecesPNG/2048.png");
        tile4096 = loadImage("piecesPNG/4096.png");

        tileImages.put(0, tileblank);
        tileImages.put(2, tile2);
        tileImages.put(4, tile4);
        tileImages.put(8, tile8);
        tileImages.put(16, tile16);
        tileImages.put(32, tile32);
        tileImages.put(64, tile64);
        tileImages.put(128, tile128);
        tileImages.put(256, tile256);
        tileImages.put(512, tile512);
        tileImages.put(1024, tile1024);
        tileImages.put(2048, tile2048);
        tileImages.put(4096, tile4096);

        board = new Board(GRID_SIZE);

        startTime = millis();
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if(key == 'r' || key =='R'){
            board = new Board(GRID_SIZE);
            startTime = millis();
            finalElapsedTime = 0;
            gameOver = false;
        }

        if (!gameOver) {
        int code = event.getKeyCode();

        if (code == LEFT && board.moveLeft()) {
            board.spawnRandomTile();
        } else if (code == RIGHT && board.moveRight()) {
            board.spawnRandomTile();
        } else if (code == UP && board.moveUp()) {
            board.spawnRandomTile();
        } else if (code == DOWN && board.moveDown()) {
            board.spawnRandomTile();
        }
    }
        

    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 37 ){
            board.spawnTileAt(e.getX(), e.getY());
        }
    }
        

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        // draw game board
        fill(255);
        rect(0, 0, WIDTH, TOP_UI_SPACE);

        fill(153, 140, 126);
        rect(0, TOP_UI_SPACE, WIDTH, HEIGHT - TOP_UI_SPACE);

        if(mouseY > TOP_UI_SPACE && mouseY < TOP_UI_SPACE + GRID_SIZE * CELLSIZE && mouseX >= 0 && mouseX < GRID_SIZE * CELLSIZE){
            HOVERED_ROW = (mouseY - TOP_UI_SPACE) / CELLSIZE;
            HOVERED_COL = mouseX / CELLSIZE;

        } else {
            HOVERED_ROW = -1;
            HOVERED_COL = -1;
        }

        board.drawGridBackground(this, HOVERED_ROW, HOVERED_COL);

        board.draw(this, tileImages);

        //Timer
        int elapsedSeconds;

        if (gameOver) {
            elapsedSeconds = finalElapsedTime;
        } else {
            elapsedSeconds = (millis() - startTime) / 1000;
            finalElapsedTime = elapsedSeconds;  // keep this updated until game ends
        }
        fill(119,110,101);
        textSize(24);
        textAlign(RIGHT,CENTER);
        text("Time: " + elapsedSeconds + "s", WIDTH - 20, TOP_UI_SPACE / 2);

        if(!gameOver && !board.canMove()){
            gameOver = true;

        }

        if(gameOver){
            fill(255,0,0);
            textSize(48);
            textAlign(CENTER,CENTER);
            text("GAME OVER", WIDTH / 2, HEIGHT / 2);

        }

    }

    public static void main(String[] args) {
        App.launchArgs = args;
        PApplet.main("TwentyFortyEight.App");
    }

}
