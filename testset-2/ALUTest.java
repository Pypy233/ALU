import static org.junit.Assert.*;

import org.junit.Test;

public class ALUTest {
	ALU test=new ALU();
	
	@Test
	public void notTest(){
		assertEquals('0',test.not('1'));
		assertEquals('1',test.not('0'));
	}
	
	@Test
	public void andTest(){
		assertEquals('0',test.and('1','0'));
		assertEquals('1',test.and('1','1'));
		assertEquals('0',test.and('0','0'));
		assertEquals('0',test.and('0','1'));
	}
	
	@Test
	public void orTest(){
		assertEquals('1',test.or('1','0'));
		assertEquals('1',test.or('1','1'));
		assertEquals('0',test.or('0','0'));
		assertEquals('1',test.or('0','1'));
	}
	
	@Test
	public void xorTest(){
		assertEquals('1',test.or('1','0'));
		assertEquals('0',test.or('1','1'));
		assertEquals('0',test.or('0','0'));
		assertEquals('1',test.or('0','1'));
	}

	@Test
	public void integerRepresentationTest() {
		assertEquals("00001001",test.integerRepresentation("9", 8));
		assertEquals("00000000000000000000000000001001",test.integerRepresentation("9", 32));
		assertEquals("01111111111111111111111111111111",test.integerRepresentation("2147483647", 32));
		assertEquals("10000000000000000000000000000000",test.integerRepresentation("-2147483648", 32));
		assertEquals("0000000000000000000000000000000000000000000000000000000000001001",test.integerRepresentation("9", 64));
		assertEquals("111111111111111111111111111111111111111111111111111111111110000",test.integerRepresentation("-16", 63));
		assertEquals("111111111111111111111111111111111111111111111111111111111111111",test.integerRepresentation("-1", 63));
		assertEquals("00000000000000000000000000001001",test.integerRepresentation("9", 32));
		assertEquals("1000",test.integerRepresentation("-8", 4));
		assertEquals("1001",test.integerRepresentation("-7", 4));
		assertEquals("",test.integerRepresentation("-7", 0));
		assertEquals("00001001",test.integerRepresentation("9", 8));
	}
	
	@Test
	public void floatRepresentationTest(){
		assertEquals("0000000000000",test.floatRepresentation("0",4,8));
		assertEquals("1000000000000",test.floatRepresentation("-0.0",4,8));
		assertEquals("1000000000000",test.floatRepresentation("-0.00000000000",4,8));
		assertEquals("0010100000000",test.floatRepresentation("0.25",4,8));
		assertEquals("1010100000000",test.floatRepresentation("-0.25",4,8));
		assertEquals("0000000000000",test.floatRepresentation("0.000000",4,8));
		assertEquals("1000000000000",test.floatRepresentation("-0.000",4,8));
		assertEquals("0111011111111",test.floatRepresentation("255.5",4,8));
		assertEquals("0011111111111",test.floatRepresentation("1.99609375",4,8));
		assertEquals("0111100000000",test.floatRepresentation("256.0",4,8));
		assertEquals("1111100000000",test.floatRepresentation("-123546.154",4,8));
		assertEquals("0111100000000",test.floatRepresentation("+Inf",4,8));
		assertEquals("1111111100000000",test.floatRepresentation("-Inf",7,8));
		assertEquals("0000000000000000",test.floatRepresentation("0.00000000000000000000000000000001",7,8));
		assertEquals("0000110000000",test.floatRepresentation("0.0234375",4,8));
		assertEquals("0000010000000",test.floatRepresentation("0.0078125",4,8));
		assertEquals("01000001001101100000",test.floatRepresentation("11.375",8,11));
	}
	
	@Test
	public void ieee754Test(){
		assertEquals("00000000000000000000000000000000",test.ieee754("0.0",32));
		assertEquals("00111111000000000000000000000000",test.ieee754("0.5",32));
		assertEquals("01000001001101100000000000000000",test.ieee754("11.375",32));
	}
	
	@Test
	public void integerTrueValueTest(){
		assertEquals("9",test.integerTrueValue("00001001"));
		assertEquals("-7",test.integerTrueValue("1001"));
		assertEquals("-7",test.integerTrueValue("11111001"));
		assertEquals("2147483647",test.integerTrueValue("01111111111111111111111111111111"));
		assertEquals("-2147483648",test.integerTrueValue("10000000000000000000000000000000"));
		assertEquals("-16",test.integerTrueValue("111111111111111111111111111111111111111111111111111111111110000"));
		assertEquals("9",test.integerTrueValue("00001001"));
	}
	
