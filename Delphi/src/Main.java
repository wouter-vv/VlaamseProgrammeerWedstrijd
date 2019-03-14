
import static java.lang.Integer.max;
import java.util.List;
import java.util.Scanner;

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

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
        
    int laagstePrijs = 0;
    

    public void init () {
        Scanner scanner = new Scanner(System.in);
               
        int aantalTestgevallen = Integer.parseInt(scanner.next());
        
        for (int test = 0; test < aantalTestgevallen; test++) {
            int maxNee = Integer.parseInt(scanner.next());
            int maxJaar = Integer.parseInt(scanner.next());
        
            
            int res = maxJaar;
            //rooster[i][j]: minVragen voor i Nee en j jaar
            int[][] rooster = new int[maxNee+1][maxJaar+1];
            // 1 test voor 1 jaar, 0 testen voor 0 jaar
            for (int i = 1; i <= maxNee; i++){
                rooster[i][1] = 1;
                rooster[i][0] = 0;
            }
                // 1 nee -> elk jaar -> jaar
            for (int j = 1; j <= maxJaar; j++) rooster[1][j] = j; 
            
            // min over x van max van jaren ervoor of jaren erna
            // max van functie(1 neevraag minder, jaar ervoor) geval nee
            // en 1+functie(evenveel neevragen, jaren erna) geval ja
            for (int i = 2; i <= maxNee; i++){
                for (int j = 2; j <= maxJaar; j++){
                    rooster[i][j] = 99999;
                    for (int x = 1; x <= j; x++){
                        res = max(rooster[i-1][x-1], 1+rooster[i][j-x]);
                        if (res < rooster[i][j]) rooster[i][j] = res;
                    }
                }

            }
            
            int max = 0;
            for (int rij = 0; rij <= maxJaar; rij++)
            {
                 if (rooster[maxNee][rij] > max)
                 {
                  max = rooster[maxNee][rij];
                 }
            }
            System.out.println(test+1 + " " + max);
        }
    }
    
}
