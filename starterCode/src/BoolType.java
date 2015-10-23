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

public class BoolType extends BasicType
{
	private boolean value;

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public BoolType()
	{
		super("bool", 2);
		setValue(false);
	}

	public BoolType(boolean value)
	{
		super("bool", 2);
		setValue(value);
	}

	private void setValue(boolean value)
	{
		this.value = value;
	}

	public boolean getValue()
	{
		return value;
	}

	// checking basic types
	@Override
	public boolean	isBool()   	{ return true; }

}
