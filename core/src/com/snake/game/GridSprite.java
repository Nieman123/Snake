package com.snake.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;
import static com.snake.game.screens.GameScreen.GRID_SIZE;

/**
 * Created by Jonathan on 3/26/2015.
 */
public class GridSprite {
    private int gridX;
    private int gridY;
    private int gridHeight;
    private int gridWidth;
    private Sprite sprite;
    private static Random random = new Random();

    public GridSprite(Texture texture, int sGridWidth, int sGridHeight){
        sprite = new Sprite(texture);
        sprite.setSize(GRID_SIZE, GRID_SIZE);
        gridHeight = sGridHeight - 1;
        gridWidth = sGridWidth - 1;
    }

    private void updatePosition(){
        if (gridX > gridWidth){
            gridX = gridWidth;
        }
        if (gridX < 0){
            gridX = 0;
        }
        if (gridY > gridHeight){
            gridY = gridHeight;
        }
        if (gridY < 0){
            gridY = 0;
        }
        sprite.setPosition(gridX * GRID_SIZE, gridY * GRID_SIZE);
    }

    public void setGridX(int x){
        gridX = x;
        updatePosition();
    }

    public void setGridY(int y){
        gridY = y;
        updatePosition();
    }

    public void translateX(int amount){
        gridX += amount;
        updatePosition();
    }

    public void translateY(int amount){
        gridY += amount;
        updatePosition();
    }

    public int getGridX(){
        return gridX;
    }

    public int getGridY(){
        return gridY;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void randomizePosition() {
        gridX = random.nextInt(gridWidth);
        gridY = random.nextInt(gridHeight);
        updatePosition();
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
