The name "PrivilegedAccessor" comes from its purpose: To obtain _privileged access_ to private parts of classes. With privileged access you can do anything with classes that would otherwise not be possible. See [Introduction](Introduction.md) on how to use it.

## History ##
PrivilegedAccessor was initially provided by Charlie Hubbard and Prashant Dhokte who also coined the name PrivilegedAccessor.

In 2004 (2004-11-26) Sebastian Dietrich started a [sourceforge project for PrivilegedAccessor](https://sourceforge.net/projects/privaccessor/) with the permission of the original authors. This project was also backlinked by the JUnit FAQ on ["How do I test private methods?"](http://junit.sourceforge.net/doc/faq/faq.htm#tests_11).

In 2004-2005 it has been improved a lot - e.g. adding the possibility to work with Java5 varargs Arguments instead of putting arguments in object-arrays. Later it became more silent around PrivilegedAccessor.

In February 2012 the project was moved to google code since the authors thought it would be a more convenient place to host a tiny library like PrivilegedAccessor.

Since April 2013 this project can also be downloaded from maven central

See [ReleaseNotes](ReleaseNotes.md) on detailed info on releases.

## Future ##
PrivilegedAccessor never intended to provide more than simple-to-use privileged access to hidden parts of classes. This will not change in the future.
Nevertheless we are working on some features to make the usage of PrivilegedAccessor even simpler - see e.g.:
  * [Issue 15](https://code.google.com/p/privilegedaccessor/issues/detail?id=15): Extend existing method chaining, so you will be able to set-up your class-under-test even more easily - e.g. `PA.new(instance).value("fieldName", value).value("otherFieldName", otherValue);`

If you have any suggestions, please feel free to create an issue for it.