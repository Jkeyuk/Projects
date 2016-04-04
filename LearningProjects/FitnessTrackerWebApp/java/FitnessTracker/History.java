package FitnessTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class History extends HttpServlet {

    private String userName;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        //handle cookies to get username
        cookieHandler(req);
        //get data for user from database
        ArrayList<String> data = getDataFromDatabase();
        //send data to client
        SendDataToClient(data, resp);
    }

    private void cookieHandler(HttpServletRequest req) {
        //get cookies from request
        Cookie[] cookies = req.getCookies();
        //loop through cookies to set username
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                userName = cookie.getValue();
            }
        }
    }

    private ArrayList<String> getDataFromDatabase() {
        //set root path and connect to database
        String rootpath = getServletContext().getRealPath("/");
        SQLiteDataBase database = new SQLiteDataBase(rootpath + "workout.db");
        //set sqlite conditions
        String sqlConditions = "USER = '" + userName + "' ORDER BY EXERDATE ASC";
        //get records from database,close connections,then return result set
        ArrayList<String> resultSet
                = database.selectRecordsConditional("WLIFT", "SETS", sqlConditions);
        database.closeConnections();
        return resultSet;
    }

    private void SendDataToClient(ArrayList<String> data, HttpServletResponse resp) {
        if (data.isEmpty()) {
            resp.setStatus(204);
        } else {
            try {
                PrintWriter output = resp.getWriter();
                //set content type
                resp.setContentType("application/json");
                //set status code
                resp.setStatus(200);
                //add data to string
                String returnData = "";
                for (String string : data) {
                    returnData += string + "?";
                }//send string of data
                output.println(returnData);
            } catch (IOException ex) {
                Logger.getLogger(Workout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
