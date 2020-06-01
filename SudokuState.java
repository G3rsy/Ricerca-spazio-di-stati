import engine.State;
import sudoku.Move;
import sudoku.Sudoku;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class SudokuState implements State {
    Sudoku G;

    //Max level = 81
    int lvl = 0;

    //Max value = 9
    int value;


    boolean expanded = false;

    //Costruttore della prima istanza
    public SudokuState(File in){
        if(in == null)
            throw new NullPointerException();

        G = new Sudoku(in);

        if(G.value() == 0)
            //solution case
            value = 999;
        else
            value = (lvl * 10) + (10 - G.value());
    }

    //Costruttore di uno stato figlio
    public SudokuState(Sudoku in, int l){
        G = in;
        lvl = l ;

        if(G.value() == 0)
            //solution case
            value = 999;
        else
            value = (lvl*10)+ (10-G.value());
    }

    @Override
    public int value() {
        return this.value;
    }

    public void expanded(){
        expanded = true;
    }

    public boolean isExpanded(){
        return expanded;
    }

    @Override
    public Iterator<State> iterator() {
        ArrayList<State> VS = new ArrayList<>();

        //per ogni mossa creo un nuovo stato
        for(Move next : G.getMoves()){
            Sudoku son = new Sudoku(G, next);
            VS.add(new SudokuState(son, this.lvl + 1));
        }

        return VS.iterator();
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        for (int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                s.append(G.getNum(i, j));
                if(j != 8)
                    s.append(" ");
            }
            s.append('\n');
        }
        return s.toString();
    }

    public void printGrid(){
        G.printGrid();
    }
}
