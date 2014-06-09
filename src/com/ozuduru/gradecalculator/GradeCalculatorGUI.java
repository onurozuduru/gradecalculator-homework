/*
*Developed by:
*name:	Onur Ozuduru
*github page:	https://github.com/onurozuduru
*twitter:	https://twitter.com/OnurOzuduru
*e-mail: onur.ozuduru { at } gmail.com
*
*This work is licensed under the Creative Commons Attribution 4.0 International License.
*To view a copy of this license, visit http://creativecommons.org/licenses/by/4.0/.
*/
package com.ozuduru.gradecalculator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Vector;


import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GradeCalculatorGUI extends JFrame implements ActionListener,
		ItemListener {

	private static final long serialVersionUID = 1L;// Default Serial.
	
	private final static String WINDOW_NAME = "GRADE CALCULATER";
	private final static int WIDTH = 470, HEIGHT = 380;
	private final static String FILE_NAME = "dataFile.txt";
	
	private JPanel jpNorth, jpCenter, jpButtom, jpSouth, jpEnd;
	private JList jlGrades;
	private DefaultListModel listModel;
	private JButton jbAdd, jbEdit, jbDelete, jbDeleteAll, jbCalculate, jbSave;
	private JRadioButton jrbAverage, jrbMin;
	private ButtonGroup bGroup;
	private JComboBox cbGradingFormat;
	private JLabel lblAvg, lblLow, lblHigh;
	private JLabel lblCalculateStatus, lblGradingFormat;
	private JTextField txtAvg, txtLow, txtHigh;
	private JTextField txtMinGrade;
	
	private int avg, low, high;
	private Vector<Student> studentList;
	private boolean gradingFormat;
	
	//------------Constructor------------
	public GradeCalculatorGUI() throws HeadlessException {
		super(WINDOW_NAME);
		
		gradingFormat = true;//flag for Add and Edit windows; true if grades are out of 100.
		this.studentList = new Vector<Student>();
		ReadWriteFile file = new ReadWriteFile(FILE_NAME, studentList);
		
		// Load data from dataFile.txt
		try {
			file.loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setLayout(new BorderLayout());
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		// Ask for saving changes while windows is closing.
		this.addWindowListener(new WindowAdapter(){		
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(null,
						"Do you want to save changes?", "SAVE", JOptionPane.YES_NO_CANCEL_OPTION);
				if(option == JOptionPane.YES_OPTION){
					save();
					((JFrame) e.getComponent()).dispose();
				}
				else if(option == JOptionPane.NO_OPTION){
					((JFrame) e.getComponent()).dispose();
				}
				else
					return;
			}			
		});
		
		//jpNorth includes student list and Add, Edit, Delete buttons
		//		and Average, Lowest, Highest score calculations.
		jpNorth = new JPanel();
		setNorth();
		this.add(jpNorth, BorderLayout.NORTH);
		
		//It can be selected one of the status calculation type in jpCenter.
		jpCenter = new JPanel();
		setCenter();
		
		jpButtom = new JPanel();
		jpButtom.add(jpCenter);
		
		//The grading format can be changed in jpSouth.
		jpSouth = new JPanel();		
		setSouth();
		jpButtom.add(jpSouth);
		
		// Calculate status and save buttons.
		jpEnd = new JPanel();
		setEnd();
		jpButtom.add(jpEnd);
		
		this.add(jpButtom);
	}
	//************End of the Constructors************
	
	
	//------------Panels' Setters------------
	private void setNorth() {
		jpNorth.setLayout(new BorderLayout());
		
		jlGrades = new JList(studentList);
		jlGrades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlGrades.setLayoutOrientation(JList.VERTICAL);
		jlGrades.setVisibleRowCount(5);
		jlGrades.setToolTipText("ID Name MidtermI MidtermII Final Status");
		
		// Edit & Delete buttons will activate if a selection is made from the list.
		jlGrades.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				jbEdit.setEnabled(true);
				jbDelete.setEnabled(true);
			}});
		JScrollPane scrollPane = new JScrollPane(jlGrades);
		
		JPanel jpDown = new JPanel(new GridLayout(0, 2, 0, 10));// includes jpButtons & jpCalculations.
		JPanel jpButtons = new JPanel(new GridLayout(4, 1, 3, 3));// includes Add, Edit, Delete buttons.	
		JPanel jpCalculations = new JPanel(new GridLayout(3, 2, 3, 3));// includes Average, Lowest and Highest score labels.
		jpCalculations.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		jbAdd = new JButton("Add");
		jbAdd.setToolTipText("Add new student.");
		jbAdd.addActionListener(this);
		
		jbEdit = new JButton("Edit");
		jbEdit.setToolTipText("Edit selected student.");
		jbEdit.setEnabled(false);
		jbEdit.addActionListener(this);
		
		jbDelete = new JButton("Delete");
		jbDelete.setToolTipText("Delete selected student.");
		jbDelete.setEnabled(false);
		jbDelete.addActionListener(this);
		
		jbDeleteAll = new JButton("Delete All");
		jbDeleteAll.setToolTipText("Clear list.");
		jbDeleteAll.addActionListener(this);
		
		jpButtons.add(jbAdd);
		jpButtons.add(jbEdit);
		jpButtons.add(jbDelete);
		jpButtons.add(jbDeleteAll);
		
		txtAvg = new JTextField(3);
		txtAvg.setEditable(false);
		txtLow = new JTextField(3);
		txtLow.setEditable(false);
		txtHigh = new JTextField(3);
		txtHigh.setEditable(false);
		
		lblAvg = new JLabel("Average: ");
		lblAvg.add(txtAvg);
		lblLow = new JLabel("Lowest Score: ");
		lblLow.add(txtLow);
		lblHigh = new JLabel("Highest Score: ");
		lblHigh.add(txtHigh);
		
		jpCalculations.add(lblAvg);
		jpCalculations.add(txtAvg);
		jpCalculations.add(lblLow);
		jpCalculations.add(txtLow);
		jpCalculations.add(lblHigh);
		jpCalculations.add(txtHigh);
		
		jpDown.add(jpButtons);
		jpDown.add(jpCalculations);
		jpNorth.add(scrollPane, BorderLayout.NORTH);
		jpNorth.add(jpDown, BorderLayout.CENTER);
	}//End of setNorth.

	private void setCenter() {
		jpCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
		bGroup = new ButtonGroup();
		
		JPanel middle = new JPanel(new BorderLayout());
		middle.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		lblCalculateStatus = new JLabel("Calculate status according to: ");
		txtMinGrade = new JTextField(3);
		txtMinGrade.setEnabled(false);
		txtMinGrade.addActionListener(this);
		
		JPanel jpRPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		jrbAverage = new JRadioButton("Average");
		jrbAverage.setSelected(true);
		
		if(!studentList.isEmpty())
			this.calculateAllStatus();
		
		jrbAverage.addItemListener(this);
		jrbMin = new JRadioButton("Minumum grade ");
		jrbMin.setSelected(false);
		jrbMin.addItemListener(this);
		bGroup.add(jrbAverage);
		bGroup.add(jrbMin);
		
		jpRPanel.add(jrbAverage);
		jpRPanel.add(jrbMin);
		jpRPanel.add(txtMinGrade);
		middle.add(lblCalculateStatus, BorderLayout.NORTH);
		middle.add(jpRPanel, BorderLayout.SOUTH);
		jpCenter.add(middle);
	}//End of setCenter.

	private void setSouth() {
		jpSouth = new JPanel(new FlowLayout());
		jpSouth.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		String[] formats = {"Out of 100", "Letter"};
		
		lblGradingFormat = new JLabel("Grading Format: ");
		cbGradingFormat = new JComboBox(formats);
		cbGradingFormat.addActionListener(this);
		
		jpSouth.add(lblGradingFormat);
		jpSouth.add(cbGradingFormat);
	}
	
	private void setEnd() {
		jpEnd.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		jbCalculate = new JButton("Calculate Status");
		jbSave = new JButton("Save");
		
		jbCalculate.addActionListener(this);
		jbCalculate.setToolTipText("Calculate all statuses.");
		
		jbSave.addActionListener(this);
		jbSave.setToolTipText("Save data.");
		
		jpEnd.add(jbCalculate);
		jpEnd.add(jbSave);
		
	}
	//************End of Panels' Setters************
	
	
	public void save() {
		ReadWriteFile file = new ReadWriteFile(FILE_NAME, studentList);
		try {
			file.saveData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Calculate and show; average, lowest and highest scores.
	//		It first gets every student's average then calculates all list's average.
	public void calculateStatistics() {
		ArrayList<Integer> avgList = new ArrayList<Integer>();
		
		for(Student student : studentList)
			avgList.add(student.calculateAverage());
		low = Collections.min(avgList);
		high = Collections.max(avgList);
		avg = 0;
		
		for(Integer i : avgList)
			avg += i;
		avg = avg / avgList.size();
		
		txtAvg.setText(String.valueOf(avg));
		txtLow.setText(String.valueOf(low));
		txtHigh.setText(String.valueOf(high));
	}
	
	// Show statuses according to average of the student's average list.
	public void calculateAllStatus() {
		this.calculateStatistics();
		for(Student student : studentList)
			student.calculateStatus(avg);
		jlGrades.updateUI();
	}
	
	// Show statuses according to given number or letter.
	public void calculateAllStatus(int criticalPoint) {
		for(Student student : studentList)
			student.calculateStatus(criticalPoint);
		
		if(cbGradingFormat.getSelectedItem().equals("Letter")){
			listModel = new DefaultListModel();
			for(Student s : studentList)
				listModel.addElement(s.getId()+" "+s.getName().toUpperCase()+" "+
						Student.convertToLetter(s.getMid1Grade())+" "+
						Student.convertToLetter(s.getMid2Grade())+" "+
						Student.convertToLetter(s.getFinalGrade())+" "+
						s.statusToString());
			jlGrades.setModel(listModel);
		}
		
		jlGrades.updateUI();
	}
	
	
	//------------Listeners' methods------------
	@Override
	public void itemStateChanged(ItemEvent arg0) {	
		//If jrbMin is selected then txtMinGrade will be enabled.
		txtMinGrade.setEnabled(jrbMin.isSelected());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == jbAdd){
			StudentCreator s = new StudentCreator(studentList, jlGrades, gradingFormat);
			s.setVisible(true);
		}
		else if(arg0.getSource() == jbEdit){
			StudentEditor sE = new StudentEditor(studentList, jlGrades, gradingFormat, 
					jlGrades.getSelectedIndex());
			sE.setVisible(true);
		}
		else if(arg0.getSource() == jbDelete){
			studentList.remove(jlGrades.getSelectedIndex());
			if(cbGradingFormat.getSelectedItem().equals("Letter"))
				((DefaultListModel) jlGrades.getModel()).remove(jlGrades.getSelectedIndex());
			jlGrades.updateUI();
		}
		else if(arg0.getSource() == jbDeleteAll){
			studentList.removeAllElements();
			if(cbGradingFormat.getSelectedItem().equals("Letter"))
				((DefaultListModel) jlGrades.getModel()).removeAllElements();
			jlGrades.updateUI();
		}
		else if(arg0.getSource() == jbCalculate){
			if(jrbAverage.isSelected()){
				try {
					this.calculateAllStatus();
				} catch (NoSuchElementException e) {
					JOptionPane.showMessageDialog(null,
							"The list is empty!",
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				if(txtMinGrade.getText().equals("")){
					JOptionPane.showMessageDialog(null,
							"Please enter a minumun grade!",
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					if(cbGradingFormat.getSelectedItem().equals("Out of 100"))
						this.calculateAllStatus(Integer.parseInt(txtMinGrade.getText()));
					else
						this.calculateAllStatus(Student.convertToNumber(txtMinGrade.getText().toUpperCase()));
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"Please enter a valid number!",
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
					return;
				}	
			}
		}
		else if(arg0.getSource() == jbSave){
			save();
		}
		else if(arg0.getSource() == cbGradingFormat){
			if(((String) cbGradingFormat.getSelectedItem()).equals("Out of 100")){
				jlGrades.setListData(studentList);
				gradingFormat = true;
			}
			else {
				listModel = new DefaultListModel();
				for(Student s : studentList)
					listModel.addElement(s.getId()+" "+s.getName().toUpperCase()+" "+
							Student.convertToLetter(s.getMid1Grade())+" "+
							Student.convertToLetter(s.getMid2Grade())+" "+
							Student.convertToLetter(s.getFinalGrade())+" "+
							s.statusToString());
				jlGrades.setModel(listModel);
				gradingFormat = false;
			}
		}//End of if(arg0.getSource() == cbGradingFormat).		
	}//End of actionPerformed.
	//************End of Listeners' methods************
}