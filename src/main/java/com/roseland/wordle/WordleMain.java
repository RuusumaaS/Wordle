package com.roseland.wordle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import static javafx.application.Application.launch;

/**
 * Wordle is a game where gamemaster gives a word and other players have to guess it.
 * In this version someone gives a word and number of turns that players have to guess it.
 * After giving the parameters (word and turns), gamemaster clicks enter-button and a new window
 * opens. In that window there are boxes that in rows. All rows are the same length
 * as number of boxes equals the length of the gamemasters word. Number of rows
 * equals the number of turns. Player only uses keys as there are now buttons.
 * Only buttons needed are letters, backspace and enter.
 * 
 * Each turn player guesses the word. If the player gets a letter correctly and in the 
 * right place, that box will become green. If player guesses right letter but it is in a wrong box
 * that will become yellow. If player has guessed letter that is not in the gamemasters
 * word, the box the guessed letter is becomes red. Game will end when all turns are used
 * or when the word is guessed.
 * 
 * This is not yet complete. I will make that you can give a sentence. So far it works
 * with only one word. The game doesn't allow spaces, numbers or special characters. 
 * Also I will make that you can give text-file where one can have sentences or words to be guessed.
 */
public class WordleMain extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("WordleOptions");
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        
        //Luodaan gridpane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));
        
        Scene scene = new Scene(vbox, 500, 200);
        stage.setScene(scene);
        
        
        Label word = new Label("Enter the word to be guessed: ");
        word.setId("WordEnter");
        
        grid.add(word,0,0);
        
        Label turns = new Label("Enter number of guesses: ");
        turns.setId("TurnsLabel");
        
        grid.add(turns,0,1);
        
        
        TextField wordField = new TextField();
        wordField.setId("fieldOp1");
        
        grid.add(wordField,1,0);
        
        TextField turnsField = new TextField();
        turnsField.setId("fieldOp2");
        
        grid.add(turnsField,1,1);
        
        Button enter = new Button("Enter");
        enter.setId("enter");
        
        grid.add(enter,2,0);
        
        Label exception = new Label("Words cans only consist letters and "
                + "number of words must be an integer");
        
        exception.setId("fieldRes");
        exception.setVisible(false);
        vbox.getChildren().addAll(grid,exception);
        
        enter.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                try{
                    int amount_of_turns = Integer.parseInt(turnsField.getText());
                    String word = wordField.getText().trim();
                    if(!isWord(word)){
                        exception.setVisible(true);
                    }
                    else{
                        GameWindow game = new GameWindow();
                        game.setGameHandler(amount_of_turns, word);
                        game.start(new Stage());
                        stage.close();
                    }
                    
                }
                catch(NumberFormatException e){
                    exception.setDisable(true);
                }
                
            }
            
        });
        
        stage.show();
    }

    public static void main() {
        launch();
    }

    public boolean isWord(String word){
        char[] letters = word.toCharArray();
        for(char letter : letters){
            if(!Character.isLetter(letter)){
                return false;
            }
        }
        return true;
    }
}