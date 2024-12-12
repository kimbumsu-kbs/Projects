package com.kbs.www.services;

import com.kbs.www.entities.AdminPageEntity;
import com.kbs.www.entities.BoardPostsEntity;
import com.kbs.www.entities.UserEntity;
import com.kbs.www.mappers.AdminPageMapper;
import com.kbs.www.vos.BoardPostPageVo;
import com.kbs.www.vos.UserPageVo;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class AdminPageService {
    private final AdminPageMapper adminPageMapper;

    @Autowired
    public AdminPageService(AdminPageMapper adminPageMapper) {
        this.adminPageMapper = adminPageMapper;
    }

    public Boolean write(AdminPageEntity adminPage, MultipartFile coverFile) {
        if (adminPage == null || adminPage.getTitle() == null || adminPage.getTitle().length() < 2 || adminPage.getTitle().length() > 20 ||
                adminPage.getLocation() == null || adminPage.getStartDate() == null || adminPage.getEndDate() == null || adminPage.getDescription() == null || adminPage.getDescription().isEmpty() || adminPage.getDescription().length() > 10000) {
            return false;
        }

        try {
            // 파일 데이터를 바이너리로 읽어서 엔티티에 저장
            if (coverFile != null && !coverFile.isEmpty()) {
                adminPage.setCoverData(coverFile.getBytes());
                adminPage.setCoverContentType(coverFile.getContentType());
            }
            adminPage.setCreatedAt(LocalDateTime.now());
            adminPage.setUpdatedAt(null);

            adminPage.setUserEmail("yellow6480@gmail.com");

            return this.adminPageMapper.insertAdminWrite(adminPage) > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserEntity[] read(UserEntity user) {
        if (user == null) {
            return null;
        }
        return this.adminPageMapper.selectAllUser();
    }

    public boolean updateDeleted(String userEmail) {
        UserEntity user = this.adminPageMapper.selectUserByEmail(userEmail);
        if (user == null) {
            return false;
        }
        user.setDeletedAt(LocalDateTime.now());
        user.setSuspended(true);
        return this.adminPageMapper.updateWarning(user) > 0;
    }

    public boolean updateWarning(String userEmail, int warning) {
        UserEntity user = this.adminPageMapper.selectUserByEmail(userEmail);
        if (user == null) {
            return false;
        }
        user.setWarning(warning + 1);
        return this.adminPageMapper.updateWarning(user) > 0;
    }

    public Pair<UserPageVo, UserEntity[]> selectUserPage(int page) {
        page = Math.max(page, 1);
        int totalCount = this.adminPageMapper.selectUserCount();
        UserPageVo userPageVo = new UserPageVo(page, totalCount);
        UserEntity[] user = this.adminPageMapper.selectUserPage(userPageVo.countPerPage, userPageVo.offsetCount);
        return Pair.of(userPageVo, user);
    }

    public Pair<UserPageVo, UserEntity[]> selectUserPageBySearch(String filter, String keyword, int page) {
        page = Math.max(page, 1);
        if (filter == null || (!filter.equals("email") && !filter.equals("nickname") && !filter.equals("contact") && !filter.equals("verified"))) {
            filter = "email";
        }
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }
        if (filter.equals("verified")) {
            if (keyword.equals("완료")) {
                keyword = "1";
            }
            if (keyword.equals("미완료")) {
                keyword = "";
            }
        }
        int totalCount = this.adminPageMapper.selectUserCountBySearch(filter, keyword);
        UserPageVo userPageVo = new UserPageVo(page, totalCount);
        UserEntity[] user = this.adminPageMapper.selectUserBySearch(filter, keyword, userPageVo.countPerPage, userPageVo.offsetCount);
        return Pair.of(userPageVo, user);
    }

    public Pair<BoardPostPageVo, BoardPostsEntity[]> selectBoardPost(int page) {
        page = Math.max(page, 1);
        int totalCount = this.adminPageMapper.selectBoardPostCount();
        BoardPostPageVo boardPostPageVo = new BoardPostPageVo(page, totalCount);
        BoardPostsEntity[] boardPosts = this.adminPageMapper.selectBoardPost(boardPostPageVo.countPerPage, boardPostPageVo.offsetCount);
        return Pair.of(boardPostPageVo, boardPosts);
    }

    public Pair<BoardPostPageVo, BoardPostsEntity[]> selectBoardPostBySearch(String filter, String keyword, int page) {
        page = Math.max(page, 1);
        if (filter == null || (!filter.equals("all") && !filter.equals("title") && !filter.equals("content") && !filter.equals("nickname"))) {
            filter = "all";
        }
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }
        int totalCount = this.adminPageMapper.selectBoardPostCountBySearch(filter, keyword);
        BoardPostPageVo boardPostPageVo = new BoardPostPageVo(page, totalCount);
        BoardPostsEntity[] boardPosts = this.adminPageMapper.selectBoardPostBySearch(filter, keyword, boardPostPageVo.countPerPage, boardPostPageVo.offsetCount);
        return Pair.of(boardPostPageVo, boardPosts);
    }
}