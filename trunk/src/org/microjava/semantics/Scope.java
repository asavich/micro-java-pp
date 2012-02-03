package org.microjava.semantics;

import java.util.HashMap;

public class Scope {
	Scope outer;
	public HashMap<String, Obj> locals = new HashMap<String, Obj>(); //key is name
	public int numVars;

}
