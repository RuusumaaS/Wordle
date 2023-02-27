/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.roseland.wordle;

/**
 *
 * @author asus
 */
public class GameHandler {
    
    private String word;
    private char[] letters;
    private int turns;
    private int current_turn;
    
    
    public GameHandler(String word, int turns){
        this.word = word;
        this.turns = turns;
        this.current_turn = 1;
        this.letters = word.toCharArray();
    }
    
    
    public int[] checkWord(String typedWord){
        char[] typedLetters = typedWord.toCharArray();
        int[] values = new int[getWord().length()];
        for(int i = 0; i < getWord().length(); ++i){
            values[i] = 0;
        }
        
        char[] correctLetters = getWord().toUpperCase().toCharArray();
        
        for(int i = 0; i < getWord().length(); ++i){
            if(typedLetters[i] == correctLetters[i]){
                values[i] = 2;
            }
        }
        
        for(int i = 0 ; i < getWord().length(); ++i){
            
            if(values[i] == 2){
                continue;
            }
            else{
                for(int j = 0; j < getWord().length(); ++j){
                    if(values[j] == 2){
                        continue;
                    }
                    else{
                        if(typedLetters[i] == correctLetters[j]){
                           values[i] = 1;
                           correctLetters[j] = "1".toCharArray()[0];
                           break;
                        }
                    }
                }
                
            }
        }
        return values;
        
    }
    
    
    public String getWord(){
        return this.word;
    }
    
    public int getTurns(){
        return this.turns;
    }
    
    public int getCurrentTurn(){
        return this.current_turn;
    }
    
    private char[] getLetters(){
        return this.letters;
    }
    
    public boolean gameOver(String typedWord){
        System.out.println(typedWord);
        System.out.println(this.getWord().toUpperCase());
        return typedWord.toUpperCase().equals(this.getWord().toUpperCase());
    }
    
    
}
