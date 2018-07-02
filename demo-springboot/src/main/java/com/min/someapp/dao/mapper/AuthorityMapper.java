package com.min.someapp.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.min.someapp.dao.model.Authority;
import com.min.someapp.dto.ZTreeNode;

public interface AuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    /**
     * 查询所有权限
     * @return 所有权限的列表
     */
    List<ZTreeNode> getAllNodes();

    /**
     * 根据权限名和父权限ID查询数量
     * @param name 权限名
     * @param parentId 父权限ID
     * @param parentId 权限ID
     * @return 权限名记录数
     */
	int selectCountByName(@Param("name")String name,@Param("parentId")Integer parentId,@Param("id")Integer id);
}