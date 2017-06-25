package spooler.modelo;

import java.util.LinkedList;
import java.util.Queue;

public class Consumivel {
	private Queue<Character> caracteresConsumiveis;
	private String caracteresOriginais;
	
	public Consumivel(String characteresOriginais) {
		this.caracteresConsumiveis = new LinkedList<Character>();
		this.caracteresOriginais = characteresOriginais;
		
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
