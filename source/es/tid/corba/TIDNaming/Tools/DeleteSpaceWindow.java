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
import es.tid.corba.TIDNamingAdmin.*;


public class DeleteSpaceWindow extends JFrame {

    String spacename;
    Agent agent;
    JLabel jl1 = new JLabel("Delete Space:");
    JTextField jtfEspacioActivo = new JTextField();
    JButton jbaccept = new JButton("Accept");
    JButton jbcancel = new JButton("Cancel");
	
    public DeleteSpaceWindow (String spacename, Agent agent) 
    {
  
  	this.spacename = spacename;
        this.agent = agent;
	Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        this.setSize(390,225);
        this.setLocation((d.width-390)/2,(d.height-225)/2);
        this.setResizable(false);
        this.setTitle("Delete Space Window");
        jl1.setBounds(new Rectangle(14, 64, 190, 21));
        jtfEspacioActivo.setBounds(new Rectangle(206, 64, 145, 21));
        jtfEspacioActivo.setText(spacename);
        jbaccept.setBounds(new Rectangle(66, 147, 79, 27));
        jbcancel.setBounds(new Rectangle(213, 147, 79, 27));
        this.getContentPane().setLayout(null);
        this.getContentPane().add(jl1);
        this.getContentPane().add(jtfEspacioActivo);
        this.getContentPane().add(jbaccept);
        this.getContentPane().add(jbcancel);
        jbaccept.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    acceptDelete();
                }
            });
        jbcancel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    cancelDelete();
                }
            });
	this.show();
	
    }//constructor


    private void acceptDelete () 
    {

        try {
            agent.delete_space (spacename);
            this.hide();
            this.dispose();
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist ex1) {
            JOptionPane.showMessageDialog(null,"NamingSpaceNotExist Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed ex2) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.Exception exg) {
            JOptionPane.showMessageDialog(null,exg.getMessage(),"error",
                                          JOptionPane.ERROR_MESSAGE);
        }
	
    } //acceptDelete


    private void cancelDelete () 
    {
        this.hide();
        this.dispose();
    } //cancelDelete


} //class
