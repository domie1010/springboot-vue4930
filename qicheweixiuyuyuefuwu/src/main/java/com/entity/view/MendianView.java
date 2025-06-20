package com.entity.view;

import com.entity.MendianEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 门店信息
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("mendian")
public class MendianView extends MendianEntity implements Serializable {
    private static final long serialVersionUID = 1L;

		/**
		* 门店类型的值
		*/
		private String mendianValue;



	public MendianView() {

	}

	public MendianView(MendianEntity mendianEntity) {
		try {
			BeanUtils.copyProperties(this, mendianEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 门店类型的值
			*/
			public String getMendianValue() {
				return mendianValue;
			}
			/**
			* 设置： 门店类型的值
			*/
			public void setMendianValue(String mendianValue) {
				this.mendianValue = mendianValue;
			}













}
