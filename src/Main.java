import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Main {
    static Connection connection;
    static Statement stmt;
    static ResultSet rs;
    static SimpleWindow gameWindow;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        gameWindow = new SimpleWindow("Moo");

        // login
        loggIn();

        gameLoop();
        gameWindow.exit();
    }

    private static void loggIn() throws SQLException, InterruptedException {
        gameWindow.addString("Enter your user name:\n");
        String userName = gameWindow.getString(); // hämtar texten i skrivfältet
        int id = 0;
        connection = DriverManager.getConnection("jdbc:mysql://localhost/Moo","ulf","ulfpw");
        stmt = connection.createStatement();
        rs = stmt.executeQuery("select id,name from players where name = '" + userName + "'");

        if (rs.next()) { //hämtar id med användarnamn
            id = rs.getInt("id");
        } else {
            gameWindow.addString("User not in database, please register with admin");
            Thread.sleep(5000);
            gameWindow.exit();
        } // användar namn finns inte stämger pogramet
    }




    static class PlayerAverage {
        String name;
        double average;
        public PlayerAverage(String name, double average) {
            this.name = name;
            this.average = average;
        }
    }

    static void showTopTen() throws SQLException {
        ArrayList<PlayerAverage> topList = new ArrayList<>();
        Statement stmt2 = connection.createStatement();
        ResultSet rs2;
        rs = stmt.executeQuery("select * from players");
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            rs2 = stmt2.executeQuery("select * from results where playerid = "+ id );
            int nGames = 0;
            int totalGuesses = 0;
            while (rs2.next()) {
                nGames++;
                totalGuesses += rs2.getInt("result");
            }
            if (nGames > 0) {
                topList.add(new PlayerAverage(name, (double)totalGuesses/nGames));
            }

        }
        gameWindow.addString("Top Ten List\n    Player     Average\n");
        int pos = 1;
        topList.sort((p1,p2)->(Double.compare(p1.average, p2.average)));
        for (PlayerAverage p : topList) {
            gameWindow.addString(String.format("%3d %-10s%5.2f%n", pos, p.name, p.average));
            if (pos++ == 10) break;
        }

    }
}