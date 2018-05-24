package com.syhd.ahriman.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syhd.ahriman.dto.Result;
import com.syhd.ahriman.service.impl.ExchangeRateService;

@RestController
@RequestMapping("exchangeRate")
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService exhangeRateService;
	
	@RequestMapping
	public Result getExchangeRate() {
		return exhangeRateService.getExchangeRate();
	}
	
}
