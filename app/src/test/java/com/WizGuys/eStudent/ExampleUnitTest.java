package com.WizGuys.eStudent;

import com.WizGuys.eStudent.students.StudentDetails;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private StudentDetails studentDetails;

    @Before
    public void setUp(){
        studentDetails = new StudentDetails();
    }

    @Test
    public void studentTotalMarks_isCorrect(){
        float result= studentDetails.getTotalMarks(90,50,70);
        assertEquals(210,result,0.001);
    }
    @Test
    public void studentAvgMarks_isCorrect(){
        float result= studentDetails.getAvgMarks(65,63,60);
        assertEquals(62.666668,result,0.001);
    }


}