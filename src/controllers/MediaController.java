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

import java.io.File;

//this class is created to control the media of the game
public class MediaController
{
    //Media paths
    private static String kickPath = "src/resources/sounds/Whip.mp3";
    private static String movePath = "src/resources/sounds/move.mp3";
    private static String whiningPath = "src/resources/sounds/horseWhinny.wav";
    private static String rollPath = "src/resources/sounds/roll.wav";
    private static String winPath = "src/resources/sounds/win.mp3";

    //Audio clip obj
    private static AudioClip kickSound = new AudioClip(new File(kickPath).toURI().toString());
    private static AudioClip moveSound = new AudioClip(new File(movePath).toURI().toString());
    private static AudioClip whinnySound = new AudioClip(new File(whiningPath).toURI().toString());
    private static AudioClip rollSound = new AudioClip(new File(rollPath).toURI().toString());
    private static AudioClip winSound = new AudioClip(new File(winPath).toURI().toString());

    //Play audio clip methods
    public static void playKickSound(){kickSound.play(0.2);}
    public static void playMoveSound(){moveSound.play();}
    public static void playWhinnySound(){whinnySound.play();}
    public static void playRollSound(){rollSound.play();}
    public static void playWinSound(){winSound.play();}
}