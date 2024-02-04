import java.sql.*;
import java.util.ArrayList;

public class DataBase {
     private Connection connection;
    private Statement stmt;

    public DataBase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moo","root","rootrootroot1");
            stmt = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public   int login(String userName) throws SQLException {
        int id = 0;
        if (stmt == null){
            throw new SQLException("faild to make connection");
        }
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

    public void updateResult(int nGuess, int id) throws SQLException {
        int ok = stmt.executeUpdate("INSERT INTO results " + // andväns inte
                "(result, playerid) VALUES (" + nGuess + ", " +	id + ")" );

    }
}
