/*************************************************************************
 * Title: Payroll Calculator Class and Driver Program 
 * File: EliJames_HW2.java
 * Author: James Eli
 * Date: 8/28/2016
 *
 * This program calculates the payroll for a group of employees using
 * a multi-dimensional array. Regular pay, overtime pay and gross pay are
 * calculated based upon hours worked and pay rate. The following equations 
 * define an employee's pay:
 *  (1) Regular Pay = hours (<=40) * rate
 *  (2) OT Pay = hours (>40) * rate * 1.5
 *  (3) Gross Pay = Regular Pay + OT pay
 * 
 * Notes: 
 *   (1) Use of double to represent currency is widely considered a bad
 *   choice. Use of Integer or BigDecimal circumvents floating-point errors.
 *   (2) Compiled with java SE JDK 8, Update 102 (JDK 8u102).
 *   (3) Classes combined for demonstration purposes.
 * 
 * Submitted in partial fulfillment of the requirements of PCC CIS-131.
 *************************************************************************
 * Change Log:
 *   08/28/2016: Initial release. JME
 *   09/25/2016: Converted to stand alone class. JME
 *************************************************************************/
public class EliJames_HW2 {
  /*********************************************************************
   * Payroll defined as inner class.
   *********************************************************************/
  public class Payroll {
    // Constants.
    private static final int HOURS_WORKED = 0;              // Array index for hours worked.
    private static final int PAY_RATE = 1;                  // Array index for hourly pay rate ($USD).
    private static final int REGULAR_PAY = 2;               // Array index for regular pay = hours*rate ($USD).
    private static final int OVERTIME_PAY = 3;              // Array index for OT pay = hours*rate*1.5 ($USD).
    private static final int TOTAL_PAY = 4;                 // Array index for total employee pay ($USD).
    private static final double MAX_REGULAR_HOURS = 40.0D;  // Maximum number of regular (non-OT) hours per pay period. 
    private static final double OVERTIME_MULTIPLIER = 1.5D; // OT pay rate multiplier.

   /*********************************************************************
    * Function: calculatePayroll
    * Input:    (double[][]) pay, employee payroll array.
    * Return:   n/a
    *  
    * This method calculates an employee's regular, overtime and gross
    * pays. Calls calcRegularPay, calcOvertimePay and calcGrossPay 
    * methods.
    *********************************************************************/
    public void calculatePayroll( double[][] pay ) throws IllegalArgumentException {
      for ( double[] employee : pay ) {
        if ( employee[HOURS_WORKED] < 0.0D ) 
   	      throw new IllegalArgumentException( "Employee hours <0" );
        if ( employee[PAY_RATE] < 0.0D ) 
          throw new IllegalArgumentException( "Employee pay rate <0" );
      
        // Calculate regular pay.
        if ( employee[HOURS_WORKED] > MAX_REGULAR_HOURS ) // Exceeding max regular hours?
          employee[REGULAR_PAY] = calcRegularPay( MAX_REGULAR_HOURS, employee[PAY_RATE] );
        else
          employee[REGULAR_PAY] = calcRegularPay( employee[HOURS_WORKED], employee[PAY_RATE] );
                                    
        // Calculate OT pay, if hours worked > 40.
        if ( employee[HOURS_WORKED] > MAX_REGULAR_HOURS ) 
          employee[OVERTIME_PAY] = calcOvertimePay( (employee[HOURS_WORKED] - MAX_REGULAR_HOURS), employee[PAY_RATE] );
        else
          employee[OVERTIME_PAY] = 0.0D; // No OT pay.

        // Calculate gross pay.
        employee[TOTAL_PAY] = calcGrossPay( employee[REGULAR_PAY], employee[OVERTIME_PAY] );
      }
    }
  
    /*********************************************************************
     * Function: displayPayroll
     * Input:    empID, employee ID array, (int[]).
     *           pay, employee payroll array, (double[][]).
     * Return:   n/a
     *  
     * This method is a formatted display of the entire employee's payroll 
     * array.
     *********************************************************************/
    public void displayPayroll( int[] empID, double[][] pay ) {
      // Assert parallel arrays equal in size. See note #3 above.
	  assert( empID.length == pay.length ) : "pay & empID arrays not equal length";

      // Display header.
      System.out.printf( "%12sHours%1$11sRegular   Overtime  Total%n", "" );
      System.out.println( "Employee ID Worked Pay Rate Gross Pay Gross Pay Gross Pay" );
      // Iterate through each employee and display payroll data.
      for ( int employee=0; employee<empID.length; employee++ ) {
        System.out.printf( "%d", empID[employee] ); 
        System.out.printf( "%14.2f", pay[employee][HOURS_WORKED] );
        System.out.printf( "%7.2f", pay[employee][PAY_RATE] );
        System.out.printf( "%11.2f", pay[employee][REGULAR_PAY] );
        System.out.printf( "%10.2f", pay[employee][OVERTIME_PAY] );
        System.out.printf( "%10.2f%n", pay[employee][TOTAL_PAY] );
      }
    }
  
   /*********************************************************************
    * Function: calcRegularPay, calcOvertimePay, calcGrossPay
    * Inputs:   (double) hours.
    *           (double) rate.
    * Returns:  (double) calculated pay.
    *  
    * These methods are separated from the above calculatePayroll method 
    * to enhance readability and maintainability.
    *********************************************************************/
    public double calcRegularPay( double hours, double rate ) throws IllegalArgumentException {
      if ( hours > MAX_REGULAR_HOURS ) // Assert arrays equal length.
        throw new IllegalArgumentException( "Employee hours >40" );
      return hours * rate;
    }
  
    public double calcOvertimePay( double hours, double rate ) {
      // OT pay = OT hours * pay rate * OT multiplier.
      return hours * rate * OVERTIME_MULTIPLIER;
    }
  
    public double calcGrossPay( double payRegular, double payOT ) {
      return payRegular + payOT; // Sum all forms of pay.
    }
  } //End of Payroll class.
  
  /*********************************************************************
   * Start of main driver program. Command line arguments are ignored.
   *********************************************************************/
  public static void main( String[] args ) {
	// Instantiate our classes.
    Payroll companyPayroll = new EliJames_HW2().new Payroll();
    // Employee IDs, parallel array to below payroll array.
    int[] employeeID = new int[] { 100, 200, 300, 400, 500, 600 };  // Filled with hard-coded data.
    // Payroll data per employee [hours, rate, regular hours, OT hours, gross pay], parallel array with employeeID.
    double[][] payroll = new double[][] { // Filled with hard-coded data.
      { 50.0, 25.00, 0.0, 0.0, 0.0 },
      { 15.5, 15.00, 0.0, 0.0, 0.0 },
      { 48.0, 27.00, 0.0, 0.0, 0.0 },
      { 40.0, 25.00, 0.0, 0.0, 0.0 },
      { 40.0, 23.33, 0.0, 0.0, 0.0 }, 
      { 45.0, 10.00, 0.0, 0.0, 0.0 } 
    };
    
    try {
      // Calculate employee payroll.
      companyPayroll.calculatePayroll( payroll );
      // Display employee payroll information.
      companyPayroll.displayPayroll( employeeID, payroll );
    } catch ( IllegalArgumentException e ) {
      // Bad data.
      System.out.println( "Data corrupt. " + e.getMessage() );
    }
  }
}  // End of EliJames_HW2 class

