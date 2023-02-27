/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roseland.wordle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author asus
 */
public class GameOverWindow extends Stage {
    
    GameOverWindow(boolean winner, String word){
        this.setTitle("Game over!");
        
        GridPane grid = new GridPane();
        
        Label wordLabel = new Label(String.format("Correct word was %s",word));
        grid.add(wordLabel, 0, 0);
        
        Label result = new Label();
        grid.add(result,0,1);
        
        
        Scene scene = new Scene(grid, 400, 200);
        this.setScene(scene);
        
        if(winner == true){
            result.setText("Congratulations, you won the game!");
        }
        else{
            result.setText("Better luck next time!");
        }
        
        Button newGame = new Button("Close");
        grid.add(newGame,0,2);
        
        newGame.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent t) {
                closeWindow();
            }
        
            
        
        });
        
        
        this.show();
    }
    
    public void closeWindow(){
        this.close();
    }
    
}
