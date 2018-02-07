package main.java.application.web.security.jwt;

import io.jsonwebtoken.JwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtInterceptor implements HandlerInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(JwtInterceptor.class);
    private final static String OPTIONS_METHOD = "OPTIONS";
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String CLAIMS_ATTR = "claims";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
    	LOG.info("inside handler");
    	if (handler instanceof HandlerMethod) {
            final HandlerMethod hm = (HandlerMethod) handler;
            final Method method = hm.getMethod();
            if (method.getDeclaringClass().isAnnotationPresent(Controller.class) || method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
                if (method.isAnnotationPresent(JwtAuth.class) && method.getAnnotation(JwtAuth.class).value() != null) {
                    Set<String> permissions = new HashSet<>(Arrays.asList(method.getAnnotation(JwtAuth.class).value()));
                    permissions = permissions.stream().map(role -> role.toUpperCase()).collect(Collectors.toSet());
                    for (String permission:permissions){
                    	LOG.info(permission);
                    }
                    final String token = request.getHeader(AUTHORIZATION_HEADER);
                    String role = null;
                    if (!request.getMethod().equalsIgnoreCase(OPTIONS_METHOD)) {
                        try {
                            if (token != null) {
                                role = JwtService.getRoleFromToken(token);
                            }
                            if (role == null) {
                                response.sendError(401);
                                return false;
                            }
                            if (!permissions.contains(role)) {
                                response.sendError(403);
                                return false;
                            }
                            request.setAttribute(CLAIMS_ATTR, JwtService.getClaims(token));

                        } catch (final JwtException e) {
                            response.sendError(403);
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final  HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
    }
}
