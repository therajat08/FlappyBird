package com.rajat.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import javax.xml.soap.Text;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	ShapeRenderer shapeRenderer;
	//like a batch shaperenderer enables us to draw shapes
    //used for collision detection

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float birdVelocity = 0;
    float gravity = 2;
    Circle birdCircle;
    int score = 0;
    int scoringTube=0;
    BitmapFont font;//for scoring
    Texture topTube;
    Texture bottomTube;
    Texture gameover;

	int gameState = 0;

	float gap = 400; //with this game hardness is determined

    float maxTubeOffset;


    Random randomGenerator; //for random gap

    float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;

    Rectangle[] topTuberectangles;
    Rectangle[] bottomTuberectangles;

	@Override
	public void create () {
		batch = new SpriteBatch();
        background = new Texture("back.png");
        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        gameover = new Texture("gameover.png");

        //setting up score font
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        font.getData().setScale(10);

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");


        topTube = new Texture("pipetop.png");
        bottomTube = new Texture("pipebottom.png");

        maxTubeOffset = Gdx.graphics.getHeight()/2 - gap/2 - 100;
        randomGenerator = new Random();

        distanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;
        topTuberectangles = new Rectangle[numberOfTubes];
        bottomTuberectangles = new Rectangle[numberOfTubes];

        startGame();

	}

	public  void startGame(){

        birdY = Gdx.graphics.getHeight()/2-birds[0].getHeight()/2;

        for (int i = 0; i < numberOfTubes; i++)
        {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
            tubeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

            topTuberectangles[i] = new Rectangle();
            bottomTuberectangles[i] = new Rectangle();
        }
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

        if (flapState == 0)
        {

            flapState = 1;

        }
        else
        {

            flapState = 0;

        }

        //bird drawn here
        batch.draw
                (birds[flapState],
                        Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
                        birdY);

        if(gameState == 1) {

            if(tubeX[scoringTube] < Gdx.graphics.getWidth()/2)
            {
                score++;
                if(scoringTube < numberOfTubes - 1)
                {
                    scoringTube++;
                    Gdx.app.log("score ", String.valueOf(score));
                }
                else{
                    scoringTube = 0;
                }
            }

            if(Gdx.input.justTouched()){

                birdVelocity = +30;

                //this method creates random num b/w 0 & 1
                //with 0.5 rand num is b/w 0.5 & -0.5
            }
            for (int i = 0; i < numberOfTubes; i++) {

                if(tubeX[i] < - topTube.getWidth())//can even take bottom tube
                {
                    tubeX[i] = numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                    //above line added so that the tubes dont come with same offset again

                }
                else {
                    tubeX[i] = tubeX[i] - tubeVelocity;


                }


                //drawing tubes
                batch.draw(topTube,
                        tubeX[i],
                        Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube,
                        tubeX[i],
                        Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

                //drawing collision renders
                topTuberectangles[i] = new Rectangle(
                           tubeX[i],
                        Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],
                           topTube.getWidth(),
                           topTube.getHeight());
                bottomTuberectangles[i] = new Rectangle(
                        tubeX[i],
                        Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],
                           bottomTube.getWidth(),
                           bottomTube.getHeight());
            }

            if(birdY>0){

                birdVelocity -= gravity;
                birdY += birdVelocity;

            }else {
                gameState = 2;
            }

        }else if(gameState == 0)
        {

            if(Gdx.input.justTouched()){

                gameState = 1;
            }
        }else if(gameState == 2)
        {
            batch.draw(gameover,Gdx.graphics.getWidth()/2 - gameover.getWidth()/2,Gdx.graphics.getHeight()/2 - gameover.getHeight()/2);

            if(Gdx.input.justTouched()){

                gameState = 1;
                startGame();
                score = 0;
                scoringTube = 0;
                birdVelocity = 0;
            }
        }

        //birds were here

        //drawing score
        font.draw(batch,String.valueOf(score),100,180);

        batch.end();

        birdCircle.set(
                /*x coordinate*/Gdx.graphics.getWidth()/2,/*this doesn't change as we know*/
                /*y coordinate*/birdY + birds[flapState].getHeight()/2,
                /*radius*/      birds[flapState].getWidth()/2 );


       /* shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //filled here can be others as well
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(birdCircle.x,
                             birdCircle.y,
                             birdCircle.radius);
            */
        for (int i=0; i < numberOfTubes ; i++)
        {

            /*shapeRenderer.rect(
                    tubeX[i],
                    Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],
                    topTube.getWidth(),
                    topTube.getHeight()
            );
            shapeRenderer.rect(
                    tubeX[i],
                    Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],
                    bottomTube.getWidth(),
                    bottomTube.getHeight()
            );*/

            if(Intersector.overlaps(birdCircle,topTuberectangles[i])  ||
                    Intersector.overlaps(birdCircle,bottomTuberectangles[i])){

                gameState = 2;
            }
        }
       // shapeRenderer.end();
	}
	/*
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
