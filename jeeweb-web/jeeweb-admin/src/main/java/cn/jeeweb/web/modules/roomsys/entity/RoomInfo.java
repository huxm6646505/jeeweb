package cn.jeeweb.web.modules.roomsys.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * Created with IDEA
 *
 * @author maozz11347
 * @create 2018-11-20 10:21
 **/
@TableName("room_info")
public class RoomInfo extends DataEntity<String> {

    /**主键*/
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**编号*/
    @TableId(value = "number")
    private String number;
    /**面积*/
    @TableId(value = "area")
    private String area;
    /**租金*/
    @TableId(value = "rent")
    private String rent;

    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @TableField(value = "project_by", el = "project.id", fill = FieldFill.DEFAULT)
    private ProjectInfo project;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }
}
