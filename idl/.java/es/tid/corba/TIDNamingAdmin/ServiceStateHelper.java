//
// ServiceStateHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class ServiceStateHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ServiceState value) {
    any.insert_Streamable(new ServiceStateHolder(value));
  };

  public static ServiceState extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ServiceStateHolder){
          return ((ServiceStateHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[4];
      members[0] = "cannot_run";
      members[1] = "can_run";
      members[2] = "running";
      members[3] = "shutdowned";
      _type = _orb().create_enum_tc(id(), "ServiceState", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/ServiceState:1.0";
  };

  public static ServiceState read(org.omg.CORBA.portable.InputStream is) {
    return ServiceState.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ServiceState val) {
    os.write_long(val.value());
  };

}
