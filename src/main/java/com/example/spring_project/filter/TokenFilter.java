package com.example.spring_project.filter;

import com.auth0.jwt.JWT;
import com.example.spring_project.SpringProjectApplication;
import com.example.spring_project.login.ClientType;
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
    private ApplicationContext ctx;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SpringProjectApplication  spa=ctx.getBean(SpringProjectApplication.class);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis()-1800000);
        System.out.println(calendar.getTimeInMillis());
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(sdf.format(calendar.getTime()));
//        String token=request.getHeader("authorization");
//        System.out.println(spa.sessions().get(token.replace("Bearer ","")).getDate().getTimeInMillis());
        try {
            String token=request.getHeader("authorization");
//           ClientType clientType= ClientType.valueOf(JWT.decode(token.replace("Bearer ","")).getClaim("clientType").asString());
            if (spa.sessions().get(token.replace("Bearer ","")).getDate().getTimeInMillis()-calendar.getTimeInMillis()>0) {
                filterChain.doFilter(request, response);
            }
            else {
                spa.sessions().remove(token);
                response.setStatus(401);
                response.getWriter().write("Your connection has been disconnected, please log in!");
            }
        }catch (Exception e){
            response.setStatus(401);
            response.getWriter().write("invalid,please log in!");
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> excludeUrlPatterns = List.of(
                "/auth/login",
                "/"
//                ,"*"
        );
        return excludeUrlPatterns
                .stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }


}
