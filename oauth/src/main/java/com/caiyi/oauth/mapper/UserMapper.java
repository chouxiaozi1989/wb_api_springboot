package com.caiyi.oauth.mapper;

import com.caiyi.oauth.entities.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from oauth.userinfo")
    @Results({
            @Result(property = "userid", column = "userid")
    })
    List<UserInfo> getAll();

    @Select("select * from oauth.userinfo where userid=#{userid}")
    UserInfo getByUserId(String userid);

    @Insert({"insert into oauth.userinfo(userid) values(#{userid})"})
    void addUser(String userid);

    @Update({"update oauth.userinfo set token=#{token}, expired_time=#{expired_time} where userid=#{userid}"})
    void UpdateToken(String userid, String token, Date expired_time);

    @Update({"update oauth.userinfo set openid=#{openid} where userid=#{userid}"})
    void UpdateOpenid(String userid, String openid);

    @Update({"update oauth.userinfo set session_key=#{session_key} where userid=#{userid}"})
    void UpdateSessionKey(String userid, String session_key);

    @Delete("delete from oauth.userinfo where userid=#{userid}")
    void delete(String userid);
}
