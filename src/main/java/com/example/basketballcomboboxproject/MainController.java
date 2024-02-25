package com.example.basketballcomboboxproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.BasketballPlayer;

import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TableView allTable;
    public TableColumn allJerseyCol;
    public TableColumn allNameCol;
    public TableColumn allPointsCol;
    public TableColumn allTeamCol;

    public Label resultsLBL;
    public TextField queryTF;
    public Button getResults;
    public ComboBox<BasketballPlayer> combo1;
    public ComboBox <BasketballPlayer> combo2;
    public ComboBox <LocalTime> combo3;
    public Button pullBtn;
    public Button clrButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allJerseyCol.setCellValueFactory(new PropertyValueFactory<>("jersey"));
        allNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        allTeamCol.setCellValueFactory(new PropertyValueFactory<>("team"));


        ObservableList<BasketballPlayer> players = BasketballPlayer.getAllPlayers();
        allTable.setItems(players);
        resultsLBL.setText(Integer.toString(players.size()) + " players returned.");



        combo1.setItems(players);
        // data looks weird

        // had to run the toString method - sucess

        // this makes 5 show up at a time with a drop down
        combo1.setVisibleRowCount(5);

        // this will make it so it chooses the first one
        combo1.getSelectionModel().selectFirst();

        // this will have a prompt for the text
        combo2.setItems(players);
        combo2.setPromptText("You must choose a player.....");

        // this will start it as a search with the object
        //BasketballPlayer bp = getPlayerwithJersey(32);
        //combo2.setValue(bp);


        // local time
        LocalTime start = LocalTime.of(6, 0);
        LocalTime end = LocalTime.NOON;

        while(start.isBefore(end.plusSeconds(1))){
            combo3.getItems().add(start);

            start = start.plusMinutes(10);
        }

        combo3.getSelectionModel().select(LocalTime.of(8,0));


        //Lambda #1
        // factory for the list cells
        Callback<ListView<BasketballPlayer>, ListCell<BasketballPlayer>> factory = lv -> new ListCell<BasketballPlayer>(){
            @Override
            protected void updateItem(BasketballPlayer item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "NOTHING" : ("Use :" + item.getName()));
            }
        };


        // lambda #2
        // used  a different factory for te button cell
        Callback<ListView<BasketballPlayer>, ListCell<BasketballPlayer>> factoryUsed = lv -> new ListCell<BasketballPlayer>(){
            @Override
            protected void updateItem(BasketballPlayer item, boolean empty){
                setText(empty ? "" : ("Your Pick : " + item.getName()));
            }
        };

        combo1.setCellFactory(factory);
        combo1.setButtonCell(factoryUsed.call(null));








    }

    public void getResultsHandler(ActionEvent actionEvent) {

        String q = queryTF.getText();

        ObservableList<BasketballPlayer> players = searchByLayerName(q);

        if(players.size() == 0){

            try {
                int jersey = Integer.parseInt(q);

                BasketballPlayer bp = getPlayerwithJersey(jersey);

                if (bp != null)
                    players.add(bp);
            }catch (NumberFormatException e){
                // ignore
            }
        }



      

    }










    private ObservableList<BasketballPlayer> searchByLayerName(String partialName) {
        ObservableList<BasketballPlayer> namedPlayers = FXCollections.observableArrayList();

        ObservableList<BasketballPlayer> allPlayers = BasketballPlayer.getAllPlayers();

        for (BasketballPlayer bp : allPlayers) {
            if (bp.getName().contains(partialName)) {
                namedPlayers.add(bp);
            }
        }

        return namedPlayers;
    }

    private BasketballPlayer getPlayerwithJersey (int jersey){


        ObservableList<BasketballPlayer> allPlayers = BasketballPlayer.getAllPlayers();

        for(int i =0; i<allPlayers.size(); i++){
            BasketballPlayer bp = allPlayers.get(i);

            if(bp.getJersey() == jersey){
                return bp;
            }
        }

        return null;
    }


    public void onCombo1(ActionEvent actionEvent) {
        onPull(null);
    }

    public void onCombo2(ActionEvent actionEvent) {

        onPull(null);
    }

    public void onCombo3(ActionEvent actionEvent) {
        onPull(null);
    }

    public void onPull(ActionEvent actionEvent) {

        StringBuilder sb = new StringBuilder((""));

        BasketballPlayer selbp = combo1.getSelectionModel().getSelectedItem();

        if(selbp == null ){
            sb.append("CB1: null");
        }
        else {
            sb.append("CB1: " + selbp.getName());
        }
        sb.append(" | ");

        if(combo2.getValue() == null){
            sb.append("CB2: null");
        }
        else{
            sb.append("CB2: " + combo2.getValue().getName());
        }

        sb.append(" | ");

        if(combo3.getValue() == null){
            sb.append("CB3: null");
        }
        else {
            sb.append("CB3: " + combo3.getValue());
        }

        resultsLBL.setText(sb.toString());
    }

    public void onClear(ActionEvent actionEvent) {
        combo1.getSelectionModel().clearSelection();
        combo2.getSelectionModel().clearSelection();
        combo3.getSelectionModel().clearSelection();

    }
}
