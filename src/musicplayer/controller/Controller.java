package musicplayer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import musicplayer.model.Playlist;
import musicplayer.model.Song;
import musicplayer.view.View;

public class Controller {
    private Stage stage;
    private Playlist playlist;
    private Song song;
//    private ButtonController btnController;
    private View GUI;
    private String[] args;
    public Controller() {
        
    }
    public Controller(String[] args) throws IOException {
        GUI = new View();
        this.args = args;
        this.playlist = GUI.getPlaylist();
        
//        btnController = new ButtonController();
        
        new Thread() {
            public void run() {
                GUI.display(args);
            }
        }.start();
    }
    
    public Song addButtonClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
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
    
    public void playButtonClicked(Playlist playlist) {
        playAudio("mp3/Day's Psalm.mp3");
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
    
    public void playAudio(String path) {
//        Audio audio = new Audio(getClass().getResource("mp3/Day's Psalm.mp3").toString());
//        audio.play();
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play(); 
    }
    
    
    
    
    
    
    
    
    
    public void loadPlaylist() {
        
    }
    
    public void savePlaylist() {
        
    }
}
