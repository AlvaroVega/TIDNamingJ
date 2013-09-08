//
// InvalidAddressHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextExtPackage;

abstract public class InvalidAddressHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, InvalidAddress value) {
    any.insert_Streamable(new InvalidAddressHolder(value));
  };

  public static InvalidAddress extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof InvalidAddressHolder){
          return ((InvalidAddressHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[0];
      _type = _orb().create_exception_tc(id(), "InvalidAddress", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CosNaming/NamingContextExt/InvalidAddress:1.0";
  };

  public static InvalidAddress read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    InvalidAddress result = new InvalidAddress();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, InvalidAddress val) {
    os.write_string(id());
  };

}
