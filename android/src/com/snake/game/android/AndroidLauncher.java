package com.snake.game.android;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.snake.game.Snake;
import com.snake.game.screens.GameScreen;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        final Snake game = new Snake();
        final View gameView = initializeForView(game, config);

        final RelativeLayout gameContainer = (RelativeLayout) findViewById(R.id.game_container);
        gameContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FrameLayout.LayoutParams params = createLayoutParams();
                params.height = gameContainer.getWidth();
                gameView.setLayoutParams(params);
                gameContainer.addView(gameView);
                gameContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        Button startGameButton = (Button) findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.startGame();
            }
        });

        findViewById(R.id.up_button).setOnTouchListener(new OnDirectionPressed(game,
                GameScreen.DirectionState.UP));
        findViewById(R.id.down_button).setOnTouchListener(new OnDirectionPressed(game,
                GameScreen.DirectionState.DOWN));
        findViewById(R.id.left_button).setOnTouchListener(new OnDirectionPressed(game,
                GameScreen.DirectionState.LEFT));
        findViewById(R.id.right_button).setOnTouchListener(new OnDirectionPressed(game,
                GameScreen.DirectionState.RIGHT));

/*		Button pauseGameButton = (Button) findViewById(R.id.pause_button);
        pauseGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Screen screen = game.getScreen();
				((GameScreen) screen).pause();
			}
		});*/
    }

    class OnDirectionPressed implements View.OnTouchListener {
        private Snake game;
        private GameScreen.DirectionState direction;

        public OnDirectionPressed(Snake game, GameScreen.DirectionState direction) {
            this.game = game;
            this.direction = direction;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Screen screen = game.getScreen();
            if (screen instanceof GameScreen) {
                ((GameScreen) screen).setDirection(direction);
            }
            return true;
        }
    }
}
