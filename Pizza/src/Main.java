
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.init();
    }
    int[][] graaf;
    int maxWinst = 0;
    int INF = 9999;
    int aantalKnooppunten; 
    
    public void init() {
        Scanner scanner = new Scanner(System.in);
               
        int aantalTestgevallen = Integer.parseInt(scanner.next());
        
        for (int testGeval = 0; testGeval < aantalTestgevallen; testGeval++) {
            aantalKnooppunten = Integer.parseInt(scanner.next());
            
            int aantalBogen = Integer.parseInt(scanner.next());
            graaf = new int[aantalKnooppunten][aantalKnooppunten];
            // maak alle afstanden gelijk aan oneindig
            for (int i = 0; i < aantalKnooppunten; i++) {
                for (int j = 0; j < aantalKnooppunten; j++) {
                    graaf[i][j] = INF;
                }
            }
            
            // vul de graaf
            for (int verbindingen = 0; verbindingen < aantalBogen; verbindingen++) {
                int knoop1 = Integer.parseInt(scanner.next());
                int knoop2 = Integer.parseInt(scanner.next());
                int gewicht = Integer.parseInt(scanner.next());
                
                graaf[knoop1][knoop2] = gewicht;
                graaf[knoop2][knoop1] = gewicht;
            }
            int aantalBestellingen = Integer.parseInt(scanner.next());
            
            bestelling[] bestellingen = new bestelling[aantalBestellingen];
            
            // vul de bestellingen
            for (int bestelling = 0; bestelling < aantalBestellingen; bestelling++) {
                int oorsprong = Integer.parseInt(scanner.next());
                int destinatie = Integer.parseInt(scanner.next());
                int tijdstip = Integer.parseInt(scanner.next());
                int winst = Integer.parseInt(scanner.next());
                
                bestellingen[bestelling] = new bestelling(oorsprong, destinatie, tijdstip, winst);
            }
            Arrays.sort(bestellingen);
            maxWinst = 0;
            
            // start
            floydWarshall(graaf, bestellingen);
            System.out.println(testGeval+1+ " " + maxWinst);
            
        }
    }
    

    
    /**
     * bereken de korste weg aan de hand van het Floyd Warshall algoritme
     * vul een array met alle  afstanden 
     * @param graph
     * @param bestellingen 
     */
    void floydWarshall(int graph[][], bestelling[] bestellingen) 
    { 
        int afstanden[][] = new int[aantalKnooppunten][aantalKnooppunten]; 
        int i, j, k; 
        
        for (i = 0; i < aantalKnooppunten; i++) 
            for (j = 0; j < aantalKnooppunten; j++) 
                afstanden[i][j] = graph[i][j]; 
        
        for (k = 0; k < aantalKnooppunten; k++) 
        { 
            for (i = 0; i < aantalKnooppunten; i++) 
            { 
                for (j = 0; j < aantalKnooppunten; j++) 
                { 
                    if (afstanden[i][k] + afstanden[k][j] < afstanden[i][j]) 
                        afstanden[i][j] = afstanden[i][k] + afstanden[k][j]; 
                } 
            } 
        }
        
        bereken(graph, 0, 0, bestellingen, 0, 0, afstanden);
    } 
    
    /**
     * recursieve methode, stapt altijd 1 bestelling dieper indien die bestelling is gelukt
     * 
     * @param graaf
     * @param huidigePositie
     * @param huidigeBestelling
     * @param bestellingen
     * @param winst
     * @param tijdstip
     * @param afstanden 
     */
    void bereken(int graaf[][],  int huidigePositie, int huidigeBestelling, bestelling[] bestellingen, int winst, int tijdstip, int afstanden[][]) 
    {             
        int tijdNaarStart;
        if(winst> maxWinst) {
            maxWinst = winst;
        }  
        
        // doorloop alle bestellingen, start bij de laatst gelukte
        for (int best = huidigeBestelling; best < bestellingen.length; best++) {
            tijdNaarStart=0;
            if(huidigePositie != bestellingen[best].oorsprong) {
                tijdNaarStart = afstanden[huidigePositie][bestellingen[best].oorsprong];
            } 
            // als de bestelling op tijd kan worden afgerond, voer methode opnieuw uit vanaf dat tijdstip
            if(tijdstip+tijdNaarStart+afstanden[bestellingen[best].oorsprong][bestellingen[best].destinatie] <= bestellingen[best].tijdstip) {
                bereken(graaf, 
                    bestellingen[best].destinatie, 
                    best+1,
                    bestellingen,
                    winst+bestellingen[best].winst,
                    bestellingen[best].tijdstip, 
                    afstanden);
            }
        }
    } 
    
    class bestelling implements Comparable<Object> {
        int oorsprong;
        int destinatie;
        int tijdstip;
        int winst;

        public bestelling(int oorsprong, int destinatie, int tijdstip, int winst) {
            this.oorsprong = oorsprong;
            this.destinatie = destinatie;
            this.tijdstip = tijdstip;
            this.winst = winst;
        }

        public int getOorsprong() {
            return oorsprong;
        }

        public void setOorsprong(int oorsprong) {
            this.oorsprong = oorsprong;
        }

        public int getDestinatie() {
            return destinatie;
        }

        public void setDestinatie(int destinatie) {
            this.destinatie = destinatie;
        }

        public int getTijdstip() {
            return tijdstip;
        }

        public void setTijdstip(int tijdstip) {
            this.tijdstip = tijdstip;
        }

        public int getWinst() {
            return winst;
        }

        public void setWinst(int winst) {
            this.winst = winst;
        }

        @Override
        public int compareTo(Object o) {
            return Comparator.comparing(bestelling::getTijdstip)
                    .thenComparing(bestelling::getWinst)
                    .compare(this, (bestelling)o);
        }
        
        
        
    }
}
