package kr.co.sist.dao;

import kr.co.sist.util.DbConnection;
import kr.co.sist.vo.DocumentVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class DocsManagementDAO {
    private static DocsManagementDAO dmmDAO;

    public DocsManagementDAO() {

    }

    public static DocsManagementDAO getInstance() {
        if (dmmDAO == null) {
            dmmDAO = new DocsManagementDAO();
        }
        return dmmDAO;
    }

    public List<DocumentVO> searchDocument() throws SQLException {
        List<DocumentVO> list = new ArrayList<>();
        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2  " +
                     "  FROM DEPT d   " +
                     "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                     "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                     "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                     "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                     "WHERE (d.dept_code = ei.dept_code) AND (ei.emp_no = bl.emp_no) AND bl.logic = 'N' AND c1.DESCRIPTION <> '휴가신청서' " +
                     "order by bl.DOC_NO desc")) {
            createResultSet(list, pstmt);
        }
        return list;
    }

    public List<DocumentVO> selectDocInfo(String dept, String fileType, String appr) throws SQLException {
        List<DocumentVO> dlist = new ArrayList<>();
        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2   " +
                             "  FROM DEPT d   " +
                             "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                             "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                             "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                             "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                             "  WHERE bl.logic = 'N' " +
                     "  AND d.dept_name = ? AND c1.DESCRIPTION = ? AND c2.DESCRIPTION = ? AND c1.DESCRIPTION <> '휴가신청서' " +
                     " order by bl.DOC_NO desc ")) {
            pstmt.setString(1, dept);
            pstmt.setString(2, fileType);
            pstmt.setString(3, appr);
            createResultSet(dlist, pstmt);
        }
        return dlist;
    }
    /**
     * Desc : 전체 문서, 선택 문서 조회 기능
     * 작성자 : 이주희
     * 작성일 : 2024.03.28
     */
    public List<DocumentVO> selectDeptDocInfo(String dept) throws SQLException {
        List<DocumentVO> dlist = new ArrayList<>();

        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2   " +
                     "  FROM DEPT d   " +
                     "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                     "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                     "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                     "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                     "  WHERE bl.logic = 'N' " +
                     "  AND d.dept_name = ? AND c1.DESCRIPTION <> '휴가신청서' " +
                     " order by bl.DOC_NO desc ")) {
            pstmt.setString(1, dept);
            createResultSet(dlist, pstmt);
        }
        return dlist;
    }
    /**
     * Desc : 전체 문서, 선택 문서 조회 기능
     * 작성자 : 이주희
     * 작성일 : 2024.03.28
     */
    public List<DocumentVO> selectApprDocInfo(String appr) throws SQLException {
        List<DocumentVO> dlist = new ArrayList<>();

        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2   " +
                     "  FROM DEPT d   " +
                     "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                     "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                     "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                     "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                     "  WHERE bl.logic = 'N' " +
                     "  AND c2.DESCRIPTION = ? AND c1.DESCRIPTION <> '휴가신청서' " +
                     " order by bl.DOC_NO desc ")) {
            pstmt.setString(1, appr);
            createResultSet(dlist, pstmt);
        }
        return dlist;
    }
    /**
     * Desc : 전체 문서, 선택 문서 조회 기능
     * 작성자 : 이주희
     * 작성일 : 2024.03.28
     */
    public List<DocumentVO> selectFileTypeDocInfo(String fileType) throws SQLException {
        List<DocumentVO> dlist = new ArrayList<>();

        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2   " +
                     "  FROM DEPT d   " +
                     "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                     "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                     "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                     "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                     "  WHERE bl.logic = 'N' " +
                     "  AND c1.DESCRIPTION = ? AND c1.DESCRIPTION <> '휴가신청서' " +
                     " order by bl.DOC_NO desc ")) {
            pstmt.setString(1, fileType);
            createResultSet(dlist, pstmt);
        }
        return dlist;
    }
    /**
     * Desc : 전체 문서, 선택 문서 조회 기능
     * 작성자 : 이주희
     * 작성일 : 2024.03.28
     */
    public List<DocumentVO> selectDeptFileTypeDocInfo(String dept, String fileType) throws SQLException {
        List<DocumentVO> dlist = new ArrayList<>();
        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2   " +
                     "  FROM DEPT d   " +
                     "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                     "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                     "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                     "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                     "  WHERE bl.logic = 'N' " +
                     "  AND d.dept_name = ? AND c1.DESCRIPTION = ? AND c1.DESCRIPTION <> '휴가신청서' " +
                     " order by bl.DOC_NO desc ")) {
            pstmt.setString(1, dept);
            pstmt.setString(2, fileType);
            createResultSet(dlist, pstmt);
        }
        return dlist;
    }
    /**
     * Desc : 전체 문서, 선택 문서 조회 기능
     * 작성자 : 이주희
     * 작성일 : 2024.03.28
     */
    public List<DocumentVO> selectDeptApprDocInfo(String dept, String appr) throws SQLException {
        List<DocumentVO> dlist = new ArrayList<>();
        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2   " +
                     "  FROM DEPT d   " +
                     "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                     "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                     "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                     "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                     "  WHERE bl.logic = 'N' " +
                     "  AND d.dept_name = ? AND c2.DESCRIPTION = ? AND c1.DESCRIPTION <> '휴가신청서' " +
                     " order by bl.DOC_NO desc ")) {
            pstmt.setString(1, dept);
            pstmt.setString(2, appr);
            createResultSet(dlist, pstmt);
        }
        return dlist;
    }
    /**
     * Desc : 전체 문서, 선택 문서 조회 기능
     * 작성자 : 이주희
     * 작성일 : 2024.03.28
     */
    public List<DocumentVO> selectFileTypeApprDocInfo(String fileType, String appr) throws SQLException {
        List<DocumentVO> dlist = new ArrayList<>();
        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = con.prepareStatement("SELECT bl.doc_no, bl.title, d.dept_name, bl.doc_date, c1.DESCRIPTION AS description1, bl.edit_date, c2.DESCRIPTION AS description2   " +
                     "  FROM DEPT d   " +
                     "  JOIN EMP_INFO ei ON d.dept_code = ei.dept_code   " +
                     "  JOIN BUSINESS_LOG bl ON ei.emp_no = bl.emp_no   " +
                     "  LEFT JOIN COMMON c1 ON bl.grp_code = c1.grp_code AND bl.code = c1.code   " +
                     "  LEFT JOIN COMMON c2 ON bl.grp_code2 = c2.grp_code AND bl.code2 = c2.code   " +
                     "  WHERE bl.logic = 'N' " +
                     "  AND c1.DESCRIPTION = ? AND c2.DESCRIPTION = ? AND c1.DESCRIPTION <> '휴가신청서' " +
                     " order by bl.DOC_NO desc ")) {
            pstmt.setString(1, fileType);
            pstmt.setString(2, appr);
            createResultSet(dlist, pstmt);
        }
        return dlist;
    }

    public List<DocumentVO> selectInfo(String column) throws SQLException {
        List<DocumentVO> list = new ArrayList<>();
        try (Connection con = DbConnection.getCon();
             PreparedStatement pstmt = createPreparedStatement(column, con);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                DocumentVO dVO;
                if ("apprv".equals(column)) {
                    String approve = rs.getString("DESCRIPTION");
                    dVO = new DocumentVO(approve, null, null);
                } else if ("paperType".equals(column)) {
                    dVO = new DocumentVO(null, null, rs.getString("DESCRIPTION"));
                } else {
                    dVO = new DocumentVO(null, rs.getString("DEPT_NAME"), null);
                }
                list.add(dVO);
            }
        }
        return list;
    }

    private void createResultSet(List<DocumentVO> dlist, PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String docNo = rs.getString("doc_no");
                String title = rs.getString("title");
                Date docDate = rs.getDate("doc_date");
                String dept = rs.getString("dept_name");
                Date modifiedDate = rs.getDate("edit_date");

                String workDesc = rs.getString("description1");
                String apprDesc = rs.getString("description2");

                DocumentVO dVO = new DocumentVO(docNo, title, workDesc, apprDesc, dept, docDate, modifiedDate);
                dlist.add(dVO);
            }
        }
    }

    private PreparedStatement createPreparedStatement(String column, Connection connection) throws SQLException {
        String query;
        if ("apprv".equals(column)) {
            query = "SELECT DESCRIPTION FROM COMMON WHERE GRP_CODE = 'APPR'";
        } else if ("paperType".equals(column)) {
            query = "SELECT DESCRIPTION FROM COMMON WHERE GRP_CODE = 'WORK'";
        } else {
            query = "SELECT DEPT_NAME FROM DEPT WHERE DEPT_NAME NOT LIKE '시스템관리자'";
        }
        return connection.prepareStatement(query);
    }
}
