package ru.paysonix.test.test_app.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.util.Objects.isNull;
import static ru.paysonix.test.test_app.common.Constants.CUSTOM_TOKEN_HEADER;

@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {
    private final String storedHeaderToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var reqHeader = request.getHeader(CUSTOM_TOKEN_HEADER);

        if (isNull(reqHeader)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        var decodedTokenFromHeader = new String(Base64.getDecoder().decode(reqHeader.getBytes(StandardCharsets.UTF_8)));

        if (!decodedTokenFromHeader.equals(storedHeaderToken)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
