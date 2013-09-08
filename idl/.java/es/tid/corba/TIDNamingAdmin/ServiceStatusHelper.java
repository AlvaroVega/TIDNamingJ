//
// ServiceStatusHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class ServiceStatusHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ServiceStatus value) {
    any.insert_Streamable(new ServiceStatusHolder(value));
  };

  public static ServiceStatus extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ServiceStatusHolder){
          return ((ServiceStatusHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("state", es.tid.corba.TIDNamingAdmin.ServiceStateHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("msgs", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_struct_tc(id(), "ServiceStatus", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/ServiceStatus:1.0";
  };

  public static ServiceStatus read(org.omg.CORBA.portable.InputStream is) {
    ServiceStatus result = new ServiceStatus();
    result.state = es.tid.corba.TIDNamingAdmin.ServiceStateHelper.read(is);
    result.msgs = is.read_string();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ServiceStatus val) {
    es.tid.corba.TIDNamingAdmin.ServiceStateHelper.write(os,val.state);
    os.write_string(val.msgs);
  };

}
