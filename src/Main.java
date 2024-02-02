import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Main {

    static SimpleWindow gameWindow;
   static public DataBase dataBase= new DataBase();


    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        gameWindow = new SimpleWindow("Moo");

        // login
        gameWindow.addString("Enter your user name:\n");
        String userName = gameWindow.getString(); // hämtar texten i skrivfältet

        loggIn(userName);
        if (rs.next()) { //hämtar id med användarnamn
            id = rs.getInt("id");
        } else {
            gameWindow.addString("User not in database, please register with admin");
            Thread.sleep(5000);
            gameWindow.exit();
        } // användar namn finns inte stämger pogramet


        Game game = new Game(gameWindow);
        game.gameLoop();
        gameWindow.exit();
    }


    static void showTopTen() throws SQLException {
        var topList = dataBase.getTopTen();
        gameWindow.addString("Top Ten List\n    Player     Average\n");
        int pos = 1;
        topList.sort((p1,p2)->(Double.compare(p1.average, p2.average)));
        for (PlayerAverage p : topList) {
            gameWindow.addString(String.format("%3d %-10s%5.2f%n", pos, p.name, p.average));
            if (pos++ == 10) break;
        }

    }
}