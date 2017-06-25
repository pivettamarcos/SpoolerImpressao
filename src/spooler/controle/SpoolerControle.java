package spooler.controle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

import javax.swing.JLabel;

import spooler.modelo.Consumivel;
import spooler.visao.JanelaSpooler;

public class SpoolerControle {
	private JanelaSpooler js;
	private File arquivoUsuarios;
	private Path pathArquivo;
	private Queue<Consumivel> consumiveis = new LinkedList<Consumivel>();
	private long numLinhasAtual = 0;

	public SpoolerControle() {
		arquivoUsuarios = new File("c:/Users/marco/workspace/SpoolerImpressora/texto/spooler.txt");
		pathArquivo = arquivoUsuarios.toPath();
		js = new JanelaSpooler();
		
		inicializaNumLinhas();
	}
	
	private void inicializaNumLinhas(){
		try{
			Stream<String> lines = Files.lines(arquivoUsuarios.toPath());
			numLinhasAtual = lines.count(); 
			lines.close();
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void removeLinha(long numLinha){
		File tempFile = new File("c:/Users/marco/workspace/SpoolerImpressora/texto/spoolerTmp.txt");
		int contLine = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(arquivoUsuarios));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, false));
	
			String currentLine;
	
			while((currentLine = reader.readLine()) != null) {
			    if(contLine != numLinha){
				    String trimmedLine = currentLine.trim();
				    System.out.println(trimmedLine);
				    writer.write(currentLine + System.getProperty("line.separator"));
			    }
			    contLine++;
			}
			writer.close(); 
			reader.close(); 

			org.apache.commons.io.FileUtils.copyFile(tempFile, arquivoUsuarios);
			//org.apache.commons.io.FileUtils.deleteQuietly(sourceFile);


			//System.out.println(successful);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		SpoolerControle sc = new SpoolerControle();
		sc.js.setVisible(true);
		sc.leProxPosicao();
		
		Thread escutaSuspensao = new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						int cont = 0;
						BufferedReader input = new BufferedReader(new FileReader(sc.arquivoUsuarios));
						String ultimaLinha = new String(), linhaAtual;
					
						while ((linhaAtual = input.readLine()) != null) {
							cont++;
							ultimaLinha = linhaAtual;
						}
					    				            
						if(ultimaLinha != null && ultimaLinha.equals("SUSPENDER>>S")){
							sc.suspenderImpressaoAtual(cont - 1);
						}
					    
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						if(sc.consumiveis.size() > 0){
							if(sc.consumiveis.peek().getCaracteresConsumiveis().size() > 0){
								Character s = sc.consumiveis.peek().getCaracteresConsumiveis().poll();
								if(s != null){
									System.out.println("[[ EU CONSUMI " + s + " ]]");
									sc.js.getTfConsumo().setText(sc.js.getTfConsumo().getText() + s);
								}
									
							}else{
								sc.js.getTfConsumo().setText("IMPRESSÃO FINALIZADA");
								sc.js.getTfDescricao().setText("");
								sc.removeLinha(0);
								sc.numLinhasAtual--;
								sc.consumiveis.poll();
								sc.leProxPosicao();
							}
						}
				        Thread.sleep(1000);
				    } catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		escutaSuspensao.start();
		t2.start();
	}
	
	private void suspenderImpressaoAtual(int ultimaLinha){
		js.getTfConsumo().setText("IMPRESSÃO SUSPENSA");
		js.getTfDescricao().setText("");
		
		removeLinha(ultimaLinha);
		consumiveis.poll();
		removeLinha(0);
		leProxPosicao();
	}
	
	//Será rodado enquanto a impressora estiver em funcionamento
	private void leProxPosicao(){
		String proxLinha = null;
		do{
			BufferedReader input;
			try {
			input = new BufferedReader(new FileReader(arquivoUsuarios));
	        proxLinha = new String();
	
	        proxLinha = input.readLine();
	        			
	        if(proxLinha != null){
	        	System.out.println(proxLinha);
	        	String[] partes = proxLinha.split(">>");
	        	
	        	consumiveis.add(new Consumivel(partes[0],partes[1]));
	        	
	        }
	        
	    	input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(proxLinha == null);
		js.getTfDescricao().setText("Imprimindo "+ proxLinha);
		js.getTfConsumo().setText("");
	}
	
}
