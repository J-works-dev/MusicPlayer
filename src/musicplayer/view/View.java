package musicplayer.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import musicplayer.controller.Controller;
import musicplayer.model.Playlist;
import musicplayer.model.Song;

// Main View Class - All design is in this class
public class View extends Application {
    private Scene scene;
    private VBox mainVBox, leftVBox, rightVBox, playBox;
    private HBox mainHBox, mPlayer, seekBar;
    private static Button firstBtn, backBtn, playBtn, nextBtn, lastBtn, addBtn, sortBtn, searchBtn, deleteBtn, loadCSVBtn, saveCSVBtn;
    private static Slider nowPlayingSlider;
    private TextField textSearch, textDelete;
    private static TextField textPlaying;
    private static ListView<String> playingList;
    private static Controller controller;
    private Label labelAdd, labelSort, labelSearch, labelDelete, labelCSV;
    private static Label totalTime, currentTime;
    private static Playlist playlist;
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("J-works Music Player");
        playlist = new Playlist();
        initializeComponents(); // Initialize all components
        buttonAction(); // All button actions
        
        // have not used yet
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("main.fxml"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        
        addComponentsToPane(); // Add components to Pane
        
        scene = new Scene(mainVBox, 400, 500);
        stage.setScene(getScene());
        stage.show();
        
        stage.setOnCloseRequest(e -> controller.exit()); // Close application properly
    }
    
    public void display(String[] args) {
        launch(args);
    }
    
    // Initialize al components
    private void initializeComponents() {
        // Buttons
        firstBtn = new Button();
        backBtn = new Button();
        playBtn = new Button();
        nextBtn = new Button();
        lastBtn = new Button();
        addBtn = new Button("Add Song");
        getAddBtn().setPrefSize(150, 20);
        sortBtn = new Button("Sort Playlist");
        getSortBtn().setPrefSize(150, 20);
        searchBtn = new Button("Search Song");
        getSearchBtn().setPrefSize(150, 20);
        deleteBtn = new Button("Delete Song");
        getDeleteBtn().setPrefSize(150, 20);
        loadCSVBtn = new Button("Load CSV");
        getLoadCSVBtn().setPrefSize(150, 20);
        saveCSVBtn = new Button("Save CSV");
        getSaveCSVBtn().setPrefSize(150, 20);
        
        // Labels
        labelAdd = new Label("Add");
        labelSort = new Label("Sort");
        labelSearch = new Label("Search");
        labelDelete = new Label("Delete");
        labelCSV = new Label("CSV");
        currentTime = new Label("00:00");
        totalTime = new Label("00:00");
        
        // Slider
        nowPlayingSlider = new Slider();
        nowPlayingSlider.setMax(200);
        
        // TextFields
        textSearch = new TextField();
        textDelete = new TextField();
        textPlaying = new TextField();
        textPlaying.setEditable(false);
        textPlaying.setFont(Font.font(10));
        
        // ListView
        playingList = new ListView<>();

        // Icons for buttons
        addIcon(getFirstBtn(), "icons/first.png");
        addIcon(getBackBtn(), "icons/back.png");
        addIcon(getPlayBtn(), "icons/play.png");
        addIcon(getNextBtn(), "icons/next.png");
        addIcon(getLastBtn(), "icons/last.png");
    }
    
