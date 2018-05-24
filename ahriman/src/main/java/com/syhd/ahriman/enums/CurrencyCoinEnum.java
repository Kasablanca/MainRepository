package com.syhd.ahriman.enums;

/**
 * 现行货币代码枚举
 * @author MIN.LEE
 *
 */
public enum CurrencyCoinEnum {
		/**中国*/
		CHINA("CHINA","CNY")
		/**美国*/
		,AMERICA("AMERICA","USD")
		/**香港*/
		,HONGKONG("HONGKONG","HKD")
		/**台湾*/
		,TAIWAN("TAIWAN","TWD")
		/**澳门*/
		,MACAU("MACAU","")
		/**韩国*/
		,KOREA("KOREA","KRW")
		/**日本*/
		,JAPAN("JAPAN","JPY");
	
	/**国家/地区英文名*/
	public String name;
	
	/**代码*/
	public String code;
	
	private CurrencyCoinEnum(String name,String code) {
		this.name = name;
		this.code = code;
	}

}
