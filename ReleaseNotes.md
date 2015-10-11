## Version 1.2.1 (25.2.2012) ##
Version 1.2.1 is the latest release of PrivilegedAccessor.

FEATURES:
  * [Issue 8](https://code.google.com/p/privilegedaccessor/issues/detail?id=8): all PA methods now only throw RuntimeExceptions -> no need to catch any exceptions when working with PA. This reduces your code a lot.
  * [Issue 9](https://code.google.com/p/privilegedaccessor/issues/detail?id=9): method-chaining is now possible for setValue(). e.g. setValue().setValue().getValue()... Unfortunately that is the only method that would otherwise return void, so no method-chaining for other methods
  * [Issue 12](https://code.google.com/p/privilegedaccessor/issues/detail?id=12): setField() now works as well with final fields. At least as long as they are not static

FIXED BUGS & ENHANCEMENTS:
  * [Issue 6](https://code.google.com/p/privilegedaccessor/issues/detail?id=6): semi automatic upload of releases to Google-Code
  * [Issue 5](https://code.google.com/p/privilegedaccessor/issues/detail?id=5): semi automatic upload of releases to maven central

KNOWN BUGS:
none

## Version 1.2 (25.2.2012) ##
Version 1.2 is the Java 1.5 (and later) compatible version of PrivilegedAccessor.

FUNCTIONAL CHANGES:
  * version 1.2 is no longer downward compatible to Java 1.3, but only to Java 1.5 (see version 1.1 and onwards for downward compatibility to Java 1.3).
  * [Issue 3](https://code.google.com/p/privilegedaccessor/issues/detail?id=3): use varargs instead of object arrays to deliver parameters to PA. PrivilegedAccessor still works with object[.md](.md).
  * [Issue 2](https://code.google.com/p/privilegedaccessor/issues/detail?id=2): provided methods to inspect an object - getFieldNames(), getMethodNames(), toString()

FEATURES:
  * works with Java 5.0 variable parameter count (e.g. PA.instantiate(MyClass.class, "Charlie", "Brown");) - thanks to Dan Richter for the advice
  * works with Java 5.0 autoboxing (e.g. PA.instantiate(MyClass.class, 1, false); instead of PA.instantiate(MyClass.class, new Integer(1), new Boolean(false));)
  * changed unit-tests to use Java 5.0 features and run based on junit4
  * added some more unit-tests - coverage is now (maxed) at 96,3% for junit.extensions.PA and 92,2% for junit.extensions.PrivilegedAccessor
  * added some assertions to fail-fast
  * improved error output
  * removed methods that are no longer needed due to variable parameter count (e.g. invokeMethod() with 0 or 1 parameters)
  * removed methods that are no longer needed due to autoboxing (e.g. setValue(primitive), invokeMethod(method, primitive))
  * returns the exact type when instantiating objects (no need to cast)
  * fixed minor javadoc typos
  * works as well with private member classes
  * added methods to get all attribute names and all method signatures

FIXED BUGS & ENHANCEMENTS:
  * [Issue 3](https://code.google.com/p/privilegedaccessor/issues/detail?id=3): works now with methods that require array type parameters
  * removed test-classes from jar and src.zip
  * [Issue 7](https://code.google.com/p/privilegedaccessor/issues/detail?id=7): works now with methods that require object array parameters

KNOWN BUGS:
none

## Version 1.0.2 (11.3.2005) - discontinued ##
Version 1.0.2 is the Java 1.3 (and later) compatible version of PrivilegedAccessor. This is the last 1.3 compatible version of PrivilegedAccessor - expect maintenance releases only (1.1.1, 1.1.2, ...)

FUNCTIONAL CHANGES:
  * added PA as a short access to PrivilegedAccessor

FEATURES:
  * added some more unit-tests
  * improved error output
  * added PA as a short access to PrivilegedAccessor

KNOWN BUGS:
  * does not work with methods that require object array parameters
  * does not work with java5 features like varargs

## Version 1.0.1 (8.2.2005) ##

FEATURES:
  * added unit-tests to test the behavior of PrivilegedAccessor
  * improved error output

KNOWN BUGS:
  * does not work with methods that require array parameters
  * does not work with java5 features like varargs

## Version 1.0 (1.2.2005) ##
Initial version taken from Charlie Hubbard and Prashant Dhokte

KNOWN BUGS:
  * does not work with methods that require array parameters
  * does not work with java5 features like varargs