package com.mmall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mmall.pojo.Cart;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
    
    Cart selectCartByUserIdProductId(@Param("userId")Integer userId,@Param("productId")Integer productId);

    List<Cart> selectCartByUserId(Integer userId);
    
    int selectCartProductCheckedStatusByUserId(Integer userId);
    
    int deleteByUserIdProductIds(@Param("userId")Integer userId,@Param("productIdList")List<String> productIdList);
    
    int checkedOrUncheckedProduct(@Param("userId")Integer userId,@Param("productId")Integer productId,@Param("checked")Integer checked);
    
    //当userId为空时，返回的数量总和为null，不能赋予给int类型，所以在mapper中用IFNULL内置函数进行处理
    int selectCartProductCount(@Param("userId") Integer userId);
    
    List<Cart> selectCheckedCartByUserId(Integer userId);
}