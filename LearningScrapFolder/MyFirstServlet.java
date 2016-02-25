
import java.io.IOException;
import java.io.PrintWriter;
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
        output.println("<p>hi</p>");
    }
    
    @Override
    public void destroy(){}
}
