package com.spring.project.config;

import com.spring.project.constant.ApplicationConstants;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>CSRF - Important<b/>
 * <br/>
 * <p>This binding class adds -csrf token into response header, so client can access it<p/>
 */
public class CsrfTokenResponseHeaderBindingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain filterChain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute(ApplicationConstants.CSRF_HEADERS.REQUEST_ATTRIBUTE_NAME.getValue());

        if (token != null) {
            //response.setHeader(ApplicationConstants.CSRF_HEADERS.RESPONSE_HEADER_NAME.getValue(), token.getHeaderName());
            //response.setHeader(ApplicationConstants.CSRF_HEADERS.RESPONSE_PARAM_NAME.getValue(), token.getParameterName());
            response.setHeader(ApplicationConstants.CSRF_HEADERS.RESPONSE_TOKEN_NAME.getValue(), token.getToken());
        }

        filterChain.doFilter(request, response);
    }

}