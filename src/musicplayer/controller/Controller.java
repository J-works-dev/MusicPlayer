package musicplayer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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
//    private ButtonController btnController;
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
        currentSong = song;
        String path = song.getPath();
        path = path.replace("\\", "/");
        path = path.replaceAll(".*src", "");
        media = new Media(getClass().getResource(path).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            totalTime = (int)mediaPlayer.getStopTime().toSeconds();
            GUI.getNowPlayingSlider().setMax(totalTime);
            GUI.getNowPlayingSlider().setValue((int)mediaPlayer.getCurrentTime().toSeconds());
            tTime = timeToString(totalTime);
            
            GUI.getTotalTime().setText(tTime);
            
            mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    if (GUI.getNowPlayingSlider().isValueChanging()) {
                        cTime = timeToString((int)mediaPlayer.getCurrentTime().toSeconds());
                        GUI.getCurrentTime().setText(cTime);
                    }
                }
            });
            
            GUI.getNowPlayingSlider().valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov)
                {
                    if (GUI.getNowPlayingSlider().isPressed()) {
                        mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(GUI.getNowPlayingSlider().getValue() / 100));
                        System.out.println(mediaPlayer.getStatus());
                    }
                }
            });
            
        });
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
        GUI.getPlayingList().getItems().clear();
        while(keys.hasNext()){
            String name = keys.next();
            if (name.contains(key)) {
                GUI.getPlayingList().getItems().add(name);
            }
        }
    }
    
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
    
    public void firstButtonClicked() {
        JOptionPane.showMessageDialog(null, "First Button Clicked");
    }
    
    public void backButtonClicked() {
        if (GUI.getPlaylist().gethMap().size() != 0) {
            if (GUI.getPlaylist().getPlaylist().previous(currentSong) != null) {
                mediaPlayer.stop();
//                currentSong = GUI.getPlaylist().getPlaylist().previous(currentSong);
                handleCurrentSong(GUI.getPlaylist().getPlaylist().previous(currentSong));
                playButtonClicked();
            } else {
                JOptionPane.showMessageDialog(null, "This is the First Song");
            }
        } else {
            JOptionPane.showMessageDialog(null, "There is no Music file!");
        }
    }
    
    public void playButtonClicked() {
        if (GUI.getPlaylist().gethMap().size() == 0) {
            JOptionPane.showMessageDialog(null, "There is no Music file!");
        } else {
            if (!hasCurrent) {
                Iterator<Song> values = GUI.getPlaylist().gethMap().values().iterator();
                handleCurrentSong(values.next());
                hasCurrent = true;
            }
            System.out.println(mediaPlayer.getStatus());
            if (mediaPlayer.getStatus() != Status.PLAYING) {
                if (mediaPlayer.getStatus() == Status.PAUSED) {
//                    mediaPlayer.seek(startTime);
                }
                
                mediaPlayer.play();
                
                isPlaying = true;
                nowPlaying = currentSong;
                GUI.getTextPlaying().setText("Now Playing: " + songName);
                GUI.addIcon(GUI.getPlayBtn(), "icons/pause.png");
            } else {
                isPlaying = false;
                mediaPlayer.pause();
//                System.out.println(mediaPlayer.getStatus());
                startTime = (int)mediaPlayer.getCurrentTime().toSeconds();
                GUI.addIcon(GUI.getPlayBtn(), "icons/play.png");
            }
        }
        // old version
//        if (!hasCurrent) {
//                Iterator<Song> values = GUI.getPlaylist().gethMap().values().iterator();
//                handleCurrentSong(values.next());
//                hasCurrent = true;
//            }
//            Status status = mediaPlayer.getStatus();
//            System.out.println(status);
//            if (status == Status.PLAYING || status == Status.READY) {
//                if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
//                    mediaPlayer.seek(mediaPlayer.getStartTime());
//                    mediaPlayer.play();
//                    GUI.getTextPlaying().setText("Now Playing: " + songName);
//                    GUI.addIcon(GUI.getPlayBtn(), "icons/pause.png");
//                    nowPlaying = currentSong;
//                    isPlaying = true;
//                }
//                else {
//                    mediaPlayer.pause();
//                    startTime = (int)mediaPlayer.getCurrentTime().toSeconds();
//                    GUI.addIcon(GUI.getPlayBtn(), "icons/play.png");
//                    isPlaying = false;
//                }
//            } 
//            if (status ==Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
//                mediaPlayer.play();
//                GUI.getTextPlaying().setText("Now Playing: " + songName);
//                GUI.addIcon(GUI.getPlayBtn(), "icons/pause.png");
//                nowPlaying = currentSong;
//                isPlaying = true;
//            }
    }
    
    private String timeToString(int time) {
        int min = time / 60;
        int sec = time % 60;
        return String.format("%02d:%02d", min, sec);
    }
    
    public void nextButtonClicked() {
        if (GUI.getPlaylist().gethMap().size() != 0) {
            if (GUI.getPlaylist().getPlaylist().next(currentSong) != null) {
                mediaPlayer.stop();
                currentSong = GUI.getPlaylist().getPlaylist().next(currentSong);
                handleCurrentSong(currentSong);
                playButtonClicked();
            } else {
                JOptionPane.showMessageDialog(null, "This is the Last Song");
            }
        } else {
            JOptionPane.showMessageDialog(null, "There is no Music file!");
        }
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
    public void listViewDoubleClicked() {
        String nextSong = GUI.getPlayingList().getSelectionModel().getSelectedItem();
        System.out.println(nextSong);
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
    
    public void exit() {
        Platform.exit();
        System.exit(0);
    }
}
