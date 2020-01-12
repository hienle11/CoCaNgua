/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Final Assignment
  Created date: 10/01/2020
  By: phuoc (s3695516)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: phuoc (s3695516)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package controllers;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

//this class is created to control the media of the game
public class MediaController
{
    private static MediaPlayer mediaPlayer = null;

    //this method is to initialize the media of the game
    public static void initializeMedia()
    {
    }

    //Media paths
    private String kickPath = "src/resources/sounds/kick.mp3";
    private String movePath = "src/resources/sounds/move.mp3";
    private String whiningPath = "src/resources/sounds/horseWhinny.wav";
    private String rollPath = "src/resources/sounds/roll.wav";

    //Audio clip obj
    private AudioClip kickSound = new AudioClip(new File(kickPath).toURI().toString());
    private AudioClip moveSound = new AudioClip(new File(movePath).toURI().toString());
    private AudioClip whinnySound = new AudioClip(new File(whiningPath).toURI().toString());
    private AudioClip rollSound = new AudioClip(new File(rollPath).toURI().toString());

    //Play audio clip methods
    public void playKickSound(){kickSound.play();}
    public void playMoveSound(){moveSound.play();}
    public void playWhinnySound(){whinnySound.play();}
    public void playRollSound(){rollSound.play();}

    //Mute audio clips

    // this method is to check if the media is playing
    public static boolean isPlaying()
    {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}