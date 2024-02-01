public class Game {
private  GameIO io;

    public Game(GameIO io) {
        this.io = io;
    }

    public void gameLoop() {
        boolean answer = true;
        while (answer) { // spel loppen
            String goal = makeGoal();
            io.clear();
            io.addString("New game:\n");
            //comment out or remove next line to play real games!

            io.addString("For practice, number is: " + goal + "\n");
            String guess = io.getString();
            io.addString(guess +"\n");
            int nGuess = 1;
            String bbcc = checkBC(goal, guess);
            io.addString(bbcc + "\n");
            while ( ! bbcc.equals("BBBB,")) {
                nGuess++;
                guess = io.getString();
                io.addString(guess +": ");
                bbcc = checkBC(goal, guess);
                io.addString(bbcc + "\n");
            }
            int ok = stmt.executeUpdate("INSERT INTO results " + // andväns inte
                    "(result, playerid) VALUES (" + nGuess + ", " +	id + ")" );
              showTopTen();
            answer = io.yesNo("Correct, it took " + nGuess
                    + " guesses\nContinue?");

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
}
