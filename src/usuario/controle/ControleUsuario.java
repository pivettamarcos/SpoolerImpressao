package usuario.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import usuario.visao.JanelaUsuario;

public class ControleUsuario {
	private JanelaUsuario ju;
	private File arquivoUsuarios;
	private PrintWriter arquivoUsuariosPW;
	private Thread atualizacaoTabela;
	
	public ControleUsuario(){
		ju = new JanelaUsuario();
		arquivoUsuarios = new File("c:/Users/marco/workspace/SpoolerImpressora/texto/consulta.txt");

		
		atualizacaoTabela = new Thread(new Runnable() {
			public void run() {
				while(true){
					atualizaTabela();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		atualizacaoTabela.start();
		inicializaArquivoUsuariosPW();
		inicializaListeners();
	}
	
	private void inicializaListeners(){
		ju.getMiImpressaoDados().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ju.setarPane(JanelaUsuario.PANE_IMPRESSAO);
				ju.revalidate();
			}
		});
		
		ju.getMiGerenciamentoImpressora().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ju.setarPane(JanelaUsuario.PANE_GERENCIAMENTO);
				atualizaTabela();
				ju.revalidate();
			}
		});
		
		ju.getBtnEnviarParaImpressao().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arquivoUsuariosPW.println(ju.getJtfEntradaDados().getText());
				arquivoUsuariosPW.flush();
			}
		});
		
		ju.getJtfEntradaDados().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		ju.getBtnRemoverProcesso().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeLinha(Integer.parseInt(ju.getTfNumeroID().getText()));
			}
		});
		
		
		ju.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
            	arquivoUsuariosPW.close();
            }
        });

	}
	
	private void inicializaArquivoUsuariosPW(){
		try {
			arquivoUsuariosPW = new PrintWriter(new FileWriter("c:/Users/marco/workspace/SpoolerImpressora/texto/consulta.txt", true));
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

	
	private void atualizaTabela(){
		BufferedReader input;
		try {
			int cont = 0;
			input = new BufferedReader(new FileReader(arquivoUsuarios));
			String ultimaLinha = new String(), linhaAtual;
			ju.getModeloTabela().setRowCount(0);
			
			while ((linhaAtual = input.readLine()) != null) {
				ultimaLinha = linhaAtual;
				if(cont == 0)
					ju.getModeloTabela().addRow(new Object[] {ultimaLinha, "IMPRIMINDO"});
				else
					ju.getModeloTabela().addRow(new Object[] {ultimaLinha, "EM ESPERA"});

				cont++;
			}
			
			input.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ControleUsuario cu = new ControleUsuario();
		
		cu.ju.setVisible(true);
	}
}
