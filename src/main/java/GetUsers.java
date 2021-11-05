import com.database.DatabaseConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import javax.servlet.http.*;
import java.sql.*;

public class GetUsers extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        try {
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

            PrintWriter out = res.getWriter();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.addHeader("Access-Control-Allow-Origin","*");
            out.print(array);
            out.flush();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}