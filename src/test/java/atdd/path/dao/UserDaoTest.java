package atdd.path.dao;

import atdd.path.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static atdd.path.TestConstant.TEST_USER;
import static atdd.path.TestConstant.USER_EMAIL_1;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class UserDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    private UserDao userDao;

    private User savedUser;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);
        userDao.setDataSource(dataSource);

        savedUser = saveUser();
    }

    private User saveUser() {
        return userDao.save(TEST_USER);
    }

    @Test
    public void save() {
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(USER_EMAIL_1);
    }

    @Test
    public void findById() {
        User findUser = userDao.findById(savedUser.getId());

        assertThat(findUser.getId()).isNotNull();
        assertThat(findUser.getEmail()).isEqualTo(USER_EMAIL_1);
    }

    @Test
    public void deleteById() {
        userDao.deleteById(savedUser.getId());

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> userDao.findById(savedUser.getId()));
    }
}