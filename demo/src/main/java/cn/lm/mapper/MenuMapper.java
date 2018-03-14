package cn.lm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lm.model.Menu;
import cn.lm.model.MenuNode;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    List<MenuNode> getMenuTree(@Param("parentId")Integer parentId);
    
    List<MenuNode> selectAll();
    
    MenuNode getMenuNode(Integer id);
    
    int getCountByName(@Param("name")String name,@Param("parentId")Integer parentId);
}