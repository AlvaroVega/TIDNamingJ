//
// NamingSpaceHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class NamingSpaceHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, NamingSpace value) {
    any.insert_Streamable(new NamingSpaceHolder(value));
  };

  public static NamingSpace extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof NamingSpaceHolder){
          return ((NamingSpaceHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("root_context", org.omg.CosNaming.NamingContextHelper.type(), null);
      members[2] = new org.omg.CORBA.StructMember("data", es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "NamingSpace", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/NamingSpace:1.0";
  };

  public static NamingSpace read(org.omg.CORBA.portable.InputStream is) {
    NamingSpace result = new NamingSpace();
    result.name = is.read_string();
    result.root_context = org.omg.CosNaming.NamingContextHelper.read(is);
    result.data = es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, NamingSpace val) {
    os.write_string(val.name);
    org.omg.CosNaming.NamingContextHelper.write(os,val.root_context);
    es.tid.corba.TIDNamingAdmin.NamingSpaceAccessDataHelper.write(os,val.data);
  };

}
