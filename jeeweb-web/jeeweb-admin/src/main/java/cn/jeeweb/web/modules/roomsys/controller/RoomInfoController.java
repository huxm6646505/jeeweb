package cn.jeeweb.web.modules.roomsys.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.roomsys.entity.ProjectInfo;
import cn.jeeweb.web.modules.roomsys.entity.RoomInfo;
import cn.jeeweb.web.modules.roomsys.service.IProjectInfoService;
import cn.jeeweb.web.modules.roomsys.service.IRoomInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author maozz11347
 * @create 2018-11-20 11:26
 **/
@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/roomsys/roominfo")
//@RequiresPathPermission("roomsys:roominfo")
@ViewPrefix("modules/roomsys/roominfo")
//@Log(title = "邮件模板")
public class RoomInfoController extends BaseBeanController<RoomInfo> {

    @Autowired
    private IRoomInfoService roomInfoService;
    @Autowired
    private IProjectInfoService proejctService;
    @GetMapping
    //@RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return displayModelAndView("list");
    }


    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    @Log(logType = LogType.SELECT)
    //@RequiresMethodPermissions("list")
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<RoomInfo> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<RoomInfo> pagejson = new PageResponse<>(roomInfoService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        System.out.println("content:"+content);
        StringUtils.printJson(response,content);
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new RoomInfo());
        List<ProjectInfo> ProjectInfo = proejctService.selectList(new EntityWrapper<ProjectInfo>());
        request.setAttribute("allProjectInfo", ProjectInfo);
        return displayModelAndView ("edit");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
   // @RequiresMethodPermissions("add")
    public Response add(RoomInfo entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        roomInfoService.insert(entity);
        return Response.ok("添加成功");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        RoomInfo entity = roomInfoService.selectById(id);
        model.addAttribute("data", entity);

        return displayModelAndView ("edit");
    }


    @PostMapping("{id}/update")
  //  @RequiresMethodPermissions("add")
    @Log(logType = LogType.UPDATE)
    public Response update(RoomInfo entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        roomInfoService.insertOrUpdate(entity);
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
  //  @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        roomInfoService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
  //  @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        roomInfoService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }

}
