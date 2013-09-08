//
// NotEmpty.java (exception)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextPackage;

final public class NotEmpty
   extends org.omg.CORBA.UserException {


  public NotEmpty() {
    super(NotEmptyHelper.id());
  }

  public NotEmpty(String reason) {
    super(NotEmptyHelper.id()+" "+reason);

  }

}
