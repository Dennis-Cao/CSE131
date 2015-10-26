//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

//---------------------------------------------------------------------
// IntType
//---------------------------------------------------------------------

public class IntType extends NumericType
{

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public IntType()
	{
		super("int");
	}

	//----------------------------------------------------------------
	//	Overrides
	//----------------------------------------------------------------
	@Override
	public boolean  isInt()	    { return true; }

	@Override
	public boolean	isAssignableTo(Type typ) 
	{
		if(typ instanceof NumericType)
			return true;
		return false;
	}
}
