package kr.co.sist.controller.event;

import kr.co.sist.dao.LoginDAO;
import kr.co.sist.dao.UpdatePasswordDAO;
import kr.co.sist.view.admin.AdminMenu;
import kr.co.sist.view.common.UpdatePassword;
import kr.co.sist.view.user.UserMenu;
import kr.co.sist.vo.LoginVO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class UpdatePasswordEvent extends WindowAdapter implements ActionListener {
    private UpdatePassword up;

    public UpdatePasswordEvent(UpdatePassword up) {
        this.up = up;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == up.getExitButton()) {
            LoginVO loginVO = LoginDAO.getInstance().confirmUser(LoginEvent.getEmpno());
            String authCode = loginVO.getAuthCode();

            if (authCode.equals("ADMIN") || authCode.equals("SUPER")) {
                new AdminMenu();
            } else if (authCode.equals("USER")) {
                new UserMenu();
            } else {
                JOptionPane.showMessageDialog(up, "유효하지 않은 인증 코드입니다.");
            }
            up.dispose();
        }//end if

        if (ae.getSource() == up.getUpdateButton()) {
            LoginVO loginVO = LoginDAO.getInstance().confirmUser(LoginEvent.getEmpno());
            String authCode = loginVO.getAuthCode();
            String pass = up.getJtfUpdatePw().getText().trim();

            if(pass.isBlank()){
                JOptionPane.showMessageDialog(up,"변경할 비밀번호를 입력하세요.");
                return;
            }

            if(pass.equals(loginVO.getPassword())){
                JOptionPane.showMessageDialog(up,"변경할 비밀번호는 현재 비밀번호와 달라야합니다.");
                return;
            }

            modifyPassword();

            if (authCode.equals("ADMIN") || authCode.equals("SUPER")) {
                new AdminMenu();
            } else if (authCode.equals("USER")) {
                new UserMenu();
            } else {
                JOptionPane.showMessageDialog(up, "유효하지 않은 인증 코드입니다.");
            }
            up.dispose();
        }
    }//actionPerformed

    /**
     * 로그인한 사원의 비밀번호를 변경하는 method
     */
    public void modifyPassword() {
        String pass = up.getJtfUpdatePw().getText().trim();
        try {
            UpdatePasswordDAO upDAO = UpdatePasswordDAO.getInstance();
            int cnt = upDAO.updatePassword(up.getLoginVO(), pass);
            if (cnt == 1) {
                JOptionPane.showMessageDialog(up, "해당 사원의 정보가 변경되었습니다.");
                up.dispose();
            } else {
                JOptionPane.showMessageDialog(up, "비밀번호를 다시 확인하세요");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        up.dispose();
    }
}
