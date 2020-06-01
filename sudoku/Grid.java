package sudoku;

import java.io.*;

public class Grid{
    private int[][] m_board;

    //Crea una griglia con i valori presenti nel file
    public Grid(File file) {
        if(file == null)
            throw new NullPointerException();

        m_board = new int[9][9];
        FileReader fr = null;
        String line;
        int i;

        try {
            fr = new FileReader(file.getPath());
            BufferedReader bf = new BufferedReader(fr);
            try {
                i = 0;
                while ((line = bf.readLine()) != null) {
                    String[] numeros = line.split(" "); // get the numbers from the line
                    for (int j = 0; j < numeros.length; j++) {
                        m_board[i][j] = Integer.valueOf(numeros[j]); // inserts the numbers into the board
                    }
                    i++;
                }
            } catch (IOException e1) {
                System.out.println("Error reading file:" + file.getName());
            }
        } catch (FileNotFoundException e2) {
            System.out.println("Error opening file: " + file.getName());
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e3) {
                System.out.println("Error closing file: " + file.getName());
            }
        }
    }

    //Utilizzo la griglia passata come parametro per inizializzare questa istanza
    public Grid(Grid original) {
        m_board = new int[9][9];

        //Copy board
        for (int i = 0; i < 9; i++) {
            System.arraycopy(original.m_board[i], 0, m_board[i], 0, 9);
        }
    }

    //Imposto il valore in una determinata cella
    public void setCell(Move m) {
        m_board[m.j.x][m.j.y] = m.num;
    }

    //Restituisco il valore della cella richiesta
    public int getNum(int row, int col){
            return m_board[row][col];
    }

    public void printGrid(){

        for(int i=0; i<9; i++){
            if(i%3 == 0)
                System.out.println("_______________________|");
            for(int j=0; j<9; j++){
                if(j%3 == 0 && j>0)
                    System.out.print(" | ");
                System.out.print(m_board[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("-----------------------|");
    }
}