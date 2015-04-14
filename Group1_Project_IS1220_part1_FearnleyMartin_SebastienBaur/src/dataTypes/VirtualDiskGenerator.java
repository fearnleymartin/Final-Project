package dataTypes;

import graphImplementation.NotInGraphException;

public class VirtualDiskGenerator {
	public static void main(String[] args) throws NotInGraphException, NoAvailableSpaceException, NotADirectoryException, InvalidVirtualDiskException {

		//makes vd, adds level 1 and saves
		VirtualDisk vd = VirtualDisk.createVirtualDisk("vdlevel1", "virtual disks/vdlevel1.ser",10000);

		try {
			vd.importFileStructure("eval/Host/level 1", "Home");
		} catch (NoAvailableSpaceException e) {
			e.printStackTrace();
		}
		
		vd.saveVirtualDisk();
	}
}
