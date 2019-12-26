package br.com.atom.nschecker.evaluation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.gml.GMLReader;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nschecker.classes.CPU;
import br.com.atom.nschecker.classes.Memory;
import br.com.atom.nschecker.classes.NFVI;
import br.com.atom.nschecker.classes.NFVIPoP;
import br.com.atom.nschecker.classes.Node;
import br.com.atom.nschecker.classes.Storage;
import br.com.atom.nschecker.classes.SwitchingMatrix;
import br.com.atom.nschecker.classes.TopologyStatus;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.evaluation.classes.VNF;
import br.com.atom.nschecker.evaluation.classes.VNFFG;
import br.com.atom.nschecker.evaluation.repositories.VNFFGRepository;
import br.com.atom.nschecker.repositories.NFVINodeRepository;
import br.com.atom.nschecker.repositories.NFVIPoPRepository;
import br.com.atom.nschecker.repositories.NFVIRepository;
import br.com.atom.nschecker.repositories.NetworkNodeRepository;
import br.com.atom.nschecker.repositories.PhysicalConnectionRepository;
import br.com.atom.nschecker.util.NamedNetFunction;

public class TopologyZooExamples {

	public OntologyManager ontomanager;
	protected NFVIRepository nfviDB;
	protected NFVIPoPRepository nfviPopDB;
	protected NFVINodeRepository nfviNodeDB;
	protected NetworkNodeRepository netNodeDB;
	protected PhysicalConnectionRepository phyConnDB;
	protected VNFFGRepository vnffgDB;
	public TopologyStatus status;
	public ArrayList<String> nfvipops; 
	public Map<String, CoreSwitch> coreswitches;	
	
	public TopologyZooExamples(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.nfviDB = new NFVIRepository(this.ontomanager);
		this.nfviPopDB = new NFVIPoPRepository(this.ontomanager);
		this.nfviNodeDB = new NFVINodeRepository(this.ontomanager);
		this.netNodeDB = new NetworkNodeRepository(this.ontomanager);
		this.phyConnDB = new PhysicalConnectionRepository(this.ontomanager);
		this.vnffgDB = new VNFFGRepository(this.ontomanager);
		this.nfvipops = new ArrayList<String>();
		this.coreswitches = new HashMap<String, CoreSwitch>();
	}	
	
	public void close() {
		this.nfvipops = null;
		this.coreswitches = null;
		this.status = null;
		this.ontomanager = null;
	}
	
	private int getIterableCount(Iterable<?> elements) {
		int counter = 0;

		Iterator<?> iterator = elements.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			counter++;
		}

