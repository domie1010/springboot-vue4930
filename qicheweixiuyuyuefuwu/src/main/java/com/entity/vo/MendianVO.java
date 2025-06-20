package com.entity.vo;

import com.entity.MendianEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 门店信息
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("mendian")
public class MendianVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 门店名称
     */

    @TableField(value = "mendian_name")
    private String mendianName;


    /**
     * 门店类型
     */

    @TableField(value = "mendian_types")
    private Integer mendianTypes;


    /**
     * 门店封面
     */

    @TableField(value = "mendian_photo")
    private String mendianPhoto;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 门店详情
     */

    @TableField(value = "mendian_content")
    private String mendianContent;


    /**
     * 创建时间 show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：门店名称
	 */
    public String getMendianName() {
        return mendianName;
    }


    /**
	 * 获取：门店名称
	 */

    public void setMendianName(String mendianName) {
        this.mendianName = mendianName;
    }
    /**
	 * 设置：门店类型
	 */
    public Integer getMendianTypes() {
        return mendianTypes;
    }


    /**
	 * 获取：门店类型
	 */

    public void setMendianTypes(Integer mendianTypes) {
        this.mendianTypes = mendianTypes;
    }
    /**
	 * 设置：门店封面
	 */
    public String getMendianPhoto() {
        return mendianPhoto;
    }


    /**
	 * 获取：门店封面
	 */

    public void setMendianPhoto(String mendianPhoto) {
        this.mendianPhoto = mendianPhoto;
    }
    /**
	 * 设置：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：添加时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：门店详情
	 */
    public String getMendianContent() {
        return mendianContent;
    }


    /**
	 * 获取：门店详情
	 */

    public void setMendianContent(String mendianContent) {
        this.mendianContent = mendianContent;
    }
    /**
	 * 设置：创建时间 show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间 show1 show2 photoShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
