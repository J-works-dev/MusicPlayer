package musicplayer.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import musicplayer.model.Playlist;
import musicplayer.model.Song;

public class CSVHandler {
    
    public static Playlist loadPlaylistFromCSV(File fileName) {
        Playlist result = new Playlist();
        CSVReader reader;
        
        try {
            reader = new CSVReader(new FileReader(fileName));
            String[] nextLine;
            Song song;

            while ((nextLine = reader.readNext()) != null) {
                song = new Song(nextLine[0]);
                
                result.addSong(song);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        
        return result;
    }

    public static void writePlaylistToCSV(Playlist playlist) throws IOException {

        CSVWriter writer;
        File file = new File("playlist.csv");
        
        try {
            writer = new CSVWriter(new FileWriter(file));
            
            for (Song song : playlist.getPlaylist()) {
                String[] path = {song.getPath()};
                writer.writeNext(path);
            }
            writer.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }
}
