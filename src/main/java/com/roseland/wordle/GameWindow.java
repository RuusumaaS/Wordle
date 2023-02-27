/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roseland.wordle;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import java.util.Vector;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
/**
 *
 * @author asus
 */
public class GameWindow extends Stage{
    
    
    private Vector<Vector<Label>> columns;
    private GameHandler game;
    private int rowPointer;
    private int letterPointer;
    private boolean gameOver;
    
    
    GameWindow(int turns, String word){
        
        
        this.setTitle("Wordle");
        this.columns = new Vector<>();
        this.rowPointer = 0;
        this.letterPointer = 0;
        
        this.game = new GameHandler(word,turns);
        
        GridPane grid = new GridPane();
        
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(grid);
        
        Label text = new Label("Guess the word");
        grid.add(text, 0,1);
        
        for(int i = 0; i < turns; ++i){
            Vector<Label> row = new Vector<>();
            HBox labels = new HBox(word.length());
            for(int j = 0; j < word.length(); ++j){
                Label letterField = new Label("");
                letterField.setId(String.format("%d%d",i,j));
                letterField.setMinWidth(20);
                letterField.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
                row.add(letterField);
                labels.getChildren().add(letterField);
            }
            this.columns.add(row);
            grid.add(labels, 1, i);
        }
        
        Scene scene = new Scene(scroll, 150, 150);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                

                if(event.getCode().equals(KeyCode.ENTER)){
                    if(isAnswerReady()){
                        String typedWord = "";
                        for(int i = 0; i < word.length(); ++i){
                            typedWord = typedWord + getColumns().elementAt(rowPointer).
                                    elementAt(i).getText();
                        }
                        int[] values = getGame().checkWord(typedWord);
                        paintLabels(values);
                        ++rowPointer;
                        letterPointer = 0;
                        if(game.gameOver(typedWord)){
                            GameOverWindow window = new GameOverWindow(true,word);
                            
                            
                        }
                        else if(rowPointer > game.getTurns()){
                            GameOverWindow window = new GameOverWindow(false,word);
                            
                        }
                    }
                }
                else if(event.getCode().equals(KeyCode.BACK_SPACE)){
                    
                    if(letterPointer != 0){
                        getColumns().elementAt(rowPointer).
                                    elementAt(letterPointer-1).setText("");
                    
                        --letterPointer;
                    }
                }
                else{
  
                    if(!isAnswerReady()){

                        if(isLetter(event.getText())){
                            getColumns().elementAt(rowPointer).
                                    elementAt(letterPointer).
                                    setText(event.getText().toUpperCase());
                            ++letterPointer;

                        }
                    }
                        
                    
                }
            }
        });
               
        
        this.setScene(scene);
        
        this.show();
        
        
    }

    private boolean isAnswerReady(){

        return getColumns().elementAt(rowPointer).
                elementAt(getGame().getWord().length()-1).getText() != "";
    }    
    
    private void closeWindow(){
        this.close();
    }

    private GameHandler getGame(){
        return this.game;
    }

    public Vector<Vector<Label>> getColumns(){
        return this.columns;
    }

    private int getLetterPointer(){
        return this.letterPointer;
    }

    
    public int getRowPointer(){
        return this.rowPointer;
    }
    
    public boolean isLetter(String letter){
        
        char[] charLetter = letter.toCharArray();
        return Character.isLetter(charLetter[0]);
        
    }
    private void paintLabels(int[] values){
        for(int i = 0; i < values.length; ++i){
            if(values[i] == 0){
                getColumns().elementAt(rowPointer).
                                    elementAt(i).
                        setBackground(new Background
                        (new BackgroundFill(Color.RED,CornerRadii.EMPTY,
                                Insets.EMPTY)));
            }
            else if(values[i] == 1){
                getColumns().elementAt(rowPointer).
                                    elementAt(i).
                        setBackground(new Background
                        (new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY,
                                Insets.EMPTY)));
            }
            else if(values[i] == 2){
                getColumns().elementAt(rowPointer).
                                    elementAt(i).
                        setBackground(new Background
                        (new BackgroundFill(Color.GREEN,CornerRadii.EMPTY,
                                Insets.EMPTY)));
            }
            
        }
    }
}
