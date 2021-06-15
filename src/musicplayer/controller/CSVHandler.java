package musicplayer.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import musicplayer.model.AVL;
import musicplayer.model.Playlist;
import musicplayer.model.Song;

public class CSVHandler {
    
    public static ArrayList<Song> loadPlaylistFromCSV(File fileName) {
        ArrayList<Song> result = new ArrayList<>();
        CSVReader reader;
        
        try {
            reader = new CSVReader(new FileReader(fileName));
            String[] nextLine;
            Song song;

            while ((nextLine = reader.readNext()) != null) {
                String path = nextLine[0].replace("/", "\\");
                song = new Song(nextLine[0]);
                
                result.add(song);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CSV saving failed.");
        }
        
        return result;
    }

    public static void writePlaylistToCSV(Playlist playlist) throws IOException {

        CSVWriter writer;
        File file = new File("playlist.csv");
        
        try {
            writer = new CSVWriter(new FileWriter(file));
            
            for (Song song : playlist.display()) {
                if (song != null) {
                    String path = song.getPath().replace("\\", "/");
                    String[] paths = {path};
                    writer.writeNext(paths);
                }
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
