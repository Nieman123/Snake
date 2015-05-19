package com.snake.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.snake.game.GridSprite;
import com.snake.game.Snake;

import java.util.ArrayList;
import java.util.List;

import static com.snake.game.screens.GameScreen.GRID_SIZE;

public class IntroScreen extends BaseScreen {

    public static final String SNAKE_TEXT =
            "OOO O  O OOOO O  O OOO\n" +
            "O   OO O O  O O O  O  \n" +
            "OOO OOOO OOOO OO   OOO\n" +
            "  O O OO O  O O O  O  \n" +
            "OOO O  O O  O O  O OOO\n";

    private Texture bodyTexture;
    private List<GridSprite> sprites = new ArrayList<>();

    private OrthographicCamera camera;

    private int gridWidth;
    private int gridHeight;

    public IntroScreen(Snake game) {
        super(game);
    }

    @Override
    public void create() {
        bodyTexture = new Texture("images.png");

        //creating a camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 480);

        //Setting the sizes of our grid
        gridWidth = (int) (camera.viewportWidth / GRID_SIZE);
        gridHeight = (int) (camera.viewportHeight / GRID_SIZE);

        String[] split = SNAKE_TEXT.split("\n");
        for (int i = 0; i < split.length; i++) {
            String line = split[i];
            for (int c = 0; c < line.length(); c++) {
                if (line.charAt(c) != ' ') {
                    GridSprite sprite = new GridSprite(bodyTexture, gridHeight, gridWidth);
                    sprite.setGridY((gridHeight + split.length) / 2 - i);
                    sprite.setGridX(c + 1);
                    sprites.add(sprite);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (GridSprite sprite : sprites) {
            sprite.draw(batch);
        }

        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.ENTER == keycode) {
            game.startGame();
            return true;
        }
        return false;
    }
}
