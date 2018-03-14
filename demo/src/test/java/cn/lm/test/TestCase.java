package cn.lm.test;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.aop.interceptor.AsyncExecutionInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.commonj.TimerManagerTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import com.alibaba.fastjson.JSON;

import cn.lm.aspect.UsageTracked;
import cn.lm.mapper.MenuMapper;
import cn.lm.model.MenuNode;
import cn.lm.model.TUser;
import cn.lm.model.User;
import cn.lm.utils.ParamValidateUtil;

public class TestCase {
	
	public static void main(String[] args) throws Exception {
		
	}
	
	public static void getTreeTest() throws IOException {
		SqlSession session = getSqlSession();
		MenuMapper menuMapper = session.getMapper(MenuMapper.class);
		List<MenuNode> menuNodes = menuMapper.getMenuTree(null);
		processNode(menuNodes,null);
		print(menuNodes);
	}
	
	private static void processNode(List<MenuNode> menuNodes, MenuNode parent) {
		for(MenuNode node:menuNodes) {
			node.setParent(parent);
			if(parent==null)
				node.setPath(node.getText());
			else
				node.setPath((node.getParent()).getText()+MenuNode.PATH_DELIMITER+node.getText());
		}
		for(MenuNode node:menuNodes)
			processNode(node.getChildren(),node);
	}
	
	private static void print(List<MenuNode> menuNodes) {
		for(MenuNode node:menuNodes)
			System.out.println(node);
		for(MenuNode node:menuNodes)
			print(node.getChildren());
	}

	public static void testJava() throws IOException, EncryptedDocumentException, InvalidFormatException{
		Workbook wb = WorkbookFactory.create(new FileInputStream("D:/Resource/dropdown.xls"));
		Sheet sheet0=wb.createSheet();
		CellRangeAddressList addressList = new CellRangeAddressList(0, 2, 0, 2);
		DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint("INDIRECT($N1)");
		DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
		dataValidation.setSuppressDropDownArrow(false);
		sheet0.addValidationData(dataValidation);
		wb.write(new BufferedOutputStream(new FileOutputStream("D:/Resource/dropdown2.xls")));
		wb.close();
	}

	public static void testValidate(){
		TUser user=new TUser();
		getSpringContext();
		List<String> validationMessage = ParamValidateUtil.validate(user);
		System.out.println(validationMessage);
	}

	public static void testImport() throws Exception {
		InputStream is = new FileInputStream("D:/Chrome/student-import1821(1).xls");
		Workbook wb = WorkbookFactory.create(is);

		Sheet sheet0 = wb.getSheetAt(0);
		System.out.println("getLastRowNum:"+sheet0.getLastRowNum());
		System.out.println("getFirstRowNum:"+sheet0.getFirstRowNum());
		Iterator<Row> it = sheet0.rowIterator();
		while(it.hasNext()){
			Row row = it.next();
			System.out.print("getRowNum"+row.getRowNum()+",");
			System.out.println(row);
		}
	}

