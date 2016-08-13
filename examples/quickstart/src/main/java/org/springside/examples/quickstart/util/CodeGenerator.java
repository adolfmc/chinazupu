package org.springside.examples.quickstart.util;

public class CodeGenerator {
	
	public static char[] cp=new char[]{
			'0','1','2','3','4','5','6','7','8','9'
			,'a','b','c','d','e','f','g','h','i','j','k'
			,'l','m','n','o','p','q','r','s','t','u','v'
			,'w','x','y','z','A','B','C','D','E','F','G'
			,'H','I','J','K','L','M','N','O','P','Q','R'
			,'S','T','U','V','W','X','Y','Z'};
	
	public static int findPosition(char v){
		for(int i=0 ;i<cp.length;i++){
			if(cp[i]==v){
				return i;
			}
		}
		
		return -9;
	}
	
	
	public static String getNext(String value){
		String vn=value;
		if(value.length()>3){
			vn=value.substring(value.length()-3,value.length());
		}
		StringBuffer sb=new StringBuffer(3);
		String v3 = vn.substring(2, 3);
		String v2 = vn.substring(1, 2);
		String v1 = vn.substring(0, 1);
		
		String v3n=null;
		String v2n=null;
		String v1n=null;
		
		if(v3.equals("Z")){
			if(findPosition(v2.charAt(0))==61 ){
				v2n="0";
				v1n=cp[findPosition(v1.charAt(0))+1]+"";
				v3n="0";
			}else{
				v1n=v1;
				v2n=cp[findPosition(v2.charAt(0))+1]+"";
				v3n="0";
			}
		}else{
			v1n=v1;
			v2n=v2;
			v3n=cp[findPosition(v3.charAt(0))+1]+"";
		}
		if(value.length()>3){
			sb.append(value.substring(0,value.length()-3));
		}
		return sb.append(v1n).append(v2n).append(v3n).toString();
	}
	
	public static String getChildNext(String v){
		return getNext(v);
	}
	
	public CodeGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println(getChildNext("000123123"));
		System.out.println(getNext("0aZ"));
		System.out.println(CodeGenerator.findPosition('Z'));
		System.out.println("123".substring(0,1));
		System.out.println("1".charAt(0));
	}
}
