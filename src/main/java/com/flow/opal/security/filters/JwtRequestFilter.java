package com.flow.opal.security.filters;

import com.flow.opal.security.services.MyUserDetailsService;
import com.flow.opal.security.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private MyUserDetailsService userDetailsService;
  private JwtUtil jwtUtil;

  @Autowired
  public JwtRequestFilter(MyUserDetailsService userDetailsService,
      JwtUtil jwtUtil) {
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtUtil.extractUsername(jwt);
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails ud = this.userDetailsService.loadUserByUsername(username);
      if (jwtUtil.validateToken(jwt, ud)) {
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(ud,
            null,ud.getAuthorities());
        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(upat);
      }
    }
    setHeaders(response);
    filterChain.doFilter(request, response);
  }

  protected HttpServletResponse setHeaders(HttpServletResponse response) {
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Expose-Headers", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    return response;
  }
}
