/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDNamingJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 1 $
* Date: $Date: 2009-03-20 11:28:08 +0100 (Fri, 20 Mar 2009) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2009 Telefnica Investigacion y Desarrollo
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

/*

 File: es.tid.corba.TIDNaming.NamingContextImpl.java
  
 Author/s      : Juan A. Caceres
 Project       : e-Force
 Rel           : 01.00
 Created       : 16/01/2001
 Revision Date : 
 Rev. History  : 
 
 ------------------------------------------------------------------------------ */ 

package es.tid.corba.TIDNaming;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import es.tid.TIDorbj.util.Base64Codec;
import es.tid.TIDorbj.util.Trace;
import es.tid.TIDorbj.core.TIDORB;

/**
 * Implementation class of CosNaming::NamingContext. <p>
 * The NamingContext has a NamingSpaceDB where saves and gets the 
 * binding data required.<p>
 *
 * This servant has been developed for running as a DEFAULT_SERVANT.
 *
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 * @see NamingSpaceDB  
 */

public class NamingContextImpl extends NamingContextPOA
{
    /**
     * The ORB.
     */
    org.omg.CORBA.ORB my_orb;
    
    /**
     * The name space database accessor, where is included the NamingContext bindings.
     */
    NamingSpaceDB my_db;
    
    /**
     * CurrentPOA.
     */
    org.omg.PortableServer.Current my_current;
  
    /**
     * Servants POA.
     */
    org.omg.PortableServer.POA my_poa; 
  
    /**
     * POA where the BindingIterators will be created.
     */
    org.omg.PortableServer.POA my_iterators_poa; 
    
    /**
     * Log.
     */
    Trace my_trace;
	
    /**
     * Constructor.
     */
    public NamingContextImpl(org.omg.CORBA.ORB orb, 
                             org.omg.PortableServer.POA poa, 
                             org.omg.PortableServer.POA iterators_poa, 
                             NamingSpaceDB db,
                             Trace trace)
    {
        my_orb = orb;
        my_poa = poa;
        my_iterators_poa = iterators_poa;
        my_db = db;
        my_trace = trace;
        try {	
            my_current = 
                org.omg.PortableServer.CurrentHelper.narrow(
                                          my_orb.resolve_initial_references("POACurrent"));
        } catch(Exception e){
            if (my_trace != null) {
                String[] msg = {"NamingContextImpl::constructor ",e.toString()}; 
                my_trace.print(Trace.ERROR,msg);
            }
        }
    }
    
    /**
     * Sets the NamingSpace database accessor.
     * @param db the database accessor.
     */
    public void setDB(NamingSpaceDB db)
    {
        my_db = db;
    }
    
