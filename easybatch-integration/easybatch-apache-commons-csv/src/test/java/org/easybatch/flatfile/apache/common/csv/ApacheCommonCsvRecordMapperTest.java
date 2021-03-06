package org.easybatch.flatfile.apache.common.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.easybatch.core.api.Header;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link ApacheCommonCsvRecordMapper}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ApacheCommonCsvRecordMapperTest {

    private ApacheCommonCsvRecordMapper<Foo> mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ApacheCommonCsvRecordMapper<Foo>(Foo.class);
    }

    @Test
    public void testApacheCommonCsvMapping() throws Exception {
        StringReader stringReader = new StringReader("foo,bar,15,true");
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("firstName", "lastName", "age", "married");
        ApacheCommonCsvRecord record = getApacheCommonCsvRecord(stringReader, csvFormat);

        Foo foo = mapper.mapRecord(record);

        assertFoo(foo);
    }

    @Test
    public void testApacheCommonCsvDelimiter() throws Exception {
        StringReader stringReader = new StringReader("foo;bar;15;true");
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withDelimiter(';')
                .withHeader("firstName", "lastName", "age", "married");
        ApacheCommonCsvRecord record = getApacheCommonCsvRecord(stringReader, csvFormat);

        Foo foo = mapper.mapRecord(record);

        assertFoo(foo);
    }

    @Test
    public void testApacheCommonCsvQualifier() throws Exception {
        StringReader stringReader = new StringReader("'foo,s','bar,n'");
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withQuote('\'')
                .withHeader("firstName", "lastName", "age", "married");
        ApacheCommonCsvRecord record = getApacheCommonCsvRecord(stringReader, csvFormat);

        Foo foo = mapper.mapRecord(record);

        assertThat(foo).isNotNull();
        assertThat(foo.getFirstName()).isEqualTo("foo,s");
        assertThat(foo.getLastName()).isEqualTo("bar,n");
        assertThat(foo.getAge()).isEqualTo(0);
        assertThat(foo.isMarried()).isFalse();
    }

    @Test
    public void testApacheCommonCsvLineFeed() throws Exception {
        StringReader stringReader = new StringReader("'foo\n','bar\n'");
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withQuote('\'')
                .withHeader("firstName", "lastName", "age", "married");
        ApacheCommonCsvRecord record = getApacheCommonCsvRecord(stringReader, csvFormat);

        Foo foo = mapper.mapRecord(record);

        assertThat(foo).isNotNull();
        assertThat(foo.getFirstName()).isEqualTo("foo\n");
        assertThat(foo.getLastName()).isEqualTo("bar\n");
        assertThat(foo.getAge()).isEqualTo(0);
        assertThat(foo.isMarried()).isFalse();
    }

    private void assertFoo(Foo foo) {
        assertThat(foo).isNotNull();
        assertThat(foo.getFirstName()).isEqualTo("foo");
        assertThat(foo.getLastName()).isEqualTo("bar");
        assertThat(foo.getAge()).isEqualTo(15);
        assertThat(foo.isMarried()).isTrue();
    }

    private ApacheCommonCsvRecord getApacheCommonCsvRecord(StringReader stringReader, CSVFormat csvFormat) throws IOException {
        CSVParser parser = new CSVParser(stringReader, csvFormat);
        CSVRecord csvRecord = parser.iterator().next();
        return new ApacheCommonCsvRecord(new Header(1l, "DataSource", new Date()), csvRecord);
    }

}
