package cucumber.runtime.java;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import cucumber.annotation.DateTimeFormat;
import cucumber.annotation.en.Given;

public class DateTimeFormatTest {
    
    @Test
    public void find_annotation_on_pojos_fields() {
        JavaBackend backend = mock(JavaBackend.class);
        ClasspathMethodScanner classpathScanner = new ClasspathMethodScanner();        
        classpathScanner.scan(backend, "cucumber.runtime.java");
        
        ArgumentCaptor<Field> fieldArgument = ArgumentCaptor.forClass(Field.class);
        verify(backend).registerFieldConverter(eq(CustomDateFormatPojo.class), fieldArgument.capture(), eq("yyyy/MM/dd"));
        assertEquals("birthDate", fieldArgument.getValue().getName());        
    }
    
    public static class StepDef {
        
        @Given("^the following groceries:$")
        public void someStep(List<CustomDateFormatPojo> pojoList) {
            
        }
    }
    
    public static class CustomDateFormatPojo {
        @DateTimeFormat("yyyy/MM/dd")
        public Date birthDate;
    }
}
