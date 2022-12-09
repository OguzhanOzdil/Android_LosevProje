package com.ozzyozdil.flappydeneme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class CreditsScreen extends ScreenAdapter {

    private FlappyDeneme game;
    private Stage stage;

    private Image creditsPng;

    private Button backButton;

    private Image backgroundBuildings;
    private Ground ground;

    public CreditsScreen(FlappyDeneme game) {

        this.game = game;
        stage = new Stage(new StretchViewport(FlappyDeneme.WIDTH, FlappyDeneme.HEIGHT));

        creditsAbout();
        initBackButton();

        ground = new Ground();

        initBackgroundBuildings();

        stage.addActor(ground);
        stage.addActor(backgroundBuildings);
        stage.addActor(creditsPng);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void initBackgroundBuildings() {
        backgroundBuildings = new Image(Assets.backgroundBuildings);
        backgroundBuildings.setWidth(FlappyDeneme.WIDTH);
        backgroundBuildings.setHeight(backgroundBuildings.getHeight() * 2f);
        backgroundBuildings.setY(Ground.HEIGHT);
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

    private void creditsAbout() {
        creditsPng = new Image(new TextureRegionDrawable(Assets.creditsAbout));
        creditsPng.setWidth(260);
        creditsPng.setHeight(370);
        creditsPng.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.6f, Align.center);
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(Utils.MAIN_MENU_BACKGROUND_COLOR.r, Utils.MAIN_MENU_BACKGROUND_COLOR.g, Utils.MAIN_MENU_BACKGROUND_COLOR.b, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
