
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//this servlet interacts with the sqlite database to store the mail information

public class MailServlet extends HttpServlet {

    //database that will be used
    private SQLiteDataBase database;

    @Override
    public void init() {
        //set root path and connect to database
        String rootpath = getServletContext().getRealPath("/");
        database = new SQLiteDataBase(rootpath + "adminData.db");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        //get data from database
        ArrayList<String> data = buildData();
        //respond with data
        responseToClient(200, data, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        //parse data
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String tracking = request.getParameter("tracking");
        String type = request.getParameter("type");
        //set sql compliant string
        String values = "'" + address + "','" + name + "','" + tracking + "','" + type + "'";
        try {//add to database
            database.insertRecord("MAIL", values);
            response.setStatus(201);
        } catch (SQLException ex) {
            Logger.getLogger(MailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        //parse data
         String tracking = request.getParameter("tracking");
        try {//send query to database
            database.deleteRecord("MAIL", "tracking="+tracking);
            response.setStatus(200);
        } catch (SQLException ex) {
            Logger.getLogger(MailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {
        database.closeConnections();
    }

    private void responseToClient(int statuscode, ArrayList<String> body,
            HttpServletResponse response) {
        try {//attempt to connect to output stream
            PrintWriter output = response.getWriter();
            //set content type
            response.setContentType("text/html");
            //set status code
            response.setStatus(statuscode);
            //output body of data
            for (String string : body) {
                output.println(string);
            }
        } catch (IOException ex) {
            Logger.getLogger(MailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<String> buildData() {
        //array to be returned
        ArrayList<String> returnResults = new ArrayList<String>();
        //data from database to build
        ArrayList<String> address = database.selectRecords("MAIL", "address");
        ArrayList<String> name = database.selectRecords("MAIL", "name");
        ArrayList<String> tracking = database.selectRecords("MAIL", "tracking");
        ArrayList<String> type = database.selectRecords("MAIL", "type");
        //build data
        for (int i = 0; i < address.size(); i++) {
            String toAdd = "<tr><td>" + address.get(i) + "</td><td>" + name.get(i)
                    + "</td><td>" + tracking.get(i) + "</td><td>" + type.get(i)
                    + "</td>"
                    + "<td><button class='removeBut'>Remove Item</button></td></tr>";
            returnResults.add(toAdd);
        }//return data
        return returnResults;
    }
}
