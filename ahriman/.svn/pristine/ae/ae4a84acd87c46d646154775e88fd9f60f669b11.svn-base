package com.syhd.ahriman.properties;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.enums.CurrencyCoinEnum;

/**
 * 汇率服务配置POJO
 * @author MIN.LEE
 *
 */
@Component
@ConfigurationProperties(prefix = "exchangerate")
public class ExchangeRateProperties {
	
	public static final String DEFAULT_PROTOCOL = "http";

	/**汇率系数*/
	private WebServiceProperties webservice;
	
	/**汇率系数,默认为1*/
	private Map<String,BigDecimal> ratio;
	
	public WebServiceProperties getWebservice() {
		return webservice;
	}
	public void setWebservice(WebServiceProperties webservice) {
		this.webservice = webservice;
	}
	public Map<String,BigDecimal> getRatio() {
		return ratio == null ? new HashMap<>() : ratio;
	}
	public void setRatio(Map<String,BigDecimal> ratio) {
		this.ratio = ratio;
	}
	
	/**
	 * 根据配置的汇率系数算出最终的汇率
	 * @param rate 标准汇率
	 * @return 项目汇率
	 */
	public ExchangeRate map(ExchangeRate rate) {
		BigDecimal usdRatio = ratio.get(CurrencyCoinEnum.AMERICA.code.toLowerCase());
		BigDecimal twdRatio = ratio.get(CurrencyCoinEnum.TAIWAN.code.toLowerCase());
		BigDecimal hkdRatio = ratio.get(CurrencyCoinEnum.HONGKONG.code.toLowerCase());
		BigDecimal krwRatio = ratio.get(CurrencyCoinEnum.KOREA.code.toLowerCase());
		BigDecimal jpyRatio = ratio.get(CurrencyCoinEnum.JAPAN.code.toLowerCase());
		
		if(usdRatio != null) {
			rate.setUsd(rate.getUsd().multiply(usdRatio));
		}
		if(twdRatio != null) {
			rate.setTwd(rate.getTwd().multiply(twdRatio));
		}
		if(hkdRatio != null) {
			rate.setHkd(rate.getHkd().multiply(hkdRatio));
		}
		if(krwRatio != null) {
			rate.setKrw(rate.getKrw().multiply(krwRatio));
		}
		if(jpyRatio != null) {
			rate.setJpy(rate.getJpy().multiply(jpyRatio));
		}
		
		return rate;
	}
	
	public static class WebServiceProperties {
		
		/**连接协议，如http或https*/
		private String protocol;
		
		/**连接url，如www.baidu.com*/
		private String url;
		
		/**基本的查询参数，如"name=tom&age=18"*/
		private String basequerystring;
		
		public String getProtocol() {
			return protocol == null ? DEFAULT_PROTOCOL : protocol;
		}
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getBasequerystring() {
			return basequerystring;
		}
		public void setBasequerystring(String basequerystring) {
			this.basequerystring = basequerystring;
		}
		
	}
}
