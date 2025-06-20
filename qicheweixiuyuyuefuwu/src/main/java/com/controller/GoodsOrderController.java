
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 商品订单
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/goodsOrder")
public class GoodsOrderController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsOrderController.class);

    @Autowired
    private GoodsOrderService goodsOrderService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private YonghuService yonghuService;



    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = goodsOrderService.queryPage(params);

        //字典表数据转换
        List<GoodsOrderView> list =(List<GoodsOrderView>)page.getList();
        for(GoodsOrderView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        GoodsOrderEntity goodsOrder = goodsOrderService.selectById(id);
        if(goodsOrder !=null){
            //entity转view
            GoodsOrderView view = new GoodsOrderView();
            BeanUtils.copyProperties( goodsOrder , view );//把实体数据重构到view中

                //级联表
                GoodsEntity goods = goodsService.selectById(goodsOrder.getGoodsId());
                if(goods != null){
                    BeanUtils.copyProperties( goods , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setGoodsId(goods.getId());
                }
                //级联表
                YonghuEntity yonghu = yonghuService.selectById(goodsOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody GoodsOrderEntity goodsOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,goodsOrder:{}",this.getClass().getName(),goodsOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            goodsOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        goodsOrder.setInsertTime(new Date());
        goodsOrder.setCreateTime(new Date());
        goodsOrderService.insert(goodsOrder);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody GoodsOrderEntity goodsOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,goodsOrder:{}",this.getClass().getName(),goodsOrder.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            goodsOrder.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<GoodsOrderEntity> queryWrapper = new EntityWrapper<GoodsOrderEntity>()
            .eq("id",0)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        GoodsOrderEntity goodsOrderEntity = goodsOrderService.selectOne(queryWrapper);
        if(goodsOrderEntity==null){
            goodsOrderService.updateById(goodsOrder);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        goodsOrderService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<GoodsOrderEntity> goodsOrderList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            GoodsOrderEntity goodsOrderEntity = new GoodsOrderEntity();
//                            goodsOrderEntity.setGoodsId(Integer.valueOf(data.get(0)));   //商品 要改的
//                            goodsOrderEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            goodsOrderEntity.setGoodsOrderTime(sdf.parse(data.get(0)));          //预约时间 要改的
//                            goodsOrderEntity.setGoodsOrderTypes(Integer.valueOf(data.get(0)));   //预约状态 要改的
//                            goodsOrderEntity.setInsertTime(date);//时间
//                            goodsOrderEntity.setCreateTime(date);//时间
                            goodsOrderList.add(goodsOrderEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        goodsOrderService.insertBatch(goodsOrderList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = goodsOrderService.queryPage(params);

        //字典表数据转换
        List<GoodsOrderView> list =(List<GoodsOrderView>)page.getList();
        for(GoodsOrderView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        GoodsOrderEntity goodsOrder = goodsOrderService.selectById(id);
            if(goodsOrder !=null){


                //entity转view
                GoodsOrderView view = new GoodsOrderView();
                BeanUtils.copyProperties( goodsOrder , view );//把实体数据重构到view中

                //级联表
                    GoodsEntity goods = goodsService.selectById(goodsOrder.getGoodsId());
                if(goods != null){
                    BeanUtils.copyProperties( goods , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setGoodsId(goods.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(goodsOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody GoodsOrderEntity goodsOrder, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,goodsOrder:{}",this.getClass().getName(),goodsOrder.toString());
            GoodsEntity goodsEntity = goodsService.selectById(goodsOrder.getGoodsId());
            if(goodsEntity == null){
                return R.error(511,"查不到该商品信息");
            }
            // Double goodsNewMoney = goodsEntity.getGoodsNewMoney();

            if(false){
            }
            else if(goodsEntity.getGoodsNewMoney() == null){
                return R.error(511,"商品信息价格不能为空");
            }

            //计算所获得积分
            Double buyJifen =0.0;
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            YonghuEntity yonghuEntity = yonghuService.selectById(userId);
            if(yonghuEntity == null)
                return R.error(511,"用户不能为空");
            if(yonghuEntity.getNewMoney() == null)
                return R.error(511,"用户金额不能为空");
            double balance = yonghuEntity.getNewMoney() - goodsEntity.getGoodsNewMoney()*1;//余额
            if(balance<0)
                return R.error(511,"余额不够支付");
            goodsOrder.setGoodsOrderTypes(1); //设置订单状态为已支付
            goodsOrder.setYonghuId(userId); //设置订单支付人id
            goodsOrder.setInsertTime(new Date());
            goodsOrder.setCreateTime(new Date());
                goodsOrderService.insert(goodsOrder);//新增订单
            yonghuEntity.setNewMoney(balance);//设置金额
            yonghuService.updateById(yonghuEntity);
            return R.ok();
    }

    /**
    * 退款
    */
    @RequestMapping("/refund")
    public R refund(Integer id, HttpServletRequest request){
        logger.debug("refund方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        String role = String.valueOf(request.getSession().getAttribute("role"));

            GoodsOrderEntity goodsOrder = goodsOrderService.selectById(id);
            Integer buyNumber = 1;
            Integer goodsOrderPaymentTypes = 1;
            Integer goodsId = goodsOrder.getGoodsId();
            if(goodsId == null)
                return R.error(511,"查不到该商品信息");
            GoodsEntity goodsEntity = goodsService.selectById(goodsId);
            if(goodsEntity == null)
                return R.error(511,"查不到该商品信息");
            Double goodsNewMoney = goodsEntity.getGoodsNewMoney();
            if(goodsNewMoney == null)
                return R.error(511,"商品信息价格不能为空");

            Integer userId = (Integer) request.getSession().getAttribute("userId");
            YonghuEntity yonghuEntity = yonghuService.selectById(userId);
            if(yonghuEntity == null)
                return R.error(511,"用户不能为空");
            if(yonghuEntity.getNewMoney() == null)
                return R.error(511,"用户金额不能为空");

            Double zhekou = 1.0;


            //判断是什么支付方式 1代表余额 2代表积分
            if(goodsOrderPaymentTypes == 1){//余额支付
                //计算金额
                Double money = goodsEntity.getGoodsNewMoney() * buyNumber  * zhekou;
                //计算所获得积分
                Double buyJifen = 0.0;
                yonghuEntity.setNewMoney(yonghuEntity.getNewMoney() + money); //设置金额
            }

            goodsOrder.setGoodsOrderTypes(2);//设置订单状态为退款
            goodsOrderService.updateById(goodsOrder);//根据id更新
            yonghuService.updateById(yonghuEntity);//更新用户信息
            goodsService.updateById(goodsEntity);//更新订单中商品信息的信息
            return R.ok();
    }


    /**
     * 发货
     */
    @RequestMapping("/deliver")
    public R deliver(Integer id ){
        logger.debug("refund:,,Controller:{},,ids:{}",this.getClass().getName(),id.toString());
        GoodsOrderEntity  goodsOrderEntity = new  GoodsOrderEntity();;
        goodsOrderEntity.setId(id);
        goodsOrderEntity.setGoodsOrderTypes(3);
        boolean b =  goodsOrderService.updateById( goodsOrderEntity);
        if(!b){
            return R.error("操作出错");
        }
        return R.ok();
    }

















}
