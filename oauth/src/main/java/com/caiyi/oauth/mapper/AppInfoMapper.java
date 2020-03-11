package com.caiyi.oauth.mapper;

import com.caiyi.oauth.entities.AppInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppInfoMapper {
    @Select("select * from oauth.appinfo")
    @Results({
            @Result(column = "appid", property = "appId"),
            @Result(column = "app_key", property = "key"),
            @Result(column = "yxbz", property = "yxbz")
    })
    List<AppInfo> getAll();

    @Select("select * from oauth.appinfo where appid=#{appid} and app_key=#{key} and yxbz='1'")
    @Results({@Result(column = "appid", property = "appId"),
            @Result(column = "app_key", property = "key"),
            @Result(column = "yxbz", property = "yxbz")})
    AppInfo getByAppidAndKey(String appid, String key);
}
