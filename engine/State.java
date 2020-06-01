package engine;

import java.util.Iterator;

public interface State extends Cloneable, Iterable<State>{
    /**
     *@return the iterator for successors, ordered as increasing values
     */
    Iterator<State> iterator();
    /**
     * Returns the value, a value of 999 means the goal has been reached
     * @return value.
     */
    int value();

    //funzioni di supporto al backtracking
    void expanded();

    boolean isExpanded();

    void printGrid();
}