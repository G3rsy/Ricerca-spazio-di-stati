package sudoku;

import java.io.File;
import java.util.ArrayList;

public class Sudoku{
    private Grid G;

    //valore di default per value
    protected int val = Integer.MAX_VALUE;

    //Costruttore tramite file
    public Sudoku(File f){
        if(f == null)
            throw new NullPointerException();

        G = new Grid(f);
        val = 9;
        if(isSolution())
            val=0;
        else
            verifyCorrectness();
    }

    //Costruttore dato uno stato e una mossa, verra' applicata alla nuova istanza
    public Sudoku(Sudoku grd, Move next){
        G = new Grid(grd.G);
        G.setCell(next);
        val = next.val;

        if(isSolution())
            val = 0;
        else
            verifyCorrectness();
    }

    //Restituisce il numero di elementi che possono andare in quella cella
    public int valuer(int row, int col){
        int count = 0;

        for(int i=1; i<10; i++) {
            //controllo che il numero non sia gia' presente nella riga
            boolean foundRow = false;
            for (int j = 0; j < 9 && !foundRow; j++) {
                if (j != col)
                    if (getNum(row, j) == i)
                        foundRow = true;
            }

            //controllo che il numero non sia gia' presente nella colonna
            boolean foundCol = false;
            for (int j = 0; j < 9 && !foundCol; j++) {
                if (j != col)
                    if (getNum(j, col) == i)
                        foundCol = true;
            }

            //ora devo controllare la sezione
            int x = ((int) (row / 3)) * 3;
            int y = ((int) (col / 3)) * 3;
            boolean foundSector = false;

            //controllo che il numero non sia gia' presente nel settore
            for (int j = x; (j < x + 3) && !foundSector; j++){
                for (int k = y; (k < y + 3) && !foundSector; k++) {
                    if (!(j == row && k == col) && (getNum(j, k) == i)) foundSector = true;
                }
            }

            if(!(foundCol || foundRow || foundSector))
                count++;
        }

        return count;
    }

    //Verifica la correttezza dello stato, se non e' corretto lo valuta val = -1000
    private void verifyCorrectness(){
        boolean breakPoint = false;
        //vedere quanti numeri si possono inserire in ogni cella vuota
        for(int i=0; i<9 && !breakPoint; i++){
            for(int j=0; j<9 && !breakPoint; j++){


                int number = getNum(i,j);

                //Se la cella e' vuota, verifico che ci siamo delle mosse possibili
                //altrimenti controllo la correttezza del numero in quella cella
                if(number == 0) {
                    int p = valuer(i, j);
                    if (p == 0) {
                        //caso in cui sono in uno stato bloccato
                        //ed ho una cella vuota con 0 numeri possibili da inserire
                        val = -1000;
                        breakPoint = true;
                    }
                }else{

                    //controllo che il numero non sia gia' presente nella riga
                    boolean foundRow = false;
                    for (int x = 0; x < 9 && !foundRow; x++) {
                        //non controllo la casella
                        if (x != i)
                            if (getNum(x, j) == number)
                                foundRow = true;
                    }

                    //controllo che il numero non sia gia' presente nella colonna
                    boolean foundCol = false;
                    for (int y = 0; y < 9 && !foundCol; y++) {
                        if (y != j)
                            if (getNum(i, y) == number)
                                foundCol = true;
                    }

                    //ora devo controllare la sezione
                    int s1 = ((int) (i / 3)) * 3;
                    int s2 = ((int) (j / 3)) * 3;
                    boolean foundSector = false;

                    //controllo che il numero non sia gia' presente nel settore
                    for (int x = s1; (x < s1 + 3) && !foundSector; x++) {
                        for (int y = s2; (y < s2 + 3) && !foundSector; y++) {
                            if (!(x == i && y == j) && (getNum(x, y) == number)) foundSector = true;
                        }
                    }

                    if (foundCol || foundRow || foundSector) {
                        val=-1000;
                    }
                }
            }
        }
    }

    //Restituisci la lista di mosse possibili per quello stato
    public ArrayList<Move> getMoves(){
        //generates a priority queue to sort the moves in evaluation order
        ArrayList<Move> ret = new ArrayList<>();
        int count;

        for (int row=0; row<9; row++) {
            for (int col = 0; col < 9; col++) {

                //cerco le mosse che si possono fare
                //ne attribuisco un valore ad ogni mossa (contando quanti numeri possono essere inseriti nella stessa cella)
                //aggiungo la mossa alla priority queue
                if(getNum(row, col) == 0){

                    //Array di supporto
                    //mi permette di tenere conto dei possibili numeri da inserire nella cella
                    ArrayList<Move> m = new ArrayList<>();
                    count = 0;

                    //Verifica per ogni numero
                    for (int i = 1; i < 10; i++) {

                        //controllo che il numero non sia gia' presente nella riga
                        boolean foundRow = false;
                        for (int j = 0; j < 9 && !foundRow; j++) {
                            //non controllo la casella
                            if (j != col)
                                if (getNum(row, j) == i)
                                    foundRow = true;
                        }

                        //controllo che il numero non sia gia' presente nella colonna
                        boolean foundCol = false;
                        for (int j = 0; j < 9 && !foundCol; j++) {
                            if (j != row)
                                if (getNum(j, col) == i)
                                    foundCol = true;
                        }

                        //ora devo controllare la sezione
                        int x = ((int) (row / 3)) * 3;
                        int y = ((int) (col / 3)) * 3;
                        boolean foundSector = false;

                        //controllo che il numero non sia gia' presente nel settore
                        for (int j = x; (j < x + 3) && !foundSector; j++) {
                            for (int k = y; (k < y + 3) && !foundSector; k++) {
                                if (!(j == row && k == col) && (getNum(j, k) == i)) foundSector = true;
                            }
                        }

                        if (!(foundCol || foundRow || foundSector)) {
                            //nella priority queue di supporto
                            m.add(new Move(row, col, i));
                            count++;
                        }

                    }

                    //attribuisco il valore alle mosse che ho appena trovato
                    if(m.size() != 0) {
                        for(Move x : m){
                            x.setVal(count);
                            ret.add(x);
                        }
                    }
                }
            }
        }
        return ret;
    }

    //Controllo se lo stato e' una soluzione
    public boolean isSolution(){
        boolean breackPoint = false;
        //vedere quanti numeri si possono inserire in ogni cella vuota
        for(int i=0; i<9 && !breackPoint; i++){
            for(int j=0; j<9 && !breackPoint; j++){

                //verifico che la cella sia vuota, altrimenti vado avanti
                if(getNum(i, j) == 0)
                        breackPoint = true;
            }
        }

        //caso in cui la griglia e' riempita con valori corretti
        if (!breackPoint)
            return true;
        else
            return false;
    }

    public int getNum(int row, int col){
        return G.getNum(row, col);
    }

    public int value(){
        return val;
    }

    public void printGrid(){
        G.printGrid();
    }
}