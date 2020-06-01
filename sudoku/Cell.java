package sudoku;

public class Cell {
    int x, y;

    public Cell(int a, int b){
        if( x > 8 || y > 8)
            throw new IllegalArgumentException();
        x = a;
        y = b;
    }
    public String toString(){
        return "x="+x+", y="+y;
    }

}
