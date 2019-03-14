
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class Main {

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
            
            List<Set<Integer>> hasCards = new ArrayList<>();
            while(hasCards.size() < 4) hasCards.add(new  LinkedHashSet<>());
            List<Set<Integer>> doesntHaveCards = new ArrayList<>();
            while(doesntHaveCards.size() < 4) doesntHaveCards.add(new  LinkedHashSet<>());

            for (int guess = 0; guess < amountGuesses; guess++) {
                String input = scanner.nextLine();
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
                            doesntHaveCards.get(i).add((int)params.charAt(0));
                            doesntHaveCards.get(i).add((int)params.charAt(1));
                            doesntHaveCards.get(i).add((int)params.charAt(2));
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
                            hasCards.get(answeredPlayer-1).add((int)params.charAt(0));
                            hasCards.get(answeredPlayer-1).add((int)params.charAt(1));
                            hasCards.get(answeredPlayer-1).add((int)params.charAt(2));
                            break;
                        //if you didnt answer, add to the cards you dont have
                        } else if(nextPlayer != answeredPlayer) {
                            doesntHaveCards.get(nextPlayer-1).add((int)params.charAt(0));
                            doesntHaveCards.get(nextPlayer-1).add((int)params.charAt(1));
                            doesntHaveCards.get(nextPlayer-1).add((int)params.charAt(2));
                        }
                    }
                }                
            }
            
            // remove the cards in the hasCardsSet that can also be found in the doesntHaveCards set
            for (int i = 0; i < amountPlayers; i++) {
                // sort the hasCardsSet
                List<Integer> sortlist = new ArrayList<>(hasCards.get(i));
                Collections.sort(sortlist);
                hasCards.set(i, new LinkedHashSet<>(sortlist));
                
                // delete the cards found in both sets
                for (Integer doesntHaveCard : doesntHaveCards.get(i)) {
                    for (Iterator<Integer> iterator = hasCards.get(i).iterator(); iterator.hasNext();) {
                        Integer temp = iterator.next();
                        if(doesntHaveCard == temp) {
                            iterator.remove();
                        }
                    }
                }
            }
            
            // create a list of the unique cards and filter these from all the cards
            // this way you keep the doubles, those have to be filtered out
            List<Integer> allCards =  new LinkedList<>();
            List<Integer> allCardsUnique =  new LinkedList<>();
            for (int i = 0; i < amountPlayers; i++) {
                for (Iterator<Integer> iterator = hasCards.get(i).iterator(); iterator.hasNext();) {
                    allCards.add(iterator.next());
                }
            }
            for (int i = 0; i < allCards.size(); i++) {
                int tmp = allCards.get(i);
                allCards.set(i, 0);
                if(allCards.contains(tmp)) {
                    allCards.set(i, tmp);
                    if(!allCardsUnique.contains(tmp)) {
                        allCardsUnique.add(tmp);
                    }
                } else {
                    allCards.remove(i);
                    i--;
                }
            }

            // remove doubles if the amount of cards are sufficient, otherwise you keep the card
            // and move all the other doubles to the front and filter these out first
            for (int i = 0; i < allCards.size();) {
                for (int j = 0; j < amountPlayers; j++) {
                    for (Iterator<Integer> iterator = hasCards.get(j).iterator(); iterator.hasNext();) {
                        Integer temp = iterator.next();
                        if(allCards.size() != 0) {
                            if(allCards.get(i) == temp) {
                                if(hasCards.get(j).size() > cardsPerPerson) {
                                    iterator.remove();
                                    allCards.remove(i);
                                } else {
                                    allCards.remove(i);
                                    for (int k = 0; k < allCards.size(); k++) {
                                        if(allCards.get(k) == temp) {
                                            int replace = allCards.get(k);
                                            allCards.remove(k);
                                            allCards.add(0, replace);
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                }
                if(allCardsUnique.size() == allCards.size()) break;
            }
            
            // if after filtering the cards, multiple cards remain, keep the first one
            for (int j = 0; j < amountPlayers; j++) {
                int cards = 0;
                for (Iterator<Integer> iterator = hasCards.get(j).iterator(); iterator.hasNext();) {
                    cards++;
                    int test = iterator.next();
                    if(cards > cardsPerPerson) {
                        iterator.remove();
                    }
                }
            }
            solutions[game]=game+1+" ";
            // print solution at the end of each game
            for (int i = 0; i < amountPlayers; i++) {
                for (Iterator<Integer> iterator = hasCards.get(i).iterator(); iterator.hasNext();) {
                    int temp = iterator.next();
                    solutions[game] += (char)temp;
                
                }
                if (amountPlayers-1 > i) {
                    solutions[game] += " ";
                }
                

            }
        }
        for (int i = 0; i < solutions.length; i++) {
            System.out.println(solutions[i]);
        }
    }
}
