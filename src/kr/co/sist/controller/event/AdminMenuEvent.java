package kr.co.sist.controller.event;

import kr.co.sist.dao.LoginDAO;
import kr.co.sist.view.admin.AdminMenu;
import kr.co.sist.view.admin.CheckEmployeeInformation;
import kr.co.sist.view.admin.DocsManagement;
import kr.co.sist.view.admin.WorkStatus;
import kr.co.sist.view.common.UpdatePassword;
import kr.co.sist.vo.LoginVO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Desc : 관리자 로그인 메뉴 화면에 보이는 내용의 이벤트처리<br>
 * 작성일 : 2024.03.18<br>
 * 작성자 : 고한별<br>
 * 수정일 : 2024.03.28<br>
 * 수정자 : 고한별<br>
 */
public class AdminMenuEvent extends WindowAdapter implements ActionListener {
    private final AdminMenu adminMenu;

    public AdminMenuEvent(AdminMenu adminMenu) {
        this.adminMenu = adminMenu;
    }

    /**
     * Desc : 각각의 버튼에 연결되는 이벤트 관리
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == adminMenu.getEmployeeInformationJbtn()){
            closeFrame();
            new CheckEmployeeInformation();
        }
        if (e.getSource() == adminMenu.getWorkAttendanceJbtn()){
            try {
                closeFrame();
                new WorkStatus();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == adminMenu.getDocumentsJbtn()){
            closeFrame();
            new DocsManagement();
        }
        if (e.getSource() == adminMenu.getCloseJbtn()){
            closeFrame();
        }
        if (e.getSource() == adminMenu.getPasswordJbtn()){
            new UpdatePassword
                    (new LoginVO(
                            LoginEvent.getEmpno(),
                            LoginDAO.getInstance().confirmUser
                                    (LoginEvent.getEmpno()).getPassword()
                    ));
            closeFrame();
        }
    }

    /**
     * Desc : 타이틀 바의 x 누를 경우 창 닫기 기능
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        closeFrame();
    }

    /**
     * Desc : 창 닫기
     */
    private void closeFrame(){
        adminMenu.dispose();
    }
}
