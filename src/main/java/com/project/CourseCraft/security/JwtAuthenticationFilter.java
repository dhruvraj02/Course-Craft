package com.project.CourseCraft.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request     The HTTP servlet request.
     * @param response    The HTTP servlet response.
     * @param filterChain The filter chain for the request.
     * @throws ServletException If an exception occurs during the filter processing.
     * @throws IOException      If an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Exclude specific URLs from authentication
        if ("/auth/login".equals(request.getRequestURI()) || "/v3/api-docs/**".equals(request.getRequestURI()) || "/swagger-ui/**".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get the token from the request header
        String requestToken = request.getHeader("Authorization");

        String userID = null;
        String token = null;

        // Check if the token exists and starts with "Bearer"
        if (requestToken != null && requestToken.startsWith("Bearer")) {

            // Extract the token without the "Bearer" prefix
            token = requestToken.substring(7);

            try {
                // Get the username (userID) from the token
                userID = this.jwtTokenHelper.getUsernameFromToken(token);
                System.out.println(userID + " UserID");
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT");
            }
        } else {
            System.out.println("JWT token does not begin with Bearer");
        }

        // Once we have the token, we validate it
        if (userID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details by username (userID)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userID);

            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // Perform authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Invalid JWT token");
            }
        } else {
            System.out.println("Username is null or context is not null!!");
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
