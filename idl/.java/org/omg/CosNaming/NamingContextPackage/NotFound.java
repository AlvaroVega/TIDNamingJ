//
// NotFound.java (exception)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming.NamingContextPackage;

final public class NotFound
   extends org.omg.CORBA.UserException {

  public org.omg.CosNaming.NamingContextPackage.NotFoundReason why;
  public org.omg.CosNaming.NameComponent[] rest_of_name;

  public NotFound() {
    super(NotFoundHelper.id());
  }

  public NotFound(org.omg.CosNaming.NamingContextPackage.NotFoundReason _why, org.omg.CosNaming.NameComponent[] _rest_of_name) {
    super(NotFoundHelper.id());

    this.why = _why;
    this.rest_of_name = _rest_of_name;
  }

  public NotFound(String reason, org.omg.CosNaming.NamingContextPackage.NotFoundReason _why, org.omg.CosNaming.NameComponent[] _rest_of_name) {
    super(NotFoundHelper.id()+" "+reason);

    this.why = _why;
    this.rest_of_name = _rest_of_name;
  }

}
