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


public class SetMaxThreadsWindow extends JFrame 
{

    JLabel jl1 = new JLabel("Maximum number of threads:");
    JLabel jl2 = new JLabel("Space name:");
    JTextField jtfthreads = new JTextField();
    JTextField jtfactivedspace = new JTextField();
    JButton jbaccept = new JButton("Accept");
    JButton jbcancel = new JButton("Cancel");
    String spacename;
    Agent agent;


    public SetMaxThreadsWindow (String spacename, Agent agent) 
    {

        this.spacename = spacename;
        this.agent = agent;
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        this.setSize(390,225);
        this.setLocation((d.width-390)/2,(d.height-225)/2);
        this.setResizable(false);
        this.setTitle("Set Maximum Number Of Threads Window");
        jl1.setBounds(new Rectangle(14, 93, 181, 21));
	jl2.setBounds(new Rectangle(14, 64, 176, 21));
        jtfthreads.setBounds(new Rectangle(190, 96, 145, 21));
        jtfactivedspace.setBounds(new Rectangle(190, 63, 145, 21));
        jtfactivedspace.setText(spacename);
        jbaccept.setBounds(new Rectangle(66, 147, 79, 27));
        jbcancel.setBounds(new Rectangle(213, 147, 79, 27));
        this.getContentPane().setLayout(null);
        this.getContentPane().add(jl1);
        this.getContentPane().add(jl2);
        this.getContentPane().add(jtfthreads);
        this.getContentPane().add(jtfactivedspace);
        this.getContentPane().add(jbaccept);
        this.getContentPane().add(jbcancel);
        jbaccept.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    acceptThreads();
                }
            });
        jbcancel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    cancelThreads();
                }
            });
	this.show();
	
    }//constructor

    private void acceptThreads () 
    {

        int valor;

        try {
            valor = Integer.parseInt(jtfthreads.getText());
            agent.set_max_threads (spacename,valor);
            this.hide();
            this.dispose();
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.NamingSpaceNotExist ex1) {
            JOptionPane.showMessageDialog(null,"NamingSpaceNotExist Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (es.tid.corba.TIDNamingAdmin.AgentPackage.CannotProceed ex2) {
            JOptionPane.showMessageDialog(null,"CannotProceed Exception has been raised",
                                          "corba error",JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.Exception exg) {
            JOptionPane.showMessageDialog(null,exg.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
        }
	
    } //acceptThreads
  
  
    private void cancelThreads () 
    {
        this.hide();
        this.dispose();
    } //cancelThreads


} //class
