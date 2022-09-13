import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundPlayer{
    //For the correct sound effect
    public static void playCorrectSound(){
        try{
            File audioFile = new File("correctSoundEffect.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            //We read the type of audio format
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            //Obtain the clip
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            //We initialise the playing process
            audioClip.open(audioStream);
            audioClip.start();
            //Close the file
            //audioClip.close();
            //audioStream.close();
        } catch (Exception e){
            System.out.println("An error in the sound player has occured; all audio won't play.");
        }
    }
    public static void playWrongSound(){
        try{
            File audioFile = new File("wrongSoundEffect.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            //We read the type of audio format
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            //Obtain the clip
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            //We initialise the playing process
            audioClip.open(audioStream);
            audioClip.start();
            //Close the file
            //audioClip.close();
            //audioStream.close();
        } catch (Exception e){
            System.out.println("An error in the sound player has occured; all audio won't play.");
        }
    }
}