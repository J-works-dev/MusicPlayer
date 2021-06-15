package musicplayer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
    private boolean hasCurrent = false;
    private static int totalTime, startTime, stopTime;
    private static Song currentSong;
    private static String songName;
    
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
    public void handleCurrentSong(Song song) {
//        System.out.println(path);
        String path = song.getPath();
        path = path.replace("\\", "/");
        path = path.replaceAll(".*src", "");
//        System.out.println(path);
        media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        hasCurrent = true;
        songName = song.getName();
    }
    
    public Song addButtonClicked() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Jeremy\\Documents\\NetBeansProjects\\MusicPlayer\\src\\musicplayer\\mp3"));
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
    
    public void searchButtonClicked(String key) {
        Iterator<String> keys = GUI.getPlaylist().gethMap().keySet().iterator();
        Song song = GUI.getPlaylist().gethMap().get(key);
        
        if (GUI.getPlaylist().getPlaylist().search(song)) {
            handleCurrentSong(song);
        }
    }
    
    public void deleteButtonClicked(String key) {
        Song song = GUI.getPlaylist().gethMap().get(key);
        
        if (song != null) {
            GUI.getPlaylist().getPlaylist().delete(song);
            GUI.getPlaylist().gethMap().remove(key);
            JOptionPane.showMessageDialog(null, key + " is Deleted.");
        } else {
            JOptionPane.showMessageDialog(null, key + " is not found.");
        }
    }
    
    public void firstButtonClicked() {
        JOptionPane.showMessageDialog(null, "First Button Clicked");
    }
    
    public void backButtonClicked() {
        mediaPlayer.stop();
        currentSong = GUI.getPlaylist().getPlaylist().previous(currentSong);
        handleCurrentSong(currentSong);
        playButtonClicked();
    }
    
    public void playButtonClicked() {
        if (GUI.getPlaylist() == null) {
            JOptionPane.showMessageDialog(null, "There is no Music file!");
        } else {
            if (!hasCurrent) {
                Iterator<Song> values = GUI.getPlaylist().gethMap().values().iterator();
                currentSong = values.next();
                handleCurrentSong(currentSong);
                hasCurrent = true;
            }
            
            if (mediaPlayer.getStatus() != Status.PLAYING) {
                isPlaying = true;
                mediaPlayer.play();
                try {
                    Thread.sleep(1000); 
                } catch (Exception e) {
                    
                }
                totalTime = (int)mediaPlayer.getMedia().getDuration().toSeconds();
                
//                System.out.println(totalTime);
                GUI.getNowPlayingSlider().setMax(totalTime);
                GUI.getTextPlaying().setText("Now Playing: " + songName);
                new Thread() {
                    public void run() {
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
//                System.out.println(mediaPlayer.getStatus());
                startTime = (int)mediaPlayer.getCurrentTime().toSeconds();
                GUI.addIcon(GUI.getPlayBtn(), "icons/play.png");
            }
        }
        
    }
    
    public void nextButtonClicked() {
        mediaPlayer.stop();
        currentSong = GUI.getPlaylist().getPlaylist().next(currentSong);
        handleCurrentSong(currentSong);
        playButtonClicked();
    }
    
    public void lastButtonClicked() {
        JOptionPane.showMessageDialog(null, "Last Button Clicked");
    }
    
    public void loadCSVButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Jeremy\\Documents\\NetBeansProjects\\MusicPlayer"));
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            ArrayList<Song> songs = new ArrayList<>();
            songs = CSVHandler.loadPlaylistFromCSV(file);
            
            for (Song song : songs) {
                GUI.getPlaylist().addSong(song);
//                String name = song.getName().replaceAll(".*playermp3", "");
                GUI.addPlaylist(song.getName());
            }
            JOptionPane.showMessageDialog(null, "CSV loaded");
        } else {
            JOptionPane.showMessageDialog(null, "Fils is not selected.");
        }
    }
    
    public void saveCSVButtonClicked() {
        try {
            CSVHandler.writePlaylistToCSV(GUI.getPlaylist());
            JOptionPane.showMessageDialog(null, "CSV saved");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Something wrong..");
        }
        
    }
//    
//    public void displayPlaylist(String[] args) {
//        
//        Iterator<String> keys = playlist.gethMap().keySet().iterator();
//        while(keys.hasNext()){
//            String key = keys.next();
//            GUI.addPlaylist(key);
//            GUI.display(args);
//        }
//    }
//    
    public void listViewClicked() {
        mediaPlayer.stop();
        String nextSong = GUI.getPlayingList().getSelectionModel().getSelectedItem();
        searchButtonClicked(nextSong);
        playButtonClicked();
    }
    
    public void exit() {
        Platform.exit();
        System.exit(0);
    }
}
