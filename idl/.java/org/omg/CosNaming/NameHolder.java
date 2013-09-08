//
// NameHolder.java (holder)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

final public class NameHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CosNaming.NameComponent[] value; 
  public NameHolder() {
    value = new org.omg.CosNaming.NameComponent[0];
  }

  public NameHolder(org.omg.CosNaming.NameComponent[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.NameHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.NameHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.NameHelper.type();
  };

}
