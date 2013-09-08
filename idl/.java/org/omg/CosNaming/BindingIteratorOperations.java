//
// BindingIterator.java (interfaceOperations)
//
// File generated: Wed Jun 23 11:46:45 CEST 2010
//   by TIDorb idl2java 1.3.11
//

package org.omg.CosNaming;

public interface BindingIteratorOperations {

  boolean next_one(org.omg.CosNaming.BindingHolder b);

  boolean next_n(int how_many, org.omg.CosNaming.BindingListHolder bl);

  void destroy();


}
