package simpleFrame18.api.view;

/**
 * @author Mario Garcia
 * @since 1.0
 *
 */
public class ViewActionDescriptor 
{
	private String expression;

	/**
	 * @param expression
	 */
	public ViewActionDescriptor(String expression){
		this.expression = expression;
	}
	
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
}