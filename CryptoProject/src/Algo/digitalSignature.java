package Algo;

import java.math.BigDecimal;
import java.util.Random;

public class digitalSignature {

	public static int modInverse(int a, int m) {

		for (int x = 1; x < m; x++)
			if (((a % m) * (x % m)) % m == 1)
				return x;
		return 1;
	}
	
	static int gcd(int a, int b)
    {
        // Everything divides 0
        if (a == 0)
          return b;
        if (b == 0)
          return a;
      
        // base case
        if (a == b)
            return a;
      
        // a is greater
        if (a > b)
            return gcd(a-b, b);
        return gcd(a, b-a);
    }


	public Point signing(String M, int a, int Xa, int q) {
		int m;
		m = M.hashCode() % q;
		if (m < 0)
			m = m + q;
		Random random = new Random();
		int k = random.nextInt(((q-1) - 1) + 1) + 1;
		
		while(gcd(k,q-1)!=1)
			k = random.nextInt(((q-1) - 1) + 1) + 1;

		
		int s1 = (int) (Math.pow(a, k) % q);
		if (s1 < 0)
			s1 += q;
		int inverseK = modInverse(k, q - 1);
		int s2 = (inverseK * (m - (Xa * s1))) % (q - 1);
		if (s2 < 0)
			s2 += q - 1;
		return new Point(BigDecimal.valueOf(s1), BigDecimal.valueOf(s2));
	}

	public boolean verification(String M, int Ya, Point s, int a, int q) {
		int v1;
		int v2;
		int m;
		int i = M.length() - 1;
		String M_without_Spaces = "";
		while (i >= 0 && M.charAt(i) == ' ')
			i--;
		while (i >= 0) {
			M_without_Spaces = M.charAt(i) + M_without_Spaces;
			i--;
		}

		int s1 = s.getX().toBigInteger().intValue();
		int s2 = s.getY().toBigInteger().intValue();

		m = M_without_Spaces.hashCode() % q;
		
		if (m < 0)
			m += q;
		
		v1 = (int) (Math.pow(a, m) % q);
		BigDecimal temp1 = new BigDecimal(String.valueOf(Ya));
		temp1 = temp1.pow(s1);
		temp1 = temp1.remainder(new BigDecimal(String.valueOf(q)));
		
		BigDecimal temp2 = new BigDecimal(String.valueOf(s1));
		temp2 = temp2.pow(s2);
		temp2 = temp2.remainder(new BigDecimal(String.valueOf(q)));
		v2 = (temp1.toBigInteger().intValue() * temp2.toBigInteger().intValue()) % q; // v2=((Ya^s1)*(s1^s2))mod q = ((Ya^s1)%p)*(s1^s2)%p)%p
		if (v1 < 0)
			v1 += q;
		if (v2 < 0)
			v2 += q;
		System.out.println("s1:" + s1);
		System.out.println("s2:" + s2);
		System.out.println("v1:" + v1);
		System.out.println("v2:" + v2);
		return v1 == v2;

	}
}