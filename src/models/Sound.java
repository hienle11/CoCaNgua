package models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Sound {
    private Media diceRoll = new Media(new File("src\\resources\\sounds\\roll.wav").toURI().toString());
    private MediaPlayer diceRollSound = new MediaPlayer(diceRoll);
    private Media horse = new Media(new File("src\\resources\\sounds\\horseRunningSound.mp3").toURI().toString());
    private MediaPlayer horseSound = new MediaPlayer(horse);
    private Media horse1 = new Media(new File("src\\resources\\sounds\\horseWhinny.wav").toURI().toString());
    private MediaPlayer horseWhinnySound = new MediaPlayer(horse1);
    private Media horseKick = new Media(new File("src\\resources\\sounds\\kick.mp3").toURI().toString());
    private MediaPlayer horseKickSound = new MediaPlayer(horseKick);

    public void playDiceSound(){
        diceRollSound.play();
    }
    public void stopDiceSound(){
        diceRollSound.stop();
    }
    public void playHorseRunningSound(){
        horseSound.play();
    }
    public void stopHorseRunningSound(){
        horseSound.stop();
    }
    public void playHorseWhinnySound(){
        horseWhinnySound.play();
    }
    public void stopHorseWhinnySound(){
        horseWhinnySound.stop();
    }
    public void playHorseKickSound(){
        horseKickSound.play();
    }
    public void stopHorseKickSund(){
        horseKickSound.stop();
    }


}
