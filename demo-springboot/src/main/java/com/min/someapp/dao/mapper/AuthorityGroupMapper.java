package com.min.someapp.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.min.someapp.dao.model.AuthorityGroup;
import com.min.someapp.dao.model.GroupAuthority;
import com.min.someapp.dto.PageAndSort;

public interface AuthorityGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthorityGroup record);

    int insertSelective(AuthorityGroup record);

    AuthorityGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthorityGroup record);

    int updateByPrimaryKey(AuthorityGroup record);

	List<AuthorityGroup> getRecordList(@Param("filter")AuthorityGroup filter, @Param("pageAndSort")PageAndSort pageAndSort);

	long getRecordListCount(@Param("filter")AuthorityGroup filter, @Param("pageAndSort")PageAndSort pageAndSort);

	AuthorityGroup getByGroupName(String groupName);

	/**
	 * 获取角色的所有权限ID
	 * @param groupId 角色ID
	 * @return 角色的所有权限ID
	 */
	List<Integer> getAuthorities(Integer groupId);
	
	/**
	 * 清除角色的权限
	 * @param groupId 角色ID
	 * @return 影响记录数
	 */
	int clearAuthorities(Integer groupId);
	
	/**
	 * 批量添加角色权限
	 * @param list 待添加权限
	 */
	void batchInsertAuthorities(@Param("list")List<GroupAuthority> list);

	/**
	 *  获取角色的所有权限URL
	 * @param groupId 角色ID
	 * @return 角色的所有权限URL
	 */
	List<String> getAuthoritiesUrl(Integer groupId);

	/**
	 * 获取所有角色
	 * @return 包含角色ID和名称的集合
	 */
	List<AuthorityGroup> getAllRecord();
}