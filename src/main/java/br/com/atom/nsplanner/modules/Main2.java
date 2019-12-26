package br.com.atom.nsplanner.modules;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.semanticweb.owlapi.model.OWLLiteral;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

public class Main2 {
	
	public static void listNetworkServices() throws MalformedURLException, ProtocolException, IOException, NoSuchAlgorithmException, KeyManagementException {	
		// Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
 
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
 
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		String user = "admin";
		String password = "admin";
		String project = "admin";
		String base_url = "https://127.0.0.1:9999/osm";	
		
		URL url;
		url = new URL(base_url+"/admin/v1/tokens");
		HttpURLConnection con;
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		//con.setRequestProperty("Content-Type", "application/yaml");
		//con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=windows-1251");
		
		
		String jsonInputString = "{\"username\": \""+user+"\", \"password\": \""+password+"\", \"project_id\": \""+project+"\"}";
		
		/*Map<String, String> parameters = new HashMap<>();
		parameters.put("username", user);
		parameters.put("password", password);
		parameters.put("project_id", project);*/			
		
		/*StringBuilder postData = new StringBuilder();
	    for (Map.Entry<String, String> param : parameters.entrySet()) {
	        if (postData.length() != 0) {
	            postData.append('&');
	        }
	        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	        postData.append('=');
	        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	    }

	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");*/
		
		byte[] input = jsonInputString.getBytes("utf-8");
		con.setDoOutput(true);		
		try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
			
			//out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			//out.write(postDataBytes);
			out.write(input, 0, input.length);
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			 
			StringBuilder content;
			BufferedReader in;
			
			if (status > 299) {
			    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			} else {
			    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			
	        String line;
	        content = new StringBuilder();
	        while ((line = in.readLine()) != null) {
	        	content.append(line);
	        	content.append(System.lineSeparator());
	        }
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode node = mapper.readTree(content.toString());
	        
	        System.out.println(node.get("_id"));
	        //JonObject jsonObject = new JsonParser().parse(content.toString()).getAsJsonObject();
	        
	        //System.out.println(content.toString());
		} finally {
			con.disconnect();
		}		
	}
	
	
	/*public static ArrayList<String> generateInitialState() {
		
		ArrayList<String> facts = new ArrayList<String>(); 
		
		OntologyManager ontomanager = new OntologyManager();
		ontomanager.startOntologyProcessing(OntoPlannerUtil.ONTOFILE, OntoPlannerUtil.NFV_IRI);

		ReasonerFactory reasonerFactory = new ReasonerFactory();
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;

		OWLReasoner reasoner = reasonerFactory.createReasoner(ontomanager.getOntology(), configuration);

		Set<OWLNamedIndividual> individuals = ontomanager.getAllIndividuals();
		Set<OWLObjectProperty> ops = ontomanager.getAllObjectProperties();
		Set<OWLDataProperty> dps = ontomanager.getAllDataProperties();
		
		Boolean precompute = true;
		String fact = "";
		for (OWLNamedIndividual ind : individuals) {
			String indName = ind.getIRI().getShortForm().toLowerCase();
			Set<OWLClass> types = ontomanager.getTypesPerIndividual(ind, reasoner, precompute);
			precompute = false;
			for (OWLClass type : types) {
				String typeName = type.getIRI().getShortForm().toLowerCase();
				if (!typeName.equals("thing")) {
					//System.out.println("(" + typeName + " " + indName + ")");
					fact = "(" + typeName + " " + indName + ")";
					facts.add(fact);
				}
			}
			for (OWLObjectProperty op : ops) {
				String opName = op.getIRI().getShortForm().toLowerCase();
				if (!opName.equals("topobjectproperty")) {
					Set<OWLNamedIndividual> partners = ontomanager.getOpValuesPerIndividual(ind, op, reasoner,
							precompute);
					for (OWLNamedIndividual partner : partners) {
						String partnerName = partner.getIRI().getShortForm().toLowerCase();
						//System.out.println("(" + opName + " " + indName + " " + partnerName + ")");
						fact = "(" + opName + " " + indName + " " + partnerName + ")";
						facts.add(fact);
					}
				}
			}
			for (OWLDataProperty dp : dps) {
				String dpName = dp.getIRI().getShortForm().toLowerCase();
				if (!dpName.equals("topdataproperty")) {
					Set<OWLLiteral> values = ontomanager.getDpValuesPerIndividual(ind, dp, reasoner, precompute);
					for (OWLLiteral value : values) {
						//System.out.println("(" + dpName + " " + indName + " " + value.getLiteral() + ")");
						fact = "(" + dpName + " " + indName + " " + value.getLiteral() + ")";
						facts.add(fact);
					}
				}
			}
		}

		reasoner.dispose();
		ontomanager.close();
		
		return facts;
	}*/
	
	/*public static void writeProblemDescriptor(String problemName, String domainName, ArrayList<String> tasks) {
		// creates path to .txt file
	    Path p = Paths.get(OntoPlannerUtil.LISPDIR + "teste.lisp");
	    // creates US-ASCII Charset
	    Charset c = Charset.forName("US-ASCII");
	    // Strings that will be read
	    
	    ArrayList<String> facts = generateInitialState();
	    ArrayList<String> descriptor = new ArrayList<String>();
	    descriptor.add("(in-package :shop-user)");
	    descriptor.add("");
	    
	    descriptor.add("(defproblem "+ problemName + " " + domainName);
	    descriptor.add("\t; Initial State");
	    descriptor.add("\t(");
	    for (String fact : facts) {
	    	descriptor.add("\t" + fact);
	    }
	    descriptor.add("\t)");
	    descriptor.add("\t; Task List");
	    descriptor.add("\t(");
	    for (String task : tasks) {
	    	descriptor.add("\t" + task);
	    }
	    descriptor.add("\t))");
	    
	    descriptor.add("");
	    descriptor.add("(find-plans '"+ problemName +" :verbose :plans)");	    
	    
	    try {
	      // writes lines from file
	      Path write = Files.write(p, descriptor, c);
	    } catch (IOException e) {
	      System.err.println(e);
	    }
	    
	}*/
	
