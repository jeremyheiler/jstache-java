package org.jstache;

public class Modes{

	private Modes(){}

	public static final Mode NORMAL = new Mode(){

		@Override
		public String escape(String input){
			return input;
		}
	};

	public static final Mode HTML = new Mode(){

		@Override
		public String escape(String input){
			return input;
		}
	};
}
