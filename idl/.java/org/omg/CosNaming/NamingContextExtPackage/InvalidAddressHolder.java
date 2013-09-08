//
// InvalidAddressHolder.java (holder)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextExtPackage;

final public class InvalidAddressHolder
   implements org.omg.CORBA.portable.Streamable {

  public InvalidAddress value; 
  public InvalidAddressHolder() {
  }

  public InvalidAddressHolder(InvalidAddress initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.type();
  };

}
