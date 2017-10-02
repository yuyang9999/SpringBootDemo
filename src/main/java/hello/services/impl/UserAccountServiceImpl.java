package hello.services.impl;

import hello.models.DataSourcesUtility;
import hello.models.UserAccount;
import hello.services.UserAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by yangyu on 27/9/17.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private DataSource mDataSource;
    private Connection mConnection;

    public UserAccountServiceImpl() {
        mDataSource = DataSourcesUtility.loadDataSource("jdbc:mysql://localhost:3306/users?createDatabaseIfNotExist=true",
                "root", "123456", "/userScheme.sql", null);
        try {
            mConnection = mDataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private boolean isUserExists(String userName, String email) {

        try {
            String command = "select * from user_account where username=?";
            PreparedStatement pst = mConnection.prepareStatement(command);
            pst.setString(1, userName);
            //check user name
            if (pst.execute()) {
                ResultSet resultSet = pst.getResultSet();
                if (resultSet.next()) {
                    return true;
                }
            }
            //check email address
            command = "select * from user_account where email=?;";
            pst = mConnection.prepareStatement(command);
            pst.setString(1, email);
            if (pst.execute()) {
                ResultSet resultSet = pst.getResultSet();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    /**
     * make sure to call isUserExists before call this method
     * @param userName user name
     * @param email    email address
     * @param password password
     * @return true if succeed or false if failed
     */
    private boolean insertUser(String userName, String email, String password) {
        boolean ret = true;

        try {
            String command = "insert into user_account (username, password, email) values (?, ?, ?);";
            PreparedStatement pst = mConnection.prepareStatement(command);
            pst.setString(1, userName);
            pst.setString(2, password);
            pst.setString(3, email);

            if (!pst.execute()) {
                ret = false;
            }

            command = "insert into user_account_roles (username, role) values (?, ?);";
            pst = mConnection.prepareStatement(command);
            pst.setString(1, userName);
            pst.setString(2, "USER");
            if (!pst.execute()) {
                ret = false;
            }

            if (ret) {
                mConnection.commit();
            }

        } catch (SQLException e) {
            System.out.println(e);
            ret = false;
        }
        return ret;
    }

    private UserAccount getUserByFetchOneSqlRow(ResultSet rs) throws SQLException {
        int userId = rs.getInt(1);
        String name = rs.getString(2);
        String email = rs.getString(3);
        String password = rs.getString(4);

        UserAccount ret = new UserAccount(userId, name, email, password);
        return ret;
    }


    @Override
    public boolean registerUser(String userName, String email, String password) {
        //sanity check
        if (userName == null || userName.length() == 0) {
            return false;
        }

        if (email == null || email.length() == 0) {
            return false;
        }

        if (password == null || password.length() < 5) {
            return false;
        }

        if (isUserExists(userName, email)) {
            return false;
        }

        return insertUser(userName, email, password);
    }

    @Override
    public UserAccount getUserByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }

        UserAccount ret = null;
        try {
            String sql = "select * from user_account where username=?;";
            PreparedStatement pst = mConnection.prepareStatement(sql);
            pst.setString(1, userName);
            if (pst.execute()) {
                ResultSet resultSet = pst.getResultSet();
                if (resultSet.next()) {
                    ret = getUserByFetchOneSqlRow(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return ret;
    }

    @Override
    public UserAccount getUserByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return null;
        }

        UserAccount ret = null;
        try {
            String sql = "select * from user_account where email=?;";
            PreparedStatement pst = mConnection.prepareStatement(sql);
            pst.setString(1, email);

            if (pst.execute()) {
                ResultSet rs = pst.getResultSet();
                if (rs.next()) {
                    ret = getUserByFetchOneSqlRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return ret;
    }
}
