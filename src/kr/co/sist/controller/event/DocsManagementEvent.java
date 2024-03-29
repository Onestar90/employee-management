package kr.co.sist.controller.event;

import kr.co.sist.dao.DocsListDAO;
import kr.co.sist.dao.DocsManagementDAO;
import kr.co.sist.dao.VacationStatusDAO;
import kr.co.sist.view.admin.AdminMenu;
import kr.co.sist.view.admin.ConfirmDocs;
import kr.co.sist.view.admin.DocsManagement;
import kr.co.sist.view.user.Reject;
import kr.co.sist.vo.DocumentVO;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class DocsManagementEvent extends WindowAdapter implements ActionListener, MouseListener {
    private DocsManagement dmm;

    public DocsManagementEvent(DocsManagement dmm) {
        this.dmm = dmm;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == dmm.getJbtnBackhome()) {
            dmm.dispose();
            new AdminMenu();
        }

        if (ae.getSource() == dmm.getJbtnSearch()) {
            try {
                selectDocument();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (ae.getSource() == dmm.getJbtnSelect()) {
            displaySelectedDoc();
        }
    }

    /**
     * Desc : 조회 버튼 클릭 시 선택된 행의 문서 결재 창 출력
     * 작성자 : 이주희
     * 작성일 : 2024.03.27
     */
    private void displaySelectedDoc() {
        int selectedRow = dmm.getJtaDob().getSelectedRow();
        String docNum = dmm.getDtmjtabResult().getValueAt(selectedRow, 0).toString();
        try {
            new ConfirmDocs(DocsListDAO.getInstance().selectDocinfo(docNum));
            dmm.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    public void searchDocument() throws SQLException {
//        Object[] content = new Object[7];
//        DocsManagementDAO dmmDAO = DocsManagementDAO.getInstance();
//        List<DocumentVO> dVOList = dmmDAO.searchDocument();
//
//        if (dVOList.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "해당 문서가 없습니다");
//        } else {
//            createContents(content, dVOList);
//        }
//    }// searchDocument
//
    private void createContents(Object[] content, List<DocumentVO> dVOList) {
        for (DocumentVO dVO : dVOList) {
            content[0] = dVO.getDocNo();
            content[1] = dVO.getTitle();
            content[2] = dVO.getWorkDesc();
            content[3] = dVO.getDept();
            content[4] = dVO.getDocDate();
            content[5] = dVO.getApprDesc();
            content[6] = dVO.getDocDate();

            dmm.getDtmjtabResult().addRow(content);
        }
    }

    /**
     * Desc : 전체 문서, 선택 문서 조회 기능
     * 작성자 : 이주희
     * 작성일 : 2024.03.28
     */
    public void selectDocument() throws SQLException {
        String dept = dmm.getJcbSelectDep().getSelectedItem().toString();
        String fileType = dmm.getJcbSelectFileType().getSelectedItem().toString();
        String appr = dmm.getJcbSelectApprovalState().getSelectedItem().toString();

        boolean isAll = dept.equals("전체") && fileType.equals("전체") && appr.equals("전체");
        boolean isDept = !dept.equals("전체") && fileType.equals("전체") && appr.equals("전체");
        boolean isFileType = dept.equals("전체") && !fileType.equals("전체") && appr.equals("전체");
        boolean isAppr = dept.equals("전체") && fileType.equals("전체") && !appr.equals("전체");
        boolean isDeptFileType = !dept.equals("전체") && !fileType.equals("전체") && appr.equals("전체");
        boolean isDeptAppr = !dept.equals("전체") && fileType.equals("전체") && !appr.equals("전체");
        boolean isFileTypeAppr = dept.equals("전체") && !fileType.equals("전체") && !appr.equals("전체");

        DocsManagementDAO dmDAO = DocsManagementDAO.getInstance();
        List<DocumentVO> dVOList;
        dmm.getDtmjtabResult().setRowCount(0);

        Object[] content = new Object[7];

        if (isAll) {
            dVOList = dmDAO.searchDocument();
        } else if (isDept){
            dVOList = dmDAO.selectDeptDocInfo(dept);
        } else if (isFileType){
            dVOList = dmDAO.selectFileTypeDocInfo(fileType);
        } else if (isAppr){
            dVOList = dmDAO.selectApprDocInfo(appr);
        } else if (isDeptFileType){
            dVOList = dmDAO.selectDeptFileTypeDocInfo(dept, fileType);
        } else if (isDeptAppr){
            dVOList = dmDAO.selectDeptApprDocInfo(dept, appr);
        } else if (isFileTypeAppr){
            dVOList = dmDAO.selectFileTypeApprDocInfo(fileType, appr);
        } else {
            dVOList = dmDAO.selectDocInfo(dept, fileType, appr);
        }
        createContents(content, dVOList);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        dmm.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int column = dmm.getJtaDob().columnAtPoint(me.getPoint());
        int row = dmm.getJtaDob().rowAtPoint(me.getPoint());

        if (dmm.getJtaDob().getValueAt(row, column) == null) {
            return;
        }

        String item = dmm.getJtaDob().getValueAt(row, column).toString();
        String DocNum = (String) dmm.getJtaDob().getValueAt(row, 0);

        if (item.equals("반려")) {
            try {
                Reject(DocNum);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void Reject(String doc_no) throws SQLException {
        VacationStatusDAO vsDAO = VacationStatusDAO.getInstance();
        String regetDetail = vsDAO.selectRejetDetail(doc_no);

        new Reject(dmm, regetDetail);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }
}
