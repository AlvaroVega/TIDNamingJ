//
// BindingTypeHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

abstract public class BindingTypeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, BindingType value) {
    any.insert_Streamable(new BindingTypeHolder(value));
  };

  public static BindingType extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof BindingTypeHolder){
          return ((BindingTypeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[2];
      members[0] = "nobject";
      members[1] = "ncontext";
      _type = _orb().create_enum_tc(id(), "BindingType", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CosNaming/BindingType:1.0";
  };

  public static BindingType read(org.omg.CORBA.portable.InputStream is) {
    return BindingType.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, BindingType val) {
    os.write_long(val.value());
  };

}
