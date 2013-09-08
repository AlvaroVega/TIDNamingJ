/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDNaming
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 1 $
* Date: $Date: 2009-03-20 11:28:08 +0100 (Fri, 20 Mar 2009) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2009 Telefonica Investigacion y Desarrollo
*     S.A.Unipersonal (Telefonica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDNamingJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDNamingJ/Licensing
*/
package es.tid.corba.CosNamingClient;

import org.omg.CORBA.*;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Properties;

public class TestCosNaming implements Runnable{
  
  String id;
  boolean print;
  String args[];  
  NameComponent[][] names;
  int binding_loop;
  
  public TestCosNaming(String args[], int binding_loop, String id, boolean print) 
  {
    this.args = args;
    this.binding_loop = binding_loop;
    this.id = id;
    this.print = print;
    this.names = generate_names(id);
  }
  
  public static void main(String args[])
  {
    /*
    TestCosNaming test0 = new TestCosNaming(args, 100, "simple",true);
    
    test0.run();
   */ 
    int threads = 10;
    
    Thread thread = null;
   
    for(int i = 0; i < threads; i++) {
      thread = new Thread(new TestCosNaming(args, 5 , "paralel" ,false));      
      thread.start();
      try {
        Thread.sleep(1000);
      } catch (Exception e) {}
    }
/*
    int threads = 20;
    
    Thread thread = null;
    
    for(int i = 0; i < threads; i++) {
      thread = new Thread(new TestCosNaming(args, Integer.toString(i+1),false));  
      thread.start();
    }
    
  */  
  }
  
  public static org.omg.CORBA.ORB prepare_orb(String args[], NamingContextHolder holder)
  {
    Properties props = new Properties();
    
    props.setProperty("org.omg.CORBA.ORBClass", 
                      "es.tid.TIDorbj.core.TIDORB");
    props.setProperty("org.omg.CORBA.ORBSingletonClass", 
                      "es.tid.TIDorbj.core.SingletonORB");

    props.setProperty("es.tid.TIDorbj.trace.level", "5");
    props.setProperty("es.tid.TIDorbj.trace.file", "TestCosNaming.log");

//     props.put("org.omg.CORBA.ORBClass",
//               "es.tid.TIDorbj.core.TIDORB");
//     System.getProperties().put("org.omg.CORBA.ORBSingletonClass",
//                                "es.tid.TIDorbj.core.SingletonORB");
    
    //org.omg.CORBA_2_3.ORB orb = (org.omg.CORBA_2_3.ORB) ORB.init(args, props);
    org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args,props);

    try {
      
      holder.value = 
          NamingContextHelper.narrow(orb.string_to_object(
                                 "corbaloc:iiop:1.2@localhost:2809/NameService"));
      //NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    
    return orb;
  }

  public void run() 
  {   
    NamingContextHolder context_holder = new NamingContextHolder();

    org.omg.CORBA.ORB orb = prepare_orb(args, context_holder);
    
    NamingContext root_context = context_holder.value;
    
    //create test base
    
    TestBindNewContext.test(root_context,names[1], context_holder, null, id, print);
    
    test_bind(root_context, binding_loop, names, id, print);
    
    test_bind_context(root_context, names, id, print);
    
    test_list(root_context, names, id, print);
    
    test_unbind(root_context,binding_loop, names, id, print);
    
    test_unbind_context(root_context, names, id, print);
     
    orb.destroy();
  }
  
  public static void test_bind(NamingContext root_context, int binding_size, 
                               NameComponent[][] names, String id, boolean print)
  {
    NamingContextHolder context_holder = new NamingContextHolder();

    System.err.println("test_bind");
    
    TestBind.test(root_context, names[0], null, null, id,print);
    //expected error
    TestBind.test(root_context, names[0], null, AlreadyBound.class, id,print);
    //expected error  
    TestBind.test(root_context, names[4], null, NotFound.class, id,print);

    TestResolveContext.test(root_context,names[1], context_holder, null, id, print);
   
    if(context_holder.value != null)
      TestBind.test_loop(context_holder.value, binding_size, null, id,print);
  }

  public static void test_unbind(NamingContext root_context, int binding_size, 
                                 NameComponent[][] names,  String id, boolean print)
  {
    NamingContextHolder context_holder = new NamingContextHolder();

    System.err.println("test_unbid");
    
    TestResolveContext.test(root_context,names[1], context_holder, null, id, print);
   
    if(context_holder.value != null)
      TestUnBind.test_loop(context_holder.value, binding_size, null, id,print);
   
    TestUnBind.test(root_context, names[0], null, id, print);
    TestUnBind.test(root_context, names[4], null, id,print);
  }
    
  public static void test_bind_context(NamingContext root_context, 
                                       NameComponent[][] names, String id, boolean print)
  {
    NamingContextHolder context_holder = new NamingContextHolder();

    System.err.println("test_bind_context");
  
    TestNewContext.test(root_context,context_holder, null, id,print);
    
    
    if(context_holder.value != null) {
      TestBindContext.test(root_context,names[1], context_holder.value, AlreadyBound.class, id, print);
      TestDestroyContext.test(context_holder.value, null, id, print);
    }
    
    TestBindNewContext.test(root_context,names[2], context_holder, null, id, print);
    TestBindNewContext.test(root_context,names[3], context_holder, null, id, print);
    //expected error
    TestBindNewContext.test(root_context,names[3], context_holder, AlreadyBound.class, id, print);
        //expected error
    TestBindNewContext.test(root_context,names[6], context_holder, NotFound.class, id, print);
  }

  public static void test_list(NamingContext root_context, 
                               NameComponent[][] names,  String id, boolean print)
  {
    NamingContextHolder context_holder = new NamingContextHolder();

    System.err.println("test_list");
    
    TestList.test(root_context, 23, null, id, print);
    TestResolveContext.test(root_context,names[1], context_holder, null, id, print);
    if(context_holder.value != null)
      TestList.test(context_holder.value, 23, null, id, print);
  }
  
  public static void test_unbind_context(NamingContext root_context,                                          
                                         NameComponent[][] names, String id, boolean print)
  {
    NamingContextHolder context_holder = new NamingContextHolder();

    System.err.println("test_unbind_context");

    TestResolveContext.test(root_context,names[1], context_holder, null, id, print);
   
    if(context_holder.value != null) {
      TestDestroyContext.test(context_holder.value, NotEmpty.class, id,print);
    }
    
    TestResolveContext.test(root_context,names[3], context_holder, null, id, print);
    if(context_holder.value != null) {
      TestUnBind.test(root_context, names[3], null, id, print);
      TestDestroyContext.test(context_holder.value, null, id,print);
      //expected error
      TestUnBind.test(root_context, names[3], NotFound.class, id, print);
      // expected error
      TestDestroyContext.test(context_holder.value, org.omg.CORBA.OBJECT_NOT_EXIST.class, id,print);
    }
    
    TestResolveContext.test(root_context,names[2], context_holder, null, id, print);
   
    if(context_holder.value != null) {
      TestUnBind.test(root_context, names[2], null, id, print);
      TestDestroyContext.test(context_holder.value, null, id,print);
    }

    TestResolveContext.test(root_context,names[1], context_holder, null, id, print);
   
    if(context_holder.value != null) {
      TestUnBind.test(root_context, names[1], null, id, print);
      TestDestroyContext.test(context_holder.value, null, id,print);
    }
    
    //expected error
    TestUnBind.test(root_context, names[3], NotFound.class, id, print);
  }
  
  public static NameComponent[][] generate_names(String prep)
  {
    NameComponent[][] names = new NameComponent[7][];
    
    names[0] = new NameComponent[1];
    names[0][0] = new NameComponent(prep + "_hola","adios");
    names[1] = new NameComponent[1];
    names[1][0] = new NameComponent(prep + "_subcontext","");
    names[2] = new NameComponent[1];
    names[2][0] = new NameComponent(prep + "_child","");
    names[3] = new NameComponent[2];
    names[3][0] = new NameComponent(prep + "_subcontext","");
    names[3][1] = new NameComponent(prep + "_child","");
    names[4] = new NameComponent[2];
    names[4][0] = new NameComponent(prep + "_subcontext","");
    names[4][1] = new NameComponent(prep + "_hola","adios");
    names[5] = new NameComponent[1];
    names[5][0] = new NameComponent(prep + "_inexistent","");
    names[6] = new NameComponent[2];
    names[6][0] = new NameComponent(prep + "_inexistent","");
    names[6][1] = new NameComponent(prep + "_hola","adios");
  
    return names;
  }
  
}
