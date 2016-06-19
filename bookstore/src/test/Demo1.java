package test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.junit.Test;

public class Demo1 {

	@Test
	public void test() {
		System.out.println(2.0-1.1);
	}

	@Test
	public void test2(){
		String s = MessageFormat.format("{0}或{1}是错误的", "张三", "李四");
		System.out.println(s);
		BigDecimal b = new BigDecimal(100);
	}
	
	/**
	https://www.yeepay.com/app-merchant-proxy/node?p0_Cmd=Buy&p1_MerId=10001126856&p2_Order=123456&p3_Amt=9&p4_Cur=CNY&p5_Pid=&p6_Pcat=&p7_Pdesc=&p8_Url=http://localhost:8080/bookstore/OrderServlet?method=back&p9_SAF=&pa_MP=
	&pd_FrpId=ICBC-NET-B2C&pr_NeedResponse=1&hmac=008e95ff1b1928d243e71a47ac48790c
		 */
	@Test
	public void test3(){
		String hmac = PaymentUtil.buildHmac("Buy", "10001126856", "123456", "998", "CNY",
				"", "", "", "http://localhost:8080/bookstore/OrderServlet?method=back", 
				"", "", "ICBC-NET-B2C", "1", "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		System.out.println(hmac);
	}
}
