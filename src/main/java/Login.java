import java.io.BufferedReader;
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
import org.json.JSONException;
import org.json.JSONObject;

// Servlet Name
@WebServlet("/Login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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

            // create a SQL query to retrieve data from users table
            PreparedStatement st = con.prepareStatement("select emailid,password from users where emailid = ? and password = ?");

            StringBuffer jb = new StringBuffer();
            String line;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jb.append(line);
                }
            } catch (Exception e) { e.printStackTrace(); }

            // get data from the request JSONObject
            String email, password;
            try {
                JSONObject jsonObject =  new JSONObject(jb.toString());
                System.out.println(jsonObject);
                email = jsonObject.getString("emailid");
                password = jsonObject.getString("password");
            } catch (JSONException e) {
                throw new IOException("Error parsing JSON request string");
            }
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            String eml="",pswd="";

            while(rs.next()) {
                 eml = rs.getString("emailid");
                 pswd = rs.getString("password");
            }
            PrintWriter out = response.getWriter();

            if (eml.isEmpty() || pswd.isEmpty())
            {
                // incorrect login credentials
                out.println("Email/Password is incorrect. Enter valid credentials.");
            }else {
                // display successful login message
                out.println("Login Successful");
            }
            out.flush();
        }
        catch (Exception e) {
           e.printStackTrace();
        }
    }
}
