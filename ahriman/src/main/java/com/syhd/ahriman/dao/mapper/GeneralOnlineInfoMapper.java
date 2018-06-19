package com.syhd.ahriman.dao.mapper;

import com.syhd.ahriman.dao.model.GeneralOnlineInfo;

public interface GeneralOnlineInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GeneralOnlineInfo record);

    int insertSelective(GeneralOnlineInfo record);

    GeneralOnlineInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GeneralOnlineInfo record);

    int updateByPrimaryKey(GeneralOnlineInfo record);
}