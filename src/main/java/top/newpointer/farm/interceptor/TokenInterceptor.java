package top.newpointer.farm.interceptor;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.newpointer.farm.service.RedisService;
import top.newpointer.farm.utils.Message;
import top.newpointer.farm.utils.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        Integer id = null;
        if (token != null && redisService.hasKey(token)) {
            id = (Integer) redisService.get(token);
            request.getSession().setAttribute("farmerId", id);
            return true;
        } else {
            Message message = new Message();
            message.setState(Status.NO_LOGIN);
            response.getWriter().write(message.toString());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
