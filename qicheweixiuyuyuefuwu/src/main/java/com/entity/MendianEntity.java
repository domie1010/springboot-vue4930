package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 门店信息
 *
 * @author 
 * @email
 */
@TableName("mendian")
public class MendianEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public MendianEntity() {

	}

	public MendianEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 门店详情
     */
    @TableField(value = "mendian_content")

    private String mendianContent;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "create_time",fill = FieldFill.INSERT)

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
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Mendian{" +
            "id=" + id +
            ", mendianName=" + mendianName +
            ", mendianTypes=" + mendianTypes +
            ", mendianPhoto=" + mendianPhoto +
            ", insertTime=" + insertTime +
            ", mendianContent=" + mendianContent +
            ", createTime=" + createTime +
        "}";
    }
}
