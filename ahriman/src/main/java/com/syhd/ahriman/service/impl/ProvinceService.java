package com.syhd.ahriman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syhd.ahriman.dao.mapper.ProvinceMapper;
import com.syhd.ahriman.dao.model.Province;

@Service
public class ProvinceService {

	@Autowired
	private ProvinceMapper provinceMapper;
	
	public Province findById(Integer id) {
		return provinceMapper.selectByPrimaryKey(id);
	}
	
}
