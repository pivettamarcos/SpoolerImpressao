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
	private File arquivoSpooler;
	private Consumivel consumivel;

	public SpoolerControle() {
		arquivoSpooler = new File("spooler.txt");
		js = new JanelaSpooler();
		
		Thread escutaSuspensao = new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						int contLinhas = 0;
						BufferedReader input = new BufferedReader(new FileReader(arquivoSpooler));
						String ultimaLinha = new String(), linhaAtual;
					
						while ((linhaAtual = input.readLine()) != null) {
							contLinhas++;
							ultimaLinha = linhaAtual;
						}
					    				            
						if(ultimaLinha.equals("SUSPENDER>>S")){
							suspenderImpressaoAtual(contLinhas - 1);
						}
					    
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		Thread imprime = new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						if(consumivel != null){
							if(consumivel.getCaracteresConsumiveis().size() > 0){
								Character s = consumivel.getCaracteresConsumiveis().poll();
								if(s != null){
									System.out.println("[[ EU CONSUMI " + s + " ]]");
									js.getTfConsumo().setText(js.getTfConsumo().getText() + s);
									
									// DORME 1 SEGUNDO PARA SIMULAR O CONSUMO DE CADA CARACTERE
							        Thread.sleep(1000);
								}
							}else{
								js.getTfConsumo().setText("IMPRESSÃO FINALIZADA");
								js.getTfDescricao().setText("");
								removeLinha(0);
								consumivel = null;
							}
						}else{
							lePrimeiraLinhaArquivo();
						}
				    } catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		escutaSuspensao.start();
		imprime.start();
	}
	
	public static void main(String[] args) {
		SpoolerControle sc = new SpoolerControle();
		sc.js.setVisible(true);
	}
	
	private void removeLinha(long numLinha){
		File tempFile = new File("spoolerTmp.txt");
		int contLine = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(arquivoSpooler));
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

			org.apache.commons.io.FileUtils.copyFile(tempFile, arquivoSpooler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void suspenderImpressaoAtual(int ultimaLinha){
		js.getTfConsumo().setText("IMPRESSÃO SUSPENSA");
		js.getTfDescricao().setText("");
		
		removeLinha(ultimaLinha);
		consumivel = null;
		removeLinha(0);
		lePrimeiraLinhaArquivo();
	}
	
	//Irá checar se existe algum dado a ser impresso que está no buffer
	private void lePrimeiraLinhaArquivo(){
		String proxLinha = null;
		String[] partes = null;
		do{
			BufferedReader input;
			try {
			input = new BufferedReader(new FileReader(arquivoSpooler));
	        proxLinha = new String();
	
	        proxLinha = input.readLine();
	        if(proxLinha != null){
	        	System.out.println(proxLinha);
	        	partes = proxLinha.split(">>");
	        	
	        	consumivel = new Consumivel(partes[0],partes[1]);
	        	
	        }
	        
	    	input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(proxLinha == null);
		js.getTfDescricao().setText("Imprimindo "+ partes[0]);
		js.getTfConsumo().setText("");
	}
	
}
