package kr.co.sist.controller.event;

import kr.co.sist.dao.LoginDAO;
import kr.co.sist.view.admin.AdminMenu;
import kr.co.sist.view.common.FindPassword;
import kr.co.sist.view.common.Login;
import kr.co.sist.view.user.UserMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LoginEvent extends WindowAdapter implements ActionListener,KeyListener{
    private Login login;
    private static String empno;

    public LoginEvent(Login login) {
        this.login = login;
    }
    
    


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login.getJbLogin()) {
            try{
            	login();
            }catch(Exception e) {
            	JOptionPane.showMessageDialog(null, "퇴사한 직원이거나, 해당 사원의 정보가 존재하지 않습니다.");
            }
        }//end if
        

        if (ae.getSource() == login.getJbExit()) {
            login.dispose();
        }

        if (ae.getSource() == login.getJbFindPassword()) {
            new FindPassword(login);
        }
        
        
    }//actionPerformed

   
    
    
    @Override
    public void windowClosing(WindowEvent we) {
        login.dispose();
    }

    public void login() {
        empno = login.getEmpNoField().getText();
        String Password = login.getPasswordField().getText();

        LoginDAO lDAO = LoginDAO.getInstance();
        String savedPw = lDAO.confirmUser(empno).getPassword();
        String authcode = lDAO.confirmUser(empno).getAuthCode();
        if (Password.equals(savedPw)) {
        	if(empno.equals(Password)) 
        		JOptionPane.showMessageDialog(null, "보안을 위해 비밀번호를 변경해주시기바랍니다.");
        		
            if (authcode.equals("SUPER") || authcode.equals("ADMIN")) {
                new AdminMenu();
                login.dispose();
            } else {
                new UserMenu();
                login.dispose();
            }
        	
        } else {
            JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요.");
        }
    }

    public static String getEmpno() {
        return empno;
    }




    @Override
    public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_ENTER) {
    		try{
    			login();
    		}catch(Exception e1) {
    			JOptionPane.showMessageDialog(null, "퇴사한 직원이거나, 해당 사원의 정보가 존재하지 않습니다.");
    		}
    	}
    	
    }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}//LoginEvent
