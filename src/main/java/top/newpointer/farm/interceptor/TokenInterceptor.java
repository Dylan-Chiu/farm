package top.newpointer.farm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import top.newpointer.farm.service.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        Integer id = null;
//        Integer id = 805339138;//用于方便测试！！！！！！！！！！！！
        if (token != null && redisService.hasKey(token)) {
            id = (Integer) redisService.get(token);
        }
        request.getSession().setAttribute("farmerId", id);

        return true;
    }
}
