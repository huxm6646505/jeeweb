package cn.jeeweb.web.modules.roomsys.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.modules.roomsys.entity.RoomInfo;
import cn.jeeweb.web.modules.roomsys.mapper.RoomInfoMapper;
import cn.jeeweb.web.modules.roomsys.service.IRoomInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IDEA
 *
 * @author maozz11347
 * @create 2018-11-20 11:10
 **/
@Transactional
@Service
public class RoomInfoServiceImpl extends CommonServiceImpl<RoomInfoMapper,RoomInfo> implements IRoomInfoService {
}
