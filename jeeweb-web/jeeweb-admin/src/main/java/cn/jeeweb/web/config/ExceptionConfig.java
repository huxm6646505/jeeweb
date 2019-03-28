package cn.jeeweb.web.config;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.utils.ServletUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * Created with IDEA
 *
 * @author maozz11347
 * @create 2018-11-16 14:53
 **/
@RestControllerAdvice
public class ExceptionConfig {

    /**
     * 捕捉UnauthorizedException
     * @return
     */
    @ExceptionHandler({ShiroException.class,UnauthorizedException.class, UnauthenticatedException.class})
    public Object handle401() {
        if (ServletUtils.isAjax()) {
            return Response.error(401, "此操作未授权");
        }else{
            ModelAndView m = new ModelAndView();
            m.setViewName("/error/401");
            return m;
        }
    }

    /**
     * 捕捉其他所有异常
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object globalException(HttpServletRequest request, Throwable ex) {
        if (ServletUtils.isAjax()) {
            return Response.error(getStatus(request).value(), "此操作找不到了，请联系管理员");
        }else{
            int status = getStatus(request).value();
            if (status==400 || status==401. || status==403 || status==404. || status==500) {
                ModelAndView m = new ModelAndView();
                m.setViewName("/error/" + status);
                return m;
            }else{
                ModelAndView m = new ModelAndView();
                m.setViewName("/error/500");
                return m;
            }
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
