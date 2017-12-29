package com.demo.oauth2.mapper;

import com.demo.oauth2.entity.TUserInfo;
import com.demo.oauth2.entity.TUserInfoCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserInfoMapper {
    int countByExample(TUserInfoCriteria example);

    int deleteByExample(TUserInfoCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(TUserInfo record);

    int insertSelective(TUserInfo record);

    List<TUserInfo> selectByExample(TUserInfoCriteria example);

    TUserInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TUserInfo record, @Param("example") TUserInfoCriteria example);

    int updateByExample(@Param("record") TUserInfo record, @Param("example") TUserInfoCriteria example);

    int updateByPrimaryKeySelective(TUserInfo record);

    int updateByPrimaryKey(TUserInfo record);
}