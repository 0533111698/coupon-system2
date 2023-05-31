package com.example.spring_project.filter;

import com.auth0.jwt.JWT;
import com.example.spring_project.SpringProjectApplication;
import com.example.spring_project.login.ClientType;
import com.example.spring_project.login.LoginParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private HashMap<String, LoginParameters> sessions;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token=request.getHeader("authorization").replace("Bearer ","");
            if (sessions.get(token)!=null) {
                filterChain.doFilter(request, response);
                LoginParameters lgToUpdate=sessions.get(token);
                lgToUpdate.setCalendar(Calendar.getInstance());
                sessions.put(token,lgToUpdate);
            }
            else {
                response.setStatus(401);
                response.getWriter().write("Your connection has been disconnected, please log in!");
            }
        }catch (Exception e){
//            response.setStatus(401);
 //           response.getWriter().write("invalid,please log in!");
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> excludeUrlPatterns = List.of(
                "/auth/login",
                "/customer/getAll",
                "/"
        );
        return excludeUrlPatterns
                .stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }


}