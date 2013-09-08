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
import java.awt.event.MouseEvent;


public class ObjectPopupMenu extends JPopupMenu 
{

    JMenuItem jmirebind = new JMenuItem("Rebind");
    JMenuItem jmiunbind = new JMenuItem("Unbind");
    JMenuItem jmisavetofile = new JMenuItem("Save IOR to file");
    int cord_x; //coordenada x donde se visualizara el menu
    int cord_y; //coordenada y donde se visualizara el menu
    NamingContextTree tree; //es al arbol al cual se asociara este menu

    public ObjectPopupMenu(java.awt.Component obj,int x, int y) 
    {
        this.setInvoker(obj);
        this.tree = (NamingContextTree)obj;
	this.add(jmirebind);
        this.add(jmiunbind);
	this.addSeparator();
        this.add(jmisavetofile);
	this.cord_x = x;
	this.cord_y = y;
	jmirebind.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    tree.rebind();
                }
            });
	jmiunbind.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    tree.unbind();
                }
            });
	jmisavetofile.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    tree.saveToFile();
                }
            });
	showMenu();
	
    } //constructor
  
    private void showMenu()
    {
  	show(getInvoker(),cord_x,cord_y);
  
    } //showMenu
  
  
  
  
} //class
