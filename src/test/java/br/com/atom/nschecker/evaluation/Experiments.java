package br.com.atom.nschecker.evaluation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nschecker.classes.TopologyStatus;
import br.com.atom.nschecker.evaluation.util.OntoNFVUtilTest;

public class Experiments {

	public OntologyManager ontomanager;
	public Map<String, String> topologies;
	public TopologyStatus status;

	public Experiments() {
		topologies = new HashMap<String, String>();
		topologies.put("Abilene", OntoNFVUtilTest.GMLTOPODIR + "Abilene.gml");
		//topologies.put("Bandcon", OntoNFVUtil.GMLTOPODIR + "Bandcon.gml");
		//topologies.put("BICS", OntoNFVUtil.GMLTOPODIR + "Bics.gml");
		//topologies.put("GEANT", OntoNFVUtil.GMLTOPODIR + "Geant2012.gml");
		//topologies.put("GlobeNet", OntoNFVUtil.GMLTOPODIR + "Globenet.gml");
		//topologies.put("Highwinds", OntoNFVUtil.GMLTOPODIR + "Highwinds.gml");
		//topologies.put("IBM", OntoNFVUtil.GMLTOPODIR + "Ibm.gml");
		//topologies.put("NTT", OntoNFVUtil.GMLTOPODIR + "Ntt.gml");
		topologies.put("RNP", OntoNFVUtilTest.GMLTOPODIR + "Rnp.gml");
		topologies.put("SURFNET", OntoNFVUtilTest.GMLTOPODIR + "Surfnet.gml");
	}
	
	public void generateTopology(int num_hosts_per_nfvipop, int num_nfv_services, String topo_name) {
		String topo_path = topologies.get(topo_name);
		String fileName = "onto-nfv-with-topo-" + topo_name.toLowerCase() + "-" + num_hosts_per_nfvipop + "-"
				+ num_nfv_services + ".owl";

		// manager = new OntoNFVManager();
		TopologyZooExamples topology = new TopologyZooExamples(this.ontomanager);
		status = topology.createTopology(num_hosts_per_nfvipop, num_nfv_services, topo_name, topo_path);
		topology.close();
		topology = null;
		this.ontomanager.saveNewOntologyFile(OntoNFVUtilTest.ONTOTEMPDIR + fileName);

		status.setFile(new File(OntoNFVUtilTest.ONTOTEMPDIR + fileName));
	}

