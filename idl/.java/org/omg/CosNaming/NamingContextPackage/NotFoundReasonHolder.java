//
// NotFoundReasonHolder.java (holder)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextPackage;

final public class NotFoundReasonHolder
   implements org.omg.CORBA.portable.Streamable {

  public NotFoundReason value; 
  public NotFoundReasonHolder() {
  }

  public NotFoundReasonHolder(NotFoundReason initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.NamingContextPackage.NotFoundReasonHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.NamingContextPackage.NotFoundReasonHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.NamingContextPackage.NotFoundReasonHelper.type();
  };

}
