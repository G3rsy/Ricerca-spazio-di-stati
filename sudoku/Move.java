package sudoku;

public class Move{
    Cell j;
    int num;
    int val;

    public Move(int x, int y, int n){
        j = new Cell(x, y);
        num = n;
    }

    public void setVal(int v){
        val = v;
    }

    public String toString(){
        return "Cell "+j.toString()+" Num="+num+" Val="+val;
    }
}
