package org.microjava.semantics;

import java.util.Map;

public class Tab {
	public static final Struct 
		NOTYPE = new Struct(Struct.NONE),
		INTTYPE = new Struct(Struct.INT),
		CHARTYPE = new Struct(Struct.CHAR),
		NULLTYPE = new Struct(Struct.CLASS);
	
	public static final Obj NOOBJ = new Obj(Obj.VAR, "noObj", NOTYPE);
	
	public static Obj chrObj, ordObj, lenObj;
	
	public static Scope topScope;
	
	public static int level;
	
	public static boolean duplicate = false;
	public static boolean error = false;
	public static int errnum = 0;
	
	public static void init()
	{
		topScope = null;
		Scope s = new Scope();
		s.outer = topScope;
		topScope = s;
		
		int kind = Obj.TYPE;
		// integer
		String intName = "int";
		Struct intType = INTTYPE;
		Obj intObj = new Obj(kind, intName, intType);
		topScope.locals.put(intName, intObj);
		// character
		String charName = "char";
		Obj charObj = new Obj(kind, charName, CHARTYPE);
		topScope.locals.put(charName, charObj);
		//eol
		kind = Obj.CON;
		String eolName = "eol";
		Obj eolObj = new Obj(kind, eolName, CHARTYPE);
		eolObj.address = 10;
		topScope.locals.put(eolName, eolObj);
		//null
		String nullName = "null";
		Obj nullObj = new Obj(kind, nullName, NULLTYPE);
		nullObj.address = 0;
		topScope.locals.put(nullName, nullObj);
		//method chr
		kind = Obj.METH;
		String chrName = "chr";
		Obj chrObject = new Obj(kind, chrName, CHARTYPE);
		chrObject.level = 1;
		Obj argChr = new Obj(Obj.VAR, "i", INTTYPE);
		argChr.address = 0;
		argChr.level = 1;
		chrObject.locals.put("i", argChr);
		chrObj = chrObject;
		topScope.locals.put(chrName, chrObject);
		//method ord
		kind = Obj.METH;
		String ordName = "ord";
		Obj ordObject = new Obj(kind, ordName, INTTYPE);
		ordObject.level = 1;
		Obj argOrd = new Obj(Obj.VAR, "ch", CHARTYPE);
		argOrd.address = 0;
		argOrd.level = 1;
		ordObject.locals.put("ch", argOrd);
		ordObj = ordObject;
		topScope.locals.put(ordName, ordObject);
		//method len
		kind = Obj.METH;
		String lenName = "len";
		Obj lenObject = new Obj(kind, lenName, INTTYPE);
		lenObject.level = 1;
		Obj argLen = new Obj(Obj.VAR, "arr", new Struct(Struct.ARR, NOTYPE));
		argLen.address = 0;
		argLen.level = 1;
		lenObject.locals.put("arr", argLen);
		lenObj = lenObject;
		topScope.locals.put(lenName, lenObject);
		
		// set number of variables
		topScope.numVars = 7;
		level = -1;
	}
	
	
	public static void openScope()
	{
		Scope s = new Scope();
		s.outer = topScope;
		topScope = s;
		level++;
	}
	
	public static void closeScope()
	{
		topScope = topScope.outer;
		level--;
	}
	
	public static Obj insert(int kind, String name, int line, Struct type)
	{
		// duplicates check
		Map<String, Obj> wholeScope = topScope.locals;
		
		for(Map.Entry<String, Obj> entry : wholeScope.entrySet())
		{
			if(entry.getValue().name.equals(name))
			{
				error("Greska na liniji " + line + " (" + name + ") je vec deklarisano" );
				duplicate = true;
				return entry.getValue();
			}
		}
		
		// creating new object
		Obj newObj = new Obj(kind, name, type);
		newObj.address = 0;
		if(level != 0) 
		{
			newObj.level = 1; // local
		}
		else
		{
			newObj.level = 0; // global
		}
		
		//add new object to hash map
		topScope.locals.put(name, newObj);
		
		// increment number of variables
		topScope.numVars++;
		
		return newObj;
	}
	
	public static void error(String err)
	{
		System.err.println(err);
		Tab.error = true;
		Tab.errnum++;
	}
	
	public static Obj find (String name, int line)
	{
		for(Scope s = topScope; s != null; s = s.outer)
		{
			Obj res = s.locals.get(name);
			if(res != null) 
			{
				return res;
			}
		}
		
		error("Greska: na liniji " + line + " (" + name + ") nije nadjeno");
		return NOOBJ;
	}
	
	public static Obj findField(String name, int line, Struct type)
	{
		if(type.kind != Struct.CLASS)
		{
			error("Greska na liniji " + line + " nije instanca klase");
			return NOOBJ;
		}
		
		for(int i = 0; i < type.fields.size(); i++)
		{
			Obj o = type.fields.get(i);
			if(o.name.equals(name))
			{
				return o;
			}
		}
		
		error("Greska na liniji " + line + ". Polje nije nadjeno ");
		return NOOBJ;
	}
	
	public static void dump()
	{
		int l = level;
		System.out.println("======================SADRZAJ TABELE SIMBOLA======================");
		for(Scope s = topScope; s != null; s = s.outer)
		{
			System.out.println("(Level " + l + ")");
			Map<String, Obj> allSymbols = topScope.locals;
			for(Map.Entry<String, Obj> entry : allSymbols.entrySet())
			{
				System.out.println(entry.getValue().toString());
			}
			l--;
		}
	}
}
