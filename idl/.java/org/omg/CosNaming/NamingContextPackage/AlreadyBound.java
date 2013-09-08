//
// AlreadyBound.java (exception)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextPackage;

final public class AlreadyBound
   extends org.omg.CORBA.UserException {


  public AlreadyBound() {
    super(AlreadyBoundHelper.id());
  }

  public AlreadyBound(String reason) {
    super(AlreadyBoundHelper.id()+" "+reason);

  }

}
