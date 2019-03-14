import java.util.Scanner;

/**
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */

class Main 
{ 
    int aantalKnopen;
    int aantalVerbindingen; 
    Verbinding[] verbindingen; 
    
    // Driver method to test above function 
    public static void main(String[] args) 
    { 
        Scanner scanner = new Scanner(System.in);
               
        int aantalTestgevallen = Integer.parseInt(scanner.next());
        
        for (int test = 0; test < aantalTestgevallen; test++) {
            int V = Integer.parseInt(scanner.next());
            int E = Integer.parseInt(scanner.next());

            Main graph = new Main(V, E); 
            
            for (int verbindingen = 0; verbindingen < E; verbindingen++) {
                
                int knoop1 = Integer.parseInt(scanner.next());
                int knoop2 = Integer.parseInt(scanner.next());
                int gewicht = Integer.parseInt(scanner.next());
                
                graph.verbindingen[verbindingen].startpunt = knoop1; 
                graph.verbindingen[verbindingen].eindpunt = knoop2; 
                graph.verbindingen[verbindingen].gewichtVerbinding = gewicht; 
            }
            int resultaat = graph.BelmanFord(graph, 0);
            switch (resultaat) {
                case Integer.MAX_VALUE:
                    System.out.println(test + 1 +" plus oneindig" );
                    break;
                case Integer.MIN_VALUE:
                    System.out.println(test + 1 +" min oneindig" );
                    break;
                default:
                    System.out.println(test + 1 +" " + resultaat);
                    break;
            }
        }
    } 
  
    int BelmanFord(Main graaf,int oorsprong) 
    { 
        int aantalKnopen = graaf.aantalKnopen;
        int aantalVerbindingen = graaf.aantalVerbindingen; 
        int dist[] = new int[aantalKnopen]; 
  
        // Zet alle waardes op oneindig behalve die van de oorsprong
        for (int i=0; i<aantalKnopen; ++i) 
            dist[i] = Integer.MAX_VALUE; 
        dist[oorsprong] = 0; 
  
        // de eerste knoop kan maximaal evenveel knopen verwijderd zijn van 
        // de laatste als het totaal aantal knopen in de graaf,
        // dit aantal is gelijk aan de relaxatie
        for (int i=1; i<aantalKnopen; ++i) 
        { 
            for (int j=0; j< aantalVerbindingen; ++j) 
            { 
                int startpunt = graaf.verbindingen[j].startpunt; 
                int eindpunt = graaf.verbindingen[j].eindpunt; 
                int gewicht = graaf.verbindingen[j].gewichtVerbinding; 
                // er wordt steeds gekeken of de score van A naar B lager is dan via de voorgaande weg
                if (dist[startpunt-1]!=Integer.MAX_VALUE && 
                    dist[startpunt-1]+gewicht<dist[eindpunt-1]) 
                    dist[eindpunt-1]=dist[startpunt-1]+gewicht; 
            } 
        } 

        // for-loop die alle verbindingen overloopt en kijkt of het gewicht 
        // tussen 2 knopen na afloop van het berekenen van het resultaat
        // lager is dan het oorspronkelijke gewicht. indien ja, negatieve loop
        for (int j=0; j<aantalVerbindingen; ++j) 
        { 
            int startpunt = graaf.verbindingen[j].startpunt; 
            int eindpunt = graaf.verbindingen[j].eindpunt; 
            int gewicht = graaf.verbindingen[j].gewichtVerbinding; 
            if (dist[startpunt-1] != Integer.MAX_VALUE && 
                dist[startpunt-1]+gewicht < dist[eindpunt-1]) 
              return Integer.MIN_VALUE;
        } 
        return dist[dist.length-1];
    } 

    /**
     * klasse die een verbinding tussen 2 knopen maakt
     */    
    class Verbinding { 
        int startpunt;
        int eindpunt;
        int gewichtVerbinding; 
        
        Verbinding() { 
            startpunt = 0;
            eindpunt = 0;
            gewichtVerbinding = 0; 
        } 
    };
    
   /**
    * constructor die een graaf voorsteld,
    * noemt Main omdat de klasse Main moet noemen
    * @param knopen het aantal knopen van de graaf
    * @param verbindingenAantal het totaal aantal verbindingen binnen de graaf
    */
    Main(int knopen, int verbindingenAantal) 
    { 
        aantalKnopen = knopen; 
        aantalVerbindingen = verbindingenAantal; 
        verbindingen = new Verbinding[verbindingenAantal]; 
        for (int i=0; i<verbindingenAantal; ++i) 
            verbindingen[i] = new Verbinding(); 
    }
} 



