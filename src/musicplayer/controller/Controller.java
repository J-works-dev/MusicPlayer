package musicplayer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import javafx.application.Platform;
import javafx.animation.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;  
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map.Entry;
import musicplayer.model.Playlist;
import musicplayer.model.Song;
import musicplayer.view.View;

public class Controller {
    private Stage stage;
    private static Playlist playlist;
    private Song song;
    private static View GUI;
    private String[] args;
    private static Media media;
    private static MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean hasCurrent = false;
    private static int totalTime, startTime, stopTime;
    private static Song currentSong;
    private static Song nowPlaying;
    private static String songName, cTime, tTime;
    
    public Controller() {}
    
    public Controller(String[] args) throws IOException {
        GUI = new View(); // GUI
        this.args = args;
        
        new Thread() {
            public void run() {
                GUI.display(args);
            }
        }.start();
    }
 
    // get a song and add to MediaPlayer and set Time and Slider value
    public void handleCurrentSong(Song song) {
        currentSong = song;
        String path = song.getPath();
        path = path.replace("\\", "/");
        path = path.replaceAll(".*src", "");
        media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            totalTime = (int)mediaPlayer.getStopTime().toSeconds();
            GUI.getNowPlayingSlider().setMax(totalTime);
            
            tTime = timeToString(totalTime);
            GUI.getTotalTime().setText(tTime);
            // get Current Time and update to Slider
            mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    cTime = timeToString((int)mediaPlayer.getCurrentTime().toSeconds());
                    GUI.getCurrentTime().setText(cTime);
                    GUI.getNowPlayingSlider().setValue((int)mediaPlayer.getCurrentTime().toSeconds());
                }
            });
            // if Slider value changed, seek that time in playing song.
            GUI.getNowPlayingSlider().valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov)
                {
                    if (GUI.getNowPlayingSlider().isPressed()) {
                        mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(GUI.getNowPlayingSlider().getValue() / 100));
                    }
                }
            });
            
        });
        hasCurrent = true;
        songName = song.getName();
    }
    
    // Add Song
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
    
    // Sorting is automatically done in AVL class
    public void sortButtonClicked() {
        JOptionPane.showMessageDialog(null, "Sort Button Clicked");
    }
    
    // Seach Song by name
    public void searchButtonClicked(String key) {
        Iterator<String> keys = GUI.getPlaylist().gethMap().keySet().iterator();
        GUI.getPlayingList().getItems().clear();
        while(keys.hasNext()){
            String name = keys.next();
            if (name.contains(key)) {
                GUI.getPlayingList().getItems().add(name);
            }
        }
    }
    
    // Delete a selected song
    public void deleteButtonClicked(String key) {
        Song song = GUI.getPlaylist().gethMap().get(key);
        
        if (song != null) {
            GUI.getPlaylist().getPlaylist().delete(song);
            GUI.getPlaylist().gethMap().remove(key);
            JOptionPane.showMessageDialog(null, key + " is Deleted.");
            GUI.displayPlaylist();
        } else {
            JOptionPane.showMessageDialog(null, key + " is not found.");
        }
        
    }
    
    // not using
    public void firstButtonClicked() {
        JOptionPane.showMessageDialog(null, "First Button Clicked");
    }
    
    // Previous Song Play
    public void backButtonClicked() {
        boolean isfound = false;
        Song cursor;
        Song previous;
        
        if (currentSong != null) {
            Iterator<Song> iterator = GUI.getPlaylist().gethMap().values().iterator();
            previous = iterator.next();
            if (currentSong == previous) {
                JOptionPane.showMessageDialog(null, "This is the First Song");
            } else {
                while (iterator.hasNext()) {
                    cursor = iterator.next();
                    if (currentSong == cursor) {
                        isfound = true;
                        break;
                    } else {
                        previous = cursor;
                    }
                }
                if (isfound) {
                    if (isPlaying) {
                        mediaPlayer.stop();
                        isPlaying = false;
                    }
                    handleCurrentSong(previous);
                    playButtonClicked();
                } else {
                    JOptionPane.showMessageDialog(null, "Something Wrong...");
                }
            }
        }
    }
    
    // Play song and toggle button to pause
    public void playButtonClicked() {
        if (GUI.getPlaylist().gethMap().size() == 0) {
            JOptionPane.showMessageDialog(null, "There is no Music file!");
        } else {
            if (!hasCurrent) {
                Iterator<Song> values = GUI.getPlaylist().gethMap().values().iterator();
                handleCurrentSong(values.next());
                hasCurrent = true;
            }
            if (mediaPlayer.getStatus() != Status.PLAYING) {
                
                mediaPlayer.play();
                
                isPlaying = true;
                nowPlaying = currentSong;
                GUI.getTextPlaying().setText("Now Playing: " + songName);
                GUI.addIcon(GUI.getPlayBtn(), "icons/pause.png");
            } else {
                isPlaying = false;
                mediaPlayer.pause();
                startTime = (int)mediaPlayer.getCurrentTime().toSeconds();
                GUI.addIcon(GUI.getPlayBtn(), "icons/play.png");
            }
        }
    }
    
    // Method for changing time to 00:00 formatted string
    private String timeToString(int time) {
        int min = time / 60;
        int sec = time % 60;
        return String.format("%02d:%02d", min, sec);
    }
    
    // Play next song
    public void nextButtonClicked() {
        boolean isfound = false;
        Song cursor;
        Song next = currentSong;
        
        if (currentSong != null) {
            Iterator<Song> iterator = GUI.getPlaylist().gethMap().values().iterator();
            while (iterator.hasNext()) {
                cursor = iterator.next();
                if (currentSong == cursor) {
                    if (iterator.hasNext()) {
                        next = iterator.next();
                        isfound = true;
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "This is the Last Song");
                    }
                }
            }
            if (isfound) {
                if (isPlaying) {
                    mediaPlayer.stop();
                    isPlaying = false;
                }
                handleCurrentSong(next);
                playButtonClicked();
            } else {
                JOptionPane.showMessageDialog(null, "This is the Last Song");
            }
        }
    }
    
    // not using
    public void lastButtonClicked() {
        JOptionPane.showMessageDialog(null, "Last Button Clicked");
    }
    
    // Load playlist from CSV
    public void loadCSVButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\Jeremy\\Documents\\NetBeansProjects\\MusicPlayer"));
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            ArrayList<Song> songs = new ArrayList<>();
            songs = CSVHandler.loadPlaylistFromCSV(file);
            
            for (Song song : songs) {
                GUI.getPlaylist().addSong(song);
                GUI.addPlaylist(song.getName());
            }
            JOptionPane.showMessageDialog(null, "CSV loaded");
        } else {
            JOptionPane.showMessageDialog(null, "Fils is not selected.");
        }
    }
    
    // Sava playlist to CSV
    public void saveCSVButtonClicked() {
        try {
            CSVHandler.writePlaylistToCSV(GUI.getPlaylist());
            JOptionPane.showMessageDialog(null, "CSV saved");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Something wrong..");
        }
        
    }
    
    // Play selected song when list double clicked
    public void listViewDoubleClicked() {
        String nextSong = GUI.getPlayingList().getSelectionModel().getSelectedItem();
        Iterator<String> keys = GUI.getPlaylist().gethMap().keySet().iterator();
        Song song = GUI.getPlaylist().gethMap().get(nextSong);

        if (GUI.getPlaylist().getPlaylist().search(song)) {
            if (isPlaying) {
                mediaPlayer.stop();
                isPlaying = false;
            }
            handleCurrentSong(song);
            playButtonClicked();
        }
        GUI.displayPlaylist();
    }
    
    // Exit application
    public void exit() {
        Platform.exit();
        System.exit(0);
    }
}
