import java.sql.*;
import java.util.ArrayList;

public class DataBase {
     private Connection connection;
    private Statement stmt;

    public DataBase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/Moo","ulf","ulfpw");
            stmt = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public   int loggIn(String userName) throws SQLException, InterruptedException {
        int id = 0;

        ResultSet rs = stmt.executeQuery("select id,name from players where name = '" + userName + "'");

        if (rs.next()) { //hämtar id med användarnamn
            return rs.getInt("id");
        }
        else {
            return -1;
        }

    }
    public ArrayList<PlayerAverage> getTopTen () throws SQLException {

        ArrayList<PlayerAverage> topList = new ArrayList<>();
        ResultSet rs2;
        ResultSet rs = stmt.executeQuery("select * from players");
        while(rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            rs2 = stmt.executeQuery("select * from results where playerid = "+ id );
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
        return topList;
    }
}
