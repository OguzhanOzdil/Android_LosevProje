package com.ozzyozdil.flappydeneme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Assets {

    // Disposeables
    public static TextureAtlas atlas;
    public static SpriteBatch batch;
    public static BitmapFont fontMedium;

    public static Sound sfxHit;
    public static Sound sfxFlap;
    public static Sound sfxBing;
    public static Sound sfxDie;
    public static Sound sfxWoosh;
    
    // Non-Disposeables
    public static TextureRegion bird;
    public static TextureRegion bird2;
    public static TextureRegion bird3;
    public static TextureRegion birdDead;
    public static TextureRegion backgroundBuildings;
    public static TextureRegion ground;
    public static TextureRegion pipe;
    public static TextureRegion title;
    public static TextureRegion playDown;
    public static TextureRegion playUp;
    public static TextureRegion skinDown;
    public static TextureRegion skinUp;
    public static TextureRegion backDown;
    public static TextureRegion backUp;
    public static TextureRegion creditsDown;
    public static TextureRegion creditsUp;
    public static TextureRegion creditsAbout;
    public static TextureRegion whitePixel;
    public static TextureRegion ruby1;
    public static TextureRegion ruby2;
    public static TextureRegion ruby3;
    public static TextureRegion ruby4;
    public static TextureRegion ruby5;
    public static TextureRegion ruby6;
    public static TextureRegion logo;
    public static TextureRegion pembeKus;
    public static TextureRegion reklam;

    // Animations
    public static Animation birdAnimation;
    public static Animation rubyAnimation;

    public static void load() {

        atlas = new TextureAtlas("pack.atlas");
        batch = new SpriteBatch();

        fontMedium = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font_0.png"), false);

        bird = atlas.findRegion("peng");
        bird2 = atlas.findRegion("peng2");
        bird3 = atlas.findRegion("peng3");
        birdDead = atlas.findRegion("peng-dead");

        pembeKus = atlas.findRegion("pembekus");

        reklam = atlas.findRegion("reklam");

        backgroundBuildings = atlas.findRegion("background-buildings");
        ground = atlas.findRegion("ground-321x96");
        pipe = atlas.findRegion("pipe-32x200");
        title = atlas.findRegion("title");
        playDown = atlas.findRegion("play_down");
        playUp = atlas.findRegion("play_up");
        skinDown = atlas.findRegion("skin_down");
        skinUp = atlas.findRegion("skin_up");
        backDown = atlas.findRegion("back_down");
        backUp = atlas.findRegion("back_up");
        creditsDown = atlas.findRegion("credits_down");
        creditsUp = atlas.findRegion("credits_up");
        creditsAbout = atlas.findRegion("credits_about");
        whitePixel = atlas.findRegion("white-pixel");
        ruby1 = atlas.findRegion("ruby1");
        ruby2 = atlas.findRegion("ruby2");
        ruby3 = atlas.findRegion("ruby3");
        ruby4 = atlas.findRegion("ruby4");
        ruby5 = atlas.findRegion("ruby5");
        ruby6 = atlas.findRegion("ruby6");
        logo = atlas.findRegion("logo");

        birdAnimation = new Animation(.1f, getBird(), getBird2(), getBird3());
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        Array<TextureRegion> rubyArray = new Array<TextureRegion>();
        rubyArray.add(ruby1);
        rubyArray.add(ruby2);
        rubyArray.add(ruby3);
        rubyArray.add(ruby4);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby5);
        rubyArray.add(ruby6);
        rubyArray.add(ruby5);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyArray.add(ruby1);
        rubyAnimation = new Animation(.07f, rubyArray, Animation.PlayMode.LOOP);

        sfxHit = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_hit.mp3"));
        sfxFlap = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_wing.mp3"));
        sfxBing = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_point.mp3"));
        sfxDie = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_die.mp3"));
        sfxWoosh = Gdx.audio.newSound(Gdx.files.internal("sounds/sfx_swooshing.mp3"));

    }

    // Dispose all disposeables here
    public static void dispose(){
        if (atlas != null) {
            atlas.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }

        fontMedium.dispose();

        sfxHit.dispose();
        sfxFlap.dispose();
        sfxBing.dispose();
        sfxDie.dispose();
        sfxWoosh.dispose();
    }

    public static TextureRegion getBird() {
        return bird;
    }

    public static void setBird(TextureRegion bird) {
        Assets.bird = bird;
    }

    public static TextureRegion getBird2() {
        return bird2;
    }

    public static void setBird2(TextureRegion bird2) {
        Assets.bird2 = bird2;
    }

    public static TextureRegion getBird3() {
        return bird3;
    }

    public static void setBird3(TextureRegion bird3) {
        Assets.bird3 = bird3;
    }

    public static TextureRegion getBird4() {
        return birdDead;
    }

    public static void setBird4(TextureRegion bird4) {
        Assets.birdDead = bird4;
    }

    public static void playPunchSound(){
        sfxHit.play();
    }

    public static void playDieSound(){
        sfxDie.play();
    }

    public static void playWooshSound(){
        sfxWoosh.play();
    }


    public static void playFlapSound() {
        sfxFlap.play();
    }

    public static void playBingSound() {
        sfxBing.play();
    }
}
