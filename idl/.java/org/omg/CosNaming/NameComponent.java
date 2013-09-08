//
// NameComponent.java (struct)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

public class NameComponent
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String id;
  public java.lang.String kind;

  public NameComponent() {
    id = "";
    kind = "";
  }

  public NameComponent(java.lang.String id, java.lang.String kind) {
    this.id = id;
    this.kind = kind;
  }

}
