
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.Entry.comparingByValue;
import java.util.Scanner;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;

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
        Main m = new Main();
        m.init();
    }
    
    List<Doos> dozen;
    
    int laagstePrijs = 0;
    
    /**
     * methode om de eerste waardes uit te lezen en de data voor elk geval te resetten
     */
    public void init () {
        Scanner scanner = new Scanner(System.in);
               
        int aantalTestgevallen = Integer.parseInt(scanner.next());
          
        for (int i = 0; i < aantalTestgevallen; i++) {
            int besteldeCupcakes = Integer.parseInt(scanner.next());
            
            int aantalSoortenDozen = Integer.parseInt(scanner.next());
            
            dozen = new LinkedList<>();
            int maat;
            int kost;
            int grootsteDoos = 0;
            // maak een map met elke letter en de bijhorende code
            for (int j = 0; j < aantalSoortenDozen; j++) {
                maat = Integer.parseInt(scanner.next());
                if(maat > grootsteDoos) {
                    grootsteDoos = grootsteDoos;
                }
                kost = Integer.parseInt(scanner.next());
                dozen.add(new Doos (maat, kost, (double)kost/(double)maat));
            }
            // sorteer de dozen op grootte
            Collections.sort(dozen);
            
            laagstePrijs = 0;
            berekenPrijs(besteldeCupcakes, 0, 0);
            
            // print de oplossing of print onmogelijk als er geen is gevonden
            if( laagstePrijs != 0) {
                System.out.println(i + 1 + " " +laagstePrijs);
            } else {
                System.out.println(i +1 + " ONMOGELIJK");
            }
        }
    }
        
    /**
     * recursieve methode, test alle mogelijke combinaties met steeds dezelfde of kleinere dozen
     * 
     * @param teVerpakkenCupcakes
     * @param huidigePrijs 
     * @param vorigeIndex voorkomt dat grotere dozen opnieuw getest worden
     * @return 
     */
    private boolean berekenPrijs(int teVerpakkenCupcakes, int huidigePrijs, int vorigeIndex) {
        if(teVerpakkenCupcakes <= 0) {
            if(laagstePrijs ==0 || huidigePrijs < laagstePrijs ) {
                laagstePrijs = huidigePrijs;
            }
            return true;
        } else {
            for (int i = vorigeIndex; i < dozen.size(); i++) {
                if(huidigePrijs < laagstePrijs || laagstePrijs == 0) {
                    Doos doos = dozen.get(i);
                    berekenPrijs(teVerpakkenCupcakes-doos.getKost(), huidigePrijs + doos.getMaat(), i);
                } 
            }

        }
        return false;
    }
    
    static class Doos implements Comparable<Object>{
        int kost;
        int maat;
        double prijsPerPlaats;

        public Doos(int kost, int maat, double prijsPerPlaats) {
            this.kost = kost;
            this.maat = maat;
            this.prijsPerPlaats = prijsPerPlaats;
        }

        public int getKost() {
            return kost;
        }

        public int getMaat() {
            return maat;
        }

        public double getPrijsPerPlaats() {
            return prijsPerPlaats;
        }

        @Override
        public int compareTo(Object o) {
            return Comparator.comparing(Doos::getMaat).reversed()
                .compare(this, (Doos)o);
        }
    }
}
