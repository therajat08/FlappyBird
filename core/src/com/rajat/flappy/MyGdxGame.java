package com.rajat.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float birdVelocity = 0;
    float gravity = 2;

	int gameState = 0;
	@Override
	public void create () {
		batch = new SpriteBatch();
        background = new Texture("bg.png");

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight()/2-birds[0].getHeight()/2;
	}

	@Override
	public void render () {
		//this method runs continuously




        if(gameState != 0) {

            if(Gdx.input.justTouched()){

                birdVelocity = +20;
            }
            birdVelocity -= gravity;
            birdY += birdVelocity;


        }else
        {

            if(Gdx.input.justTouched()){

                gameState = 1;
            }
        }

        if (flapState == 0) {

            flapState = 1;
        } else {
            flapState = 0;
        }
        batch.begin();
        batch.draw(background,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
		/*0 , 0 means background renders from bottom left
		  3rd and 4th parameter to stretch background as muchas the screen */
        batch.draw
                (birds[flapState],
                        Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
                        birdY);
        batch.end();
	}
	/*
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
