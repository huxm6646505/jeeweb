package cn.jeeweb.web.modules.sms.service.impl;

import cn.jeeweb.common.sms.client.DhstSmsClient;
import cn.jeeweb.common.sms.client.ISmsClient;
import cn.jeeweb.common.sms.config.DhstConfigProperties;
import cn.jeeweb.common.sms.data.SmsResult;
import cn.jeeweb.web.modules.sms.entity.SmsSendLog;
import cn.jeeweb.web.modules.sms.entity.SmsTemplate;
import cn.jeeweb.web.modules.sms.service.ISmsSendLogService;
import cn.jeeweb.web.modules.sms.service.ISmsSendService;
import cn.jeeweb.web.modules.sms.service.ISmsTemplateService;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.sms.disruptor.SmsHelper;
import cn.jeeweb.common.utils.mapper.JsonMapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.modules.sms.service.impl
 * @title:
 * @description: 短信发送
 * @author: 王存见
 * @date: 2018/9/12 11:15
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Service("smsSendService")
public class SmsSendServiceImpl implements ISmsSendService {

	@Autowired
	private ISmsTemplateService smsTemplateService;
	@Autowired
	private ISmsSendLogService smsSendLogService;
	@Autowired
	private SmsHelper smsHelper; //自动注入的Bean

	@Override
	@Transactional(propagation= Propagation.NOT_SUPPORTED)
	public SmsResult send(String phone, String code, Map<String, Object> datas) {
		String[] phones = { phone };
		return send(phones,code,datas);
	}
/*
	@Override
	@Transactional(propagation= Propagation.NOT_SUPPORTED)
	public void send(String[] phones, String code, Map<String, Object> datas) {
		SmsTemplate template = smsTemplateService.selectOne(new EntityWrapper<SmsTemplate>().eq("code", code));
		if (datas == null) {
			datas = new HashMap<>();
		}
		if (template == null){
			return ;
		}
		for (String phone: phones) {
			SmsSendLog smsSendLog = new SmsSendLog();
			smsSendLog.setPhone(phone);
			smsSendLog.setTemplateName(template.getName());
			smsSendLog.setMsg("发送成功");
			smsSendLog.setSendCode(code);
			smsSendLog.setResponseDate(new Date());
			smsSendLog.setSendData(JsonMapper.toJsonString(datas));
			smsSendLog.setStatus(SmsSendLog.SMS_SEND_STATUS_IN);
			smsSendLog.setTryNum(0);
			smsSendLog.setDelFlag("0");
			smsSendLogService.insert(smsSendLog);
			// 发送邮件,这里以后需要判断类型
			smsHelper.sendAsyncSms(smsSendLog.getId(),phone,template.getTemplateContent(),null,datas);
		}
	}*/


 @Override
	@Transactional(propagation= Propagation.NOT_SUPPORTED)
	public SmsResult send(String[] phones, String code, Map<String, Object> datas) {
	SmsTemplate template = smsTemplateService.selectOne(new EntityWrapper<SmsTemplate>().eq("code", code));
	 SmsResult smsResult=null;
			if (datas == null) {
			datas = new HashMap<>();
		}
		if (template == null){
			return smsResult;
		}
//获取模板内容
    String contnet= template.getTemplateContent();
//	 String contnet="您的验证码是"+datas.get("yzm")+",有效时间为10分钟";

//替换占位符  发送内容
        String newcontnet= MessageFormat.format(contnet, datas.get("yzm"));
		for (String phone: phones) {
			ISmsClient DhstSmsClient = new DhstSmsClient();
			DhstSmsClient.init(DhstConfigProperties.buildConfigProperties());
			 smsResult = DhstSmsClient.send(phone, newcontnet,datas);
			System.out.println("提交单条普通短信响应：" + JSONObject.toJSONString(smsResult));
			SmsSendLog smsSendLog = new SmsSendLog();
			smsSendLog.setPhone(phone);
			smsSendLog.setTemplateName(template.getName());
			smsSendLog.setMsg(smsResult.getReponseData());
			smsSendLog.setSendCode(code);
			smsSendLog.setResponseDate(new Date());
			smsSendLog.setSendData(JsonMapper.toJsonString(datas));
			smsSendLog.setStatus(SmsSendLog.SMS_SEND_STATUS_IN);
			smsSendLog.setTryNum(0);
			smsSendLog.setDelFlag("0");
			smsSendLogService.insert(smsSendLog);

		}
	 return smsResult;
	}
}