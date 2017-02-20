package main;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class multissh {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.out.println("				Multi-SSH				");
System.out.println("				         v0.1b          ");
		try {
		String usuario = args[0];
		String senha = args[1];
		String porta = args[2];
		String lista = args[3];
		String comando = args[4];
		
		System.out.println("Iniciando....");
	      try{
	          BufferedReader br = new BufferedReader(new FileReader(lista));
	          while(br.ready()){
	             String ips[] = br.readLine().split("\n");
	             
	             for(int i = 0; i < ips.length; i++) {
	                    try {
	                        ssh(usuario, senha, ips[i], porta, comando);
	                    } catch (Exception e) {
	                        System.out.println("IP: " + ips[i] + " Erro: " + e.getMessage());

	                    }
	             
	             }
	             
	             
	             
	             
	          }
	          br.close();
	          System.out.println("\nFim!\nCriador: BadGuy\n");
	          System.exit(0);
	       }catch(IOException ioe){
	         System.out.println("Erro ao abrir o arquivo " + lista);

	       }
	      
	      
	      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Erro: " + e.getMessage());
			return;
		}
		
	}

	public static String ssh(String username, String password, String hostname, String port, String command)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, Integer.parseInt(port));
        session.setPassword(password);
        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        //prop.put("Compression", "yes");
        session.setConfig(prop);

session.connect(2000);
System.out.println("IP: " + hostname + " LOGIN: " + username + " SENHA: " + password + " OK");

// Canal SSH para Executar o Comando definido pelo usuario:

ChannelExec channelssh = (ChannelExec)
        session.openChannel("exec");
ByteArrayOutputStream baos = new ByteArrayOutputStream();
BufferedReader in = new BufferedReader(new InputStreamReader(channelssh.getInputStream()));
channelssh.setCommand(command);
System.out.println("Abrindo Canal SSH...");
channelssh.connect();
System.out.println("Executando comando..");
final StringBuilder output = new StringBuilder();

String s = null;
while((s = in.readLine()) != null){
	output.append(s + System.getProperty("line.separator"));
	System.out.println(output);
}
session.disconnect();
return baos.toString();
	}
}
