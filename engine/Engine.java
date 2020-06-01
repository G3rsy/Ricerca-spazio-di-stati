package engine;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Engine implements Comparator<State> {
    private PriorityQueue<State> q;
    /**
     * Number of expanded nodes
     */
    public static int expandedNodes=0;

    /**
     * Value for solution States
     */
    public static int solutionValue = 999;

    /**
     * Limit value for queue insertion
     */
    public static int limitValue = 1000;

    /**
     * Limit size for queue
     */
    public static int limitSize = 100000;



    /**
     * Class that retain the indecision node of the tree
     */
    public Backtrack btState = null;

    public Engine(State seed) {
        this.expandedNodes = 0;
        this.q = new PriorityQueue<State>(limitSize, this);
        if(seed.value() < limitValue)
            this.q.add(seed);
    }
    /**
     * Expand a state putting its successors in the queue
     * @return the solution state if the goal has been reached
     * null otherwise
     */
    public State expand() {
        expandedNodes++;

        if (q.isEmpty()) return null;

        //non lo rimuovo dalla queue, sara' lo stopper della funzione di clean
        State item = q.poll();

        //caso di stato risolto
        //serve solo nel caso in cui venga inserito un problema gia' risolto
        if(item.value()==solutionValue)return item;

        if(!item.isExpanded()) {
            item.expanded();
            q.add(item);

            //Verifico se c'e' indecisione in questo nodo
            //se e' il primo nodo dell'albero che ha indecisione
            //allora e' il nodo sul quale tornero' in caso di fallimento

            //restituisce quante mosse sono possibili per quello stato
            int indecision = 10-(item.value()%10);

            if( (indecision != 1)  && btState == null){
                btState = new Backtrack(item);
            }
            //non c'e' un ramo else perche':
            // - caso in cui indecision == 1, non ho indecisione quindi scorro in basso nell'albero
            // - caso in cui backtrackState != null, ho un indecisione ad un livello piu' alto
            //   che e' preferita ad una di livello piu' basso

            for (State son : item) {
                if (son.value() == solutionValue) return son;
                if (son.value() < limitValue) q.add(son);
            }

        }else{
            if(btState == null) {
                //caso in cui sono in uno stato non consistente
                item.printGrid();
                throw new IllegalStateException();
            }else {
                System.out.println("Eseguito la cleanUp");
                q = btState.cleanUp(q);
                btState = null;
            }
        }

        return null;
    }

    /**
     * Perform a complete search
     * @return the solution state if the goal has been reached
     * null otherwise
     */
    public State completeSearch() {
        State son = expand();

        while (!q.isEmpty() && q.size() < limitSize && son == null)
            son = expand();

        return son;
    }

    /**
     * @return a string with the queue size
     * the minimal value and the number of expanded nodes.
     */
    public String toString() {
        return
                "Queue size = " +
                        q.size() +", min value " +
                        q.peek().value()+", expanded nodes "+expandedNodes;
    }

    /**
     * Comparator for the Priority Queue
     * @return the sign of the difference arg0.value()-arg1.value()
     */
    public int compare(State arg0, State arg1) {
        if (arg0.value() < arg1.value())
            return 1;
        else if (arg0.value() > arg1.value())
            return -1;
        return 0;

    }

}