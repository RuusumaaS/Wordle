/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roseland.wordle;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import java.util.Vector;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
/**
 *
 * @author asus
 */
public class GameWindow extends Application{
    
    
    private Vector<Vector<Label>> columns;
    private GameHandler game;
    private int rowPointer;
    private int letterPointer;
    private boolean gameOver;
    
    @Override
    public void start(Stage stage){
        
        stage.setTitle("Wordle");
        
        this.columns = new Vector<>();
        this.rowPointer = 0;
        this.letterPointer = 0;
        
        
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(grid);
        
        
        Label text = new Label("Guess the word in " + getGame().getTurns() + " turns");
        vbox.getChildren().addAll(text, grid);
        
        for(int i = 0; i < getGame().getTurns(); ++i){
            Vector<Label> row = new Vector<>();
            for(int j = 0; j < getGame().getWord().length(); ++j){
                Label letterField = new Label("");
                letterField.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                letterField.setId(String.format("%d%d",i,j));
                letterField.setMinWidth(30);
                letterField.setMinHeight(30);
                letterField.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY, Insets.EMPTY)));
                row.add(letterField);
                grid.add(letterField, j, i+1);
                
            }
            this.columns.add(row);
            
        }
        
        Scene scene = new Scene(vbox, 400, 400);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                

                if(event.getCode().equals(KeyCode.ENTER)){
                    if(isAnswerReady()){
                        String typedWord = "";
                        for(int i = 0; i < getGame().getWord().length(); ++i){
                            typedWord = typedWord + getColumns().elementAt(getRowPointer()).
                                    elementAt(i).getText();
                        }
                        int[] values = getGame().checkWord(typedWord);
                        paintLabels(values);
                        ++rowPointer;
                        letterPointer = 0;
                        if(game.gameOver(typedWord)){
                            GameOverWindow window = new GameOverWindow(true,getGame().getWord());
                            stage.close();
                            
                        }
                        else if(getRowPointer() > game.getTurns()){
                            GameOverWindow window = new GameOverWindow(false,getGame().getWord());
                            stage.close();
                        }
                    }
                }
                else if(event.getCode().equals(KeyCode.BACK_SPACE)){
                    
                    if(letterPointer != 0){
                        getColumns().elementAt(getRowPointer()).
                                    elementAt(letterPointer-1).setText("");
                    
                        --letterPointer;
                    }
                }
                else{
  
                    if(!isAnswerReady()){

                        if(isLetter(event.getText())){
                            getColumns().elementAt(getRowPointer()).
                                    elementAt(getLetterPointer()).
                                    setText(event.getText().toUpperCase());
                            ++letterPointer;

                        }
                    }
                        
                    
                }
            }
        });
        
        stage.setScene(scene);
        stage.show();
    }

    private boolean isAnswerReady(){

        return getColumns().elementAt(rowPointer).
                elementAt(getGame().getWord().length()-1).getText() != "";
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
    
    public void setGameHandler(int turns, String word){
        this.game = new GameHandler(word,turns);
    }
    
}