		return counter;
	}

	// Create Network Services - each one has 3 VNFs
	public void createNetworkServices(int num_hosts_per_nfvipop, int num_nfv_services) {
		
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
		
		int num_groups = num_hosts_per_nfvipop/3;
		int index_groups = 1;
		int index_nfvipop = 0;
		
		for (int i=1;i<=num_nfv_services;i++) {			
			
			String nfvipop_name = nfvipops.get(index_nfvipop);
			
			int index_nfvinode =  index_groups*3;
			
			Node nfvinode1 = new Node();
			nfvinode1.setName("NFVINode" + (index_nfvinode-2) + " " + nfvipop_name);

			Node nfvinode2 = new Node();
			nfvinode2.setName("NFVINode" + (index_nfvinode-1) + " " + nfvipop_name);

			Node nfvinode3 = new Node();
			nfvinode3.setName("NFVINode" + (index_nfvinode) + " " + nfvipop_name);			
			
			VNFC vnfc1 = new VNFC();
			vnfc1.setName("NS"+i+" VNF1 VNFC DPI");
			vnfc1.setCpu(cpu);
			vnfc1.setMemory(mem);
			vnfc1.setStorage(sto);
			vnfc1.setNumInterfaces(1);
			vnfc1.setGuestNode(nfvinode1);
			vnfc1.setNetFunction(NamedNetFunction.DPI);

			VNFC vnfc2 = new VNFC();
			vnfc2.setName("NS"+i+" VNF2 VNFC Firewall");
			vnfc2.setCpu(cpu);
			vnfc2.setMemory(mem);
			vnfc2.setStorage(sto);
			vnfc2.setNumInterfaces(1);
			vnfc2.setGuestNode(nfvinode2);
			vnfc2.setNetFunction(NamedNetFunction.FIREWALL);

			VNFC vnfc3 = new VNFC();
			vnfc3.setName("NS"+i+" VNF2 VNFC NAT");
			vnfc3.setCpu(cpu);
			vnfc3.setMemory(mem);
			vnfc3.setStorage(sto);
			vnfc3.setNumInterfaces(1);
			vnfc3.setGuestNode(nfvinode3);
			vnfc3.setNetFunction(NamedNetFunction.NAT);			
			
			ArrayList<VNFC> vnfcs1 = new ArrayList<VNFC>();

			VNF vnf1 = new VNF();
			vnf1.setName("NS"+i+" VNF1");
			vnfcs1.add(vnfc1);
			vnf1.setVnfcs(vnfcs1);

			ArrayList<VNFC> vnfcs2 = new ArrayList<VNFC>();

			VNF vnf2 = new VNF();
			vnf2.setName("NS"+i+" VNF2");
			vnfcs2.add(vnfc2);
			vnfcs2.add(vnfc3);
			vnf2.setVnfcs(vnfcs2);

			ArrayList<VNF> vnfs = new ArrayList<VNF>();

			VNFFG vnffg1 = new VNFFG();
			vnffg1.setName("NS"+i);
			vnfs.add(vnf1);
			vnfs.add(vnf2);
			vnffg1.setVnfs(vnfs);

			this.vnffgDB.createVNFFGIndividual(vnffg1);
			
			index_nfvipop+=1;
			if (index_nfvipop == nfvipops.size()) {
				index_nfvipop = 0;
				index_groups+=1;
				if (index_groups == num_groups) {
					index_groups = 1;
				}
			}			
			
		}		

	}
	
	public TopologyStatus createNFVI(int num_hosts_per_nfvipop, NFVI nfvi, TinkerGraph graph) {
		
		status = new TopologyStatus();
		int total_nfvi_pops = 0;
		int total_inter_pop_links = 0;
		int total_nfvi_nodes = 0;
		
		CPU cpu = new CPU();
		cpu.setName("Intel I5");
		cpu.setNumCores(4);
		cpu.setAvailableCores(4);
		cpu.setSpeed(1330000);

		Memory mem = new Memory();
		mem.setName("Kingston DDR3");
		mem.setSize(16);
		mem.setAvailableSize(16);

		Storage sto = new Storage();
		sto.setName("Sansung HD");
		sto.setSize(1000);
		sto.setAvailableSize(1000);

		SwitchingMatrix swt1 = new SwitchingMatrix();
		swt1.setName("Switching Matrix");

		// Create all NFVI-Pops
		Iterator<Vertex> iterator_vertices = graph.getVertices().iterator();
		while (iterator_vertices.hasNext()) {

			Vertex nfvipop_vertex = iterator_vertices.next();
			total_nfvi_pops+=1;
			total_nfvi_nodes+=num_hosts_per_nfvipop;

			// Create one NFVI-PoP
			String nfvipop_id = (String)nfvipop_vertex.getId();
			String nfvipop_name = nfvipop_vertex.getProperty("label");
			nfvipops.add(nfvipop_name);
			NFVIPoP nfvipop = new NFVIPoP();
			nfvipop.setName("NFVIPoP " + nfvipop_name);
			nfvipop.setNfvi(nfvi);
			this.nfviPopDB.createNFVIPoPIndividual(nfvipop);

			// Create core switch
			int num_interfaces = getIterableCount(nfvipop_vertex.getEdges(Direction.OUT)) + num_hosts_per_nfvipop;
			Node switchnode = new Node();
			switchnode.setName("Switch-" + nfvipop_name);
			switchnode.setCpu(cpu);
			switchnode.setMemory(mem);
			switchnode.setStorage(sto);
			switchnode.setSwitchingMatrix(swt1);
			switchnode.setNumInterfaces(num_interfaces);
			switchnode.setNfvipop(nfvipop);
			this.netNodeDB.createNetworkNodeIndividual(switchnode);

			// Save central switch
			CoreSwitch coreswitch = new CoreSwitch(switchnode, num_hosts_per_nfvipop + 1);
			coreswitches.put(nfvipop_id,coreswitch);

			// Create all NFVI Nodes
			ArrayList<Node> nfvinodes = new ArrayList<Node>();
			for (int j = 1; j <= num_hosts_per_nfvipop; j++) {
				// Create one NFVI Node
				Node nfvinode = new Node();
				nfvinode.setName("NFVINode" + j + " " + nfvipop_name);
				nfvinode.setCpu(cpu);
				nfvinode.setMemory(mem);
				nfvinode.setStorage(sto);
				nfvinode.setNumInterfaces(1);
				nfvinode.setNfvipop(nfvipop);
				nfvinode.setMaxNumVNFs(10);
				nfvinode.setMaxNumVCPUs(10);
				nfvinode.setMaxNumVMems(10);
				nfvinode.setMaxNumVStos(10);
				this.nfviNodeDB.createNFVINodeIndividual(nfvinode);

				// Connect NFVI Node to the Central Switch
				this.phyConnDB.createPhysicalConnectionBetweenHosts(nfvinode, 1, switchnode, j, 1000000);

				// Store the created NFVI Node
				nfvinodes.add(nfvinode);
			}

		}

		// Create a physical connection between all NFVI PoPs
		Iterator<Edge> iterator_edges = graph.getEdges().iterator();
		int vxlan_id = 1000;
		while (iterator_edges.hasNext()) {

			Edge nfvipop_edge = iterator_edges.next();
			total_inter_pop_links+=1;
			
			Vertex source_vertex = nfvipop_edge.getVertex(Direction.OUT);
			Vertex target_vertex = nfvipop_edge.getVertex(Direction.IN);
			
			String id_source = (String)source_vertex.getId();
			String id_target = (String)target_vertex.getId();
			
			CoreSwitch coreswitch_source = coreswitches.get(id_source);
			CoreSwitch coreswitch_target = coreswitches.get(id_target);
			
			int port_source = coreswitch_source.getFirst_free_external_port();
			int port_target = coreswitch_target.getFirst_free_external_port();
			
			this.phyConnDB.createPhysicalConnectionBetweenPoPs(coreswitch_source.getNode(), port_source, 
					coreswitch_target.getNode(), port_target, 1000000000, vxlan_id);
			
			coreswitch_source.setFirst_free_external_port(port_source+1);
			coreswitch_target.setFirst_free_external_port(port_target+1);
			vxlan_id+=1;			
		}
		
		status.setTotalNFVIPops(total_nfvi_pops);
		status.setTotalNFVINodes(total_nfvi_nodes);
		status.setTotalInterPoPLinks(total_inter_pop_links);
		
		return status;
		
	}

	public TopologyStatus createTopology(int num_hosts_per_nfvipop, int num_nfv_services, String topo_name, String topo_path) {

		TopologyStatus status = null;
		
		NFVI nfvi = new NFVI();
		nfvi.setName("NFVI-" + topo_name);

		this.nfviDB.createNFVIIndividual(nfvi);

		TinkerGraph graph = new TinkerGraph();
		InputStream inputstream;
		try {
			inputstream = new FileInputStream(topo_path);
			GMLReader.inputGraph(graph, inputstream);
			status = createNFVI(num_hosts_per_nfvipop, nfvi, graph);
			status.setNamesNFVIPoPs(nfvipops);
			createNetworkServices(num_hosts_per_nfvipop, num_nfv_services);
			inputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
		
	}

	public class CoreSwitch {

		private Node node;
		private int first_free_external_port;

		public CoreSwitch(Node node, int first_free_external_port) {
			this.node = node;
			this.first_free_external_port = first_free_external_port;
		}

		public Node getNode() {
			return node;
		}

		public void setNode(Node node) {
			this.node = node;
		}

		public int getFirst_free_external_port() {
			return first_free_external_port;
		}

		public void setFirst_free_external_port(int first_free_external_port) {
			this.first_free_external_port = first_free_external_port;
		}

	}
	
}
