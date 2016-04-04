package FitnessTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Workout extends HttpServlet {

    private String exerciseDate;
    private String exerciseName;
    private String objectAsJson;
    private String userName;
    private Cookie[] cookies;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        //handle cookies
        cookieHandler(req);
        //get data from database
        ArrayList<String> resultSet = getDataFromDatabase(req);
        //send data to client
        SendDataToClient(resultSet, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        //parse request data
        parseRequestData(req);
        //handle cookies for username
        cookieHandler(req);
        //store data in sqlite database and respond with code
        responseToClient(addExerciseToDatabase(), resp);
    }

    private void parseRequestData(HttpServletRequest req) {
        try {//buffer input stream from request
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(req.getInputStream()));
            //create json parser
            JSONParser parser = new JSONParser();
            //create json object with parser
            JSONObject jsonObj = (JSONObject) parser.parse(reader);
            //parse data from json object
            exerciseDate = (String) jsonObj.get("date");
            exerciseName = (String) jsonObj.get("name");
            objectAsJson = jsonObj.toJSONString();
        } catch (IOException ex) {
            Logger.getLogger(Workout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Workout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cookieHandler(HttpServletRequest req) {
        //get cookies from request
        cookies = req.getCookies();
        //loop through cookies to set username
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                userName = cookie.getValue();
            }
        }
    }

    private boolean addExerciseToDatabase() {
        //set root path and connect to database
        String rootpath = getServletContext().getRealPath("/");
        SQLiteDataBase database = new SQLiteDataBase(rootpath + "workout.db");
        //set string to be sql compliant     
        String values = "'" + userName + "' , '" + exerciseName + "' , '"
                + exerciseDate + "' , '" + objectAsJson + "'";
        try {//insert data into database
            database.insertRecord("WLIFT", values);
        } catch (SQLException ex) {
            database.closeConnections();
            //return false if data was not added
            return false;
        }//close connection
        database.closeConnections();
        //return true if data added
        return true;
    }

    private void responseToClient(boolean wasDataAdded, HttpServletResponse response) {
        if (wasDataAdded) {
            response.setStatus(201);
        } else {
            response.setStatus(400);
        }
    }

    private ArrayList<String> getDataFromDatabase(HttpServletRequest req) {
        //get date from request
        String date = req.getParameter("date");
        //set root path and connect to database
        String rootpath = getServletContext().getRealPath("/");
        SQLiteDataBase database = new SQLiteDataBase(rootpath + "workout.db");
        //set sqlite conditions
        String sqlConditions = "USER = '" + userName + "' AND EXERDATE = '" + date + "'";
        //get records from database
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
