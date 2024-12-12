package com.kbs.www.mappers;

import com.kbs.www.entities.AdminPageEntity;
import com.kbs.www.entities.BoardPostsEntity;
import com.kbs.www.entities.UserEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminPageMapper {

    int insertAdminWrite(AdminPageEntity adminPage);

    UserEntity[] selectAllUser();

    UserEntity selectUserByEmail(@Param("userEmail") String userEmail);

    int updateWarning(UserEntity user);

    int selectUserCount();

    UserEntity[] selectUserPage(@Param(value = "limitCount") int limitCount,
                                @Param(value = "offsetCount") int offsetCount);

    int selectUserCountBySearch(@Param(value = "filter") String filter,
                                @Param(value = "keyword") String keyword);

    UserEntity[] selectUserBySearch(@Param(value = "filter") String filter,
                                    @Param(value = "keyword") String keyword,
                                    @Param(value = "limitCount") int limitCount,
                                    @Param(value = "offsetCount") int offsetCount);

    int selectBoardPostCount();

    BoardPostsEntity[] selectBoardPost(@Param(value = "limitCount") int limitCount,
                                       @Param(value = "offsetCount") int offsetCount);

    int selectBoardPostCountBySearch(@Param(value = "filter") String filter,
                                     @Param(value = "keyword") String keyword);

    BoardPostsEntity[] selectBoardPostBySearch(@Param(value = "filter") String filter,
                                               @Param(value = "keyword") String keyword,
                                               @Param(value = "limitCount") int limitCount,
                                               @Param(value = "offsetCount") int offsetCount);

}
