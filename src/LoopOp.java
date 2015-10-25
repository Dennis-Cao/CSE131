public class LoopOp{
    private String op_name;
    STO conditional;
    STO codeblock;
    public LoopOp(STO a, STO b){
        setName("LoopOp");
        setConditional(a);
        setCodeBlock(b);
    }
    public LoopOp(STO a){
        setName("LoopOp");
        setConditional(a);
    }

    //getter
    public String getName(){
        return op_name;
    }

    //setter
    private void setName(String name){
        op_name=name;
    }
    public STO checkOperands(STO a){
        Type aType = a.getType();
        if(!aType.isEquivalentTo(new BoolType())) {
            return new ErrorSTO("Loop",aType,"random");
        }
        return new ExprSTO("loop",new BoolType());
    }
    //setter methods
    private void setConditional(STO a){
        conditional=a;
    }
    private void setCodeBlock(STO b){
        codeblock=b;
    }

    //getter methods
    public STO getConditional(){
        return conditional;
    }
    public STO getCodeBlock(){
        return  codeblock;
    }
}