/*************************************************************************
 * Title: Payroll Calculator Test Suite 
 * File: EliJames_HW2Test.java
 * Author: James Eli
 * Date: 8/29/2016
 *
 * Multiple JUnit test cases for EliJames_HW2.java class.
 * 
 * Notes: 
 *   (1) Use of double to represent currency is widely considered a bad
 *   choice. Use of Integer or BigDecimal circumvents floating-point errors.
 *   (2) Compiled with java SE JDK 8, Update 102 (JDK 8u102).
 *   (3) Under Eclipse, turn on Assertions via:
 *       Run>Run Configurations>Java Applications>HW2_Driver
 *       Arguments Tab>VM Arguments, add "-ea"
 * 
 * Submitted in partial fulfillment of the requirements of PCC CIS-131.
 *************************************************************************/
import org.junit.Assert;
import org.junit.Test;

public class EliJames_HW2_Test {

  // Empty array for testing.
  double[][] test = new double[][] {
    { 0.0,  25.00, 0.0, 0.0, 0.0 }, 
    { 0.1,  25.33, 0.0, 0.0, 0.0 },
    { 1.0,  25.50, 0.0, 0.0, 0.0 },
    { 40.0, 25.66, 0.0, 0.0, 0.0 },
    { 40.1, 25.75, 0.0, 0.0, 0.0 }, 
    { 41.0, 26.00, 0.0, 0.0, 0.0 }, 
    { 48.0, 25.00, 0.0, 0.0, 0.0 } 
  };
  
  // Array populated with answers.
  double[][] answers = new double[][] {
    { 0.0,  25.00, 0.00, 0.00, 0.00 }, 
    { 0.1,  25.33, (0.1*25.33), 0.00, (0.1*25.33) },
    { 1.0,  25.50, (1.0*25.50), 0.00, (1.0*25.50) },
    { 40.0, 25.66, (40.0*25.66), 0.00, (40.0*25.66) },
    { 40.1, 25.75, (40.0*25.75), ((40.1 - 40.0)*25.75*1.5), (40.0*25.75)+((40.1 - 40.0)*25.75*1.5) }, 
    { 41.0, 26.00, (40.0*26.00), ((41.0 - 40.0)*26.00*1.5), (40.0*26.00)+((41.0 - 40.0)*26.00*1.5) }, 
    { 48.0, 25.00, (40.0*25.00), ((48.0 - 40.0)*25.00*1.5), (40.0*25.00)+((48.0 - 40.0)*25.00*1.5) } 
  };

  // Instantiate class for testing.
  public EliJames_HW2 hw2 = new EliJames_HW2();
  EliJames_HW2.Payroll payroll = hw2.new Payroll();

  // calcRegularPay method test.
  @Test
  public void testCalcRegularPay() {
    Assert.assertEquals( payroll.calcRegularPay( 40.0, 25.50 ), (40.0*25.50), 0.0 );
  }

  // calcOvertimePay method test.
  @Test
  public void testCalcOvertimePay() {
    Assert.assertEquals( payroll.calcOvertimePay( 10.0, 25.50 ), (10.0*25.50*1.5), 0.0 );
  }

  // calcGrossPay method test.
  @Test
  public void testCalcGrossPay() {
    Assert.assertEquals( payroll.calcGrossPay( 123.45, 543.21 ), (123.45+543.21), 0.0 );
  }
  
  // calculatePayroll method test.
  @Test ( timeout = 100 ) // Crude way to assure method doesn't exceed 100ms.
  public void testCalculatePayroll() {
    payroll.calculatePayroll( test );
    for ( int i=0; i<test.length; i++ ) 
      for ( int j=0; j<test[i].length; j++ ) 
        // Assert calculated payroll equals computed answers.
        Assert.assertEquals( test[i][j], answers[i][j], 0.0 );
  }

  // calcRegularPay hours > 40 exception test.
  @Test ( expected = IllegalArgumentException.class )
  public void testCalcRegularHoursIllegalArgumentException() {
    payroll.calcRegularPay( 40.1, 1.0 );
  }

  // calculatePayroll negative hours exception test.
  @Test ( expected = IllegalArgumentException.class )
  public void testCalculatePayrollHoursIllegalArgumentException() {
    // Sending negative work hours, should trip IllegalArgumentException.
    payroll.calculatePayroll( new double[][] { { -1.0,  0.0, 0.0, 0.0, 0.0 } } );
  }

  // calculatePayroll negative pay rate exception test.
  @Test ( expected = IllegalArgumentException.class )
  public void testCalculatePayrollPayRateIllegalArgumentException() {
    // Sending negative pay rate, should trigger IllegalArgumentException.
    payroll.calculatePayroll( new double[][] { { 0.0,  -1.00, 0.0, 0.0, 0.0 } } );
  }

  // displayPayroll Assert arrays not equal test.
  @Test
  public void testDisplayPayrollAssertArraysNotEqual() {
    try {
      // Sending unequal length arrays, should trigger assertion.
      payroll.displayPayroll( new int[] { 1, 2 }, new double[][] { { 0.0 } } );
    }  catch ( AssertionError e ) { 
      // Check assertion message for match.
      Assert.assertEquals( e.getMessage(), "pay & empID arrays not equal length"  );
    }
  }

} // End EliJames_HW2_Test class.

