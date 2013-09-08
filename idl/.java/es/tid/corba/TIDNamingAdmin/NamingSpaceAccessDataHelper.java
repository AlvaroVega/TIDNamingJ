//
// NamingSpaceAccessDataHelper.java (helper)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

abstract public class NamingSpaceAccessDataHelper {

  public static void insert(org.omg.CORBA.Any any, NamingSpaceAccessData value) {
    any.insert_Streamable(new NamingSpaceAccessDataHolder(value));
  };

  public static NamingSpaceAccessData extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof NamingSpaceAccessDataHolder){
          return ((NamingSpaceAccessDataHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
     if (_type == null) {
       org.omg.CORBA.UnionMember[] members = new org.omg.CORBA.UnionMember[4];
       org.omg.CORBA.Any _any0 = _orb().create_any();
       es.tid.corba.TIDNamingAdmin.NamingSpaceTypeHelper.insert(_any0, es.tid.corba.TIDNamingAdmin.NamingSpaceType.SQL);
       members[0] = new org.omg.CORBA.UnionMember("sql_data", _any0, es.tid.corba.TIDNamingAdmin.SQL_AccessDataHelper.type(), null);
       org.omg.CORBA.Any _any1 = _orb().create_any();
       es.tid.corba.TIDNamingAdmin.NamingSpaceTypeHelper.insert(_any1, es.tid.corba.TIDNamingAdmin.NamingSpaceType.LDAP);
       members[1] = new org.omg.CORBA.UnionMember("ldap_data", _any1, es.tid.corba.TIDNamingAdmin.LDAP_AccessDataHelper.type(), null);
       org.omg.CORBA.Any _any2 = _orb().create_any();
       es.tid.corba.TIDNamingAdmin.NamingSpaceTypeHelper.insert(_any2, es.tid.corba.TIDNamingAdmin.NamingSpaceType.FILE);
       members[2] = new org.omg.CORBA.UnionMember("file_data", _any2, es.tid.corba.TIDNamingAdmin.FILE_AccessDataHelper.type(), null);
       org.omg.CORBA.Any _any3 = _orb().create_any();
       es.tid.corba.TIDNamingAdmin.NamingSpaceTypeHelper.insert(_any3, es.tid.corba.TIDNamingAdmin.NamingSpaceType.NULL);
       members[3] = new org.omg.CORBA.UnionMember("null_data", _any3, es.tid.corba.TIDNamingAdmin.NULL_AccessDataHelper.type(), null);
       _type = _orb().create_union_tc(id(), "NamingSpaceAccessData", es.tid.corba.TIDNamingAdmin.NamingSpaceTypeHelper.type(), members);
     };
     return _type;
  };

  public static String id() {
    return "IDL:tid.es/TIDNamingAdmin/NamingSpaceAccessData:1.0";
  };

  public static NamingSpaceAccessData read(org.omg.CORBA.portable.InputStream is) {
    NamingSpaceAccessData _union_result = new NamingSpaceAccessData();
    es.tid.corba.TIDNamingAdmin.NamingSpaceType _disc_value = es.tid.corba.TIDNamingAdmin.NamingSpaceTypeHelper.read(is);
    switch (_disc_value.value()){

     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._SQL: {
       es.tid.corba.TIDNamingAdmin.SQL_AccessData _tmp = es.tid.corba.TIDNamingAdmin.SQL_AccessDataHelper.read(is);
       _union_result.sql_data(_tmp);
       break;
     }
     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._LDAP: {
       es.tid.corba.TIDNamingAdmin.LDAP_AccessData _tmp = es.tid.corba.TIDNamingAdmin.LDAP_AccessDataHelper.read(is);
       _union_result.ldap_data(_tmp);
       break;
     }
     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._FILE: {
       es.tid.corba.TIDNamingAdmin.FILE_AccessData _tmp = es.tid.corba.TIDNamingAdmin.FILE_AccessDataHelper.read(is);
       _union_result.file_data(_tmp);
       break;
     }
     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._NULL: {
       es.tid.corba.TIDNamingAdmin.NULL_AccessData _tmp = es.tid.corba.TIDNamingAdmin.NULL_AccessDataHelper.read(is);
       _union_result.null_data(_tmp);
       break;
     }
    }
    return _union_result;

  }

  public static void write(org.omg.CORBA.portable.OutputStream os, NamingSpaceAccessData _value) {
    es.tid.corba.TIDNamingAdmin.NamingSpaceTypeHelper.write(os,_value.discriminator());
    switch (_value.discriminator().value()){

     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._SQL: {
       es.tid.corba.TIDNamingAdmin.SQL_AccessData sql_data = _value.sql_data();
       es.tid.corba.TIDNamingAdmin.SQL_AccessDataHelper.write(os,sql_data);
       break;
     }
     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._LDAP: {
       es.tid.corba.TIDNamingAdmin.LDAP_AccessData ldap_data = _value.ldap_data();
       es.tid.corba.TIDNamingAdmin.LDAP_AccessDataHelper.write(os,ldap_data);
       break;
     }
     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._FILE: {
       es.tid.corba.TIDNamingAdmin.FILE_AccessData file_data = _value.file_data();
       es.tid.corba.TIDNamingAdmin.FILE_AccessDataHelper.write(os,file_data);
       break;
     }
     case es.tid.corba.TIDNamingAdmin.NamingSpaceType._NULL: {
       es.tid.corba.TIDNamingAdmin.NULL_AccessData null_data = _value.null_data();
       es.tid.corba.TIDNamingAdmin.NULL_AccessDataHelper.write(os,null_data);
       break;
     }
    }

  }

}
