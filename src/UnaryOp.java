// Oodrye

abstract class UnaryOp extends Operator{
    String symbol;
    STO op;

    public UnaryOp(String name, String symbol, STO op){
        super(name);
        setSymbol(symbol);
        this.op = op;
    }

    //setters
    protected void setSymbol(String symbol) {
        this.symbol=symbol;
    }

    //getters
    public String getSymbol() {
        return symbol;
    }


    abstract STO checkOperands(STO a);

}