package Clients;

import javax.swing.JFrame;
import javax.swing.JTree;

import treeImplementation.NotInTreeException;
import dataTypes.VirtualDisk;

public class test extends JFrame {

	public static void main(String[] args)  throws NotInTreeException, VirtualDiskDoesntExistException {
		// TODO Auto-generated method stub
//		CLUI.crvfs("pastetest",1000);
//		CLUI.impvfs("eval/pasteTest", "pastetest", "Home");
//		JTree tree= TreeUtil.buildTreeFromVd(CLUI.getVdACNFromVfsname("pastetest").getVd());
//		JFrame f = new JFrame();
//		f.setSize(700, 500);
//	
//		
//		
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.add(tree);
//		f.setVisible(true);
		VirtualDisk vd456 = VirtualDisk.loadVirtualDisk("virtual disks/salu25t.ser");
		System.out.println(vd456.getTree());
	}

}
