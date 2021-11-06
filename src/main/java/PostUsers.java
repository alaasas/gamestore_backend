import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.database.DatabaseConnection;

// Servlet Name
@WebServlet("/PostUsers")
public class PostUsers extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");

        try {
            // initialize the database
            Connection con = DatabaseConnection.initializeDatabase();

            // create a SQL query to insert data into users table
            PreparedStatement st = con.prepareStatement("insert into users values(?, ?, ?)");

            // check if any parameter is empty
            if(request.getParameter("fullname") == ""
                    || request.getParameter("emailid") == ""
                    || request.getParameter("password") == "")
                throw new Exception("One or more of the request params is (are) empty!");

            // check if email is already registered
            String email = request.getParameter("emailid");
            String sql_res = "select * from users where emailid='".concat(email).concat("'");
            ResultSet rs = st.executeQuery(sql_res);

            if(rs.next())
                throw new Exception("Email already exists!");

            // get data from the request object and set data to st pointer
            st.setString(1, request.getParameter("fullname"));
            st.setString(2, request.getParameter("password"));
            st.setString(3, request.getParameter("emailid"));

            // register changes in database
            st.executeUpdate();
            st.close();
            con.close();

            // display successfully created message
            PrintWriter out = response.getWriter();
            out.print("Successfully Inserted");

        }
        catch (Exception e) {
            // display failure message
            PrintWriter out = response.getWriter();
            out.print(e.getMessage());
            e.printStackTrace();
        }
    }
}