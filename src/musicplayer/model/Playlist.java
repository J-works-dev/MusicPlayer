package musicplayer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

// Playlist class
public class Playlist {
    private static AVL playlist;
    private static HashMap<String, Song> hMap;
    
    public Playlist() {
        playlist = new AVL();
        hMap = new HashMap<>(); // Hash Map has Name and Song object itself
    }
    // Load song from the file
    public void loadSongs(File song) throws IOException {
        FileInputStream fis = new FileInputStream(song);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        
        String line = null;
        
        while ((line = br.readLine()) != null) {
            addSong(new Song(line));
        }
        
        br.close();
    }
    // Add a Song to Playlist and add to HashMap
    public void addSong(Song song) {
        getPlaylist().add(song);
        gethMap().put(song.getName(), song);
    }
    
    // Getters
    public AVL getPlaylist() {
        return playlist;
    }
    
    public Song[] display() {
        return getPlaylist().display();
    }

    public HashMap<String, Song> gethMap() {
        return hMap;
    }
}
