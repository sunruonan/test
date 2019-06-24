package com.mmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVO;
import com.mmall.vo.ProductListVo;
@Service("iProductService")
public class ProductServiceImpl implements IProductService{

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ICategoryService iCategoryService;
	
	public ServerResponse saveOrUpdateProduct(Product product){
		if(product!=null){
			if(StringUtils.isNotBlank(product.getSubImages())){
				String[] subImageArray=product.getSubImages().split(",");
				if(subImageArray.length>0){
					//把主图设置为子图的第一个
					product.setMainImage(subImageArray[0]);
				}
			}
			if(product.getId()!=null){
				//更新
				int rowCount=productMapper.updateByPrimaryKey(product);
				if(rowCount>0){
					return ServerResponse.createBySuccessMessage("更新产品成功");
				}
				return ServerResponse.createByErrorMessage("更新产品失败");
			}else{
				//新增
				int rowCount=productMapper.insert(product);
				if(rowCount>0){
					return ServerResponse.createBySuccessMessage("新增产品成功");
				}
				return ServerResponse.createByErrorMessage("新增产品失败");
			}
		}
		return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
	}
	
	public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
		if(productId==null||status==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		Product product=new Product();
		product.setId(productId);
		product.setStatus(status);
		int rowCount=productMapper.updateByPrimaryKeySelective(product);
		if(rowCount>0){
			return ServerResponse.createBySuccessMessage("修改产品销售状态成功");
		}
		return ServerResponse.createByErrorMessage("修改产品销售状态失败");
	}
	
	public ServerResponse<ProductDetailVO> manageProductDetail(Integer productId){
		if(productId==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		Product product=productMapper.selectByPrimaryKey(productId);
		if(product==null){
			return ServerResponse.createByErrorMessage("产品已下架或者删除");
		}
		ProductDetailVO productDetailVO=assembleProductDetailVO(product);
		return ServerResponse.createBySuccess(productDetailVO);
	}
	
	private ProductDetailVO assembleProductDetailVO(Product product){
		ProductDetailVO productDetailVO=new ProductDetailVO();
		productDetailVO.setId(product.getId());
		productDetailVO.setSubtitle(product.getSubtitle());
		productDetailVO.setPrice(product.getPrice());
		productDetailVO.setMainImage(product.getMainImage());
		productDetailVO.setSubImages(product.getSubImages());
		productDetailVO.setCategoryId(product.getCategoryId());
		productDetailVO.setDetail(product.getDetail());
		productDetailVO.setName(product.getName());
		productDetailVO.setStatus(product.getStatus());
		productDetailVO.setStock(product.getStock());
		productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
		Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
		if(category==null){
			productDetailVO.setParentCategoryId(0);//默认根节点
		}else{
			productDetailVO.setParentCategoryId(category.getParentId());
		}
		productDetailVO.setCreateTime(DateTimeUtil.dateToString(product.getCreateTime()));
		productDetailVO.setUpdateTime(DateTimeUtil.dateToString(product.getUpdateTime()));
		return productDetailVO;
	}
	
	public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }
    
    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize){
    	PageHelper.startPage(pageNum, pageSize);
    	if(StringUtils.isNotBlank(productName)) {
    		productName=new StringBuilder().append("%").append(productName).append("%").toString();
    	}
    	List<Product> productList=productMapper.selectByNameAndProductId(productName, productId);
    	List<ProductListVo> productListVoList=Lists.newArrayList();
    	for(Product productItem:productList) {
    		ProductListVo productListVo=assembleProductListVo(productItem);
    		productListVoList.add(productListVo);
    	}
    	PageInfo pageResult=new PageInfo(productList);
    	pageResult.setList(productListVoList);
    	return ServerResponse.createBySuccess(pageResult);
    }
    
    public ServerResponse<ProductDetailVO> getProductDetail(Integer productId){
    	if(productId==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
		}
		Product product=productMapper.selectByPrimaryKey(productId);
		if(product==null){
			return ServerResponse.createByErrorMessage("产品已下架或者删除");
		}
		if(product.getStatus()!=Const.ProductStatusEnum.ON_SALE.getCode()){
			return ServerResponse.createByErrorMessage("产品已下架或者删除");
		}
		ProductDetailVO productDetailVO=assembleProductDetailVO(product);
		return ServerResponse.createBySuccess(productDetailVO);
    }
    
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy){
    	if(StringUtils.isBlank(keyword)&&categoryId==null){
    		return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    	}
    	//当传的分类是一个大分类时，底下还有子分类，此时需要调用之前的递归算法，把该分类下所有的分类查出一起返回
    	List<Integer> categoryIdList=new ArrayList<Integer>();
    	if(categoryId!=null){
    		Category category=categoryMapper.selectByPrimaryKey(categoryId);
    		if(category==null&&StringUtils.isBlank(keyword)){
    			//没有该分类，并且还没有关键字，这个时候返回一个空的结果集，不报错
    			PageHelper.startPage(pageNum, pageSize);
    			List<ProductListVo> productListVoList=Lists.newArrayList();
    			PageInfo pageInfo=new PageInfo(productListVoList);
    			return ServerResponse.createBySuccess(pageInfo);
    		}
    		categoryIdList=iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
    	}
    	if(StringUtils.isNotBlank(keyword)){
    		keyword=new StringBuilder().append("%").append(keyword).append("%").toString();
    	}
    	PageHelper.startPage(pageNum,pageSize);
    	//排序处理
    	if(StringUtils.isNotBlank(orderBy)){
    		if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
    			String[] orderByArray=orderBy.split("_");
    			PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
    		}
    	}
    	List<Product> productList=productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword, categoryIdList.size()==0?null:categoryIdList);
    	List<ProductListVo> productListVoList=Lists.newArrayList();
    	for(Product product:productList){
    		ProductListVo productListVo=assembleProductListVo(product);
    		productListVoList.add(productListVo);
    	}
    	PageInfo pageInfo=new PageInfo(productList);
    	pageInfo.setList(productListVoList);
    	return ServerResponse.createBySuccess(pageInfo);
    }
}
