package spooler.visao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTextField;

public class JanelaSpooler extends JFrame {

	private JPanel contentPane;
	private JTextField tfDescricao;
	private JTextField tfConsumo;

	public JanelaSpooler() {
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfDescricao = new JTextField();
		tfDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		tfDescricao.setEditable(false);
		tfDescricao.setBounds(105, 79, 232, 20);
		contentPane.add(tfDescricao);
		tfDescricao.setColumns(10);
		
		tfConsumo = new JTextField();
		tfConsumo.setHorizontalAlignment(SwingConstants.CENTER);
		tfConsumo.setEditable(false);
		tfConsumo.setColumns(10);
		tfConsumo.setBounds(105, 139, 232, 20);
		contentPane.add(tfConsumo);
	}

	public JTextField getTfDescricao() {
		return tfDescricao;
	}

	public JTextField getTfConsumo() {
		return tfConsumo;
	}

	
}
