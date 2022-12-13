package com.ozzyozdil.flappydeneme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SavedDataManager {

    private static final String KEY_PREFERENCES = "prefs";
    private static final String KEY_HIGH_SCORE = "high score";
    private static final String KEY_TOTAL_MONEY = "total money";

    private static SavedDataManager instance = null;
    private static int highScore;
    public int totalMoney = 0;

    protected SavedDataManager(){
        // Exists only to defeat instantiation.
    }

    public static SavedDataManager getInstance(){
        if (instance == null){
            instance = new SavedDataManager();
        }
        return instance;
    }

    /**
     * Initialize all the saved values from the Preferences
     */
    public void load(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        highScore = prefs.getInteger(KEY_HIGH_SCORE, 0);
        totalMoney = prefs.getInteger(KEY_TOTAL_MONEY, 0);
    }

    /**
     * Save all the values to Preferences
     */
    public void save(){
        Preferences prefs = Gdx.app.getPreferences(KEY_PREFERENCES);
        prefs.putInteger(KEY_HIGH_SCORE, highScore);
        prefs.putInteger(KEY_TOTAL_MONEY, totalMoney);

        prefs.flush();
    }



    public void setTotalMoney(int score) {
        if (0 == 0){
            totalMoney += score;
        }
    }

    public int getTotalMoney(){
        return totalMoney;
    }


    public void setHighScore(int score){
        if (score > highScore)
            highScore = score;
    }

    public int getHighScore(){
        return highScore;
    }
}