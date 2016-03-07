
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFirstServlet extends HttpServlet {

    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        //set response content type
        response.setContentType("text/html");

        //setup output stream
        PrintWriter output = response.getWriter();

        //write to output
        output.println("<p>hi " + request.getParameter("name") + "</p>");

        //get headers then write them to output
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            output.println("<p>" + headerName + ": " + request.getHeader(headerName) + "</p>");
        }
    }

    @Override
    public void destroy() {
    }
}
