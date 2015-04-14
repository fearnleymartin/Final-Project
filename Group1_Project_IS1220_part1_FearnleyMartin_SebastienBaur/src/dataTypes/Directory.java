package dataTypes;

import java.io.Serializable;

public class Directory extends graphImplementation.Node implements Serializable{



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






}
