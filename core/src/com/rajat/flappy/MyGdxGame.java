package com.rajat.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import javax.xml.soap.Text;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float birdVelocity = 0;
    float gravity = 2;

    Texture topTube;
    Texture bottomTube;

	int gameState = 0;

	float gap = 400; //with this game hardness is determined

    float maxTubeOffset;
    float tubeOffset;

    Random randomGenerator; //for random gap

	@Override
	public void create () {
		batch = new SpriteBatch();
        background = new Texture("bg.png");

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight()/2-birds[0].getHeight()/2;

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        maxTubeOffset = Gdx.graphics.getHeight()/2 - gap/2 - 100;
        randomGenerator = new Random();


	}

	@Override
	public void render () {
		//this method runs continuously
        batch.begin();
        batch.draw(background,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
		/*0 , 0 means background renders from bottom left
		  3rd and 4th parameter to stretch background as muchas the screen */



        if(gameState != 0) {

            if(Gdx.input.justTouched()){

                birdVelocity = +30;
                tubeOffset = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                //this method creates random num b/w 0 & 1
                //with 0.5 rand num is b/w 0.5 & -0.5
            }


            //drawing tubes
            batch.draw(topTube,
                    Gdx.graphics.getWidth()/2 - topTube.getWidth()/2,
                    Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset);
            batch.draw(bottomTube,
                    Gdx.graphics.getWidth()/2 - bottomTube.getWidth()/2,
                    Gdx.graphics.getHeight()/2 - gap/2 - bottomTube.getHeight() + tubeOffset);

            if(birdY>0 || birdVelocity <0){

                birdVelocity -= gravity;
                birdY += birdVelocity;

            }




        }else
        {

            if(Gdx.input.justTouched()){

                gameState = 1;
            }
        }

        if (flapState == 0)
            {

                flapState = 1;

            }
        else
            {

                flapState = 0;

            }

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
