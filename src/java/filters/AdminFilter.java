/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import domain.Account;
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
import services.AccountService;

/**
 *
 * @author Harmanjit Mohaar
 */
public class AdminFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest r = (HttpServletRequest)request;
        HttpSession session = r.getSession();
        
        Account account = (Account) session.getAttribute("account");
        
        if (account!=null && account.getAccountType().equals("admin")){
            chain.doFilter(request, response);
        } else {
            HttpServletResponse resp = (HttpServletResponse)response;
            resp.sendRedirect("login");
        }
    }


    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {
    }
}

