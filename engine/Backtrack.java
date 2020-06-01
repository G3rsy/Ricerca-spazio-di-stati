package engine;

import java.util.PriorityQueue;

public class Backtrack {
    private State backtrackState;

    public Backtrack(State in){
        backtrackState = in;
    }

    public PriorityQueue<State> cleanUp(PriorityQueue<State> p){
        State x = null;
        boolean found = false;

        //il ciclo scorre rimuovendo tutti gli stati
        //si ferma nel momento in cui trova lo stato Backtrack che verra' rimosso
        //poi termina ritornando la coda pulita
        while(!p.isEmpty() && !found){
            x = p.poll();

            if(x.value() == backtrackState.value())
                found = true;
        }

        return p;
    }

}
