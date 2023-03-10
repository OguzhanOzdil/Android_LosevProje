package com.ozzyozdil.flappydeneme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class Ground extends Actor {

    public static final float WIDTH = 320f; // pixels
    public static final float HEIGHT = 96f;

    public static final float MOVE_VELOCITY = 120f; // pixels per second

    // Actor keeps track of position so we just need to keep track of velocity and acceleration
    public Vector2 vel;

    private TextureRegion region;

    private State state;



    private enum State { alive, dead };

    public Ground() {
        region = new TextureRegion(Assets.ground);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.alive;

        vel = new Vector2(-MOVE_VELOCITY, 0);

        // An actor's origin defines the center for rotation.
        setOrigin(Align.center);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        switch (state){
            case alive:
                actAlive(delta);
                break;
            case dead:
                vel = Vector2.Zero;
                break;
        }
    }

    private void actAlive(float delta) {
        updatePosition(delta);
        if (getX() <= -21f){
            setX(getX() + 21f);
        }
    }

    private void updatePosition(float delta) {
        setX(getX() + vel.x * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    public TextureRegion getRegion() {
        return region;
    }
}
