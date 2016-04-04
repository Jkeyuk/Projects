package FitnessTracker;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {

    //Username from form
    private String username;
    //user salt from database
    private byte[] salt;
    //hash from database
    private String hashFromDatabase;
    //hash created from form
    private String hashToTest;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        //parse form data
        parseData(request);
        //respond to client with appropriate action
        respondToClient(hashToTest.equals(hashFromDatabase), request, response);
    }

    private void parseData(HttpServletRequest request) {
        //get username from form
        username = request.getParameter("username").toUpperCase();
        //get password from form
        char[] passWord = request.getParameter("password").toCharArray();
        //get hash from database
        hashFromDatabase = recordFromDatabase("HASH", username);
        //get salt from database
        salt = SecurityTools.decode(recordFromDatabase("SALT", username));
        //generate hash from form data and salt
        hashToTest = SecurityTools.passWordToHashWord(passWord, salt);
    }

    private String recordFromDatabase(String colum, String userName) {
        //set root path and connect to database
        String rootpath = getServletContext().getRealPath("/");
        SQLiteDataBase database = new SQLiteDataBase(rootpath + "USERS.db");
        //retrieve resultset from sql query
        ArrayList<String> resultSet = database.selectRecordsConditional(
                "USERS", colum, "NAME='" + userName + "'");
        try {//try to parse hash from result set
            String hash = resultSet.get(0).trim();
            //return hash if no exception
            return hash;
            //if index is out of bounds, that means that user does not exsist
        } catch (IndexOutOfBoundsException e) {
            //return hash that will fail
            return "999999999999";
        }
    }

    private void respondToClient(boolean pass, HttpServletRequest request,
            HttpServletResponse response) {
        if (pass) {//if credentials pass create session and send okay response
            HttpSession session = request.getSession();
            session.setAttribute("isUserLoged", "true");
            Cookie nameCookie = new Cookie("username", username);
            response.addCookie(nameCookie);
            response.setStatus(200);
        } else {//else respond with error
            response.setStatus(403);
        }

    }
}
