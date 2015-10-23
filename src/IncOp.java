// Oodrye

public class IncOp extends UnaryOp{
    boolean time;
    public IncOp(String name, String symbol, STO op, boolean time)
    {
        super(name, symbol, op);
        this.time=time;
    }

    public IncOp(STO op)
    {
        super("Inc", "++", op);
    }

    public STO checkOperands(STO a)
    {
        Type aType = a.getType();

        // error generated if operand is not modifiable
        if(!(a.getIsModifiable()) ){
            return new ErrorSTO("NotMod",symbol,"throwaway");
        }

        // error generated if operand is not numeric
        if ( !(aType.isNumeric()) )
        {
            // error
            return new ErrorSTO("IncOp",aType,symbol);
        }
        else
        {
        	Type typ;
        	if(aType instanceof FloatType)
        	{
	            typ = new FloatType();
	            return new ExprSTO("Float", typ);
        	}
        	typ = new IntType();
        	return new ExprSTO("Int", typ);
        }
    }

}
