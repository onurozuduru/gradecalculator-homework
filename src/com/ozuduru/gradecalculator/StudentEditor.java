/*********************************************************************************
*File: StudentEditor.java
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

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.ozuduru.gradecalculator.Student.Status;

public class StudentEditor extends StudentCreator {

	private static final long serialVersionUID = 1L;// Default Serial.
	
	private final static String WINDOW_NAME = "EDIT";
	protected int index;
	
	public StudentEditor(Vector<Student> studentList, JList jlList, boolean gF, int index) throws HeadlessException {
		super(studentList, jlList, gF);
		this.setTitle(WINDOW_NAME);
		
		this.index = index;
		Student student = studentList.get(this.index);
		this.txtID.setText(student.getId());
		this.txtName.setText(student.getName());
		this.txtMid1.setText(String.valueOf(student.getMid1Grade()));
		this.txtMid2.setText(String.valueOf(student.getMid2Grade()));
		this.txtFinal.setText(String.valueOf(student.getFinalGrade()));
		this.jcbMid1.setSelected(true);
		this.jcbMid2.setSelected(true);
		this.jcbFinal.setSelected(true);
	}
	
	public void updateStudent(Student student, String id, String name, int m1, int m2, int f){
		student.setId(id);
		student.setName(name);
		student.setMid1Grade(m1);
		student.setMid2Grade(m2);
		student.setFinalGrade(f);
		student.setStatus(Status.NOT_CALCULATED);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == jbCancel){
			this.dispose();
			return;
		}
		if(arg0.getSource() == jbDone){
			if(txtID.getText().equals("Enter the ID") || txtName.getText().equals("Name & Surname") ||
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
			
			Student student = studentList.get(index);
			if(isGradeFormat100){
				try {
					this.updateStudent(student, txtID.getText(), txtName.getText(),
							Integer.parseInt(mid1), Integer.parseInt(mid2), Integer.parseInt(finalS));

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
					this.updateStudent(student, txtID.getText(), txtName.getText(),
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
			
			jlList.updateUI();
			this.dispose();
			
		}//End of if(arg0.getSource() == jbDone).
	}//End of actionPerformed
}