	@Test
	public void floatTrueValueTest(){
		assertEquals("10.0",test.floatTrueValue("01000001001000000000", 8, 11));
		assertEquals("1.0",test.floatTrueValue("00111111100000000000", 8, 11));
		assertEquals("-1.0",test.floatTrueValue("10111111100000000000", 8, 11));
		assertEquals("11.375",test.floatTrueValue("01000001001101100000", 8, 11));
		assertEquals("-11.375",test.floatTrueValue("11000001001101100000", 8, 11));
		assertEquals("+Inf",test.floatTrueValue("01111111100000000000", 8, 11));
		assertEquals("-Inf",test.floatTrueValue("11111100000000000", 5, 11));
		assertEquals("NaN",test.floatTrueValue("01111111101101100000", 8, 11));
		assertEquals("NaN",test.floatTrueValue("111111111101101100000", 9, 11));
		assertEquals("0.000030517578125",test.floatTrueValue("00000010000000000", 5, 11));
		assertEquals("0",test.floatTrueValue("00000000000000000", 5, 11));
		assertEquals("0",test.floatTrueValue("10000000000000000", 5, 11));
		assertEquals("0.0078125",test.floatTrueValue("0000010000000", 4, 8));
		assertEquals("0.0234375",test.floatTrueValue("0000110000000", 4, 8));
		assertEquals("11.375",test.floatTrueValue("01000001001101100000", 8, 11));
		
	}
	
	@Test
	public void negationTest(){
		assertEquals("111111",test.negation("000000"));
		assertEquals("11110110",test.negation("00001001"));
	}
	
	@Test
	public void leftShiftTest(){
		assertEquals("00100100",test.leftShift("00001001", 2));
	}
	
	@Test
	public void logRightShiftTest(){
		assertEquals("00111101",test.logRightShift("11110110", 2));
	}
	
	@Test
	public void ariRightShiftTest(){
		assertEquals("11111101",test.ariRightShift("11110110", 2));
	}
	
	@Test
	public void fullAdderTest(){
		assertEquals("01",test.fullAdder('1','0','0'));
		assertEquals("01",test.fullAdder('0','1','0'));
		assertEquals("01",test.fullAdder('0','0','1'));
		assertEquals("10",test.fullAdder('1','1','0'));
		assertEquals("10",test.fullAdder('1','0','1'));
		assertEquals("10",test.fullAdder('0','1','1'));
		assertEquals("11",test.fullAdder('1','1','1'));
	}
	
	@Test
	public void claAdderTest(){
		assertEquals("01011",test.claAdder("1001","0001",'1'));
		assertEquals("10011",test.claAdder("1001","1001",'1'));
		assertEquals("11111",test.claAdder("1111","1111",'1'));
		assertEquals("00001",test.claAdder("0000","0000",'1'));
	}
	
	@Test
	public void oneAdderTest(){
		assertEquals("01010",test.oneAdder("1001"));
		assertEquals("01110",test.oneAdder("1101"));
		assertEquals("00000",test.oneAdder("1111"));
		assertEquals("0111100",test.oneAdder("111011"));
		assertEquals("000000000",test.oneAdder("11111111"));
		assertEquals("000001010",test.oneAdder("00001001"));
		assertEquals("110000000",test.oneAdder("01111111"));
	}
	@Test
	public void adderTest(){
		assertEquals("011111010",test.adder("1001","0001",'0',8));
		assertEquals("011110111",test.adder("1001","1101",'1',8));
		assertEquals("00101",test.adder("0111","1101",'1',4));
		assertEquals("011111010",test.adder("01001","10001",'0',8));
		assertEquals("00000000000000011",test.adder("01","01",'1',16));
		assertEquals("10111111111111110",test.adder("1000000000000101","1001",'0',16));
		assertEquals("000000111",test.adder("0100","0011",'0',8));
	}
	
	@Test
	public void integerAdditionTest(){
		assertEquals("011111010",test.integerAddition("1001","0001",8));
		assertEquals("011110110",test.integerAddition("1001","1101",8));
		assertEquals("011111010",test.integerAddition("01001","10001",8));
		assertEquals("00000000000000010",test.integerAddition("01","01",16));
		assertEquals("10111111111111110",test.integerAddition("1000000000000101","1001",16));
		assertEquals("000000111",test.integerAddition("0100","0011",8));
	}
	
	@Test
	public void integerSubtractionTest(){
		assertEquals("011111000",test.integerSubtraction("1001","0001",8));
		assertEquals("000000000",test.integerSubtraction("1001","1001",8));
		assertEquals("000000000",test.integerSubtraction("0001","0001",8));
		assertEquals("0000000000100",test.integerSubtraction("000101","0001",12));
		assertEquals("000000010",test.integerSubtraction("0001","1111",8));
		assertEquals("000000001",test.integerSubtraction("0100","0011",8));
	}
	
