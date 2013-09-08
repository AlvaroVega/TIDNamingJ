//
// NamingSpace.java (struct)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

public class NamingSpace
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public org.omg.CosNaming.NamingContext root_context;
  public es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data;

  public NamingSpace() {
    name = "";
  }

  public NamingSpace(java.lang.String name, org.omg.CosNaming.NamingContext root_context, es.tid.corba.TIDNamingAdmin.NamingSpaceAccessData data) {
    this.name = name;
    this.root_context = root_context;
    this.data = data;
  }

}
