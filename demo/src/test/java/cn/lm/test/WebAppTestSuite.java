package cn.lm.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.lm.model.TUser;
import cn.lm.service.impl.MenuServiceImpl;
import cn.lm.utils.ParamValidateUtil;

@WebAppConfiguration
//@TestPropertySource("classpath:config.company.properties")
//@DirtiesContext()
//@TestExecutionListeners
@RunWith(SpringRunner.class)
//@Commit
//@Rollback
@ContextConfiguration({"classpath:spring.xml","classpath:spring-mvc.xml",
	"classpath:spring-remote.xml"})
public class WebAppTestSuite {
/*	
	@BeforeTransaction
	public void beforeTransaction(){
		
	}
	
	@AfterTransaction
	public void afterTransaction(){
		
	}
	
	@Timed(millis=1000*30)
	public void Timed(){
		
	}
	
	@Repeat(value=5)
	public void Repeat(){
		
	}*/
	
	//@Test
	public void testValidate(){
		TUser user=new TUser();
		List<String> validationMessage = ParamValidateUtil.validate(user);
		System.out.println(validationMessage);
	}
	
	@SuppressWarnings("unused")
	@Autowired
	private MenuServiceImpl menuService;
	
	@Test
	public void testDownloadExcel() throws FileNotFoundException, IOException {
		//menuService.getDownloadExcel().write(new FileOutputStream("F:/test.xls"));
	}
	
}
