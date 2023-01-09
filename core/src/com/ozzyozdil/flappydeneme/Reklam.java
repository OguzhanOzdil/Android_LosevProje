package com.ozzyozdil.flappydeneme;

import static com.ozzyozdil.flappydeneme.Assets.atlas;
import static com.ozzyozdil.flappydeneme.Assets.setBird;
import static com.ozzyozdil.flappydeneme.Assets.setBird2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Reklam extends ScreenAdapter {

    private FlappyDeneme game;
    private Stage stage;

    private Button backButton;

    private Image reklam;

    public static TextureRegion reklamRegion;

    private Image backgroundBuildings;
    private Ground ground;

    public Reklam (FlappyDeneme game){
        this.game = game;
        stage = new Stage(new StretchViewport(FlappyDeneme.WIDTH, FlappyDeneme.HEIGHT));

        reklamYerlestir();
        initBackButton();

        ground = new Ground();

        initBackgroundBuildings();

        stage.addActor(ground);
        stage.addActor(backgroundBuildings);
        stage.addActor(reklam);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void reklamYerlestir() {
        reklam = new Image(new TextureRegionDrawable(Assets.bird3));
        reklam.setWidth(32);
        reklam.setHeight(32);
        reklam.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.75f, Align.center);
        reklam.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                reklamRegion = atlas.findRegion("peng");
                setBird(reklamRegion);
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    private void initBackButton() {
        backButton = new Button(new TextureRegionDrawable(Assets.backUp), new TextureRegionDrawable(Assets.backDown));
        backButton.setWidth(96);
        backButton.setHeight(48);
        backButton.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.10f, Align.center);
        backButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));

            }
        });
    }

    private void initBackgroundBuildings() {
        backgroundBuildings = new Image(Assets.backgroundBuildings);
        backgroundBuildings.setWidth(FlappyDeneme.WIDTH);
        backgroundBuildings.setHeight(backgroundBuildings.getHeight() * 2f);
        backgroundBuildings.setY(Ground.HEIGHT);
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(Utils.MAIN_MENU_BACKGROUND_COLOR.r, Utils.MAIN_MENU_BACKGROUND_COLOR.g, Utils.MAIN_MENU_BACKGROUND_COLOR.b, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
