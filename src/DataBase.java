import java.sql.*;

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
    public void getTopTen (){

    }
}
