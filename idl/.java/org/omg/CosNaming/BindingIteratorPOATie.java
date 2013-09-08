//
// BindingIteratorPOATie.java (tie)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

public class BindingIteratorPOATie
 extends BindingIteratorPOA
 implements BindingIteratorOperations {

  private BindingIteratorOperations _delegate;
  public BindingIteratorPOATie(BindingIteratorOperations delegate) {
    this._delegate = delegate;
  };

  public BindingIteratorOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CosNaming/BindingIterator:1.0"  };

  public boolean next_one(org.omg.CosNaming.BindingHolder b) {
    return this._delegate.next_one(
    b
    );
  };

  public boolean next_n(int how_many, org.omg.CosNaming.BindingListHolder bl) {
    return this._delegate.next_n(
    how_many, 
    bl
    );
  };

  public void destroy() {
    this._delegate.destroy(
    );
  };


}
