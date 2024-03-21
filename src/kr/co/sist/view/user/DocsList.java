package kr.co.sist.view.user;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument.Content;

import kr.co.sist.controller.event.DocsListEvent;
import kr.co.sist.dao.DocsListDAO;
import kr.co.sist.view.admin.CheckEmployeeInformation;
import kr.co.sist.vo.DocumentVO;
import kr.co.sist.vo.EmpInfoVO;

public class DocsList extends JFrame{
    private JCheckBox cbcheck;
    private JButton jbtnAddDoc;
    private JButton jbtnGoMain;
    private JTable jtaDob;
    private DefaultTableModel dtmjtabResult;
    private DocumentVO dVO;
    
    public DocsList() {
        setTitle("사원문서리스트");
        
        
        cbcheck=new JCheckBox();
        jbtnAddDoc=new JButton("문서등록");
        jbtnGoMain=new JButton("메인으로");
    
       
        String[]columnName= {"문서번호","문서제목","신청부서","신청날짜","결제상태","공유상태","최종수정일"};
        dtmjtabResult=new DefaultTableModel(columnName,0);
        jtaDob=new JTable(dtmjtabResult);
        Object[] content = new Object[8];
        
        
//        DocsListDAO dlDAO = DocsListDAO.getInstance();
//        List<DocumentVO> list;
//        try {
//        	list=dlDAO.selectAllDocument();
//        dVO= new DocumentVO();
//        for(int i=0; i<list.size();i++) {
//        	dVO =list.get(i);
//        	content[0]=dVO.getDocNo();
//        	content[1]=dVO.getTitle();
//        	content[2]=dVO.getWorkDesc();
//        	content[3]=dVO.getWorkLog();
//        	content[4]=dVO.getApprDesc();
//        	content[5]=dVO.getFileName();
//        	content[6]=dVO.getEmpNo();
//        	content[7]=dVO.getDocDate();
//        	dtmjtabResult.addRow(content);
//        	
//        }
//        }catch (Exception e) {
//			// TODO: handle exception
//        	e.printStackTrace();
//		}
       
        jtaDob.setEnabled(false);
        
        
        jtaDob.getColumnModel().getColumn(0).setPreferredWidth(60);
        jtaDob.getColumnModel().getColumn(1).setPreferredWidth(60);
        jtaDob.getColumnModel().getColumn(2).setPreferredWidth(60);
        jtaDob.getColumnModel().getColumn(3).setPreferredWidth(60);
        jtaDob.getColumnModel().getColumn(4).setPreferredWidth(60);
        jtaDob.getColumnModel().getColumn(5).setPreferredWidth(60);
        jtaDob.getColumnModel().getColumn(6).setPreferredWidth(60);
    
        cbcheck.setBounds(30, 20, 20, 20);
        jbtnAddDoc.setBounds(550, 60, 100, 30);
        jbtnGoMain.setBounds(550, 20, 100, 30);
        
        DocsListEvent dle=new DocsListEvent(this);
        
        jbtnAddDoc.addActionListener(dle);
        jbtnGoMain.addActionListener(dle);
        cbcheck.addItemListener(dle);
        
        dle.searchDocInfo(240000);
        
        setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane(jtaDob);
        scrollPane.setBounds(30, 110, 600, 400);
        add(scrollPane);
        
        add(cbcheck);
        add(jbtnAddDoc);
        add(jbtnGoMain);
        
       
        
        setBounds(300,100,700,570);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
    
    
    public JCheckBox getCbcheck() {
		return cbcheck;
	}


	public JButton getJbtnAddDoc() {
		return jbtnAddDoc;
	}


	public JButton getJbtnGoMain() {
		return jbtnGoMain;
	}


	public JTable getJtaDob() {
		return jtaDob;
	}


	public DefaultTableModel getDtmjtabResult() {
		return dtmjtabResult;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(DocsList::new);
	}
	
    
} 