package com.yt.dao;

import com.yt.BaseTest;
import com.yt.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/9 16:03
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);

        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assert effectedNum == 2;
        assertEquals(2, effectedNum);
    }

    @Test
    public void testBQueryProductImgList() {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
        assert productImgList.size() == 2;
        System.out.println("productImgList size: " + productImgList.size());
    }

    @Test
    public void testCDeleteProductImgByProductId() {
        int effectedNum = productImgDao.deleteProductImgByProductId(1L);
        assert effectedNum == 2;
        System.out.println("effectedNum: " + effectedNum);
    }

}
