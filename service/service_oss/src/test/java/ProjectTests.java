import com.onlineSchool.ossService.OssApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = OssApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ProjectTests {
    @Test
    public void test01(){
        String str = new String();
        log.info(str);
    }
}
