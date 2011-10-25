package cucumber.runtime.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.SingleValueConverterWrapper;
import com.thoughtworks.xstream.core.DefaultConverterLookup;

public class LocalizedXStreams {
    private final Map<Locale, XStream> xStreams = new HashMap<Locale, XStream>();
    private final List<ConverterDescription> customConverters = new ArrayList<ConverterDescription>();
    
    public XStream get(Locale locale) {
        XStream xStream = xStreams.get(locale);
        if (xStream == null) {
            xStream = newXStream(locale);
            xStreams.put(locale, xStream);
        }
        return xStream;
    }

    private XStream newXStream(Locale locale) {
        DefaultConverterLookup lookup = new DefaultConverterLookup();

        // XStream's registers all the default converters.
        XStream xStream = new XStream(null, null, Thread.currentThread().getContextClassLoader(), null, lookup, lookup);
        xStream.autodetectAnnotations(true);

        // Override with our own Locale-aware converters.
        register(lookup, new BigDecimalConverter(locale));
        register(lookup, new BigIntegerConverter(locale));
        register(lookup, new ByteConverter(locale));
        register(lookup, new DateConverter(locale));
        register(lookup, new DoubleConverter(locale));
        register(lookup, new FloatConverter(locale));
        register(lookup, new IntegerConverter(locale));
        register(lookup, new LongConverter(locale));
        
        // assume locale not required for customised conversions (Overrides all locales)
        registerAllCustomConverters(xStream);

        return xStream;
    }   

    private void register(DefaultConverterLookup lookup, SingleValueConverter converter) {
        lookup.registerConverter(new SingleValueConverterWrapper(converter), XStream.PRIORITY_VERY_HIGH);
    }
    
    public void addCustomConverter(ConverterDescription converterDefinition) {
        customConverters.add(converterDefinition);
    }
    
    private void registerAllCustomConverters(XStream xStream) {
        for (ConverterDescription description: customConverters) {
            if (description.getFieldType().isAssignableFrom(Date.class)) {
                SingleValueConverter converter = new com.thoughtworks.xstream.converters.basic.DateConverter(description.getPattern(), new String[]{});
                xStream.registerLocalConverter(description.getBeanClass(), description.getField(), converter );
            }
        }
    }

}
