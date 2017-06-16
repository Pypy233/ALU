
package ALU;

/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author 161250096_潘羽
 *
 */
public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	 public String integerRepresentation (String number, int length) {
		String result = "";
		long num = Long.parseLong(number);
		long absNum = Math.abs(num);
		
		int pos = 0;
		String ordinary = "";
		if(length==0)
			return "";
		while(absNum>0){
			ordinary+=absNum%2;
			absNum/=2;
		}
		StringBuilder str = new StringBuilder(ordinary);
		ordinary = str.reverse().toString();
		if(length>ordinary.length()){
			for(int i = 0;i<length-ordinary.length();i++)
				result+="0";
		}
		result+=ordinary;
		if(num>=0)
			return result;
		else{
			if(num==-Math.pow(2,length-1)){
				result = "1";
				for(int i = 1;i<length;i++)
					result+="0";
			}
			else{
				result = negation(result);
				char[] arr = result.toCharArray();
				for(int i = 0;i<arr.length;i++){
					if(arr[i]=='0')
						pos = i;
				}
				if(pos==arr.length-1){
					arr[pos] = '1';
					StringBuilder str1 = new StringBuilder();
					for(int i = 0;i<arr.length;i++)
					str1.append(arr[i]);
					result = str1.toString();
				}
				else{
					arr[pos] = '1';
					for(int i = pos+1;i<arr.length;i++){
						arr[i] = '0';
					}
						StringBuilder str1 = new StringBuilder();
						for(int i = 0;i<arr.length;i++)
						str1.append(arr[i]);
						result = str1.toString();
				}
			}
		}
		 return result;
	 }
	
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	 public String floatRepresentation (String number, int eLength, int sLength) {
		 String result = "";
		 String sign = "";
		 String integerStr = "";
		 String exponentStr = "";
		 String decimalStr = "";
		 int exponentVal = 0;
		 int offset = (int)(Math.pow(2, eLength-1))-1;
		 int shift = 0;
		
		 if(number.substring(0,1).equals("-")){
			 sign = "1";
			 number = number.substring(1); // abs(number)
		 }
		 else
			 sign = "0";
		if(number.equals("+Inf")){
			for(int i = 0;i<eLength;i++){
				result+="1";
			}
			for(int i = 0;i<sLength;i++){
				result+="0";
			}
			result = sign+result;
			return result;
		}
		else if(number.equals("Inf")){
			for(int i = 0;i<eLength;i++){
				result+="1";
			}
			for(int i = 0;i<sLength;i++){
				result+="0";
			}
			result = sign+result;
			return result;
		}
		 
		 double num = Double.parseDouble(number);
		 if(num==0){
			for(int i = 0;i<eLength+sLength;i++){
				result+="0";
			}
			result = sign+result;
			return result;
		}//As "+0.0","-0.0"
		 
		 String[] spiltArr = number.split("\\.");
		 int integer = Integer.parseInt(spiltArr[0]);
		 // As exponent
		 int saveInteger = integer;
		 double decimal = Double.parseDouble("0."+spiltArr[1]);
		 while(decimal>0){
			 if(2*decimal>1){
				 decimalStr+=1;
				 decimal = 2*decimal-1;
			 }
			 else if(2*decimal<1){
				 decimalStr+=0;
				 decimal = 2*decimal;
			 }
			 else{
				 decimalStr+=1;
				 break;
			 }
		 }
		
		 if(integer>0){
			 int temp=integer%2;
			 while(integer>0){
				 integerStr = temp+integerStr;
				 integer = integer/2;
				 temp = integer%2;
			 }
		 }
		
		 
		 if(saveInteger==0){
			 if(decimalStr.substring(0,1).equals("0")){
				 while(decimalStr.substring(0,1).equals("0")){
					 shift++;
					 decimalStr = leftShift(decimalStr,1);
			 }
		
				 decimalStr = "0"+decimalStr;
				 exponentVal = offset - shift;
				 exponentStr = integerRepresentation(exponentVal+"",eLength);
			 if(decimalStr.length()>sLength){
				 decimalStr = decimalStr.substring(0,sLength);
			 }		
			 else{
				 while(decimalStr.length()<sLength){
					 decimalStr+="0";
				 	}
			 	}
			 
			 }
			 else{
				 exponentVal = offset+integerStr.length()-1;
				 exponentStr = integerRepresentation(exponentVal+"",eLength);
				 if(decimalStr.length()>sLength){
					 decimalStr = decimalStr.substring(0,sLength);
				 }
				 else{
					 while(decimalStr.length()<sLength){
						 decimalStr+="0";
					 }
					 decimalStr = "0"+decimalStr.substring(1);
				 }
			 }
			 result = sign+exponentStr+decimalStr;
			 return result;
		 }
		 else{
			 exponentVal = offset+integerStr.length()-1;
			 exponentStr = integerRepresentation(exponentVal+"",eLength);
			 decimalStr = integerStr.substring(1)+decimalStr;
			 if(decimalStr.length()>sLength){
				 decimalStr = decimalStr.substring(0,sLength);
			 }
			 else{
				 while(decimalStr.length()<sLength){
					 decimalStr+="0";
				 }
			 }
			 System.out.println(decimalStr);
		 }
		
		 result = sign+exponentStr+decimalStr;
		return result;
	 }
	 /**
		 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
		 * 例：ieee754("11.375", 32)
		 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
		 * @param length 二进制表示的长度，为32或64
		 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
		 */
		public String ieee754(String number, int length) {
			String result = "";
			if (length == 32) {
				result = floatRepresentation(number, 8, 23);
			}
			else if (length == 64) {
				result = floatRepresentation(number,11, 52);
			}
			return result;
		}
		
		/**
		 * 计算二进制补码表示的整数的真值。<br/>
		 * 例：integerTrueValue("00001001")
		 * @param operand 二进制补码表示的操作数
		 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
		 */
		public String integerTrueValue(String operand) {
			String result = "";
			int num = 0;
			int pos = 0;
			char[] arr  = operand.toCharArray();
			char sign = arr[0];
			if(sign=='0'){
				for(int i = 1;i<arr.length;i++){
					if(arr[i]=='1')
					num+=Math.pow(2, arr.length-1-i);	
				}
				result+= num;
			}
			else{
				result = "-";
				operand = negation(operand);
				char[] arr2 = operand.toCharArray();
				for(int i = 1;i<arr2.length;i++){
					if(arr2[i]=='0')
						pos = i;
				}
				if(pos==0){
					int  temp = (int)Math.pow(2, arr2.length-1)+1;// 由于浮点数误差加一得到-2147483648
					System.out.println(temp);
					result = "";
					result+=temp;
				}
				else{
					arr2[pos] = '1';
					if(pos==arr2.length-1){
						for(int i = 1;i<arr2.length;i++){
							if(arr2[i]=='1')
								num+=Math.pow(2, arr2.length-1-i);
						}
						result+=num;
					}
					else{
						for(int i = pos+1;i<arr2.length;i++)
							arr2[i] = '0';
						for(int i = 1;i<arr2.length;i++){
							if(arr2[i]=='1')
								num+=Math.pow(2, arr2.length-1-i);
						}
						result+=num;
					}
				}
			}
			return result;
		}


	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		String result  = "";
		String sign = operand.substring(0,1);
		String exponent = operand.substring(1,eLength+1);
		String mantissa = operand.substring(eLength+1);
		String judgeZero = operand.substring(1);
		long integerValue = 0;
		double decimalValue = 0;
		int expValue = 0;
		double offset = Math.pow(2, eLength-1);
		if(sign.equals("1"))
			result  = "-";
		else{}
		
		if(judgeZero.contains("1")==false&&sign.equals("1"))
			return "-0.0";
		if(judgeZero.contains("1")==false&&sign.equals("0"))
			return "0.0";
		String testexp="";
		for(int i=0;i<eLength;i++)
			testexp+="1";
		String testMan="";
		for(int i=0;i<sLength;i++)
			testMan+="0";
		
		if(testexp.equals(exponent)&&testMan.equals(mantissa))
		{
			if(sign.equals("1"))
				return "-Inf";
			else
				return "+Inf";
		}
		else if(!(testMan.equals(mantissa))&&testexp.equals(exponent))
			return "NaN";
		
		else if(exponent.contains("1")){
			if(eLength>4){
			char[] arr = exponent.toCharArray();
			for(int i = 0;i<arr.length;i++){
				if(arr[i]=='1')
				expValue+=Math.pow(2,arr.length-i-1);
			}
			expValue = (int) (expValue-offset+1);
			double mul = Math.pow(2,expValue);
			integerValue = (long) mul;
			char[] arrM = mantissa.toCharArray();
			for(int i = 0;i<expValue;i++){
				
				if(arrM[i]=='1')
					integerValue+=Math.pow(2, expValue-i-1);
			}
			if(expValue>arrM.length)
			for(int i = expValue;i<=arrM.length;i++){
				if(arrM[i]=='1')
					decimalValue+=Math.pow(2, -(i-expValue)-1);
			}
			result+=integerValue+decimalValue;
		}
			else{
				System.out.println(true);
				char[] arr = exponent.toCharArray();
			for(int i = 0;i<exponent.length();i++){
				if(arr[i]=='1')
					expValue+=Math.pow(2, exponent.length()-1-i);
			}
				System.out.println(expValue);
				expValue = (int) (expValue-Math.pow(2, eLength-1)+1);
				System.out.println(expValue);
				double val = 0;
				val =  Math.pow(2, expValue);
				System.out.println(val);
				decimalValue = val;
				char[] arrM = mantissa.toCharArray();
				for(int i = 0;i<arr.length;i++){
					if(arrM[i]=='1')
					decimalValue+=Math.pow(2,-i-1)*val;
				}
				System.out.println(decimalValue);
				result+=decimalValue;
				return result;
			}// As eLength<=4
		}
		else if(exponent.contains("1")==false&&mantissa.contains("1")){
			expValue=1-(int)Math.pow(2,eLength-1);
			double val  = Math.pow(2,expValue);
			double num = 0;
			char[] arr = mantissa.toCharArray();
			for(int i = 0;i<arr.length;i++){
				if(arr[i]=='1'){
					num+=Math.pow(2,-i)*val;
				}
				System.out.println(num);
			}
			result +=num;
			return result;
		}
		return result;
		
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		char[] arr = operand.toCharArray();
		for(int i = 0;i<arr.length;i++)
			arr[i] =not(arr[i]);
		StringBuilder str = new StringBuilder();
		for(int i = 0;i<operand.length();i++)
			str.append(arr[i]);
		return str.toString();
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		for(int i =0;i<n;i++)
			operand+="0";
		operand = operand.substring(n);
		return operand;
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		for(int i = 0;i<n;i++)
			operand="0"+operand;
		operand = operand.substring(0,operand.length()-n);
		return operand;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		char sign = operand.charAt(0);
		if(sign=='0'){
			for(int i = 0;i<n;i++)
				operand = "0"+operand;
			operand = operand.substring(0,operand.length()-n);
		}
		else{
			for(int i = 0;i<n;i++)
				operand = "1"+operand;
			operand = operand.substring(0,operand.length()-n);
		}
		return operand;
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		int numX = x-48;
		int numY = y-48;
		int numC = c-48;
		int carry = numX & numC|numY&numC|numX&numY;;
		int sum = numX^numY^numC;
		String result = ""+carry+sum;
		return result;
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		String result = "";
		String pos0="";
		String pos1="";
		String pos2="";
		String pos3="";
		pos3 = fullAdder(operand1.charAt(3), operand2.charAt(3), c);
		pos2 = fullAdder(operand1.charAt(2), operand2.charAt(2), pos3.charAt(0));
		pos1 = fullAdder(operand1.charAt(1), operand2.charAt(1), pos2.charAt(0));
		pos0 = fullAdder(operand1.charAt(0), operand2.charAt(0), pos1.charAt(0));
		result = pos0 + pos1.substring(1) + pos2.substring(1) + pos3.substring(1);
		return result;
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param exponent 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String exponent) {
		String result = "";
		String overflow = "";
		int pos = 0;
		if(exponent.contains("0")==false){
			for(int i = 0;i<=exponent.length();i++)
				result+="0";
			return result;
		}
		if(exponent.substring(0,1).equals("0")&&
				exponent.substring(1).contains("0")==false){
			overflow = "1";
			for(int i = 1;i<exponent.length();i++){
				result+="0";
			}
			result = overflow+"1"+result;
			return result;
		}
		char[] arr = exponent.toCharArray();
		for(int i = 0;i<arr.length;i++){
			if(arr[i]=='0')
				pos = i;
		}
		if(pos==arr.length-1){
			arr[pos] = '1';
			StringBuilder str1 = new StringBuilder();
			for(int i = 0;i<arr.length;i++)
			str1.append(arr[i]);
			result = "0"+str1.toString();
		}
		else{
			arr[pos] = '1';
			for(int i = pos+1;i<arr.length;i++){
				arr[i] = '0';
			}
				StringBuilder str1 = new StringBuilder();
				for(int i = 0;i<arr.length;i++)
				str1.append(arr[i]);
				result = "0"+str1.toString();
		}
	
			return result;
		}
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		String result = "";
		int count = length/4;
		operand1=integerRepresentation(integerTrueValue(operand1),length);
		operand2=integerRepresentation(integerTrueValue(operand2),length);
		for(int i=0;i<count;i++){
			result=claAdder(operand1.substring(length-4-4*i,length-4*i),
					operand2.substring(length-4-4*i,length-4*i),c).substring(1)+result;
			c=claAdder(operand1.substring(length-4-4*i,length-4*i),
					operand2.substring(length-4*i-4,length-4*i),c).charAt(0);
		}
		if(operand1.charAt(0)==operand2.charAt(0)&&operand1.charAt(0)!=
				result.charAt(0))
			result ='1'+result;
		else
			result ='0'+result;
		return result;
	}
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1,operand2,'0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		
		if (operand2.charAt(0) == '0')
			operand2 = integerRepresentation("-" + integerTrueValue(operand2),
					length);
		else {
			operand2 = integerTrueValue(operand2);
			operand2 = operand2.substring(1);
			operand2 = integerRepresentation(operand2, length);
		}
		return adder(operand1, operand2, '0', length);

	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		String result = "";
		String assistOp2 = operand2+"0";
		String overflow = "";
		String sign1 = operand1.substring(0, 1);
		String sign2 = operand2.substring(0, 1);
		String negativeOp1 = oneAdder(negation(operand1)).substring(1);
		if(operand2.contains("0")==false){
			result = negativeOp1;
			String temp = "";
			int count = length-result.length();
			for(int i = 0;i<count;i++){
				temp+="1";
			}
			result = "0"+temp+result;
			return result;
		}
		if(operand1.substring(1).contains("1")==false||operand2.substring(1).contains("1")==false){
			for(int i = 0;i<length+1;i++){
				result+="0";
			}
			return result;
		}
		if(operand1.substring(1).contains("1")==false){
			for(int i = 0;i<length-1;i++){
				result+="0";
			}
			result = "0"+sign2+result;
			return result;
		}
		
		if(operand1.contains("0")==false){
			overflow = "0";
			while(operand2.length()<length){
				operand2 = sign2+operand2;
			}
			operand2 = oneAdder(negation(operand2)).substring(1);
			result = overflow+operand2;
			return result;
		}
		for(int i=0;i<operand1.length();i++){
			result+="0";
		}
		for(int i = 0;i<operand2.length();i++){
			if(assistOp2.substring(assistOp2.length()-2).equals("00")||
					assistOp2.substring(assistOp2.length()-2).equals("11")){
				String temp = ariRightShift(assistOp2,1);
				assistOp2 = result.substring(result.length()-1)+
						temp.substring(1);
				result = ariRightShift(result,1);
			}
			else if(assistOp2.substring(assistOp2.length()-2).equals("10")){	
				result = adder(result,negativeOp1,'0',operand2.length()).substring(1);
				assistOp2 = result.substring(result.length()-1)+
						ariRightShift(assistOp2,1).substring(1);
				result=ariRightShift(result,1);	
			}
			else{
				result = adder(result,operand1,'0',operand2.length()).substring(1);
				assistOp2 = result.substring(result.length()-1)+
						ariRightShift(assistOp2,1).substring(1);
				result=ariRightShift(result,1);
			}
			if(i==operand2.length()-1){
				result = result+assistOp2.substring(0, operand2.length());
			}
		}
		
		if(result.length()>length){
			int gap = result.length()-length;
			String ordinarySign = result.substring(0, 1)	;
			String result2 = result.substring(gap);
			String newSign = result2.substring(0,1);
			overflow = "0";
			if(newSign.equals(ordinarySign)==false)
				overflow = "1";
			String judge = result.substring(0,gap);
			char[] arr = judge.toCharArray();
			char s = arr[0];
			for(int i = 0;i<arr.length;i++){
				if(arr[i]!=s)
					overflow = "1";
			}
			result = overflow+result2;
			return result;
		}
		result = "0"+result;
		return result;
	}
		

	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		String result = "";
		String overflow = "";
		boolean IsSameSign = true;
		String saveRem = "";
		
		while(operand1.length()<length){
			operand1 = operand1.charAt(0)+operand1;
			}
		while(operand2.length()<length){
			operand2 = operand2.charAt(0)+operand2;
			}
		if(operand2.substring(1).contains("1")==false){
			return "NaN";
		}// As operand1/0
		  
		for(int i=0;i<length;i++){
			saveRem = saveRem+operand1.charAt(0);
		}
		String saveQuo = operand1;
		String judge = saveRem+saveQuo;
		String divisor = operand2;
	
		if(judge.charAt(0)==divisor.charAt(0)){
			saveRem = integerSubtraction(saveRem,divisor,length).substring(1);
		}
		else{
			saveRem = integerAddition(saveRem,divisor,length).substring(1);
		}//judge "+" or "-" divisor
		
		judge = saveRem+saveQuo;
		if(saveRem.charAt(0)==divisor.charAt(0)){
			IsSameSign = true;
		}
		else{
			IsSameSign = false;
		}
		
		for(int i=0;i<length;i++){
			if(saveRem.charAt(0)==divisor.charAt(0)){
				judge=judge.substring(1)+"1";
			}
			else{
				judge=judge.substring(1)+"0";
			}
			saveRem = judge.substring(0,length);
			saveQuo = judge.substring(length);
            
			if(judge.charAt(0)==divisor.charAt(0)){
				saveRem = integerSubtraction(saveRem,divisor, length).substring(1);
			}
			else{
				saveRem = integerAddition(saveRem,divisor, length).substring(1);
			}
			judge=saveRem+saveQuo;
		}
		if(saveRem.charAt(0)==divisor.charAt(0)){
			saveQuo = saveQuo.substring(1)+"1";
		}
		else{
			saveQuo = saveQuo.substring(1)+"0";
		}
		if(operand1.charAt(0)!=operand2.charAt(0)){
			saveQuo = oneAdder(saveQuo).substring(1);
		}
		if(saveRem.charAt(0)!=operand1.charAt(0)){
			if(operand1.charAt(0)!=operand2.charAt(0)){
				saveRem = integerSubtraction(saveRem,divisor, length).substring(1);
			}
			else{
				saveRem = integerAddition(saveRem,divisor, length).substring(1);
			}
		}
		if((operand1.charAt(0)==operand2.charAt(0)&&IsSameSign)&&
				(operand1.charAt(0)!=operand2.charAt(0)&&!IsSameSign)){
			overflow = "1";
		}
		else
			overflow = "0";
		
		int divisorVal = Integer.parseInt(integerTrueValue(divisor));
		int remVal = Integer.parseInt(integerTrueValue(saveRem));
		if(divisorVal==remVal){
			saveRem = "";
			for(int i = 0;i<length;i++){
				saveRem+="0";
			}
			saveQuo = oneAdder(saveQuo).substring(1);
		}
		else if(divisorVal==-remVal){
			saveRem = "";
			for(int i = 0;i<length;i++){
				saveRem+="0";
			}
			saveQuo = adder(saveQuo,"-1",'0',length).substring(1);
		} // exact divide
		System.out.println(divisorVal);
		System.out.println(remVal);
		result = overflow+saveQuo+saveRem;
		return result;
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		String result = "";
		String sign1=operand1.substring(0,1);
		String sign2=operand2.substring(0,1);
		operand1 = operand1.substring(1);
		operand2 = operand2.substring(1);
		String addedResult = "";
		String overflow = "";
		String sign = "";
		while(operand1.length()<length){
			operand1 = "0"+operand1;
		}
		while(operand2.length()<length){
			operand2 = "0"+operand2;
		}
		boolean IsSameSign = sign1.equals(sign2);
		if(IsSameSign){
			addedResult = adder(operand1,operand2,'0',length);
			overflow = addedResult.substring(1,2);
			if(addedResult.substring(1).equals("0")&&operand1.substring(1,2).equals("1")||
					addedResult.substring(1).equals("0")&&operand2.substring(1,2).equals("1")||
					addedResult.substring(2).contains("1")==false){
				overflow = "1";
				
			}
			sign = sign1;
			result = overflow+sign1+addedResult.substring(1);
		}
		else{
			if(operand1.substring(1).equals(operand2.substring(1))){
				overflow = "0";
				sign = sign1;
				for(int i = 0;i<length;i++){
					result+="0";
				}
				result = overflow+sign+result;
				return result;
			}
			if(sign1.equals("1")){
				String temp = operand1;
				String tempSign = sign1;
				operand1 = operand2;
				sign1 = sign2;
				operand2 = temp;
				sign2 = tempSign;
			}
			operand2 = negation(operand2);
			operand2 = oneAdder(operand2).substring(1);
			addedResult = adder(operand1,operand2,'0',length);
			String judge = addedResult.substring(0,1);
			if(judge.equals("0")){
				sign = sign1;
				result = judge+sign+addedResult.substring(1);
			}
			else{
				sign = negation(sign1);
				String val = addedResult.substring(1);
				val = oneAdder(negation(val));
				result = judge+sign+val;
			} 
		}
		return result;
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		String result = "";
		String sign1 = operand1.substring(0,1);
		String sign2 = operand2.substring(0,1);
		String sign = "";
		String overflow  = "0";
		String decimalOverflow = "";
		String exponent1 = operand1.substring(1,eLength+1);
		String exponent2 = operand2.substring(1,eLength+1);
		String exponent = "";
		String decimal1 = operand1.substring(eLength+1);
		String decimal2 = operand2.substring(eLength+1);
		String decimal = "";
		int count = 0;
		for(int i = 0;i<gLength;i++){
			decimal1+="0";
			decimal2+="0";
		}
		if(operand1.substring(1).contains("1")==false){
			result = overflow+operand2;
			return result;
		}
		else if(operand2.substring(1).contains("1")==false){
			result = overflow+operand1;
			return result;
		}
		if(sign1.equals(sign2)==false&&operand1.substring(1).equals(operand2.substring(1))){
			for(int i = 0;i< eLength+sLength+2;i++){
				result+="0";
			}
			return result;
		}
		String subE = integerSubtraction("0"+exponent1,"0"+exponent2,eLength+1).substring(1);
		if(subE.substring(0,1).equals("0")){
			exponent = exponent1;
			sign = sign1;
			int gap = Integer.parseInt(integerTrueValue(subE));
			while(count<gap){
				decimal2 = logRightShift(decimal2,1);
				count++;
			}
		}
		else{
			exponent = exponent2;
			sign = sign2;
			int gap = -Integer.parseInt(integerTrueValue(subE));
			while(count<gap){
				decimal1 = logRightShift(decimal1,1);
				count++;
			}
		}
		decimal1 = sign1+"1"+decimal1;
		decimal2 = sign2+"1"+decimal2;
		System.out.println("decimal1:"+decimal1);
		System.out.println("decimal2:"+decimal2);
		String addedDecimal = signedAddition(decimal1,decimal2,sLength+2+gLength);
		System.out.println("addedDecimal:"+addedDecimal);
		decimalOverflow = addedDecimal.substring(0,1);
		if(decimalOverflow.equals("1")){
			exponent = oneAdder(exponent).substring(1);
			addedDecimal = addedDecimal.substring(1,sLength+1);
			decimal = addedDecimal;
			result = overflow+sign+exponent+decimal;
			return result;
		}
		if(addedDecimal.contains("1")==false){
				int eVal = Integer.parseInt(integerTrueValue("0"+exponent));
				eVal-=count;
			exponent = integerRepresentation(eVal+"",eLength);
			System.out.println(exponent);
			decimal = addedDecimal.substring(1,1+sLength);
			result = overflow+sign+exponent+decimal;
			return result;
		}
		int eVal2 = Integer.parseInt(integerTrueValue("0"+exponent));;
		addedDecimal = addedDecimal.substring(1);
		while(addedDecimal.substring(0,1).equals("0")){
			eVal2--;
			addedDecimal = leftShift(addedDecimal,1);
		}
		System.out.println(addedDecimal);
		exponent = integerRepresentation(eVal2+"",eLength);
		decimal = (addedDecimal.substring(1)).substring(0,sLength);
		result = overflow+sign+exponent+decimal;
		return result;
	}
		
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		if(operand2.charAt(0)=='0'){
			operand2="1"+operand2.substring(1);
		}
		else{
			operand2="0"+operand2.substring(1);
		}
		return floatAddition(operand1,operand2,eLength,sLength,gLength);
	
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		String result = "";
		String sign = "";
        	String overflow = "0";
        	String sign1 = operand1.substring(0,1);
        	String sign2 = operand2.substring(0,1);
        	String exponent1 = operand1.substring(1,eLength+1);
        	String exponent2 = operand2.substring(1,eLength+1);
        	int offset = -(int)(Math.pow(2,eLength-1))+1;
       	String strOffset = integerRepresentation(String.valueOf(offset),eLength);
       	String decimal1 = "01"+operand1.substring(eLength+1);
       	String decimal2 = "01"+operand2.substring(eLength+1);
       	String mulE = "";
       	String mulDecimal = "";
       	String ESign = "";
          if(sign1.equals(sign2))
        	  	sign = "0";
          else
        	  	sign = "1";
          if(exponent1.contains("0")==false){
                  result = "1"+operand1;
              return result;
          }
          else if(exponent2.contains("0")==false){
                  result = "1"+operand2;
              return result;
          }
          if(operand1.substring(1).contains("1")==false||
        		  operand2.substring(1).contains("1")==false){
                  overflow = "0";
                  for(int i = 0;i<eLength+sLength;i++){
                          result+="0";
                  }
                  result = overflow+sign+result;
                  return result;
          }
         mulDecimal = integerMultiplication(decimal1,decimal2,2*sLength+4).substring(3,3+sLength);
         System.out.println(mulDecimal);
         String s = signedAddition("0"+exponent1,"0"+exponent2,eLength+1).substring(2);
         mulE = signedAddition(s,strOffset,eLength+1).substring(2);
         ESign = mulE.substring(0,1);
         if(mulE.contains("0")==false||(ESign.equals("0")&&exponent1.substring(0,1).equals("1")
        		 &&exponent2.substring(0,1).equals("1"))){
        	  		overflow = "1";
        	  		for(int i = 0;i<eLength;i++){
        	  			result+="1";
        	  		}
        	  		for(int i = 0;i<sLength;i++){
        	  			result+="0";
        	  		}
        	  		result = overflow+sign+result;
        	  		return result;
          }
          	result = overflow+sign+mulE+mulDecimal;
          	return result;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 *
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		String result = "";
		String sign = "";
		String overflow = "0";
		String sign1 = operand1.substring(0,1);
		String sign2 = operand2.substring(0,1);
		String exponent1 = operand1.substring(1,eLength+1);
		String exponent2 = operand2.substring(1,eLength+1);
		String exponent = "";
		String decimal1 = "1"+operand1.substring(eLength+1);
		String decimal2 = "1"+operand2.substring(eLength+1);
		String decimal = "";
		if(sign1.equals(sign2))
			sign = "0";
		else
			sign = "1";
		if(operand1.substring(1).contains("1")==false){
			result = "0"+sign;
			for(int i = 0;i<eLength+sLength;i++){
				result+="0";
			}
			return result;
		}
		if(operand2.substring(1).contains("1")==false){
			sign = sign2;
			for(int i = 0;i<eLength;i++){
				exponent+="1";
			}
			for(int i = 0;i<sLength;i++){
				decimal+="0";
			}
			result = overflow+sign2+exponent+decimal;
			return result;
		}
		if(sign1.equals(sign2))
			sign = "0";
		else
			sign = "1";
		int expVal1 = Integer.parseInt(integerTrueValue("0"+exponent1));
		int expVal2 = Integer.parseInt(integerTrueValue("0"+exponent2));
		
		int expVal = expVal1-expVal2+(int)Math.pow(2,eLength-1)-1;
		exponent = integerRepresentation(expVal+"",eLength);
		System.out.println(exponent);
		for(int i = 0;i<sLength;i++){
			decimal1+="0";
			decimal2 = "0"+decimal2;
		}
		System.out.println("de1:"+decimal1);
		System.out.println("de2:"+decimal2);
		decimal = integerDivision("011100000000000000","000000000100000000",32);
		
		decimal = integerDivision(decimal1,decimal2,4*sLength);
		int totalLength = decimal.length();
		System.out.println(decimal);
		decimal = decimal.substring(totalLength-5*sLength,totalLength-4*sLength);
		System.out.println(decimal);
		result  = overflow+sign+exponent+decimal;
		return result;
	}
	public char and(char c, char d) {
		if(c=='0'||d=='0')
			return '0';
		else if(c=='1'&&d=='1')
			return '1';
		else
			return '0';
	}
	public char or(char c, char d) {
		if(c=='1'||d=='1')
			return '1';
		else
			return '0';
	}
	public char not(char c){
		if(c=='1')
			return '0';
		else if(c=='0')
			return '1';
		return 0;
	}
}
