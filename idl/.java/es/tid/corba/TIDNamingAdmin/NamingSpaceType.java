//
// NamingSpaceType.java (enum)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public class NamingSpaceType
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SQL = 0;
  public static final NamingSpaceType SQL = new NamingSpaceType(_SQL);
  public static final int _LDAP = 1;
  public static final NamingSpaceType LDAP = new NamingSpaceType(_LDAP);
  public static final int _FILE = 2;
  public static final NamingSpaceType FILE = new NamingSpaceType(_FILE);
  public static final int _NULL = 3;
  public static final NamingSpaceType NULL = new NamingSpaceType(_NULL);

  public int value() { return _value; }
  public static NamingSpaceType from_int(int value) {
    switch (value) {
      case 0: return SQL;
      case 1: return LDAP;
      case 2: return FILE;
      case 3: return NULL;
    };
    return null;
  };
  protected NamingSpaceType (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
