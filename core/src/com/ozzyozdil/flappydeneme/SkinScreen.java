package com.ozzyozdil.flappydeneme;

import static com.ozzyozdil.flappydeneme.Assets.atlas;
import static com.ozzyozdil.flappydeneme.Assets.setBird;
import static com.ozzyozdil.flappydeneme.Assets.setBird2;
import static com.ozzyozdil.flappydeneme.MainMenuScreen.reklamKontrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SkinScreen extends ScreenAdapter {

    private FlappyDeneme game;
    private Stage stage;

    private Image bird1;
    private Image bird2;

    private Button backButton;

    public static TextureRegion bird1Region;
    public static TextureRegion bird2Region;

    private Image backgroundBuildings;
    private Ground ground;

    public SkinScreen(FlappyDeneme game) {

        this.game = game;
        stage = new Stage(new StretchViewport(FlappyDeneme.WIDTH, FlappyDeneme.HEIGHT));

        reklamKontrol = "hide";

        skins();
        initBackButton();

        ground = new Ground();

        initBackgroundBuildings();

        stage.addActor(ground);
        stage.addActor(backgroundBuildings);
        stage.addActor(bird1);
        stage.addActor(bird2);
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

    private void skins() {
        bird1 = new Image(new TextureRegionDrawable(Assets.bird3));
        bird1.setWidth(32);
        bird1.setHeight(32);
        bird1.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.75f, Align.center);
        bird1.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                bird1Region = atlas.findRegion("peng");
                setBird(bird1Region);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        bird2 = new Image(new TextureRegionDrawable(Assets.pembeKus));
        bird2.setWidth(32);
        bird2.setHeight(28);
        bird2.setPosition(FlappyDeneme.WIDTH/2, FlappyDeneme.HEIGHT*.55f, Align.center);
        bird2.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                bird2Region = atlas.findRegion("pembekus");
                setBird2(bird2Region);
                game.setScreen(new MainMenuScreen(game));
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
