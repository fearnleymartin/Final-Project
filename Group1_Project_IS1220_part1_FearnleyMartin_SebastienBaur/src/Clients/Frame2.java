

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import dataTypes.VirtualDisk;

public class Frame2 extends JFrame implements TreeSelectionListener, ActionListener, MouseListener{

	//		CLUI.crvfs("vfsGUItest",1000);

	VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
	VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);



	JTree tree = TreeUtil.buildTreeFromVd(vd);
	TreePath treepath;
	JTextField textField = new JTextField(20);
	String renameText;


	private JButton buttonRename = new JButton("Rename");
	private JButton buttonCopy = new JButton("Copy");
	private JButton buttonPaste = new JButton("Paste");
	private JButton buttonRemove = new JButton("Remove");
	private JButton buttonParent = new JButton("Parent");
	protected JEditorPane htmlPane = new JEditorPane();
	private JPanel panLeft = new JPanel();
	private JPanel panUpRight = new JPanel();
	private JPanel panDownRight = new JPanel();
	JScrollPane htmlView = new JScrollPane(htmlPane);

//	JTabbedPane tabbedPanUpRight = new JTabbedPane();
//	panUpRight.add(new JLabel("Content 1"));
//	tabbedPanUpRight.addTab("vfs1", panUpRight);
//	frame.setContentPane(tabbedPane);
	
	
	
	public Frame2() throws NotInTreeException{
		VdcnManagement.getVdList().add(vdcn);
		JFrame Frame2 = new JFrame();
		this.setTitle("VFS");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new GridLayout(0,2));

		htmlPane.setEditable(false);

		this.getContentPane().add(panLeft);
		this.getContentPane().add(panUpRight);
		panLeft.add(textField);
		panLeft.add(buttonRename);
		panLeft.add(buttonCopy);
		panLeft.add(buttonPaste);
		panLeft.add(buttonRemove);
		panLeft.add(buttonParent);
		panLeft.add(htmlPane);
		panUpRight.add(tree);
		panDownRight.add(new JLabel("salut"));

		tree.addTreeSelectionListener(this);
		textField.addActionListener(this);
		buttonRename.addMouseListener(this);
		buttonCopy.addMouseListener(this);
		buttonPaste.addMouseListener(this);
		buttonRemove.addMouseListener(this);
		buttonParent.addMouseListener(this);
		

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
		htmlPane.setText(renameText);

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

		try {
			Frame2 frame = new Frame2();
		} catch (NotInTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}



