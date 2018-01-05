package com.demo.oauth2.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.oauth2.server.entity.TUserInfo;
import com.demo.oauth2.server.entity.TUserInfoCriteria;

@Mapper
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