package musicplayer.model;

import java.io.File;

public class Song {
    private File song;
    private String path;
    private String name;
    
    public Song(String songPath) {        
        song = new File(Song.class.getResource(songPath).getFile());
        path = songPath;
        name = song.getName();
    }
        
    public int compareTo(Song cSong) {
        return getName().compareTo(cSong.getName());
    }
    
    public boolean equals(Song tSong) {
        if (this == tSong) {
            return true;
        } else {
            return false;
        }
    }
    public File getSong() {
        return song;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
