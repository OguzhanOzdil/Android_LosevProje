package com.ozzyozdil.flappydeneme;

import static com.ozzyozdil.flappydeneme.Assets.atlas;
import static com.ozzyozdil.flappydeneme.Assets.batch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen extends ScreenAdapter {

    private FlappyDeneme game;
    private Stage stage;

    private Label titleLabel;

    private Button playButton;
    private Button creditsButton;
    private Button skinButton;
    private Image backgroundBuildings;
    private Image logo;
    private Ground ground;

    private Image reklam;
    public static TextureRegion reklamRegion;
    private Button backButton;

    public MainMenuScreen(FlappyDeneme game) {

        this.game = game;
        stage = new Stage(new StretchViewport(FlappyDeneme.WIDTH, FlappyDeneme.HEIGHT));

        reklamYerlestir();
        initBackButton();

        initPlayButton();
        initCreditsButton();
        initSkinButton();

        ground = new Ground();

        titleLabel = new Label("Bu oyunu Losev adi altinda\n" +
                " bagis toplamayi, insanlar\n" +
                " icin hem eglenceli hem de \n" +
                "devamli hale getirmek icin \n" +
                "             yapilmistir.", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        titleLabel.setPosition(FlappyDeneme.CENTER_X - 143, FlappyDeneme.HEIGHT - 250);
        titleLabel.setFontScale(0.8f,0.8f);

        initBackgroundBuildings();

        logo = new Image(Assets.logo);
        logo.setWidth(208);
        logo.setHeight(112);
        logo.setPosition(FlappyDeneme.WIDTH / 2 - 100, FlappyDeneme.HEIGHT/ 2 + 108);

        stage.addActor(ground);
        stage.addActor(backgroundBuildings);
        stage.addActor(titleLabel);
        stage.addActor(playButton);
        stage.addActor(creditsButton);
        stage.addActor(skinButton);
        stage.addActor(logo);
        stage.addActor(reklam);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void reklamYerlestir() {
        reklam = new Image(new TextureRegionDrawable(Assets.reklam));
        reklam.setWidth(250);
        reklam.setHeight(250);
        reklam.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.65f, Align.center);

    }

    private void initBackButton() {
        backButton = new Button(new TextureRegionDrawable(Assets.backUp), new TextureRegionDrawable(Assets.backDown));
        backButton.setWidth(96);
        backButton.setHeight(48);
        backButton.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.10f, Align.center);
        backButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                reklam.setVisible(false);
                backButton.setVisible(false);
            }
        });
    }

    private void initBackgroundBuildings() {
        backgroundBuildings = new Image(Assets.backgroundBuildings);
        backgroundBuildings.setWidth(FlappyDeneme.WIDTH);
        backgroundBuildings.setHeight(backgroundBuildings.getHeight() * 2f);
        backgroundBuildings.setY(Ground.HEIGHT);
    }

    private void initPlayButton() {
        playButton = new Button(new TextureRegionDrawable(Assets.playUp), new TextureRegionDrawable(Assets.playDown));
        playButton.setWidth(104);
        playButton.setHeight(56);
        playButton.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.4f, Align.center);
        playButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameplayScreen(game));

            }
        });
    }

    private void initCreditsButton() {
        creditsButton = new Button(new TextureRegionDrawable(Assets.creditsUp), new TextureRegionDrawable(Assets.creditsDown));
        creditsButton.setWidth(96);
        creditsButton.setHeight(48);
        creditsButton.setPosition(FlappyDeneme.WIDTH/2 + 55, FlappyDeneme.HEIGHT *.28f, Align.center);
        creditsButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CreditsScreen(game));

            }
        });
    }

    private void initSkinButton() {
        skinButton = new Button(new TextureRegionDrawable(Assets.skinDown), new TextureRegionDrawable(Assets.skinUp));
        skinButton.setWidth(96);
        skinButton.setHeight(48);
        skinButton.setPosition(FlappyDeneme.WIDTH/2 - 55, FlappyDeneme.HEIGHT *.28f, Align.center);
        skinButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SkinScreen(game));

            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(Utils.MAIN_MENU_BACKGROUND_COLOR.r, Utils.MAIN_MENU_BACKGROUND_COLOR.g, Utils.MAIN_MENU_BACKGROUND_COLOR.b, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
