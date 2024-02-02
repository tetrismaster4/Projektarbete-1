import java.sql.SQLException;


public class Main {


    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {

        SimpleWindow gameWindow = new SimpleWindow("Moo");
        DataBase dataBase = new DataBase();
        // login
        gameWindow.print("Enter your user name:\n");
        String userName = gameWindow.getUserInput(); // hämtar texten i skrivfältet

        int id = dataBase.login(userName);
        if (id == -1) { //hämtar id med användarnamn
            gameWindow.print("User not in database, please register with admin");
            Thread.sleep(5000);
            gameWindow.exit();
        } // användar namn finns inte stämger pogramet


        Game game = new Game(gameWindow,dataBase,id);
        game.gameLoop();
        gameWindow.exit();
    }

}