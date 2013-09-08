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
import java.awt.*;
import java.awt.event.*;
import org.omg.CosNaming.*;


public class NewContextWindow extends JFrame 
{

    NamingContext context_created;
    JLabel jl1 = new JLabel("Context created IOR:");
    JTextArea jta1 = new JTextArea();
    JButton jbaccept = new JButton("Accept");
	

    public NewContextWindow (NamingContext context, org.omg.CORBA.ORB orb) 
    {
    
        try
            {
                Toolkit tk = Toolkit.getDefaultToolkit();
                Dimension d = tk.getScreenSize();
	    	this.setSize(400,305);
                this.setLocation((d.width-400)/2,(d.height-305)/2);
                this.getContentPane().setLayout(null);
                this.setResizable(false);
                this.setTitle("New Context Window");
                jl1.setBounds(new Rectangle(10, 10, 125, 21));
                jta1.setLineWrap(true);
                jta1.setBounds(new Rectangle(10, 40, 375, 184));
                jbaccept.setBounds(new Rectangle(160, 238, 79, 27)); 
                context_created = context.new_context();
                jta1.setText(orb.object_to_string(context_created));
                jta1.setEditable(false);
                this.getContentPane().setLayout(null);
                this.getContentPane().add(jta1);
                this.getContentPane().add(jbaccept);
                this.getContentPane().add(jl1);
                jbaccept.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            accept();
                        }
		    });
                this.show();
            }
        catch (org.omg.CORBA.OBJECT_NOT_EXIST ex)
            {
                JOptionPane.showMessageDialog(null,"OBJECT_NOT_EXIST Exception has been raised","corba error",JOptionPane.ERROR_MESSAGE);
            }
        catch (java.lang.Exception exg)
            {
                JOptionPane.showMessageDialog(null,exg.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
            }
		
    }//constructor


    private void accept() 
    {
    	this.hide();
        this.dispose();
    }//aceptar


}//class
