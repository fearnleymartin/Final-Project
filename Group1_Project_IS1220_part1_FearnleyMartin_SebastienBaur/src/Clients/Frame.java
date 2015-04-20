package Clients;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import dataTypes.VirtualDisk;

public class Frame extends JFrame implements TreeSelectionListener, ActionListener, MouseListener{
	
//	CLUI.crvfs("vfsGUItest",1000);
	
	VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
	VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
	
	
	
	JTree tree = TreeUtil.buildTreeFromVd(vd);
	TreePath treepath;
	JTextField textField = new JTextField(20);
	String renameText;
	
	
	private JButton button = new JButton("Rename");
	protected JEditorPane htmlPane = new JEditorPane();
	private JPanel panLeft = new JPanel();
	private JPanel panRight = new JPanel();
	JScrollPane htmlView = new JScrollPane(htmlPane);
	
	public Frame() throws NotInTreeException{
		VdcnManagement.getVdList().add(vdcn);
		JFrame frame = new JFrame();
		this.setTitle("VFS");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(1,0));
	
		htmlPane.setEditable(false);
		
		this.getContentPane().add(panLeft);
		this.getContentPane().add(panRight);
		panLeft.add(textField);
		panLeft.add(button);
		panLeft.add(htmlPane);
		panRight.add(tree);
		
		tree.addTreeSelectionListener(this);
		textField.addActionListener(this);
		button.addMouseListener(this);
		
		this.setVisible(true);
		

	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {return;}
		
		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()){
			htmlPane.setText("file");
		}
		else{
			htmlPane.setText("folder");
		}
		
		treepath = e.getPath();
		htmlPane.setText( TreeUtil.treePathToString(treepath));
}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		renameText = textField.getText();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		String oldPath = TreeUtil.treePathToString(treepath);	
		String newPath = textField.getText();
		htmlPane.setText(renameText);
		CLUI.mv("vdlevel1", oldPath, newPath);
		try {
			tree = TreeUtil.buildTreeFromVd(vd);
		} catch (NotInTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setVisible(true);
		validate();
		repaint();
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
