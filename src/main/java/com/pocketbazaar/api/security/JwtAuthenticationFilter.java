// package com.pocketbazaar.api.security;

// import com.pocketbazaar.api.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import io.jsonwebtoken.Claims;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;

// import java.io.IOException;

// @Component 
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtUtil jwtUtil;
//     private final UserService userService;

//     @Autowired
//     public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
//         this.jwtUtil = jwtUtil;
//         this.userService = userService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, 
//                                     HttpServletResponse response, 
//                                     FilterChain filterChain) throws ServletException, IOException {
//         String token = getTokenFromRequest(request);  
//         if (token != null && jwtUtil.isTokenValid(token)) {
//             String email = jwtUtil.extractClaim(token, Claims::getSubject);
            

//             if (userService.isValidUser(email)) { 
//                 UsernamePasswordAuthenticationToken authentication = 
//                         new UsernamePasswordAuthenticationToken(email, null, null);
                
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             }

//         }
//         filterChain.doFilter(request, response);  
//     }

//     // Token extraction logic
//     private String getTokenFromRequest(HttpServletRequest request) {
//         String bearerToken = request.getHeader("Authorization");
//         if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//             return bearerToken.substring(7);  // Extract the token part after "Bearer "
//         }
//         return null;
//     }
// }


package com.pocketbazaar.api.security;

import com.pocketbazaar.api.service.UserService;
import com.pocketbazaar.api.util.CurrentUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        // Token validation
        if (token != null && jwtUtil.isTokenValid(token)) {
            String userEmail = jwtUtil.extractUsername(token); // Extract email from token

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userEmail); // Load user from DB

                if (userDetails != null) {
                    // Set authentication in SecurityContext
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // Set CurrentUserSession
                    CurrentUserSession.setCurrentUser(userDetails);
                }
            }
        }

        filterChain.doFilter(request, response);

        // Clear CurrentUserSession after request
        CurrentUserSession.clear();
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract the token part after "Bearer "
        }
        return null;
    }
}
