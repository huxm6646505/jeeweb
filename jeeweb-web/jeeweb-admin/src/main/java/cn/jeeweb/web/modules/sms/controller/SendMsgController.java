package cn.jeeweb.web.modules.sms.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.sms.data.SmsResult;
import cn.jeeweb.web.modules.sms.service.ISmsSendService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//@CrossOrigin(origins ="*", maxAge =3600)
@RestController
@RequestMapping("smsapp/sendmsg")
public class SendMsgController {
    private  String code="7hch8lsi8w";
    @Autowired
    private ISmsSendService smsSendService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/sendyzm/{phone}")
    public Response sendyzm(HttpServletRequest request,@PathVariable("phone") String phone) {
       //验证码
        String yzm=createRandomVcode();
        //将短信验证码保存到session中
//        HttpSession session=request.getSession();
//        session.setAttribute(phone,yzm);
//        //设置session过期时间
//        session.setMaxInactiveInterval(1*60);

        //redis存储
        redisTemplate.opsForValue().set(phone,yzm);
        //设置过期时间
        redisTemplate.expire(phone,10, TimeUnit.MINUTES);

        Map yzmmap=new HashMap<String, String>();
        yzmmap.put("yzm",yzm);
        smsSendService.send(phone,code,yzmmap);
        return Response.ok("发送成功"+yzm);
    }



    @CrossOrigin//(allowCredentials="true",maxAge = 3600)
    @PostMapping("/sendpostyzm")
    public JSONObject sendyzm(@RequestBody JSONObject requestJson) {
        String phone= requestJson.getString("phone");
        //验证码
        String yzm=createRandomVcode();
        //将短信验证码保存到session中
/*        HttpSession session=request.getSession();
        session.setAttribute(phone,yzm);
        //设置session过期时间
        session.setMaxInactiveInterval(1*60);*/
        //redis存储
        String UUID= java.util.UUID.randomUUID().toString().replaceAll("-", "");
        Map yzmmap=new HashMap<String, String>();
        yzmmap.put("yzm",yzm);
        yzmmap.put("uuid",UUID);
        SmsResult res=smsSendService.send(phone,code,yzmmap);
        if(res.getCode()==0) {
            redisTemplate.opsForValue().set(UUID, yzm);
            //设置过期时间
            redisTemplate.expire(UUID, 2, TimeUnit.MINUTES);
        }
        return JSONObject.parseObject(JSON.toJSONString(res));
    }



    @PostMapping("/checkyzm")
    public Response checkyzm(HttpServletRequest request,@RequestBody JSONObject requestJson) {

        String phone= requestJson.getString("phone");
        String yzm= requestJson.getString("yzm");
//        HttpSession session=request.getSession();
//        String checkcodeSession =  (String) session.getAttribute(phone);

        //redis 验证
        String checkcodeSession =  redisTemplate.opsForValue().get(phone);
//        String checkcodeSession =  jedis.get(phone);
        if(checkcodeSession==null||!checkcodeSession.equals(yzm)){
            //短信验证码错误
            return Response.error("0");

        }else{
            return Response.ok("1");
        }

    }



    /**
     * 随机生成6位随机验证码
     * 方法说明
     * @Discription:扩展说明
     * @return
     * @return String
     */
    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}
