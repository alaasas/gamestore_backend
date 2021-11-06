import com.database.DatabaseConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class PostUsersTest extends Mockito {

    PostUsers servlet = new PostUsers();

    @Test
    void doPost() throws ServletException, IOException, SQLException, ClassNotFoundException {
        // mock request and response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // test 1 - if one (or more) of the params are empty
        request.addParameter("fullname", "test");
        request.addParameter("emailid", "");
        request.addParameter("password", "test");

        servlet.doPost(request, response);

        assertEquals("One or more of the request params is (are) empty!", response.getContentAsString());

        // test 2 - if email is already registered (test@gmail.com is already in database)
        // reinitialize request and response
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        request.addParameter("fullname", "test");
        request.addParameter("emailid", "test@gmail.com");
        request.addParameter("password", "test");

        servlet.doPost(request, response);

        assertEquals("Email already exists!", response.getContentAsString());

        // test 3 - success scenario
        // delete row with test@test.com so it doesn't throw 'email already exists' exception
        Connection con = DatabaseConnection.initializeDatabase();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("delete from users where emailid='test@test.com'");

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        request.addParameter("fullname", "test");
        request.addParameter("emailid", "test@test.com");
        request.addParameter("password", "test");

        servlet.doPost(request, response);

        assertEquals("Successfully Inserted", response.getContentAsString());
    }
}