package cn.jeeweb.web.modules.sys.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.modules.sys.entity.WxUser;
import cn.jeeweb.web.modules.sys.mapper.WxUserMapper;
import cn.jeeweb.web.modules.sys.service.IWxUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huxm on 2018/12/12
 */
@Transactional
@Service
public class  WxUserServiceImpl extends CommonServiceImpl<WxUserMapper,WxUser> implements IWxUserService{
}
