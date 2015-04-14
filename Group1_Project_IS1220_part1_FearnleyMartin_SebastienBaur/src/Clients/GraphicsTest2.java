package Clients;
import java.awt.HeadlessException;
import java.util.List;

import graphImplementation.Graph;
import graphImplementation.Node;
import graphImplementation.NotInGraphException;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import dataTypes.Directory;
import dataTypes.Fichier;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.VirtualDisk;

public class GraphicsTest2 extends JFrame {
	    
	    public static void main(String[] args) throws NotInGraphException, NoAvailableSpaceException, NotADirectoryException
	    {
	    	
	    VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
	    GenerateTree tree = new GenerateTree(vd);
	    }       
	}