	@SuppressWarnings("unused")
	public static void testExecutor() {
		ApplicationContext ctx = getSpringContext();
		TaskExecutor executor = ctx.getBean(ThreadPoolTaskExecutor.class);
		executor.execute(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				System.out.println(new Date().toLocaleString());
			}

		});
		Thread thread = new Thread();
		//Trigger trigger;
		TaskScheduler sch;
		JobDetailFactoryBean j;
		TimerManagerTaskScheduler t;
		ThreadPoolTaskScheduler ts;
		AsyncExecutionInterceptor a;
	}

	public static void activemq() {
		ActiveMQConnectionFactory factory;
		javax.jms.Connection conn;
		Session session;
		Destination dest;
		MessageProducer producer;
		factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
		try {
			conn = factory.createConnection();
			conn.start();
			session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			dest = session.createQueue("test-queue");
			producer = session.createProducer(dest);
			//producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			TUser user = new TUser();
			for(int i=0;i<20;i++) {
				user.setId(UUID.randomUUID().toString());
				producer.send(session.createObjectMessage(user));
			}
			session.commit();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testWebService() {
		@SuppressWarnings("unused")
		ApplicationContext ctx = getSpringContext();
	}

	public static void restTemplate(){

	}

	/**
	 * 生成32位md5散列码<br> 
	 * @param input 输入字符串<br>
	 * @return
	 */
	public static String string2MD5(String input){  
		MessageDigest md5 = null;  
		try{  
			md5 = MessageDigest.getInstance("MD5");  
		}catch (Exception e){  
			e.printStackTrace();  
			return "";  
		}  
		byte[] md5Bytes = md5.digest(input.getBytes());  
		StringBuffer hexValue = new StringBuffer();  
		for (int i = 0; i < md5Bytes.length; i++){  
			int val = ((int) md5Bytes[i]) & 0xff;  
			if (val < 16)  
				hexValue.append("0");  
			hexValue.append(Integer.toHexString(val));  
		}  
		return hexValue.toString();
	} 

	public static void redis(){
		ApplicationContext ctx = getSpringContext();
		@SuppressWarnings("unchecked")
		RedisTemplate<String,Object> redis = ctx.getBean(RedisTemplate.class);
		ValueOperations<String, Object> ops = redis.opsForValue();
		/*User user = new User();
		user.setId(123);
		user.setUsername("MR.RIGHT");*/
		String userStr = (String) ops.get("limin_test_key");
		User user = JSON.parseObject(userStr, User.class);
		System.out.println(user);
		/*		redis.multi();

		redis.execute(new RedisCallback<Object>(){
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.decr(new byte[]{1,2,3});
				return null;
			}
		});

		redis.discard();*/
	}

	/**
	 * 测试方法注入
	 */
	public static void test5(){
/*		ApplicationContext ctx = getSpringContext();
		Account bean = (Account) ctx.getBean("cn.lm.model.Account");
		Account bean2 = (Account) ctx.getBean("cn.lm.model.Account");
		System.out.println(bean.getRole() == bean2.getRole());*/
	}

	/**
	 * 测试spring配置
	 */
	public static void test4(){
		ApplicationContext ctx = getSpringContext();
		UsageTracked track =  (UsageTracked) ctx.getBean("testServiceImpl");
		track.incrementUseCount();

	}

	/**
	 * 测试spring环境里的mybatis
	 */
	public static void test3(){
/*		ApplicationContext ctx = getSpringContext();
		AccountMapper mapper = ctx.getBean(AccountMapper.class);
		System.out.println(mapper.getAccountById("111"));*/
	}

	/**
	 * 测试jdbc环境里的mybatis
	 * @throws SQLException
	 */
	public static void test2() throws SQLException{
		Connection conn = getJdbcConnection();
		CallableStatement callStmt = conn.prepareCall("{call getAccountById(?)}");
		callStmt.setString(1, "111");
		ResultSet result = callStmt.executeQuery();
		while(result.next()){
			System.out.println("account id :"+result.getString(1)+",username: "+result.getString(2));
		}
		while(callStmt.getMoreResults()){
			result = callStmt.getResultSet();
			while(result.next()){
				System.out.println("1 :"+result.getString(1)+",2: "+result.getString(2));
			}
		}

		conn.close();
	}

	/**
	 * 测试原生mybatis
	 * @throws IOException
	 */
	public static void test1() throws IOException{
/*		SqlSession session = getSqlSession();
		AccountMapper accountMapper = session.getMapper(AccountMapper.class);
		Account account = accountMapper.getAccountById("fc1be03bb7684857b5259dff3f8dcdd9");
		System.out.println(account.getStatus());*/
	}

	/**
	 * 获取spring上下文环境
	 * @return
	 */
	public static ApplicationContext getSpringContext(){
		return new ClassPathXmlApplicationContext("spring.xml");
	}

	/**
	 * 获取mybatis连接
	 * @return
	 * @throws IOException
	 */
	public static SqlSession getSqlSession() throws IOException{
		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		return sqlSessionFactory.openSession();
	}

	/**
	 * 获取jdbc连接
	 * @return
	 */
	public static Connection getJdbcConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://192.168.2.210:3306/itpms", "itpms", "itpms");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
