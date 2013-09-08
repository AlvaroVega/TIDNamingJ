//
// CannotProceedHolder.java (holder)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin.AgentPackage;

final public class CannotProceedHolder
   implements org.omg.CORBA.portable.Streamable {

  public CannotProceed value; 
  public CannotProceedHolder() {
  }

  public CannotProceedHolder(CannotProceed initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceedHelper.type();
  };

}
