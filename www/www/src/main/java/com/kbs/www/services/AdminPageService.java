package Projects.www.www.src.main.java.com.kbs.www.services;

import Projects.www.www.src.main.java.com.kbs.www.entities.AdminPageEntity;
import com.kbs.www.entities.UserEntity;
import Projects.www.www.src.main.java.com.kbs.www.mappers.AdminPageMapper;
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

    public UserEntity[] read() {
        return this.adminPageMapper.selectAllUser();
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
}