package cn.jeeweb.web.modules.roomsys.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mvc.entity.tree.BootstrapTreeHelper;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.roomsys.entity.ProjectInfo;
import cn.jeeweb.web.modules.roomsys.service.IProjectInfoService;
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
import java.util.List;

/**
 * Created by huxm on 2018/11/30
 */
@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/roomsys/projectinfo")
//@RequiresPathPermission("roomsys:projectinfo")
@ViewPrefix("modules/roomsys/projectinfo")
//@Log(title = "邮件模板")
public class ProjectInfoController extends BaseBeanController<ProjectInfo> {
    @Autowired
    private IProjectInfoService projectInfoService;

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
        EntityWrapper<ProjectInfo> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<ProjectInfo> pagejson = new PageResponse<>(projectInfoService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        System.out.println("content:"+content);
        StringUtils.printJson(response,content);
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new ProjectInfo());
        return displayModelAndView ("edit");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    // @RequiresMethodPermissions("add")
    public Response add(ProjectInfo entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        projectInfoService.insert(entity);
        return Response.ok("添加成功");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        ProjectInfo entity = projectInfoService.selectById(id);
        model.addAttribute("data", entity);
        return displayModelAndView ("edit");
    }


    @PostMapping("{id}/update")
    //  @RequiresMethodPermissions("add")
    @Log(logType = LogType.UPDATE)
    public Response update(ProjectInfo entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        projectInfoService.insertOrUpdate(entity);
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    //  @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        projectInfoService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    //  @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        projectInfoService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }


    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "selectData")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void selectData(Queryable queryable,
                                  @RequestParam(value = "nodeid", required = false, defaultValue = "") String nodeid, HttpServletRequest request,
                                  HttpServletResponse response, PropertyPreFilterable propertyPreFilterable) throws IOException {
        EntityWrapper<ProjectInfo> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<ProjectInfo> pagejson = new PageResponse<>(projectInfoService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

}
