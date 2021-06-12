package musicplayer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Playlist {
    private AVL playlist;
    
    public Playlist() {
        playlist = new AVL();
    }
    
    public void loadSongs(File song) throws IOException {
        FileInputStream fis = new FileInputStream(song);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        
        String line = null;
        
        while ((line = br.readLine()) != null) {
            getPlaylist().add(new Song(line));
        }
        
        br.close();
    }
    
    public void addSong(Song song) {
        getPlaylist().add(song);
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
}
