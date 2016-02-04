/*********************************************************************************
*File: ReadWriteFile.java
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
