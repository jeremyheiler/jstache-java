package org.jstache.internal;

public class TokenItem{
	private final Token token;
	private final String value;

	public TokenItem(Token token,String value){
		this.token = token;
		this.value = value;
	}

	public Token getToken(){
		return token;
	}

	public String getValue(){
		return value;
	}
}
