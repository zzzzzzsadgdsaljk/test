package test;


import kesun.entity.web.WebSite;
import kesun.util.PathUtil;
import kesun.util.PropertyUtil;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.crypto.CryptoException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Created by wph-pc on 2017/11/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationContext.xml","classpath:ApplicationContext-dataSource.xml"})
public class AssetServiceImplTest {

    @Test
    public void getLoadoutExcelColumns() throws Exception {
        PropertyUtil res = new PropertyUtil("dictionary.properties");
        System.out.println("路径："+res.getProperty("dict.degree"));
    }

}