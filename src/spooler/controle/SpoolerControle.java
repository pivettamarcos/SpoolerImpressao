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

import spooler.modelo.Consumivel;

public class SpoolerControle {
	private File arquivoUsuarios;
	private Path pathArquivo;
	private Queue<Consumivel> consumiveis = new LinkedList<Consumivel>();
	private long numLinhasAtual = 0;

	public SpoolerControle() {
		arquivoUsuarios = new File("c:/Users/marco/workspace/SpoolerImpressora/texto/consulta.txt");
		pathArquivo = arquivoUsuarios.toPath();
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
		File tempFile = new File("c:/Users/marco/workspace/SpoolerImpressora/texto/consultaTmp.txt");
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
		sc.leProxPosicao();
		/*Thread t1 = new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						Stream<String> lines = Files.lines(sc.pathArquivo);
				        long contLinhas = lines.count(); 
				        if(contLinhas > sc.numLinhasAtual){
				        	int cont = 0;
				        	BufferedReader input = new BufferedReader(new FileReader(sc.arquivoUsuarios));
				            String ultimaLinha = new String(), linhaAtual;

				            while ((linhaAtual = input.readLine()) != null) {
				            	cont++;
				            	ultimaLinha = linhaAtual;
				            }
				            				            
				           if(ultimaLinha != null){
				        	   sc.consumiveis.add(new Consumivel(ultimaLinha));
				        	   System.out.println(sc.consumiveis.peek());
				           }
				            
				        	sc.numLinhasAtual = contLinhas;
				        	input.close();
				        	lines.close();
				        }
				        Thread.sleep(1);
				    } catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});*/
		
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						if(sc.consumiveis.size() > 0){
							if(sc.consumiveis.peek().getCaracteresConsumiveis().size() > 0){
								Character s = sc.consumiveis.peek().getCaracteresConsumiveis().poll();
								if(s != null)
									System.out.println("[[ EU CONSUMI " + s + " ]]");
							}else{
								System.out.println("acabou impressao");
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
		
		//t1.start();
		t2.start();

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
	        			
	        if(proxLinha != null)
	        	consumiveis.add(new Consumivel(proxLinha));
	        
	    	input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(proxLinha == null);
	}
	
}
