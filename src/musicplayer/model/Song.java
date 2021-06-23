package musicplayer.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

// Song Object class
public class Song {
    private File song;
    private String path;
    private String name;
    
    public Song(String songPath) {
        path = songPath;
        Path aaa = Paths.get(path);
        name = aaa.getFileName().toString();
    }
    
    // override compareTo
    public int compareTo(Song cSong) {
        return getName().compareTo(cSong.getName());
    }
    // override equals
    public boolean equals(Song tSong) {
        if (this == tSong) {
            return true;
        } else {
            return false;
        }
    }
    
    // Getters
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
