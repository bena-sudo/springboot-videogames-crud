package edu.alumno.videogames.security.security.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.alumno.videogames.security.service.JwtService;
import edu.alumno.videogames.security.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsServiceImpl userDetailsService;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = req.getHeader("Authorization");
    final String jwt;
    final String nickname;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(req, res);
      return;
    }
    jwt = authHeader.substring(7);
    try {
      nickname = jwtService.getNicknameUsuarioFromToken(jwt);

      if (nickname != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    } catch (MalformedJwtException e) {
      logger.error("Token mal formado");
    } catch (UnsupportedJwtException e) {
      logger.error("Token no soportado");
    } catch (ExpiredJwtException e) {
      logger.error("Token expirado");
    } catch (IllegalArgumentException e) {
      logger.error("Token vacío");
    } catch (SecurityException e) {
      logger.error("Fallo en la firma");
    }
    filterChain.doFilter(req, res);
  }
}
