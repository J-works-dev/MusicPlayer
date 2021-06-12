package musicplayer.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import musicplayer.controller.ButtonController;

public class View extends Application {
    private Scene scene;
    private VBox mainVBox, leftVBox, rightVBox;
    private HBox mainHBox, mPlayer;
    private Button firstBtn, backBtn, playBtn, nextBtn, lastBtn, addBtn, sortBtn, searchBtn, deleteBtn;
    private Slider nowPlayingSlider;
    private TextField textSearch, textDelete;
    private ListView playingList;
    private ButtonController controller;
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("J-works Music Player");
        
        initializeComponents();
        buttonAction();
        
        
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("main.fxml"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        
        addComponentsToPane();
        
        scene = new Scene(mainVBox, 400, 500);
        stage.setScene(scene);
        stage.show();
    }
    
    private void initializeComponents() {
        // Buttons
        firstBtn = new Button();
        backBtn = new Button();
        playBtn = new Button();
        nextBtn = new Button();
        lastBtn = new Button();
        addBtn = new Button("Add Song");
        sortBtn = new Button("Sort Playlist");
        searchBtn = new Button("Search Song");
        deleteBtn = new Button("Delete Song");
        
        // Slider
        nowPlayingSlider = new Slider();
        
        // TextField
        textSearch = new TextField();
        textDelete = new TextField();
        
        // ListView
        playingList = new ListView();

        addIcon(firstBtn, "icons/first.png");
        addIcon(backBtn, "icons/back.png");
        addIcon(playBtn, "icons/play.png");
        addIcon(nextBtn, "icons/next.png");
        addIcon(lastBtn, "icons/last.png");
        
        
    }
    
    private void addComponentsToPane() {
        leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10, 10, 10, 10));
        leftVBox.setSpacing(10);
        leftVBox.getChildren().addAll(addBtn, sortBtn, textSearch, searchBtn, textDelete, deleteBtn);
        
        rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10, 10, 10, 10));
        rightVBox.setSpacing(10);
        rightVBox.getChildren().addAll(playingList);
        
        mainHBox = new HBox();
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        
        mPlayer = new HBox();
        mPlayer.setPadding(new Insets(10, 10, 10, 10));
        mPlayer.setSpacing(10);
        mPlayer.getChildren().addAll(firstBtn, backBtn, playBtn, nextBtn, lastBtn, nowPlayingSlider);
        
        mainVBox = new VBox();
        mainVBox.getChildren().addAll(mainHBox, mPlayer);
    }
    
    private void buttonAction() {
        addBtn.setOnAction(e -> controller.addButtonClicked());
        sortBtn.setOnAction(e -> controller.sortButtonClicked());
        searchBtn.setOnAction(e -> controller.searchButtonClicked());
        deleteBtn.setOnAction(e -> controller.deleteButtonClicked());
        firstBtn.setOnAction(e -> controller.firstButtonClicked());
        backBtn.setOnAction(e -> controller.backButtonClicked());
        playBtn.setOnAction(e -> controller.playButtonClicked());
        nextBtn.setOnAction(e -> controller.nextButtonClicked());
        lastBtn.setOnAction(e -> controller.lastButtonClicked());
    }
    
    private void addIcon(Button button, String iconPath) {
        try {
            Image icon = new Image(iconPath);
            button.setGraphic(new ImageView(icon));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, iconPath + " not found.");
        }
    }
}
