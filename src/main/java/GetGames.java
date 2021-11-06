import com.database.DatabaseConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class GetGames extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        try {
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