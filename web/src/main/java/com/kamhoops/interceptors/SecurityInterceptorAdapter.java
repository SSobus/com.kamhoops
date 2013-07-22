package com.kamhoops.interceptors;

import com.kamhoops.data.domain.Users;
import com.kamhoops.services.UsersService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Security Interceptor Adapter
 * <p/>
 * This request interceptor ensure that the authenticated user information is always
 * added to the model/view
 */
public class SecurityInterceptorAdapter extends HandlerInterceptorAdapter {

    private UsersService userAccountService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {

            Users userAccount = userAccountService.getAuthenticatedUser();
            modelAndView.addObject("isUserAuthenticated", userAccount != null);

            if (userAccount != null) {
                modelAndView.addObject("authenticatedUser", userAccount);
                modelAndView.addObject("authenticatedUserType", userAccount.getClass().getSimpleName().toUpperCase());
            }
        }
    }

    public void setUserAccountService(UsersService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
