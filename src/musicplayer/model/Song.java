package musicplayer.model;

import java.io.File;

public class Song {
    private File song;
    private String path;
    
    public Song(String songPath) {        
        song = new File(Song.class.getResource(songPath).getFile());
        path = songPath;
    }
        
    public File getSong() {
        return song;
    }

    public String getPath() {
        return path;
    }
}
