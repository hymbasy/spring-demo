import com.alex.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class UserServiceTest {

    @Resource
    private UserService userService;
    
    @Resource
    private JedisPool jedisPool;

    @Test
    public void saveUserAndPerson() {
        userService.saveStuAndPerson();
    }
    
    @Test
    public void test(){
        Jedis jedis = jedisPool.getResource();
        jedis.set("scoh","alex");
        jedis.close();
    }
}
