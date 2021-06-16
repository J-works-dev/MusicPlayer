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
//import musicplayer.controller.ButtonController;
import musicplayer.controller.Controller;
import musicplayer.model.Playlist;
import musicplayer.model.Song;

public class View extends Application {
    private Scene scene;
    private VBox mainVBox, leftVBox, rightVBox, playBox;
    private HBox mainHBox, mPlayer;
    private static Button firstBtn, backBtn, playBtn, nextBtn, lastBtn, addBtn, sortBtn, searchBtn, deleteBtn, loadCSVBtn, saveCSVBtn;
    private static Slider nowPlayingSlider;
    private TextField textSearch, textDelete;
    private static TextField textPlaying;
    private static ListView<String> playingList;
//    private ButtonController controller;
    private static Controller controller;
    private Label labelAdd, labelSort, labelSearch, labelDelete, labelCSV;
    private static Playlist playlist;
    
//    public View(Playlist list) {
//        this.playlist = list;
//    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("J-works Music Player");
        playlist = new Playlist();
        initializeComponents();
        buttonAction();

        Iterator<String> keys = getPlaylist().gethMap().keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            getPlayingList().getItems().add(key);
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("main.fxml"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        
        addComponentsToPane();
        
        scene = new Scene(mainVBox, 400, 500);
        stage.setScene(getScene());
        stage.show();
        
        stage.setOnCloseRequest(e -> controller.exit());
    }
    
    public void display(String[] args) {
        launch(args);
    }
    
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
        
        // Label
        labelAdd = new Label("Add");
        labelSort = new Label("Sort");
        labelSearch = new Label("Search");
        labelDelete = new Label("Delete");
        labelCSV = new Label("CSV");
        
        // Slider
        nowPlayingSlider = new Slider();
        nowPlayingSlider.setMax(200);
        
        // TextField
        textSearch = new TextField();
        textDelete = new TextField();
        textPlaying = new TextField();
        textPlaying.setEditable(false);
        textPlaying.setFont(Font.font(10));
        
        // ListView
        playingList = new ListView<>();

        addIcon(getFirstBtn(), "icons/first.png");
        addIcon(getBackBtn(), "icons/back.png");
        addIcon(getPlayBtn(), "icons/play.png");
        addIcon(getNextBtn(), "icons/next.png");
        addIcon(getLastBtn(), "icons/last.png");
        
        
    }
    
    private void addComponentsToPane() {
        labelAdd.setPadding(new Insets(10, 0, 0, 0));
        labelSort.setPadding(new Insets(10, 0, 0, 0));
        labelSearch.setPadding(new Insets(10, 0, 0, 0));
        labelDelete.setPadding(new Insets(10, 0, 0, 0));
        labelCSV.setPadding(new Insets(10, 0, 0, 0));
        
        leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10, 10, 10, 10));
        leftVBox.setSpacing(10);
        leftVBox.getChildren().addAll(labelAdd, getAddBtn(), labelSearch, textSearch, getSearchBtn(), labelDelete, textDelete, getDeleteBtn(), labelCSV, getLoadCSVBtn(), getSaveCSVBtn()); // labelSort, sortBtn, 
        
        rightVBox = new VBox();
        rightVBox.setPadding(new Insets(10, 10, 10, 10));
        rightVBox.setSpacing(10);
        rightVBox.getChildren().addAll(getPlayingList());
        
        mainHBox = new HBox();
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        
        playBox = new VBox();
        playBox.setMargin(textPlaying, new Insets(5, 0, 0, 0));
        playBox.getChildren().addAll(nowPlayingSlider, textPlaying);
        
        mPlayer = new HBox();
        mPlayer.setPadding(new Insets(10, 10, 10, 10));
        mPlayer.setSpacing(10);
        mPlayer.getChildren().addAll(getBackBtn(), getPlayBtn(), getNextBtn(), playBox);
//        mPlayer.getChildren().addAll(getFirstBtn(), getBackBtn(), getPlayBtn(), getNextBtn(), getLastBtn(), playBox);
        
        mainVBox = new VBox();
        mainVBox.getChildren().addAll(mainHBox, mPlayer);
    }
    
    private void buttonAction() {
        controller = new Controller();
        
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
        
        getSortBtn().setOnAction(e -> controller.sortButtonClicked());
        
        getSearchBtn().setOnAction(e -> {
            String key = textSearch.getText();
            System.out.println(key);
            controller.searchButtonClicked(key);
        });
        
        getDeleteBtn().setOnAction(e -> {
            String key = textDelete.getText();
            System.out.println(key);
            controller.deleteButtonClicked(key);
        });
        
        getFirstBtn().setOnAction(e -> controller.firstButtonClicked());
        
        getBackBtn().setOnAction(e -> controller.backButtonClicked());
        
        getPlayBtn().setOnAction(e -> controller.playButtonClicked());
        
        getNextBtn().setOnAction(e -> controller.nextButtonClicked());
        
        getLastBtn().setOnAction(e -> controller.lastButtonClicked());
        
        getLoadCSVBtn().setOnAction(e -> controller.loadCSVButtonClicked());
        
        getSaveCSVBtn().setOnAction(e -> controller.saveCSVButtonClicked());
        
        getPlayingList().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                controller.listViewDoubleClicked();
            } else if (e.getClickCount() == 1) {
                textDelete.setText(getPlayingList().getSelectionModel().getSelectedItem());
            }
            
        });
    }
    
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
    
    public Scene getScene() {
        return scene;
    }
    
    public void addPlaylist(String key) {
        getPlayingList().getItems().add(key);
        
    }
    
    public void displayPlaylist() {
        getPlayingList().getItems().clear();
        Song[] songs = playlist.getPlaylist().display();
        for (Song song : songs) {
            getPlayingList().getItems().add(song.getName());
        }
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
}
