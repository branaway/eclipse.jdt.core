/*******************************************************************************
 * Copyright (c) 2003, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This is an implementation of an early-draft specification developed under the Java
 * Community Process (JCP) and is made available for testing and evaluation purposes
 * only. The code is not compatible with any specification of the JCP.
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.core.tests.compiler.regression;

import junit.framework.Test;
public class TryStatement17Test extends AbstractRegressionTest {

static {
//	TESTS_NAMES = new String[] { "test061" };
//	TESTS_NUMBERS = new int[] { 40, 41, 43, 45, 63, 64 };
//	TESTS_RANGE = new int[] { 11, -1 };
}
public TryStatement17Test(String name) {
	super(name);
}
public static Test suite() {
	return buildMinimalComplianceTestSuite(testClass(), F_1_7);
}
public void test001() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"import java.io.*;\n" + 
			"\n" + 
			"public class X {\n" + 
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			System.out.println();\n" + 
			"			Reader r = new FileReader(args[0]);\n" + 
			"			r.read();\n" + 
			"		} catch(IOException | FileNotFoundException e) {\n" + 
			"			e.printStackTrace();\n" + 
			"		}\n" + 
			"	}\n" + 
			"}",
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 9)\n" + 
		"	} catch(IOException | FileNotFoundException e) {\n" + 
		"	        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" + 
		"The exception FileNotFoundException is already caught by the exception IOException\n" + 
		"----------\n");
}
public void test002() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"import java.io.*;\n" + 
			"\n" + 
			"public class X {\n" + 
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			System.out.println();\n" + 
			"			Reader r = new FileReader(args[0]);\n" + 
			"			r.read();\n" + 
			"		} catch(FileNotFoundException | FileNotFoundException | IOException e) {\n" + 
			"			e.printStackTrace();\n" + 
			"		}\n" + 
			"	}\n" + 
			"}",
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 9)\n" + 
		"	} catch(FileNotFoundException | FileNotFoundException | IOException e) {\n" + 
		"	        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" + 
		"The exception FileNotFoundException is already caught by the exception FileNotFoundException\n" + 
		"----------\n");
}
public void test003() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"import java.io.*;\n" + 
			"\n" + 
			"public class X {\n" + 
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			System.out.println();\n" + 
			"			Reader r = new FileReader(args[0]);\n" + 
			"			r.read();\n" + 
			"		} catch(FileNotFoundException e) {" +
			"			e.printStackTrace();\n" + 
			"		} catch(FileNotFoundException | IOException e) {\n" + 
			"			e.printStackTrace();\n" + 
			"		}\n" + 
			"	}\n" + 
			"}",
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 10)\n" + 
		"	} catch(FileNotFoundException | IOException e) {\n" + 
		"	        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" + 
		"Unreachable catch block for FileNotFoundException. It is already handled by the catch block for FileNotFoundException\n" + 
		"----------\n");
}
public void test004() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"import java.io.*;\n" + 
			"\n" + 
			"public class X {\n" + 
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			System.out.println();\n" + 
			"			Reader r = new FileReader(args[0]);\n" + 
			"			r.read();\n" + 
			"		} catch(RuntimeException | Exception e) {" +
			"			e.printStackTrace();\n" + 
			"		} catch(FileNotFoundException | IOException e) {\n" + 
			"			e.printStackTrace();\n" + 
			"		}\n" + 
			"	}\n" + 
			"}",
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 10)\n" + 
		"	} catch(FileNotFoundException | IOException e) {\n" + 
		"	        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n" + 
		"Unreachable catch block for FileNotFoundException. It is already handled by the catch block for Exception\n" + 
		"----------\n");
}
public void test005() {
	this.runConformTest(
		new String[] {
			"X.java",
			"import java.io.*;\n" + 
			"\n" + 
			"public class X {\n" + 
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			System.out.println();\n" + 
			"			Reader r = new FileReader(\"Zork\");\n" + 
			"			r.read();\n" + 
			"		} catch(NumberFormatException | RuntimeException e) {\n" + 
			"			e.printStackTrace();\n" + 
			"		} catch(FileNotFoundException | IOException e) {\n" + 
			"			// ignore\n" + 
			"		}\n" + 
			"	}\n" + 
			"}",
		},
		"");
}
//Test that lub is not used for checking for checking the exceptions
public void test006() {
	this.runNegativeTest(
		new String[] {
			"X.java",

			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new Foo();\n"+
			"		} catch(SonOfFoo | DaughterOfFoo e) {\n" +
			"			e.printStackTrace();\n" +
			"		}\n" + 
			"	}\n" + 
			"}\n" +
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 4)\n" + 
		"	throw new Foo();\n" + 
		"	^^^^^^^^^^^^^^^^\n" + 
		"Unhandled exception type Foo\n" + 
		"----------\n" +  
		"2. WARNING in X.java (at line 10)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 11)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"4. WARNING in X.java (at line 12)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}
public void test007() {
	this.runConformTest(
		new String[] {
			"X.java",

			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new Foo();\n"+
			"		} catch(SonOfFoo | DaughterOfFoo e) {\n" +
			"			System.out.println(\"Caught lub\");\n" +
			"		} catch(Foo e) {\n" +
			"           System.out.println(\"Caught Foo\");\n" + 
			"        }\n" +
			"	}\n" + 
			"}\n" +
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		}, 
		"Caught Foo");
}
// test that lub is not used for precise rethrow
public void test008() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			if (args.length == 0) throw new SonOfFoo();\n"+
			"			throw new DaughterOfFoo();\n" +
			"		} catch(SonOfFoo | DaughterOfFoo e) {\n" +
			"			try {\n" +
			"				throw e;\n" +
			"			} catch(SonOfFoo | DaughterOfFoo e1) {}\n"+
			"		}\n" + 
			"	}\n" + 
			"}\n" +
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" +  
		"1. WARNING in X.java (at line 13)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"2. WARNING in X.java (at line 14)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 15)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}
public void test009() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"import java.io.*;\n" + 
			"\n" + 
			"public class X {\n" + 
			"	public static void main(String[] args) {\n" + 
			"		try {\n" +
			"			throw new IOException();\n" +
			"		} catch(IOException | RuntimeException e) {\n" + 
			"			e = new IOException();\n" +
			"		}\n" + 
			"	}\n" + 
			"}",
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 8)\n" + 
		"	e = new IOException();\n" + 
		"	^\n" + 
		"The parameter e of a multi-catch block cannot be assigned\n" + 
		"----------\n");
}
//Test that disjunctive type checks are done for a precise throw too
public void test010() {
	this.runNegativeTest(
		new String[] {
			"X.java",

			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new DaughterOfFoo();\n"+
			"		} catch(SonOfFoo | DaughterOfFoo e) {\n" +
			"			e.printStackTrace();\n" +
			"		}\n" + 
			"	}\n" + 
			"}\n" +
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 5)\n" + 
		"	} catch(SonOfFoo | DaughterOfFoo e) {\n" + 
		"	        ^^^^^^^^\n" + 
		"Unreachable catch block for SonOfFoo. This exception is never thrown from the try statement body\n" + 
		"----------\n" + 
		"2. WARNING in X.java (at line 10)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 11)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"4. WARNING in X.java (at line 12)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}
// Test that a rethrow is precisely computed
public void test011() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new DaughterOfFoo();\n"+
			"		} catch(Foo e) {\n" + 
			"			try {\n" +
			"				throw e;\n" +
			"			} catch (SonOfFoo e1) {\n" +
			"			 	e1.printStackTrace();\n" +
			"			} catch (Foo e1) {}\n" +
			"		}\n" + 
			"	}\n" + 
			"}\n"+
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 8)\n" + 
		"	} catch (SonOfFoo e1) {\n" + 
		"	         ^^^^^^^^\n" + 
		"Unreachable catch block for SonOfFoo. This exception is never thrown from the try statement body\n" + 
		"----------\n" + 
		"2. WARNING in X.java (at line 14)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 15)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"4. WARNING in X.java (at line 16)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}
//Test that a rethrow is precisely computed
public void test012() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new DaughterOfFoo();\n"+
			"		} catch(Foo e) {\n" + 
			"			try {\n" +
			"				throw e;\n" +
			"			} catch (SonOfFoo e1) {\n" +
			"			 	e1.printStackTrace();\n" +
			"			} catch (Foo e1) {}\n" +
			"			finally {" +
			"				System.out.println(\"\");}\n" +
			"		}\n" + 
			"	}\n" + 
			"}\n"+
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 8)\n" + 
		"	} catch (SonOfFoo e1) {\n" + 
		"	         ^^^^^^^^\n" + 
		"Unreachable catch block for SonOfFoo. This exception is never thrown from the try statement body\n" + 
		"----------\n" + 
		"2. WARNING in X.java (at line 15)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 16)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"4. WARNING in X.java (at line 17)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}
// Test that if the rethrow argument is modified (not effectively final), then it is not precisely 
// computed
public void test013() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new DaughterOfFoo();\n"+
			"		} catch(Foo e) {\n" + 
			"			try {\n" +
			"				e = new Foo();\n" +
			"				throw e;\n" +
			"			} catch (SonOfFoo e1) {\n" +
			"			 	e1.printStackTrace();\n" +
			"			} catch (Foo e1) {}\n"+
			"		}\n" + 
			"	}\n" + 
			"}\n"+
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" +
		"1. WARNING in X.java (at line 15)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"2. WARNING in X.java (at line 16)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 17)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}

// Test that if the rethrow argument is modified in a different flow (not effectively final), then also precise throw
// should not be computed
public void test014() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new DaughterOfFoo();\n"+
			"		} catch(Foo e) {\n" + 
			"			try {\n" +
			"				boolean DEBUG = true;\n" +
			"				if (DEBUG) {\n" +
			"					throw e;\n"+
			"				}" +
			"				e = new Foo();\n" +
			"				e.printStackTrace();\n"+
			"			} catch (SonOfFoo e1) {\n" +
			"			 	e1.printStackTrace();\n" +
			"			} catch (Foo e1) {}\n"+
			"		}\n" + 
			"	}\n" + 
			"}\n"+
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" +
		"1. WARNING in X.java (at line 18)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"2. WARNING in X.java (at line 19)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 20)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}

// test015 moved into org.eclipse.jdt.core.tests.compiler.regression.TryStatementTest.test070()

// Test precise rethrow works good even in nested try catch block
public void test016() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"	public static void main(String[] args) {\n" + 
			"		try {\n" + 
			"			throw new DaughterOfFoo();\n"+
			"		} catch(Foo e) {\n" + 
			"			try {\n" +
			"				throw new Foo();\n" +
			"			} catch (Foo e1) {\n" +
			"				try {\n" +
			"					throw e;\n" + 
			"				} catch (SonOfFoo e2) {\n" +
			"			 		e1.printStackTrace();\n" +
			"				} catch (Foo e3) {}\n" +
			"			}\n" +
			"		}\n" + 
			"	}\n" + 
			"}\n"+
			"class Foo extends Exception {}\n"+
			"class SonOfFoo extends Foo {}\n"+
			"class DaughterOfFoo extends Foo {}\n"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 11)\n" + 
		"	} catch (SonOfFoo e2) {\n" + 
		"	         ^^^^^^^^\n" + 
		"Unreachable catch block for SonOfFoo. This exception is never thrown from the try statement body\n" + 
		"----------\n" + 
		"2. WARNING in X.java (at line 18)\n" + 
		"	class Foo extends Exception {}\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"3. WARNING in X.java (at line 19)\n" + 
		"	class SonOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"4. WARNING in X.java (at line 20)\n" + 
		"	class DaughterOfFoo extends Foo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}
// Test lub computation.
public void test017() {
	this.runNegativeTest(
		new String[] {
			"X.java",
			"public class X {\n" +
			"    public static void main(String [] args) {\n" +
			"        doSomething(false);\n" +
			"    }\n" +
			"    public static void doSomething (boolean bool) {\n" +
			"        try {\n" +
			"            if (bool)\n" +
			"                throw new GrandSonOfFoo();\n" +
			"            else \n" +
			"                throw new GrandDaughterOfFoo();\n" +
			"        } catch(SonOfFoo | DaughterOfFoo e) {\n" +
			"        	SonOfFoo s = e;\n" +
			"        	e.callableOnBothGenders();\n" +
			"        	e.callableOnlyOnMales();\n" +
			"        	e.callableOnlyOnFemales();\n" +
			"        }\n" +
			"    }\n" +
			"}\n" +
			"class Foo extends Exception {\n" +
			"	void callableOnBothGenders () {\n" +
			"	}\n" +
			"}\n" +
			"class SonOfFoo extends Foo {\n" +
			"	void callableOnlyOnMales() {\n" +
			"	}\n" +
			"}\n" +
			"class GrandSonOfFoo extends SonOfFoo {}\n" +
			"class DaughterOfFoo extends Foo {\n" +
			"	void callableOnlyOnFemales() {\n" +
			"	}\n" +
			"}\n" +
			"class GrandDaughterOfFoo extends DaughterOfFoo {}\n"
		},
		"----------\n" + 
		"1. ERROR in X.java (at line 12)\n" + 
		"	SonOfFoo s = e;\n" + 
		"	             ^\n" + 
		"Type mismatch: cannot convert from Foo to SonOfFoo\n" + 
		"----------\n" + 
		"2. ERROR in X.java (at line 14)\n" + 
		"	e.callableOnlyOnMales();\n" + 
		"	  ^^^^^^^^^^^^^^^^^^^\n" + 
		"The method callableOnlyOnMales() is undefined for the type Foo\n" + 
		"----------\n" + 
		"3. ERROR in X.java (at line 15)\n" + 
		"	e.callableOnlyOnFemales();\n" + 
		"	  ^^^^^^^^^^^^^^^^^^^^^\n" + 
		"The method callableOnlyOnFemales() is undefined for the type Foo\n" + 
		"----------\n" + 
		"4. WARNING in X.java (at line 19)\n" + 
		"	class Foo extends Exception {\n" + 
		"	      ^^^\n" + 
		"The serializable class Foo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"5. WARNING in X.java (at line 23)\n" + 
		"	class SonOfFoo extends Foo {\n" + 
		"	      ^^^^^^^^\n" + 
		"The serializable class SonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"6. WARNING in X.java (at line 27)\n" + 
		"	class GrandSonOfFoo extends SonOfFoo {}\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class GrandSonOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"7. WARNING in X.java (at line 28)\n" + 
		"	class DaughterOfFoo extends Foo {\n" + 
		"	      ^^^^^^^^^^^^^\n" + 
		"The serializable class DaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n" + 
		"8. WARNING in X.java (at line 32)\n" + 
		"	class GrandDaughterOfFoo extends DaughterOfFoo {}\n" + 
		"	      ^^^^^^^^^^^^^^^^^^\n" + 
		"The serializable class GrandDaughterOfFoo does not declare a static final serialVersionUID field of type long\n" + 
		"----------\n");
}

public static Class testClass() {
	return TryStatement17Test.class;
}
}
