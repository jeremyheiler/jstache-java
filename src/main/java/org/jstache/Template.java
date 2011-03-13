package org.jstache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import org.jstache.internal.BeanPresenter;
import org.jstache.internal.BlockElement;
import org.jstache.internal.MapPresenter;
import org.jstache.internal.Parser;
import org.jstache.internal.Renderer;

/**
 * An instance of <tt>Template</tt> represents a parsed, logic-less template.
 */
public final class Template{
	//private static final Logger log = LoggerFactory.getLogger(Template.class);
	private final BlockElement root;
	private final String begin;
	private final String end;

	Template(Reader template){
		this(template,"{{","}}");
	};

	Template(Reader template,String begin,String end){
		this.root = new Parser(template,begin,end).execute();
		this.begin = begin;
		this.end = end;
	};
	
	public String getBegin(){
		return begin;
	}
	
	public String getEnd(){
		return end;
	}
	
	public static TemplateBuilder with(String begin,String end){
		return new TemplateBuilder(begin,end);
	}

	/**
	 * Creates a Template from from contents of the given {@link Reader}.
	 * 
	 * @param template
	 *            A Reader that wraps a template.
	 * @return A parsed Template that can be rendered.
	 */
	public static Template parse(Reader template){
		return new Template(template);
	}

	/**
	 * Creates a Template from the given String.
	 *
	 * @param template
	 *            A String that represents a template.
	 * @return A parsed Template that can be rendered.
	 */
	public static Template parse(String template){
		return new Template(new StringReader(template));
	}

	/**
	 * Creates a Template from the given File.
	 *
	 * @param template
	 *            A file that points to a template.
	 * @return A parsed Template that can be rendered.
	 * @throws FileNotFoundException
	 *             If the given file does not exist.
	 */
	public static Template parse(File template) throws FileNotFoundException{
		return new Template(new FileReader(template));
	}

	/**
	 * <p>
	 * Renders the template with the given {@link Presenter}.
	 * </p>
	 * <p>
	 * The purpose of a {@link Presenter} is to encapsulate the structure of the
	 * information that will be injected into the template. This method gives
	 * you the option to implement your own {@link Presenter} and render the
	 * template by your own rules. In fact, the other two render methods simply
	 * delegate to the {@link MapPresenter} and the {@link BeanPresenter}.
	 * </p>
	 *
	 * @param presenter
	 * @return The rendered template.
	 * @see MapPresenter
	 * @see BeanPresenter
	 */
	public String render(Presenter presenter){
		return new Renderer(root.getElements(),presenter).execute();
	}

	/**
	 * Renders the template with the values from the given <tt>Map</tt>.
	 * 
	 * @param data
	 *            The map of data with the key being the tag keys and the value
	 *            being the values to be rendered into the template.
	 * @return The rendered template.
	 * @see MapPresenter
	 */
	public String render(Map<String,?> data){
		return render(new MapPresenter(data));
	}

	/**
	 * <p>
	 * Renders the template by converting tag names into methods and dynamically
	 * invoking them and inserting their returned value into the template.
	 * </p>
	 * <p>
	 * <strong>Note:</strong> The returned value of an invoked method is cached
	 * and used again instead of invoking the method multiple times. See the
	 * {@link BeanPresenter} for the complete set of rules.
	 * </p>
	 *
	 * @param bean
	 *            Any bean-like object.
	 * @return The rendered template.
	 * @see BeanPresenter
	 */
	public <T> String render(T bean){
		return render(new BeanPresenter<T>(bean));
	}
}