	public void runExperiment(int num_hosts_per_nfvipop, int num_nfv_services, ArrayList<String> levels_num_vnfcs,
			int rounds, PrintWriter writer) throws IOException, InterruptedException {

		NSRequestExamples nsrequest = null;
		//ArrayList<Float> timesOntologyLoad = null;
		ArrayList<Float> timesConsistencyTest = null;

		System.out.println("#### BEGIN - Run Experiment for: " + num_hosts_per_nfvipop + " NFVI-Nodes per NFVI-PoP and "
				+ num_nfv_services + " deployed NFV Services" + " ####");

		for (String key : topologies.keySet()) {
			String topo_name = key;
			status = null;

			System.out.println("# Step 1 - Generating Topology..."); // LOG
			
			//manager = new OntoNFVManager(OntoNFVUtilTest.ONTOFILE);
			this.ontomanager = new OntologyManager();
			this.ontomanager.startOntologyProcessing(OntoNFVUtilTest.ONTOFILE, OntoNFVUtilTest.NFV_IRI);
			generateTopology(num_hosts_per_nfvipop, num_nfv_services, topo_name);
			System.out.println("# Step 1 - Topology Generated..."); // LOG
			this.ontomanager.close();
			this.ontomanager = null;

			writer.println("------------ BEGIN TOPOLOGY: " + topo_name + " ------------");

			writer.println("File Size (bytes): " + status.getFile().length());
			writer.println("Total NFVI-PoPs: " + status.getTotalNFVIPops());
			writer.println("Total NFVI-Nodes: " + status.getTotalNFVINodes());
			writer.println("Total Inter-PoP Links: " + status.getTotalInterPoPLinks());
			writer.println("Total NFV Services (3 VNFs) already deployed: " + num_nfv_services);

			System.out.println("# Step 2 - Test Consistency Experiments..."); // LOG
			for (String level_num_vnfcs : levels_num_vnfcs) {

				System.out.println("- Number of VNFs in NS Request: " + level_num_vnfcs); // LOG

				int num_vnfcs = new Integer(level_num_vnfcs);
				writer.println("--------");
				writer.println("Total VNFs in NS Request: " + num_vnfcs);
				writer.println("Total NS Policy Restrictions in NS Request: " + (num_vnfcs + (num_vnfcs - 1)));

				//manager = new OntoNFVManager(status.getFile());
				this.ontomanager = new OntologyManager();
				this.ontomanager.startOntologyProcessing(status.getFile(), OntoNFVUtilTest.NFV_IRI);
				
				//timesOntologyLoad = new ArrayList<Float>();
				timesConsistencyTest = new ArrayList<Float>();
				for (int i = 0; i < rounds; i++) {
					System.out.println("> Round: " + i);

					/*
					 * manager.getCdmManager().getReasoner().dispose();
					 * manager.getOntoManager().getManager().clearOntologies();
					 * manager.setNfviManager(null);
					 * manager.setSfcManager(null);
					 * manager.setRestManager(null);
					 * manager.setCdmManager(null);
					 * manager.setOntoManager(null); manager = null;
					 */
					// System.gc();					

					// Calculate the time for Ontology Loading
					//long startTime = System.nanoTime();
					//manager = new OntoNFVManager(status.getFile());
					//long endTime = System.nanoTime();
					//long totalTime = endTime - startTime;
					//timesOntologyLoad.add((float) totalTime / 1000000);

					
					
					// Processing received NS Request
					nsrequest = new NSRequestExamples(this.ontomanager,status.getNamesNFVIPoPs());
					nsrequest.createNetworkServiceRequest(num_hosts_per_nfvipop, num_vnfcs);
					nsrequest.close();
					nsrequest = null;

					// Calculate the time for Consistency Test
					long startTime = System.nanoTime();
					ConflictDetectionModule cdm = new ConflictDetectionModule(this.ontomanager);
					cdm.start();
					cdm.testOntoConsistency();										
					long endTime = System.nanoTime();
					long totalTime = endTime - startTime;
					timesConsistencyTest.add((float) totalTime / 1000000);
					cdm.close();
					cdm = null;
				}
				
				this.ontomanager.close();
				this.ontomanager = null;
				
				Thread.sleep(10000);
				
				//writer.println("Time to load ontology (milliseconds): " + timesOntologyLoad.toString());
				writer.println("Time to check consistency (milliseconds): " + timesConsistencyTest.toString());
				writer.println("--------");
				
			}
			System.out.println("# Step 2 - Finished Test Consistency Experiments..."); // LOG
			System.out.println("#### END - Run Experiment for: " + num_hosts_per_nfvipop
					+ " NFVI-Nodes per NFVI-PoP and " + num_nfv_services + " deployed NFV Services" + " ####"); // LOG

			writer.println("------------ END TOPOLOGY: " + topo_name + " ------------");

		}

	}

	public void start() throws IOException, NumberFormatException, InterruptedException {

		Properties prop = new Properties();
		InputStream input = null;
		PrintWriter writer = null;

		if (topologies.size() > 0) {
			try {
				input = new FileInputStream(OntoNFVUtilTest.CONFIGFILE);
				// load a properties file
				prop.load(input);

				ArrayList<String> levels_num_hosts_per_nfvipop = new ArrayList<String>(
						Arrays.asList(prop.getProperty("num_hosts_per_nfvipop").split(",")));
				ArrayList<String> levels_num_nfv_services = new ArrayList<String>(
						Arrays.asList(prop.getProperty("num_nfv_services").split(",")));
				ArrayList<String> levels_num_vnfcs = new ArrayList<String>(
						Arrays.asList(prop.getProperty("num_vnfcs").split(",")));
				String rounds = prop.getProperty("rounds");

				input.close();

				for (String level_num_hosts_per_nfvipop : levels_num_hosts_per_nfvipop) {
					for (String level_num_nfv_services : levels_num_nfv_services) {
						writer = new PrintWriter(
								OntoNFVUtilTest.RESULTDIR + "result-" + new Integer(level_num_hosts_per_nfvipop) + "-"
										+ new Integer(level_num_nfv_services) + ".txt",
								"UTF-8");
						runExperiment(new Integer(level_num_hosts_per_nfvipop), new Integer(level_num_nfv_services),
								levels_num_vnfcs, new Integer(rounds), writer);
						writer.close();
					}
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Please insert topologies");
		}
	}

}
