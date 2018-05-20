package com.syhd.ahriman.dao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.syhd.ahriman.dao.mapper") //扫描该包下面的所有接口为mapper，不加该注解则需要在每个接口上添加"@Mapper"注解
public class DaoConfig {

}
