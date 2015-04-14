package Clients;
import graphImplementation.Graph;
import graphImplementation.Node;
import graphImplementation.NotInGraphException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import dataTypes.Directory;
import dataTypes.Fichier;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.VirtualDisk;

public class GenerateTree {
	JFrame f = new JFrame();
	 
	// Create the root node, I'm assuming that the delimited strings will have
    // different string value at index 0
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Home");
    
	// Create the tree model and add the root node to it
    DefaultTreeModel model = new DefaultTreeModel(root);
    
	// Create the tree with the new model
    JTree tree = new JTree(model);
//    tree.setRootVisible(false);
    
    public GenerateTree(VirtualDisk vd) throws NotInGraphException {
      
        
        //build the tree from the virtual disk's graph object
        Graph graph = vd.getGraph();
        List<Node> nodeList = graph.getNodeList();
        List<String> pathList = new ArrayList<String>();
//        nodeList.remove(vd.getNodeFromPath("Home"));
        for (Node n : nodeList){
        	pathList.add(vd.getPath(n));
        }
        
        for (String path: pathList){
        	buildTreeFromString(model,path);
        }
        
        // UI
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);}
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(tree);
        f.setSize(300, 300);
        f.setLocation(200, 200);
        f.setVisible(true);
    }

//    redisplays tree but actually need to update the nodes somewhere
    public void UpdateTree(){
    	
    	((DefaultTreeModel)tree.getModel()).reload();
    	 for (int i = 0; i < tree.getRowCount(); i++) {
             tree.expandRow(i);}
    	f.invalidate();
    	f.validate();
    	f.repaint();
    }
    /**
     * Builds a tree from a given forward slash delimited string.
     * 
     * @param model The tree model
     * @param str The string to build the tree from
     */
    private void buildTreeFromString(final DefaultTreeModel model, final String str) {
        // Fetch the root node
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        // Split the string around the delimiter
        String [] strings = str.split("/");
        strings = Arrays.copyOfRange(strings, 1, strings.length);
        // Create a node object to use for traversing down the tree as it 
        // is being created
        DefaultMutableTreeNode node = root;

        // Iterate of the string array
        for (String s: strings) {
            // Look for the index of a node at the current level that
            // has a value equal to the current string
            int index = childIndex(node, s);

            // Index less than 0, this is a new node not currently present on the tree
            if (index < 0) {
                // Add the new node
                DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(s);
                node.insert(newChild, node.getChildCount());
                node = newChild;
            }
            // Else, existing node, skip to the next string
            else {
                node = (DefaultMutableTreeNode) node.getChildAt(index);
            }
        }
    }

    /**
     * Returns the index of a child of a given node, provided its string value.
     * 
     * @param node The node to search its children
     * @param childValue The value of the child to compare with
     * @return The index
     */
    private int childIndex(final DefaultMutableTreeNode node, final String childValue) {
        Enumeration<DefaultMutableTreeNode> children = node.children();
        DefaultMutableTreeNode child = null;
        int index = -1;

        while (children.hasMoreElements() && index < 0) {
            child = children.nextElement();

            if (child.getUserObject() != null && childValue.equals(child.getUserObject())) {
                index = node.getIndex(child);
            }
        }

        return index;
    }

    public static void main(String[] args) throws NotInGraphException, NotADirectoryException, NoAvailableSpaceException {
    	VirtualDisk vd = new VirtualDisk("vdg","vdg2.ser",100000);
    	vd.getGraph().getNodeList().add(new Directory("Home"));
    	Fichier f = new Fichier("test");
    	vd.addFile("Home", f);
        new GenerateTree(vd);
    }
}
