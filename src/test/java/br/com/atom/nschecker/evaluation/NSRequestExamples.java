package br.com.atom.nschecker.evaluation;

import java.util.ArrayList;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nschecker.classes.CPU;
import br.com.atom.nschecker.classes.Memory;
import br.com.atom.nschecker.classes.Node;
import br.com.atom.nschecker.classes.Storage;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.repositories.LocationRestRepository;
import br.com.atom.nschecker.repositories.VNFCRepository;
import br.com.atom.nschecker.util.NamedNetFunction;

public class NSRequestExamples {

	public OntologyManager ontomanager;
	protected VNFCRepository vnfcDB;
	protected LocationRestRepository lrrDB;
	public ArrayList<String> nfvipops;

	public NSRequestExamples(OntologyManager ontomanager, ArrayList<String> nfvipops) {
		this.ontomanager = ontomanager;
		this.vnfcDB = new VNFCRepository(ontomanager);
		this.lrrDB = new LocationRestRepository(ontomanager);
		this.nfvipops = nfvipops;
	}

	// Create Network Service Request 
	public void createNetworkServiceRequest(int num_hosts_per_nfvipop, int num_vnfcs) {

		CPU cpu = new CPU();
		cpu.setName("Virtual CPU");
		cpu.setNumCores(1);
		cpu.setAvailableCores(1);
		cpu.setSpeed(1330000);

		Memory mem = new Memory();
		mem.setName("Virtual Memory");
		mem.setSize(2);
		mem.setAvailableSize(2);

		Storage sto = new Storage();
		sto.setName("Virtual HD");
		sto.setSize(100);
		sto.setAvailableSize(100);

		ArrayList<VNFC> vnfcs = new ArrayList<VNFC>();
		
		int num_groups = num_hosts_per_nfvipop;
		int index_groups = 1;
		int index_nfvipop = 0;
		
		for (int i=1; i<=num_vnfcs;i++) {
			
			String nfvipop_name = nfvipops.get(index_nfvipop);			
			int index_nfvinode =  index_groups;	
			
			Node nfvinode = new Node();
			nfvinode.setName("NFVINode" + index_nfvinode + " " + nfvipop_name);
			
			VNFC vnfc = new VNFC();
			vnfc.setName("NSReq VNF"+i);
			vnfc.setCpu(cpu);
			vnfc.setMemory(mem);
			vnfc.setStorage(sto);
			vnfc.setNumInterfaces(1);
			vnfc.setGuestNode(nfvinode);
			vnfc.setNetFunction(NamedNetFunction.FIREWALL);
			
			vnfcs.add(vnfc);
			
			//Restriction 1
			this.vnfcDB.createVNFCIndividual(vnfc, true);
			
			//Restriction 2
			//if (i > 1) {
				//manager.getRestManager().createNotSameNodeRestriction(vnfc, vnfcs.get(i-2));
			//}
			
			index_nfvipop+=1;
			if (index_nfvipop == nfvipops.size()) {
				index_nfvipop = 0;
				index_groups+=1;
				if (index_groups == num_groups) {
					index_groups = 1;
				}
			}	
			
		}
		
		// Restriction 2
		for (int i = 0; i<vnfcs.size();i++) {
			VNFC vnfcSource = vnfcs.get(i);
			for (int j = i+1; j<vnfcs.size();j++) {
				VNFC vnfcTarget = vnfcs.get(j);
				if (vnfcSource.getGuestNode().getName() != vnfcTarget.getGuestNode().getName()) {
					//manager.getRestManager().createNotSameNodeRestriction(vnfcSource, vnfcTarget);
					this.lrrDB.createNotSameNodeRestrictionToVNFCs(vnfcSource.getName(), vnfcTarget.getName());
				} else {
					//manager.getRestManager().createSameNodeRestriction(vnfcSource, vnfcTarget);
					this.lrrDB.createSameNodeRestrictionToVNFCs(vnfcSource.getName(), vnfcTarget.getName());
				}
			}
		}
		
		//manager.getRestManager().createNFVINodesDifferentRestriction(manager.getSfcManager().getNfvinodesFromSFCRequest());
		
	}
	
	public void close(){
		this.nfvipops = null;
	}

}
