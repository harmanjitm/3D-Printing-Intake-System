/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ben Wozak
 */
public class AuthenticationFilter implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthenticationFilter() {
    }    

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
       // this.filterConfig = filterConfig;
    }
    
    /**
     *
     * for authenticating the username against the database.
     * if username exists within the database, proceed to the
     * program, if the name does not exist, redirect to the 
     * login screen.
     * 
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
            HttpServletRequest r = (HttpServletRequest)request;
            HttpSession session = r.getSession();
            //My dude we are NOT storing passwords anywhere except the database
            //Email is enough for checking if cookies are stored on the browser
            if (session.getAttribute("account") != null) {
                //if user is authenticated, continue on to the servlet
                chain.doFilter(request, response);
            } else {
                //they do not have a username in the database
                HttpServletResponse resp = (HttpServletResponse)response;
                resp.sendRedirect("login");
            }
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {    
        //filterConfig = null;
    }
}
