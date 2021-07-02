package mybatisGenerator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglisheng on 2019/5/25
 */
public class MysqlUtil {
    protected static Logger logger = LoggerFactory.getLogger(MysqlUtil.class);

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }
    }

    public static void close(Connection conn, ResultSet rs, Statement s) {
        closeConnection(conn);
        clostResultSet(rs);
        clostStatement(s);
    }

    public static Connection getConnection(String url,String user,String passwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                url,
                user,
                passwd);
        return conn;
    }

    public static void clostResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }
    }

    public static void clostStatement(Statement s) {
        if (s != null) {
            try {
                s.close();
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }
    }

    /**
     * 依据数据库名查询,查询该库的数据表列表
     *
     * @return List<String> 表名集合
     */
    public static List<String> getTables(String url,String user,String passwd,String database) throws Exception {
        String sql = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA= ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<String>();
        try {
            conn = getConnection(url, user, passwd);
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, database);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("TABLE_NAME"));
            }
        } finally {
            close(conn, rs, pstm);
        }
        return list;
    }
    public static List<String> getColumns(String url,String user,String passwd,String tableName) throws Exception {
        String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<String>();
        try {
            conn = getConnection(url, user, passwd);
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, tableName);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("COLUMN_NAME"));
            }
        } finally {
            close(conn, rs, pstm);
        }
        return list;
    }

    public static List<PageData> queryBySql(String url,String user,String passwd, String sql,boolean clearLongData) throws Exception {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<PageData> result = new ArrayList<>();
        try {
            conn = getConnection(url, user, passwd);
            pstm = conn.prepareStatement(sql);
            logger.info("sql:" + sql);
            rs = pstm.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int filedCount = rsmd.getColumnCount();

            while (rs.next()) {
                PageData valueMap = new PageData();
                int i = 0;
                while (++i <= filedCount) {
                    valueMap.put(rsmd.getColumnLabel(i), rs.getString(rsmd.getColumnLabel(i)));
                }
                result.add(valueMap);
                if (clearLongData) {
                    clearLongData(result);
                }
            }
        }finally {
            close(conn, rs, pstm);
        }
        return result;
    }

    public static void clearLongData(List<PageData> datas) {
        for (PageData data : datas) {
            for (Map.Entry<Object, Object> entry : data.entrySet()) {
                if (entry.getValue() != null && String.valueOf(entry.getValue()).length() > 200) {
                    entry.setValue("未知");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
//
//        List<PageData> users = queryBySql(datasource, "select * from  test_tmp",false);
//        for (PageData data : users) {
//            System.out.println("=================");
//            for (Map.Entry<Object, Object> entry : data.entrySet()) {
//                System.out.println(String.valueOf(entry.getKey()) + ":" + String.valueOf(entry.getValue()));
//            }
//        }
    }
}
