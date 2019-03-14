/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedopoging2;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class CluedoPoging2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int amountGames = Integer.parseInt(scanner.next());
        
        final int amountPlayers = 4;
        
        String[] solutions = new String[amountGames];
        
        for (int game = 0; game < amountGames; game++) {
            int amountPersons = Integer.parseInt(scanner.next());
            int amountLocations = Integer.parseInt(scanner.next());
            int amountWeapons = Integer.parseInt(scanner.next());
            int amountGuesses = Integer.parseInt(scanner.next());
            int cardsPerPerson = ((amountPersons-1) + (amountLocations-1)+ (amountWeapons-1))/4;
            
            scanner.nextLine();
            
            List<Set<Character>> hasCards = new ArrayList<>();
            List<Set<Character>> maybeHasCards = new ArrayList<>();
            List<Set<Character>> doesntHaveCards = new ArrayList<>();
            while(hasCards.size() < 4) hasCards.add(new  LinkedHashSet<>());
            while(doesntHaveCards.size() < 4) doesntHaveCards.add(new  LinkedHashSet<>());
            int t = 0;
            while(t <= amountPersons) {
                
                maybeHasCards.add(new  LinkedHashSet<>());
                for (int i = 0; i < amountLocations; i++) {
                    maybeHasCards.get(t).add((char)(65+i));
                }
                for (int i = 0; i < amountWeapons; i++) {
                    maybeHasCards.get(t).add((char)(97+i));
                }
                for (int i = 0; i < amountPersons; i++) {
                    maybeHasCards.get(t).add((char)(48+i));
                }
                t++;
            }
            List<String> inp = new ArrayList<>();
            for (int guess = 0; guess < amountGuesses; guess++) {
                String input = scanner.nextLine();
                inp.add(input);
                String[] splited = input.split(" ");
                int currPlayer = Integer.parseInt(splited[0]);
                String params = splited[1];
                int answeredPlayer;
                // set X as -1
                if(!(splited[2].equals("X"))){
                    answeredPlayer = Integer.parseInt(splited[2]);
                } else {
                    answeredPlayer = -1;
                }

                // no one has these cards, add to doesn't have for all players except self
                // X is equal as -1 so we dont have to use a string
                if(answeredPlayer == -1) {
                    for (int i = 0; i < 4; i++) {
                        if(currPlayer != (i+1)) {
                            doesntHaveCards.get(i).add(params.charAt(0));
                            doesntHaveCards.get(i).add(params.charAt(1));
                            doesntHaveCards.get(i).add(params.charAt(2));
                        }
                    }
                } else {
                    int counter = 0;
                    while( !(counter == 4)) {
                        counter++;
                        int nextPlayer = currPlayer + counter;
                        if(!(nextPlayer <= amountPlayers)) {
                            nextPlayer-=4;
                        } 
                        // if you did answer, add to your cards
                        if(nextPlayer == answeredPlayer) {
                            int teller = 0;
                            for (int i = 0; i < 3; i++) {
                                if(doesntHaveCards.get(answeredPlayer-1).contains(params.charAt(i))) {
                                    teller++;
                                }
                            }
                            
                            if(teller ==2) {
                                for (int i = 0; i < 3; i++) {
                                    if(!doesntHaveCards.get(answeredPlayer-1).contains(params.charAt(i))) {
                                        hasCards.get(answeredPlayer-1).add(params.charAt(i));
                                    } else {
                                        for (int j = 0; j < amountPlayers; j++) {
                                            if(answeredPlayer-1 != i) {
                                                doesntHaveCards.get(j).add(params.charAt(i));
                                            }
                                        }
                                    }
                                }
                            } else {                                
                                maybeHasCards.get(answeredPlayer-1).add(params.charAt(0));
                                maybeHasCards.get(answeredPlayer-1).add(params.charAt(1));
                                maybeHasCards.get(answeredPlayer-1).add(params.charAt(2));
                            }
                            break;
                        }
                    }
                }          
                int teszerazt =0;      
            }
            int test =0;
            
        }
        
        
    }
    
}
