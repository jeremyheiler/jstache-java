package org.jstache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import org.jstache.internal.Element;
import org.jstache.internal.ProviderStack;
import org.jstache.provider.BeanProvider;
import org.jstache.provider.MapProvider;
import org.jstache.provider.Provider;

/**
 * <p>A <code>Template</code> represents a parsed, logic-less template.</p>
 */
public class Template{
    //private static final Logger log = LoggerFactory.getLogger(Template.class);
    private final List<Element> elements;
    private final Charset encoding;
    private final String begin;
    private final String end;
    private final Mode mode;

    /**
     * <p>The default encoding is set to the platforms default charset.</p>
     */
    public static final Charset DEFAULT_ENCODING = Charset.defaultCharset();

    /**
     * <p>the default begin delimiter is <code>{{</code>.</p>
     */
    public static final String DEFAULT_BEGIN = "{{";

    /**
     * <p>the default end delimiter is <code>}}</code>.</p>
     */
    public static final String DEFAULT_END = "}}";

    /**
     *
     */
    public static final Mode DEFAULT_MODE = Modes.DEFAULT;

    /**
     *
     */
    Template(List<Element> elements, Charset encoding, String begin, String end, Mode mode){
        this.elements = elements;
        this.encoding = encoding;
        this.begin = begin;
        this.end = end;
        this.mode = mode;
    }

    /**
     * Creates a Template from from contents of the given {@link InputStream}.
     *
     * @param source
     * @return A parsed Template that can be rendered.
     */
    public static Template parse(InputStream source){
        return new Parser(DEFAULT_ENCODING, DEFAULT_BEGIN, DEFAULT_END, DEFAULT_MODE).parse(source);
    }

    /**
     * Creates a Template from the given String.
     *
     * @param template
     *            A String that represents a template.
     * @return A parsed Template that can be rendered.
     */
    public static Template parse(String source){
        return new Parser(DEFAULT_ENCODING, DEFAULT_BEGIN, DEFAULT_END, DEFAULT_MODE).parse(source);
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
    public static Template parse(File source) throws FileNotFoundException{
        return new Parser(DEFAULT_ENCODING, DEFAULT_BEGIN, DEFAULT_END, DEFAULT_MODE).parse(source);
    }

    /**
     * Creates a Template from the given File.
     *
     * @param template
     *            A file that points to a template.
     * @return A parsed Template that can be rendered.
     * @throws IOException
     *             If the given URL cannot connect.
     */
    public static Template parse(URL source) throws IOException{
        return new Parser(DEFAULT_ENCODING, DEFAULT_BEGIN, DEFAULT_END, DEFAULT_MODE).parse(source);
    }

    /**
     * Specify the delimiters to parse a template with.
     */
    public static ParserBuilder with(String begin, String end){
        return new ParserBuilder().with(begin, end);
    }

    /**
     *
     */
    public static ParserBuilder encodedAs(Charset encoding){
        return new ParserBuilder().encodedAs(encoding);
    }

    /**
     *
     */
    public static ParserBuilder inMode(Mode mode){
        return new ParserBuilder().inMode(mode);
    }

    /**
     *
     */
    private String render(Provider provider){
        StringBuilder buffer = new StringBuilder();
        ProviderStack stack = new ProviderStack();
        stack.push(provider);
        for(Element element : elements){
            element.render(buffer, stack);
        }
        return buffer.toString();
    }

    /**
     * Renders the template with the values from the given <tt>Map</tt>.
     *
     * @param data
     *            The map of data with the key being the tag keys and the value
     *            being the values to be rendered into the template.
     * @return The rendered template.
     * @see MapProvider
     */
    public String render(Map<String,?> data){
        return render(new MapProvider(data));
    }

    /**
     * <p>
     * Renders the template by converting tag names into methods and dynamically
     * invoking them and inserting their returned value into the template.
     * </p>
     * <p>
     * <strong>Note:</strong> The returned value of an invoked method is cached
     * and used again instead of invoking the method multiple times. See the
     * {@link BeanProvider} for the complete set of rules.
     * </p>
     *
     * @param bean
     *            Any bean-like object.
     * @return The rendered template.
     * @see BeanProvider
     */
    public <T> String render(T bean){
        return render(new BeanProvider<T>(bean));
    }
}

