package com.syhd.ahriman.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.dao.model.UserGroup;
import com.syhd.ahriman.dto.PageAndSort;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User getByAccName(String accName);

	List<User> getUserList(@Param("filter")User filter, @Param("pageAndSort")PageAndSort pageAndSort);

	long getUserListCount(@Param("filter")User filter, @Param("pageAndSort")PageAndSort pageAndSort);

	int updateLastLoginTime(User user);

	/**
	 * 获取用户所具有的角色
	 * @param id 用户ID
	 * @return 角色ID集合
	 */
	List<Integer> getAuthorityGroupId(Integer id);

	/**
	 * 批量添加用户角色
	 * @param list 待添加数据
	 */
	void batchInsertAuthorityGroups(@Param("list")List<UserGroup> list);
	
	/**
	 * 清空用户角色
	 * @param id 用户ID
	 * @return 影响记录数
	 */
	int clearGroups(Integer id);

	/**
	 * 获取用户所具有的权限
	 * @param id 用户ID
	 * @return 权限URI集合
	 */
	List<String> getAuthoritiesUrl(Integer id);
}