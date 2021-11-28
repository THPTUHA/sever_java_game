package com.example.demo.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

     @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if(jwt==null) jwt = request.getParameter("token");
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt,response)) {
                int user_id = tokenProvider.getUserIdFromJWT(jwt);
                User user = userRepository.findById(user_id);
                if(!user.getLocked())response.setStatus(403);
                System.out.println("FUCCCCCCCCCCCCCCCCK "+ user_id);
                request.setAttribute("id", user_id);
            }
        } catch (Exception ex) {
            System.out.println("failed on set user authentication "+ ex);
        }

        filterChain.doFilter(request, response);
    }
}
