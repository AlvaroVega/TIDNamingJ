//
// FILE_AccessDataHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class FILE_AccessDataHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, FILE_AccessData value) {
    any.insert_Streamable(new FILE_AccessDataHolder(value));
  };

  public static FILE_AccessData extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof FILE_AccessDataHolder){
          return ((FILE_AccessDataHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[1];
      members[0] = new org.omg.CORBA.StructMember("file_url", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_struct_tc(id(), "FILE_AccessData", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/FILE_AccessData:1.0";
  };

  public static FILE_AccessData read(org.omg.CORBA.portable.InputStream is) {
    FILE_AccessData result = new FILE_AccessData();
    result.file_url = is.read_string();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, FILE_AccessData val) {
    os.write_string(val.file_url);
  };

}
