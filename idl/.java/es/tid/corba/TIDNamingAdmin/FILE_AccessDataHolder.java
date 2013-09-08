//
// FILE_AccessDataHolder.java (holder)
//
// File generated: Wed Jun 23 11:46:46 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package es.tid.corba.TIDNamingAdmin;

final public class FILE_AccessDataHolder
   implements org.omg.CORBA.portable.Streamable {

  public FILE_AccessData value; 
  public FILE_AccessDataHolder() {
  }

  public FILE_AccessDataHolder(FILE_AccessData initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = es.tid.corba.TIDNamingAdmin.FILE_AccessDataHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    es.tid.corba.TIDNamingAdmin.FILE_AccessDataHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return es.tid.corba.TIDNamingAdmin.FILE_AccessDataHelper.type();
  };

}
