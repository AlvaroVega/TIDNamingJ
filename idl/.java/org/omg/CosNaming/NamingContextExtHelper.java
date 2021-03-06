//
// NamingContextExtHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

abstract public class NamingContextExtHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      _type = _orb().create_interface_tc(id(), "NamingContextExt");
    }
    return _type;
  }

  public static String id() {
    return "IDL:omg.org/CosNaming/NamingContextExt:1.0";
  };

  public static void insert(org.omg.CORBA.Any any, NamingContextExt value) {
    any.insert_Object((org.omg.CORBA.Object)value, type());
  };

  public static NamingContextExt extract(org.omg.CORBA.Any any) {
    org.omg.CORBA.Object obj = any.extract_Object();
    NamingContextExt value = narrow(obj);
    return value;
  };

  public static NamingContextExt read(org.omg.CORBA.portable.InputStream is) {
    return narrow(is.read_Object(), true); 
  }

  public static void write(org.omg.CORBA.portable.OutputStream os, NamingContextExt val) {
    if (!(os instanceof org.omg.CORBA_2_3.portable.OutputStream)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    if (val != null && !(val instanceof org.omg.CORBA.portable.ObjectImpl)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    os.write_Object((org.omg.CORBA.Object)val);
  }

  public static NamingContextExt narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, false);
  }

  public static NamingContextExt unchecked_narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, true);
  }

  private static NamingContextExt narrow(org.omg.CORBA.Object obj, boolean is_a) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof NamingContextExt) {
      return (NamingContextExt)obj;
    }
    if (is_a || obj._is_a(id())) {
      _NamingContextExtStub result = (_NamingContextExtStub)new _NamingContextExtStub();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate());
      return (NamingContextExt)result;
    }
    throw new org.omg.CORBA.BAD_PARAM();
  }

}
