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
import java.awt.event.*;
import java.io.*;


public class MenubarAdmin extends JMenuBar 
{

    JMenu jmservice = new JMenu("Service");
    JMenu jmagentoptions = new JMenu("Agent Options");
    JMenu jmnamingoptions = new JMenu("Naming Options");
    JMenuItem jmiexit = new JMenuItem("Exit");
    JMenuItem jmiloadagentior = new JMenuItem("Load Agent IOR");
    JMenuItem jmibindnewcontext = new JMenuItem("Bind New Context");
    JMenuItem jmirebindcontext = new JMenuItem("Rebind Context");
    JMenuItem jmibindcontext = new JMenuItem("Bind Context");
    JMenuItem jminewcontext = new JMenuItem("New Context");
    JMenuItem jmiunbind = new JMenuItem("Unbind");
    JMenuItem jmirebind = new JMenuItem("Rebind");
    JMenuItem jmibind = new JMenuItem("Bind");
    JMenuItem jmidestroy = new JMenuItem("Destroy");
    JMenuItem jmiremovespace = new JMenuItem("Remove Space");
    JMenuItem jmideletespace = new JMenuItem("Delete Space");
    JMenuItem jmigetspaces = new JMenuItem("Get Spaces");
    JMenuItem jmiupdatespace = new JMenuItem("Update Space");
    JMenuItem jmiaddspace = new JMenuItem("Add Space");
    JMenuItem jmiinitservice = new JMenuItem("Init Service");
    JMenuItem jminewspace = new JMenuItem("New Space");
    JMenuItem jmisetmaxthreads = new JMenuItem("Set Max Threads");
    JMenuItem jmigetstatus = new JMenuItem("Get Status");
    JMenuItem jmishutdownservice = new JMenuItem("Shutdown Service");
    JMenuItem jmisaveiortofile = new JMenuItem("Save IOR to file");
    SpacesTree spacestree;
    NamingContextTree contexttree;

    public MenubarAdmin() 
    {
    	this.add(jmservice);
        jmservice.add(jmiloadagentior);
        jmservice.add(jmagentoptions);
        jmservice.add(jmnamingoptions);
        jmservice.addSeparator();
        jmservice.add(jmiexit);
        jmagentoptions.add(jmiinitservice);
        jmagentoptions.add(jmishutdownservice);
        jmagentoptions.add(jmigetstatus);
        jmagentoptions.add(jmisetmaxthreads);
        jmagentoptions.addSeparator();
        jmagentoptions.add(jminewspace);
        jmagentoptions.add(jmiaddspace);
        jmagentoptions.add(jmiupdatespace);
        jmagentoptions.add(jmigetspaces);
        jmagentoptions.add(jmideletespace);
        jmagentoptions.add(jmiremovespace);
        jmnamingoptions.add(jmibind);
        jmnamingoptions.add(jmirebind);
        jmnamingoptions.add(jmiunbind);
        jmnamingoptions.addSeparator();
        jmnamingoptions.add(jminewcontext);
        jmnamingoptions.add(jmibindcontext);
        jmnamingoptions.add(jmirebindcontext);
        jmnamingoptions.add(jmibindnewcontext);
        jmnamingoptions.add(jmidestroy);
        jmnamingoptions.addSeparator();
        jmnamingoptions.add(jmisaveiortofile);
		
        jmiexit.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    System.exit(0);
                }
            });	
		
        /************** EVENTOS RELACIONADOS CON EL ARBOL DE ESPACIOS **************/
        jmiinitservice.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.initService();
                }
            });
        jmishutdownservice.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.shutdownService();
                }
            });
        jmigetstatus.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.getStatus();
                }
            });
        jmisetmaxthreads.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.setMaxThreads();
                }
            });
        jminewspace.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.newSpace();
                }
            });	
        jmiaddspace.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.addSpace();
                }
            });	
        jmigetspaces.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.getSpaces();
                }
            });
        jmideletespace.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.deleteSpace();
                }
            });
        jmiremovespace.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    spacestree.removeSpace();
                }
            });	
		
        /************** EVENTOS RELACIONADOS CON EL ARBOL DE UN ESPACIO **************/	
        jmibind.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.bind();
                }
            });	
        jmirebind.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.rebind();
                }
            });	
        jmiunbind.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.unbind();
                }
            });	
        jminewcontext.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.newContext();
                }
            });
        jmibindcontext.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.bindContext();
                }
            });
        jmirebindcontext.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.rebindContext();
                }
            });	
        jmibindnewcontext.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.bindNewContext();
                }
            });
        jmidestroy.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.destroy();
                }
            });
        jmisaveiortofile.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    contexttree.saveToFile();
                }
            });
								
    }//constructor
	
    public void setSpacesTree (SpacesTree sptree)
    {
        this.spacestree = sptree;
    }//setSpacesTree


    public void setContextTree (NamingContextTree nmtree)
    {
        this.contexttree = nmtree;
    }//setContextTree

}//class
