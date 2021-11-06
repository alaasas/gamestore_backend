import com.database.DatabaseConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class GetGamesTest {

    @Test
    void doGet() throws SQLException, ClassNotFoundException, IOException, ServletException {
        // mock request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // initialize the database
        Connection con = DatabaseConnection.initializeDatabase();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from games");

        // creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        while(rs.next()) {
            JSONObject record = new JSONObject();
            // inserting key-value pairs into the json object
            record.put("gameId", rs.getString("gameId"));
            record.put("gameName", rs.getString("gameName"));
            record.put("URL", rs.getString("URL"));
            record.put("summary", rs.getString("summary"));
            record.put("developers", rs.getString("developers"));
            record.put("price", rs.getDouble("price"));
            record.put("genres", rs.getString("genres"));
            array.put(record);
        }

        GetGames servlet = new GetGames();
        servlet.doGet(request, response);

        assertEquals(array.toString(), response.getContentAsString());
    }
}