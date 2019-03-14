
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    int graph[][];
    int maxWinst = 0;
    public void init() {
        Scanner scanner = new Scanner(System.in);
               
        int aantalTestgevallen = Integer.parseInt(scanner.next());
        
        for (int testGeval = 0; testGeval < aantalTestgevallen; testGeval++) {
            int aantalNodes = Integer.parseInt(scanner.next());
            V = aantalNodes;
            int aantalBogen = Integer.parseInt(scanner.next());
            graph = new int[aantalNodes][aantalNodes];
            
            for (int verbindingen = 0; verbindingen < aantalBogen; verbindingen++) {
                int knoop1 = Integer.parseInt(scanner.next());
                int knoop2 = Integer.parseInt(scanner.next());
                int gewicht = Integer.parseInt(scanner.next());
                
                graph[knoop1][knoop2] = gewicht;
                graph[knoop2][knoop1] = gewicht;
                
            }
            int aantalBestellingen = Integer.parseInt(scanner.next());
            
            bestelling[] bestellingen = new bestelling[aantalBestellingen];
            
            for (int bestelling = 0; bestelling < aantalBestellingen; bestelling++) {
                int oorsprong = Integer.parseInt(scanner.next());
                int destinatie = Integer.parseInt(scanner.next());
                int tijdstip = Integer.parseInt(scanner.next());
                int winst = Integer.parseInt(scanner.next());
                
                bestellingen[bestelling] = new bestelling(oorsprong, destinatie, tijdstip, winst);
            }
            
            Arrays.sort(bestellingen);
            maxWinst = 0;
            dijkstra(graph, 0, 0, bestellingen, 0, 0);
            System.out.println(testGeval+1+ " " + maxWinst);
            
        }
    }
    
    
    // A utility function to find the vertex with minimum distance value, 
    // from the set of vertices not yet included in shortest path tree 
    int V; 
    int minDistance(int dist[], Boolean sptSet[]) 
    { 
        // Initialize min value 
        int min = 9999, min_index=-1; 
  
        for (int v = 0; v < V; v++) 
            if (sptSet[v] == false && dist[v] <= min) 
            { 
                min = dist[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    } 

  
    // Funtion that implements Dijkstra's single source shortest path 
    // algorithm for a graph represented using adjacency matrix 
    // representation 
    void dijkstra(int graph[][],  int huidigePositie, int huidigeBestelling, bestelling[] bestellingen, int winst, int tijdstip) 
    {             
        int dist[] = new int[V]; // The output array. dist[i] will hold 
        // the shortest distance from src to i 
        int tijdNaarStart ;

        
        if(winst> maxWinst) {
            maxWinst = winst;
        }  
        
        for (int best = huidigeBestelling; best < bestellingen.length; best++) {
            tijdNaarStart=0;
            if(huidigePositie != bestellingen[best].oorsprong) {

                // sptSet[i] will true if vertex i is included in shortest 
                // path tree or shortest distance from src to i is finalized 
                Boolean sptSet[] = new Boolean[V]; 

                // Initialize all distances as INFINITE and stpSet[] as false 
                for (int i = 0; i < V; i++) 
                { 
                    dist[i] = 9999; 
                    sptSet[i] = false; 
                } 

                // Distance of source vertex from itself is always 0 
                dist[huidigePositie] = 0; 

                // Find shortest path for all vertices 
                for (int count = 0; count < V-1; count++) 
                { 
                    // Pick the minimum distance vertex from the set of vertices 
                    // not yet processed. u is always equal to src in first 
                    // iteration. 
                    int u = minDistance(dist, sptSet); 

                    // Mark the picked vertex as processed 
                    sptSet[u] = true; 

                    // Update dist value of the adjacent vertices of the 
                    // picked vertex. 
                    for (int v = 0; v < V; v++) 
                        // Update dist[v] only if is not in sptSet, there is an 
                        // edge from u to v, and total weight of path from src to 
                        // v through u is smaller than current value of dist[v] 
                        if (!sptSet[v] && graph[u][v]!=0 && 
                                dist[u] != 9999 && 
                                dist[u]+graph[u][v] < dist[v]) 
                            dist[v] = dist[u] + graph[u][v]; 
                } 
                //huidigePositie = bestellingen[best].oorsprong;
                tijdNaarStart = dist[bestellingen[best].oorsprong];
            } 

            // sptSet[i] will true if vertex i is included in shortest 
            // path tree or shortest distance from src to i is finalized 
            Boolean sptSet[] = new Boolean[V]; 

            // Initialize all distances as INFINITE and stpSet[] as false 
            for (int i = 0; i < V; i++) 
            { 
                dist[i] = 9999; 
                sptSet[i] = false; 
            } 

            // Distance of source vertex from itself is always 0 
            dist[bestellingen[best].oorsprong] = 0; 

            // Find shortest path for all vertices 
            for (int count = 0; count < V-1; count++) 
            { 
                // Pick the minimum distance vertex from the set of vertices 
                // not yet processed. u is always equal to src in first 
                // iteration. 
                int u = minDistance(dist, sptSet); 

                // Mark the picked vertex as processed 
                sptSet[u] = true; 

                // Update dist value of the adjacent vertices of the 
                // picked vertex. 
                for (int v = 0; v < V; v++) 

                    // Update dist[v] only if is not in sptSet, there is an 
                    // edge from u to v, and total weight of path from src to 
                    // v through u is smaller than current value of dist[v] 
                    if (!sptSet[v] && graph[u][v]!=0 && 
                            dist[u] != 9999 && 
                            dist[u]+graph[u][v] < dist[v]) 
                        dist[v] = dist[u] + graph[u][v]; 
            } 

            if(tijdstip+tijdNaarStart+dist[bestellingen[best].destinatie] <= bestellingen[best].tijdstip 
                    && tijdNaarStart != 9999 
                    && dist[bestellingen[best].destinatie] != 9999) {
                dijkstra(graph, 
                        bestellingen[best].destinatie, 
                        best+1,
                        bestellingen,
                        winst+bestellingen[best].winst,
                        bestellingen[best].tijdstip);
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
                    .compare(this, (bestelling)o);
        }
        
        
        
    }
}
