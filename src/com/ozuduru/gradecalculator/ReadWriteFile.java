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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class ReadWriteFile {
	private String fileName;
	private File file;
	private Vector<Student> studentList;
	
	public ReadWriteFile(String fileName, Vector<Student> studentList) {
		this.fileName = fileName;
		this.studentList = studentList;// Get list which will be modified.
		
		// If there is no data which is saved before then create a new data file.
		file = new File(this.fileName);
		if(!file.canExecute())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	// Read the file line by line.
	void loadData() throws IOException {
		BufferedReader f = new BufferedReader(new FileReader(file));
		String line;
		while((line = f.readLine()) != null)
			addToList(line);
		f.close();
	}
	
	// Write data from the list to the file in poper format.
	void saveData() throws IOException {
		BufferedWriter f = new BufferedWriter(new FileWriter(file));
		for(Student student : studentList)
			f.write(student.toStringStorageFormat()+"\n");
		f.close();
	}
	
	// That function gets a line from data file and chops it into five values.
	//	Line format of the file:
	//		ID,NAME,MIDTERMI,MIDTERMII,FINAL
	private void addToList(String line) {
		String[] value = new String[5];
		value = line.split(",", 5);
		Student student = new Student(value[0], value[1],
				Integer.valueOf(value[2]), Integer.valueOf(value[3]), Integer.valueOf(value[4]));
		studentList.add(student);
	}
}
