package com.example.demo;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CORSFilter extends GenericFilterBean  {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, token");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        System.out.println("********** CORS Configuration Completed **********");

        chain.doFilter(request, response);
    }


}
