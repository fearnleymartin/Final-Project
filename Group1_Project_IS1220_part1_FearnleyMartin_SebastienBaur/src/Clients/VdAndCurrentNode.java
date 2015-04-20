package Clients;

import graphImplementation.Node;
import graphImplementation.NotInGraphException;
import dataTypes.VirtualDisk;

	public class VdAndCurrentNode {

		private Node currentNode;
		private VirtualDisk vd;
		
		public VdAndCurrentNode(VirtualDisk vd) {
			super();
			try {
				this.currentNode = vd.getNodeFromPath("Home");
			} catch (NotInGraphException e) {
				System.out.print("This vd contains no Home node");
				e.printStackTrace();
			}
			this.vd = vd;
		}
		public Node getCurrentNode() {
			return currentNode;
		}
		public void setCurrentNode(Node currentNode) {
			this.currentNode = currentNode;
		}
		public VirtualDisk getVd() {
			return vd;
		}
		public void setVd(VirtualDisk vd) {
			this.vd = vd;
		}
		@Override
		public String toString() {
			return "[currentNode= '" + currentNode + "', vd="
					+ vd + "]";
		}		
	}
