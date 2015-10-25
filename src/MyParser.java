//---------------------------------------------------------------------
// CSE 131 Reduced-C Compiler Project
// Copyright (C) 2008-2015 Garo Bournoutian and Rick Ord
// University of California, San Diego
//---------------------------------------------------------------------

import java_cup.runtime.*;
import java.util.Vector;

class MyParser extends parser
{
	private Lexer m_lexer;
	private ErrorPrinter m_errors;
	private boolean m_debugMode;
	private int m_nNumErrors;
	private String m_strLastLexeme;
	private boolean m_bSyntaxError = true;
	private int m_nSavedLineNum;

	private SymbolTable m_symtab;

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public MyParser(Lexer lexer, ErrorPrinter errors, boolean debugMode)
	{
		m_lexer = lexer;
		m_symtab = new SymbolTable();
		m_errors = errors;
		m_debugMode = debugMode;
		m_nNumErrors = 0;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public boolean Ok()
	{
		return m_nNumErrors == 0;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public Symbol scan()
	{
		Token t = m_lexer.GetToken();

		//	We'll save the last token read for error messages.
		//	Sometimes, the token is lost reading for the next
		//	token which can be null.
		m_strLastLexeme = t.GetLexeme();

		switch (t.GetCode())
		{
			case sym.T_ID:
			case sym.T_ID_U:
			case sym.T_STR_LITERAL:
			case sym.T_FLOAT_LITERAL:
			case sym.T_INT_LITERAL:
				return new Symbol(t.GetCode(), t.GetLexeme());
			default:
				return new Symbol(t.GetCode());
		}
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public void syntax_error(Symbol s)
	{
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public void report_fatal_error(Symbol s)
	{
		m_nNumErrors++;
		if (m_bSyntaxError)
		{
			m_nNumErrors++;

			//	It is possible that the error was detected
			//	at the end of a line - in which case, s will
			//	be null.  Instead, we saved the last token
			//	read in to give a more meaningful error 
			//	message.
			m_errors.print(Formatter.toString(ErrorMsg.syntax_error, m_strLastLexeme));
		}
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public void unrecovered_syntax_error(Symbol s)
	{
		report_fatal_error(s);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public void DisableSyntaxError()
	{
		m_bSyntaxError = false;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public void EnableSyntaxError()
	{
		m_bSyntaxError = true;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public String GetFile()
	{
		return m_lexer.getEPFilename();
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public int GetLineNum()
	{
		return m_lexer.getLineNumber();
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public void SaveLineNum()
	{
		m_nSavedLineNum = m_lexer.getLineNumber();
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	public int GetSavedLineNum()
	{
		return m_nSavedLineNum;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoProgramStart()
	{
		// Opens the global scope.
		m_symtab.openScope();
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoProgramEnd()
	{
		m_symtab.closeScope();
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoVarDecl(String id)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}
		VarSTO sto = new VarSTO(id);
		m_symtab.insert(sto);
	}
	void DoVarDecl(String id, Type typ)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}
		VarSTO sto = new VarSTO(id, typ);
		m_symtab.insert(sto);
	}
	void DoVarDecl(Type typ, Vector<String> v)
	{
		for(String id:v){
			if (m_symtab.accessLocal(id) != null)
			{
				m_nNumErrors++;
				m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
			}
			VarSTO sto = new VarSTO(id, typ);
			m_symtab.insert(sto);
		}
	}
	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoExternDecl(String id)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}

		VarSTO sto = new VarSTO(id);
		m_symtab.insert(sto);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoConstDecl(String id)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}
		
		ConstSTO sto = new ConstSTO(id, null, 0);   // fix me
		m_symtab.insert(sto);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoStructdefDecl(String id)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}
		
		StructdefSTO sto = new StructdefSTO(id);
		m_symtab.insert(sto);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoFuncDecl_1(String id)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}
		FuncSTO sto = new FuncSTO(id);
		m_symtab.insert(sto);

		m_symtab.openScope();
		m_symtab.setFunc(sto);
	}
	void DoFuncDecl_1(String id,Type typ)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}
		FuncSTO sto = new FuncSTO(id,typ);
		m_symtab.insert(sto);

		m_symtab.openScope();
		m_symtab.setFunc(sto);
	}
	void DoFuncDecl_1(String id,STO ret)
	{
		if (m_symtab.accessLocal(id) != null)
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.redeclared_id, id));
		}
		FuncSTO sto = new FuncSTO(id,ret.getType());
		m_symtab.insert(sto);

		m_symtab.openScope();
		m_symtab.setFunc(sto);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoFuncDecl_2()
	{
		m_symtab.closeScope();
		m_symtab.setFunc(null);
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoFormalParams(Vector<STO> params)
	{

		if (m_symtab.getFunc() == null)
		{
			m_nNumErrors++;
			m_errors.print ("internal: DoFormalParams says no proc!");
		}

		// insert parameters here
		FuncSTO paramList = m_symtab.getFunc();
		if(params != null){
			System.out.println(params.size());
			for(int i=0; i<params.size();i++){
				STO p=params.get(i);
				paramList.addParameter(p);
			}
		}
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoBlockOpen()
	{
		// Open a scope.
		m_symtab.openScope();
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	void DoBlockClose()
	{
		m_symtab.closeScope();
	}
	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	STO DoLoop(STO conditional){
		STO result;
		LoopOp operator = new LoopOp(conditional);
		result=operator.checkOperands(conditional);
		if(result.isError()){
			m_nNumErrors++;
			if(result.getName().equals("Loop"))
				m_errors.print(Formatter.toString(ErrorMsg.error4_Test, result.thisTyp.toString()));
			else{
				m_errors.print(Formatter.toString(ErrorMsg.error1u_Expr, "fuckl u", "rofl", "bool") + result.getName());
			}			
		}
			return result;
		


	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	STO DoAssignExpr(STO stoDes, STO assignedValue)
	{
		if(stoDes instanceof ErrorSTO || assignedValue instanceof ErrorSTO)
			return new ErrorSTO("err");
		System.out.println("Set: " + stoDes.getName() + " to:" + assignedValue.getName());
		if (!stoDes.isModLValue())
		{
				m_errors.print("Left-hand operand is not assignable (not a modifiable L-value).");
            	return new ErrorSTO("NotAss");
		}
		if(((FuncSTO)assignedValue).isFunc())
		{
			if(!((FuncSTO)assignedValue).getReturnType().isAssignableTo(stoDes.getType()))
			{
				if(((FuncSTO)assignedValue).getReturnType().isError()){
					return stoDes;
				}
				m_nNumErrors++;
				if(stoDes.isFunc()){
					m_errors.print(Formatter.toString(ErrorMsg.error3b_Assign, ((FuncSTO)assignedValue).getReturnType().toString(),((FuncSTO)stoDes).getReturnType().toString()));
					return new ErrorSTO("error3b_Assign",assignedValue.getType().toString(),((FuncSTO)stoDes).getReturnType().toString());
				}
				m_errors.print(Formatter.toString(ErrorMsg.error3b_Assign, ((FuncSTO)assignedValue).getReturnType().toString(),stoDes.getType().toString()));
				return new ErrorSTO("error3b_Assign",((FuncSTO)assignedValue).getReturnType().toString(),stoDes.getType().toString());
			}
		}
		else if(!assignedValue.getType().isAssignableTo(stoDes.getType())){
			if(assignedValue.getType().isError()){
				return stoDes;
			}
			m_nNumErrors++;
			if(stoDes.isFunc()){
				m_errors.print(Formatter.toString(ErrorMsg.error3b_Assign, assignedValue.getType().toString(),((FuncSTO)stoDes).getReturnType().toString()));
				return new ErrorSTO("error3b_Assign",assignedValue.getType().toString(),((FuncSTO)stoDes).getReturnType().toString());
			}
			m_errors.print(Formatter.toString(ErrorMsg.error3b_Assign, assignedValue.getType().toString(),stoDes.getType().toString()));
			return new ErrorSTO("error3b_Assign",assignedValue.getType().toString(),stoDes.getType().toString());
		}
		
		return stoDes;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	STO DoFuncCall(STO sto)
	{
		System.out.println(sto.getName());
		if (!sto.isFunc())
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.not_function, sto.getName()));
			return new ErrorSTO(sto.getName());
		}

		return sto;
	}
	STO DoFuncCall(STO sto, Vector<STO> vec)
	{
		Vector<STO> funcParams = ((FuncSTO)sto).getParameter();
		if(vec == null){
			if(funcParams.size() !=0){
				m_nNumErrors++;
				m_errors.print(Formatter.toString(ErrorMsg.error5n_Call, "0",funcParams.size()));
				return new ErrorSTO(sto.getName());
			}
		}

		if(vec != null)
		{
			if(vec.size() != funcParams.size()){
				m_nNumErrors++;
				m_errors.print(Formatter.toString(ErrorMsg.error5n_Call, vec.size(),funcParams.size()));
				return new ErrorSTO(sto.getName());
			}
			for(int i = 0; i < vec.size();i++){
				if(funcParams.get(i).getName().startsWith("&")){
					if(!vec.get(i).isModLValue()) {
						m_nNumErrors++;
						m_errors.print(Formatter.toString(ErrorMsg.error5c_Call,funcParams.get(i).getName(),vec.get(i).getType().toString()));
					}
					else if(!vec.get(i).getType().isEquivalentTo(funcParams.get(i).getType())){
						m_nNumErrors++;
						m_errors.print(Formatter.toString(ErrorMsg.error5r_Call, vec.get(i).getType().toString(),funcParams.get(i).getName(),funcParams.get(i).getType().toString()));
					}
				}
				else if(!vec.get(i).getType().isAssignableTo(funcParams.get(i).getType())){
					m_nNumErrors++;
					m_errors.print(Formatter.toString(ErrorMsg.error5a_Call, vec.get(i).getType().toString(),funcParams.get(i).getName(),funcParams.get(i).getType().toString()));
				}
			}
			if(m_nNumErrors > 0){
				return new ErrorSTO(sto.getName());
			}
		}
		if (!sto.isFunc())
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.not_function, sto.getName()));
			return new ErrorSTO(sto.getName());
		}

		return sto;
	}
	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	STO DoDesignator2_Dot(STO sto, String strID)
	{
		// Good place to do the struct checks

		return sto;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	STO DoDesignator2_Array(STO sto)
	{
		// Good place to do the array checks

		return sto;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	STO DoDesignator3_ID(String strID)
	{
		//p1c0
		STO sto = m_symtab.accessGlobal(strID);

		if ((sto) == null)
		{
			m_nNumErrors++;
		 	m_errors.print(Formatter.toString(ErrorMsg.undeclared_id, strID));
			sto = new ErrorSTO(strID);
		}

		return sto;
	}
	STO DoDesignator3_ID2(String strID)
	{
		//p1c0
		STO sto = m_symtab.access(strID);

		if (sto == null)
		{
			m_nNumErrors++;
		 	m_errors.print(Formatter.toString(ErrorMsg.undeclared_id, strID));
			sto = new ErrorSTO(strID);
		}

		return sto;
	}

	//----------------------------------------------------------------
	//
	//----------------------------------------------------------------
	Type DoStructType_ID(String strID)
	{
		STO sto;

		if ((sto = m_symtab.access(strID)) == null)
		{
			m_nNumErrors++;
		 	m_errors.print(Formatter.toString(ErrorMsg.undeclared_id, strID));
			return new ErrorType();
		}

		if (!sto.isStructdef())
		{
			m_nNumErrors++;
			m_errors.print(Formatter.toString(ErrorMsg.not_type, sto.getName()));
			return new ErrorType();
		}

		return sto.getType();
	}

	STO doBinaryOp(STO a, String op, STO b){
		STO result;
		BinaryOp operator;
		switch (op){
			case "+": operator= new AddOp(a,b);
				break;
			case "-": operator= new MinusOp(a,b);
				break;
			case "*": operator= new MulOp(a,b);
				break;
			case "/": operator= new DivOp(a,b);
				break;
			case "%": operator= new ModOp(a,b);
				break;
			case "&&": operator= new AndOp(a,b);
				break;
			case "||": operator= new OrOp(a,b);
				break;
			case "==": operator= new EqualOp(a,b);
				break;
			case "!=": operator= new NotEqualOp(a,b);
				break;
			case ">": operator= new GreaterThanOp(a,b);
				break;
			case "<": operator= new LessThanOp(a,b);
				break;
			case ">=": operator= new GreaterThanEqualOp(a,b);
				break;
			case "<=": operator= new LessThanEqualOp(a,b);
				break;
			case "^": operator= new XorOp(a,b);
				break;
			case "&": operator= new BwAndOp(a,b);
				break;
			case "|": operator= new BwOrOp(a,b);
				break;
			default: operator= null;
		}

		if(operator!=null){
			result=operator.checkOperands(a,b);
		}
		else {
			result=new ErrorSTO("error");
		}

		//if error and error print message
		if(result.isError()){
			m_nNumErrors++;
			if(result.getName().equals("Arithmetic"))
				m_errors.print(Formatter.toString(ErrorMsg.error1n_Expr, result.thisTyp.toString(), result.thisOp));
			else if(result.getName().equals("Modulus"))
				m_errors.print(Formatter.toString(ErrorMsg.error1w_Expr, result.thisTyp.toString(), "%","int"));
			else if(result.getName().equals("Comparison"))
				m_errors.print(Formatter.toString(ErrorMsg.error1n_Expr, result.thisTyp.toString(), result.thisOp));
			else if(result.getName().equals("Equality"))
				m_errors.print(Formatter.toString(ErrorMsg.error1b_Expr, result.str1, result.str3, result.str2));
			else if(result.getName().equals("Boolean"))
				m_errors.print(Formatter.toString(ErrorMsg.error1w_Expr, result.str1, result.str3, "bool"));
			else if(result.getName().equals("Bitwise"))
				m_errors.print(Formatter.toString(ErrorMsg.error1w_Expr, result.thisTyp.toString(), result.thisOp,"int"));
			else if(result.getName().equals("NotAss"))
				m_errors.print("Left-hand operand is not assignable (not a modifiable L-value).");
			else{
				m_errors.print(Formatter.toString(ErrorMsg.error1u_Expr, "fuckl u", "rofl", "bool") + result.getName());
			}			
		}

		return result;
	}
	
	STO doUnaryOp(STO a, String op){
		STO result;
		UnaryOp operator;
		switch (op){
			case "!": operator= new NotOp("Not","!",a);
				break;
			default: operator= null;
		}

		if(operator!=null){
			result=operator.checkOperands(a);
		}
		else {
			result=new ErrorSTO("error");
		}

		//if error and error print message
		if(result.isError()){
			m_nNumErrors++;
			if(result.getName().equals("Not"))
				m_errors.print(Formatter.toString(ErrorMsg.error1u_Expr, result.str1,"!","bool"));

			// m_errors.print(Formatter.toString(ErrorMsg.not_type, result.getName()));
		}

		return result;
	}
	STO doIncOp(STO a, String op,boolean time){
		STO result;
		UnaryOp operator;
		switch (op){
			case "++": operator= new IncOp("Inc","++",a,time);
				break;
			case "--": operator= new DecOp("Dec","--",a,time);
				break;
			default: operator= null;
		}

		if(operator!=null){
			result=operator.checkOperands(a);
		}
		else {
			result=new ErrorSTO("error");
		}

		//if error and error print message
		if(result.isError()){
			m_nNumErrors++;
			if(result.getName().equals("IncOp"))
				m_errors.print(Formatter.toString(ErrorMsg.error2_Type, result.thisTyp.toString(),result.thisOp));
			if(result.getName().equals("NotMod"))
				m_errors.print(Formatter.toString(ErrorMsg.error2_Lval, result.str1));
			// m_errors.print(Formatter.toString(ErrorMsg.not_type, result.getName()));
		}
		result.setIsModifiable(false);
		result.setIsAddressable(false);
		return result;
	}





}
