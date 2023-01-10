package com.ozzyozdil.flappydeneme;

import static com.ozzyozdil.flappydeneme.MainMenuScreen.reklamKontrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameplayScreen extends ScreenAdapter {

    public static final float PIPE_SPACING = 200f;
    public static final int PIPE_SETS = 3;

    protected OrthographicCamera camera;
    protected FlappyDeneme game;

    private Stage gameplayStage;
    private Stage uiStage;

    private Label scoreLabel;
    private Label tapToRetry;
    private Label best;
    private Label tapToFlap;
    private Label moneyAbout;
    private Label money;

    private Image whitePixel;
    private Image backgroundBuildings;

    private int score;

    private Bird bird;
    private Array<PipePair> pipePairs;

    private Ground ground;

    private boolean justTouched;

    private Color backgroundColor;

    private State screenState = State.PREGAME;
    private boolean allowRestart = false;

    private enum State {PREGAME, PLAYING, DYING, DEAD}

    public GameplayScreen(FlappyDeneme game) {
        this.game = game;

        reklamKontrol = "hide";

        camera = new OrthographicCamera(FlappyDeneme.WIDTH, FlappyDeneme.HEIGHT);
        gameplayStage = new Stage(new StretchViewport(FlappyDeneme.WIDTH, FlappyDeneme.HEIGHT, camera));
        uiStage = new Stage(new StretchViewport(FlappyDeneme.WIDTH, FlappyDeneme.HEIGHT));

        bird = new Bird();
        bird.setPosition(FlappyDeneme.WIDTH * .25f, FlappyDeneme.HEIGHT / 2, Align.center);
        bird.addAction(Utils.getFloatyAction());
        bird.setState(Bird.State.PREGAME);

        whitePixel = new Image(Assets.whitePixel);

        scoreLabel = new Label("0", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        scoreLabel.setPosition(FlappyDeneme.WIDTH / 2, FlappyDeneme.HEIGHT * .9f, Align.center);
        uiStage.addActor(scoreLabel);

        tapToRetry = new Label("", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        tapToRetry.setPosition(FlappyDeneme.WIDTH / 2, 0, Align.top);
        uiStage.addActor(tapToRetry);

        best = new Label("Best: ", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        best.setPosition(FlappyDeneme.WIDTH / 2 - 10, 0, Align.top);
        uiStage.addActor(best);

        money = new Label("Money: ", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        money.setPosition(FlappyDeneme.WIDTH / 2 - 10, 0, Align.top);
        uiStage.addActor(money);

        tapToFlap = new Label("Tap To Flap!", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        tapToFlap.setPosition(FlappyDeneme.WIDTH / 2, FlappyDeneme.HEIGHT, Align.bottom);
        uiStage.addActor(tapToFlap);

        moneyAbout = new Label("10 Point 50 kr", new Label.LabelStyle(Assets.fontMedium, Color.WHITE));
        moneyAbout.setPosition(FlappyDeneme.WIDTH / 1.25f, 0, Align.bottomRight);

        initBackgroundBuildings();

        pipePairs = new Array<PipePair>();

        ground = new Ground();
        ground.setPosition(0, 0);

        backgroundColor = Utils.getRandomBackgroundColor();

        // The order actors are added determines the order they are drawn so make sure the background is first
        gameplayStage.addActor(ground);
        gameplayStage.addActor(backgroundBuildings);
        gameplayStage.addActor(bird);

        // Setup the input processor
        initInputProcessor();
    }

    private void initBackgroundBuildings() {
        backgroundBuildings = new Image(Assets.backgroundBuildings);
        backgroundBuildings.setWidth(FlappyDeneme.WIDTH);
        backgroundBuildings.setHeight(backgroundBuildings.getHeight()*2f);
        backgroundBuildings.setY(Ground.HEIGHT);
    }

    @Override
    public void show() {
        tapToFlap.addAction(Actions.moveToAligned(FlappyDeneme.CENTER_X, FlappyDeneme.CENTER_Y + 100f, Align.center, .75f, Interpolation.sine));
        Assets.playWooshSound();
    }

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch (screenState) {
            case PREGAME:
                updateAndDrawStages();
                break;
            case PLAYING:
                renderPlaying();
                break;
            case DYING:
            case DEAD:
                uiStage.addActor(moneyAbout);
                renderDeadOrDying();
                break;
        }
    }

    private void renderDeadOrDying() {
        if (bird.getState() == Bird.State.DEAD) {
            screenState = State.DEAD;
        }
        updateAndDrawStages();
    }

    private void renderPlaying() {
        if (justTouched) {
            bird.jump();
            justTouched = false;
        }
        updatePipePairs();
        gameplayStage.act();
        uiStage.act();
        checkCollisions();
        if (bird.getState() == Bird.State.DYING) {
            stopTheWorld();
            SavedDataManager.getInstance().setHighScore(score);
            SavedDataManager.getInstance().setTotalMoney(score);

            RunnableAction playWooshAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    Assets.playWooshSound();

                }
            });

            SequenceAction actions = Actions.sequence(Actions.delay(1f), playWooshAction, Actions.moveToAligned(FlappyDeneme.CENTER_X, FlappyDeneme.CENTER_Y, Align.bottom,
                    .75f, Interpolation.sine), Actions.run(new Runnable() {
                @Override
                public void run() {
                    // Allow the player to restart the game once the tap to retry finishes coming up
                    allowRestart = true;
                }
            }));
            tapToRetry.addAction(actions);

            best.setText("Best: " + SavedDataManager.getInstance().getHighScore());
            best.setPosition(FlappyDeneme.WIDTH / 2 - 10, 0, Align.top);
            best.addAction(Actions.delay(1f, Actions.moveToAligned(FlappyDeneme.CENTER_X - 10, FlappyDeneme.CENTER_Y + 50, Align.top, .75f, Interpolation.sine)));

            money.setText("Money: " + SavedDataManager.getInstance().getTotalMoney());
            money.setPosition(FlappyDeneme.WIDTH / 2 - 10, 0, Align.top);
            money.addAction(Actions.delay(1f, Actions.moveToAligned(FlappyDeneme.CENTER_X - 10, FlappyDeneme.CENTER_Y + 20, Align.top, .75f, Interpolation.sine)));

            screenState = State.DYING;
        }
        gameplayStage.draw();
        uiStage.draw();
    }

    private void updateAndDrawStages() {
        gameplayStage.act();
        gameplayStage.draw();
        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width, height);
        Assets.batch.setProjectionMatrix(camera.combined);
        gameplayStage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);

    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        uiStage.dispose();
    }


    private void checkCollisions() {

        for (int i = 0; i < pipePairs.size; i++) {
            PipePair pair = pipePairs.get(i);
            if (pair.getBottomPipe().getBounds().overlaps(bird.getBounds()) || pair.getTopPipe().getBounds().overlaps(bird.getBounds())) {
                stopTheWorld();
                showWhiteScreen();
            } else if (bird.isBelowGround()) {
                bird.setY(FlappyDeneme.GROUND_LEVEL);
                bird.clearActions();
                bird.setToDying();
                showWhiteScreen();
            } else if (bird.isAboveCeiling()) {
                bird.setY(FlappyDeneme.HEIGHT - bird.getHeight());
                bird.setToDying();
                showWhiteScreen();
            } else if (pair.getRuby().getBounds().overlaps(bird.getBounds())) {
                score++;
                updateScoreLabel();
                pair.moveCoinOffscreen();
                Assets.playBingSound();
            }
        }

    }

    private void showWhiteScreen() {
        whitePixel.setWidth(FlappyDeneme.WIDTH);
        whitePixel.setHeight(FlappyDeneme.HEIGHT);

        gameplayStage.addActor(whitePixel);

        whitePixel.addAction(Actions.fadeOut(.5f));
    }

    private void updateScoreLabel() {
        scoreLabel.setText(String.valueOf(score));
        scoreLabel.setPosition(FlappyDeneme.WIDTH / 2, FlappyDeneme.HEIGHT * .9f, Align.center);

    }

    private void stopTheWorld() {
        bird.setToDying();
        killPipePairs();
        stopTheGround();
        screenState = State.DYING;

    }

    private void stopTheGround() {
        ground.vel.x = 0;
    }

    private void killPipePairs() {
        for (PipePair pair : pipePairs) {
            pair.getBottomPipe().setState(Pipe.State.dead);
            pair.getTopPipe().setState(Pipe.State.dead);
            pair.getRuby().setVel(0, 0);
        }
    }


    private void updatePipePairs() {
        for (int i = 0; i < pipePairs.size; i++) {
            pipePairs.get(i).update();
        }
    }


    private void addPipes(Stage gameplayStage) {
        for (int i = 0; i < pipePairs.size; i++) {
            gameplayStage.addActor(pipePairs.get(i).getBottomPipe());
            gameplayStage.addActor(pipePairs.get(i).getTopPipe());
            gameplayStage.addActor(pipePairs.get(i).getRuby());
        }
    }

    private void initThirdSetOfPipes() {
        Pipe topPipe = new Pipe();
        Pipe bottomPipe = new Pipe();
        topPipe.getRegion().flip(false, true);
        PipePair pair = new PipePair(topPipe, bottomPipe);
        pair.initThird();

        // add the pair to the list
        pipePairs.add(pair);
    }

    private void initSecondSetOfPipes() {
        Pipe topPipe = new Pipe();
        Pipe bottomPipe = new Pipe();
        topPipe.getRegion().flip(false, true);
        PipePair pair = new PipePair(topPipe, bottomPipe);
        pair.initSecond();

        // add the pair to the list
        pipePairs.add(pair);
    }

    private void initFirstSetOfPipes() {
        Pipe topPipe = new Pipe();
        Pipe bottomPipe = new Pipe();
        topPipe.getRegion().flip(false, true);
        PipePair pair = new PipePair(topPipe, bottomPipe);
        pair.initFirst();

        // add the pair to the list
        pipePairs.add(pair);
    }

    /**
     * Tells libgdx to listen for inputs coming from the InputAdapter we give it
     */
    private void initInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            // We only care about the touch down event
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                switch (screenState) {

                    case DYING:
                        justTouched = true;
                        break;

                    case DEAD:
                        if (allowRestart) {
                            game.setScreen(new MainMenuScreen(game));
                        }
                        justTouched = true;
                        break;

                    case PLAYING:
                        justTouched = true;
                        break;

                    case PREGAME:
                        justTouched = true;
                        screenState = State.PLAYING;
                        bird.setState(Bird.State.ALIVE);
                        bird.clearActions();
                        tapToFlap.addAction(Actions.moveToAligned(FlappyDeneme.CENTER_X, FlappyDeneme.HEIGHT, Align.bottom, .75f, Interpolation.sine));
                        initFirstSetOfPipes();
                        initSecondSetOfPipes();
                        initThirdSetOfPipes();
                        addPipes(gameplayStage);
                        gameplayStage.addActor(ground);
                        gameplayStage.addActor(bird);
                        break;

                }
                return true;
            }
        });
    }
}
