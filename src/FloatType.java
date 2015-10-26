//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

//---------------------------------------------------------------------
// IntType
//---------------------------------------------------------------------

public class FloatType extends NumericType
{

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public FloatType()
	{
		super("float");
	}


	//----------------------------------------------------------------
	//	Overrides
	//----------------------------------------------------------------
	@Override
	public boolean  isFloat()	    { return true; }

	@Override
	public boolean	isAssignableTo(Type typ) 
	{
		if(typ instanceof FloatType)
			return true;
		return false;
	}
}
