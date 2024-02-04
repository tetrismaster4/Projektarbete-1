import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @org.junit.jupiter.api.Test
    void gameLoop() {

    }

    @org.junit.jupiter.api.Test
    void generateSecretNumberShouldReturnUniqueFourDigitString() {

    }
    @org.junit.jupiter.api.Test
    void  countBullsAndCows_GuessEquelsSecretNumber_FourBulls(){
        // arrenge
        Game game = new Game(null,null,0);
        //acte
        var bullsAndCows = game.countBullsAndCows("1234","1234");
        //assert
        assertTrue(bullsAndCows.bulls()==4);
    }

    @org.junit.jupiter.api.Test
    void checkBC() {
    }


}