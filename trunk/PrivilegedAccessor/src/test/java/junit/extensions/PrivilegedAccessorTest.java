package junit.extensions;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the class <code>PrivilegedAccessor</code>.
 */
public class PrivilegedAccessorTest extends TestCase {

   /**
    * An instance of a test-subclass.
    */
   private Child  child;

   /**
    * An instance of a test-subclass in a variable of type superclass.
    */
   private Parent childInParent;
   /**
    * an instance of a test-superclass.
    */
   private Parent parent;

   /**
    * Sets up the test-environment by instantiating the test-instances.
    * 
    * @see junit.framework.TestCase#setUp()
    */
   @Before
   public final void setUp() {
      this.parent = new Parent("Charlie");
      this.child = new Child("Charlie");
      this.childInParent = new Child("Charlie");
   }

   /**
    * Tears down the test-environment by deleting the test-instances.
    * 
    * @see junit.framework.TestCase#tearDown()
    */
   @After
   public final void tearDown() {
      this.parent = null;
      this.child = null;
      this.childInParent = null;
   }

   /**
    * Tests the constructor of PrivilegedAccessor and PrivilegedAccessor.
    * 
    * @throws Exception if something went wrong
    */
   @Test
   @SuppressWarnings("deprecation")
   public final void testInstantiationThrowsException() throws Exception {
      try {
         PrivilegedAccessor.instantiate(PrivilegedAccessor.class, null);
         fail("Instantiating PrivilegedAccessor should throw Exception - you must have enabled assertions to run unit-tests");
      } catch (InvocationTargetException e) {
         // this is what we expect
      }
   }

   /**
    * Tests the method <code>toString</code>
    */
   @Test
   @SuppressWarnings("deprecation")
   public final void testToString() throws Exception {
      assertEquals("java.lang.Object", PrivilegedAccessor.toString(new Object()));
      assertEquals(this.parent.toString(), PrivilegedAccessor.toString(this.parent));
      assertEquals(this.child.toString(), PrivilegedAccessor.toString(this.child));
      assertEquals(this.childInParent.toString(), PrivilegedAccessor.toString(this.childInParent));
   }

   /**
    * Tests the method <code>getFieldNames</code>.
    */
   @Test
   @SuppressWarnings("deprecation")
   public final void testGetFieldNames() throws Exception {
      Collection<String> testFieldNames = new ArrayList<String>();

      assertEquals(testFieldNames, PrivilegedAccessor.getFieldNames(null));

      assertEquals(testFieldNames, PrivilegedAccessor.getFieldNames(Object.class));

      testFieldNames.add("privateName");
      testFieldNames.add("privateObject");
      testFieldNames.add("privateStaticInt");
      testFieldNames.add("privateFinalInt");
      testFieldNames.add("privateFinalString");
      testFieldNames.add("privateStaticFinalInt");
      testFieldNames.add("privateStaticFinalString");
      assertEquals(testFieldNames, PrivilegedAccessor.getFieldNames(this.parent));

      testFieldNames = Arrays.asList(new String[] {"privateInt", "privateLong", "privateShort", "privateByte", "privateChar",
         "privateBoolean", "privateFloat", "privateDouble", "privateInts", "privateStrings", "privateObjects", "privateName",
         "privateObject", "privateStaticInt"});
      assertTrue("getFieldNames() returned wrong field names", PrivilegedAccessor.getFieldNames(this.child)
         .containsAll(testFieldNames));
      assertTrue("getFieldNames() returned wrong field names", PrivilegedAccessor.getFieldNames(this.childInParent).containsAll(
         testFieldNames));
   }

