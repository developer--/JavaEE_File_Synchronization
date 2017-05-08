package repository.manager;

import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpSession;

/**
 * Created by root on 5/4/17.
 */
public class MySessionManager {
    public static boolean isValidLoginSession(@Nullable final HttpSession session){
        return session != null && session.getAttribute("username") != null && session.getAttribute("password") != null;
    }
}
