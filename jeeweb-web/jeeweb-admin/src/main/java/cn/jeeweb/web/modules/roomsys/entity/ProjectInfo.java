package cn.jeeweb.web.modules.roomsys.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * Created with IDEA
 *
 * @author huxm
 * @create 2018-11-30 10:06
 **/
@TableName("project_info")
public class ProjectInfo extends DataEntity<String> {


    /**主键*/
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**编号*/
    @TableId(value = "number")
    private String number;
    /**name*/
    @TableId(value = "name")
    private String name;
    /**所属地址*/
    @TableId(value = "adress")
    private String adress;




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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

}
