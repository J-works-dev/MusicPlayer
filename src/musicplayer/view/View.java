package musicplayer.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class View extends Application {
    private Scene scene;
    private VBox vBox;
    private Button firstBtn, backBtn, playBtn, nextBtn, lastBtn;
    private Slider nowPlaySlider;
    private TextField textField;
    private ListView playlist;
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("main.fxml"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }
}
