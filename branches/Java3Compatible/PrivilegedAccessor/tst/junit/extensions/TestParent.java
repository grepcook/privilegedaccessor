package junit.extensions;

/**
 * Test class with private methods to invoke via PrivilegedAccessor
 */
public class TestParent {
    private static int privateStaticNumber;
    private String privateName;

    public TestParent(String name) {
        this.privateName = name;
        privateStaticNumber = 1;
    }

    private TestParent() {
        this.privateName = "Charlie";
    }

    private String getName() {
        return this.privateName;
    }

    protected void setName(String newName) {
        this.privateName = newName;
    }
    
    /** overloading setName(String) **/
    private void setName() {
        this.privateName = "Chaplin";
    }

    private static void setStaticNumber(int number) {
        TestParent.privateStaticNumber = number;
    }

    public boolean equals(Object other) {
        if (!(other instanceof TestParent)) {
            return false;
        }

        TestParent otherTestParent = (TestParent) other;

        if (this.privateName.equals(otherTestParent.privateName)) {
            return true;
        }

        return false;
    }
}