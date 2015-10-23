//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

//---------------------------------------------------------------------
// This is the top of the Type hierarchy. You most likely will need to
// create sub-classes (since this one is abstract) that handle specific
// types, such as IntType, FloatType, ArrayType, etc.
//---------------------------------------------------------------------

abstract class BasicType extends Type
{
	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public BasicType(String strName, int size)
	{
		super(strName, size);
	}

}
