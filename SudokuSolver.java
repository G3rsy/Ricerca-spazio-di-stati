import java.io.IOException;

public class SudokuSolver{
    public static void main(String[] Args) throws IOException {

        SudokuSearch s = new SudokuSearch();
        String path = new String();

        Stats easy = new Stats("facile");
        Stats normy = new Stats("normale");
        Stats hard = new Stats("difficile");

        long start = 0, end = 0;
        for(int i =0; i < 1000;i++){
            if(i<250){
                path = "easy"+i+".txt";

                start = System.currentTimeMillis();

                if(s.run("test\\"+path)){
                    end = System.currentTimeMillis();

                    easy.addSolved(end-start);
                }else{
                    end = System.currentTimeMillis();

                    easy.addNotSolved(end-start);
                    System.out.println("Non ho trovato una soluzione");
                }
            }

            if(i>=250 && i<700){
                path = "normal"+i+".txt";

                start = System.currentTimeMillis();
                if(s.run("test\\"+path)){
                    end = System.currentTimeMillis();

                    normy.addSolved(end-start);
                }else{
                    end = System.currentTimeMillis();

                    normy.addNotSolved(end-start);
                    System.out.println("Non ho trovato una soluzione");
                }
            }

            if(i>=700) {
                path = "hard" + i + ".txt";

                start = System.currentTimeMillis();
                if(s.run("test\\"+path)){
                    end = System.currentTimeMillis();

                    hard.addSolved(end-start);
                }else{
                    end = System.currentTimeMillis();

                    hard.addNotSolved(end-start);
                    System.out.println("Non ho trovato una soluzione");
                }
            }
        }

        System.out.println("Risultati:");

        easy.printStats();
        normy.printStats();
        hard.printStats();
    }
}
