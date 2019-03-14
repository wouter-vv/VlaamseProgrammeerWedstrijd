
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.stream.IntStream;

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

    boolean [][] adjacencyMatrix;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
    
    public void init() {
        Scanner scanner = new Scanner(System.in);
               
        int aantalTestgevallen = Integer.parseInt(scanner.next());
        
        for (int testGeval = 0; testGeval < aantalTestgevallen; testGeval++) {
            List<List<Integer>> vorigeAntwoorden = new LinkedList<>();
            
            int aantalKnopen = Integer.parseInt(scanner.next());
            
            List<Integer> keienInKnoop = new LinkedList<>();
            List<Integer> keienInKnoopTemp;
            
            
            for (int knoop = 0; knoop < aantalKnopen; knoop++) {
                keienInKnoop.add(Integer.parseInt(scanner.next()));
            }
            
            // tijdelijke lijst met keien
            keienInKnoopTemp = new ArrayList<>(keienInKnoop);
            
            int aantalBogen = Integer.parseInt(scanner.next());
            
            // maak en vul de adjecency matrix
            adjacencyMatrix = new boolean[aantalKnopen][aantalKnopen];
            for (int j = 0; j < aantalBogen; j++) {
                int knoop1 = Integer.parseInt(scanner.next());
                int knoop2 = Integer.parseInt(scanner.next());
                
                adjacencyMatrix[knoop1-1][knoop2-1] = true;
                adjacencyMatrix[knoop2-1][knoop1-1] = true;
            }
            
            List<List<Integer>> aanliggendeKnopenLijst = new ArrayList<>();
            for (int knoop = 0; knoop < aantalKnopen; knoop++) {
                
                aanliggendeKnopenLijst.add(zoekAanliggendeKnopen(keienInKnoop.size(), knoop));
            }
            
            // zoek totdat een gelijke oplossing wordt gevonden
            while(!vorigeAntwoorden.contains(keienInKnoop)) {
                vorigeAntwoorden.add(keienInKnoop);
                
                // verplaats de stenen voor elke aanliggende knoop
                for (int knoop1 = 0; knoop1 < aantalKnopen; knoop1++) {
                    
                    int keiPerBuur = keienInKnoop.get(knoop1)/aanliggendeKnopenLijst.get(knoop1).size();
                    
                    // tel keien op bij de buren
                    for(int i = 0; i<aanliggendeKnopenLijst.get(knoop1).size();i++) {
                        keienInKnoopTemp.set(aanliggendeKnopenLijst.get(knoop1).get(i), keienInKnoopTemp.get(aanliggendeKnopenLijst.get(knoop1).get(i))+ keiPerBuur);
                    }                    
                    // trek keien af bij de oorspronkelijke
                    keienInKnoopTemp.set(knoop1, keienInKnoopTemp.get(knoop1)-keiPerBuur*aanliggendeKnopenLijst.get(knoop1).size());
                }
                keienInKnoop = new ArrayList<>(keienInKnoopTemp);
            }
            System.out.println(vorigeAntwoorden.size() - vorigeAntwoorden.indexOf(keienInKnoop));
        }
    }
    
    /**
     * methode die alle aanliggende knopen uit de adjacencymatrix haalt
     * 
     * @param keienInKnoop
     * @param knoop de knoop waarrond gezocht wordt
     * @return lijst met aanliggende knopen
     */
    public List<Integer> zoekAanliggendeKnopen(int keienInKnoop, int knoop)    {
        List<Integer> list = new ArrayList<>(); 
        for (int j = 0; j < keienInKnoop; j++) {
            if(adjacencyMatrix[j][knoop])   {
                list.add(j);
            }
        }
        return list;
    }
    
}
