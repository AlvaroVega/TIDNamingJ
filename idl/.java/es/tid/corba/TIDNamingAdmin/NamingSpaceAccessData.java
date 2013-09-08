//
// NamingSpaceAccessData.java (union)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

final public class NamingSpaceAccessData
   implements org.omg.CORBA.portable.IDLEntity {

  private es.tid.corba.TIDNamingAdmin.NamingSpaceType _discriminator;
  private java.lang.Object _union_value;
  protected boolean _isDefault = true;

  public es.tid.corba.TIDNamingAdmin.SQL_AccessData sql_data(){
    if (_discriminator.value() == es.tid.corba.TIDNamingAdmin.NamingSpaceType._SQL) {
       return (es.tid.corba.TIDNamingAdmin.SQL_AccessData)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("sql_data");
  }

  public void sql_data(es.tid.corba.TIDNamingAdmin.SQL_AccessData value){
    _discriminator = es.tid.corba.TIDNamingAdmin.NamingSpaceType.SQL;
    _union_value = (value);
    _isDefault = false;
  }


  public es.tid.corba.TIDNamingAdmin.LDAP_AccessData ldap_data(){
    if (_discriminator.value() == es.tid.corba.TIDNamingAdmin.NamingSpaceType._LDAP) {
       return (es.tid.corba.TIDNamingAdmin.LDAP_AccessData)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("ldap_data");
  }

  public void ldap_data(es.tid.corba.TIDNamingAdmin.LDAP_AccessData value){
    _discriminator = es.tid.corba.TIDNamingAdmin.NamingSpaceType.LDAP;
    _union_value = (value);
    _isDefault = false;
  }


  public es.tid.corba.TIDNamingAdmin.FILE_AccessData file_data(){
    if (_discriminator.value() == es.tid.corba.TIDNamingAdmin.NamingSpaceType._FILE) {
       return (es.tid.corba.TIDNamingAdmin.FILE_AccessData)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("file_data");
  }

  public void file_data(es.tid.corba.TIDNamingAdmin.FILE_AccessData value){
    _discriminator = es.tid.corba.TIDNamingAdmin.NamingSpaceType.FILE;
    _union_value = (value);
    _isDefault = false;
  }


  public es.tid.corba.TIDNamingAdmin.NULL_AccessData null_data(){
    if (_discriminator.value() == es.tid.corba.TIDNamingAdmin.NamingSpaceType._NULL) {
       return (es.tid.corba.TIDNamingAdmin.NULL_AccessData)_union_value;
    }
    throw new org.omg.CORBA.BAD_OPERATION("null_data");
  }

  public void null_data(es.tid.corba.TIDNamingAdmin.NULL_AccessData value){
    _discriminator = es.tid.corba.TIDNamingAdmin.NamingSpaceType.NULL;
    _union_value = (value);
    _isDefault = false;
  }


  public es.tid.corba.TIDNamingAdmin.NamingSpaceType discriminator(){
     return _discriminator;
  }

}
