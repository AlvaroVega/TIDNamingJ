//
// AlreadyBoundHolder.java (holder)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextPackage;

final public class AlreadyBoundHolder
   implements org.omg.CORBA.portable.Streamable {

  public AlreadyBound value; 
  public AlreadyBoundHolder() {
  }

  public AlreadyBoundHolder(AlreadyBound initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type();
  };

}
