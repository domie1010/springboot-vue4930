package com.dao;

import com.entity.MendianEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.MendianView;

/**
 * 门店信息 Dao 接口
 *
 * @author 
 */
public interface MendianDao extends BaseMapper<MendianEntity> {

   List<MendianView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
