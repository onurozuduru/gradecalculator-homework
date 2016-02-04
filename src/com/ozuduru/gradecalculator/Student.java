/*********************************************************************************
*File: Student.java
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

// Student class is to keep informations about student.
public class Student {
	enum Status {NOT_CALCULATED, PASSED, FAILED};
	
	private String id;// student's ID.
	private String name;// student's name.
	private int mid1Grade, mid2Grade, finalGrade;// exam results of the student.
	private Status status;// status of the student.
	
	//------------Constructors------------
	public Student(String id, String name, int mid1Grade, int mid2Grade,
			int finalGrade, Status status) {
		this.setId(id);
		this.setName(name);
		this.setMid1Grade(mid1Grade);
		this.setMid2Grade(mid2Grade);
		this.setFinalGrade(finalGrade);
		this.setStatus(status);
	}

	public Student(String id, String name, int mid1Grade, int mid2Grade,
			int finalGrade) {
		this(id, name, mid1Grade, mid2Grade, finalGrade, Status.NOT_CALCULATED);
	}
	
	public Student(String id, String name) {
		this(id, name, 0, 0, 0, Status.NOT_CALCULATED);
	}
	
	public Student(String id) {
		this(id, "Unknown Name", 0, 0, 0, Status.NOT_CALCULATED);
	}
	//************End of the Constructors************
	
	
	//------------Getters and Setters------------
	public int getMid1Grade() {
		return mid1Grade;
	}
	public void setMid1Grade(int mid1Grade) {
		if(mid1Grade < 0) throw new IllegalArgumentException("Grade value must be positive!");
		this.mid1Grade = mid1Grade;
	}
	public int getMid2Grade() {
		return mid2Grade;
	}
	public void setMid2Grade(int mid2Grade) {
		if(mid2Grade < 0) throw new IllegalArgumentException("Grade value must be positive!");
		this.mid2Grade = mid2Grade;
	}
	public int getFinalGrade() {
		return finalGrade;
	}
	public void setFinalGrade(int finalGrade) {
		if(finalGrade < 0) throw new IllegalArgumentException("Grade value must be positive!");
		this.finalGrade = finalGrade;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		if(id.length() != 8) throw new IllegalArgumentException("ID must be an 8 digit number!");
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Status isStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	//************End of Getters and Setters************
	
	
	// That function converts from letter grading format to (%) percentage grading format.
	public static String convertToLetter(int grade) {
		if(grade >= 95)
			return "A";
		if(grade >= 90)
			return "A-";
		if(grade >= 87)
			return "B+";
		if(grade >= 83)
			return "B";
		if(grade >= 80)
			return "B-";
		if(grade >= 77)
			return "C+";
		if(grade >= 73)
			return "C";
		if(grade >= 70)
			return "C-";
		if(grade >= 67)
			return "D+";
		if(grade >= 60)
			return "D";
		return "F";
	}
	
	// It is basically reverse of convertToLetter function.
	public static int convertToNumber(String letter) {
		if(letter.equals("A"))
			return 95;
		if(letter.equals("A-"))
			return 90;
		if(letter.equals("B+"))
			return 87;
		if(letter.equals("B"))
			return 83;
		if(letter.equals("B-"))
			return 80;
		if(letter.equals("C+"))
			return 77;
		if(letter.equals("C"))
			return 73;
		if(letter.equals("C-"))
			return 70;
		if(letter.equals("D+"))
			return 67;
		if(letter.equals("D"))
			return 60;
		return 0;
	}
	
	// Calculates status of this student according to given critical point.
	//		If average of this student's grade is higher or equal to critical point, student is passed
	//			otherwise, student is failed.
	public Status calculateStatus(int criticalPoint) {
		int avg = (mid1Grade + mid2Grade + finalGrade) / 3;
		
		if(avg >= criticalPoint) this.setStatus(Status.PASSED);
		else this.setStatus(Status.FAILED);
		
		return status;
	}
	
	//	The same function with calculateStatus but this one gets Letter grading format as argument.
	public Status calculateStatus(String criticalPointLetter) {
		return calculateStatus(Student.convertToNumber(criticalPointLetter));
	}
	
	// Calculates average of this student.
	public int calculateAverage() {
		return ((this.mid1Grade + this.mid2Grade + this.finalGrade) / 3);
	}
	
	// Returns string format of the status.
	public String statusToString() {
		if(status == Status.NOT_CALCULATED)
			return " ";
		if(status == Status.PASSED)
			return "Passed";
		return "Failed";
	}

	// Redesign string data as storage format:
	//	 ID,NAME,MIDTERMI,MIDTERMII,FINAL
	public String toStringStorageFormat() {
		return id + "," + name + "," + mid1Grade + "," + mid2Grade + ","
				+ finalGrade;
	}

	@Override
	public String toString() {
		return id + " " + name.toUpperCase() + " " + mid1Grade + " " + mid2Grade + " "
				+ finalGrade + " " + this.statusToString();
	}

}
