//
// BindingListHolder.java (holder)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

final public class BindingListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CosNaming.Binding[] value; 
  public BindingListHolder() {
    value = new org.omg.CosNaming.Binding[0];
  }

  public BindingListHolder(org.omg.CosNaming.Binding[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.BindingListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.BindingListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.BindingListHelper.type();
  };

}
