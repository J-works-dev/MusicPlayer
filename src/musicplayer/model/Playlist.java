package musicplayer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class Playlist {
    private static AVL playlist;
    private static HashMap<String, String> hMap;
    
    public Playlist() {
        playlist = new AVL();
        hMap = new HashMap<>();
    }
    
    public void loadSongs(File song) throws IOException {
        FileInputStream fis = new FileInputStream(song);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        
        String line = null;
        
        while ((line = br.readLine()) != null) {
            addSong(new Song(line));
        }
        
        br.close();
    }
    
    public void addSong(Song song) {
        getPlaylist().add(song);
        gethMap().put(song.getName(), song.getPath());
    }
    
//    public Song getSong(String key) {
//        return getPlaylist().search(key);
//    }
//    
//    public int getCount() {
//        return getPlaylist().size();
//    }
    
    public AVL getPlaylist() {
        return playlist;
    }
    
    public Song[] display() {
        return getPlaylist().display();
    }

    public HashMap<String, String> gethMap() {
        return hMap;
    }
}
