package A1;

import java.util.*;

public class WordSearch{
    private char[][] plane;
    private boolean[][] sol;
    private String[] words;

    //playgame method
    public static void playgame(){
        boolean generated = false;
        Scanner console = new Scanner(System.in);
        String choice;
        WordSearch search = new WordSearch();
        do{
            printIntro();
            choice = console.next();
            if(choice.equals("g")){
                System.out.println("Enter words line by line until you are finished at which point type a single \"q\"");
                String tok = console.next();
                ArrayList<String> wordsAR = new ArrayList<String>();
                while(!tok.equals("q")){
                    wordsAR.add(tok);
                    tok = console.next();
                }
                String[] words = new String[wordsAR.size()];
                wordsAR.toArray(words);
                search.generate(words);
                generated = true;
            }else if(choice.equals("p")){
                if(generated){
                    print(search);
                }else{
                    System.out.println("You need to create a word search first!");
                    System.out.println("");
                }
            }else if(choice.equals("s")){
                if(generated){
                    showSolution(search);
                }else{
                    System.out.println("You need to create a word search first!");
                    System.out.println("");
                }
            }

        }while(!choice.equals("q"));
    
    }

    //prints introduction of program
    public static void printIntro(){
        System.out.println("Welcome to my word search generator!");
        System.out.println("This programs will allow you to generate your own word search puzzle");
        System.out.println("Please select and option:");
        System.out.println("Generate a new word search (g)");
        System.out.println("Print out your word search (p)");
        System.out.println("Show the solution to your word search(s)");
        System.out.println("Quit the program (q)");
    }

    //prints wordsearch
    public static void print(WordSearch ws){
        System.out.println(ws);
    }

    //prints solution to wordsearch
    public static void showSolution(WordSearch ws){
        System.out.println(ws.toSolution());
    }

    //generates a new wordsearch from array
    public void generate(String[] w){
        for(int i = 0 ; i < w.length ; i++){
            w[i] = w[i].toLowerCase();
        }
        this.words = w;
        char[][] wordChars = setupPlane();
        for(int i = 0 ; i < wordChars.length ; i++){
            placeWord(wordChars, i);
        }
    fillPlane();
    }

    //toString method
    public String toString(){
        String result = "";
        for(int i = 0 ; i < plane.length ; i++){
            for(int x = 0 ; x < plane[i].length ; x++){
                result += " " + plane[i][x] + " ";
            }
            result += "\r\n";
        }
        return result;
    }

    //returns string of wordsearch solution
    public String toSolution(){
        String result = "";
        for(int i = 0 ; i < plane.length ; i++){
            for(int x = 0 ; x < plane[i].length ; x++){
                if(sol[i][x]){
                    result += " " + plane[i][x] + " ";
                }else{
                    result += " X ";
                }
            }
            result += "\r\n";
        }
        return result;
    }
    //place word in word search plane, then determines direction.
    private void placeWord(char[][] wordChars, int iter){
        Random rand = new Random();
        int direction = rand.nextInt(3);
        int[] pos = {0,0};
        if(direction == 0){
            horizontal(wordChars, iter, rand, pos);
        }else if(direction == 1){
            vertical(wordChars, iter, rand, pos);
        }else if(direction == 2){
            diagonal(wordChars, iter, rand, pos);
        }
    }

    //horizontal method
    private void horizontal(char[][] wordChars, int iter, Random rand, int[] pos){
        boolean placed = false;
            int attempts = 0;
            while(!placed && attempts < 100){
                pos[0] = rand.nextInt((plane.length-1) - wordChars[iter].length);
                pos[1] = rand.nextInt((plane.length-1) - wordChars[iter].length);
                placed = true;
                for(int u = 0 ; u < wordChars[iter].length ; u++){
                    if(plane[pos[0] + u][pos[1]] != '\u0000' && plane[pos[0] + u][pos[1]] != wordChars[iter][u]){
                        placed = false;
                        break;
                    }
                }
                attempts++;
            }
            if(placed){
                for(int x = 0 ; x < wordChars[iter].length ; x++){
                    plane[pos[0]][pos[1]] = wordChars[iter][x];
                    sol[pos[0]][pos[1]] = true;
                    pos[0]++;
                }
            }

    }

    //vertical method
    private void vertical(char[][] wordChars, int iter, Random rand, int[] pos){
        boolean placed = false;
        int attempts = 0;
        while(!placed && attempts < 100){
            pos[0] = rand.nextInt((plane.length-1) - wordChars[iter].length);
            pos[1] = rand.nextInt((plane.length-1) - wordChars[iter].length);
            placed = true;
            for(int u = 0 ; u < wordChars[iter].length ; u++){
                if(plane[pos[0]][pos[1] + u] != '\u0000' && plane[pos[0]][pos[1] + u] != wordChars[iter][u]){
                    placed = false;
                    break;
                }
            }
        attempts++;
        }
        if(placed){
            for(int x = 0 ; x < wordChars[iter].length ; x++){
                plane[pos[0]][pos[1]] = wordChars[iter][x];
                sol[pos[0]][pos[1]] = true;
                pos[1]++;
            }
        }
    }

    //diagonal method
    private void diagonal(char[][] wordChars, int iter, Random rand, int[] pos){
        boolean placed = false;
        int attempts = 0;
        while(!placed && attempts < 100){
            pos[0] = rand.nextInt((plane.length-1) - wordChars[iter].length);
            pos[1] = rand.nextInt((plane.length-1) - wordChars[iter].length);
            placed = true;
            for(int u = 0 ; u < wordChars[iter].length ; u++){
                if(plane[pos[0] + u][pos[1] + u] != '\u0000' && plane[pos[0] + u][pos[1] + u] != wordChars[iter][u]){
                    placed = false;
                    break;
                }
            }
            attempts++;
        }
        if(placed){
            for(int x = 0 ; x < wordChars[iter].length ; x++){
                plane[pos[0]][pos[1]] = wordChars[iter][x];
                sol[pos[0]][pos[1]] = true;
                pos[1]++;
                pos[0]++;
            }
        }
    }
    
    //breaks up string array and adjusts size of plane based on how many words
    private char[][] setupPlane(){
        char[][] wordChars = new char[words.length][];
        int longest = 8;
        for(int i = 0 ; i < words.length ; i++){
            wordChars[i] = words[i].toCharArray();
            if(wordChars[i].length > longest){
                longest = wordChars[i].length;
            }
        }
        if(words.length > longest){
            longest = words.length;
        }
        this.plane = new char[longest + 4][longest + 4];
        this.sol = new boolean[longest + 4][longest + 4];
        return wordChars;
    }

    //fills extra places on plane with random words
    private void fillPlane(){
        for(int i = 0 ; i < plane.length ; i++){
            for(int x = 0 ; x < plane[i].length ; x++){
                Random rand = new Random();
                if(plane[i][x] == '\u0000'){
                    plane[i][x] = (char)(rand.nextInt(26)+97);
                }
            }
        }
    }
}