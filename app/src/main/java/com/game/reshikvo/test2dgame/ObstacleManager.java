package com.game.reshikvo.test2dgame;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Anudeep Bulla on 2/26/2017.
 */

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleHeight;
    private int obstacleGap;
    private int color;

    private long startTime;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleHeight = obstacleHeight;
        this.obstacleGap = obstacleGap;
        this.color = color;
        obstacles = new ArrayList<>();
        startTime = System.currentTimeMillis();
        populateObstacles();
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT/4;
        while(currY < 0){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;

        }
    }

    public void draw(Canvas canvas){
        for(Obstacle ob : obstacles){
            ob.draw(canvas);
        }
    }

    public void update(){
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_HEIGHT/10000.f;

        for (Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
        }
    }

    public boolean playerCollide(RectPlayer player){
        for (Obstacle ob : obstacles) {
            if (ob.playerCollide(player))
                return  true;
        }
        return false;
    }
}
