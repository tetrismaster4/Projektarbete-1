import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {

    @Test
    void login() {
        DataBase dataBase = new DataBase();

        var exceptionon = assertThrows(SQLException.class, () -> dataBase.login("s"));
    }

    @Test
    void getTopTen() {

    }

    @Test
    void updateResult() {
    }
}