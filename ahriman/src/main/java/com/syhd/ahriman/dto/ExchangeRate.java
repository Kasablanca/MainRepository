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
	
	/**人民币，基准货币*/
	private BigDecimal cny = BigDecimal.ONE;

	/**美元*/
	private BigDecimal usd;
	
	/**港币*/
	private BigDecimal hkd;
	
	/**新台币*/
	private BigDecimal twd;
	
	/**英镑*/
	private BigDecimal gbp;
	
	/**日元*/
	private BigDecimal jpy;
	
	/**韩元*/
	private BigDecimal krw;
	
	/**新加坡元*/
	private BigDecimal sgd;
	
	/**欧元*/
	private BigDecimal eur;
	
	/**加拿大元*/
	private BigDecimal cad;
	
	/**泰铢*/
	private BigDecimal thb;
	
	/**菲律宾比索*/
	private BigDecimal php;
	
	/**俄罗斯卢布*/
	private BigDecimal sur;
	
	/**印度卢比*/
	private BigDecimal inr;
	
	public BigDecimal getCny() {
		return cny;
	}
	public void setCny(BigDecimal cny) {
		this.cny = cny;
	}
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
	public BigDecimal getGbp() {
		return gbp;
	}
	public void setGbp(BigDecimal gbp) {
		this.gbp = gbp;
	}
	public BigDecimal getJpy() {
		return jpy;
	}
	public void setJpy(BigDecimal jpy) {
		this.jpy = jpy;
	}
	public BigDecimal getKrw() {
		return krw;
	}
	public void setKrw(BigDecimal krw) {
		this.krw = krw;
	}
	public BigDecimal getSgd() {
		return sgd;
	}
	public void setSgd(BigDecimal sgd) {
		this.sgd = sgd;
	}
	public BigDecimal getEur() {
		return eur;
	}
	public void setEur(BigDecimal eur) {
		this.eur = eur;
	}
	public BigDecimal getCad() {
		return cad;
	}
	public void setCad(BigDecimal cad) {
		this.cad = cad;
	}
	public BigDecimal getThb() {
		return thb;
	}
	public void setThb(BigDecimal thb) {
		this.thb = thb;
	}
	public BigDecimal getPhp() {
		return php;
	}
	public void setPhp(BigDecimal php) {
		this.php = php;
	}
	public BigDecimal getSur() {
		return sur;
	}
	public void setSur(BigDecimal sur) {
		this.sur = sur;
	}
	public BigDecimal getInr() {
		return inr;
	}
	public void setInr(BigDecimal inr) {
		this.inr = inr;
	}
	
	public static ExchangeRate getDefaultRate() {
		ExchangeRate rate = new ExchangeRate();
		rate.setUsd(new BigDecimal("6.6129999"));
		rate.setHkd(new BigDecimal("0.8146190"));
		rate.setTwd(new BigDecimal("0.2135190"));
		rate.setKrw(new BigDecimal("0.0059340"));
		rate.setJpy(new BigDecimal("0.0583850"));
		
		return rate;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("0:").append(cny.toPlainString());
		
		if(usd != null) {
			builder.append(",1:").append(usd.toPlainString());
		}
		if(twd != null) {
			builder.append(",2:").append(twd.toPlainString());
		}
		if(hkd != null) {
			builder.append(",3:").append(hkd.toPlainString());
		}
		if(krw != null) {
			builder.append(",4:").append(krw.toPlainString());
		}
		if(jpy != null) {
			builder.append(",5:").append(jpy.toPlainString());
		}
		/*if(gbp != null) {
			builder.append(",3:").append(gbp.toPlainString());
		}
		if(sgd != null) {
			builder.append(",3:").append(sgd.toPlainString());
		}
		if(eur != null) {
			builder.append(",3:").append(eur.toPlainString());
		}
		if(cad != null) {
			builder.append(",3:").append(cad.toPlainString());
		}
		if(thb != null) {
			builder.append(",3:").append(thb.toPlainString());
		}
		if(php != null) {
			builder.append(",3:").append(php.toPlainString());
		}
		if(sur != null) {
			builder.append(",3:").append(sur.toPlainString());
		}
		if(inr != null) {
			builder.append(",3:").append(inr.toPlainString());
		}*/
		return builder.toString();
	}
	
}
