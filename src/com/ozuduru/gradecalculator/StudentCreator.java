/*********************************************************************************
*File: StudentCreator.java
*Author: Onur Ozuduru
*   e-mail: onur.ozuduru { at } gmail.com
*   github: github.com/onurozuduru
*   twitter: twitter.com/OnurOzuduru
*
*License: The MIT License (MIT)
*
*   Copyright (c) 2014 Onur Ozuduru
*   Permission is hereby granted, free of charge, to any person obtaining a copy
*   of this software and associated documentation files (the "Software"), to deal
*   in the Software without restriction, including without limitation the rights
*   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*   copies of the Software, and to permit persons to whom the Software is
*   furnished to do so, subject to the following conditions:
*  
*   The above copyright notice and this permission notice shall be included in all
*   copies or substantial portions of the Software.
*  
*   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
*   SOFTWARE.
*********************************************************************************/

package com.ozuduru.gradecalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

//GUI for creating and adding new students.
public class StudentCreator extends JFrame implements MouseListener,
		ItemListener, ActionListener {

	private static final long serialVersionUID = 1L;// Default Serial.
	
	private final static String WINDOW_NAME = "ADD";
	private final int WIDTH = 400, HEIGHT = 220;
	
	protected JPanel jpNorth, jpCenter, jpSouth;
	protected JCheckBox jcbMid1, jcbMid2, jcbFinal;
	protected JTextField txtMid1, txtMid2, txtFinal, txtID, txtName;
	protected JButton jbDone, jbCancel;
	protected JRadioButton rbGradeFormat100, rbGradeFormatLetter;
	protected ButtonGroup bgroupFormat;
	protected JLabel lblID, lblName, lblExam, lblAttended, lblGrade;
	protected JLabel lblMid1, lblMid2, lblFinal, lblFormat;
	
	protected Vector<Student> studentList;
	protected JList jlList;
	protected boolean gradingFormat;
	
	public StudentCreator(Vector<Student> studentList, JList jlList, boolean gF) throws HeadlessException {
		super(WINDOW_NAME);
		
		this.studentList = studentList;
		this.jlList = jlList;
		this.gradingFormat = gF;
		
		this.setLayout(new BorderLayout());
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Close only this window.
		
		jpNorth = new JPanel();
		setNorth();
		this.add(jpNorth, BorderLayout.NORTH);
		
		jpCenter = new JPanel();
		setCenter();
		this.add(jpCenter, BorderLayout.CENTER);
		
		jpSouth = new JPanel();		
		setSouth();
		this.add(jpSouth, BorderLayout.SOUTH);
	}

	
	//------------Panels' Setters------------
	private void setNorth() {
		jpNorth.setLayout(new FlowLayout());
		
		lblID = new JLabel("ID:");
		lblName = new JLabel("Name:");
		
		txtID = new JTextField("Enter the ID", 12);
		txtID.setForeground(Color.GRAY);
		txtID.selectAll();
		txtID.addMouseListener(this);
		
		txtName = new JTextField("Name & Surname", 15);
		txtName.setForeground(Color.GRAY);
		txtName.addMouseListener(this);
		
		jpNorth.add(lblID);
		jpNorth.add(txtID);
		jpNorth.add(lblName);
		jpNorth.add(txtName);
	}

	private void setCenter() {
		jpCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel jpCLeft = new JPanel(new GridLayout(4, 3, 3, 5));
		jpCLeft.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JPanel jpCRight = new JPanel(new GridLayout(3, 1));
		jpCRight.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		lblExam = new JLabel("Exam");
		lblAttended = new JLabel("Attended");
		lblGrade = new JLabel("Grade");
		lblMid1 = new JLabel("MidtermI:");
		lblMid2 = new JLabel("MidtermII:");
		lblFinal = new JLabel("Final:");
		
		jcbMid1 = new JCheckBox();
		jcbMid1.setSelected(false);
		jcbMid1.addItemListener(this);
		jcbMid2 = new JCheckBox();
		jcbMid2.setSelected(false);
		jcbMid2.addItemListener(this);
		jcbFinal = new JCheckBox();
		jcbFinal.setSelected(false);
		jcbFinal.addItemListener(this);
		
		txtMid1 = new JTextField(3);
		txtMid1.setEnabled(false);
		txtMid2 = new JTextField(3);
		txtMid2.setEnabled(false);
		txtFinal = new JTextField(3);
		txtFinal.setEnabled(false);
		
		jpCLeft.add(lblExam);
		jpCLeft.add(lblAttended);
		jpCLeft.add(lblGrade);
		jpCLeft.add(lblMid1);
		jpCLeft.add(jcbMid1);
		jpCLeft.add(txtMid1);
		jpCLeft.add(lblMid2);
		jpCLeft.add(jcbMid2);
		jpCLeft.add(txtMid2);
		jpCLeft.add(lblFinal);
		jpCLeft.add(jcbFinal);
		jpCLeft.add(txtFinal);
		
		lblFormat = new JLabel("Format");
		lblFormat.setToolTipText("Select grading format.");
		rbGradeFormat100 = new JRadioButton("Out of 100");
		rbGradeFormat100.setSelected(true);
		rbGradeFormat100.addItemListener(this);
		rbGradeFormatLetter = new JRadioButton("Letter");
		rbGradeFormatLetter.addItemListener(this);
		bgroupFormat = new ButtonGroup();
		bgroupFormat.add(rbGradeFormat100);
		bgroupFormat.add(rbGradeFormatLetter);
		
		jpCRight.add(lblFormat);
		jpCRight.add(rbGradeFormat100);
		jpCRight.add(rbGradeFormatLetter);
		
		jpCenter.add(jpCLeft);
		jpCenter.add(jpCRight);
	}//End of setCenter.

	private void setSouth() {
		jpSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		jbDone = new JButton("Done");
		jbDone.addActionListener(this);
		jbCancel = new JButton("Cancel");
		jbCancel.addActionListener(this);
		
		jpSouth.add(jbDone);
		jpSouth.add(jbCancel);
	}
	//************End of Panels' Setters************
	
	
	//------------Listeners' methods------------
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Cancel button -> Close only this window.
		if(arg0.getSource() == jbCancel){
			this.dispose();
			return;
		}
		
		// Done button -> Add new student on the list.
		if(arg0.getSource() == jbDone){
			// If Name or ID is not entered, show a warning massage.
			if(txtID.getText().equals("Enter the ID") 
					|| txtName.getText().equals("Name & Surname") ||
					txtID.getText().equals("") || txtName.getText().equals("")){
				JOptionPane.showMessageDialog(null,
						"It must be entered both ID and Name!",
						"ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			boolean isGradeFormat100 = rbGradeFormat100.isSelected();
			String mid1 = ((jcbMid1.isSelected()) ? txtMid1.getText() : "0");
			String mid2 = ((jcbMid2.isSelected()) ? txtMid2.getText() : "0");
			String finalS = ((jcbFinal.isSelected()) ? txtFinal.getText() : "0");
			
			Student newStudent;
			if(isGradeFormat100){
				try {
					newStudent = new Student(txtID.getText(), txtName.getText(),
							Integer.parseInt(mid1), Integer.parseInt(mid2),
							Integer.parseInt(finalS));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							e.getMessage(),
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else {
				try {
					newStudent = new Student(txtID.getText(), txtName.getText(),
							Student.convertToNumber(mid1.toUpperCase()),
							Student.convertToNumber(mid2.toUpperCase()),
							Student.convertToNumber(finalS.toUpperCase()));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							e.getMessage(),
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			studentList.add(newStudent);//Add new student.
			
			// If the grading format of the main window(grade calculater window)'s is in Letter format
			//		it will be updated as Letter format otherwise
			//			 it will continue to show numbered format from studentList.
			if(!gradingFormat){
				DefaultListModel listModel = new DefaultListModel();
				for(Student s : studentList)
					listModel.addElement(s.getId()+" "+s.getName().toUpperCase()+" "+
							Student.convertToLetter(s.getMid1Grade())+" "+
							Student.convertToLetter(s.getMid2Grade())+" "+
							Student.convertToLetter(s.getFinalGrade())+" "+
							s.statusToString());
				jlList.setModel(listModel);
			}
			
			jlList.updateUI();//update main window's list.
			this.dispose();//Close only this window.
			
		}//End of if(arg0.getSource() == jbDone).
	}//End of actionPerformed.
	
	//When check boxes are selected, text fields will be activated.
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getSource() == jcbMid1){
			txtMid1.setEnabled(jcbMid1.isSelected());
			if(!txtMid1.isEnabled()) txtMid1.setText("");
		}
		else if(arg0.getSource() == jcbMid2){
			txtMid2.setEnabled(jcbMid2.isSelected());
			if(!txtMid2.isEnabled()) txtMid2.setText("");
		}
		else if(arg0.getSource() == jcbFinal){
			txtFinal.setEnabled(jcbFinal.isSelected());
			if(!txtFinal.isEnabled()) txtFinal.setText("");
		}

	}

	//Clear tips from text fields.
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getSource() == txtID && txtID.getText().equals("Enter the ID")){
			txtID.setText("");
			txtID.setForeground(Color.BLACK);
		}
		else if(arg0.getSource() == txtName && txtName.getText().equals("Name & Surname")){
			txtName.setText("");
			txtName.setForeground(Color.BLACK);
		}
	
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	//************End of Listeners' methods************
}
