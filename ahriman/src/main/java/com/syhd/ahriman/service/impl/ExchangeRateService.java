package com.syhd.ahriman.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syhd.ahriman.dto.ExchangeRate;
import com.syhd.ahriman.dto.JuHeResponse;
import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.enums.CurrencyCoinEnum;
import com.syhd.ahriman.properties.ExchangeRateProperties;

@Service
@CacheConfig(cacheNames="exchangeRate")
public class ExchangeRateService {

	@Autowired
	private ExchangeRateProperties exchangeRateConfig;
	
	@Autowired
	private ObjectMapper jackson;
	
	@Autowired
	private CacheManager cacheManager;
	
	
	/**
	 * 查询实时利率
	 * @return 包含类型为{@link com.syhd.ahriman.dto.ExchangeRate}的利率
	 */
	@Cacheable(sync=true,key="#root.method")
	public Result getExchangeRate() {
		Result result = Result.getSuccessResult();
		
		if(StringUtils.isEmpty(exchangeRateConfig.getUrl())) {
			//使用模拟数据
			ExchangeRate rate = new ExchangeRate();
			rate.setUsd(new BigDecimal("6"));
			rate.setHkd(new BigDecimal("0.8"));
			rate.setTwd(new BigDecimal("0.2"));
			result.setData(rate);
			return result;
		}
		
		Result usd = getExchangeRate(CurrencyCoinEnum.AMERICA);
		Result hkd = getExchangeRate(CurrencyCoinEnum.HONGKONG);
		Result twd = getExchangeRate(CurrencyCoinEnum.TAIWAN);
		
		if(Result.isSuccessResult(usd) && Result.isSuccessResult(hkd) && Result.isSuccessResult(twd)) {
			ExchangeRate rate = new ExchangeRate();
			rate.setUsd((BigDecimal) usd.getData());
			rate.setHkd((BigDecimal) hkd.getData());
			rate.setTwd((BigDecimal) twd.getData());
			
			result.setData(rate);
			return result;
		}
		
		result = Result.getErrorResult();
		if(!Result.isSuccessResult(usd)) {
			result.setMessage(usd.getMessage());
		} else if(!Result.isSuccessResult(hkd)) {
			result.setMessage(hkd.getMessage());
		} else if(!Result.isSuccessResult(twd)) {
			result.setMessage(twd.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 查询目标货币对人民币的汇率
	 * @param coin 目标货币
	 * @return 汇率
	 * @exception java.lang.IllegalArgumentException 说明参数错误
	 */
	private Result getExchangeRate(CurrencyCoinEnum coin) {
		if(CurrencyCoinEnum.CHINA.equals(coin)) {
			throw new IllegalArgumentException("无效的来源货币类型");
		};
		
		Result result = Result.getErrorResult();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(exchangeRateConfig.getProtocol())
				.append("://")
				.append(exchangeRateConfig.getUrl())
				.append("?")
				.append(exchangeRateConfig.getBasequerystring())
				.append("&from=")
				.append(coin.code)
				.append("&to=")
				.append(CurrencyCoinEnum.CHINA.code);
			
			HttpResponse response = Request.Get(builder.toString()).execute().returnResponse();
			int status = response.getStatusLine().getStatusCode();
			if(status < 200 || status >= 300) {
				// 连接服务器失败
				result.setMessage("连接服务器失败");
				return result;
			} else {
				// 连接服务器成功
				String responseText = EntityUtils.toString(response.getEntity());
				JuHeResponse data = jackson.readValue(responseText, JuHeResponse.class);
				if(Integer.valueOf(0).equals(data.getError_code())) {
					// 服务器返回成功
					// 查询结果见网址：https://www.juhe.cn/docs/api/id/80
					@SuppressWarnings("unchecked")
					List<Map<String,String>> list = (List<Map<String, String>>) data.getResult();
					BigDecimal exchangeRate = null;
					for(Map<String,String> item : list) {
						if(coin.code.equals(item.get("currencyT"))) {
							exchangeRate = new BigDecimal(item.get("exchange"));
							break;
						}
					}
					if(exchangeRate == null) {
						// 说明返回格式已经变化
						result.setMessage("汇率API有变化");
						return result;
					} else {
						result = Result.getSuccessResult();
						result.setData(exchangeRate);
						return result;
					}
				} else {
					// 服务器返回错误
					result.setMessage(data.getReason());
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			result.setMessage("读取汇率信息时发生错误");
			return result;
		}
	}
	
	@Scheduled(cron="0 0 0 * * ?")
	public void dailyTask() {
		cacheManager.getCache("exchangeRate").clear(); // 手动清除缓存
	}
}