	@Test
	public void integerMultiplicationTest(){
		assertEquals("000001100",test.integerMultiplication("0100","0011",8));
		assertEquals("000000110",test.integerMultiplication("0010","0011",8));
		assertEquals("011111000",test.integerMultiplication("1110","0100",8));
		assertEquals("000000110",test.integerMultiplication("1110","1101",8));
		assertEquals("11100",test.integerMultiplication("0100","0011",4));
		assertEquals("10101",test.integerMultiplication("0111","0011",4));
		assertEquals("000001100",test.integerMultiplication("0100","0011",8));
	}
	
	@Test
	public void integerDivisionTest(){
		assertEquals("011101111",test.integerDivision("1001","0011",4));
		assertEquals("01111111011111111",test.integerDivision("1001","0011",8));
		assertEquals("000010010",test.integerDivision("0110","0100",4));
		assertEquals("011111110",test.integerDivision("1010","0100",4));
		assertEquals("000011110",test.integerDivision("1010","1100",4));
		assertEquals("011110010",test.integerDivision("0110","1100",4));
		assertEquals("011100000",test.integerDivision("0110","1101",4));
		assertEquals("011111101",test.integerDivision("1010","0011",4));
		assertEquals("001011111",test.integerDivision("1010","1111",4));
		assertEquals("001111111",test.integerDivision("1000","1111",4));
		assertEquals("00000000100000001",test.integerDivision("0100","0011",8));
	}
	
	@Test
	public void signedAdditionTest(){
		assertEquals("0100000111",test.signedAddition("1100","1011",8));
		assertEquals("0000000110",test.signedAddition("11001","001111",8));
		assertEquals("010111",test.signedAddition("1100","1011",4));
		assertEquals("100111",test.signedAddition("01101","01010",4));
		assertEquals("0100001001",test.signedAddition("11100","0011",8));
		assertEquals("011001",test.signedAddition("11100","00011",4));
		assertEquals("0100000111",test.signedAddition("1100","1011",8));
	}
	
	@Test
	public void floatAdditionTest(){
		assertEquals("000111111101110000",test.floatAddition("00111111010100000", "00111111001000000", 8, 8, 8));
		assertEquals("000111101100000000000000000000000",test.floatAddition("00111111000000000000000000000000", "10111110111000000000000000000000", 8, 23, 6));
		assertEquals("000000011100000",test.floatAddition("00000010100000", "00000001000000", 5, 8, 6));
		assertEquals("010000001100000",test.floatAddition("10000010100000", "00000001000000", 5, 8, 6));
		assertEquals("001111010100000",test.floatAddition("01111010100000", "00000001000000", 5, 8, 6));
		assertEquals("111111100000000",test.floatAddition("11111100000000", "01100001000000", 5, 8, 6));
		assertEquals("000000000000000",test.floatAddition("11111100000000", "01111100000000", 5, 8, 6));
		assertEquals("111111100000000",test.floatAddition("11111100000000", "11111100000000", 5, 8, 6));
		assertEquals("001111011100000",test.floatAddition("00011101110000", "01111011100000", 5, 8, 6));
		assertEquals("001111011101011",test.floatAddition("01100101110000", "01111011100000", 5, 8, 6));
		assertEquals("101111100000000",test.floatAddition("01110101110000", "01111011100000", 5, 8, 6));
		assertEquals("000010000010100",test.floatAddition("00001111110000", "00000011100000", 5, 8, 6));
		assertEquals("010001110111000",test.floatAddition("10001111110000", "00000011100000", 5, 8, 6));
		assertEquals("000000000000000",test.floatAddition("10001111110000", "00001111110000", 5, 8, 6));
		assertEquals("010010011110000",test.floatAddition("10001111110000", "10001111110000", 5, 8, 6));
		assertEquals("000111111101110000",test.floatAddition("00111111010100000", "00111111001000000", 8, 8, 4));
	}
	
	@Test
	public void extendedSignedAdditionTest(){
		assertEquals("01011000",test.extendedSignedAddition("0101111", "0101001"));
		assertEquals("00000110",test.extendedSignedAddition("0101111", "1101001"));
		assertEquals("10001010",test.extendedSignedAddition("0101111", "1111001"));
		assertEquals("00000000",test.extendedSignedAddition("0101111", "1101111"));
	}

