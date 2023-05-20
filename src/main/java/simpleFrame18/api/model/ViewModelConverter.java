package simpleFrame18.api.model;

/**
 * @author Mario Garcia
 * @since 1.0
 *
 * @param <S>
 * @param <T>
 */
public interface ViewModelConverter<S,T> {

	/**
	 * @param T
	 * @return
	 */
	public T convert(S source);
	
	/**
	 * @param target
	 * @return
	 */
	public S invert(T target);
	
}