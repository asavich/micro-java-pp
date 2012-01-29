package org.microjava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java_cup.runtime.Symbol;

import org.microjava.syntax.*;

public class MicroJavaBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FileReader inputFile = null;
		try {
			inputFile = new FileReader(new File("jflex_cup/program.mj"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Lexer l = new Lexer(inputFile);
//		try {
//			Symbol s = l.next_token();
//			System.out.print(s.sym + " ");
//			while(s.sym != sym.EOF)
//			{
//				s = l.next_token();
//				System.out.print(s.sym + " ");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		parser p = new parser(l);
		try {
			Symbol s = p.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
