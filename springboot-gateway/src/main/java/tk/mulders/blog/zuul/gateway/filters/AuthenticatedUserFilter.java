package tk.mulders.blog.zuul.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class AuthenticatedUserFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        final HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        final Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            findSessionCookie(cookies)
                    .map(this::findSession)
                    .ifPresent(this::addAuthenticationHeaders);
        }

        return null;
    }

    private Map<String, String> findSession(final Cookie cookie) {
        return Collections.singletonMap("username", "dummy");
    }

    private void addAuthenticationHeaders(final Map<String, String> authentication) {
        final RequestContext requestContext = RequestContext.getCurrentContext();
        authentication.forEach(requestContext::addZuulRequestHeader);
    }

    private Optional<Cookie> findSessionCookie(final Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "sessionId".equalsIgnoreCase(cookie.getName()))
                .findFirst();
    }
}