    // Add components to Pane
    private void addComponentsToPane() {
        // Set Labels
        labelAdd.setPadding(new Insets(10, 0, 0, 0));
        labelSort.setPadding(new Insets(10, 0, 0, 0));
        labelSearch.setPadding(new Insets(10, 0, 0, 0));
        labelDelete.setPadding(new Insets(10, 0, 0, 0));
        labelCSV.setPadding(new Insets(10, 0, 0, 0));
        
        // Funtionality Section
        leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10, 10, 10, 10));
        leftVBox.setSpacing(10);
        leftVBox.getChildren().addAll(labelAdd, getAddBtn(), labelSearch, textSearch, getSearchBtn(), labelDelete, textDelete, getDeleteBtn(), labelCSV, getLoadCSVBtn(), getSaveCSVBtn()); // labelSort, sortBtn, 
        
        // Display Playlist
        rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10, 10, 10, 10));
        rightVBox.setSpacing(10);
        rightVBox.getChildren().addAll(getPlayingList());
        
        mainHBox = new HBox();
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        
        // Seek Slider and Time
        seekBar = new HBox();
        seekBar.setMargin(nowPlayingSlider, new Insets(0, 3, 0, 3));
        seekBar.getChildren().addAll(currentTime, nowPlayingSlider, totalTime);
        
        // Slider and Text for playing song name
        playBox = new VBox();
        playBox.setMargin(textPlaying, new Insets(5, 0, 0, 0));
        playBox.getChildren().addAll(seekBar, textPlaying);
        
        // Music Player
        mPlayer = new HBox();
        mPlayer.setPadding(new Insets(10, 10, 10, 10));
        mPlayer.setMargin(playBox, new Insets(0, 0, 0, 15));
        mPlayer.setSpacing(10);
        mPlayer.getChildren().addAll(getBackBtn(), getPlayBtn(), getNextBtn(), playBox);
        
        mainVBox = new VBox();
        mainVBox.getChildren().addAll(mainHBox, mPlayer);
    }
    
    // All Button Actions
    private void buttonAction() {
        // When Button action occured, call main function in Controller
        controller = new Controller();
        
        // Add Song
        getAddBtn().setOnAction(e -> {
            try {
                Song song = controller.addButtonClicked();
                if (song != null) {
                    playlist.addSong(song);
                    displayPlaylist();
                }
                
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // not using
        getSortBtn().setOnAction(e -> controller.sortButtonClicked());
        
        // Search a Song
        getSearchBtn().setOnAction(e -> {
            String key = textSearch.getText();
            if (key != null) {
                controller.searchButtonClicked(key);
            }
        });
        
        // Delete a Song
        getDeleteBtn().setOnAction(e -> {
            String key = textDelete.getText();
            System.out.println(key);
            controller.deleteButtonClicked(key);
        });
        
        // Player buttons
        getFirstBtn().setOnAction(e -> controller.firstButtonClicked());
        
        getBackBtn().setOnAction(e -> controller.backButtonClicked());
        
        getPlayBtn().setOnAction(e -> controller.playButtonClicked());
        
        getNextBtn().setOnAction(e -> controller.nextButtonClicked());
        
        getLastBtn().setOnAction(e -> controller.lastButtonClicked());
        
        // CSV buttons
        getLoadCSVBtn().setOnAction(e -> controller.loadCSVButtonClicked());
        
        getSaveCSVBtn().setOnAction(e -> controller.saveCSVButtonClicked());
        
        // Playlist click option
        getPlayingList().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                controller.listViewDoubleClicked();
            } else if (e.getClickCount() == 1) {
                textDelete.setText(getPlayingList().getSelectionModel().getSelectedItem());
            }
            
        });
    }
    
    // Add Icon to Button
    public void addIcon(Button button, String iconPath) {
        try {
            Image icon = new Image(getClass().getResource(iconPath).toExternalForm());
            ImageView view = new ImageView(icon);
            view.setFitHeight(20);
            view.setPreserveRatio(true);
            button.setGraphic(view);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, iconPath + " not found." + ex);
        }
    }
    
    // Add a item to list
    public void addPlaylist(String key) {
        getPlayingList().getItems().add(key);
        
    }
    
    // Display Playlist
    public void displayPlaylist() {
        Song[] songs = playlist.getPlaylist().display();
        if (songs != null) {
            getPlayingList().getItems().clear();
            for (Song song : songs) {
                getPlayingList().getItems().add(song.getName());
            }
        }
    }
    
    // Getter and Setter
    public Scene getScene() {
        return scene;
    }
    
    public ListView<String> getPlayingList() {
        return playingList;
    }

    public Button getFirstBtn() {
        return firstBtn;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public Button getPlayBtn() {
        return playBtn;
    }

    public Button getNextBtn() {
        return nextBtn;
    }

    public Button getLastBtn() {
        return lastBtn;
    }

    public Button getAddBtn() {
        return addBtn;
    }

    public Button getSortBtn() {
        return sortBtn;
    }

    public Button getSearchBtn() {
        return searchBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }
    
    public Button getLoadCSVBtn() {
        return loadCSVBtn;
    }
    
    public Button getSaveCSVBtn() {
        return saveCSVBtn;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
    
    public void setPlaylist(Playlist list) {
        this.playlist = list;
    }
    
    public Slider getNowPlayingSlider() {
        return nowPlayingSlider;
    }
    
    public TextField getTextPlaying() {
        return textPlaying;
    }
    
    public Label getCurrentTime() {
        return currentTime;
    }
    
    public void setCurrentTime(String time) {
//        this.currnetTime.setText(time);
    }
    
    public Label getTotalTime() {
        return totalTime;
    }
}