	public static void executeCmd() {
		//String command = "cmd /c sbcl --eval (print (+ 3 2)) --eval (quit)";
		//String command = "cmd /c sbcl --eval (ql:quickload 'shop3') --eval (quit)";
		//String command = "netsh";
		//String command = "cmd /c dir";
		//String[] cmdArray = {"cmd","/c","dir"};
		String[] cmdArray = {"cmd","/c","sbcl","--load",OntoPlannerUtil.LISPDIR+"/planning-domain.lisp","--load",OntoPlannerUtil.LISPDIR+"/planning-problem.lisp","--quit"};
		String s = null;
		 
		System.out.println(System.getProperty("user.dir"));
		
		try {            
		    //System.out.println(command);
			Process p = Runtime.getRuntime().exec(cmdArray);
		    //p.waitFor();
		    
		    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		    
		    // read the output from the command
		    System.out.println("Here is the standard output of the command:\n");
		    String plans = "";
		    
		    while ((s = stdInput.readLine()) != null) {
		    	//System.out.println(s);
		    	if (s.contains("Plans:")) {
		    		plans = stdInput.readLine();
		    		break;
		    	}
		    }
		    
		    //System.out.println(plans.replace(") (!",","));
		    
		    plans = plans.substring(1, plans.length()-1);
		    plans = plans.replace(")) ((",":");
		    plans = plans.replace(") (",",");
		    plans = plans.replace("((","");
		    plans = plans.replace("))","");
		    
		    String[] listPlans = plans.split(":");
		    for (String plan : listPlans) {
		    	System.out.println(plan);
		    }
		    
		    
		    // read any errors from the attempted command
		    System.out.println("Here is the standard error of the command (if any):\n");
		    while ((s = stdError.readLine()) != null) {
		    	System.out.println(s);
		    }
		    
		    System.exit(0);
	        
		} catch (IOException e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
			System.exit(-1);
		} /*catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		
	}
	
	public static void getAllClasses() {
		OntologyManager ontomanager = new OntologyManager();
		ontomanager.startOntologyProcessing(OntoPlannerUtil.ONTOFILE, OntoPlannerUtil.NFV_IRI);
		
		//ontomanager.listAllClassesFormatted();
		//ontomanager.listAllObjectPropertiesFormatted();
		ontomanager.listAllDatatypePropertiesFormatted();
		//ontomanager.listAllIndividualsFormatted();
		
		//ReasonerFactory reasonerFactory = new ReasonerFactory();
		//Configuration configuration = new Configuration();
		//configuration.throwInconsistentOntologyException = false;

		//OWLReasoner reasoner = reasonerFactory.createReasoner(ontomanager.getOntology(), configuration);

		//Set<OWLNamedIndividual> individuals = ontomanager.getAllIndividuals();
		//Set<OWLClass> classes = ontomanager.get
		//Set<OWLObjectProperty> ops = ontomanager.getAllObjectProperties();
		//Set<OWLDataProperty> dps = ontomanager.getAllDataProperties();
		
	}
	
	public static void generateInitialState() {
		OntologyManager ontomanager = new OntologyManager();
		ontomanager.startOntologyProcessing(OntoPlannerUtil.ONTOFILE, OntoPlannerUtil.NFV_IRI);
		/*Set<OWLNamedIndividual> individuals = ontomanager.getClassIndividualsWithoutReasoner(NamedClasses.MANAGEDENTITY);
		for (OWLNamedIndividual ind : individuals) {
			System.out.println(ind.getIRI().getShortForm());
		}*/
		/*Set<OWLNamedIndividual> individuals = ontomanager.getOpValuesPerIndividualWithoutReasoner("migrate", NamedObjectProp.HASTHRESHOLD);
		for (OWLNamedIndividual ind : individuals) {
			System.out.println(ind.getIRI().getShortForm());
		}*/
		Set<OWLLiteral> values = ontomanager.getDpValuesPerIndividualWithoutReasoner("policy-base-migrate-cpu", NamedDataProp.HASLOWTHRESHOLDVALUE);
		for (OWLLiteral value : values) {
			System.out.println(value.getLiteral());
		}
	}
	
	public static void generateMetrics() {
		for (int i = 1; i<=1000;i++) {
			System.out.println("public static final @Nonnull String METRIC" + i + " = \"" + "metric" + i + "\";");
		}
	}
	
	public static void main(String[] args) {

		//generateInitialState();
		//executeCmd();
		/*try {
			try {
				listNetworkServices();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*HTTPClient client = new HTTPClient("127.0.0.1", "9999", "osm");
		
		JsonNode node = client.sendGetRequest("/nslcm/v1/ns_instances_content");
		
		System.out.println(node);*/
		
		/*ArrayList<String> tasks = new ArrayList<String>();
		tasks.add("(migrate vm1 vm2)");
		
		writeProblemDescriptor("resiliency", "nfvmano-domain", tasks);*/
		
		//getAllClasses();
		//generateInitialState();
		generateMetrics();
		
		//String os = System.getProperty("os.name");
	    //System.out.println("Using System Property: " + os);
		
	}
	


}

