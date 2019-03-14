
import java.util.Arrays;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * oplossing gebaseerd op de het levenshtein algoritme 
 * oplossing gebaseerd op de code op volgende website:
 * https://www.baeldung.com/java-levenshtein-distance
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class Main {

    public static void main(String[] args) {
        Main gb = new Main();
        gb.init();
    }
    
    public void init () {
        Scanner scanner = new Scanner(System.in);
               
        int aantalTesten = Integer.parseInt(scanner.nextLine());
        String correct, poging;
        
        for (int test = 0; test < aantalTesten; test++) {
            correct = scanner.nextLine();
            poging = scanner.nextLine();
            
            System.out.println(test +1 + " " +bereken(poging, correct));
        }
        
    }
    
    int bereken(String x, String y) {
        // aantal foutpunten per character
        int[][] foutPunten = new int[x.length() + 1][y.length() + 1];
        
        // overloop alle characters van beide strings en vergelijk ze met elkaar
        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    foutPunten[i][j] = 2*j;
                }
                else if (j == 0) {
                    foutPunten[i][j] = 2*i;
                }
                else {
                    // vergelijk de 2 karakters en kijk of het lager is, 
                    // als het niet lager is, is het sowieso een fout van 2 punten
                    foutPunten[i][j] = min(foutPunten[i - 1][j - 1] 
                     + puntenFoutenVervanging(x.charAt(i - 1), y.charAt(j - 1)), 
                      foutPunten[i - 1][j] + 2, 
                      foutPunten[i][j - 1] + 2);
                }
            }
        }

        return foutPunten[x.length()][y.length()];
    }
    
    /**
     * vergelijk 2 karakters en bepaal de punten
     * @param karakter1
     * @param karakter2
     * @return 
     */
    public static int puntenFoutenVervanging(char karakter1, char karakter2) {
        // ze zijn gelijk
        if (karakter1 == karakter2) {
            return 0 ;
        }
        // ze verschillen 32 van elkaar, van hoofdletter naar kleine
        if( karakter1+32 == karakter2 || karakter1-32 == karakter2) {
            // kijk of het wel een letter is
            if((karakter1 >64 && karakter1 <91) || (karakter1 >96 && karakter1 <123)){
                return 1;
            }
        }
        // anders fout is 2
        return 2;
        
    }
 
    public static int min(int... numbers) {
        return Arrays.stream(numbers)
          .min().orElse(Integer.MAX_VALUE);
    }
    
}
