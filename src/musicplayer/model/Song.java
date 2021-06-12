package musicplayer.model;

import java.io.File;

public class Song {
    private File song;
    
    public Song(String songPath) {        
        song = new File(Song.class.getResource(songPath).getFile());
    }
        
    public File getSong() {
        return song;
    }
}
