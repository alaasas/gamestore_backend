import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.DatabaseConnection;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

// Servlet Name
@WebServlet("/PostUsers")
public class PostUsers extends HttpServlet {

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

            // create a SQL query to insert data into users table
            PreparedStatement st = con.prepareStatement("insert into users values(?, ?, ?)");

            StringBuffer jb = new StringBuffer();
            String line;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    jb.append(line);
                }
            } catch (Exception e) { /*report an error*/ }

            // get data from the request  JSONObject object
            String fullname, email, password;
            try {
                JSONObject jsonObject =  new JSONObject(jb.toString());
                System.out.println(jsonObject);
                fullname = jsonObject.getString("fullname");
                email = jsonObject.getString("emailid");
                password = jsonObject.getString("password");
            } catch (JSONException e) {
                throw new IOException("Error parsing JSON request string");
            }

            if (fullname.isEmpty() || password.isEmpty() )
            {
                // display that fullname and password must not be empty
                PrintWriter out = response.getWriter();
                out.println(" Some fields are empty");
            }else{
                // set data to st pointer
                st.setString(1, fullname);
                st.setString(2, password);
                st.setString(3, email);

                // register changes in database
                st.executeUpdate();
                st.close();
                con.close();

                // display successfully created message
                PrintWriter out = response.getWriter();
                out.println("Successfully Inserted");

            }
        }
        catch (Exception e) {
            // display failure message
            PrintWriter out = response.getWriter();

            if(e.getMessage().contains("''")){
                out.println(" Some fields are empty");
            } else {
                out.println("Email already registered");
            }
            e.printStackTrace();
        }
    }
}