package spooler.modelo;

import java.util.LinkedList;
import java.util.Queue;

public class Consumivel {
	private Queue<Character> caracteresConsumiveis;
	private String caracteresOriginais;
	private String nomeUsuario;
	
	public Consumivel(String characteresOriginais, String nomeUsuario) {
		this.caracteresConsumiveis = new LinkedList<Character>();
		this.caracteresOriginais = characteresOriginais;
		this.nomeUsuario = nomeUsuario;
		
		inicializaCaracteresConsumiveis();
	}
	
	private void inicializaCaracteresConsumiveis(){
		for(int x = 0; x < caracteresOriginais.length(); x++){
			caracteresConsumiveis.add(caracteresOriginais.charAt(x));
		}
	}

	public Queue<Character> getCaracteresConsumiveis() {
		return caracteresConsumiveis;
	}

	public String getCaracteresOriginais() {
		return caracteresOriginais;
	}
	
	
}
