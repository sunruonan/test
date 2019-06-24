package com.mmall.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;

@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {
	
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IProductService iProductService;
	
	/**
	 * 新增或更新产品，若产品Id为空，则为新增；若产品Id不为空，则为更新
	 */
	@RequestMapping("save.do")
	@ResponseBody
	public ServerResponse productSave(HttpSession session,Product product){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
		}
		if(iUserService.checkAdminRole(user).isSuccess()){
			//增加产品的业务逻辑
			return iProductService.saveOrUpdateProduct(product);
		}else{
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}
	/**
	 * 产品上下架，更新产品销售的状态
	 */
	@RequestMapping("set_sale_status.do")
	@ResponseBody
	public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
		}
		if(iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.setSaleStatus(productId, status);
		}else{
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}
	/**
	 * 查看产品详情
	 * 
	 */
	@RequestMapping("detail.do")
	@ResponseBody
	public ServerResponse getDetail(HttpSession session,Integer productId,Integer status){
		User user=(User)session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
		}
		if(iUserService.checkAdminRole(user).isSuccess()){
			return iProductService.manageProductDetail(productId);
		}else{
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}
	/**
	 * 查询并分页
	 */
	@RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.getProductList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
	/**
	 * 产品搜索
	 */
	@RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
	/**
	 * 上传产品图片到服务器
	 */
}
