//
// SQL_AccessDataHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class SQL_AccessDataHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, SQL_AccessData value) {
    any.insert_Streamable(new SQL_AccessDataHolder(value));
  };

  public static SQL_AccessData extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SQL_AccessDataHolder){
          return ((SQL_AccessDataHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("user", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("passwd", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("table", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[3] = new org.omg.CORBA.StructMember("connection_init", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_struct_tc(id(), "SQL_AccessData", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/SQL_AccessData:1.0";
  };

  public static SQL_AccessData read(org.omg.CORBA.portable.InputStream is) {
    SQL_AccessData result = new SQL_AccessData();
    result.user = is.read_string();
    result.passwd = is.read_string();
    result.table = is.read_string();
    result.connection_init = is.read_string();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, SQL_AccessData val) {
    os.write_string(val.user);
    os.write_string(val.passwd);
    os.write_string(val.table);
    os.write_string(val.connection_init);
  };

}
