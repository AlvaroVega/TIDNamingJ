//
// AgentHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class AgentHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      _type = _orb().create_interface_tc(id(), "Agent");
    }
    return _type;
  }

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/Agent:1.0";
  };

  public static void insert(org.omg.CORBA.Any any, Agent value) {
    any.insert_Object((org.omg.CORBA.Object)value, type());
  };

  public static Agent extract(org.omg.CORBA.Any any) {
    org.omg.CORBA.Object obj = any.extract_Object();
    Agent value = narrow(obj);
    return value;
  };

  public static Agent read(org.omg.CORBA.portable.InputStream is) {
    return narrow(is.read_Object(), true); 
  }

  public static void write(org.omg.CORBA.portable.OutputStream os, Agent val) {
    if (!(os instanceof org.omg.CORBA_2_3.portable.OutputStream)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    if (val != null && !(val instanceof org.omg.CORBA.portable.ObjectImpl)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    os.write_Object((org.omg.CORBA.Object)val);
  }

  public static Agent narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, false);
  }

  public static Agent unchecked_narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, true);
  }

  private static Agent narrow(org.omg.CORBA.Object obj, boolean is_a) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof Agent) {
      return (Agent)obj;
    }
    if (is_a || obj._is_a(id())) {
      _AgentStub result = (_AgentStub)new _AgentStub();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate());
      return (Agent)result;
    }
    throw new org.omg.CORBA.BAD_PARAM();
  }

}
