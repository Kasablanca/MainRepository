package com.syhd.ahriman.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 各货币相对人民币的汇率
 * @author MIN.LEE
 *
 */
public class ExchangeRate implements Serializable {

	private static final long serialVersionUID = 1L;

	/**美元*/
	private BigDecimal usd;
	
	/**港币*/
	private BigDecimal hkd;
	
	/**新台币*/
	private BigDecimal twd;
	
	public BigDecimal getUsd() {
		return usd;
	}
	public void setUsd(BigDecimal usd) {
		this.usd = usd;
	}
	public BigDecimal getHkd() {
		return hkd;
	}
	public void setHkd(BigDecimal hkd) {
		this.hkd = hkd;
	}
	public BigDecimal getTwd() {
		return twd;
	}
	public void setTwd(BigDecimal twd) {
		this.twd = twd;
	}
	
}
