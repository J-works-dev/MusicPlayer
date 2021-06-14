package musicplayer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
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

public class Controller {
    private Stage stage;
    private Playlist playlist;
    private Song song;
//    private ButtonController btnController;
    private static View GUI;
    private String[] args;
    private Media media;
    private static MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private static int totalTime, startTime, stopTime;
    
    public Controller() {}
    
    public Controller(String[] args) throws IOException {
//        playlist = new Playlist();
        GUI = new View();
        this.args = args;
        playlist = GUI.getPlaylist();
        
//        btnController = new ButtonController();
        
        new Thread() {
            public void run() {
                GUI.display(args);
            }
        }.start();
        String path = "/musicplayer/mp3/ConversationattheCross.mp3";
        media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        totalTime = (int)mediaPlayer.getMedia().getDuration().toSeconds();
        System.out.println(totalTime);
//        GUI.getNowPlayingSlider().setMax(totalTime);
    }
        
//    public void setCurrentPlay(String path) {
////        String path = "ConversationattheCross.mp3";
//        Media media = new Media(getClass().getResource(path).toExternalForm());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        playAudio();
//    }
    
    public Song addButtonClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Jeremy\\Documents\\NetBeansProjects\\MusicPlayer\\src\\musicplayer"));
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            song = new Song(file.getAbsolutePath());
            return song;
        }
        return null;
    }
    
    public void sortButtonClicked() {
        JOptionPane.showMessageDialog(null, "Sort Button Clicked");
    }
    
    public void searchButtonClicked() {
        JOptionPane.showMessageDialog(null, "Search Button Clicked");
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
//        String path = "/musicplayer/mp3/ConversationattheCross.mp3";
//        media = new Media(getClass().getResource(path).toExternalForm());
//        mediaPlayer = new MediaPlayer(media);
        GUI.getNowPlayingSlider().setMax(totalTime);
        if (mediaPlayer.getStatus() != Status.PLAYING) {
            isPlaying = true;
//            if (mediaPlayer.getStatus().PLAYING) {
//                
//            }
            new Thread() {
                public void run() {
                    if (mediaPlayer.getStatus() == Status.PAUSED) {
                        mediaPlayer.getCurrentTime();
                    }
                    
                    
                    
                    mediaPlayer.play();
                    System.out.println(mediaPlayer.getCurrentTime().toSeconds());
                    GUI.getNowPlayingSlider().setValue(40);
                }
            }.start();
            GUI.addIcon(GUI.getPlayBtn(), "icons/pause.png");
        } else {
            isPlaying = false;
//            Duration d1 = mediaPlayer.getCurrentTime();
            mediaPlayer.pause();
            GUI.addIcon(GUI.getPlayBtn(), "icons/play.png");
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
