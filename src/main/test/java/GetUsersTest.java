import com.database.DatabaseConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


class GetUsersTest extends Mockito {

    @Test
    void doGet() throws IOException, SQLException, ClassNotFoundException {
        //Mock request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // initialize the database
        Connection con = DatabaseConnection.initializeDatabase();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users");

        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        while(rs.next()) {
            JSONObject record = new JSONObject();
            //Inserting key-value pairs into the json object
            record.put("fullName", rs.getString("fullname"));
            record.put("password", rs.getString("password"));
            record.put("email", rs.getString("emailid"));
            array.put(record);
        }

        GetUsers servlet = new GetUsers();
        servlet.doGet(request, response);

        assertEquals(array.toString(), response.getContentAsString());
    }
}