   /**
    * Tests the method <code>getMethodSignatures</code>.
    */
   @Test
   @SuppressWarnings("deprecation")
   public final void testGetMethodsignatures() throws Exception {
      Collection<String> testMethodSignatures = new ArrayList<String>();

      assertEquals(testMethodSignatures, PrivilegedAccessor.getMethodSignatures(null));

      testMethodSignatures = Arrays.asList(new String[] {"hashCode()", "getClass()", "finalize()", "clone()", "wait(long, int)",
         "wait()", "wait(long)", "registerNatives()", "equals(java.lang.Object)", "toString()", "notify()", "notifyAll()"});
      assertTrue("getMethodSignatures didn't return correct signatures", PrivilegedAccessor.getMethodSignatures(Object.class)
         .containsAll(testMethodSignatures));

      testMethodSignatures = Arrays.asList(new String[] {"equals(java.lang.Object)", "getName()", "setName(java.lang.String)",
         "setName()", "setStaticInt(int)", "hashCode()", "getClass()", "finalize()", "clone()", "wait(long, int)", "wait()",
         "wait(long)", "registerNatives()", "equals(java.lang.Object)", "toString()", "notify()", "notifyAll()"});
      assertTrue("getMethodSignatures didn't return correct signatures", PrivilegedAccessor.getMethodSignatures(this.parent)
         .containsAll(testMethodSignatures));
   }

   /**
    * Tests the method <code>getValue</code>.
    * 
    * @throws Exception
    * @see PrivilegedAccessor#getValue(Object, String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public final void testGetValue() throws Exception {
      assertEquals("Charlie", PrivilegedAccessor.getValue(this.parent, "privateName"));

      assertEquals("Charlie", PrivilegedAccessor.getValue(this.child, "privateName"));
      assertEquals(new Integer(8), PrivilegedAccessor.getValue(this.child, "privateInt"));

      assertEquals("Charlie", PrivilegedAccessor.getValue(this.childInParent, "privateName"));
      assertEquals(new Integer(8), PrivilegedAccessor.getValue(this.childInParent, "privateInt"));
   }

   /**
    * Tests the method <code>getValue</code> with a static field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#getValue(java.lang.Object, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testGetValueOfStaticField() throws Exception {
      assertEquals(new Integer(1), PrivilegedAccessor.getValue(this.parent, "privateStaticInt"));
      assertEquals(new Integer(1), PrivilegedAccessor.getValue(Parent.class, "privateStaticInt"));
   }

   /**
    * Tests the method <code>getValue</code> with a non-existing field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#getValue(java.lang.Object, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testGetValueOnInvalidField() throws Exception {
      try {
         PrivilegedAccessor.getValue(this.parent, "noSuchField");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.getValue(this.child, "noSuchField");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.getValue(this.childInParent, "noSuchField");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.getValue(Parent.class, "noSuchField");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.getValue(null, "noSuchField");
         fail("should throw InvalidParameterException");
      } catch (InvalidParameterException e) {
         // that is what we expect
      }
   }

   /**
    * Tests the method <code>instantiate</code>.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#instantiate(java.lang.Class, java.lang.Object[])
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInstantiate() throws Exception {
      assertEquals(this.parent, PrivilegedAccessor.instantiate(Parent.class, null));
      assertEquals(this.parent, PrivilegedAccessor.instantiate(Parent.class, new Object[0]));
      assertEquals(this.parent, PrivilegedAccessor.instantiate(Parent.class, new Object[] {"Charlie"}));
      assertEquals(this.child, PrivilegedAccessor.instantiate(Child.class, new Object[] {"Charlie", 8}));
      assertEquals(this.childInParent, PrivilegedAccessor.instantiate(Child.class, new Object[] {"Charlie", 8}));
   }

   /**
    * Tests the method <code>instantiate</code> with fully qualified types.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#instantiate(java.lang.Class, java.lang.Class[], java.lang.Object[])
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInstantiateWithTypes() throws Exception {
      assertEquals(this.parent, PrivilegedAccessor.instantiate(Parent.class, new Class[] {String.class, Object.class},
         new Object[] {"Charlie", "Brown"}));
      assertEquals(this.childInParent, PrivilegedAccessor.instantiate(Child.class, new Class[] {String.class, Integer.class},
         new Object[] {"Charlie", 8}));
   }

   /**
    * Tests the method <code>instantiate</code>.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#instantiate(java.lang.Class)
    */
   @Test
   @SuppressWarnings({"deprecation", "cast"})
   public void testInstantiateOnInvalidParameters() throws Exception {
      try {
         PrivilegedAccessor.instantiate(Parent.class, new Object[] {21});
         fail("instantiating with wrong parameter type should throw Exception");
      } catch (Exception e) {
         // this is what we expect
      }

      try {
         PrivilegedAccessor.instantiate(Parent.class, new Object[] {"Charlie", "Brown"});
         fail("instantiating with wrong second parameter type should throw Exception");
      } catch (Exception e) {
         // this is what we expect - that's why we need a instantiate with types
      }

      try {
         PrivilegedAccessor.instantiate(Parent.class, new Object[] {"Charlie", (Object) "Brown"});
         fail("instantiating with wrong second parameter type should throw Exception");
      } catch (Exception e) {
         // this is what we expect - that's why we need a instantiate with types
      }

      try {
         PrivilegedAccessor.instantiate(Child.class, new Object[] {"Charlie", "Brown"});
         fail("instantiating with wrong second parameter type should throw Exception");
      } catch (Exception e) {
         // this is what we expect
      }

      try {
         PrivilegedAccessor.instantiate(Child.class, new Class[] {String.class, String.class}, new Object[] {"Charlie", 8});
         fail("instantiating with unmatching parameter types should throw Exception");
         PrivilegedAccessor.instantiate(Child.class, new Class[] {String.class, Integer.class}, new Object[] {"Charlie", "Brown"});
         fail("instantiating with unmatching parameter types should throw Exception");
      } catch (Exception e) {
         // this is what we expect
      }

      try {
         PrivilegedAccessor.instantiate(Child.class, new Class[] {String.class, Integer.class, String.class}, new Object[] {
            "Charlie", 8, "Brown"});
         fail("instantiating with wrong parameter count should throw Exception");
      } catch (Exception e) {
         // this is what we expect
      }
   }

