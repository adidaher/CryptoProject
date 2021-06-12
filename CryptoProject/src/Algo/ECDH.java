package Algo;
import java.math.BigDecimal;
import java.math.BigInteger;
public class ECDH {

    // the random number is 0 - 10000
	//this class compute the key as decimal and convert it to hex 
	
	static BigDecimal p = new BigDecimal("1461501637330902918203684832716283019651637554291");
	static BigDecimal a = new BigDecimal("0");
	static BigDecimal b = new BigDecimal("7");
	static BigDecimal gx = new BigDecimal("338530205676502674729549372677647997389429898939");
	static BigDecimal gy = new BigDecimal("842365456698940303598009444920994870805149798382");
	public static Point g = new Point(gx,gy);


	
public static Point  calculate_Key(BigDecimal x ,Point point) {
		
	       BigDecimal m, m1 , m2 , x3 , y3; 
	       BigDecimal  px = point.getX();
	       BigDecimal  py = point.getY();
	       Point newpoint = new Point(px,py);
	       BigInteger big1;
	       BigDecimal zero = new BigDecimal("0");
	       BigDecimal i = new BigDecimal("1");
	       
	       while(i.compareTo(x) == -1){
	    	   
	       
			if(px.compareTo(newpoint.getX()) == 0  && py.compareTo(newpoint.getY()) == 0) { //case 2 
			    m1 = px.multiply(px);
			    m1 = m1.multiply(new BigDecimal("3"));
			    m1 = m1.add(a);
			    m1 = m1.remainder(p);
    
			    m2 = py.multiply(new BigDecimal("2"));
			    m2 = m2.remainder(p);
			    big1 = m2.toBigInteger();
			    m2 = new BigDecimal((big1.modInverse(p.toBigInteger())).toString());
					
				m = m1.multiply(m2);
				m = m.remainder(p);
				if(m.compareTo(zero) == -1 )
					m = m.add(p);
			
				x3 = ( (m.multiply(m)).subtract( px.multiply(new BigDecimal("2")) ) ).remainder(p);
				if(x3.compareTo(zero) == -1 )
					x3 = x3.add(p);
				
				y3 = ( ( m.multiply( (px.subtract(x3))) ).subtract(py)).remainder(p);
				if(y3.compareTo(zero) == -1 )
					y3 = y3.add(p);
				
				
				newpoint.setX(x3);
				newpoint.setY(y3);
				
				
			 }else if( newpoint.getX().compareTo(px) == 0  &&  newpoint.getX().compareTo(px.multiply(new BigDecimal("-1"))) ==0   ) { //case 2
					
				 newpoint.setX(new BigDecimal("-1"));
				 newpoint.setY(new BigDecimal("-1"));
					
				 
			} else if(newpoint.getX().compareTo(px) != 0) {//case 3 
					
				
					m = (newpoint.getY().subtract(py)).remainder(p);
					
				    m2 = newpoint.getX().subtract(px);
				    big1 = m2.toBigInteger();
				    big1 = big1.modInverse(p.toBigInteger());
				    m = (m.multiply( new BigDecimal( big1.toString())).remainder(p));
					
					
					if(m.compareTo(zero) == -1 )
						m = m.add(p);
					
					x3 = (m.multiply(m).subtract(newpoint.getX()).subtract(px)).remainder(p);
					y3 = (m.multiply(newpoint.getX().subtract(x3)).subtract(newpoint.getY())).remainder(p);
					
					
					if(x3.compareTo(zero) == -1 )
						x3 = x3.add(p);
					
					if(y3.compareTo(zero) == -1 )
						y3 = y3.add(p);
					
					
					newpoint.setX(x3);
					newpoint.setY(y3);
					
				}
			i = i.add(new BigDecimal("1"));
	       }
	       return newpoint;
	}
	




}
