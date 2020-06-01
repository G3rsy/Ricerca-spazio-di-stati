import engine.Engine;
import engine.State;

import java.io.File;
import java.io.IOException;

public class SudokuSearch {
    public boolean run(String filePath) throws IOException {
        File x = new File(filePath);


        State T = new SudokuState(x);

        Engine E = new Engine(T);

        T = E.completeSearch();

        if(T != null) {
            System.out.println("======== SOLUZIONE ======");
            T.printGrid();
            System.out.println("Expanded nodes " + Engine.expandedNodes);
            return true;
        }else{
            return false;
        }
    }


}