   /**
    * Tests the method <code>invokeMethod</code>.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethod() throws Exception {
      assertEquals("Charlie", PrivilegedAccessor.invokeMethod(this.parent, "getName()", null));

      PrivilegedAccessor.invokeMethod(this.parent, "setName(java.lang.String)", new Object[] {"Herbert"});
      assertEquals("Herbert", PrivilegedAccessor.getValue(this.parent, "privateName"));

      PrivilegedAccessor.invokeMethod(this.parent, "setName(java.lang.String)", new Object[] {null});
      assertEquals(null, PrivilegedAccessor.getValue(this.parent, "privateName"));

      PrivilegedAccessor.invokeMethod(this.parent, "setName()", null);
      assertEquals("Chaplin", PrivilegedAccessor.getValue(this.parent, "privateName"));

      PrivilegedAccessor.invokeMethod(this.child, "setName(java.lang.String)", new Object[] {"Hubert"});
      assertEquals("Hubert", PrivilegedAccessor.getValue(this.child, "privateName"));

      PrivilegedAccessor.invokeMethod(this.child, "setInt(int)", new Object[] {3});
      assertEquals(3, PrivilegedAccessor.invokeMethod(this.child, "getInt()", null));

      PrivilegedAccessor.invokeMethod(this.childInParent, "setName(java.lang.String)", new Object[] {"Norbert"});
      PrivilegedAccessor.invokeMethod(this.childInParent, "setInt(int)", new Object[] {3});
      assertEquals("Norbert", PrivilegedAccessor.getValue(this.childInParent, "privateName"));
      assertEquals(3, PrivilegedAccessor.getValue(this.childInParent, "privateInt"));
   }

   /**
    * Tests the method <code>invokeMethod</code> with different primitives.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithPrimitives() throws Exception {
      PrivilegedAccessor.invokeMethod(this.child, "setInt(int)", new Object[] {3});
      assertEquals(3, PrivilegedAccessor.invokeMethod(this.child, "getInt()", null));

      PrivilegedAccessor.invokeMethod(this.child, "setPrivateLong(long)", new Object[] {3L});
      assertEquals(3L, PrivilegedAccessor.invokeMethod(this.child, "getPrivateLong()", null));

      PrivilegedAccessor.invokeMethod(this.child, "setPrivateShort(short)", new Object[] {(short) 3});
      assertEquals((short) 3, PrivilegedAccessor.invokeMethod(this.child, "getPrivateShort()", null));

      PrivilegedAccessor.invokeMethod(this.child, "setPrivateByte(byte)", new Object[] {(byte) 3});
      assertEquals((byte) 3, PrivilegedAccessor.invokeMethod(this.child, "getPrivateByte()", null));

      PrivilegedAccessor.invokeMethod(this.child, "setPrivateBoolean(boolean)", new Object[] {true});
      assertEquals(true, PrivilegedAccessor.invokeMethod(this.child, "isPrivateBoolean()", null));

      PrivilegedAccessor.invokeMethod(this.child, "setPrivateChar(char)", new Object[] {'A'});
      assertEquals('A', PrivilegedAccessor.invokeMethod(this.child, "getPrivateChar()", null));

      PrivilegedAccessor.invokeMethod(this.child, "setPrivateFloat(float)", new Object[] {3.1f});
      assertEquals(3.1f, PrivilegedAccessor.invokeMethod(this.child, "getPrivateFloat()", null));

      PrivilegedAccessor.invokeMethod(this.child, "setPrivateDouble(double)", new Object[] {3.1});
      assertEquals(3.1, PrivilegedAccessor.invokeMethod(this.child, "getPrivateDouble()", null));
   }

   /**
    * Tests the method <code>invokeMethod</code> on a non-existing method.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodOnInvalidMethodName() throws Exception {
      try {
         PrivilegedAccessor.invokeMethod(this.child, "getInt", null);
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setInt)(", new Object[] {5});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setInt(", new Object[] {5});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "getInt)", null);
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "noSuchMethod()", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.parent, "noSuchMethod()", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.childInParent, "noSuchMethod()", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(Parent.class, "noSuchMethod()", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }
   }

   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithArray() throws Exception {
      Object[] args = {5};
      PrivilegedAccessor.invokeMethod(this.childInParent, "setInt(int)", args);
      assertEquals(5, PrivilegedAccessor.getValue(this.childInParent, "privateInt"));
   }

   /**
    * Tests the method <code>invokeMethod</code> with not fully declared types.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithNotFullyDeclaredTypes() throws Exception {
      PrivilegedAccessor.invokeMethod(this.parent, "setName(String)", new Object[] {"Hubert"});
      assertEquals("Hubert", PrivilegedAccessor.getValue(this.parent, "privateName"));

      PrivilegedAccessor.invokeMethod(this.parent, "setObject(Object)", new Object[] {"Heribert"});
      assertEquals("Heribert", PrivilegedAccessor.getValue(this.parent, "privateObject"));

      Collection<String> testCollection = new ArrayList<String>();
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateCollection(Collection)", new Object[] {testCollection});
      assertEquals(testCollection, PrivilegedAccessor.getValue(this.child, "privateCollection"));
   }

   /**
    * Tests the method <code>invokeMethod</code> with a typed collection.
    * 
    * @throws Exception
    * @see junit.extensions.PA#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithTypedCollection() throws Exception {
      Collection<String> testCollection = new ArrayList<String>();
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateCollection(java.util.Collection)", new Object[] {testCollection});
      assertEquals(testCollection, PrivilegedAccessor.getValue(this.child, "privateCollection"));
   }

   /**
    * Tests the method <code>invokeMethod</code> with invalid arguments.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithInvalidSignature() throws Exception {
      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect - since the signature is missing
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName java.lang.String)", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect - since the first brace is missing
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName(java.lang.String", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect - since the last brace is missing
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName(java.lang.SString)", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect - since the signature denotes a non-existing class
      }

   }

   /**
    * Tests the method <code>invokeMethod</code> with invalid arguments.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithInvalidArguments() throws Exception {
      try {
         PrivilegedAccessor.invokeMethod(this.child, "setData(java.lang.String)", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setData(non.existing.package.NonExistingClass)", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setData()", null);
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.parent, "setData(java.lang.String)", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.childInParent, "setData(java.lang.String)", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName(java.lang.String)", new Object[] {2});
         fail("should throw IllegalArgumentException");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName(.String)", new Object[] {"Heribert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName(string)", new Object[] {"Heribert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setName(NotAString)", new Object[] {"Heribert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setData(Integer)", new Object[] {2});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setInt(java.lang.String)", (Object[]) null);
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setInt(int)", new Object[] {"Herbert"});
         fail("should throw IllegalArgumentException");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(Parent.class, "setStaticInt(java.lang.String)", new Object[] {"Herbert"});
         fail("should throw NoSuchMethodException");
      } catch (NoSuchMethodException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setInt(int)", (Object[]) null);
         fail("should throw IllegalArgumentException");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {3, 4, null});
         fail("should throw IllegalArgumentException");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }
   }

   /**
    * Tests the method <code>invokeMethod</code> with several primitive arguments.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithMoreThanOnePrimitive() throws Exception {
      PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {5, 3});
      assertEquals(8, PrivilegedAccessor.getValue(this.child, "privateInt"));

      PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {new Integer(5), new Integer(4)});
      assertEquals(9, PrivilegedAccessor.getValue(this.child, "privateInt"));
   }

   /**
    * Tests the method <code>invokeMethod</code> with arrays as arguments.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithArrays() throws Exception {
      int[] numbers = new int[] {5, 3};
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateInts(int[])", new Object[] {numbers});
      assertEquals(numbers, PrivilegedAccessor.getValue(this.child, "privateInts"));

      numbers = new int[] {5};
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateInts(int[])", new Object[] {numbers});
      assertEquals(numbers, PrivilegedAccessor.getValue(this.child, "privateInts"));

      String[] strings = new String[] {"Hello", "Dolly"};
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateStrings(java.lang.String[])", new Object[] {strings});
      assertEquals(strings, PrivilegedAccessor.getValue(this.child, "privateStrings"));

      strings = new String[] {"Hello"};
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateStrings(java.lang.String[])", new Object[] {strings});
      assertEquals(strings, PrivilegedAccessor.getValue(this.child, "privateStrings"));
   }

   /**
    * Tests autoboxing not supported
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testAutoboxingNotSupported() throws Exception {
      try {
         PrivilegedAccessor.invokeMethod(this.child, "setPrivateInts(int[])", new Integer[] {1, 2});
         fail("invoking method with single values instead of array as parameters should raise exception");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }
   }

   /**
    * Tests the method <code>invokeMethod</code> with single values instead of an array.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithSingleValuesInsteadOfArray() throws Exception {
      String[] strings = new String[] {"Hello", "Bruno"};
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateStrings(java.lang.String[])", strings);
      assertEquals(strings, PrivilegedAccessor.getValue(this.child, "privateStrings"));

      Object[] objects = new Object[] {"Hello", new Integer(3)};
      PrivilegedAccessor.invokeMethod(this.child, "setPrivateObjects(java.lang.Object[])", objects);
      assertEquals(objects, PrivilegedAccessor.getValue(this.child, "privateObjects"));

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setPrivateStrings(java.lang.String[])", objects);
         fail("invoking method with object array instead of string array should raise exception");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }
   }

   /**
    * Tests the method <code>invokeMethod</code> with array of non primitives instead of several arguments. This is ok for several
    * reasons: a) downward compatibility - since this was the only way prior to varargs b) using varargs there is no possibility to
    * distinquish arrays from several arguments.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithArrayInsteadOfSingleValues() throws Exception {
      Object[] onumbers = new Object[] {3, 3};
      PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", onumbers);
      assertEquals(6, PrivilegedAccessor.getValue(this.child, "privateInt"));
   }

   /**
    * Tests the method <code>invokeMethod</code> with array of primitives instead of several arguments. This is not ok for several
    * reasons: a) downward compatibility - was not ok in the past (one had to use Object[]) b) this is the typical behaviour when using
    * varargs (Java doesn't autoconvert primitive arrays)
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithPrimitiveArrayInsteadOfSingleValues() throws Exception {
      try {
         PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {new int[] {5, 3}});
         fail("invoking method with an array of primitives instead of single primitives should raise exception");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {new Integer[] {4, 3}});
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }
   }

   /**
    * Tests the method <code>invokeMethod</code> with arrays of wrong length instead of several arguments.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithArraysOfWrongLengthInsteadOfSingleValues() throws Exception {
      try {
         PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {new int[] {1}});
         fail("invoking method with array of wrong size should raise exception");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {new Integer[] {2}});
         fail("invoking method with array of wrong size should raise exception");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.invokeMethod(this.child, "setSumOfTwoInts(int, int)", new Object[] {new Object[] {3}});
         fail("invoking method with array of wrong size should raise exception");
      } catch (IllegalArgumentException e) {
         // that is what we expect
      }
   }

   /**
    * Tests the method <code>invokeMethod</code> with arguments of type object and primitive.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeMethodWithObjectAndPrimitive() throws Exception {
      Object[] args = {"Marcus", 5};
      PrivilegedAccessor.invokeMethod(this.child, "setData(java.lang.String, int)", args);
      assertEquals("Marcus", PrivilegedAccessor.getValue(this.child, "privateName"));
      assertEquals(5, PrivilegedAccessor.getValue(this.child, "privateInt"));

      PrivilegedAccessor.invokeMethod(this.childInParent, "setData(java.lang.String, int)", args);
      assertEquals("Marcus", PrivilegedAccessor.getValue(this.childInParent, "privateName"));
      assertEquals(5, PrivilegedAccessor.getValue(this.childInParent, "privateInt"));
   }

   /**
    * Tests the method <code>invokeMethod</code> on a static method.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#invokeMethod(java.lang.Object, java.lang.String, java.lang.Object)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testInvokeStaticMethod() throws Exception {
      PrivilegedAccessor.invokeMethod(Parent.class, "setStaticInt(int)", new Object[] {3});
      assertEquals(3, PrivilegedAccessor.getValue(Parent.class, "privateStaticInt"));
   }

   /**
    * Tests the example given in javadoc of <code>setValue</code>.
    * 
    * This test could fail under some JVMs, since setting the value of final fields at other times than instantiation can have
    * unpredictable effects.
    * 
    * @see java.lang.reflect.Field.set(java.lang.Object, java.lang.Object)
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetGetValueExample() throws Exception {
      String myString = "Test";
      PrivilegedAccessor.setValue(myString, "value", new char[] {'T', 'e', 's', 't'}); // sets the final field value
      PrivilegedAccessor.setValue(myString.getClass(), "serialVersionUID", 1); // sets the static final field serialVersionUID

      assertTrue(Arrays.equals(new char[] {'T', 'e', 's', 't'}, (char[]) PrivilegedAccessor.getValue(myString, "value")));
      assertEquals(1L, PrivilegedAccessor.getValue(String.class, "serialVersionUID"));
   }

   /**
    * Tests the method <code>setValue</code>.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetGetValueWithPrimitives() throws Exception {
      PrivilegedAccessor.setValue(this.child, "privateInt", 6);
      assertEquals(6, PrivilegedAccessor.getValue(this.child, "privateInt"));

      PrivilegedAccessor.setValue(this.childInParent, "privateInt", 6);
      assertEquals(6, PrivilegedAccessor.getValue(this.childInParent, "privateInt"));

      PrivilegedAccessor.setValue(this.child, "privateLong", 8L);
      assertEquals(8L, PrivilegedAccessor.getValue(this.child, "privateLong"));

      PrivilegedAccessor.setValue(this.child, "privateShort", (short) 6);
      assertEquals((short) 6, PrivilegedAccessor.getValue(this.child, "privateShort"));

      PrivilegedAccessor.setValue(this.child, "privateByte", (byte) 2);
      assertEquals((byte) 2, PrivilegedAccessor.getValue(this.child, "privateByte"));

      PrivilegedAccessor.setValue(this.child, "privateChar", 'F');
      assertEquals('F', PrivilegedAccessor.getValue(this.child, "privateChar"));

      PrivilegedAccessor.setValue(this.child, "privateBoolean", true);
      assertEquals(true, PrivilegedAccessor.getValue(this.child, "privateBoolean"));

      PrivilegedAccessor.setValue(this.child, "privateFloat", 1.5f);
      assertEquals(1.5f, PrivilegedAccessor.getValue(this.child, "privateFloat"));

      PrivilegedAccessor.setValue(this.child, "privateDouble", 1.175);
      assertEquals(1.175, PrivilegedAccessor.getValue(this.child, "privateDouble"));
   }

   /**
    * Tests the method <code>setValue</code>.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetGetValueWithObjectsAndArrays() throws Exception {
      PrivilegedAccessor.setValue(this.parent, "privateName", "Hubert");
      assertEquals("Hubert", PrivilegedAccessor.getValue(this.parent, "privateName"));

      PrivilegedAccessor.setValue(this.child, "privateName", "Hubert");
      assertEquals("Hubert", PrivilegedAccessor.getValue(this.child, "privateName"));

      PrivilegedAccessor.setValue(this.childInParent, "privateName", "Hubert");
      assertEquals("Hubert", PrivilegedAccessor.getValue(this.childInParent, "privateName"));

      int[] numbers = new int[] {1, 2, 3};
      PrivilegedAccessor.setValue(this.child, "privateInts", numbers);
      assertEquals(numbers, PrivilegedAccessor.getValue(this.child, "privateInts"));

      String[] strings = new String[] {"Happy", "Birthday"};
      PrivilegedAccessor.setValue(this.child, "privateStrings", strings);
      assertEquals(strings, PrivilegedAccessor.getValue(this.child, "privateStrings"));
   }

   /**
    * Tests the method <code>setValue</code> with a static field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetValueOfStaticField() throws Exception {
      int previousValue = (Integer) PrivilegedAccessor.getValue(this.parent, "privateStaticInt");
      assertTrue(previousValue != -1);

      PrivilegedAccessor.setValue(this.parent, "privateStaticInt", -1);
      assertEquals(-1, PrivilegedAccessor.getValue(this.parent, "privateStaticInt"));

      PrivilegedAccessor.setValue(Parent.class, "privateStaticInt", previousValue);
      assertEquals(previousValue, PrivilegedAccessor.getValue(this.parent, "privateStaticInt"));
   }

   /**
    * Tests the method <code>setValue</code> with a final field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetValueOfFinalField() throws Exception {
      int previousValue = (Integer) PrivilegedAccessor.getValue(this.parent, "privateFinalInt");
      assertTrue(previousValue != -2);

      PrivilegedAccessor.setValue(this.parent, "privateFinalInt", -2);
      assertEquals(-2, PrivilegedAccessor.getValue(this.parent, "privateFinalInt"));

      PrivilegedAccessor.setValue(this.parent, "privateFinalInt", previousValue);
      assertEquals(previousValue, PrivilegedAccessor.getValue(this.parent, "privateFinalInt"));
   }

   /**
    * Tests the method <code>setValue</code> with a final field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetValueOfFinalStringField() throws Exception {
      String previousValue = (String) PrivilegedAccessor.getValue(this.parent, "privateFinalString");
      assertTrue(previousValue != "Test");

      PrivilegedAccessor.setValue(this.parent, "privateFinalString", "Test");
      assertEquals("Test", PrivilegedAccessor.getValue(this.parent, "privateFinalString"));

      PrivilegedAccessor.setValue(this.parent, "privateFinalString", previousValue);
      assertEquals(previousValue, PrivilegedAccessor.getValue(this.parent, "privateFinalString"));
   }

   /**
    * Tests the method <code>setValue</code> with a static final field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetValueOfStaticFinalField() throws Exception {
      int previousValue = (Integer) PrivilegedAccessor.getValue(this.parent, "privateStaticFinalInt");
      assertTrue(previousValue != -3);

      try {
         PrivilegedAccessor.setValue(Parent.class, "privateStaticFinalInt", -3);
         assertEquals(-3, PrivilegedAccessor.getValue(this.parent, "privateStaticFinalInt"));
         fail();
      } catch (IllegalAccessException e) {
         // this is what we expect when accessing private static final fields of non java.lang classes
      }

      try {
         PrivilegedAccessor.setValue(Parent.class, "privateStaticFinalInt", previousValue);
         assertEquals(previousValue, PrivilegedAccessor.getValue(this.parent, "privateStaticFinalInt"));
      } catch (IllegalAccessException e) {
         // this is what we expect when accessing private static final fields of non java.lang classes
      }
   }

   /**
    * Tests the method <code>setValue</code> with a static final object field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetValueOfStaticFinalObjectField() throws Exception {
      String previousValue = (String) PrivilegedAccessor.getValue(this.parent, "privateStaticFinalString");
      assertTrue(previousValue != "Herbert");

      try {
         PrivilegedAccessor.setValue(Parent.class, "privateStaticFinalString", "Herbert");
         assertEquals("Herbert", PrivilegedAccessor.getValue(this.parent, "privateStaticFinalString"));
         fail();
      } catch (IllegalAccessException e) {
         // this is what we expect when accessing private static final fields of non java.lang classes
      }

      try {
         PrivilegedAccessor.setValue(Parent.class, "privateStaticFinalString", previousValue);
         assertEquals(previousValue, PrivilegedAccessor.getValue(this.parent, "privateStaticFinalString"));
      } catch (IllegalAccessException e) {
         // this is what we expect when accessing private static final fields of non java.lang classes
      }
   }

   /**
    * Tests the method <code>setValue</code> with a non-existing field.
    * 
    * @throws Exception
    * @see junit.extensions.PrivilegedAccessor#setValue(java.lang.Object, java.lang.String, java.lang.String)
    */
   @Test
   @SuppressWarnings("deprecation")
   public void testSetValueOnInvalidField() throws Exception {
      try {
         PrivilegedAccessor.setValue(this.parent, "noSuchField", "value");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.setValue(this.child, "noSuchField", "value");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.setValue(this.childInParent, "noSuchField", "value");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }

      try {
         PrivilegedAccessor.setValue(Parent.class, "noSuchField", "value");
         fail("should throw NoSuchFieldException");
      } catch (NoSuchFieldException e) {
         // that is what we expect
      }
   }

