package org.microjava.semantics;

import java.util.LinkedList;

public class Struct {
	public static final int NONE = 0, INT = 1, CHAR = 2, ARR = 3, CLASS = 4;
	
	public int kind; 
	public Struct elementType; //only for arrays
	public int numOfFields; // for Class number of fields
	public LinkedList<Obj> fields; // list of fields
	
	
	public Struct (int kind)
	{
		this.kind = kind;
		fields = new LinkedList<Obj>();
	}
	
	public Struct (int kind, Struct elemType)
	{
		this.kind = kind;
		if(kind == ARR)
		{
			this.elementType = elemType;
		}
		fields = new LinkedList<Obj>();
	}
	
	public boolean equals (Object o)
	{
		if(super.equals(0)) return true;
		
		if(!(o instanceof Struct)) return false;
		
		boolean res = this.equals((Struct)o);
		return res;
	}
	
	public boolean isReferencedType()
	{
		if(kind == ARR || kind == CLASS)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean equals(Struct other)
	{
		if(kind == ARR)
		{
			return (other.kind == ARR) && elementType.equals(other.elementType);  
		}
		else if (kind == CLASS)
		{
			return other.kind == CLASS && numOfFields == other.numOfFields && Obj.equalsCompleteList(fields, other.fields);
		} 
		else
		{
			return this == other;
		}
	}
	
	public boolean compatibleWith (Struct other)
	{
		boolean res = this.equals(other) || this == Tab.NULLTYPE && other.isReferencedType() || other == Tab.NULLTYPE && this.isReferencedType();
		return res;
	}
	
	public boolean assignableTo (Struct dest)
	{
		return this.equals(dest) || this == Tab.NULLTYPE && dest.isReferencedType() || kind == ARR && dest.kind == ARR && dest.elementType == Tab.NOTYPE;
	}

}
