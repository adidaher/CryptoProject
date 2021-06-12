package Algo;
import java.util.ArrayList;

/*
 * Big Integer: a class for representing integers of
 * unlimited size (limited only by the amount of memory).
 */

public class BigInteger {
  
  /*
   * The state of a BigInteger object is represented
   * by an ArrayList of Integers. The i'th element of
   * the ArrayList is the i'th decimal digit of the number.
   * 
   * That is, the represented number is
   * digits.get(i)*10^i for i=0,...,digits.size()-1
   * 
   * This is an instance field (a field of a particular object,
   * as opposed to a class field). We declare such fields without
   * the 'static' keyword.
   */
  
  private ArrayList<Integer> digits;
  
  /*
   * The constructor takes a string representation of
   * the number.
   */
  
  public BigInteger(String s) {
    digits = new ArrayList<Integer>();
    for (int i=0; i<s.length(); i++) {
      // extract the i'th character FROM THE RIGHT
      char char_i  = s.charAt( s.length() - 1 - i );
      // convert it to a decimal digit
      int  digit_i = Character.getNumericValue(char_i);
      // add it to the representation
      digits.add(i,digit_i);
    }
  }
  
  /*
   * Converting the number to a string. This
   * implementation is inefficient because it constructs
   * many strings (the + operator returns a new string
   * every time). It's possible to do is more efficcietly,
   * but also more complex.
   * 
   * Note that the name 'digits' refers to a field of
   * the object on which we invoked the method.
   * 
   * This is an instance method that acts on an object, so
   * it does not have the 'static' keyword that we used to define
   * functions and procedures.
   */
  
  public String toString() {
    String s = "";
    for (int i=0; i<digits.size(); i++) {
      // convert the integer to a digit and concatenete
      s = Character.forDigit(digits.get(i), 10) + s;
    }
    return s;
  }

  /*
   * This is a private constructor. Only methods of this
   * class can call it to construct new BigInteger's.
   * 
   * Note the special syntax: We use the name 'digits'
   * twice, once as a field and once as a method argument.
   * If we simply write 'digits', we refer to the argument.
   * To refer to this object's field, we use the keyword 'this'.
   * Compare this to the use of digits in the implementation of
   * toString().
   * 
   * 'this' is always a reference to the object on which the
   * current method was invoked.
   */
  
  private BigInteger(ArrayList<Integer> digits) {
    this.digits = digits;
  }
  

  
  public static void main(String[] arguments) {
    BigInteger x = new BigInteger("132");

  }
}