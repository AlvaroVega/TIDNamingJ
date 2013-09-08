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

import javax.swing.tree.*;
import javax.swing.JPopupMenu;
import javax.swing.event.TreeModelEvent;
import javax.swing.JOptionPane;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.TreeExpansionEvent;
import es.tid.corba.TIDNamingAdmin.*;


public class SpacesTree extends javax.swing.JTree 
    implements	java.awt.event.MouseListener,
                javax.swing.event.TreeWillExpandListener
{

    Agent agent; //es el agente
    DefaultMutableTreeNode root_tree_node; //el el nodo raiz del arbol
    String root_tree_name; //es el nombre del nodo raiz del arbol
    String spacename; //es el espacio sobre el que se hace click
    org.omg.CORBA.ORB orb;
    NamingContextTree context_tree; //es el NamingContextTree asociado a este SpaceTree
	
    public SpacesTree (org.omg.CORBA.ORB orb, String root_tree_name, NamingContextTree context_tree)
    {
	
        super(new DefaultTreeModel(new DefaultMutableTreeNode(root_tree_name)));
        this.orb = orb;
        this.root_tree_node = (DefaultMutableTreeNode)(this.getModel()).getRoot();
        this.root_tree_name = root_tree_name;
        this.context_tree = context_tree;
        this.putClientProperty ("JTree.lineStyle","Angled");
        this.setShowsRootHandles(true); 
        this.addTreeWillExpandListener(this);
        this.addMouseListener(this);
        this.setVisible(false);
		
    }//constructor

    public void treeWillExpand (TreeExpansionEvent event){treatGenerate(event);}
    public void treeWillCollapse (TreeExpansionEvent event){}
    public void mouseClicked (MouseEvent e) {} 
    public void mouseEntered (MouseEvent e)  {}
    public void mouseExited (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {treatEvent(e);}
    public void mouseReleased (MouseEvent e) {}


    public void setAgentIOR (String agent_ior)	
    {
        this.agent = AgentHelper.narrow(orb.string_to_object (agent_ior));
        context_tree.setAgent(agent);
    }//setAgentIOR
		
	
    public String getActiveSpaceRootContext (String active_space)	
    {//este metodo devuelve la IOR del root context del espacio que esta activo
		
        String ior=""; //es la ior del root-context del espacio que tienen el foco
        NamingSpace space;
        try {
            spacename = active_space;
            space = agent.get_space(spacename);
            ior = orb.object_to_string(space.root_context); 
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist ex) {
            JOptionPane.showMessageDialog(null,"NamingSpaceNotExist Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        }
        return (ior);
        
    }//setAgentIOR
	

    private void generateTree()
    {
		  
    	int numero_espacios;
        NamingSpace [] spaces_vector;
        DefaultMutableTreeNode spacename_node;
        DefaultMutableTreeNode ior_node;
        DefaultMutableTreeNode type_node;

		
        root_tree_node.removeAllChildren();
    	spaces_vector = agent.get_spaces();
    	numero_espacios = spaces_vector.length;
        if (numero_espacios==0) {
            root_tree_node.add(new DefaultMutableTreeNode("<empty>"));
        }
        else {
            for (int i=0;i<numero_espacios;i++) {
	       		
                spacename_node = new DefaultMutableTreeNode("name: " +(spaces_vector[i]).name);
                root_tree_node.add(spacename_node);
                if (spaces_vector[i].data.discriminator().value()==0) /*SQL*/
                    {
                        type_node = new DefaultMutableTreeNode("SQL access");
                        spacename_node.add(type_node);
                        type_node.add(new DefaultMutableTreeNode("user: " + (spaces_vector[i]).data.sql_data().user));
                        type_node.add(new DefaultMutableTreeNode("passwd: ********"));
                        type_node.add(new DefaultMutableTreeNode("table: " + (spaces_vector[i]).data.sql_data().table)); 
                        type_node.add(new DefaultMutableTreeNode("connection-init: " + (spaces_vector[i]).data.sql_data().connection_init));
                        
                    }
                else if (spaces_vector[i].data.discriminator().value()==1) /*LDAP*/
                    {
                        type_node = new DefaultMutableTreeNode("LDAP access");
                        spacename_node.add(type_node);
                        type_node.add(new DefaultMutableTreeNode ("URL: " + (spaces_vector[i]).data.ldap_data().dir_url));
                    }
                else  /*FILE*/
                    {
                        type_node = new DefaultMutableTreeNode("FILE access");
                        spacename_node.add(type_node);
                        type_node.add(new DefaultMutableTreeNode ("URL: " + (spaces_vector[i]).data.file_data().file_url));
                    }
            }//for
        }
        ((DefaultTreeModel)this.getModel()).reload(root_tree_node);
        this.setVisible(true);
        
    } //Generate_Tree
    
    
    private void treatEvent (MouseEvent e)
    {
	
        JPopupMenu menu;
        TreePath nodepath;
        String nodevalue;
        DefaultMutableTreeNode node=null; //nodo en el que se ha hecho click
        String ior;//es la ior del root-context del espacio activo 		
		
        nodepath = this.getPathForLocation(e.getX(), e.getY());	
        if (nodepath!=null)
            {//se ha producido en evento de click sobre un nodo
                this.setSelectionPath(nodepath); //el nodo toma el foco
                node = (DefaultMutableTreeNode)nodepath.getPathComponent(nodepath.getPathCount()-1);
                nodevalue = node.toString();
                if ((e.getModifiers()==InputEvent.BUTTON1_MASK)&((nodevalue.substring(0,4)).equals("name")))
                    {//se hace click sobre un nodo que es un space-name
                        spacename = nodevalue.substring(6);
                        ior = this.getActiveSpaceRootContext(spacename);
                        context_tree.setRootContext(ior);
							
                    }
                else if (e.getModifiers()==InputEvent.BUTTON3_MASK)
                    {//se ha hecho click en boton derecho
                        if ((nodevalue.equals(root_tree_name)))//se hace click sobre el root del arbol
                            menu = new RootNodeMenu(this,e.getX(),e.getY());
                        else if ((nodevalue.substring(0,4)).equals("name"))//se hace click sobre un nodo que es un space-name
                            {
                                menu = new SpaceNameMenu(this,e.getX(),e.getY());
                                spacename = nodevalue.substring(6);
                            }
                    }
            }

    }//treat_Event	
	
	
    private void treatGenerate(TreeExpansionEvent event)
    {
        TreePath nodepath; //ruta desde el nodo raiz hasta el nodo a expandir
	
        nodepath = event.getPath();
        if (nodepath.getPathCount()==1) //Se va a desarrollar el nodo raiz
            generateTree();
			 
    } //treatGenerate
	
		
    public void getSpaces ()
    {
        this.generateTree();
        this.collapsePath(getPathForRow(0));
    }//getSpaces
	
	
    public void initService()
    {
        try {
            agent.init_service();
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed ex) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        }
		
    }//initService
	
	
    public void shutdownService()
    {
        try {
            agent.shutdown_service();
        }
        catch (es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed ex) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        }
        
    }//shutdownService
		
		
    public void getStatus ()
    {
	
        ServiceStatusWindow win;
		
        win = new ServiceStatusWindow(agent);
		
    }//getStatus
		
		
    public void newSpace ()
    {
	
        NewSpaceWindow win;
		
        win = new NewSpaceWindow(agent);
		
    }//newSpace
	
	
    public void addSpace ()
    {
        AddSpaceWindow win;
		
        win = new AddSpaceWindow(agent,orb);
		
    }//addSpace
	
	
    public void updateSpace ()
    {
        UpdateSpaceWindow win;
		
        win = new UpdateSpaceWindow(spacename,agent);
		
    }//updateSpace
	
	
    public void setMaxThreads ()
    {
        SetMaxThreadsWindow win;
		
        win = new SetMaxThreadsWindow(spacename,agent);
		
    }//setMaxThreads
	
	
    public void deleteSpace ()
    {
        DeleteSpaceWindow win;
		
        win = new DeleteSpaceWindow(spacename,agent);
	
    }//deleteSpace
	
	
    public void removeSpace ()
    {
        RemoveSpaceWindow win;
		
        win = new RemoveSpaceWindow(spacename,agent);
		
    }//removeSpace
	
	

}//class
