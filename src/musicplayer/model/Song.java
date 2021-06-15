package musicplayer.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Song {
    private File song;
    private String path;
    private String name;
    
    public Song(String songPath) {        
//        song = new File(Song.class.getResource(songPath).getFile());
        path = songPath;
        Path aaa = Paths.get(path);
        name = aaa.getFileName().toString();
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