    public org.omg.CORBA.Object resolve(org.omg.CosNaming.NameComponent[] n)
        throws NotFound, CannotProceed, InvalidName
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::resolve ");
        }
        
        NamingContext next_context = null;
        NameComponent[] next_name = getNextName(n);
        String oid_str = null;
        byte[] oid = null;
        
        try {
    
            oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
            
            // Final Binding
            if(n.length == 1) {        
                String binding_str = my_db.resolve(oid_str, n[0].id, n[0].kind);          
                return my_orb.string_to_object(binding_str); 
            }
            
            // Path Binding
            String next_context_str = my_db.resolve_context(oid_str, n[0].id, n[0].kind);
            next_context = NamingContextHelper.narrow(my_orb.string_to_object(next_context_str));
        } catch(NotFound nf) {
            nf.rest_of_name = n;
            throw nf;      
        } catch(org.omg.CORBA.SystemException se){
            throw se;     
        } catch (CannotProceed cp) {
            cp.cxt = my_reference(oid);
            cp.rest_of_name =  n;
            throw cp;
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingSpaceServer::resolve in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingSpaceServer::resolve error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }         
            }
            
            throw new CannotProceed(my_reference(oid), n);
        }
        
        try{
            return next_context.resolve(next_name);
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST no) {
            throw new NotFound(NotFoundReason.not_object,next_name);
        } catch (org.omg.CORBA.SystemException se) {
            throw new CannotProceed(next_context, next_name);
        }
    }
    
    
  public void bind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
      throws NotFound, CannotProceed, InvalidName, AlreadyBound
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::bind ");
        }
        
        NamingContext next_context = null;
        // validate NameComponent[] names
        NameComponent[] next_name = getNextName(n);
        byte[] oid = null;
        String oid_str = null;
    
        try {
            oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
  
            // Final Binding
            if(n.length == 1) {
        
                my_db.bind(oid_str, n[0].id, n[0].kind, my_orb.object_to_string(obj)); 
        
                if (my_trace != null) {
                    String[] msg = {"NamingContextImpl::bind name_id=", 
                                    n[0].id, " name_kind=",n[0].kind," binded in ", oid_str}; 
                    my_trace.print(Trace.DEBUG,msg);
                }
      
                return;
            }
      
            // Path Binding
            String next_context_str = my_db.resolve_context(oid_str, n[0].id, n[0].kind);
 
            next_context = 
                NamingContextHelper.narrow(my_orb.string_to_object(next_context_str));
    
        } catch(NotFound nf) {
            nf.rest_of_name = n;
            throw nf;      
        } catch(AlreadyBound ab) {
            throw ab;      
        } catch (CannotProceed cp) {
            cp.cxt = my_reference(oid);
            cp.rest_of_name =  n;
            throw cp;
        } catch(org.omg.CORBA.SystemException se){
            throw se;     
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::bind in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::bind error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }         
            }
        
            throw new CannotProceed(my_reference(oid), n);
        }

        try{
            next_context.bind(next_name, obj);    
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST no) {
            throw new NotFound(NotFoundReason.not_object,next_name);
        } catch (org.omg.CORBA.SystemException se) {
            throw new CannotProceed(next_context, next_name);
        }
    }
  
    public void rebind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
        throws NotFound, CannotProceed, InvalidName
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::rebind ");
        }

        NamingContext next_context = null;
        // validate NameComponent[] names
        NameComponent[] next_name = getNextName(n);
        byte[] oid = null;
        String oid_str = null;
    
        try {
    
            oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
  
            // Final Binding
            if(n.length == 1) { 
                my_db.rebind(oid_str, n[0].id, n[0].kind, 
                             my_orb.object_to_string(obj));         
                if (my_trace != null) {
                    String[] msg = {"NamingContextImpl::rebind name_id=", 
                                    n[0].id, " name_kind=",n[0].kind," rebinded in ",oid_str}; 
                    my_trace.print(Trace.DEBUG,msg);
                }
                return;
            }
      
            // Path Binding
            String next_context_str = my_db.resolve_context(oid_str, n[0].id, n[0].kind);
            next_context = 
                NamingContextHelper.narrow(my_orb.string_to_object(next_context_str));
    
        } catch(NotFound nf) {
            nf.rest_of_name = n;
            throw nf;      
        } catch (CannotProceed cp) {
            cp.cxt = my_reference(oid);
            cp.rest_of_name =  n;
            throw cp;
        } catch(org.omg.CORBA.SystemException se){
            throw se;     
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::rebind in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::rebind error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }             
            }
        
            throw new CannotProceed(my_reference(oid), n);
        }

        try {
            next_context.rebind(next_name, obj);
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST no) {
            throw new NotFound(NotFoundReason.not_object,next_name);
        } catch (org.omg.CORBA.SystemException se) {
            throw new CannotProceed(next_context, next_name);
        }
    }

    public void unbind(org.omg.CosNaming.NameComponent[] n)
        throws NotFound, CannotProceed,InvalidName
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::unbind ");
        }
    
        NamingContext next_context = null;
        // validate NameComponent[] names
        NameComponent[] next_name = getNextName(n);
        byte[] oid = null;
        String oid_str = null;
    
        try {
 
            oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
  
            // Final Binding
            if(n.length == 1) { 
                my_db.unbind(oid_str, n[0].id, n[0].kind);
        
                if (my_trace != null) {
                    String[] msg = {"NamingContextImpl::unbind name_id=", 
                                    n[0].id, " name_kind=",n[0].kind," unbinded in ",oid_str}; 
                    my_trace.print(Trace.DEBUG,msg);
                }
                return;
            }
      
            // Path Binding
            String next_context_str = my_db.resolve_context(oid_str, n[0].id, n[0].kind);
  
            next_context = 
                NamingContextHelper.narrow(my_orb.string_to_object(next_context_str));
    
        } catch(NotFound nf) {
            nf.rest_of_name = n;
            throw nf;      
        } catch (CannotProceed cp) {
            cp.cxt = my_reference(oid);
            cp.rest_of_name =  n;
            throw cp;
        } catch(org.omg.CORBA.SystemException se){
            throw se;     
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::unbind in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::unbind error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }             
            }
        
            throw new CannotProceed(my_reference(oid), n);
        }
   
        try {   
            next_context.unbind(next_name);
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST no) {
            throw new NotFound(NotFoundReason.not_object,next_name);
        } catch (org.omg.CORBA.SystemException se) {
            throw new CannotProceed(next_context, next_name);
        }
    }
  
    public org.omg.CosNaming.NamingContext new_context()
        {
            if (my_trace != null) {
                my_trace.print(Trace.ERROR,"NamingContextImpl::new_context ");
            }
    
            String oid_str = null;
            NamingContext new_context = null;

            try {
 
                byte[] oid = my_current.get_object_id();
                    oid_str = Base64Codec.encode(oid);
    
                new_context = 
                    NamingContextHelper.narrow(my_poa.create_reference(NamingContextHelper.id())); 
            
                String new_ref_str = my_orb.object_to_string(new_context);
                byte [] new_oid = my_poa.reference_to_id(new_context);
                String new_oid_str = Base64Codec.encode(new_oid);

                my_db.new_context(oid_str, new_oid_str, new_ref_str);

            } catch (org.omg.CORBA.SystemException se) {
                throw se;
            } catch(Throwable th) {
                if (my_trace != null) {
                    if(oid_str != null) {
                        String[] msg = 
                            {"NamingContextImpl::new_context in ",oid_str, " error: ",th.toString()}; 
                        my_trace.print(Trace.ERROR,msg);
                    } else {
                        String[] msg = 
                            {"NamingContextImpl::new error: ",th.toString()}; 
                        my_trace.print(Trace.ERROR,msg);
                    }             
                }
                throw new org.omg.CORBA.TRANSIENT();
            }
    
            return new_context;
        }
  
    public org.omg.CosNaming.NamingContext global_new_context()
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::global_new_context ");
        }

        String oid_str = null;
    
        try {
            NamingContext context = 
                NamingContextHelper.narrow(my_poa.create_reference(NamingContextHelper.id())); 
            
            String ctx_ref_str = my_orb.object_to_string(context);
            byte [] oid = my_poa.reference_to_id(context);
            oid_str = Base64Codec.encode(oid);
      
            my_db.new_root_context(oid_str, ctx_ref_str);

            if (my_trace != null) {
                String[] msg = {"NamingContextImpl::global_new_context ", oid_str," created"}; 
                my_trace.print(Trace.DEBUG,msg);
            }

            try {
                ((es.tid.TIDorbj.core.TIDORB)my_orb).setORBservice("NameService", 
                                                                   context);
            } catch (java.lang.Exception ex ) {
                return context;
            }

            return context;
      
        } catch(org.omg.CORBA.SystemException se){
            throw se;     
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::global_new_context in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::global_new_context error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }             
            }
        
            return null;
        }
    }

  
    public void bind_context(NameComponent[] n, NamingContext nc)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::bind_context ");
        }

        NamingContext next_context = null;
        // validate NameComponent[] names
        NameComponent[] next_name = getNextName(n);
        byte[] oid = null;
        String oid_str = null;
    
        try {
            oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
  
            // Final Binding
            if(n.length == 1) {    
                my_db.bind_context(oid_str, n[0].id, n[0].kind, 
                                   my_orb.object_to_string(nc)); 
                return;
            }
      
            // Path Binding
            String next_context_str = my_db.resolve_context(oid_str, n[0].id, n[0].kind);
            next_context = 
                NamingContextHelper.narrow(my_orb.string_to_object(next_context_str));
    
        } catch(NotFound nf) {
            nf.rest_of_name = n;
            throw nf;      
        } catch(AlreadyBound ab) {
            throw ab;      
        } catch (CannotProceed cp) {
            cp.cxt = my_reference(oid);
            cp.rest_of_name =  n;
            throw cp;
        } catch(org.omg.CORBA.SystemException se){
            throw se;     
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::bind_context in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::bind_context error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }             
            }
        
            throw new CannotProceed(my_reference(oid), n);
        }
    
        try {
            next_context.bind_context(next_name, nc);    
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST no) {
            throw new NotFound(NotFoundReason.not_object,next_name);
        } catch (org.omg.CORBA.SystemException se) {
            throw new CannotProceed(next_context, next_name);
        }
      
    }

  
    public void rebind_context(NameComponent[] n, NamingContext nc)
        throws NotFound, CannotProceed,InvalidName
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::rebind_context ");
        }
    
        NamingContext next_context = null;
        // validate NameComponent[] names
        NameComponent[] next_name = getNextName(n);
        byte[] oid = null;
        String oid_str = null;
   
        try {
    
            oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
  
            // Final Binding
            if(n.length == 1) { 
                my_db.rebind_context(oid_str, n[0].id, n[0].kind,
                                     my_orb.object_to_string(nc));
                return;
            }
      
            // Path Binding
            String next_context_str = my_db.resolve_context(oid_str, n[0].id, n[0].kind);
            next_context = NamingContextHelper.narrow(my_orb.string_to_object(next_context_str));
    
        } catch(NotFound nf) {
            nf.rest_of_name = n;
            throw nf;      
        } catch (CannotProceed cp) {
            cp.cxt = my_reference(oid);
            cp.rest_of_name =  n;
            throw cp;
        } catch(org.omg.CORBA.SystemException se){
            throw se;     
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::rebind_context in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::rebind_context error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }             
            }
        
            throw new CannotProceed(my_reference(oid), n);
        }
    
        try{
            next_context.rebind_context(next_name, nc);
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST no) {
            throw new NotFound(NotFoundReason.not_object,next_name);
        } catch (org.omg.CORBA.SystemException se) {
            throw new CannotProceed(next_context, next_name);
        }
    }

  
    public NamingContext bind_new_context(NameComponent[] n)
        throws NotFound,CannotProceed, InvalidName, AlreadyBound
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::new_context ");
        }
    
        // validate NameComponent[] names
        NameComponent[] next_name = getNextName(n);
        byte[] oid = null;
        String oid_str = null;
        NamingContext next_context = null;
    
        try {
 
            oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
    
            if(n.length == 1) {

                NamingContext new_context = 
                    NamingContextHelper.narrow(my_poa.create_reference(NamingContextHelper.id())); 
              
                String new_ref_str = my_orb.object_to_string(new_context);
                byte [] new_oid = my_poa.reference_to_id(new_context);
                String new_oid_str = Base64Codec.encode(new_oid);
  
                my_db.bind_new_context(oid_str, new_oid_str, n[0].id, n[0].kind, new_ref_str);
        
                return new_context;
        
            } else {
                // Path Binding
                String next_context_str = my_db.resolve_context(oid_str, n[0].id, n[0].kind);
   
                next_context = 
                    NamingContextHelper.narrow(my_orb.string_to_object(next_context_str));
            }
        } catch(NotFound nf) {
            nf.rest_of_name = n;
            throw nf;      
        } catch(AlreadyBound ab) {
            throw ab;      
        } catch (CannotProceed cp) {
            cp.cxt = my_reference(oid);
            cp.rest_of_name =  n;
            throw cp;
        } catch (org.omg.CORBA.SystemException se) {
            throw se;
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::bind_new_context in ",oid_str, 
                         " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::bind_newcontext error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
        
                }             
      
            }
            throw new org.omg.CORBA.TRANSIENT();
    
        }
    
        try{
            return next_context.bind_new_context(next_name);
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST no) {
            throw new NotFound(NotFoundReason.not_object,next_name);
        } catch (org.omg.CORBA.SystemException se) {
            throw new CannotProceed(next_context, next_name);
        }
    }

    public void destroy() throws NotEmpty
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::destroy ");
        }
    
        String oid_str = null;
    
        try {
      
            byte[] oid = my_current.get_object_id();
            oid_str = Base64Codec.encode(oid);
      
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::destroy in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::destroy error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }             
            }
        }  
    
        my_db.destroy(oid_str);

    }

  
    public void list(int how_many, BindingListHolder bl, BindingIteratorHolder bi)
    {
        if (my_trace != null) {
            my_trace.print(Trace.ERROR,"NamingContextImpl::list ");
        }
    
        String oid_str = null;
        try {
      
            byte[] oid = my_current.get_object_id();
    
            oid_str = Base64Codec.encode(oid);
  
            RowIterator row_iterator = my_db.list(oid_str);
            bl.value = new Binding[0];
            if(row_iterator == null) {
                bl.value = new Binding[1];
                bl.value = TIDNamingConstants.NULL_BINDING_LIST;
                return;
            }
      
            bl.value = row_iterator.next_n(how_many);
      
            if(bl.value.length < how_many)
                return;
            BindingIteratorImpl iterator = 
                new BindingIteratorImpl(row_iterator, my_iterators_poa);
      
            byte[] iterator_oid = my_iterators_poa.activate_object(iterator);
      
            iterator.setName(es.tid.TIDorbj.util.Base64Codec.encode(iterator_oid));
       
            bi.value =  BindingIteratorHelper.narrow(
                                   my_iterators_poa.id_to_reference(iterator_oid));
    
        } catch(org.omg.CORBA.SystemException se){
            throw se;  
        } catch(Throwable th) {
            if (my_trace != null) {
                if(oid_str != null) {
                    String[] msg = 
                        {"NamingContextImpl::list in  ",oid_str, " error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                } else {
                    String[] msg = 
                        {"NamingContextImpl::list error: ",th.toString()}; 
                    my_trace.print(Trace.ERROR,msg);
                }             
            }
        }  
    }

  
    private static NameComponent[] getNextName(NameComponent[] n) throws InvalidName 
    {
        int name_size = n.length;
   
        if(name_size == 0)
            throw new InvalidName();
        
        for(int i = 0; i < name_size; i++) {
            if ((n[i].id.length() == 0) && (n[i].kind.length() == 0) )
                throw new InvalidName();
        }

        if(name_size == 1)
            return TIDNamingConstants.EMPTY_NAME;
     
        name_size --;
   
        NameComponent[] next_name = new org.omg.CosNaming.NameComponent[name_size];
        System.arraycopy(n, 1, next_name, 0, name_size);
    
        return next_name;
    }


    private NamingContext my_reference(byte[] my_oid) {
        try {
            return NamingContextHelper.narrow(
                      my_poa.create_reference_with_id(my_oid, NamingContextHelper.id()));
        } catch(Exception ex) {
            return null;
        }
    }
 }
