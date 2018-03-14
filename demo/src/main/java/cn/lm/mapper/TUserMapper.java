package cn.lm.mapper;

import java.util.List;

import cn.lm.model.TUser;
import cn.lm.model.vo.UserVO;

public interface TUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

	List<UserVO> getRecordList();
}