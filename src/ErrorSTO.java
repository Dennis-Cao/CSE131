//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

class ErrorSTO extends STO
{
	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public ErrorSTO(String strName)
	{
		super(strName, new ErrorType());
	}
	public ErrorSTO(String strName,String str1,String str2)
	{
		super(strName, new ErrorType());
		this.str1=str1;
		this.str2=str2;
	}
	public ErrorSTO(String strName,String str1,String str2, String str3)
	{
		super(strName, new ErrorType());
		this.str1=str1;
		this.str2=str2;
		this.str3=str3;
	}
	public ErrorSTO(String strName, Type typ, String op)
	{
		super(strName, new ErrorType());
		thisTyp=typ;
		thisOp=op;
	}

	//----------------------------------------------------------------
	//	There are times where it is an error if the STO is not a 
	//	constant or adddressable or something else. However, if
	//	the STO is already an error, nothing should be said. To
	//	supress that error, we would have to check if the STO is
	//	not an ErrorSTO as well as what we want it to be.  Rather
	//	than 2 checks we'll have the ErrorSTO always return true
	//	for every check.  (This is an example of where the
	//	instanceof operator would not have been appropriate.)
	//----------------------------------------------------------------
    public boolean isVar()              { return true; }
    public boolean isConst()            { return true; }
    public boolean isExpr()             { return true; }
    public boolean isFunc()             { return true; }
    public boolean isStructdef()        { return true; }
    public boolean isError()            { return true; }

	public boolean getIsAddressable()   { return true; }
	public boolean getIsModifiable()    { return true; }
	public boolean isModLValue()        { return true; }
}
