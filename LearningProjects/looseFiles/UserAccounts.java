
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//This servlet is used to handle a post request to add a new user to the app
public class UserAccounts extends HttpServlet {

    //username to be stored in database
    private String username;
    //usersalt to be stored in database
    private byte[] salt;
    //userhash will be saved and used for security
    private String hash;

    //response to each post request
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        //parse form data through security method
        secureParse(request);
        //add user and respond to client
        responseToClient(addUserToDatabase(), response);
    }

    private void secureParse(HttpServletRequest request) {
        //parse form data
        username = request.getParameter("username").toUpperCase();
        char[] password = request.getParameter("password").toCharArray();
        //generate random security salt
        salt = SecurityTools.returnSecureSalt();
        //get hash from salted password
        hash = SecurityTools.passWordToHashWord(password, salt);
        //erase password before garbage collection
        password = username.toCharArray();
    }

    private boolean addUserToDatabase() {
        //set root path and connect to database
        String rootpath = getServletContext().getRealPath("/");
        SQLiteDataBase database = new SQLiteDataBase(rootpath + "adminData.db");
        //set string to be sql compliant     
        String values = "'" + username + "' , '" + hash + "' , '"
                + SecurityTools.encode(salt) + "'";
        try {//insert username and password into database
            database.insertRecord("USERACCOUNTS", values);
        } catch (SQLException ex) {
            database.closeConnections();
            //return false if user was not added
            return false;
        }//close connection
        database.closeConnections();
        //return true if user added
        return true;
    }

    private void responseToClient(boolean wasUserAdded, HttpServletResponse response) {
        try {//connect to output stream
            PrintWriter output = response.getWriter();
            //set content type
            response.setContentType("text/html");
            if (wasUserAdded) {
                response.setStatus(201);
                //write response to output stream
                output.println("<p>user succesfully added</p>");
                output.println("<a href='/../adminApp/'>Back To Login</a>");
            } else {
                response.setStatus(409);
                //write response to output stream
                output.println("<p>USER ALREADY EXISTS</p>");
                output.println("<a href='./signUp.html'>TRY AGAIN</a>");
            }//close connections to writer
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(UserAccounts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
