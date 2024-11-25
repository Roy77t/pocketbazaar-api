// package com.pocketbazaar.api.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import com.pocketbazaar.api.service.UserService;
// import io.jsonwebtoken.Claims;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;

// @Component  // Registering JwtAuthenticationFilter as a Spring Bean
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
//         String token = getTokenFromRequest(request);  // Token ko request se extract karte hain
//         if (token != null && jwtUtil.isTokenValid(token)) {
//             String email = jwtUtil.extractClaim(token, Claims::getSubject);
//             // Validate user from email (you can add your user validation logic here)
//             userService.validateUser(email);
//         }
//         filterChain.doFilter(request, response);  // Continue the filter chain
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Component  // Registering JwtAuthenticationFilter as a Spring Bean
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
        String token = getTokenFromRequest(request);  // Token ko request se extract karte hain
        if (token != null && jwtUtil.isTokenValid(token)) {
            String email = jwtUtil.extractClaim(token, Claims::getSubject);
            
            // Validate the user using the email (you can fetch user details from DB if needed)
            if (userService.isValidUser(email)) {  // You can change this logic as per your requirements
                // Create the authentication object
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(email, null, null);
                
                // Set authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);  // Continue the filter chain
    }

    // Token extraction logic
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Extract the token part after "Bearer "
        }
        return null;
    }
}
