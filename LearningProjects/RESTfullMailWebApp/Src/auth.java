
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class auth implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        //create http response and request
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //get the session
        HttpSession session = req.getSession(false);
        try { //get attribute from session
            String isLogged = (String) session.getAttribute("isUserLoged");
            //check attribute
            if (isLogged.equals("true")) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect("../../index.html");
            }//if null user has no session redirect to login
        } catch (NullPointerException e) {
            res.sendRedirect("../../index.html");
        }
    }

    @Override
    public void destroy() {

    }

}
