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

package es.tid.corba.TIDNaming.Tools;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeExpansionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.*;
import org.omg.CosNaming.*;
import es.tid.corba.TIDNamingAdmin.*;
import es.tid.TIDorbj.core.util.Corbaname;


public class NamingContextTree extends javax.swing.JTree 
    implements javax.swing.event.TreeWillExpandListener,
               javax.swing.event.TreeExpansionListener,    
               java.awt.event.MouseListener
{

    String root_context_ior; //ior del root context del arbol
    NamingContext root_context; //es el root_context
    String root_tree_name; //nombre que se le da a la raiz del arbol
    org.omg.CORBA.ORB orb; //orb a utilizar por el NamingContextTree
    DefaultMutableTreeNode root_tree; //nodo raiz del arbol
    Agent agent; //es el agente
    TreePath pathcoll;//es el path del nodo a colapsar en cada momento
    boolean collapse=false;

    public NamingContextTree (org.omg.CORBA.ORB orb, String root_tree_name)
    {	
	
        super(new DefaultTreeModel(new DefaultMutableTreeNode(root_tree_name)));
        this.orb = orb;
        this.root_tree_name = root_tree_name;
        root_tree = (DefaultMutableTreeNode)(this.getModel()).getRoot();
        root_tree.add(new DefaultMutableTreeNode("<empty>"));
        this.putClientProperty ("JTree.lineStyle","Angled");
        this.setShowsRootHandles(true); 	
        this.addTreeWillExpandListener(this);
        this.addMouseListener (this);
        this.addTreeExpansionListener(this);
        this.setVisible(false);

    } //constructor
	
	
    public void treeWillExpand (TreeExpansionEvent event){nodeDescendantsGenerate(event);}
    public void treeWillCollapse (TreeExpansionEvent event){nodeDescendantsDegenerate(event);}
    public void treeCollapsed(TreeExpansionEvent event){} 
    public void treeExpanded(TreeExpansionEvent event){removeExpansion();}
    public void mouseClicked (MouseEvent e) {} 
    public void mouseEntered (MouseEvent e)  {}
    public void mouseExited (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {treatEvent(e);}
    public void mouseReleased (MouseEvent e) {}
	
	
    public void setRootContext (String root_cxt_ior)	
    {	
        this.root_context_ior =  root_cxt_ior;
        this.root_context = NamingContextHelper.narrow(orb.string_to_object(root_cxt_ior));
        this.collapsePath(getPathForRow(0));
        ((DefaultTreeModel)this.getModel()).reload(null);
        this.setVisible(true);

    } //setRoot
	
	
    public void setAgent (Agent agent)
    {
        this.agent = agent;
    }//setAgent	
		
		
    private void removeExpansion()
    {
        if (agent.get_status().state.value()!=2)//el servicio no esta funcionando
            this.collapseRow(0);
        else if (collapse) {
            this.collapsePath(pathcoll);
            collapse=false;
        }
    }//removeExpansion	
		
		
    private void nodeDescendantsGenerate (TreeExpansionEvent event)
    {
	
        DefaultMutableTreeNode node; // es el nodo a expandir
        TreePath nodepath; //ruta desde el nodo raiz hasta el nodo a expandir
        String nodepathstring=""; //ruta desde el nodo raiz hasta el nodo a expandir
        NameComponent [] noderoute;
        NamingContext obj=null;
        int length; //longitud de la ruta (array)
        Binding[] bindinglist=null;
        int long_bindinglist=0;/*************/
        BindingIterator bindingiterator=null;
        BindingListHolder bindinglist_H = new BindingListHolder();
        BindingIteratorHolder bindingiterator_H = new BindingIteratorHolder();
        Binding [] resto_nodos=null;
        boolean seguir=true;
        String id, kind, name;
        int i;
		
        if (agent.get_status().state.value()!=2)//el servicio no esta funcionando
            JOptionPane.showMessageDialog(null,"Service must be running","caution",JOptionPane.INFORMATION_MESSAGE);	
        else {
            nodepath = event.getPath();
            if (nodepath.getPathCount()>1) {//se mete por aqui si el nodo a desarrollar no es el raiz
                length = nodepath.getPathCount();
                node = (DefaultMutableTreeNode)nodepath.getPathComponent(length-1);
                for (i=1;i<length;i++) {
                    if (i==1)
                        nodepathstring = nodepath.getPathComponent(i).toString();
                    else
                        nodepathstring = nodepathstring + '/' + nodepath.getPathComponent(i).toString();
                } //for
                
                try {
                    noderoute = Corbaname.toName(nodepathstring);
                    obj = NamingContextHelper.narrow(root_context.resolve(noderoute));
                } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0) {
                    JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised",
                                                  "corba error",JOptionPane.ERROR_MESSAGE);
                } catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1) {
                    JOptionPane.showMessageDialog(null,"NotFound Exception has been raised",
                                                  "corba error",JOptionPane.ERROR_MESSAGE);
                } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2) {
                    JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                                  "corba error",JOptionPane.ERROR_MESSAGE);
                }
            } //if
            else {//se va a desarrollar el nodo raiz
                length = 1;		
                node = (DefaultMutableTreeNode)nodepath.getPathComponent(length-1);
                obj = root_context;
            } //else
            node.removeAllChildren();
            try/*********************/ {

                obj.list(100,bindinglist_H,bindingiterator_H);
                bindinglist = bindinglist_H.value;
                bindingiterator = bindingiterator_H.value;
                long_bindinglist = bindinglist.length;
                
                if (long_bindinglist>0)
                    {//tengo todos los bindings del contexto en el bindinglist y el contexto no es vacio
                        
                        for (i=0;i<long_bindinglist;i++){
                            if (bindinglist[i].binding_type==BindingType.nobject)
                                {//el nodo es un 'objeto'
                                    id = (bindinglist[i].binding_name)[bindinglist[i].binding_name.length-1].id;
                                    kind = (bindinglist[i].binding_name)[bindinglist[i].binding_name.length-1].kind;
                                    name = id + '.' + kind;
                                    node.add(new DefaultMutableTreeNode (name));
                                } //if
                            else
                                {//el nodo es un 'contexto'
                                    DefaultMutableTreeNode aux1;
                                    
                                    id = (bindinglist[i].binding_name)[bindinglist[i].binding_name.length-1].id;
                                    kind = (bindinglist[i].binding_name)[bindinglist[i].binding_name.length-1].kind;
                                    name = id + '.' + kind;
                                    aux1 = new DefaultMutableTreeNode (name);
                                    node.add(aux1);
                                    aux1.add(new DefaultMutableTreeNode("<empty>"));
                                }//else
                        }//for
                        while ((bindingiterator!=null)&(seguir))
                            {//se condidera si hay elementos por recorrer con el BindingIterator
                                seguir=bindingiterator.next_n(100,bindinglist_H);
                                resto_nodos = bindinglist_H.value;
                                for (i=0;i<resto_nodos.length;i++){
                                    if (resto_nodos[i].binding_type==BindingType.nobject)
                                        {//el nodo es un 'objeto'
                                            id = (resto_nodos[i].binding_name)[resto_nodos[i].binding_name.length-1].id;
                                            kind = (resto_nodos[i].binding_name)[resto_nodos[i].binding_name.length-1].kind;
                                            name = id + '.' + kind;
                                            node.add(new DefaultMutableTreeNode (name));
                                        } //if
                                    else
                                        {//el nodo es un 'contexto'
                                            DefaultMutableTreeNode aux1;
                                            
                                            id = (resto_nodos[i].binding_name)[resto_nodos[i].binding_name.length-1].id;
                                            kind = (resto_nodos[i].binding_name)[resto_nodos[i].binding_name.length-1].kind;
                                            name = id + '.' + kind;
                                            aux1 = new DefaultMutableTreeNode (name);
                                            node.add(aux1);
                                            aux1.add(new DefaultMutableTreeNode("<empty>"));
                                        }//else
                                }//for
                            } //while
                        if (bindingiterator!=null)
                            bindingiterator.destroy();
                    }//if
                else 
                    {//el contexto esta vacio
                        node.add(new DefaultMutableTreeNode("<empty>"));
                    }
                
                ((DefaultTreeModel)this.getModel()).reload(node);
            }//try
            catch (org.omg.CORBA.OBJECT_NOT_EXIST ex3) {
                node.add(new DefaultMutableTreeNode("<empty>"));
                JOptionPane.showMessageDialog(null,"OBJECT_NOT_EXIST Exception has been raised",
                                              "corba error",JOptionPane.ERROR_MESSAGE);
                pathcoll = nodepath;
                collapse=true;
            }
        }//else
    } //nodeDescendantsGenerate
    
    
    private void nodeDescendantsDegenerate (TreeExpansionEvent event)
    {	
	
        DefaultMutableTreeNode node; // es el nodo a colapsar
        TreePath nodepath; //ruta desde el nodo raiz hasta el nodo a expandir
        int length; //longitud de la ruta (array)
		
        nodepath = event.getPath();
        length = nodepath.getPathCount();	
        node = (DefaultMutableTreeNode)nodepath.getPathComponent(length-1); 
        node.removeAllChildren();
        node.add(new DefaultMutableTreeNode("<empty>"));	
		
    }//nodeDescendantsDegenerate	
		
		
    private void treatEvent (MouseEvent e)
    {
	
        JPopupMenu menu;
        TreePath nodepath;
        String nodevalue;
        DefaultMutableTreeNode node=null; //nodo en el que se ha hecho click
		 		
        nodepath = this.getPathForLocation(e.getX(), e.getY());		
        if (nodepath!=null)
            {//se ha producido en evento de click sobre un nodo
                this.setSelectionPath(nodepath); //el nodo toma el foco
                node = (DefaultMutableTreeNode)nodepath.getPathComponent(nodepath.getPathCount()-1);
                nodevalue = node.toString();
                if ((e.getModifiers()==InputEvent.BUTTON3_MASK)&(!(nodevalue.equals("<empty>")))) 
                    {//se ha hecho click en boton derecho sobre un objeto (contexto u objeto)
                        if (node.isLeaf()) //el nodo clickeado es un objeto
                            {
                                menu = new ObjectPopupMenu(this,e.getX(),e.getY());
					
                            }
                        else
                            {
                                menu = new ContextPopupMenu(this,e.getX(),e.getY());	
                            }
                    }//if
            }//if

    }//treatEvent	
			
		
    public void destroy()
    {
        DestroyWindow win;
        NamingContext context;
        TreePath contextpath;
        String contextpathstring="";
        String element;
        NameComponent [] contextroute;
        int i;
        //		String borrame="corbaname:rir:#";/*********************************************/
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount();i++) {
            if (i==1)
                contextpathstring = contextpath.getPathComponent(i).toString();
            else
                contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
        } //for
        //		/***************************************************************/
        //		borrame = borrame+contextpathstring;
        //		System.out.println(orb.string_to_object(borrame));
        //		/***************************************************************/
        try {
            contextroute = Corbaname.toName(contextpathstring);
            if (contextpath.getPathCount()==1)
                { //se hace destroy del root-context
                    context = root_context;
                    element = root_tree_name;
                }
            else
                {
                    context = NamingContextHelper.narrow(root_context.resolve(contextroute));
                    element = contextpath.getPathComponent(i-1).toString();
                }
            win = new DestroyWindow(context,element);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0) {
            JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1) {
            JOptionPane.showMessageDialog(null,"NotFound Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        }
        repaint();
    } //destroy	
	
	
    public void unbind() /* MIRAR QUE NO SE PUEDE HACER UNBIND DEL ROOT-CONTEXT*/
    {
        UnbindWindow win;
        NamingContext context; //contexto que contiene al objeto
        NameComponent[] name; //es el objeto a eliminar en si
        TreePath contextpath;
        String contextpathstring="";
        NameComponent [] contextroute;
        int i;
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount()-1;i++) {
            if (i==1)
                contextpathstring = contextpath.getPathComponent(i).toString();
            else
                contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
        } //for
        try {
            contextroute = Corbaname.toName(contextpathstring);
            name = Corbaname.toName(contextpath.getPathComponent(i).toString());
            if (contextpath.getPathCount()==2)
                context = root_context;
            else	
                context = NamingContextHelper.narrow(root_context.resolve(contextroute));
            win = new UnbindWindow(context,name);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0) {
            JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1) {
            JOptionPane.showMessageDialog(null,"NotFound Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        }
        repaint();
    } //unbind
	
	
    public void bind()
    {
        BindWindow win;
        NamingContext context;
        TreePath contextpath;
        String contextpathstring="";
        NameComponent [] contextroute;
        int i;
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount();i++) {
            if (i==1)
                contextpathstring = contextpath.getPathComponent(i).toString();
      		else
                    contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
        } //for
        try {
            contextroute = Corbaname.toName(contextpathstring);
            if (contextpath.getPathCount()==1)
                { //se hace un bind en  el root-context
                    context = root_context;
                }
            else
                {
                    context = NamingContextHelper.narrow(root_context.resolve(contextroute));
                }
            win = new BindWindow(context,orb);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0) {
            JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1) {
            JOptionPane.showMessageDialog(null,"NotFound Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        }
        repaint();
    } //bind
	
	
    public void newContext()
    {
	
        NewContextWindow win;
        NamingContext context;
        TreePath contextpath;
        String contextpathstring="";
        NameComponent [] contextroute;
        int i;
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount();i++) {
            if (i==1)
                contextpathstring = contextpath.getPathComponent(i).toString();
            else
                contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
        } //for
        try {
            contextroute = Corbaname.toName(contextpathstring);
            if (contextpath.getPathCount()==1)
                { //se hace un newContext en  el root-context
                    context = root_context;
                }
            else
                {
                    context = NamingContextHelper.narrow(root_context.resolve(contextroute));
                }
            win = new NewContextWindow(context,orb);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0) {
            JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1) {
            JOptionPane.showMessageDialog(null,"NotFound Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        }
        repaint();
    } //newContext
	
	
    public void bindContext()
    {
        BindContextWindow win;
        NamingContext context;
        TreePath contextpath;
        String contextpathstring="";
        NameComponent [] contextroute;
        int i;
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount();i++)
            {
      		if (i==1)
                    contextpathstring = contextpath.getPathComponent(i).toString();
      		else
                    contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
	    } //for
        try
            {
                contextroute = Corbaname.toName(contextpathstring);
                if (contextpath.getPathCount()==1)
                    { //se hace un bindContext en  el root-context
                        context = root_context;
                    }
                else
                    {
                        context = NamingContextHelper.narrow(root_context.resolve(contextroute));
                    }
                win = new BindContextWindow(context,orb);
            }
        catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0)
            {
                JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1)
            {
                JOptionPane.showMessageDialog(null,"NotFound Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2)
            {
                JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        repaint();
    } //bindContext
	
	
    public void rebindContext()
    {
        RebindContextWindow win;
        NamingContext context;
        TreePath contextpath;
        String contextpathstring="";
        NameComponent [] contextroute;
        NameComponent [] contextname;
        int i;
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount()-1;i++)
            {
      		if (i==1)
                    contextpathstring = contextpath.getPathComponent(i).toString();
      		else
                    contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
	    } //for
        System.out.println ("contextpathstring: "+ contextpathstring);
        try
            {
                contextroute = Corbaname.toName(contextpathstring);
                contextname = Corbaname.toName(contextpath.getPathComponent(i).toString());
                if (contextpath.getPathCount()==2)
                    { //se hace un rebindContext en  el root-context
                        context = root_context;
                    }
                else
                    {
                        context = NamingContextHelper.narrow(root_context.resolve(contextroute));
                    }
                win = new RebindContextWindow(context,contextname,orb);
            }
        catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0)
            {
                JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1)
            {
                JOptionPane.showMessageDialog(null,"NotFound Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2)
            {
                JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        repaint();
    } //RebindContext
	
	
    public void bindNewContext()
    {
        BindNewContextWindow win;
        NamingContext context;
        TreePath contextpath;
        String contextpathstring="";
        NameComponent [] contextroute;
        int i;
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount();i++)
            {
      		if (i==1)
                    contextpathstring = contextpath.getPathComponent(i).toString();
      		else
                    contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
	    } //for
        try
            {
                contextroute = Corbaname.toName(contextpathstring);
                if (contextpath.getPathCount()==1)
                    { //se hace un BindnewContext en  el root-context
                        context = root_context;
                    }
                else
                    {
                        context = NamingContextHelper.narrow(root_context.resolve(contextroute));
                    }
                win = new BindNewContextWindow(context,orb);
            }
        catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0)
            {
                JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1)
            {
                JOptionPane.showMessageDialog(null,"NotFound Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2)
            {
                JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        repaint();
    } //bindNewContext
	
	
    public void rebind ()
    {
	
        RebindWindow win;
        NamingContext context;
        TreePath contextpath;
        String contextpathstring="";
        NameComponent [] contextroute;
        NameComponent [] name;
        int i;
		
        contextpath = this.getSelectionPath();
        for (i=1;i<contextpath.getPathCount()-1;i++)
            {
      		if (i==1)
                    contextpathstring = contextpath.getPathComponent(i).toString();
      		else
                    contextpathstring = contextpathstring + '/' + contextpath.getPathComponent(i).toString();
	    } //for
        try
            {
                contextroute = Corbaname.toName(contextpathstring);
                name = Corbaname.toName(contextpath.getPathComponent(i).toString());
                if (contextpath.getPathCount()==2)
                    { //se hace un rebind en  el root-context
                        context = root_context;
                    }
                else
                    {
                        context = NamingContextHelper.narrow(root_context.resolve(contextroute));
                    }
                win = new RebindWindow(context,name,orb);
            }
        catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0)
            {
                JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1)
            {
                JOptionPane.showMessageDialog(null,"NotFound Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2)
            {
                JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        repaint();
    }//rebind
	
    public void saveToFile()
    {
        JFileChooser fc = new JFileChooser();
        int returnVal=0;
        int i;
        File file;
        String ior="";
        TreePath elementpath;
        int length;
        String elementpathstring="";
        NameComponent[] elementroute=null;

        returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
        	file = fc.getSelectedFile();
                try
                    {
                        if (!file.exists())
                            file.createNewFile();
	    		if (file.canWrite())
                            {
	      			BufferedWriter buffer = new BufferedWriter (new FileWriter(file));
                                elementpath = this.getSelectionPath();
                                length = elementpath.getPathCount();
                                if (length==1) //se ha selecionado el root-context
                                    ior = root_context_ior;
                                else
                                    { 
                                        for (i=1;i<length;i++)
                                            {
      						if (i==1)
                                                    elementpathstring = elementpath.getPathComponent(i).toString();
      						else
                                                    elementpathstring = elementpathstring + '/' + elementpath.getPathComponent(i).toString();
                                            } //for
                                        elementroute = Corbaname.toName(elementpathstring);
                                        ior = orb.object_to_string(root_context.resolve(elementroute));
                                    }
                                buffer.write(ior);
                                buffer.flush();
                                buffer.close();
                            }//if
	    		else
                            JOptionPane.showMessageDialog(null,"Can't write in such file","i/o error",JOptionPane.ERROR_MESSAGE);
                    } //try
                catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex0)
                    {
			JOptionPane.showMessageDialog(null,"InvalidName Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
                    }
                catch (java.io.IOException ex1)
                    {
		  	JOptionPane.showMessageDialog(null,ex1.getMessage(),"i/o error",JOptionPane.ERROR_MESSAGE);
                    }
                catch (org.omg.CosNaming.NamingContextPackage.NotFound ex1)
                    {
			JOptionPane.showMessageDialog(null,"NotFound Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
                    }
                catch (org.omg.CosNaming.NamingContextPackage.CannotProceed ex2)
                    {
			JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
                    }
            }//if
        repaint();
    } //saveToFile	
		
		
} //class

























		
		
		
		
		

