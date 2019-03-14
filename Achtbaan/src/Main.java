
import java.util.ArrayList;
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
        Main m = new Main();
        m.initialiseer();
        
    }
    
    static void initialiseer() {
        Scanner scanner = new Scanner(System.in);
               
        int aantalTesten = Integer.parseInt(scanner.next());
        

        
        for (int test = 0; test < aantalTesten; test++) {
            ArrayList<ArrayList<Character>> layout = new ArrayList<ArrayList<Character>>();
            ArrayList<Character> init = new ArrayList<Character>();
            layout.add(init);

            int huidigeHoogte = 0;
            int orientatie = 1;
            int rij = 0;
        
            int segmenten = Integer.parseInt(scanner.next());
            String route = scanner.next();
            
            for (int j = 0; j < segmenten; j++) {
                if(rij < 0) {
                    for(int i = layout.size()-1; i>=0 ; i--) {
                        layout.get(i).add(0, '.');
                    }
                    rij++;
                }
                if(layout.get(huidigeHoogte).size() <= rij) {
                    int addedDots = rij - layout.get(huidigeHoogte).size();
                    for (int i = 0; i <= addedDots; i++) {
                        layout.get(huidigeHoogte).add('.');
                    }
                }
                switch (route.charAt(j)) {
                    case 'S':
                        layout.get(huidigeHoogte).set(rij, '=');
                        rij++;
                        break;
                    case 'V':
                        
                        if(orientatie == 1) {
                            if(layout.get(huidigeHoogte).size() <= rij) {
                                int addedDots = rij - layout.get(huidigeHoogte).size();
                                for (int i = 0; i <= addedDots; i++) {
                                    layout.get(huidigeHoogte).add('.');
                                }
                            }
                            
                            layout.get(huidigeHoogte).set(rij, '_');
                            rij++;
                        } else if (orientatie == 3) {
                            if(layout.get(huidigeHoogte).size() <= rij) {
                                int addedDots = rij - layout.get(huidigeHoogte).size();
                                for (int i = 0; i <= addedDots; i++) {
                                    layout.get(huidigeHoogte).add('.');
                                }
                            }
                            layout.get(huidigeHoogte).set(rij, '_');
                            rij--;
                        }
                        break;
                    case 'U':
                        if(huidigeHoogte==layout.size()-1) {
                            layout.add(new ArrayList<Character>());
                        } 
                        if(orientatie == 1) {
                            if(layout.get(huidigeHoogte).size() <= rij) {
                                int addedDots = rij - layout.get(huidigeHoogte).size();
                                for (int i = 0; i <= addedDots; i++) {
                                    layout.get(huidigeHoogte).add('.');
                                }
                            }
                            layout.get(huidigeHoogte).set(rij, '/'); 
                            huidigeHoogte++;
                            rij++;
                        } else if (orientatie == 3) {
                            if(layout.get(huidigeHoogte).size() <= rij) {
                                int addedDots = rij - layout.get(huidigeHoogte).size();
                                for (int i = 0; i <= addedDots; i++) {
                                    layout.get(huidigeHoogte).add('.');
                                }
                            }
                            layout.get(huidigeHoogte).set(rij, '\\'); 
                            huidigeHoogte++;
                            rij--;
                        } else {
                            layout.get(huidigeHoogte).set(rij, '#'); 
                            if(orientatie == 2 ) {
                                huidigeHoogte++;
                            } else if (orientatie == 4) {
                                huidigeHoogte++;
                            }
                        }
                        
                        break;
                    case 'D':
                        if(huidigeHoogte==0) {
                            layout.add(0, new ArrayList<Character>());
                        } else {
                            huidigeHoogte--;
                        }
                        if(orientatie == 1) {
                            int addedDots = rij - layout.get(huidigeHoogte).size();
                            for (int i = 0; i <= addedDots; i++) {
                                layout.get(huidigeHoogte).add('.');
                            }
                            layout.get(huidigeHoogte).set(rij, '\\'); 
                            rij++;
                        } else if (orientatie == 3) {
                            int addedDots = rij - layout.get(huidigeHoogte).size();
                            for (int i = 0; i <= addedDots; i++) {
                                layout.get(huidigeHoogte).add('.');
                            }
                            layout.get(huidigeHoogte).set(rij, '/'); 
                            rij--;
                        
                        } else {
                            layout.get(huidigeHoogte).set(rij, '#'); 
                        }
                        break;
                    case 'L':
                        orientatie++;
                        
                        if(orientatie == 5 ){
                            orientatie -=4;
                        } 
                        if(orientatie  == 2 || orientatie  == 4) {
                            if(rij > layout.get(huidigeHoogte).size()-1) {
                                layout.get(huidigeHoogte).add('_');
                            } else {
                                layout.get(huidigeHoogte).set(rij, '_');
                            }
                        }
                        if(orientatie  == 3) {
                            rij--;
                        }
                        
                        if(orientatie  == 1) {
                            rij++;
                        }
                        break;
                    case 'R':
                        orientatie--;
                        if(orientatie == 5 ){
                            orientatie -=4;
                        } 
                        if(orientatie  == 2 || orientatie  == 4) {
                            if(rij > layout.get(huidigeHoogte).size()-1) {
                                layout.get(huidigeHoogte).add('_');
                            } else {
                                layout.get(huidigeHoogte).set(rij, '_');
                            }
                        }
                        if(orientatie  == 3) {
                            rij++;
                        }
                        
                        if(orientatie  == 1) {
                            rij--;
                        }
                        break;
                }
                System.out.print("");
            }
            int maxLengteRij = 0;
            for(int i = layout.size()-1; i>=0 ; i--) {
                if(layout.get(i).size() > maxLengteRij) maxLengteRij = layout.get(i).size();
            }
            for(int i = layout.size()-1; i>=0 ; i--) {
                if(layout.get(i).size() < maxLengteRij)
                    for (int j = layout.get(i).size(); j < maxLengteRij; j++) {
                        layout.get(i).add('.');
                    }
            }
            write(test, layout);
        }
    }
    static ArrayList<ArrayList<Character>> addDots(int rij, int huidigeHoogte, ArrayList<ArrayList<Character>> layout) {
        int addedDots = rij - layout.get(huidigeHoogte).size();
        for (int i = 0; i <= addedDots; i++) {
            layout.get(huidigeHoogte).add('.');
        }
        return layout;
    }
    
    static void write(int test, ArrayList<ArrayList<Character>> layout) {

        for(int i = layout.size()-1; i>=0 ; i--) {
            System.out.print(test+1 +" ");
            for(int j = 0; j < layout.get(i).size(); j++) {
                System.out.print(layout.get(i).get(j));
            }
            System.out.println();
        }
    }
    
    
}