   @Test
   @SuppressWarnings("deprecation")
   public void testInstantiateInnerClass() throws Exception {
      Object tic = PrivilegedAccessor.instantiate(Class.forName("junit.extensions.Child$InnerChild"),
         new Object[] {this.child});
      assertEquals(Class.forName("junit.extensions.Child$InnerChild"), tic.getClass());
   }

   @Test
   @SuppressWarnings("deprecation")
   public void testAccessInnerClass() throws Exception {
      Object tic = PrivilegedAccessor.instantiate(Class.forName("junit.extensions.Child$InnerChild"),
         new Object[] {this.child});
      PrivilegedAccessor.setValue(tic, "privateInnerInt", 5);
      assertEquals(5, PrivilegedAccessor.getValue(tic, "privateInnerInt"));
   }

   @Test
   @SuppressWarnings("deprecation")
   public void testAccessInnerMethod() throws Exception {
      Object tic = PrivilegedAccessor.instantiate(Class.forName("junit.extensions.Child$InnerChild"),
         new Object[] {this.child});
      PrivilegedAccessor.invokeMethod(tic, "setPrivateInnerInt(int)", new Object[] {7});
      assertEquals(7, PrivilegedAccessor.invokeMethod(tic, "getPrivateInnerInt()", null));
   }
}