	@Test
	public void floatSubtractionTest(){
		assertEquals("000000000000000",test.floatSubtraction("10001111110000", "10001111110000", 5, 8, 6));
		assertEquals("010001110111000",test.floatSubtraction("00000011100000", "00001111110000", 5, 8, 6));	
		assertEquals("111111100000000",test.floatSubtraction("01100001000000", "01111100000000", 5, 8, 6));
		assertEquals("000000000000000",test.floatSubtraction("10001111110000", "10001111110000", 5, 8, 6));
		assertEquals("000111110010000000",test.floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 4));
	}
	
	@Test
	public void floatMultiplicationTest(){
		assertEquals("000111110011000000",test.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
		assertEquals("000111110011000000000000000000000",test.floatMultiplication("00111111000000000000000000000000", "00111110111000000000000000000000", 8, 23));
		assertEquals("010000000000000",test.floatMultiplication("10000000000000", "00111100000000", 5, 8));
		assertEquals("111111100000000",test.floatMultiplication("11000011100000", "01111000001100", 5, 8));
		assertEquals("010000000000000",test.floatMultiplication("10000111000000", "00001100010000", 5, 8));	
		assertEquals("000001000000000",test.floatMultiplication("00000010000000", "01000100000000", 5, 8));
		assertEquals("000000100000000",test.floatMultiplication("00000010000000", "01000000000000", 5, 8));		
		assertEquals("000000010000000",test.floatMultiplication("00000010000000", "00111100000000", 5, 8));		
		assertEquals("000000001000000",test.floatMultiplication("00000010000000", "00111000000000", 5, 8));	
		assertEquals("010000010000000",test.floatMultiplication("10000010000000", "00111100000000", 5, 8));
		assertEquals("000000111000000",test.floatMultiplication("00011011000000", "00101000000000", 5, 8));
		assertEquals("000001010001000",test.floatMultiplication("00000111000000", "00111011000000", 5, 8));
		assertEquals("000000110001000",test.floatMultiplication("00000111000000", "00110111000000", 5, 8));
		assertEquals("000000011000100",test.floatMultiplication("00000111000000", "00110011000000", 5, 8));
		assertEquals("111111100000000",test.floatMultiplication("11111100000000", "00000011000000", 5, 8));
		assertEquals("000001000000",test.floatMultiplication("00000100000", "01000000000", 4, 6));
		assertEquals("000111110011000000",test.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
	}
	
	@Test
	public void unsignedMultiplicationTest(){
		assertEquals("011100000000",test.unsignedMultiplication("100000", "111000"));
		assertEquals("0000001111",test.unsignedMultiplication("00101", "00011"));
		assertEquals("0010000000",test.unsignedMultiplication("01000", "10000"));
		assertEquals("01101001",test.unsignedMultiplication("1111", "0111"));
	}
	
	@Test
	public void serialAdderTest(){
		assertEquals("011011",test.serialAdder("10001","01010",'0'));
		assertEquals("10100111",test.serialAdder("1111111","0101000",'0'));
		assertEquals("10101000",test.serialAdder("1111111","0101000",'1'));
	}

	@Test
	public void floatDivisionTest(){
		assertEquals("000111111011000000",test.floatDivision("00111110111000000", "00111111000000000", 8, 8));
		assertEquals("0001111110110000",test.floatDivision("001111101110000", "001111110000000", 8, 6));
		assertEquals("NaN",test.floatTrueValue(test.floatDivision("001111101110", "000000000000", 4, 7).substring(1), 4, 7));
		assertEquals("0100000000000",test.floatDivision("000000000000", "101111101110", 4, 7));
		assertEquals("0100000000000",test.floatDivision("000000000000", "111110000000", 4, 7));
		assertEquals("1011110000000",test.floatDivision("111110000000", "111100000000", 4, 7));
		assertEquals("0100000000000",test.floatDivision("111100000000", "011110000000", 4, 7));
		assertEquals("0001110000000",test.floatDivision("111110000000", "111110000000", 4, 7));
		assertEquals("1111110000000",test.floatDivision("111101000000", "000001000000", 4, 7));
		assertEquals("000111111011000000",test.floatDivision("00111110111000000", "00111111000000000", 8, 8));
	}
	
	@Test
	public void unsignedDivisionTest(){
		String[] results=test.unsignedDivision("111000", "100000");
		assertEquals("1110000",results[0]);
		assertEquals("0",results[1]);
		assertEquals("0000000",results[2]);

		results=test.unsignedDivision("001110", "100000");
		assertEquals("1110000",results[0]);
		assertEquals("-2",results[1]);
		assertEquals("0000000",results[2]);
		
		results=test.unsignedDivision("001100", "111000");
		assertEquals("0110110",results[0]);
		assertEquals("-2",results[1]);
		assertEquals("0110000",results[2]);
	}
}