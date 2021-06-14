package musicplayer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javafx.animation.*;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;  
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import musicplayer.model.Playlist;
import musicplayer.model.Song;
import musicplayer.view.View;
import java.util.HashMap;

public class Controller {
    private Stage stage;
    private static Playlist playlist;
    private Song song;
//    private ButtonController btnController;
    private static View GUI;
    private String[] args;
    private static Media media;
    private static MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean isFirst = true;
    private boolean hasCurrent = false;
    private static int totalTime, startTime, stopTime;
    private static String currentSong;
    
    public Controller() {}
    
    public Controller(String[] args) throws IOException {
        GUI = new View();
        this.args = args;
//        playlist = GUI.getPlaylist();
        
//        btnController = new ButtonController();
        
        new Thread() {
            public void run() {
                GUI.display(args);
            }
        }.start();
//        String path = "/musicplayer/mp3/ConversationattheCross.mp3";
//        media = new Media(getClass().getResource(path).toExternalForm());
//        mediaPlayer = new MediaPlayer(media);
//        totalTime = (int)mediaPlayer.getMedia().getDuration().toSeconds();
//        System.out.println(totalTime);
//        GUI.getNowPlayingSlider().setMax(totalTime);
    }
        
//    public void setCurrentPlay(String path) {
////        String path = "ConversationattheCross.mp3";
//        Media media = new Media(getClass().getResource(path).toExternalForm());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        playAudio();
//    }
    public void handleCurrentSong(String path, String[] args) {
//        Iterator<String> values = playlist.gethMap().values().iterator();
//        currentSong = values.next();
        GUI.display(args);
        System.out.println(path);
        path = path.replaceAll(".*src", "");
        System.out.println(path);
        media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        
    }
    
    public Song addButtonClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Jeremy\\Documents\\NetBeansProjects\\MusicPlayer\\src\\musicplayer"));
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            song = new Song(file.getAbsolutePath());
//            playlist.addSong(song);
            isFirst = false;
            return song;
        }
        return null;
    }
    
    public void sortButtonClicked() {
        JOptionPane.showMessageDialog(null, "Sort Button Clicked");
    }
    
    public void searchButtonClicked(String key) {
        boolean found = false;
        Iterator<String> keys = GUI.getPlaylist().gethMap().keySet().iterator();
        while(keys.hasNext()){
            String comKey = keys.next();
            if (key.equals(comKey)) {
                JOptionPane.showMessageDialog(null, key + " is found.");
                found = true;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, key + " is not found.");
        }
        String result = GUI.getPlaylist().gethMap.get(key);
        handleCurrentSong(result);
    }
    
    public void deleteButtonClicked() {
        JOptionPane.showMessageDialog(null, "Delete Button Clicked");
    }
    
    public void firstButtonClicked() {
        JOptionPane.showMessageDialog(null, "First Button Clicked");
    }
    
    public void backButtonClicked() {
        JOptionPane.showMessageDialog(null, "Back Button Clicked");
    }
    
    public void playButtonClicked() {
        if (GUI.getPlaylist() != null) {
            Iterator<String> values = GUI.getPlaylist().gethMap().values().iterator();
            currentSong = values.next();
            handleCurrentSong(currentSong, args);
            isFirst = false;
        } else {
            JOptionPane.showMessageDialog(null, "There is no Music file!");
        }
        if (!isFirst) {
            if (!hasCurrent) {
                Iterator<String> values = GUI.getPlaylist().gethMap().values().iterator();
                currentSong = values.next();
                handleCurrentSong(currentSong, args);
                hasCurrent = true;
                isFirst = true;
            }
            totalTime = (int)mediaPlayer.getMedia().getDuration().toSeconds();
            System.out.println(totalTime);
            GUI.getNowPlayingSlider().setMax(totalTime);
            if (mediaPlayer.getStatus() != Status.PLAYING) {
                isPlaying = true;
                new Thread() {
                    public void run() {

                        mediaPlayer.play();

                        while (isPlaying) {
                            for (int i = startTime; i < totalTime; i++) {
                                GUI.getNowPlayingSlider().setValue((int)mediaPlayer.getCurrentTime().toSeconds());
                                try {
                                    Thread.sleep(1000); 
                                } catch (InterruptedException e) {
                                }
                            }
                        }
                    }
                }.start();
                GUI.addIcon(GUI.getPlayBtn(), "icons/pause.png");
            } else {
                isPlaying = false;
                mediaPlayer.pause();
                System.out.println(mediaPlayer.getStatus());
                startTime = (int)mediaPlayer.getCurrentTime().toSeconds();
                GUI.addIcon(GUI.getPlayBtn(), "icons/play.png");
            }
        }
    }
    
    public void nextButtonClicked() {
        JOptionPane.showMessageDialog(null, "Next Button Clicked");
    }
    
    public void lastButtonClicked() {
        JOptionPane.showMessageDialog(null, "Last Button Clicked");
    }
    
    public void loadCSVButtonClicked() {
        JOptionPane.showMessageDialog(null, "CSV loading...");
    }
    
    public void saveCSVButtonClicked() {
        JOptionPane.showMessageDialog(null, "CSV saving...");
    }
    
    public void displayPlaylist(String[] args) {
        
        Iterator<String> keys = playlist.gethMap().keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            GUI.addPlaylist(key);
            GUI.display(args);
        }
    }
    
//    public void playAudio() {
//        if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING){
//            mediaPlayer.play();
//        }
//    }
    
    
    
    
    
    
    
    
    
    public void loadPlaylist() {
        
    }
    
    public void savePlaylist() {
        
    }
}
