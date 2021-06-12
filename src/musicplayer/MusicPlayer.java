/**
 * Project AT3
 * SangJoon Lee
 * 30024165
 * 11/06/2021
 */
package musicplayer;

//import javafx.application.Application;
//import javafx.stage.Stage;
import musicplayer.view.View;

public class MusicPlayer{

    public static void main(String[] args) {
        View GUI = new View();
        GUI.getStart(args);
    }
}
