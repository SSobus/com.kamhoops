package com.kamhoops.interceptors;

import com.kamhoops.data.domain.UserAccount;
import com.kamhoops.services.UserAccountService;
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

    private UserAccountService userAccountService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {

            UserAccount userAccount = userAccountService.getAuthenticatedUser();
            modelAndView.addObject("isUserAuthenticated", userAccount != null);

            if (userAccount != null) {
                modelAndView.addObject("authenticatedUser", userAccount);
                modelAndView.addObject("authenticatedUserType", userAccount.getClass().getSimpleName().toUpperCase());
            }
        }
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
}
