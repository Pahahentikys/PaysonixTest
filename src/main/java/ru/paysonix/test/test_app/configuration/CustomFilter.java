package ru.paysonix.test.test_app.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {
    private static final String CUSTOM_TOKEN_HEADER = "Token";

    private final String storedHeaderToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var reqHeader = request.getHeader(CUSTOM_TOKEN_HEADER);

        if (!reqHeader.equals(storedHeaderToken)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
