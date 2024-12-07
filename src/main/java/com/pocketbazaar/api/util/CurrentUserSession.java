// package com.pocketbazaar.api.util;


// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;

// public class CurrentUserSession {

//     /**
//      * Fetch the current user's email or username from SecurityContext.
//      *
//      * @return Current user's email or username
//      */
//     public static String getCurrentUser() {
//         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//         if (principal instanceof UserDetails) {
//             // If principal is an instance of UserDetails, extract the username (email or username)
//             return ((UserDetails) principal).getUsername();
//         } else {
//             // If principal is not UserDetails, fallback to principal's string representation
//             return principal.toString();
//         }
//     }
    
// }

package com.pocketbazaar.api.util;

import org.springframework.security.core.userdetails.UserDetails;

public class CurrentUserSession {

    private static final ThreadLocal<UserDetails> currentUserThreadLocal = new ThreadLocal<>();

    /**
     * Set the current user's details in the ThreadLocal.
     *
     * @param userDetails The authenticated user's details
     */
    public static void setCurrentUser(UserDetails userDetails) {
        currentUserThreadLocal.set(userDetails);
    }

    /**
     * Get the current user's details from the ThreadLocal.
     *
     * @return The authenticated user's details
     */
    public static UserDetails getCurrentUserDetails() {
        return currentUserThreadLocal.get();
    }

    /**
     * Clear the ThreadLocal after the request is processed.
     */
    public static void clear() {
        currentUserThreadLocal.remove();
    }
}
