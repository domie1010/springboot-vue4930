package com.entity.model;

import com.entity.MendianEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 门店信息
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class MendianModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 门店名称
     */
    private String mendianName;


    /**
     * 门店类型
     */
    private Integer mendianTypes;


    /**
     * 门店封面
     */
    private String mendianPhoto;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 门店详情
     */
    private String mendianContent;


    /**
     * 创建时间 show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：门店名称
	 */
    public String getMendianName() {
        return mendianName;
    }


    /**
	 * 设置：门店名称
	 */
    public void setMendianName(String mendianName) {
        this.mendianName = mendianName;
    }
    /**
	 * 获取：门店类型
	 */
    public Integer getMendianTypes() {
        return mendianTypes;
    }


    /**
	 * 设置：门店类型
	 */
    public void setMendianTypes(Integer mendianTypes) {
        this.mendianTypes = mendianTypes;
    }
    /**
	 * 获取：门店封面
	 */
    public String getMendianPhoto() {
        return mendianPhoto;
    }


    /**
	 * 设置：门店封面
	 */
    public void setMendianPhoto(String mendianPhoto) {
        this.mendianPhoto = mendianPhoto;
    }
    /**
	 * 获取：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：添加时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：门店详情
	 */
    public String getMendianContent() {
        return mendianContent;
    }


    /**
	 * 设置：门店详情
	 */
    public void setMendianContent(String mendianContent) {
        this.mendianContent = mendianContent;
    }
    /**
	 * 获取：创建时间 show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间 show1 show2 photoShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
