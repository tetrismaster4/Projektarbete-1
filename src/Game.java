import java.sql.SQLException;

public class Game {
private  GameIO io;
    private DataBase dataBase;
private int id;

    public Game(GameIO io, DataBase dataBase, int id) {
        this.io = io;
        this.dataBase = dataBase;
        this.id = id;
    }

    public void gameLoop() throws SQLException {
        boolean answer = true;
        while (answer) { // spel loppen
            String goal = makeGoal();
            io.clear();
            io.print("New game:\n");
            //comment out or remove next line to play real games!

            io.print("For practice, number is: " + goal + "\n");
            String guess = io.getUserInput();
            io.print(guess +"\n");
            int nGuess = 1;
            String bbcc = checkBC(goal, guess);
            io.print(bbcc + "\n");
            while ( ! bbcc.equals("BBBB,")) {
                nGuess++;
                guess = io.getUserInput();
                io.print(guess +": ");
                bbcc = checkBC(goal, guess);
                io.print(bbcc + "\n");
            }
            dataBase.updateResult(nGuess, id);
            answer = io.yesNo("Correct, it took " + nGuess
                    + " guesses\nContinue?");
            showTopTen();

        }

    }



    public static String makeGoal(){ // får mål numret
        String goal = "";
        for (int i = 0; i < 4; i++){ // får en int för varje ruta i spelet
            int random = (int) (Math.random() * 10);
            String randomDigit = "" + random;

            while (goal.contains(randomDigit)){ // ser till att inte adderar goal med goal
                random = (int) (Math.random() * 10);
                randomDigit = "" + random;
            }
            goal = goal + randomDigit;
        }
        return goal;
    }

    public static String checkBC(String goal, String guess) {
        guess += "    ";
        int cows = 0, bulls = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++ ) {
                if (goal.charAt(i) == guess.charAt(j)){
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
        String result = "";
        for (int i = 0; i < bulls; i++){
            result = result + "B";
        }
        result = result + ",";
        for (int i = 0; i < cows; i++){
            result = result + "C";
        }
        return result;

    }
    private void showTopTen() throws SQLException {
        var topList = dataBase.getTopTen();
        io.print("Top Ten List\n    Player     Average\n");
        int pos = 1;
        topList.sort((p1,p2)->(Double.compare(p1.average, p2.average)));
        for (PlayerAverage p : topList) {
            io.print(String.format("%3d %-10s%5.2f%n", pos, p.name, p.average));
            if (pos++ == 10) break;
        }

    }
}
