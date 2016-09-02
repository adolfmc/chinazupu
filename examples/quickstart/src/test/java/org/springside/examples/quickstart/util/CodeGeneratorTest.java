package org.springside.examples.quickstart.util;

import org.junit.Assert;
import org.junit.Test;

public class CodeGeneratorTest {

	public CodeGeneratorTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void S000(){
		Assert.assertEquals(CodeGenerator.getNext("000"), "000000");
	}
	
	@Test
	public void S000000(){
		Assert.assertEquals(CodeGenerator.getNext("000000"), "000001");
	}
	
	@Test
	public void S000001(){
		Assert.assertEquals(CodeGenerator.getNext("000001"), "000002");
	}
	
	@Test
	public void S00000a(){
		Assert.assertEquals(CodeGenerator.getNext("00000a"), "00000b");
	}
	
	@Test
	public void S00000Z(){
		Assert.assertEquals(CodeGenerator.getNext("00000Z"), "000010");
	}
}
