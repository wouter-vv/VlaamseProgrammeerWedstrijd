/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class Main {
    public static void main (String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        
        // get the alphabet input as a String
        String[] stringInput = new String[3];
        stringInput[0] = scanner.next();
        stringInput[1] = scanner.next();
        stringInput[2] = scanner.next();
        
        // create a map with each time the letter and the code
        Map <Character, String> alphabet = new HashMap<Character, String>();
        int asciiValue = 65;
        for (int i = 0; i < 52; i+=2) {
            String letter = "";
            for (int j = 0; j < 3; j++) {
                letter += stringInput[j].charAt(i);
                letter += stringInput[j].charAt(i+1);
            }
            alphabet.put((char)asciiValue, letter);
            asciiValue++;
        }
                
        // read the amount of tests
        int amtTestsInteger=0;
        String amtTestsInput = scanner.next();
        amtTestsInteger = Integer.parseInt(amtTestsInput);

        
        // create an array of all the solutions and set them to an empty string
        String[] solutions = new String[amtTestsInteger];
        for (int i = 0; i < amtTestsInteger; i++) {
            solutions[i] = "";
        }
        
        // for loop for each test, and read 3 lines each
        for (int i = 0; i < amtTestsInteger; i++) {
            stringInput = new String[3];
            stringInput[0] = scanner.next();
            stringInput[1] = scanner.next();
            stringInput[2] = scanner.next();
            
            // get 2 chars from each row for 3 columns as a letter
            for (int j = 0; j < stringInput[0].length(); j+=2) {
                String letter = "";
                for (int k = 0; k < 3; k++) {
                    letter += stringInput[k].charAt(j);
                    letter += stringInput[k].charAt(j+1);
                }
                // compare the test with the alphabet, break if it is found
                for ( Map.Entry<Character, String> entry : alphabet.entrySet()) {
                    if(entry.getValue().equals(letter)) {
                        solutions[i]+= entry.getKey();
                        break;
                    }
                }
                
            }
        }
        
        // print out the solutions
        for (int i = 0; i < amtTestsInteger; i++) {
            System.out.print(i+1 + " ");
            System.out.println(solutions[i]);
        }
        
    }
    
}
