package util;

public class WhaozlMath {
	/**
	 * 传递2个参数：logA和logB——来计算log(A+B)
	 * 方法：log (A + B) 
	 * 				=log (A(1 + B/A)) 
	 * 				=log A + log(1 + B/A)
	 * 				=log A + log(1 + exp(logB - logA)) 
	 * 【注意】当logB-logA=diff<-37时，则log(1 + exp(diff))≈0
	 */
	public static double logplus(double logA, double logB) {
		double max, diff;
		if (logA > logB) {
			if (logB == Double.NEGATIVE_INFINITY){
				return logA;
			}else {
				max = logA;
				diff = logB - logA;
			}
		} else {
			if (logA == Double.NEGATIVE_INFINITY){
				return logB;
			} else {
				max = logB;
				diff = logA - logB;
			}
		}
		return max + (diff < -37 ? 0 : Math.log(1 + Math.exp(diff)));
	}
}
