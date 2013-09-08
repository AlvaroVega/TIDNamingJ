//
// NamingSpaceSeqHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class NamingSpaceSeqHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, es.tid.corba.TIDNamingAdmin.NamingSpace[] value) {
    any.insert_Streamable(new NamingSpaceSeqHolder(value));
  };

  public static es.tid.corba.TIDNamingAdmin.NamingSpace[] extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof NamingSpaceSeqHolder){
          return ((NamingSpaceSeqHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = _orb().create_sequence_tc(0 , es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.type());
      _type = _orb().create_alias_tc(id(), "NamingSpaceSeq", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/NamingSpaceSeq:1.0";
  };

  public static es.tid.corba.TIDNamingAdmin.NamingSpace[] read(org.omg.CORBA.portable.InputStream is) {
    es.tid.corba.TIDNamingAdmin.NamingSpace[] result;
      int length0 = is.read_ulong();
      result = new es.tid.corba.TIDNamingAdmin.NamingSpace[length0];
      for (int i0=0; i0<length0; i0++) {
        result[i0] = es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.read(is);
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, es.tid.corba.TIDNamingAdmin.NamingSpace[] val) {
      os.write_ulong(val.length);
      for (int i0=0; i0<val.length; i0++) {
        es.tid.corba.TIDNamingAdmin.NamingSpaceHelper.write(os,val[i0]);
      }
  };

}
