package org.easybatch.jdbc;

import org.easybatch.core.api.Header;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link JdbcRecordMapper}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@RunWith(MockitoJUnitRunner.class)
public class JdbcRecordMapperTest {

    private JdbcRecordMapper<Tweet> tweetMapper;

    private JdbcRecord jdbcRecord;

    @Mock
    private ResultSet payload;
    @Mock
    private ResultSetMetaData metadata;

    @Before
    public void setUp() throws Exception {
        tweetMapper = new JdbcRecordMapper<Tweet>(Tweet.class);
        jdbcRecord = new JdbcRecord(new Header(1l, "ds", new Date()), payload);
    }

    @Test
    public void testMapRecord() throws Exception {
        when(payload.getMetaData()).thenReturn(metadata);
        when(metadata.getColumnCount()).thenReturn(3);
        when(metadata.getColumnLabel(1)).thenReturn("id");
        when(metadata.getColumnLabel(2)).thenReturn("user");
        when(metadata.getColumnLabel(3)).thenReturn("message");
        when(payload.getString(1)).thenReturn("1");
        when(payload.getString(2)).thenReturn("foo");
        when(payload.getString(3)).thenReturn("message");

        Tweet tweet = tweetMapper.mapRecord(jdbcRecord);

        assertThat(tweet).isNotNull();
        assertThat(tweet.getId()).isEqualTo(1);
        assertThat(tweet.getUser()).isEqualTo("foo");
        assertThat(tweet.getMessage()).isEqualTo("message");
    }
}
