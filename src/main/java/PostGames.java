import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.DatabaseConnection;

// Servlet Name
@WebServlet("/PostGames")
public class PostGames extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            // initialize the database
            Connection con = DatabaseConnection.initializeDatabase();

            // create a SQL query to insert data into games table
            PreparedStatement st = con.prepareStatement("insert into games values(?, ?, ?)");

            // get the number of rows for game id
            Statement stmt = con.createStatement();
            String query = "select count(*) from games";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int count = rs.getInt(1);

            // get data from the request object and set data to st pointer
            st.setInt(1, count + 1);
            st.setString(2, request.getParameter("gameName"));
            st.setString(3, request.getParameter("URL"));

            // register changes in database
            st.executeUpdate();
            st.close();
            con.close();

            // display successfully created message
            PrintWriter out = response.getWriter();
            out.println("<html><body><b>Successfully Inserted"
                    + "</b></body></html>");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}