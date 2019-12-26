package br.com.atom.nsplanner.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HTTPClient {

	protected String base_url;
	protected String token;
	protected static final int CONNECT_TIMEOUT = 10000;
	protected static final int READ_TIMEOUT = 10000;
	
	public HTTPClient(String host, String port, String base_path) {
		try {
			configInsecureConnection();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		this.base_url = String.format("https://%s:%s/%s", host, port, base_path);
		try {
			this.token = this.getToken();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void configInsecureConnection( ) throws NoSuchAlgorithmException, KeyManagementException {
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
	}
	
	public JsonNode sendGetRequest(String servicePath) {
		
		//String token;
		try {
			//token = getToken();
			//System.out.println(token);
			//if (token == null) {
				//return null;
			//}
			
			/*try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			//String bearerAuth = "Bearer " + new String(Base64.getEncoder().encode(token.getBytes()));
			//String bearerAuth = "Bearer " + Base64.getEncoder().withoutPadding().encodeToString(token.getBytes("utf-8"));
			//token = new String("JXnQxPn6ajdBuZwV1J7MerVPLtTSzphB");
			System.out.println(this.token);			
			
			String bearerAuth = "Bearer " + this.token;
			
			URL url;
			url = new URL(this.base_url+servicePath);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection();			
			con.setRequestMethod("GET");
			con.setConnectTimeout(CONNECT_TIMEOUT);
			con.setReadTimeout(READ_TIMEOUT);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization", bearerAuth);
			
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
	        
	        con.disconnect();
	        
	        return node;	         
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
		return null;
		
	}
	
	public JsonNode sendDeleteRequest(String servicePath) {
		
		//String token;
		try {			
			String bearerAuth = "Bearer " + this.token;
			
			URL url;
			url = new URL(this.base_url+servicePath);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection();			
			con.setRequestMethod("DELETE");
			con.setConnectTimeout(CONNECT_TIMEOUT);
			con.setReadTimeout(READ_TIMEOUT);
			con.setRequestProperty("Authorization", bearerAuth);
			
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
	        
	        con.disconnect();
	        
	        return node;	         
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
		return null;
		
	}
	
	public JsonNode sendPutRequest(String servicePath, File file) {		
		try {
			
			URL url;
			url = new URL(this.base_url+servicePath);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setConnectTimeout(CONNECT_TIMEOUT);
			con.setReadTimeout(READ_TIMEOUT);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "text/plain; utf-8");
			con.setRequestProperty("Authorization", String.format("Bearer %s", this.token));
			
			con.setDoOutput(true);
			con.setDoInput(true);
			
			try (BufferedOutputStream bos = new BufferedOutputStream(con.getOutputStream())) {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				int i;
				// read byte by byte until end of stream
				while ((i = bis.read()) > 0) {
					bos.write(i);
				}
				bis.close();
				bos.close();
				
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
		        
		        con.disconnect();
		        
		        return node;	
				
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
		}	
		return null;		
	}
	
	public JsonNode sendPostRequest(String servicePath, String jsonInputString) {
		
		String token;
		try {
			token = getToken();
			
			if (token == null) {
				return null;
			}
			
			URL url;
			url = new URL(this.base_url+servicePath);
			HttpURLConnection con;
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(CONNECT_TIMEOUT);
			con.setReadTimeout(READ_TIMEOUT);
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Authorization", String.format("Bearer %s", token));
			
			byte[] input = jsonInputString.getBytes("utf-8");
			con.setDoOutput(true);		
			try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
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
		        
		        con.disconnect();
		        
		       return node;		        
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
		}		
	
		return null;
		
	}
	
	protected String getToken() throws MalformedURLException, ProtocolException, IOException {
		String user = "admin";
		String password = "admin";
		String project = "admin";
		
		URL url;
		url = new URL(this.base_url+"/admin/v1/tokens");
		HttpURLConnection con;
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json; utf-8");		
		
		String jsonInputString = "{\"username\": \""+user+"\", \"password\": \""+password+"\", \"project_id\": \""+project+"\"}";
		
		byte[] input = jsonInputString.getBytes("utf-8");
		con.setDoOutput(true);		
		try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
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
	        
	        con.disconnect();
	        
	        if (node.has("_id")) {
	        	return node.get("_id").toString();
	        } else {
	        	return null;
	        }      
		}		
	}
	
}
