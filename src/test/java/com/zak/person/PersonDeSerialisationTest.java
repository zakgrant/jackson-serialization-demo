package com.zak.person;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import uk.co.datumedge.hamcrest.json.SameJSONAs;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

public class PersonDeSerialisationTest {

    private Person person = null;
    private ObjectMapper objectMapper = null;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);

        objectMapper.registerModule(new JodaModule());

        objectMapper.registerModule(new SimpleModule() {
            {
                addSerializer(DateTime.class, new StdSerializer<DateTime>(DateTime.class) {
                    @Override
                    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                        jgen.writeString(ISODateTimeFormat.date().print(value));
                    }
                });
            }
        });

        DateTime dob = new DateTime().withYear(1980)
                .withMonthOfYear(01)
                .withDayOfMonth(01);

        person = new Person(new Name("Joe", "Blogs"), dob)
            .withAddress(new Address(1, "Blog Road", "Blogland", "KK4 1JJ"));
    }

    @Test
    public void shouldDeserialisePersonCorrectly() throws Exception {
        String personJson         = readFileAsString("/person.json");
        Person personDeserialised = objectMapper.readValue(personJson, Person.class);

        assertThat(personDeserialised, is(equalTo(person)));
    }

    @Test
    public void shouldSerialisePersonCorrectly() throws Exception {

        String personJson       = readFileAsString("/person.json");
        String personSerialized = objectMapper.writeValueAsString(person);

        assertThat(personSerialized, sameJSONAs(personJson));
    }

    private String readFileAsString(String path) throws IOException {
        return FileUtils.readFileToString(
                FileUtils.toFile(
                        this.getClass().getResource(path)
                )
        );
    }
}
