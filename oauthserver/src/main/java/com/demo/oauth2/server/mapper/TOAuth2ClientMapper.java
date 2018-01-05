package com.demo.oauth2.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.oauth2.server.entity.TOAuth2Client;
import com.demo.oauth2.server.entity.TOAuth2ClientCriteria;

@Mapper
public interface TOAuth2ClientMapper {
    int countByExample(TOAuth2ClientCriteria example);

    int deleteByExample(TOAuth2ClientCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(TOAuth2Client record);

    int insertSelective(TOAuth2Client record);

    List<TOAuth2Client> selectByExample(TOAuth2ClientCriteria example);

    TOAuth2Client selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TOAuth2Client record, @Param("example") TOAuth2ClientCriteria example);

    int updateByExample(@Param("record") TOAuth2Client record, @Param("example") TOAuth2ClientCriteria example);

    int updateByPrimaryKeySelective(TOAuth2Client record);

    int updateByPrimaryKey(TOAuth2Client record);
}