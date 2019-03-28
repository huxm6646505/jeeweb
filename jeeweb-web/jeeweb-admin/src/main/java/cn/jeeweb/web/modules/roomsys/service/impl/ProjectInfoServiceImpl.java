package cn.jeeweb.web.modules.roomsys.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.modules.roomsys.entity.ProjectInfo;
import cn.jeeweb.web.modules.roomsys.mapper.ProjectInfoMapper;
import cn.jeeweb.web.modules.roomsys.service.IProjectInfoService;
import cn.jeeweb.web.modules.roomsys.service.IRoomInfoService;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huxm on 2018/11/30
 */
@Transactional
@Service
public class ProjectInfoServiceImpl extends CommonServiceImpl<ProjectInfoMapper,ProjectInfo> implements IProjectInfoService {
}
