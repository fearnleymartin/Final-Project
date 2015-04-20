package dataTypes;

import java.io.Serializable;

public class Directory extends treeImplementation.Node implements Serializable, Visitable{



	public Directory(String name) {
		super();
		this.name = name;
	}





	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}





	@Override
	public String toString() {
		return "Directory [" +name +"]";
	}





	@Override
	public long accept(Visitor visitor) {
		return visitor.visit(this);

	}






}
