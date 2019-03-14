
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

    int aantalVerbindingen; 
    int[][] graaf;
    int INF = 9999;
    
    
    public static void main(String[] args) 
    { 
        Main m = new Main();
        m.init();
        
            
        
    }
    public void init() {
        Scanner scanner = new Scanner(System.in);
        int aantalTestgevallen = Integer.parseInt(scanner.next());  
        for (int test = 0; test < aantalTestgevallen; test++) {
            
            int aantalKnopen = Integer.parseInt(scanner.next());
            int startplaats = Integer.parseInt(scanner.next());
            int terugkeerplaats = Integer.parseInt(scanner.next());
            int aantalPlaatsenAlice = Integer.parseInt(scanner.next());
            Bestemming[] plaatsenAlice = new Bestemming[aantalPlaatsenAlice];
            for (int plaats = 0; plaats < aantalPlaatsenAlice; plaats++) {
                plaatsenAlice[plaats] = new Bestemming(-1, -1);
                plaatsenAlice[plaats].locatie = Integer.parseInt(scanner.next());
            }
            int aantalPlaatsenBob = Integer.parseInt(scanner.next());
            Bestemming[] plaatsenBob = new Bestemming[aantalPlaatsenBob];
            for (int plaats = 0; plaats < aantalPlaatsenBob; plaats++) {
                plaatsenBob[plaats] = new Bestemming(-1, -1);
                plaatsenBob[plaats].locatie = Integer.parseInt(scanner.next());
            }

            int E = Integer.parseInt(scanner.next());
            graaf = new int[aantalKnopen][aantalKnopen];
                // maak alle afstanden gelijk aan oneindig
            for (int i = 0; i < aantalKnopen; i++) {
                for (int j = 0; j < aantalKnopen; j++) {
                    graaf[i][j] = 9999;
                }
            }

            for (int verbindingen = 0; verbindingen < E; verbindingen++) {
                int knoop1 = Integer.parseInt(scanner.next())-1;
                int knoop2 = Integer.parseInt(scanner.next())-1;

                graaf[knoop1][knoop2] = 1;
                graaf[knoop2][knoop1] = 1;
            }
            int afstanden[][] = new int[aantalKnopen][aantalKnopen]; 
            afstanden = berekenAfstanden(afstanden, aantalKnopen);
            int aantalDagenSamen = start(plaatsenAlice, plaatsenBob, afstanden, startplaats);
            if(aantalDagenSamen == 0 ) {
                System.out.println(test+1 + " onmogelijk");
            } else {
                System.out.println(test+1 + " " + aantalDagenSamen);
            }
        }
    }   
    public int[][] berekenAfstanden(int afstanden[][], int aantalKnopen) {
        int i, j, k; 
        
        for (i = 0; i < aantalKnopen; i++) 
            for (j = 0; j < aantalKnopen; j++) 
                afstanden[i][j] = graaf[i][j]; 
        
        for (k = 0; k < aantalKnopen; k++) 
        { 
            for (i = 0; i < aantalKnopen; i++) 
            { 
                for (j = 0; j < aantalKnopen; j++) 
                { 
                    if (afstanden[i][k] + afstanden[k][j] < afstanden[i][j]) 
                        afstanden[i][j] = afstanden[i][k] + afstanden[k][j]; 
                } 
            } 
        }
        return afstanden;
    }

    private int start(Bestemming[] plaatsenAlice, Bestemming[] plaatsenBob, int afstanden[][], int startplaats) {
        int minimumLengteAlice = 0;
        plaatsenAlice[0].dag = afstanden[startplaats][plaatsenAlice[0].locatie];
        for (int i = 0; i < plaatsenAlice.length-1; i++) {
            plaatsenAlice[i+1].dag = afstanden[plaatsenAlice[i].locatie][plaatsenAlice[i+1].locatie]+plaatsenAlice[i].dag;
        }
        minimumLengteAlice = plaatsenAlice[plaatsenAlice.length-1].dag;
        
        int minimumLengteBob = 0;
        plaatsenBob[0].dag = afstanden[startplaats][plaatsenBob[0].locatie];
        for (int i = 0; i < plaatsenBob.length-1; i++) {
            plaatsenBob[i+1].dag = afstanden[plaatsenBob[i].locatie][plaatsenBob[i+1].locatie]+plaatsenBob[i].dag;
        }
        minimumLengteBob = plaatsenAlice[plaatsenBob.length-1].dag;
        
        int aantalDagenSamen = 0;
        for (int i = 0; i < plaatsenAlice.length; i++) {
            for (int j = 0; j < plaatsenBob.length; j++) {
                if(plaatsenAlice[i].locatie == plaatsenBob[j].locatie) {
                    aantalDagenSamen++;
                }
            }
        }
        return aantalDagenSamen;
    }
    
    
    static class Bestemming {
        int locatie;
        int dag;

        public Bestemming(int locatie, int dag) {
            this.locatie = locatie;
            this.dag = dag;
        }

        public int getLocatie() {
            return locatie;
        }

        public void setLocatie(int locatie) {
            this.locatie = locatie;
        }

        public int getDag() {
            return dag;
        }

        public void setDag(int dag) {
            this.dag = dag;
        }
        
        

    }
}
