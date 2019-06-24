package com.mmall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService{

	@Autowired
	private ShippingMapper shippingMapper;
	
	public ServerResponse add(Integer userId,Shipping shipping) {
		shipping.setUserId(userId);
		int rowCount=shippingMapper.insert(shipping);
		if(rowCount>0) {
			Map result=Maps.newHashMap();
			//新建完之后需要将shipping的id返回给前端
			result.put("shippingId", shipping.getId());
			return ServerResponse.createBySuccess("新建地址成功", result);
		}
		return ServerResponse.createByErrorMessage("新建地址失败");
	}
	
	public ServerResponse<String> del(Integer userId,Integer shippingId){
		int resultCount=shippingMapper.deleteByShippingIdUserId(userId, shippingId);
		if(resultCount>0) {
			return ServerResponse.createBySuccessMessage("删除地址成功");
		}
		return ServerResponse.createByErrorMessage("删除地址失败");
	}
	
	public ServerResponse update(Integer userId,Shipping shipping) {
		//需要重新给shipping的userId赋值，防止直接用传过来的shipping里的userId会更新别人的地址
		shipping.setUserId(userId);
		int rowCount=shippingMapper.updateByShipping(shipping);
		if(rowCount>0) {
			return ServerResponse.createBySuccessMessage("更新地址成功");
		}
		return ServerResponse.createByErrorMessage("更新地址失败");
	}
	
	public ServerResponse<Shipping> select(Integer userId,Integer shippingId) {
		Shipping shipping=shippingMapper.selectByShippingIdUserId(userId, shippingId);
		if(shipping==null) {
			return ServerResponse.createByErrorMessage("无法查询到该地址");
		}
		return ServerResponse.createBySuccess("查询成功", shipping);
	}
	
	public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Shipping> shippingList=shippingMapper.selectByUserId(userId);
		PageInfo pageInfo=new PageInfo(shippingList);
		return ServerResponse.createBySuccess(pageInfo);
	}
}
