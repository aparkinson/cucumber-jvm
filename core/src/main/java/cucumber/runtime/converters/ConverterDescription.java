package cucumber.runtime.converters;

public class ConverterDescription {
    
    private final Class<?> fieldType;
    private final Class<?> beanClass;
    private final String field;
    private final String pattern;
    
    public ConverterDescription(Class<?> beanClass, Class<?> type,
            String field, String pattern) {
        this.fieldType = type;
        this.beanClass = beanClass;
        this.field = field;
        this.pattern = pattern;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public String getField() {
        return field;
    }

    public String getPattern() {
        return pattern;
    }
    
}
