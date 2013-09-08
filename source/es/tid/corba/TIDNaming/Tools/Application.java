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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Application extends JFrame 
{

    MenubarAdmin jmbservicio = new MenubarAdmin();
    SpacesTree spaces_tree;
    NamingContextTree context_tree;
    JScrollPane scrollpanelizquierdo;
    JScrollPane scrollpanelderecho;
    org.omg.CORBA.ORB orb;
    String agentIOR;
    JSplitPane jspppl;
	
	
    public Application ()
    {
	
        Toolkit tk = Toolkit.getDefaultToolkit();  
        Dimension dim = tk.getScreenSize();
        int x = dim.width/2+100;
        int y =  dim.height/2+100;
        this.setSize(x,y);
        Dimension dim2 = new Dimension(x,y-50); //tamano del ContentPane del frame
        this.setLocation((dim.width-x)/2,(dim.height-y)/2);
        this.setTitle("Visual TIDNamingAdmin");
        this.setJMenuBar(jmbservicio);
        this.getContentPane().setBackground(UIManager.getColor("window"));
        InicializeORB();
        context_tree = new NamingContextTree(orb, "root-context");
        spaces_tree = new SpacesTree (orb, "Naming Spaces", context_tree);
        jmbservicio.setSpacesTree(spaces_tree);
        jmbservicio.setContextTree(context_tree);
        scrollpanelizquierdo = new JScrollPane(spaces_tree);
        scrollpanelizquierdo.getViewport().setBackground(UIManager.getColor("window"));
        scrollpanelderecho = new JScrollPane(context_tree);
        scrollpanelderecho.getViewport().setBackground(UIManager.getColor("window"));
        jspppl = new JSplitPane ( JSplitPane.HORIZONTAL_SPLIT,scrollpanelizquierdo,scrollpanelderecho);
        this.getContentPane().add(jspppl);
	    		
        /************* TRATAMIENTO DE LOS EVENTOS *************/
        this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        jmbservicio.getMenu(0).getItem(0).addMouseListener(new MouseAdapter() {
      		public void mousePressed(MouseEvent e) {
                    loadAgentIOR();
                    repaint();
    		}
            });	
    	this.show();
		
    }//constructor
	
		
    private void InicializeORB()
    {
        Properties props;
    	String [] arguments = null/*new String[2]*/;

    	props = System.getProperties();
    	props.put("org.omg.CORBA.ORBClass","es.tid.TIDorbj.core.TIDORB");
    	props.put("org.omg.CORBA.ORBSingletonClass","es.tid.TIDorbj.core.SingletonORB");
    	orb  = org.omg.CORBA.ORB.init (arguments,props);
		
    }//InicializeORB


    private void loadAgentIOR () 
    {
        JFileChooser fc = new JFileChooser();
        int returnVal = 0;
        File file;
        String ior = "";

        returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            try {
                if (file.canRead()) {
                    BufferedReader buffer = new BufferedReader (new FileReader(file));
                    ior = buffer.readLine();
                    agentIOR = ior;
                    spaces_tree.setAgentIOR(agentIOR);
                    buffer.close();
                } else
                    JOptionPane.showMessageDialog(null,"Can't read from file","i/o error",JOptionPane.ERROR_MESSAGE);
            } //try  
            catch (java.io.IOException ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage(),"i/o error",JOptionPane.ERROR_MESSAGE);
            }
        }//if
        else
            JOptionPane.showMessageDialog(null,"IOR hasn't been saved","caution",JOptionPane.INFORMATION_MESSAGE);	
        
    }//loadAgentIOR





    /****************************************************************************************
     ****************************************************************************************/

    public static void main (String [] args)
    {
        Application apli = new Application();
		
    }//main

    /****************************************************************************************
     ****************************************************************************************/

}//class
