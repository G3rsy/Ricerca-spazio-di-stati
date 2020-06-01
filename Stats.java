public class Stats {
    String name;
    //impiegato per poi effettuare la media
    //si presuppone che i problemi non risolti
    //non abbiano una stima del tempo impiegato
    //quindi non vengono contati
    long totalTimeSolved;
    long totalTimeNotSolved;

    long bestValue;
    long worstValue;

    int solved;
    int notSolved;

    public Stats(String n){
        this.name = n;
        totalTimeSolved = 0;
        totalTimeNotSolved = 0;

        bestValue = Long.MAX_VALUE;
        worstValue = Long.MIN_VALUE;

        solved = 0;
        notSolved = 0;
    }

    public void addSolved(long t){
        if(t < bestValue)
            bestValue = t;
        if(t > worstValue)
            worstValue = t;
        solved++;
        totalTimeSolved+=t;
    }

    public void addNotSolved(long t){
        notSolved++;
        totalTimeNotSolved += t;
    }

    public void printStats(){
        System.out.println("Stiamo lavorando su "+(solved+notSolved)+" casi di livello "+name+";");
        System.out.println("Sono stati risolti "+solved+" problemi, con una media di "+(totalTimeSolved/solved)+"ms per problema;");
        System.out.println("Il problema risolto nel minor tempo ha impiegato "+bestValue+"ms, quello con il peggiore ha impiegato "+worstValue+"ms");
        if(notSolved > 0)
            System.out.println("I casi non risolti sono "+notSolved+" e ci hanno impiegato in media "+(totalTimeNotSolved/notSolved)+"ms a fallire;");
    }

}
