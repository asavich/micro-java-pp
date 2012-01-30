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
			inputFile = new FileReader(new File("jflex_cup/program3.mj"));
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
			if(s.sym == sym.INVALID){
				System.out.println("JBG");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Na kraju je pronadjeno " + p.innerClassDeclarations + " unutrasnjih klasa ");
		System.out.println("Na kraju je pronadjeno " + p.innerClassMethods + " metoda unutrasnih klasa ");
		System.out.println("Na kraju je pronadjeno " + p.mainClassMethods + " metoda glavne klase ");
		System.out.println("Na kraju je pronadjeno " + p.constDeclaration + " globalnih konstanti ");
		System.out.println("Na kraju je pronadjeno " + p.arrayDeclaration + " globalnih nizova ");
		System.out.println("Na kraju je pronadjeno " + p.varDeclaration + " globalnih promenljivih prostog tipa ");
		System.out.println("Na kraju je pronadjeno " + p.numOfFunctions + " poziva funkcija u okviru main ");
		System.out.println("Na kraju je pronadjeno " + p.numOfStatements + " iskaza u okviru main ");
	}

}
