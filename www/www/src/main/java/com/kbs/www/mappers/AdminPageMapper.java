package Projects.www.www.src.main.java.com.kbs.www.mappers;

import Projects.www.www.src.main.java.com.kbs.www.entities.AdminPageEntity;
import com.kbs.www.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminPageMapper {

    int insertAdminWrite(AdminPageEntity adminPage);
    UserEntity[] selectAllUser();
}
