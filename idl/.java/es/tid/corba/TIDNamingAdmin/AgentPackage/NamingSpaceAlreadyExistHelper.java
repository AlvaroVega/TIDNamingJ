//
// NamingSpaceAlreadyExistHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin.AgentPackage;

abstract public class NamingSpaceAlreadyExistHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, NamingSpaceAlreadyExist value) {
    any.insert_Streamable(new NamingSpaceAlreadyExistHolder(value));
  };

  public static NamingSpaceAlreadyExist extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof NamingSpaceAlreadyExistHolder){
          return ((NamingSpaceAlreadyExistHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[0];
      _type = _orb().create_exception_tc(id(), "NamingSpaceAlreadyExist", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/Agent/NamingSpaceAlreadyExist:1.0";
  };

  public static NamingSpaceAlreadyExist read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    NamingSpaceAlreadyExist result = new NamingSpaceAlreadyExist();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, NamingSpaceAlreadyExist val) {
    os.write_string(id());
  };

}
