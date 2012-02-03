package org.microjava.semantics;


import java.util.LinkedList;

public class Obj {

	public static final int CON = 0, VAR = 1, TYPE = 2, METH = 3, FLD = 4, PROG = 5;
	
	public int kind; //CON, VAR, TYPE, METH, FLD, PROG
	public String name;
	public Struct type;
	
	public int address; // CON: value, VAR, FLD, METH: address
	public int level; // VAR: declaration level, METH: number of parameters
	public LinkedList<Obj> locals; // METH: local objects
	
	public Obj (int kind, String name, Struct type)
	{
		this.kind = kind;
		this.name = name;
		this.type = type;
		locals = new LinkedList<Obj>();
	}
	
	
	
	public Obj(int kind, String name, Struct type, int address, int level,
			LinkedList<Obj> locals) {
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

	public static boolean equalsCompleteList(LinkedList<Obj> fields, LinkedList<Obj> fields2) {
		if(fields == fields2) return true;
		if(fields.size() != fields2.size()) return false;
		
		for(int i=0; i<fields.size(); i++)
		{
			if(fields2.get(i) != fields.get(i)) return false;
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
		
		LinkedList<Obj> objlist = locals;
		
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
