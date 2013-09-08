//
// Binding.java (struct)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

public class Binding
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.CosNaming.NameComponent[] binding_name;
  public org.omg.CosNaming.BindingType binding_type;

  public Binding() {
  }

  public Binding(org.omg.CosNaming.NameComponent[] binding_name, org.omg.CosNaming.BindingType binding_type) {
    this.binding_name = binding_name;
    this.binding_type = binding_type;
  }

}
