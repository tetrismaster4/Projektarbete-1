import java.sql.SQLException;

public final class Game {
    private GameIO io;
    private DataBase dataBase;
    private int id;

    public Game(GameIO io, DataBase dataBase, int id) {
        this.io = io;
        this.dataBase = dataBase;
        this.id = id;
    }


    public void gameLoop() throws SQLException {
        boolean isPracticeMode = isPracticeModeOn();
        boolean continuePlaying = true;
        while (continuePlaying) {
            io.clear();
            io.println("New game:");

            int guessCount = runGame(isPracticeMode);

            dataBase.updateResult(guessCount, id);
            continuePlaying = io.continuePromptWindow("Correct, it took " + guessCount
                    + " guesses\nContinue?");
            showTopTen();
        }
    }

    private boolean isPracticeModeOn() {
        io.println("Do you want to play practice mode? (Y/N)");
        String input = io.getUserInput().toUpperCase();

        if (input.equals("Y")) {
            return true;
        }
        return false;
    }

    private int runGame(boolean isPracticeMode) {
        /*
        1. Create secret number
        2. Get user input
        3. Test user input
        4a. if correct exit
        4b. else increment guess count and do step 2
        */

        // 1.
        String secretNumber = generateSecretNumber();

        if (isPracticeMode) {
            io.println("For practice, number is: " + secretNumber);
        }

        int guessCount = 0;
        while (true) {
            // 2.
            String guess = getUserInput();
            // print UserInput
            io.println(guess);

            // 3.
            var result = countBullsAndCows(secretNumber, guess);
            // print result
            io.println(result.toString());

            // 4a.
            if (result.isCorrect()) {
                return guessCount;
            } else {
                guessCount++;
            }
        }
    }



    public String generateSecretNumber() { // får mål numret
        String secretNumber = "";
        for (int i = 0; i < 4; i++){ // får en int för varje ruta i spelet
            int random = (int) (Math.random() * 10);
            String randomDigit = "" + random;

            while (secretNumber.contains(randomDigit)){ // ser till att inte adderar goal med goal
                random = (int) (Math.random() * 10);
                randomDigit = "" + random;
            }
            secretNumber = secretNumber + randomDigit;
        }
        return secretNumber;
    }

    private String getUserInput() {
        return io.getUserInput();
    }

    public record BullsAndCows(int bulls, int cows) {
        boolean isCorrect() {
            return bulls == 4;
        }

        @Override
        public String toString() {
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

    public BullsAndCows countBullsAndCows(String secretNumber, String guess) {
        guess += "    ";
        int misplacedNumbers = 0;
        int correctNumbers = 0;

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++ ) {
                if (secretNumber.charAt(i) == guess.charAt(j)){
                    if (i == j) {
                        correctNumbers++;
                    } else {
                        misplacedNumbers++;
                    }
                }
            }
        }
        return new BullsAndCows(correctNumbers, misplacedNumbers);
    }


    private void showTopTen() throws SQLException {
        var topList = dataBase.getTopTen();
        io.println("Top Ten List\n    Player     Average");
        int pos = 1;
        topList.sort((p1,p2)->(Double.compare(p1.average, p2.average)));
        for (PlayerAverage p : topList) {
            io.print(String.format("%3d %-10s%5.2f%n", pos, p.name, p.average));
            if (pos++ == 10) break;
        }


    }
}
