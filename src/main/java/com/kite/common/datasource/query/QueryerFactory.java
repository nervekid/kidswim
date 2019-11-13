package com.kite.common.datasource.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.kite.common.datasource.remarks.IRemarks;
import com.kite.common.datasource.remarks.MysqlRemarks;
import com.kite.modules.datasources.entity.MultipleDatasourceDesignDetail;

public class QueryerFactory extends QueryerFactoryBase{
    public static List<MultipleDatasourceDesignDetail> parseMetaDataColumns(String sqlText, Queryer queryer) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        List<MultipleDatasourceDesignDetail> columns = Lists.newArrayList();
        try {
            conn = queryer.getJdbcConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(preprocessSqlText(sqlText));
            ResultSetMetaData rsMataData = rs.getMetaData();
            int count = rsMataData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                MultipleDatasourceDesignDetail column = new MultipleDatasourceDesignDetail();
                column.setName(rsMataData.getColumnLabel(i));
                column.setType(rsMataData.getColumnTypeName(i));
                column.setWidth(rsMataData.getColumnDisplaySize(i)+"");
                columns.add(column);
            }
            buildRemark(conn, columns, sqlText);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            releaseJdbcResource(conn, stmt, rs);
        }
        return columns;
    }

    public  static void buildRemark(Connection conn, List<MultipleDatasourceDesignDetail> columns, String sqlText) {
        try {
            IRemarks remarks = new MysqlRemarks();
            Map<String, String> columnRemarks = remarks.getColumnRemarksBySql(conn, sqlText);
            for (int i = 0; i < columns.size(); i++) {
                MultipleDatasourceDesignDetail column = columns.get(i);
                column.setTitle(columnRemarks.get(column.getName()));
                //设置默认字段类型，减少配置工作
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
