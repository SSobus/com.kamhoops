package com.kamhoops.interceptors;

import com.kamhoops.view.BuildInformationView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Build Information Interceptor Adapter
 * <p/>
 * Attach build information to all requests by attaching it to the model/view (if available)
 */
public class BuildInformationInterceptorAdapter extends HandlerInterceptorAdapter {
    private BuildInformationView buildInformationView = null;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.addObject("git", buildInformationView);
        }
    }

    public void setBuildInformationView(BuildInformationView buildInformationView) {
        this.buildInformationView = buildInformationView;
    }
}
