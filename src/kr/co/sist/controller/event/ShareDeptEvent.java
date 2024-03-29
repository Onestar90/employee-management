package kr.co.sist.controller.event;

import kr.co.sist.dao.ShareDeptDAO;
import kr.co.sist.view.admin.ConfirmDocs;
import kr.co.sist.view.admin.DocsManagement;
import kr.co.sist.view.admin.ShareDept;
import kr.co.sist.vo.DocumentVO;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ShareDeptEvent extends WindowAdapter implements ActionListener, MouseListener {

    ShareDept dept;
    ConfirmDocs cd;

    public ShareDeptEvent(ShareDept dept) {
        this.dept = dept;
    }


    @Override
    public void mouseClicked(MouseEvent me) {

        if (me.getSource() == dept.getJlDept()) {

            int index = dept.getJlDept().getSelectedIndex();
            if (index != 1) {
                String strdept = (String) dept.getDlmDept().getElementAt(index);
                dept.getDlmSelectedDept().addElement(strdept);
                dept.getDlmDept().remove(index);
            }
        }//end if
        if (me.getSource() == dept.getJlSelectedDept()) {
            int index = dept.getJlSelectedDept().getSelectedIndex();
            if (index != 1) {
                String strdept = (String) dept.getDlmSelectedDept().getElementAt(index);
                dept.getDlmDept().addElement(strdept);
                dept.getDlmSelectedDept().remove(index);

            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == dept.getJbtncancel()) {
            dept.dispose();
            new DocsManagement();
        }
        if (ae.getSource() == dept.getJbtncheck()) {
            try {
                Object[] selectedDept =
                        dept.getDlmSelectedDept().toArray();
                if(selectedDept.length==0) {
                	JOptionPane.showMessageDialog(null, "하나 이상의 부서를 선택하세요.");
                	return;
                }
                addSharedDoc(selectedDept);
                JOptionPane.showMessageDialog(null, "공유되었습니다.");
                dept.dispose();
                new DocsManagement();
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSharedDoc(Object[] selectedDept) throws NumberFormatException, SQLException {
    	String docNum = ConfirmDocs.getDocNum();
//        int index = dept.getJlDept().getSelectedIndex();

        String strdept = (String) dept.getDlmSelectedDept().get(0);
        System.out.println(strdept);
        try {
            ShareDeptDAO sdDAO = ShareDeptDAO.getInstance();
            DocumentVO dVO = new DocumentVO();
            for (int i = 0; i < selectedDept.length; i++) {
                dVO.setDocNo(docNum);
                dVO.setDept(selectedDept[i].toString());
                sdDAO.shareDoc(dVO);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        dept.dispose();
    }
}
