package com.snake.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.snake.game.GridSprite;
import com.snake.game.Snake;

import java.util.ArrayList;

public class GameScreen extends BaseScreen {

    //Grid size and movement interval
    public static final int GRID_SIZE = 20;
    private static final int MOVE_INCREMENT = 125;

    private OrthographicCamera camera;
    private GridSprite head;
    private GridSprite cherry;
    private BitmapFont font;


    //Keeps track of how long its been since movement
    private float moveTime;

    private int snakeLength;
    private Integer score = 0;

    //Height and width of our grid
    private int gridWidth;
    private int gridHeight;

    //Lists of information of where the cherry is
    private ArrayList<GridSprite> bodySprites;
    private Texture bodyTexture;
    private Texture headTexture;
    private Texture cherryTexture;

    public enum DirectionState {
        RIGHT, LEFT, UP, DOWN
    }

    public DirectionState direction;

    /*public enum GameState {
        RUNNING, PAUSED
    }

    public GameState state;*/

    public GameScreen(Snake game) {
        super(game);
    }

    @Override
    public void create() {
        direction = DirectionState.RIGHT;

        /*state = GameState.RUNNING;*/

        //creating a camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 480);

        //Setting the sizes of our grid
        gridWidth = (int) (camera.viewportWidth / GRID_SIZE);
        gridHeight = (int) (camera.viewportHeight / GRID_SIZE);

        //Snake and cherry textures
        snakeLength = 3;
        headTexture = new Texture("images.png");
        bodyTexture = new Texture("images.png");
        cherryTexture = new Texture("cherries.png");

        //Initializing the cherry sprite and it's position
        head = new GridSprite(headTexture, gridWidth, gridHeight);
        head.setGridX(snakeLength + 1);
        head.setGridY(1);
        cherry = new GridSprite(cherryTexture, gridWidth, gridHeight);
        cherry.randomizePosition();

        bodySprites = new ArrayList<>();
        for (int i = 0; i < snakeLength; i++) {
            GridSprite sprite = new GridSprite(bodyTexture, gridWidth, gridHeight);
            sprite.setGridX(i + 1);
            sprite.setGridY(1);
            bodySprites.add(sprite);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        moveTime += delta * 1000;

        font = new BitmapFont();

        //Setting the direction state based on the input from the keys
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direction = DirectionState.RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) direction = DirectionState.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) direction = DirectionState.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) direction = DirectionState.DOWN;

        //Moving the head if the required amount of time has passed
        if (moveTime > MOVE_INCREMENT) {
            // Move body sprites
            for (int i = bodySprites.size() - 1; i >= 0; i--) {
                GridSprite sprite = bodySprites.get(i), nextSprite;
                if (i > 0) {
                    nextSprite = bodySprites.get(i - 1);
                } else {
                    nextSprite = head;
                }
                sprite.setGridX(nextSprite.getGridX());
                sprite.setGridY(nextSprite.getGridY());
            }

            //Moves the head
            move(direction);

            //Subtracting the move time after the action was completed
            moveTime -= MOVE_INCREMENT;

            if (head.getGridX() == cherry.getGridX() &&
                    head.getGridY() == cherry.getGridY()) {
                cherry.randomizePosition();
                score += 100;

                GridSprite lastBodySprite = bodySprites.get(bodySprites.size() - 1);
                GridSprite secondLastBodySprite = bodySprites.get(bodySprites.size() - 2);
                GridSprite sprite = new GridSprite(bodyTexture, gridWidth, gridHeight);

                sprite.setGridX(lastBodySprite.getGridX());
                sprite.setGridY(lastBodySprite.getGridY());

                if (lastBodySprite.getGridX() != secondLastBodySprite.getGridX()) {
                    sprite.translateX(lastBodySprite.getGridX() - secondLastBodySprite.getGridX());
                }
                if (lastBodySprite.getGridY() != secondLastBodySprite.getGridY()) {
                    sprite.translateY(lastBodySprite.getGridY() - secondLastBodySprite.getGridY());
                }
                bodySprites.add(sprite);
                snakeLength++;
            }

            boolean headHit = false;
            for (GridSprite sprite : bodySprites) {
                if (sprite.getGridX() == head.getGridX() &&
                        sprite.getGridY() == head.getGridY()) {
                    headHit = true;
                    break;
                }
            }
            if (headHit) {
                score = 0;
                dispose();
                create();
            }
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //drawing the cherry, head and body
        cherry.draw(batch);
        head.draw(batch);
        font.draw(batch, "Score: " + score.toString(), 400, 400);
        for (GridSprite sprite : bodySprites) {
            sprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
        cherryTexture.dispose();
        font.dispose();
    }

    public void move(DirectionState direction){
        switch (direction) {
            case RIGHT:
                head.translateX(1);
                break;

            case LEFT:
                head.translateX(-1);
                break;

            case UP:
                head.translateY(1);
                break;

            case DOWN:
                head.translateY(-1);
                break;
        }
    }

    public void setDirection(DirectionState newDir) {
        direction = newDir;
    }

    public DirectionState getDirection() {
        return direction;
    }

/*    public void pause(){
        if (state.equals(GameState.PAUSED)){
            state = GameState.RUNNING;
        }else state = GameState.PAUSED;
    }*/
}


