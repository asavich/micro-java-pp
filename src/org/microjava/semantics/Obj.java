package org.microjava.semantics;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Obj {

	public static final int CON = 0, VAR = 1, TYPE = 2, METH = 3, FLD = 4, PROG = 5;
	
	public int kind; //CON, VAR, TYPE, METH, FLD, PROG
	public String name;
	public Struct type;
	
	public int address; // CON: value, VAR, FLD, METH: address
	public int level; // VAR: declaration level, METH: number of parameters
	//public LinkedList<Obj> locals; 
	public HashMap<String, Obj> locals;// METH: local objects, PROG
	
	public Obj (int kind, String name, Struct type)
	{
		this.kind = kind;
		this.name = name;
		this.type = type;
		locals = new HashMap<String, Obj>();
	}
	
	
	
	public Obj(int kind, String name, Struct type, int address, int level,
			HashMap<String, Obj> locals) {
		super();
		this.kind = kind;
		this.name = name;
		this.type = type;
		this.address = address;
		this.level = level;
		this.locals = locals;
	}

	public boolean equals (Object o)
	{
		if (super.equals(o)) return true;
		
		if( !(o instanceof Obj)) return false;
		
		Obj other = (Obj)o;
		
		return kind == other.kind && name.equals(other.name) && type.equals(other.type) && address == other.address && level == other.level && equalsCompleteList(locals, other.locals);
		
	}

	public static boolean equalsCompleteList(HashMap<String, Obj> locals1, HashMap<String, Obj> locals2) {
		if(locals1 == locals2) return true;
		if(locals1.size() != locals2.size()) return false;
		
		Map<String, Obj> map1 = locals1;
		Map<String, Obj> map2 = locals2;
		
		for(Map.Entry<String, Obj> e : map1.entrySet())
		{
			if(!map2.containsKey(e))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean equalsCompleteList(LinkedList<Obj> locals1, LinkedList<Obj> locals2) {
		if(locals1 == locals2) return true;
		if(locals1.size() != locals2.size()) return false;
		
		for(int i = 0; i < locals1.size(); i++)
		{
			if(!locals1.get(i).equals(locals2.get(i)))
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder res = new StringBuilder();
		
		boolean b = false;
		
		switch(kind)
		{
		case CON: res.append("Constant "); break;
		case VAR: res.append("Variable: "); break;
		case TYPE: res.append("Type: "); break;
		case METH: res.append("Method: "); break;
		case FLD: res.append("Field: "); b = true; break;
		case PROG: res.append("Program: "); break;
		}
		
		res.append(this.name);
		res.append(": ");
		
		//Map<String, Obj> objlist = locals;
		LinkedList<Obj> objlist = new LinkedList<Obj>(locals.values());
		
		switch(type.kind)
		{
		case Struct.NONE: res.append("notype "); break;
		case Struct.INT: res.append("int "); break;
		case Struct.CHAR: res.append("char "); break;
		case Struct.ARR: res.append("Array of ");
			switch(type.elementType.kind)
			{
			case Struct.NONE: res.append("notype "); break;
			case Struct.INT: res.append("int "); break;
			case Struct.CHAR: res.append("char "); break;
			case Struct.ARR: res.append("Array of ");
			case Struct.CLASS: res.append("Class "); break;
			}
		case Struct.CLASS: {
			res.append("Class ");
			if (!b)
				objlist = type.fields;
			break;
		}
		
		}
		
		res.append(", ");
		res.append(address);
		res.append(", ");
		res.append(level + " ");

		for(Obj o : objlist)
		{
			if(kind == PROG) res.append("\n ");
			res.append("[");
			res.append(o.toString());
			res.append("]");
		}
		
		return res.toString();
	}



	

}
