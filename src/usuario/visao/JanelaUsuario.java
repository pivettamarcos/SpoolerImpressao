package usuario.visao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import java.awt.Font;

public class JanelaUsuario extends JFrame {
	private JPanel contentPaneImpressao;
	private JPanel contentPaneGerenciamento;
	public static final int PANE_IMPRESSAO = 0;
	public static final int PANE_GERENCIAMENTO = 1;
	
	private JMenuItem miImpressaoDados;
	private JMenuItem miGerenciamentoImpressora;
	
	private JButton btnEnviarParaImpressao;
	private JTextField jtfEntradaDados;
	private JTable tabelaProcessos;
	
	private DefaultTableModel modeloTabela;
	private JTextField tfNumeroID;
	private JButton btnRemoverProcesso;
	private JButton btnSuspenderImpressaoAtual;
	private JTextField jtfNomeUsuario;
	
	public JanelaUsuario() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 398);
		setResizable(false);
		setTitle("M\u00F3dulo Usu\u00E1rio");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		miImpressaoDados = new JMenuItem("Impress\u00E3o");
		mnMenu.add(miImpressaoDados);
		
		miGerenciamentoImpressora = new JMenuItem("Gerenciamento da impressora");
		mnMenu.add(miGerenciamentoImpressora);
		getContentPane().setLayout(null);

		this.contentPaneImpressao = new JPanel();
		this.contentPaneGerenciamento = new JPanel();

		inicializarPanes();
	}
	
	private void inicializarPanes(){
		//INICIALIZA CPANE IMPRESSAO
		contentPaneImpressao.setLayout(null);
		btnEnviarParaImpressao = new JButton("Enviar para Impress\u00E3o");
		btnEnviarParaImpressao.setBounds(346, 314, 200, 23);
		contentPaneImpressao.add(btnEnviarParaImpressao);
		
		jtfEntradaDados = new JTextField();
		jtfEntradaDados.setBounds(134, 158, 297, 20);
		contentPaneImpressao.add(jtfEntradaDados);
		jtfEntradaDados.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Dado a ser impresso");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 133, 556, 14);
		contentPaneImpressao.add(lblNewLabel);
		
		jtfNomeUsuario = new JTextField();
		jtfNomeUsuario.setBounds(122, 11, 86, 20);
		contentPaneImpressao.add(jtfNomeUsuario);
		jtfNomeUsuario.setColumns(10);
		
		JLabel lblNomeDoUsurio = new JLabel("Nome do usu\u00E1rio");
		lblNomeDoUsurio.setBounds(20, 14, 92, 14);
		contentPaneImpressao.add(lblNomeDoUsurio);
		
		//INICIALIZA CPANE DE GERENCIAMENTO
		contentPaneGerenciamento.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(tabelaProcessos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(55, 36, 444, 253);
		contentPaneGerenciamento.add(scrollPane);
		
		tabelaProcessos = new JTable();
		tabelaProcessos.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"ID","Dados", "Status", "Usuário"
			}
		));
		modeloTabela = (DefaultTableModel) tabelaProcessos.getModel();
		scrollPane.setViewportView(tabelaProcessos);
		
		btnRemoverProcesso = new JButton("Remover Processo");
		btnRemoverProcesso.setBounds(343, 314, 141, 23);
		contentPaneGerenciamento.add(btnRemoverProcesso);
		
		tfNumeroID = new JTextField();
		tfNumeroID.setBounds(494, 315, 52, 20);
		contentPaneGerenciamento.add(tfNumeroID);
		tfNumeroID.setColumns(10);
		
		btnSuspenderImpressaoAtual = new JButton("Suspender Impress\u00E3o Atual");
		btnSuspenderImpressaoAtual.setBounds(10, 314, 187, 23);
		contentPaneGerenciamento.add(btnSuspenderImpressaoAtual);
		
		JLabel lblGerenciadorDeImpresses = new JLabel("Gerenciador de Impress\u00F5es");
		lblGerenciadorDeImpresses.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblGerenciadorDeImpresses.setHorizontalAlignment(SwingConstants.CENTER);
		lblGerenciadorDeImpresses.setBounds(55, 11, 444, 14);
		contentPaneGerenciamento.add(lblGerenciadorDeImpresses);
	}
	
	public void setarPane(int num){
		System.out.println(num);
		switch(num){
		case 0:
			this.setContentPane(contentPaneImpressao);
			break;
		case 1:
			this.setContentPane(contentPaneGerenciamento);
		}
	}

	public JMenuItem getMiImpressaoDados() {
		return miImpressaoDados;
	}

	public JMenuItem getMiGerenciamentoImpressora() {
		return miGerenciamentoImpressora;
	}

	public JButton getBtnEnviarParaImpressao() {
		return btnEnviarParaImpressao;
	}

	public JTextField getJtfEntradaDados() {
		return jtfEntradaDados;
	}

	public DefaultTableModel getModeloTabela() {
		return modeloTabela;
	}

	public JTextField getTfNumeroID() {
		return tfNumeroID;
	}

	public JButton getBtnRemoverProcesso() {
		return btnRemoverProcesso;
	}

	public JButton getBtnSuspenderImpressaoAtual() {
		return btnSuspenderImpressaoAtual;
	}

	public JTextField getJtfNomeUsuario() {
		return jtfNomeUsuario;
	}
}
