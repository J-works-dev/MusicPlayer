/**
 * Project AT3
 * SangJoon Lee
 * 30024165
 * 11/06/2021
 */
package musicplayer;

import java.io.IOException;
import musicplayer.controller.Controller;

public class MusicPlayer{

    public static void main(String[] args) throws IOException {
        // Call Controller, it controll all application
        Controller MusicPlayer = new Controller(args);
    }
}
